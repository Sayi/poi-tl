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

    private List<TextRenderData> rowData;

    /**
     * 行样式：背景色、行文字对齐方式
     */
    private TableStyle style;
    
    public RowRenderData() {
    }

    public RowRenderData(List<TextRenderData> rowData) {
        this.rowData = rowData;
    }
    
    public static RowRenderData build(String...cellStr) {
    	RowRenderData instance = new RowRenderData();
    	instance.rowData = new ArrayList<TextRenderData>();
    	for (String col : cellStr) {
    		instance.rowData.add(new TextRenderData(col));
    	}
    	return instance;
    }
    
    public static RowRenderData build(TextRenderData...rowData) {
    	RowRenderData instance = new RowRenderData();
    	instance.rowData = null == rowData ? null : Arrays.asList(rowData);
    	return instance;
    }
    
    public RowRenderData(List<TextRenderData> rowData, String backgroundColor) {
        this.rowData = rowData;
        TableStyle style = new TableStyle();
        style.setBackgroundColor(backgroundColor);
        this.style = style;
    }

    public int size() {
        return null == rowData ? 0 : rowData.size();
    }

    public List<TextRenderData> getRowData() {
        return rowData;
    }

    public void setRowData(List<TextRenderData> rowData) {
        this.rowData = rowData;
    }

	public TableStyle getStyle() {
		return style;
	}

	public void setStyle(TableStyle style) {
		this.style = style;
	}

}
