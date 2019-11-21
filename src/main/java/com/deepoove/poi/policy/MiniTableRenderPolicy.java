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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.render.RenderContext;
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
        return (null != data && (data.isSetBody() || data.isSetHeader()));
    }

    @Override
    public void doRender(RenderContext<MiniTableRenderData> context) throws Exception {
        Helper.renderMiniTable(context.getRun(), context.getData());
    }

    @Override
    protected void afterRender(RenderContext<MiniTableRenderData> context) {
        clearPlaceholder(context, true);
    }

    public static class Helper {

        private static Logger LOG = LoggerFactory.getLogger(Helper.class);

        public static void renderMiniTable(XWPFRun run, MiniTableRenderData data) {
            NiceXWPFDocument doc = (NiceXWPFDocument) run.getParent().getDocument();
            if (!data.isSetBody()) {
                renderNoDataTable(doc, run, data);
            } else {
                renderTable(doc, run, data);
            }
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
                    LOG.warn("Extra cell data at row {}, but no extra cell: col {}", row, cell);
                    break;
                }
                renderCell(cell, cellList.get(i), rowStyle);
            }
        }

        public static void renderCell(XWPFTableCell cell, CellRenderData cellData,
                TableStyle rowStyle) {
            TableStyle cellStyle = (null == cellData.getCellStyle() ? rowStyle
                    : cellData.getCellStyle());

            // 处理单元格样式
            if (null != cellStyle && null != cellStyle.getBackgroundColor()) {
                cell.setColor(cellStyle.getBackgroundColor());
            }

            TextRenderData renderData = cellData.getRenderData();
            String cellText = renderData.getText();
            if (StringUtils.isBlank(cellText)) return;

            // 处理单元格数据
            CTTc ctTc = cell.getCTTc();
            CTP ctP = (ctTc.sizeOfPArray() == 0) ? ctTc.addNewP() : ctTc.getPArray(0);
            XWPFParagraph par = new XWPFParagraph(ctP, cell);
            StyleUtils.styleTableParagraph(par, cellStyle);

            String text = renderData.getText();
            String[] fragment = text.split(TextRenderPolicy.REGEX_LINE_CHARACTOR, -1);

            if (fragment.length <= 1) {
                TextRenderPolicy.Helper.renderTextRun(par.createRun(), renderData);
            } else {
                // 单元格内换行的用不同段落来特殊处理
                XWPFRun run;
                for (int j = 0; j < fragment.length; j++) {
                    if (0 != j) {
                        par = cell.addParagraph();
                        StyleUtils.styleTableParagraph(par, cellStyle);
                    }
                    run = par.createRun();
                    StyleUtils.styleRun(run, renderData.getStyle());
                    run.setText(fragment[j]);
                }
            }
        }

        private static void renderTable(NiceXWPFDocument doc, XWPFRun run,
                MiniTableRenderData tableData) {
            // 1.计算行和列
            int row = tableData.getDatas().size(), col = 0;
            if (!tableData.isSetHeader()) {
                col = getMaxColumFromData(tableData.getDatas());
            } else {
                row++;
                col = tableData.getHeader().size();
            }

            // 2.创建表格
            XWPFTable table = doc.insertNewTable(run, row, col);
            TableTools.initBasicTable(table, col, tableData.getWidth(), tableData.getStyle());

            // 3.渲染数据
            int startRow = 0;
            if (tableData.isSetHeader()) Helper.renderRow(table, startRow++, tableData.getHeader());
            for (RowRenderData data : tableData.getDatas()) {
                Helper.renderRow(table, startRow++, data);
            }

        }

        private static void renderNoDataTable(NiceXWPFDocument doc, XWPFRun run,
                MiniTableRenderData tableData) {
            int row = 2, col = tableData.getHeader().size();

            XWPFTable table = doc.insertNewTable(run, row, col);
            TableTools.initBasicTable(table, col, tableData.getWidth(), tableData.getStyle());

            Helper.renderRow(table, 0, tableData.getHeader());

            TableTools.mergeCellsHorizonal(table, 1, 0, col - 1);
            XWPFTableCell cell = table.getRow(1).getCell(0);
            cell.setText(tableData.getNoDatadesc());

        }

        private static int getMaxColumFromData(List<RowRenderData> datas) {
            int maxColom = 0;
            for (RowRenderData obj : datas) {
                if (null == obj) continue;
                if (obj.size() > maxColom) maxColom = obj.size();
            }
            return maxColom;
        }
    }

}
