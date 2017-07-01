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

}
