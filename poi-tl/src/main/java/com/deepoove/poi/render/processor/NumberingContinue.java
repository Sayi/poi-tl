/*
 * Copyright 2014-2021 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.deepoove.poi.render.processor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTNumPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.XWPFNumberingWrapper;

public class NumberingContinue {

    private Map<BigInteger, BigInteger> consistCache = new HashMap<>();
    private BigInteger continueNumID;

    public NumberingContinue() {}

    public NumberingContinue(BigInteger numID) {
        this.continueNumID = numID;
    }

    public static NumberingContinue of(BodyContainer bodyContainer, int start, int end, IterableTemplate iterable) {
        if (start + 1 >= end) return new NumberingContinue();

        final List<IBodyElement> elements = bodyContainer.getBodyElements().subList(start + 1, end);
        if (elements.isEmpty()) return new NumberingContinue();

        CTNumPr first = null;
        int firstPos = -1;
        for (IBodyElement element : elements) {
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                CTP ctp = paragraph.getCTP();
                if (ctp.getPPr() != null && ctp.getPPr().getNumPr() != null) {
                    CTNumPr numPr = ctp.getPPr().getNumPr();
                    // find first
                    if (null == first) {
                        first = numPr;
                        firstPos = bodyContainer.getPosOfParagraphCTP(ctp);
                    } else {
                        // first is not unique
                        if ((Objects.equals(numPr.getIlvl().getVal(), first.getIlvl().getVal())
                                && Objects.equals(numPr.getNumId().getVal(), first.getNumId().getVal()))) {
                            first = null;
                            break;
                        }
                    }
                }
            }
        }
        if (null == first) return new NumberingContinue();

        // the first is unique, if first inside other iterable section
        List<MetaTemplate> templates = iterable.getTemplates();
        for (MetaTemplate template : templates) {
            if (template instanceof IterableTemplate) {
                CTP startCtp = ((XWPFParagraph) ((IterableTemplate) template).getStartRun().getParent()).getCTP();
                CTP endCtp = ((XWPFParagraph) ((IterableTemplate) template).getEndRun().getParent()).getCTP();

                int startPos = bodyContainer.getPosOfParagraphCTP(startCtp);
                if (startPos >= firstPos) break;

                int endPos = bodyContainer.getPosOfParagraphCTP(endCtp);
                if (firstPos > startPos && firstPos < endPos) { return new NumberingContinue(); }
            }
        }
        return new NumberingContinue(first.getNumId().getVal());
    }

    public NumberingContinue resetCache() {
        this.consistCache.clear();
        return this;
    }

    public void updateNumbering(XWPFParagraph source, XWPFParagraph target) {
        XWPFDocument document = source.getDocument();
        XWPFNumbering numbering = document.getNumbering();
        if (null == numbering) return;
        BigInteger numID = source.getNumID();
        if (numID == null) return;

        if (null != continueNumID && numID.equals(continueNumID)) { return; }

        if (consistCache.get(numID) != null) {
            target.setNumID(consistCache.get(numID));
            return;
        }

        XWPFNumberingWrapper wrapper = new XWPFNumberingWrapper(numbering);
        XWPFNum num = numbering.getNum(numID);
        if (null == num) return;
        XWPFAbstractNum abstractNum = numbering.getAbstractNum(num.getCTNum().getAbstractNumId().getVal());
        CTAbstractNum ctAbstractNum = (CTAbstractNum) abstractNum.getAbstractNum().copy();
        ctAbstractNum.setAbstractNumId(wrapper.getNextAbstractNumID());

        // clear continues list
        // (related to tracking numbering definitions when documents are
        // repurposed and
        // changed
        if (ctAbstractNum.isSetNsid()) ctAbstractNum.unsetNsid();
        // related to where the definition can be displayed in the user
        // interface
        if (ctAbstractNum.isSetTmpl()) ctAbstractNum.unsetTmpl();

        BigInteger abstractNumID = numbering.addAbstractNum(new XWPFAbstractNum(ctAbstractNum));
        BigInteger newNumId = numbering.addNum(abstractNumID);
        target.setNumID(newNumId);
        consistCache.put(numID, newNumId);
    }

}
