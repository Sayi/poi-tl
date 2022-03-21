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

package com.deepoove.poi.xwpf;

import java.util.List;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ISDTContents;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtContentBlock;

import com.deepoove.poi.util.ReflectionUtils;

public class SDTBodyContainer implements BodyContainer {

    private XWPFStructuredDocumentTagContent sdtContent;

    public SDTBodyContainer(XWPFStructuredDocumentTagContent hf) {
        this.sdtContent = hf;
    }

    @Override
    public List<IBodyElement> getBodyElements() {
        return sdtContent.getBodyElements();
    }

    @Override
    public void removeBodyElement(int pos) {
        List<IBodyElement> bodyElements = getBodyElements();
        if (pos >= 0 && pos < bodyElements.size()) {
            BodyElementType type = bodyElements.get(pos).getElementType();
            if (type == BodyElementType.TABLE) {
                // TODO remove table
            }
            if (type == BodyElementType.PARAGRAPH) {
                sdtContent.removeParagraph((XWPFParagraph) bodyElements.get(pos));
            }
        }
    }

    @Override
    public void updateBodyElements(IBodyElement bodyElement, IBodyElement copy) {
        int pos = -1;
        List<ISDTContents> bodyElements = sdtContent.getSdtElements();
        for (int i = 0; i < bodyElements.size(); i++) {
            if (bodyElements.get(i) == bodyElement) {
                pos = i;
            }
        }
        if (-1 != pos) bodyElements.set(pos, (ISDTContents) copy);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setParagraph(XWPFParagraph paragraph, int pos) {
        List<XWPFParagraph> paragraphs = (List<XWPFParagraph>) ReflectionUtils.getValue("paragraphs", sdtContent);
        paragraphs.set(pos, paragraph);
        CTSdtContentBlock sdtContentBlock = sdtContent.getSdtContentBlock();
        sdtContentBlock.setPArray(pos, paragraph.getCTP());

    }

    @Override
    public IBody getTarget() {
        return sdtContent;
    }

    @Override
    public void setTable(int pos, XWPFTable table) {
        throw new UnsupportedOperationException();
    }

    @Override
    public XWPFTable insertNewTable(XWPFRun run, int row, int col) {
        throw new UnsupportedOperationException();
    }

    @Override
    public XWPFSection closelySectPr(IBodyElement element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int elementPageWidth(IBodyElement element) {
        return 8295;
    }

}
