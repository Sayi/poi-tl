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
    public void doRender(RenderContext<Object> context) throws Exception {
        Helper.renderTextRun(context.getRun(), context.getData());
    }

    public static class Helper {

        public static void renderTextRun(XWPFRun run, Object renderData) {
            // create hyper link run
            if (renderData instanceof HyperLinkTextRenderData) {
                XWPFRun hyperLinkRun = NiceXWPFDocument.insertNewHyperLinkRun(run,
                        ((HyperLinkTextRenderData) renderData).getUrl());
                run.setText("", 0);
                run = hyperLinkRun;
            }

            // text
            TextRenderData textRenderData = renderData instanceof TextRenderData
                    ? (TextRenderData) renderData
                    : new TextRenderData(renderData.toString());

            String data = null == textRenderData.getText() ? "" : textRenderData.getText();

            StyleUtils.styleRun(run, textRenderData.getStyle());

            String[] split = data.split(REGEX_LINE_CHARACTOR, -1);
            if (null != split && split.length > 0) {
                run.setText(split[0], 0);
                for (int i = 1; i < split.length; i++) {
                    run.addCarriageReturn();
                    run.setText(split[i]);
                }
            }
        }
    }
}
