/*
 * Copyright 2014-2021 Sayi
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;

import com.deepoove.poi.data.BookmarkTextRenderData;
import com.deepoove.poi.data.HyperlinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.util.TableTools;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

/**
 * text render policy
 * 
 * @author Sayi
 *
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
            if (data instanceof HyperlinkTextRenderData) {
                textRun = createHyperlink(run, ((HyperlinkTextRenderData) data).getUrl());
            }

            TextRenderData wrapper = wrapper(data);
            StyleUtils.styleRun(textRun, wrapper.getStyle());

            String text = wrapper.getText();
            String[] fragment = text.split(REGEX_LINE_CHARACTOR, -1);
            if (fragment.length > 0) {
                textRun.setText(fragment[0], 0);
                boolean lineAtTable = fragment.length > 1 && !(data instanceof HyperlinkTextRenderData)
                        && TableTools.isInsideTable(run);
                for (int i = 1; i < fragment.length; i++) {
                    if (lineAtTable) {
                        textRun.addBreak(BreakType.TEXT_WRAPPING);
                    } else {
                        textRun.addCarriageReturn();
                    }
                    textRun.setText(fragment[i]);
                }
            }
            if (data instanceof BookmarkTextRenderData) {
                createBookmark(textRun, ((BookmarkTextRenderData) data).getBookmark());
            }
        }

        private static TextRenderData wrapper(Object obj) {
            TextRenderData text = obj instanceof TextRenderData ? (TextRenderData) obj
                    : new TextRenderData(obj.toString());
            return null == text.getText() ? new TextRenderData("") : text;
        }

        private static XWPFRun createHyperlink(XWPFRun run, String url) {
            XWPFParagraphWrapper paragraph = new XWPFParagraphWrapper((XWPFParagraph) run.getParent());
            XWPFRun hyperlink = paragraph.insertNewHyperLinkRun(run, url);
            StyleUtils.styleRun(hyperlink, run);
            run.setText("", 0);
            return hyperlink;
        }

        private static void createBookmark(XWPFRun textRun, String name) {
            XWPFParagraphWrapper wapper = new XWPFParagraphWrapper((XWPFParagraph) textRun.getParent());
            CTBookmark bookmarkStart = wapper.insertNewBookmark(textRun);
            bookmarkStart.setName(name);
//                bookmarkStart.setName(Base64.getEncoder().encodeToString(text.getBytes()));
        }
    }
}
