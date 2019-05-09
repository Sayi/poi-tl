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

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFParagraphWrapper;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;

/**
 * 
 * @author Sayi
 * @version
 */
public class TextRenderPolicy extends AbstractRenderPolicy {

    static final String REGEX_LINE_CHARACTOR = "\\n";

    @Override
    protected boolean validate(Object data) {
        return null != data;
    }

    @Override
    public void doRender(RunTemplate runTemplate, Object renderData, XWPFTemplate template)
            throws Exception {
        XWPFRun run = runTemplate.getRun();

        // create hyper link run
        if (renderData instanceof HyperLinkTextRenderData) {
            run = createHyperLinkRun(runTemplate, renderData);
        }

        // text
        TextRenderData textRenderData = renderData instanceof TextRenderData
                ? (TextRenderData) renderData : new TextRenderData(renderData.toString());

        String data = null == textRenderData.getText() ? "" : textRenderData.getText();

        StyleUtils.styleRun(run, textRenderData.getStyle());

        String[] split = data.split(REGEX_LINE_CHARACTOR, -1);
        if (null != split && split.length > 0) {
            run.setText(split[0], 0);
            for (int i = 1; i < split.length; i++) {
                run.addBreak();
                run.setText(split[i]);
            }
        }
    }

    private XWPFRun createHyperLinkRun(RunTemplate runTemplate, Object renderData) {
        XWPFRun run = runTemplate.getRun();
        XWPFParagraphWrapper paragraph = new XWPFParagraphWrapper((XWPFParagraph) run.getParent());
        XWPFRun hyperLinkRun = paragraph.insertNewHyperLinkRun(runTemplate.getRunPos(),
                ((HyperLinkTextRenderData) renderData).getUrl());
        StyleUtils.styleRun(hyperLinkRun, run);
        clearPlaceholder(run);
        return run = hyperLinkRun;
    }

}
