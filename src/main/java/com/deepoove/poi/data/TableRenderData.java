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
package com.deepoove.poi.data;

import java.util.List;

/**
 * 简单的表格渲染数据
 * 
 * @author Sayi
 * @version 0.0.3
 */
public class TableRenderData implements RenderData {

	private List<RenderData> headers;
	private List<Object> datas;
	private String noDatadesc;

	// 9500
	private int width;

	public TableRenderData(List<RenderData> headers, List<Object> datas) {
		this(headers, datas, null, 0);
	}

	/**
	 * 动态渲染的表格
	 * 
	 * @param datas
	 * @param noDatadesc
	 */
	public TableRenderData(List<Object> datas, String noDatadesc) {
		this(null, datas, null, 0);
	}

	/**
	 * @param headers 表格头
	 * @param datas 表格数据
	 * @param noDatadesc 没有数据显示的文案
	 * @param width 宽度
	 */
	public TableRenderData(List<RenderData> headers, List<Object> datas,
			String noDatadesc, int width) {
		this.headers = headers;
		this.datas = datas;
		this.noDatadesc = noDatadesc;
		this.width = width;
	}

	public List<Object> getDatas() {
		return datas;
	}

	public void setDatas(List<Object> datas) {
		this.datas = datas;
	}

	public String getNoDatadesc() {
		return noDatadesc;
	}

	public void setNoDatadesc(String noDatadesc) {
		this.noDatadesc = noDatadesc;
	}

	public List<RenderData> getHeaders() {
		return headers;
	}

	public void setHeaders(List<RenderData> headers) {
		this.headers = headers;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
