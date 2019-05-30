/*
 * Copyright 2014-2019 the original author or authors.
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
package com.deepoove.poi.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.deepoove.poi.data.style.TableStyle;

/**
 * 表格行数据
 * 
 * @author Sayi
 * @version 1.3.0
 */
public class RowRenderData implements RenderData {

    private List<CellRenderData> cellDatas;

    /**
     * 行级样式，应用到该行所有单元格：背景色、行文字对齐方式
     */
    private TableStyle rowStyle;

    public RowRenderData() {}

    public RowRenderData(List<CellRenderData> cellDatas) {
        this.cellDatas = cellDatas;
    }

    public static RowRenderData build(String... cellStr) {
        List<TextRenderData> cellDatas = new ArrayList<TextRenderData>();
        if (null != cellStr) {
            for (String col : cellStr) {
                cellDatas.add(new TextRenderData(col));
            }
        }
        return new RowRenderData(cellDatas, null);
    }

    public static RowRenderData build(TextRenderData... cellData) {
        return new RowRenderData(null == cellData ? null : Arrays.asList(cellData), null);
    }

    public RowRenderData(List<TextRenderData> rowData, String backgroundColor) {
        this.cellDatas = new ArrayList<CellRenderData>();
        if (null != rowData) {
            for (TextRenderData data : rowData) {
                this.cellDatas.add(new CellRenderData(data));
            }
        }
        TableStyle style = new TableStyle();
        style.setBackgroundColor(backgroundColor);
        this.rowStyle = style;
    }

    public int size() {
        return null == cellDatas ? 0 : cellDatas.size();
    }

    public List<CellRenderData> getCellDatas() {
        return cellDatas;
    }

    public void setCellDatas(List<CellRenderData> cellDatas) {
        this.cellDatas = cellDatas;
    }

    public TableStyle getRowStyle() {
        return rowStyle;
    }

    public void setRowStyle(TableStyle style) {
        this.rowStyle = style;
    }

}
