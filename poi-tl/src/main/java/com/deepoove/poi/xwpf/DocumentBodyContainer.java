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

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;

public class DocumentBodyContainer implements BodyContainer {

    private NiceXWPFDocument doc;

    public DocumentBodyContainer(NiceXWPFDocument doc) {
        this.doc = doc;
    }

    @Override
    public void removeBodyElement(int i) {
        doc.removeBodyElement(i);
    }

    @Override
    public void setParagraph(XWPFParagraph para, int paraPos) {
        doc.setParagraph(para, paraPos);
    }

    @Override
    public IBody getTarget() {
        return doc;
    }

    @Override
    public void setTable(int tablePos, XWPFTable table) {
        doc.setTable(tablePos, table);
    }

    @Override
    public XWPFTable insertNewTable(XWPFRun run, int row, int col) {
        XmlCursor cursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
        XWPFTable table = insertNewTbl(cursor);
        for (int i = 0; i < row; i++) {
            XWPFTableRow tabRow = (table.getRow(i) == null) ? table.createRow() : table.getRow(i);
            for (int k = 0; k < col; k++) {
                if (tabRow.getCell(k) == null) {
                    tabRow.createCell();
                }
            }
        }
        return table;
    }

    @Override
    public XWPFSection closelySectPr(IBodyElement element) {
        List<IBodyElement> bodyElements = doc.getBodyElements();
        boolean isEncounter = false;
        for (IBodyElement ele : bodyElements) {
            if (isEncounter) {
                if (ele instanceof XWPFParagraph) {
                    XWPFParagraph para = (XWPFParagraph) ele;
                    CTP ctp = para.getCTP();
                    if (!ctp.isSetPPr()) continue;
                    CTPPr pPr = ctp.getPPr();
                    if (pPr.isSetSectPr()) {
                        return new XWPFSection(pPr.getSectPr());
                    }
                }
            } else {
                if (ele == element) {
                    isEncounter = true;
                }
            }
        }
        CTBody body = doc.getDocument().getBody();
        if (body.isSetSectPr()) {
            return new XWPFSection(body.getSectPr());
        }
        return null;
    }

    public int elementPageWidth(IBodyElement element) {
        XWPFSection section = closelySectPr(element);
        if (null == section) {
            //throw new IllegalAccessError("Unable to read the page where the element is located.");
            // default A4
            return Page.A4_NORMAL.contentWidth().intValue();
        }
        return section.getPageContentWidth().intValue();
    }

}
