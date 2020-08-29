/*
 * Copyright 2014-2020 Sayi
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
package com.deepoove.poi.policy;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;

import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.util.TableTools;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

/**
 * 表格处理
 * 
 * @author Sayi
 * @since v1.3.0
 */
public class MiniTableRenderPolicy extends AbstractRenderPolicy<MiniTableRenderData> {

    @Override
    protected boolean validate(MiniTableRenderData data) {
        return (null != data && (data.hasBody() || data.hasHeader()));
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

        public static void renderMiniTable(XWPFRun run, MiniTableRenderData data) {
            if (data.hasBody()) {
                renderTable(run, data);
            } else {
                renderNoDataTable(run, data);
            }
        }

        public static void renderTable(XWPFRun run, MiniTableRenderData tableData) {
            // 1.计算行和列
            int row = tableData.getRows().size(), col = 0;
            if (!tableData.hasHeader()) {
                col = getMaxColumFromData(tableData.getRows());
            } else {
                row++;
                col = tableData.getHeader().size();
            }

            // 2.创建表格
            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
            XWPFTable table = bodyContainer.insertNewTable(run, row, col);
            TableTools.initBasicTable(table, col, tableData.getWidth(), tableData.getStyle());

            // 3.渲染数据
            int startRow = 0;
            if (tableData.hasHeader()) Helper.renderRow(table, startRow++, tableData.getHeader());
            for (RowRenderData data : tableData.getRows()) {
                Helper.renderRow(table, startRow++, data);
            }

        }

        public static void renderNoDataTable(XWPFRun run, MiniTableRenderData tableData) {
            int row = 2, col = tableData.getHeader().size();
            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
            XWPFTable table = bodyContainer.insertNewTable(run, row, col);
            TableTools.initBasicTable(table, col, tableData.getWidth(), tableData.getStyle());

            Helper.renderRow(table, 0, tableData.getHeader());

            TableTools.mergeCellsHorizonal(table, 1, 0, col - 1);
            XWPFTableCell cell = table.getRow(1).getCell(0);
            cell.setText(tableData.getNoDatadesc());
        }

        public static void renderRow(XWPFTable table, int row, RowRenderData rowData) {
            if (null == rowData || rowData.size() <= 0) return;
            XWPFTableRow tableRow = table.getRow(row);
            Objects.requireNonNull(tableRow, "Row [" + row + "] do not exist in the table");

            TableStyle rowStyle = rowData.getRowStyle();
            List<CellRenderData> cellDatas = rowData.getCells();
            XWPFTableCell cell = null;

            for (int i = 0; i < cellDatas.size(); i++) {
                cell = tableRow.getCell(i);
                Objects.requireNonNull(cell, "Cell [" + i + "] do not exist at row " + row);
                renderCell(cell, cellDatas.get(i), rowStyle);
            }
        }

        public static void renderCell(XWPFTableCell cell, CellRenderData cellData, TableStyle rowStyle) {
            TableStyle cellStyle = (null == cellData.getCellStyle() ? rowStyle : cellData.getCellStyle());
            if (null != cellStyle && null != cellStyle.getBackgroundColor()) {
                cell.setColor(cellStyle.getBackgroundColor());
            }

            TextRenderData renderData = cellData.getCellText();
            if (StringUtils.isBlank(renderData.getText())) return;

            CTTc ctTc = cell.getCTTc();
            CTP ctP = (ctTc.sizeOfPArray() == 0) ? ctTc.addNewP() : ctTc.getPArray(0);
            XWPFParagraph par = new XWPFParagraph(ctP, cell);
            StyleUtils.styleTableParagraph(par, cellStyle);

            TextRenderPolicy.Helper.renderTextRun(par.createRun(), renderData);
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
