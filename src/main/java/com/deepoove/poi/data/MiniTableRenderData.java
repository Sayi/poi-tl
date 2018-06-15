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

/**
 * 表格数据
 * 
 * @author Sayi
 * @version 1.3.0
 */
public class MiniTableRenderData implements RenderData {

    private RowRenderData headers;
    private List<RowRenderData> datas;
    private String noDatadesc;

    /**
     * dxa - Specifies that the value is in twentieths of a point (1/1440 of an
     * inch).
     */
    private int width;

    public MiniTableRenderData(RowRenderData headers, List<RowRenderData> datas) {
        this(headers, datas, null, 0);
    }
    
    public MiniTableRenderData(List<RowRenderData> datas) {
        this(null, datas, null, 0);
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
            String noDatadesc, int width) {
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
