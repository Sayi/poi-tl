/*
 * Copyright 2014-2020 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.policy;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.util.TableTools;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

/**
 * text render
 */
public class TextRenderPolicy extends AbstractRenderPolicy<Object> {

    @Override
    protected boolean validate(Object data) {
        return null != data;
    }

    @Override
    public void doRender(RenderContext<Object> context) throws Exception {
        Helper.renderTextRun(context.getRun(), context.getData());
    }

    public static class Helper {

        public static final String REGEX_LINE_CHARACTOR = "\\n";

        public static void renderTextRun(XWPFRun run, Object data) {
            XWPFRun textRun = run;
            if (data instanceof HyperLinkTextRenderData) {
                textRun = createHyperLinkRun(run, data);
            }

            TextRenderData wrapperData = wrapper(data);
            String text = null == wrapperData.getText() ? "" : wrapperData.getText();
            StyleUtils.styleRun(textRun, wrapperData.getStyle());

            String[] split = text.split(REGEX_LINE_CHARACTOR, -1);
            if (split.length > 0) {
                textRun.setText(split[0], 0);
                boolean lineAtTable = split.length > 1 && !(data instanceof HyperLinkTextRenderData)
                        && TableTools.isInsideTable(run);
                for (int i = 1; i < split.length; i++) {
                    if (lineAtTable) {
                        textRun.addBreak(BreakType.TEXT_WRAPPING);
                    } else {
                        textRun.addCarriageReturn();
                    }
                    textRun.setText(split[i]);
                }
            }
        }

        private static TextRenderData wrapper(Object obj) {
            return obj instanceof TextRenderData ? (TextRenderData) obj : new TextRenderData(obj.toString());
        }

        private static XWPFRun createHyperLinkRun(XWPFRun run, Object data) {
            XWPFParagraphWrapper paragraph = new XWPFParagraphWrapper((XWPFParagraph) run.getParent());
            XWPFRun hyperLinkRun = paragraph.insertNewHyperLinkRun(run, ((HyperLinkTextRenderData) data).getUrl());
            StyleUtils.styleRun(hyperLinkRun, run);
            run.setText("", 0);
            return hyperLinkRun;
        }
    }
}
