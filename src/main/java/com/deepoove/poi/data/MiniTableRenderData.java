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

import java.util.List;

import com.deepoove.poi.data.style.TableStyle;

/**
 * 表格数据
 * 
 * @author Sayi
 * @version 1.3.0
 */
public class MiniTableRenderData implements RenderData {
	
	/**
	 * 通用边距的表格宽度：A4(20.99*29.6),页边距为3.17*2.54
	 */
	public static final float WIDTH_A4_FULL = 14.65f;
	/**
	 * 窄边距的表格宽度：A4(20.99*29.6),页边距为1.27*1.27
	 */
	public static final float WIDTH_A4_NARROW_FULL = 18.45f;
	/**
	 * 适中边距的表格宽度：A4(20.99*29.6),页边距为1.91*2.54
	 */
	public static final float WIDTH_A4_MEDIUM_FULL = 17.17f;
	/**
	 * 宽边距的表格宽度：A4(20.99*29.6),页边距为5.08*2.54
	 */
	public static final float WIDTH_A4_EXTEND_FULL = 10.83f;

    private RowRenderData headers;
    private List<RowRenderData> datas;
    
    private String noDatadesc;
    
    private TableStyle style;

    /**
     * 最大宽度为：页面宽度-页边距宽度*2
     * 单位：cm
     */
    private float width;

    public MiniTableRenderData(List<RowRenderData> datas) {
        this(null, datas);
    }
    
    public MiniTableRenderData(RowRenderData headers, List<RowRenderData> datas) {
        this(headers, datas, WIDTH_A4_FULL);
    }
    
    public MiniTableRenderData(RowRenderData headers, List<RowRenderData> datas, float width) {
        this(headers, datas, null, width);
    }
    
    public MiniTableRenderData(RowRenderData headers, String noDatadesc) {
        this(headers, null, noDatadesc, WIDTH_A4_FULL);
    }
    

    /**
     * @param headers
     *            表格头
     * @param datas
     *            表格数据
     * @param noDatadesc
     *            没有数据显示的文案
     * @param width
     *            宽度
     */
    public MiniTableRenderData(RowRenderData headers, List<RowRenderData> datas,
            String noDatadesc, float width) {
        this.headers = headers;
        this.datas = datas;
        this.noDatadesc = noDatadesc;
        this.width = width;
    }
    
    
    public boolean isSetHeader(){
        return null != headers && headers.size() > 0;
    }
    public boolean isSetBody(){
        return null != datas && datas.size() > 0;
    }

    public String getNoDatadesc() {
        return noDatadesc;
    }

    public void setNoDatadesc(String noDatadesc) {
        this.noDatadesc = noDatadesc;
    }

    public RowRenderData getHeaders() {
        return headers;
    }

    public void setHeaders(RowRenderData headers) {
        this.headers = headers;
    }

    public List<RowRenderData> getDatas() {
        return datas;
    }

    public void setDatas(List<RowRenderData> datas) {
        this.datas = datas;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

	public TableStyle getStyle() {
		return style;
	}

	public void setStyle(TableStyle style) {
		this.style = style;
	}
    
    

}
