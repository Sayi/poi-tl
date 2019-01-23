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

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;

/**
 * 
 * @author Sayi
 * @version 
 */
public class TextRenderPolicy implements RenderPolicy {

	static final String REGEX_LINE_CHARACTOR = "\\n";

	@Override
	public void render(ElementTemplate eleTemplate, Object renderData, XWPFTemplate template) {
		RunTemplate runTemplate = (RunTemplate) eleTemplate;
		XWPFRun run = runTemplate.getRun();
		if (null == renderData) {
			// support String to set blank
			run.setText("", 0);
			return;
		}

		TextRenderData textRenderData = null;
		if (renderData instanceof TextRenderData) {
			textRenderData = (TextRenderData) renderData;
		} else {
			textRenderData = new TextRenderData(renderData.toString());
		}
		String data = textRenderData.getText();
		StyleUtils.styleRun(run, textRenderData.getStyle());
		if (null == data) data = "";
		
		String[] split = data.split(REGEX_LINE_CHARACTOR);
		if (null != split){
		    run.setText(split[0], 0); 
		    for (int i = 1; i < split.length; i++) {
                run.addBreak(); 
                run.setText(split[i]);
            }
		}
	}
}
