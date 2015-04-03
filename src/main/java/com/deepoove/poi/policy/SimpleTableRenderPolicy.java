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

import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * 简单的表格处理，暂无样式
 * @author Sayi 卅一
 *
 */
public class SimpleTableRenderPolicy implements RenderPolicy {

	@Override
	public void render(ElementTemplate runTemplateP, Object data,
			XWPFTemplate template) {
		NiceXWPFDocument doc = template.getXWPFDocument();
		RunTemplate runTemplate = (RunTemplate) runTemplateP;
		XWPFRun run = runTemplate.getRun();
		if (null == data) return;
		
		TableRenderData tableData = (TableRenderData) data;
		List<RenderData> headers = tableData.getHeaders();
		List<Object> datas = tableData.getDatas();
		if (datas == null || datas.isEmpty()) {
			//XWPFTable table = doc.createTable(2, headers.size());
			XWPFTable table = doc.insertNewTable(run ,2, headers.size());
			if (null == table){
				logger.warn("cannot insert table.");
				return;
			}
			CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
			width.setW(BigInteger.valueOf(tableData.getWidth()));
			//width.setType(STTblWidth.);
			createHeader(table, headers);
			doc.mergeCellsHorizonal(table, 1, 0, 2);
			XWPFTableCell cell = table.getRow(1).getCell(0);
			cell.setText(tableData.getNoDatadesc());

		} else {
			int size = datas.size();
			XWPFTable table = doc.insertNewTable(run, 1 + size, headers.size());
			CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
			width.setW(BigInteger.valueOf(tableData.getWidth()));
			createHeader(table, headers);
			int i = 1;
			for (Object obj : datas) {
				String str = obj.toString();
				String[] split = str.split(";");
				int length = split.length;
				for (int m = 0; m < length; m++) {
					table.getRow(i).getCell(m).setText(split[m]);
				}
				i++;
			}
		}

		
		
		//doc.getDocument().getBody().insertNewTbl(arg0)
		
		runTemplate.getRun().setText("", 0);

		//XWPFTable table = new XWPFTable(doc.getDocument().getBody().addNewTbl(), doc, 2, headers.size());
		//doc.insertTable(0, table);
//		XmlCursor newCursor =
//		doc.getDocument().getBody().getPArray(1).newCursor();
//		XWPFParagraph paragraph = run.getParagraph();
//		XmlCursor cursor = paragraph.getCTP().newCursor();
//		XWPFTable t1 = doc.insertNewTbl(cursor);
//		t1.getRow(0).getCell(0).setText("are you sure?");
//		XWPFTableCell cell = t1.getRow(0).addNewTableCell();
//		cell.setText("what are you?");
		//XWPFTable tableOne = run.getParagraph().getBody().insertNewTbl(c);
//		run.getCTR().newCursor();

	}

	private void createHeader(XWPFTable table, List<RenderData> headers) {
		int i = 0;
		for (RenderData head : headers) {
			TextRenderData textHead = (TextRenderData) head;
			Style style = textHead.getStyle();
			String color = null == style ? null : style.getColor();
			table.getRow(0).getCell(i).setText(textHead.getText());
			if (null != color)
				table.getRow(0).getCell(i).setColor(color);
			i++;
		}
	}

}
