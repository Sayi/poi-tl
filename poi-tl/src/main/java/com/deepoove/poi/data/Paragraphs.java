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
package com.deepoove.poi.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.comment.CommentRenderData;
import com.deepoove.poi.xwpf.XWPFShadingPattern;

/**
 * Factory to create {@link ParagraphRenderData}
 * 
 * @author Sayi
 */
public class Paragraphs {

    private Paragraphs() {
    }

    public static ParagraphBuilder of() {
        return new ParagraphBuilder();
    }

    public static ParagraphBuilder of(String text) {
        return of().addText(text);
    }

    public static ParagraphBuilder of(TextRenderData text) {
        return of().addText(text);
    }

    public static ParagraphBuilder of(PictureRenderData picture) {
        return of().addPicture(picture);
    }

    public static ParagraphBuilder of(CommentRenderData comment) {
        return of().addComment(comment);
    }

    /**
     * Builder to build {@link ParagraphRenderData}
     */
    public static class ParagraphBuilder implements RenderDataBuilder<ParagraphRenderData> {

        private List<RenderData> contents = new ArrayList<>();
        private ParagraphStyle paragraphStyle;

        private ParagraphBuilder() {
        }

        public ParagraphBuilder addText(TextRenderData text) {
            contents.add(text);
            return this;
        }

        public ParagraphBuilder addTexts(List<TextRenderData> texts) {
            texts.forEach(this::addText);
            return this;
        }

        public ParagraphBuilder addText(String text) {
            contents.add(Texts.of(text).create());
            return this;
        }

        public ParagraphBuilder addPicture(PictureRenderData picture) {
            contents.add(picture);
            return this;
        }

        public ParagraphBuilder addParagraph(ParagraphRenderData paragraph) {
            contents.addAll(paragraph.getContents());
            return this;
        }

        public ParagraphBuilder addComment(CommentRenderData comment) {
            contents.add(comment);
            return this;
        }

        public ParagraphBuilder addList(List<RenderData> datas) {
            contents.addAll(datas);
            return this;
        }

        public ParagraphBuilder paraStyle(ParagraphStyle style) {
            this.paragraphStyle = style;
            return this;
        }

        public ParagraphBuilder glyphStyle(Style style) {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withGlyphStyle(style).build();
            } else {
                this.paragraphStyle.setGlyphStyle(style);
            }
            return this;
        }

        public ParagraphBuilder defaultTextStyle(Style style) {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withDefaultTextStyle(style).build();
            } else {
                this.paragraphStyle.setDefaultTextStyle(style);
            }
            return this;
        }

        public ParagraphBuilder left() {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withAlign(ParagraphAlignment.LEFT).build();
            } else {
                this.paragraphStyle.setAlign(ParagraphAlignment.LEFT);
            }
            return this;
        }

        public ParagraphBuilder center() {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withAlign(ParagraphAlignment.CENTER).build();
            } else {
                this.paragraphStyle.setAlign(ParagraphAlignment.CENTER);
            }
            return this;
        }

        public ParagraphBuilder right() {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withAlign(ParagraphAlignment.RIGHT).build();
            } else {
                this.paragraphStyle.setAlign(ParagraphAlignment.RIGHT);
            }
            return this;
        }

        public ParagraphBuilder indentFirstLine(double lineChars) {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withIndentFirstLineChars(lineChars).build();
            } else {
                this.paragraphStyle.setIndentFirstLineChars(lineChars);
            }
            return this;
        }

        public ParagraphBuilder indentLeft(double lineChars) {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withIndentLeftChars(lineChars).build();
            } else {
                this.paragraphStyle.setIndentLeftChars(lineChars);
            }
            return this;
        }

        public ParagraphBuilder borderLeft(BorderStyle borderStyle) {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withLeftBorder(borderStyle).build();
            } else {
                this.paragraphStyle.setLeftBorder(borderStyle);
            }
            return this;
        }

        public ParagraphBuilder spacing(double spacing, LineSpacingRule spacingRule) {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder()
                        .withSpacing(spacing)
                        .withSpacingRule(spacingRule)
                        .build();
            } else {
                this.paragraphStyle.setSpacing(spacing);
                this.paragraphStyle.setSpacingRule(spacingRule);
            }
            return this;
        }

        public ParagraphBuilder bgColor(String backgroundColor) {
            return this.bgColor(backgroundColor, null);
        }

        public ParagraphBuilder bgColor(String backgroundColor, XWPFShadingPattern pattern) {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder()
                        .withBackgroundColor(backgroundColor)
                        .withShadingPattern(pattern)
                        .build();
            } else {
                this.paragraphStyle.setBackgroundColor(backgroundColor);
                this.paragraphStyle.setShadingPattern(pattern);
            }
            return this;
        }

        public ParagraphBuilder styleId(String styleId) {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withStyleId(styleId).build();
            } else {
                this.paragraphStyle.setStyleId(styleId);
            }
            return this;
        }

        public ParagraphBuilder allowWordBreak() {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder().withAllowWordBreak(true).build();
            } else {
                this.paragraphStyle.setAllowWordBreak(true);
            }
            return this;
        }

        public ParagraphBuilder pageControl(Boolean widowControl, Boolean keepNext, Boolean keepLines,
                Boolean pageBreakBefore) {
            if (null == this.paragraphStyle) {
                this.paragraphStyle = ParagraphStyle.builder()
                        .withWidowControl(widowControl)
                        .withKeepNext(keepNext)
                        .withKeepLines(keepLines)
                        .withPageBreakBefore(pageBreakBefore)
                        .build();
            } else {
                this.paragraphStyle.setWidowControl(widowControl);
                this.paragraphStyle.setKeepNext(keepNext);
                this.paragraphStyle.setKeepLines(keepLines);
                this.paragraphStyle.setPageBreakBefore(pageBreakBefore);
            }
            return this;
        }

        @Override
        public ParagraphRenderData create() {
            ParagraphRenderData data = new ParagraphRenderData();
            data.setContents(contents);
            data.setParagraphStyle(paragraphStyle);
            return data;
        }

    }

}
