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

import com.deepoove.poi.util.BytePictureUtils;

/**
 * 图片渲染数据
 * 
 * @author Sayi
 * @version 0.0.3
 *
 */
public class PictureRenderData implements RenderData {

	/**
	 * 图片宽度
	 */
	private int width;
	/**
	 * 图片高度
	 */
	private int height;
	/**
	 * 图片路径
	 */
	private String path;

	/**
	 * 图片二进制数据
	 */
	private transient byte[] data;

	/**
	 * @param width
	 * @param height
	 * @param path  本地图片路径
	 */
	public PictureRenderData(int width, int height, String path) {
		this.width = width;
		this.height = height;
		this.path = path;
	}

	/**
	 * @param width
	 * @param height
	 * @param path 标识图片后缀，如.png、.jpg等
	 * @param data 图片byte[]数据，可以通过工具类{@link BytePictureUtils}生成
	 */
	public PictureRenderData(int width, int height, String path, byte[] data) {
		this.width = width;
		this.height = height;
		this.path = path;
		this.data = data;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
