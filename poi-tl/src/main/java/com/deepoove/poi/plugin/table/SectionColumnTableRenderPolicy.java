/*
 * Copyright 2014-2026 Sayi
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
package com.deepoove.poi.plugin.table;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.TableTools;

/**
 * Marks a table section for deletion when the bound value is false.
 * <p>
 * When the data is {@code null} or {@link Boolean#FALSE} the tag is replaced by
 * the {@code _delete_} marker so that a following
 * {@link RemoveTableColumnRenderPolicy} can drop the whole column, and the
 * vertically merged continuation cell in the next row is marked as well. Any other
 * value clears the tag and keeps the row.
 * </p>
 *
 * @author Sayi
 */
public class SectionColumnTableRenderPolicy implements RenderPolicy {

    /**
     * Marks the tagged section for deletion when the bound value is false.
     *
     * @param eleTemplate the tag that marks the section
     * @param data        {@code null} or {@code false} to delete the section, any other
     *                    value keeps it
     * @param template    the template instance being rendered
     * @throws RenderException if the tag is not inside a table or the section cannot be
     *         marked
     */
    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        XWPFRun run = runTemplate.getRun();
        try {
            if (!TableTools.isInsideTable(run)) {
                throw new IllegalStateException(
                        "The template tag " + runTemplate.getSource() + " must be inside a table");
            }
            XWPFTableCell tagCell = (XWPFTableCell) ((XWPFParagraph) run.getParent()).getBody();
            XWPFTable table = tagCell.getTableRow().getTable();
            int templateRowIndex = getTemplateRowIndex(tagCell);
            int templateColIndex = getColIndex(tagCell);
            if (null == data || (data instanceof Boolean && !(Boolean) data)) {
//                table.removeRow(templateRowIndex);
//                tagCell.setText("_delete_");
                run.setText("_delete_", 0);
                
                CTTcPr tcPr = TableTools.getTcPr(tagCell);
                CTVMerge vMerge = tcPr.getVMerge();
                System.out.println(null == vMerge ? null : vMerge.getVal());
                if (null != vMerge && vMerge.getVal().equals(STMerge.RESTART)) {
                    XWPFTableRow row = table.getRow(templateRowIndex + 1);
                    int actualInsertPosition = getActualInsertPosition(row, templateColIndex);
                    XWPFTableCell actualCell = row.getCell(actualInsertPosition);
                    System.out.println(actualCell.getCTTc());
                    actualCell.getParagraphArray(0).createRun().setText("_delete_", 0);
                    
                }
                
            } else {
                run.setText("", 0);
            }
        } catch (Exception e) {
            throw new RenderException("HackLoopTable for " + eleTemplate + "error: " + e.getMessage(), e);
        }
    }

    private int getTemplateRowIndex(XWPFTableCell tagCell) {
        XWPFTableRow tagRow = tagCell.getTableRow();
        return getRowIndex(tagRow);
    }

    private int getRowIndex(XWPFTableRow row) {
        List<XWPFTableRow> rows = row.getTable().getRows();
        return rows.indexOf(row);
    }
    
    private int getColIndex(XWPFTableCell cell) {
        XWPFTableRow tableRow = cell.getTableRow();
        int orginalCol = 0;
        for (int i = 0; i < tableRow.getTableCells().size(); i++) {
            XWPFTableCell current = tableRow.getCell(i);
            int intValue = 1;
            CTTcPr tcPr = current.getCTTc().getTcPr();
            if (null != tcPr) {
                CTDecimalNumber gridSpan = tcPr.getGridSpan();
                if (null != gridSpan) intValue = gridSpan.getVal().intValue();
            }
            orginalCol += intValue;
            if (current.getCTTc() == cell.getCTTc()) {
                return orginalCol - intValue;
            }
        }
        return -1;
    }
    
    private int getActualInsertPosition(XWPFTableRow tableRow, int insertPosition) {
        int orginalCol = 0;
        for (int i = 0; i < tableRow.getTableCells().size(); i++) {
            XWPFTableCell current = tableRow.getCell(i);
            int intValue = 1;
            CTTcPr tcPr = current.getCTTc().getTcPr();
            if (null != tcPr) {
                CTDecimalNumber gridSpan = tcPr.getGridSpan();
                if (null != gridSpan) intValue = gridSpan.getVal().intValue();
            }
            orginalCol += intValue;
            if (orginalCol - intValue == insertPosition && intValue == 1) {
                return i;
            }
        }
        return -1;
    }

    private XWPFTableCell getActualCell(XWPFTableRow tableRow, int insertPosition) {
        int orginalCol = 0;
        for (int i = 0; i < tableRow.getTableCells().size(); i++) {
            XWPFTableCell current = tableRow.getCell(i);
            int intValue = 1;
            CTTcPr tcPr = current.getCTTc().getTcPr();
            if (null != tcPr) {
                CTDecimalNumber gridSpan = tcPr.getGridSpan();
                if (null != gridSpan) intValue = gridSpan.getVal().intValue();
            }
            orginalCol += intValue;
            if (orginalCol - 1 >= insertPosition) {
                return current;
            }
        }
        return null;
    }

}
