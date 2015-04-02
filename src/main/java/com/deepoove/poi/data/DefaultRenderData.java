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


/**
 * 默认的渲染数据
 * @author Sayi
 * @version 0.0.2
 */
@Deprecated
public class DefaultRenderData implements RenderData{

	private String color;
	private String fontFamlily;
	private int fontSize;
	private boolean isBold;
	private boolean isItalic;

	private int width;
	private int height;

	private String text;

	private transient byte[] data;

	public DefaultRenderData() {
	}

	public DefaultRenderData(String text) {
		this.text = text;
	}

	public DefaultRenderData(String color, String text) {
		this.color = color;
		this.text = text;
	}

	public DefaultRenderData(int width, int height, String path) {
		this.width = width;
		this.height = height;
		this.text = path;
	}

	public DefaultRenderData(int width, int height, String path, byte[] data) {
		this.width = width;
		this.height = height;
		this.text = path;
		this.data = data;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the fontFamlily
	 */
	public String getFontFamlily() {
		return fontFamlily;
	}

	/**
	 * @param fontFamlily
	 *            the fontFamlily to set
	 */
	public void setFontFamlily(String fontFamlily) {
		this.fontFamlily = fontFamlily;
	}

	/**
	 * @return the fontSize
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * @param fontSize
	 *            the fontSize to set
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * @return the isBold
	 */
	public boolean isBold() {
		return isBold;
	}

	/**
	 * @param isBold
	 *            the isBold to set
	 */
	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}

	/**
	 * @return the isItalic
	 */
	public boolean isItalic() {
		return isItalic;
	}

	/**
	 * @param isItalic
	 *            the isItalic to set
	 */
	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

}
