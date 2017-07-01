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
package com.deepoove.poi.util;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.style.Style;

/**
 * 样式工具类
 * 
 * @author Sayi
 * @version
 */
public final class StyleUtils {

	/**
	 * 设置run的样式
	 * 
	 * @param run
	 * @param style
	 */
	public static void styleRun(XWPFRun run, Style style) {
		if (null == run || null == style) return;
		String color = style.getColor();
		String fontFamily = style.getFontFamily();
		int fontSize = style.getFontSize();
		Boolean bold = style.isBold();
		Boolean italic = style.isItalic();
		Boolean strike = style.isStrike();
		if (null != color) run.setColor(color);
		if (0 != fontSize) run.setFontSize(fontSize);
		if (null != fontFamily) run.setFontFamily(fontFamily);
		if (null != bold) run.setBold(bold);
		if (null != italic) run.setItalic(italic);
		if (null != strike) run.setStrikeThrough(strike);
	}

	/**
	 * 重复样式
	 * 
	 * @param destRun
	 * @param srcRun
	 */
	public static void styleRun(XWPFRun destRun, XWPFRun srcRun) {
		if (null == destRun || null == srcRun) return;
		destRun.setBold(srcRun.isBold());
		destRun.setColor(srcRun.getColor());
		// destRun.setCharacterSpacing(srcRun.getCharacterSpacing());
		destRun.setFontFamily(srcRun.getFontFamily());
		int fontSize = srcRun.getFontSize();
		if (-1 != fontSize) destRun.setFontSize(fontSize);
		destRun.setItalic(srcRun.isItalic());
		destRun.setStrikeThrough(srcRun.isStrikeThrough());
		destRun.setUnderline(srcRun.getUnderline());
	}

}
