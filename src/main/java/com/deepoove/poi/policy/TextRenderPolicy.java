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
package com.deepoove.poi.policy;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

public class TextRenderPolicy implements RenderPolicy {

	private static final String LINE_CHARACTOR = "\\n";

	@Override
	public void render(ElementTemplate runTemplateP, Object renderData,
			XWPFTemplate template) {
		RunTemplate runTemplate = (RunTemplate) runTemplateP;
		XWPFRun run = runTemplate.getRun();
		if (null == renderData) {
			//support String to set blank
			run.setText("", 0);
			return;
		}

		TextRenderData textRenderData = null;
		if (renderData instanceof TextRenderData) {
			textRenderData = (TextRenderData) renderData;
		} else {
			textRenderData = new TextRenderData(String.valueOf(renderData));
		}
		String data = textRenderData.getText();
		styleRun(run, textRenderData.getStyle());
		if (null == data)
			data = "";

		if (data.contains(LINE_CHARACTOR)) {
			// String[] lines = data.split("\\n");
			int from = 0, end = 0;
			List<String> lines = new ArrayList<String>();
			while (-1 != (end = data.indexOf(LINE_CHARACTOR, from))) {
				lines.add(data.substring(from, end));
				from = end + LINE_CHARACTOR.length();
			}
			lines.add(data.substring(from));
			Object[] linesArray = lines.toArray();
			run.setText(linesArray[0].toString(), 0); // set first line into
														// XWPFRun
			for (int i = 1; i < linesArray.length; i++) {
				run.addBreak(); // add break and insert new text
				run.setText(linesArray[i].toString());
			}
		} else {
			run.setText(data, 0);
		}
	}

	private void styleRun(XWPFRun run, Style style) {
		if (null != style) {
			String color = style.getColor();
			String fontFamily = style.getFontFamily();
			int fontSize = style.getFontSize();
			Boolean bold = style.isBold();
			Boolean italic = style.isItalic();
			Boolean strike = style.isStrike();
			if (null != color)
				run.setColor(color);
			if (0 != fontSize)
				run.setFontSize(fontSize);
			if (null != fontFamily)
				run.setFontFamily(fontFamily);
			if (null != bold)
				run.setBold(bold);
			if (null != italic)
				run.setItalic(italic);
			if (null != strike)
				run.setStrike(strike);
		}
	}

}
