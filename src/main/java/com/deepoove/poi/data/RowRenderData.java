package com.deepoove.poi.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.deepoove.poi.data.style.TableStyle;

/**
 * 行数据
 * 
 * @author Sayi
 * @version 1.3.0
 */
public class RowRenderData implements RenderData {

    private List<TextRenderData> rowData;

    private TableStyle style;
    
    public RowRenderData() {
    }

    public RowRenderData(List<TextRenderData> rowData) {
        this.rowData = rowData;
    }
    
    public static RowRenderData build(String...row) {
    	RowRenderData instance = new RowRenderData();
    	instance.rowData = new ArrayList<TextRenderData>();
    	for (String col : row) {
    		instance.rowData.add(new TextRenderData(col));
    	}
    	return instance;
    }
    
    public static RowRenderData build(TextRenderData...row) {
    	RowRenderData instance = new RowRenderData();
    	instance.rowData = null == row ? null : Arrays.asList(row);
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
