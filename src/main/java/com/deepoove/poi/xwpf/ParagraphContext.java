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

import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFFieldRun;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;

public class ParagraphContext implements RunBodyContext {

    private XWPFParagraphWrapper paragraphWrapper;

    public ParagraphContext(XWPFParagraphWrapper paragraphWrapper) {
        this.paragraphWrapper = paragraphWrapper;
    }

    @Override
    public IRunBody getTarget() {
        return paragraphWrapper.getParagraph();
    }

    @Override
    public List<XWPFRun> getRuns() {
        return paragraphWrapper.getParagraph().getRuns();
    }

    @Override
    public void setAndUpdateRun(XWPFRun xwpfRun, XWPFRun sourceRun, int insertPostionCursor) {
        paragraphWrapper.setAndUpdateRun(xwpfRun, sourceRun, insertPostionCursor);
    }

    @Override
    public XWPFRun insertNewRun(XWPFRun xwpfRun, int insertPostionCursor) {
        if (xwpfRun instanceof XWPFHyperlinkRun) {
            return paragraphWrapper.insertNewHyperLinkRun(insertPostionCursor, "");
        } else if (xwpfRun instanceof XWPFFieldRun) {
            return paragraphWrapper.insertNewField(insertPostionCursor);
        } else {
            return paragraphWrapper.insertNewRun(insertPostionCursor);
        }
    }

    @Override
    public XWPFRun createRun(XWPFRun xwpfRun, IRunBody p) {
        if (xwpfRun instanceof XWPFHyperlinkRun) {
            return new XWPFHyperlinkRun((CTHyperlink) ((XWPFHyperlinkRun) xwpfRun).getCTHyperlink().copy(),
                    (CTR) ((XWPFHyperlinkRun) xwpfRun).getCTR().copy(), p);
        } else if (xwpfRun instanceof XWPFFieldRun) {
            return new XWPFFieldRun((CTSimpleField) ((XWPFFieldRun) xwpfRun).getCTField().copy(),
                    (CTR) ((XWPFFieldRun) xwpfRun).getCTR().copy(), p);
        } else {
            return new XWPFRun((CTR) xwpfRun.getCTR().copy(), p);
        }
    }

    @Override
    public XWPFRun createRun(XmlObject object, IRunBody p) {
        if (object instanceof CTHyperlink) {
            return new XWPFHyperlinkRun((CTHyperlink) object, ((CTHyperlink) object).getRArray(0), p);
        } else if (object instanceof CTSimpleField) {
            return new XWPFFieldRun((CTSimpleField) object, ((CTSimpleField) object).getRArray(0), p);
        } else {
            return new XWPFRun((CTR) object, p);
        }
    }

    @Override
    public void removeRun(int pos) {
        paragraphWrapper.removeRun(pos);
    }

}
