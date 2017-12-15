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

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

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
 * 
 * @author Sayi 卅一
 *
 */
public class SimpleTableRenderPolicy implements RenderPolicy {

    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        NiceXWPFDocument doc = template.getXWPFDocument();
        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        XWPFRun run = runTemplate.getRun();
        if (null == data) return;

        TableRenderData tableData = (TableRenderData) data;
        List<RenderData> headers = tableData.getHeaders();
        List<Object> datas = tableData.getDatas();

        if (datas == null || datas.isEmpty()) {
            if (headers == null || headers.isEmpty()) {
                runTemplate.getRun().setText("", 0);
                return;
            }
            // XWPFTable table = doc.createTable(2, headers.size());
            XWPFTable table = doc.insertNewTable(run, 2, headers.size());
            if (null == table) {
                logger.warn("cannot insert table.");
                return;
            }
            widthTable(table, tableData.getWidth(), 2, headers.size());

            createHeader(table, headers);
            doc.mergeCellsHorizonal(table, 1, 0, headers.size() - 1);
            XWPFTableCell cell = table.getRow(1).getCell(0);
            cell.setText(tableData.getNoDatadesc());

        } else {
            int maxColom = 0;
            int row = datas.size();
            int startRow = 1;
            if (headers == null || headers.isEmpty()) {
                startRow = 0;
                maxColom = getMaxColumFromData(datas);
            } else {
                row++;
                maxColom = headers.size();
            }
            XWPFTable table = doc.insertNewTable(run, row, maxColom);
            widthTable(table, tableData.getWidth(), row, maxColom);
            createHeader(table, headers);
            for (Object obj : datas) {
                if (null == obj) continue;
                String str = obj.toString();
                String[] split = str.split(";");
                int length = split.length;
                for (int m = 0; m < length; m++) {
                    XWPFTableCell cell = table.getRow(startRow).getCell(m);
                    // new break line
                    String[] fragment = split[m].split(TextRenderPolicy.REGEX_LINE_CHARACTOR);
                    if (null != fragment) {
                        cell.setText(fragment[0]);
                        for (int i = 1; i < fragment.length; i++) {
                            XWPFParagraph addParagraph = cell.addParagraph();
                            addParagraph.createRun().setText(fragment[i]);
                        }
                    }
                }
                startRow++;
            }
        }
        runTemplate.getRun().setText("", 0);
    }

    private void widthTable(XWPFTable table, int width, int rows, int cols) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (null == tblPr) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
        CTTblWidth tblW = tblPr.getTblW();
        if (tblW == null) {
            tblW = tblPr.addNewTblW();
        }
        tblW.setType(0 == width ? STTblWidth.AUTO : STTblWidth.DXA);
        tblW.setW(BigInteger.valueOf(width));

        if (0 != width) {
            CTTblGrid tblGrid = table.getCTTbl().getTblGrid();
            if (null == tblGrid) {
                tblGrid = table.getCTTbl().addNewTblGrid();
            }

            for (int j = 0; j < cols; j++) {
                CTTblGridCol addNewGridCol = tblGrid.addNewGridCol();
                addNewGridCol.setW(BigInteger.valueOf(width / cols));
            }
        }
        CTTblBorders tblBorders = tblPr.getTblBorders();
        tblBorders.getBottom().setSz(BigInteger.valueOf(4));
        tblBorders.getLeft().setSz(BigInteger.valueOf(4));
        tblBorders.getTop().setSz(BigInteger.valueOf(4));
        tblBorders.getRight().setSz(BigInteger.valueOf(4));
        tblBorders.getInsideH().setSz(BigInteger.valueOf(4));
        tblBorders.getInsideV().setSz(BigInteger.valueOf(4));
        // for (int i = 0; i < rows; i++){
        // for (int j = 0; j < cols; j++){
        // XWPFTableCell cell = table.getRow(i).getCell(j);
        // widthTableCell(cell, width/cols);
        // }
        // }
    }

    // private void widthTableCell(XWPFTableCell cell, int width) {
    // CTTcPr tcPr = cell.getCTTc().getTcPr();
    // if (null == tcPr){
    // tcPr = cell.getCTTc().addNewTcPr();
    // }
    // CTTblWidth tcW = tcPr.getTcW();
    // if (tcW == null){
    // tcW = tcPr.addNewTcW();
    // }
    // tcW.setType(0 == width ? STTblWidth.AUTO : STTblWidth.DXA);
    // tcW.setW(BigInteger.valueOf(width));
    //
    // }

    private int getMaxColumFromData(List<Object> datas) {
        int maxColom = 0;
        for (Object obj : datas) {
            if (null == obj) continue;
            String str = obj.toString();
            String[] split = str.split(";");
            if (split.length > maxColom) maxColom = split.length;
        }
        return maxColom;
    }

    private void createHeader(XWPFTable table, List<RenderData> headers) {
        if (null == headers || headers.isEmpty()) return;
        int i = 0;
        for (RenderData head : headers) {
            TextRenderData textHead = (TextRenderData) head;
            Style style = textHead.getStyle();
            String color = null == style ? null : style.getColor();
            XWPFTableCell cell = table.getRow(0).getCell(i);
            String[] fragment = textHead.getText().split(TextRenderPolicy.REGEX_LINE_CHARACTOR);
            if (null != fragment) {
                cell.setText(fragment[0]);
                for (int j = 1; j < fragment.length; j++) {
                    XWPFParagraph addParagraph = cell.addParagraph();
                    addParagraph.createRun().setText(fragment[j]);
                }
            }
            if (null != color) table.getRow(0).getCell(i).setColor(color);
            i++;
        }
    }

}
