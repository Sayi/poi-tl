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

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.deepoove.poi.data.CellV2RenderData;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.RowV2RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.TableV2Style;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

/**
 * 
 * @author Sayi
 */
public class TableRenderPolicy extends AbstractRenderPolicy<TableRenderData> {

    @Override
    protected boolean validate(TableRenderData data) {
        if (null == data || 0 == data.obtainColSize()) return false;
        List<RowV2RenderData> rows = data.getRows();
        final int col = data.obtainColSize();
        for (RowV2RenderData row : rows) {
            if (col != row.obtainColSize()) {
                throw new IllegalArgumentException("Number of cells in each row should be the same!");
            }
        }
        TableV2Style tableStyle = data.getTableStyle();
        if (null != tableStyle) {
            int[] colWidths = tableStyle.getColWidths();
            if (null != colWidths && colWidths.length != col) {
                throw new IllegalArgumentException("Number of cells in each row should be the same!");
            }
        }
        return true;
    }

    @Override
    public void doRender(RenderContext<TableRenderData> context) throws Exception {
        Helper.renderTable(context.getRun(), context.getData());
    }

    @Override
    protected void afterRender(RenderContext<TableRenderData> context) {
        clearPlaceholder(context, true);
    }

    public static class Helper {

        public static void renderTable(XWPFRun run, TableRenderData data) throws Exception {
            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
            XWPFTable table = bodyContainer.insertNewTable(run, data.obtainRowSize(), data.obtainColSize());
            StyleUtils.styleTable(table, data.getTableStyle());

            List<XWPFTableRow> rows = table.getRows();
            int size = rows.size();
            for (int i = 0; i < size; i++) {
                renderRow(rows.get(i), data.getRows().get(i));
            }
        }

        public static void renderRow(XWPFTableRow row, RowV2RenderData data) throws Exception {
            int size = row.getTableCells().size();
            if (size != data.obtainColSize()) {
                throw new IllegalArgumentException("Number of cells and render data should be the same!");
            }

            StyleUtils.styleTableRow(row, data.getRowStyle());
            CellStyle defaultCellStyle = null == data.getRowStyle() ? null : data.getRowStyle().getDefaultCellStyle();

            for (int i = 0; i < size; i++) {
                renderCell(row.getCell(i), data.getCells().get(i), defaultCellStyle);
            }
        }

        public static void renderCell(XWPFTableCell cell, CellV2RenderData data, CellStyle defaultCellStyle)
                throws Exception {
            StyleUtils.styleTableCell(cell, defaultCellStyle);
            StyleUtils.styleTableCell(cell, data.getCellStyle());
            ParagraphAlignment defaultParaAlign = null == data.getCellStyle()
                    ? (null == defaultCellStyle ? null : defaultCellStyle.getDefaultParagraphAlign())
                    : data.getCellStyle().getDefaultParagraphAlign();

            List<ParagraphRenderData> cellParagraphDatas = data.getParagraphs();
            if (null != cellParagraphDatas && !cellParagraphDatas.isEmpty()) {
                BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(cell);
                XWPFParagraph placeHolder = cell.getParagraphArray(0);
                if (null == placeHolder) placeHolder = cell.addParagraph();

                ParagraphStyle defaultParaStyle = ParagraphStyle.builder().withAlign(defaultParaAlign).build();
                for (ParagraphRenderData item : cellParagraphDatas) {
                    XWPFParagraph paragraph = bodyContainer.insertNewParagraph(placeHolder.getCTP().newCursor());
                    StyleUtils.styleParagraph(paragraph, defaultParaStyle);
                    ParagraphRenderPolicy.Helper.renderParagraph(paragraph.createRun(), item);
                }

                List<XWPFParagraph> paragraphs = cell.getParagraphs();
                int pos = paragraphs.indexOf(placeHolder);
                if (-1 != pos) cell.removeParagraph(pos);
            }
        }

    }

}
