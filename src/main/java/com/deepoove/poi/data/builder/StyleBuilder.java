package com.deepoove.poi.data.builder;

import com.deepoove.poi.data.style.Style;

/**
 * 样式构建器
 * 
 * @author Sayi
 *
 */
public class StyleBuilder {

	Style style;

	private StyleBuilder() {
		style = new Style();
	}

	public StyleBuilder newBuilder() {
		return new StyleBuilder();
	}

	public StyleBuilder buildColor(String color) {
		style.setColor(color);
		return this;
	}

	public StyleBuilder buildFontFamily(String fontFamily) {
		style.setFontFamily(fontFamily);
		return this;
	}

	public StyleBuilder buildFontSize(int fontSize) {
		style.setFontSize(fontSize);
		return this;
	}

	public StyleBuilder buildBold() {
		style.setBold(true);
		return this;
	}

	public StyleBuilder buildItalic() {
		style.setItalic(true);
		return this;
	}

	public StyleBuilder buildStrike() {
		style.setStrike(true);
		return this;
	}

	public StyleBuilder buildUnderLine() {
		style.setUnderLine(true);
		return this;
	}

	public Style build() {
		return style;
	}

}
