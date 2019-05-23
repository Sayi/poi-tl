/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.policy;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.ObjectUtils;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.util.TableTools;

/**
 * 表格处理
 * 
 * @author Sayi 卅一
 * @since v1.3.0
 */
public class MiniTableRenderPolicy extends AbstractRenderPolicy<MiniTableRenderData> {

    @Override
    protected boolean validate(MiniTableRenderData data) {
        if (!(data).isSetBody() && !(data).isSetHeader()) {
            logger.debug("Empty MiniTableRenderData datamodel: {}", data);
            return false;
        }
        return true;
    }

    @Override
    public void doRender(RunTemplate runTemplate, MiniTableRenderData data, XWPFTemplate template)
            throws Exception {
        NiceXWPFDocument doc = template.getXWPFDocument();
        XWPFRun run = runTemplate.getRun();

        if (!data.isSetBody()) {
            renderNoDataTable(doc, run, data);
        } else {
            renderTable(doc, run, data);
        }

        // 成功后，才会清除标签，发生异常则保留标签，可以重写doRenderException方法在发生异常后也会清除标签
        clearPlaceholder(run);
    }

    private void renderTable(NiceXWPFDocument doc, XWPFRun run, MiniTableRenderData tableData) {
        // 1.计算行和列
        int row = tableData.getDatas().size(), col = 0;
        if (!tableData.isSetHeader()) {
            col = getMaxColumFromData(tableData.getDatas());
        } else {
            row++;
            col = tableData.getHeaders().size();
        }

        // 2.创建表格
        XWPFTable table = doc.insertNewTable(run, row, col);
        initBasicTable(table, col, tableData.getWidth(), tableData.getStyle());

        // 3.渲染数据
        int startRow = 0;
        if (tableData.isSetHeader()) renderRow(table, startRow++, tableData.getHeaders());
        for (RowRenderData data : tableData.getDatas()) {
            renderRow(table, startRow++, data);
        }

    }

    private void renderNoDataTable(NiceXWPFDocument doc, XWPFRun run,
            MiniTableRenderData tableData) {
        int row = 2, col = tableData.getHeaders().size();

        XWPFTable table = doc.insertNewTable(run, row, col);
        initBasicTable(table, col, tableData.getWidth(), tableData.getStyle());

        renderRow(table, 0, tableData.getHeaders());

        TableTools.mergeCellsHorizonal(table, 1, 0, tableData.getHeaders().size() - 1);
        XWPFTableCell cell = table.getRow(1).getCell(0);
        cell.setText(tableData.getNoDatadesc());

    }

    private void initBasicTable(XWPFTable table, int col, float width, TableStyle style) {
        TableTools.widthTable(table, width, col);
        TableTools.borderTable(table, 4);
        StyleUtils.styleTable(table, style);
    }

    /**
     * 填充表格一行的数据
     * 
     * @param table
     * @param row
     *            第几行
     * @param rowData
     *            行数据：确保行数据的大小不超过表格该行的单元格数量
     */
    public static void renderRow(XWPFTable table, int row, RowRenderData rowData) {
        if (null == rowData || rowData.size() <= 0) return;
        XWPFTableRow tableRow = table.getRow(row);
        ObjectUtils.requireNonNull(tableRow, "Row " + row + " do not exist in the table");

        TableStyle rowStyle = rowData.getRowStyle();
        List<CellRenderData> cellList = rowData.getCellDatas();
        XWPFTableCell cell = null;

        for (int i = 0; i < cellList.size(); i++) {
            cell = tableRow.getCell(i);
            if (null == cell) {
                logger.warn("Extra cell data at row {}, but no extra cell: col {}", row, cell);
                break;
            }
            
            CellRenderData cellData = cellList.get(i);
            TableStyle cellStyle = (null == cellData.getCellStyle() ? rowStyle : cellData.getCellStyle());

            // 处理单元格样式
            if (null != cellStyle && null != cellStyle.getBackgroundColor()) {
                cell.setColor(cellStyle.getBackgroundColor());
            }

            TextRenderData renderData = cellData.getRenderData();
            String cellText = renderData.getText();
            if (StringUtils.isBlank(cellText)) continue;

            String[] fragment = cellText.split(TextRenderPolicy.REGEX_LINE_CHARACTOR, -1);
            if (null == fragment || fragment.length <= 0) continue;

            // 处理单元格数据
            XWPFParagraph par;
            XWPFRun run;
            for (int j = 0; j < fragment.length; j++) {
                if (0 == j) {
                    CTTc ctTc = cell.getCTTc();
                    CTP ctP = (ctTc.sizeOfPArray() == 0) ? ctTc.addNewP() : ctTc.getPArray(0);
                    par = new XWPFParagraph(ctP, cell);
                } else {
                    par = cell.addParagraph();
                }
                StyleUtils.styleTableParagraph(par, cellStyle);
                run = par.createRun();
                StyleUtils.styleRun(run, renderData.getStyle());
                run.setText(fragment[j]);
            }
        }
    }

    private int getMaxColumFromData(List<RowRenderData> datas) {
        int maxColom = 0;
        for (RowRenderData obj : datas) {
            if (null == obj) continue;
            if (obj.size() > maxColom) maxColom = obj.size();
        }
        return maxColom;
    }

}
