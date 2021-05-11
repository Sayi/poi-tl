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
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTComment;

import com.deepoove.poi.plugin.comment.XWPFComment;
import com.deepoove.poi.util.ReflectionUtils;

public class CommentBodyContainer implements BodyContainer {

    private XWPFComment comment;

    public CommentBodyContainer(XWPFComment hf) {
        this.comment = hf;
    }

    @Override
    public void removeBodyElement(int pos) {
        List<IBodyElement> bodyElements = getBodyElements();
        if (pos >= 0 && pos < bodyElements.size()) {
            BodyElementType type = bodyElements.get(pos).getElementType();
            if (type == BodyElementType.TABLE) {
                comment.removeTable((XWPFTable) bodyElements.get(pos));
            }
            if (type == BodyElementType.PARAGRAPH) {
                comment.removeParagraph((XWPFParagraph) bodyElements.get(pos));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setParagraph(XWPFParagraph p, int paraPos) {
        List<XWPFParagraph> paragraphs = (List<XWPFParagraph>) ReflectionUtils.getValue("paragraphs", comment);
        paragraphs.set(paraPos, p);
        CTComment ctc = comment.getCtComment();
        ctc.setPArray(paraPos, p.getCTP());

    }

    @Override
    public IBody getTarget() {
        return comment;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setTable(int pos, XWPFTable table) {
        // cell.getTables().set(pos, table);
        List<XWPFTable> tables = (List<XWPFTable>) ReflectionUtils.getValue("tables", comment);
        tables.set(pos, table);
        comment.getCtComment().setTblArray(pos, table.getCTTbl());

    }

    @Override
    public XWPFTable insertNewTable(XWPFRun run, int row, int col) {
        XmlCursor cursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
        XWPFTable table = insertNewTbl(cursor);
        // hack for cursor.removeXmlContents(); in XWPFHeaderFooter
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 0; i < rows.size(); i++) {
            table.removeRow(i);
        }
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
        throw new UnsupportedOperationException();
    }

    @Override
    public int elementPageWidth(IBodyElement element) {
        return 8295;
    }

}
