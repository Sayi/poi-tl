package com.deepoove.poi.data;

import java.util.List;

/**
 * 行数据
 * 
 * @author Sayi
 * @version 1.3.0
 */
public class RowRenderData implements RenderData {

    private List<TextRenderData> rowData;

    /**
     * 行背景色
     */
    private String backgroundColor;

    public RowRenderData(List<TextRenderData> rowData) {
        this.rowData = rowData;
    }

    public RowRenderData(List<TextRenderData> rowData, String backgroundColor) {
        this.rowData = rowData;
        this.backgroundColor = backgroundColor;
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

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
