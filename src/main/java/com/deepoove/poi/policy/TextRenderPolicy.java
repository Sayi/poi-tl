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

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;

/**
 * @author Sayi
 * @version
 */
public class TextRenderPolicy extends AbstractRenderPolicy<Object> {

    public static final String REGEX_LINE_CHARACTOR = "\\n";

    @Override
    protected boolean validate(Object data) {
        return null != data;
    }

    @Override
    public void doRender(RenderContext<Object> context) throws Exception {
        Helper.renderTextRun(context.getRun(), context.getData());
    }

    public static class Helper {

        public static void renderTextRun(XWPFRun run, Object renderData) {
            XWPFRun textRun = run;
            // create hyper link run
            if (renderData instanceof HyperLinkTextRenderData) {
                XWPFRun hyperLinkRun = NiceXWPFDocument.insertNewHyperLinkRun(run,
                        ((HyperLinkTextRenderData) renderData).getUrl());
                run.setText("", 0);
                textRun = hyperLinkRun;
            }

            // text
            TextRenderData data = renderData instanceof TextRenderData
                    ? (TextRenderData) renderData
                    : new TextRenderData(renderData.toString());

            String text = null == data.getText() ? "" : data.getText();

            StyleUtils.styleRun(textRun, data.getStyle());

            String[] split = text.split(REGEX_LINE_CHARACTOR, -1);
            if (null != split && split.length > 0) {
                textRun.setText(split[0], 0);
                for (int i = 1; i < split.length; i++) {
                    textRun.addCarriageReturn();
                    textRun.setText(split[i]);
                }
            }
        }
    }
}
