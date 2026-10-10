/*
 * Copyright 2014-2026 Sayi
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

import java.util.List;
import java.util.Objects;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.plugin.comment.CommentRenderData;
import com.deepoove.poi.plugin.comment.CommentRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.ParagraphUtils;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

/**
 * Renders a tag as a paragraph
 * <p>
 * The paragraph of the tag is reused: the contents of the
 * {@link ParagraphRenderData} are written as runs into it, supporting
 * {@link TextRenderData}, {@link PictureRenderData} and comment data, and the
 * paragraph and text styles of the data are applied on top of the inherited
 * default styles.
 * </p>
 * 
 * @author Sayi
 *
 */
public class ParagraphRenderPolicy extends AbstractRenderPolicy<ParagraphRenderData> {

    /**
     * {@inheritDoc}
     * <p>
     * The data is valid when it is not {@code null} and has at least one content.
     * </p>
     */
    @Override
    protected boolean validate(ParagraphRenderData data) {
        return null != data && !data.getContents().isEmpty();
    }

    /**
     * {@inheritDoc}
     * <p>
     * It clears the tag text without removing the paragraph.
     * </p>
     */
    @Override
    protected void afterRender(RenderContext<ParagraphRenderData> context) {
        clearPlaceholder(context, false);
    }

    /**
     * {@inheritDoc}
     * <p>
     * It delegates to {@link Helper#renderParagraph(XWPFRun, ParagraphRenderData)}.
     * </p>
     */
    @Override
    public void doRender(RenderContext<ParagraphRenderData> context) throws Exception {
        Helper.renderParagraph(context.getRun(), context.getData());

    }

    /**
     * Utilities that fill a paragraph with mixed contents.
     */
    public static class Helper {

        /**
         * Renders the contents into the paragraph of the given run.
         * 
         * @param run  the run holding the tag
         * @param data the paragraph render data
         * @throws Exception when a content can not be rendered
         */
        public static void renderParagraph(XWPFRun run, ParagraphRenderData data) throws Exception {
            renderParagraph(run, data, null);
        }

        /**
         * Renders the contents into the paragraph of the given run, applying the
         * default styles first.
         * 
         * @param run                   the run holding the tag
         * @param data                  the paragraph render data
         * @param defaultControlStyles the default paragraph styles inherited from the
         *                              surrounding control, may be {@code null}
         * @throws Exception when a content can not be rendered
         */
        public static void renderParagraph(XWPFRun run, ParagraphRenderData data,
                List<ParagraphStyle> defaultControlStyles) throws Exception {
            List<RenderData> contents = data.getContents();
            XWPFParagraph paragraph = (XWPFParagraph) run.getParent();
            styleParagraphWithDefaultStyle(paragraph, defaultControlStyles);
            StyleUtils.styleParagraph(paragraph, data.getParagraphStyle());

            XWPFParagraphWrapper parentContext = new XWPFParagraphWrapper(paragraph);
            for (RenderData content : contents) {
                XWPFRun fragment = parentContext.insertNewRun(ParagraphUtils.getRunPos(run));
                StyleUtils.styleRun(fragment, run);
                if (content instanceof TextRenderData) {
                    styleRunWithDefaultStyle(fragment, defaultControlStyles);
                    StyleUtils.styleRun(fragment,
                            null == data.getParagraphStyle() ? null : data.getParagraphStyle().getDefaultTextStyle());
                    TextRenderPolicy.Helper.renderTextRun(fragment, (TextRenderData)content);
                } else if (content instanceof PictureRenderData) {
                    PictureRenderPolicy.Helper.renderPicture(fragment, (PictureRenderData) content);
                } else if (content instanceof CommentRenderData) {
                    CommentRenderPolicy.Helper.renderComment(fragment, (CommentRenderData) content);
                }
            }

        }

        private static void styleRunWithDefaultStyle(XWPFRun fragment, List<ParagraphStyle> defaultControlStyles) {
            if (null != defaultControlStyles) {
                defaultControlStyles.stream().filter(Objects::nonNull).forEach(style -> {
                    StyleUtils.styleRun(fragment, style.getDefaultTextStyle());
                });
            }
        }

        private static void styleParagraphWithDefaultStyle(XWPFParagraph paragraph,
                List<ParagraphStyle> defaultControlStyles) {
            if (null != defaultControlStyles) {
                defaultControlStyles.stream()
                        .filter(Objects::nonNull)
                        .forEach(style -> StyleUtils.styleParagraph(paragraph, style));
            }
        }
    }

}
