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

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;

/**
 * 表格处理
 * 
 * @author Sayi 卅一
 * @since v1.3.0
 */
public class MiniTableRenderPolicy implements RenderPolicy {

    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        NiceXWPFDocument doc = template.getXWPFDocument();
        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        XWPFRun run = runTemplate.getRun();
        if (null == data) return;

        MiniTableRenderData tableData = (MiniTableRenderData) data;
        RowRenderData headers = tableData.getHeaders();
        List<RowRenderData> datas = tableData.getDatas();
        TableStyle style = tableData.getStyle();
        float width = tableData.getWidth();

        if (!tableData.isSetBody()) {
            if (!tableData.isSetHeader()) {
                run.setText("", 0);
                return;
            }
            int row = 2;
            int col = headers.size();
            int startRow = 1;
            
            XWPFTable table = createTableWithHeaders(doc, run, headers, row, col, width);
            StyleUtils.styleTable(table, style);
            NiceXWPFDocument.mergeCellsHorizonal(table, 1, 0, headers.size() - 1);
            XWPFTableCell cell = table.getRow(startRow).getCell(0);
            cell.setText(tableData.getNoDatadesc());

        } else {
            int row = datas.size();
            int col = 0;
            int startRow = 0;
            if (!tableData.isSetHeader()) {
                col = getMaxColumFromData(datas);
            } else {
                startRow++;
                row++;
                col = headers.size();
            }
            
            XWPFTable table = createTableWithHeaders(doc, run, headers, row, col, width);
            StyleUtils.styleTable(table, style);
            for (RowRenderData obj : datas) {
                renderRow(table, startRow++, obj);
            }
        }
        run.setText("", 0);
    }

    private XWPFTable createTableWithHeaders(NiceXWPFDocument doc, XWPFRun run, RowRenderData headers,
            int row, int col, float width) {
        XWPFTable table = doc.insertNewTable(run, row, col);
        doc.widthTable(table, width, row, col);
        renderRow(table, 0, headers);
        return table;
    }

	/**
	 * 填充表格一行的数据
	 * @param table 
	 * @param row 第几行
	 * @param rowData 行数据：确保行数据的大小不超过表格该行的单元格数量
	 */
	public static void renderRow(XWPFTable table, int row, RowRenderData rowData) {
		if (null == rowData || rowData.size() <= 0) return;
		int i = 0;
		TableStyle style = rowData.getStyle();
		List<TextRenderData> cellDatas = rowData.getRowData();
		XWPFTableCell cell = null;
		for (TextRenderData cellData : cellDatas) {
			cell = table.getRow(row).getCell(i);
			String[] fragment = cellData.getText().split(TextRenderPolicy.REGEX_LINE_CHARACTOR);
			if (null != fragment) {
				CTTc ctTc = cell.getCTTc();
				CTP ctP = (ctTc.sizeOfPArray() == 0) ? ctTc.addNewP() : ctTc.getPArray(0);
				XWPFParagraph par = new XWPFParagraph(ctP, cell);
				StyleUtils.styleTableParagraph(par, style);
				XWPFRun run = par.createRun();
				StyleUtils.styleRun(run, cellData.getStyle());
				run.setText(fragment[0]);
				for (int j = 1; j < fragment.length; j++) {
					XWPFParagraph addParagraph = cell.addParagraph();
					StyleUtils.styleTableParagraph(addParagraph, style);
					run = addParagraph.createRun();
					StyleUtils.styleRun(run, cellData.getStyle());
					run.setText(fragment[j]);
				}
			}

			if (null != style && null != style.getBackgroundColor()) cell.setColor(style.getBackgroundColor());
			i++;
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
