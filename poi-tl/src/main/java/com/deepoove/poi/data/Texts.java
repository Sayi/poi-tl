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

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;

import com.deepoove.poi.data.style.Style;

/**
 * Factory method to create {@link TextRenderData}
 * 
 * @author Sayi
 *
 */
public final class Texts {

    private Texts() {
    }

    public static TextBuilder of(String text) {
        return new TextBuilder(text);
    }

    /**
     * Builder to build {@link TextRenderData}
     *
     */
    public static class TextBuilder implements RenderDataBuilder<TextRenderData> {
        private String text;
        private Style style;
        private String url;
        private String bookmark;

        private TextBuilder(String text) {
            this.text = text;
        }

        public TextBuilder style(Style style) {
            this.style = style;
            return this;
        }

        public TextBuilder color(String color) {
            if (null != this.style) {
                style.setColor(color);
            } else {
                this.style = Style.builder().buildColor(color).build();
            }
            return this;
        }

        public TextBuilder bold() {
            if (null != this.style) {
                style.setBold(true);
            } else {
                this.style = Style.builder().buildBold().build();
            }
            return this;
        }

        public TextBuilder italic() {
            if (null != this.style) {
                style.setItalic(true);
            } else {
                this.style = Style.builder().buildItalic().build();
            }
            return this;
        }

        public TextBuilder sup() {
            if (null != this.style) {
                style.setVertAlign("superscript");
            } else {
                this.style = Style.builder().buildSuper().build();
            }
            return this;
        }

        public TextBuilder sub() {
            if (null != this.style) {
                style.setVertAlign("subscript");
            } else {
                this.style = Style.builder().buildSub().build();
            }
            return this;
        }

        public TextBuilder fontSize(double fontSize) {
            if (null != this.style) {
                style.setFontSize(fontSize);
            } else {
                this.style = Style.builder().buildFontSize(fontSize).build();
            }
            return this;
        }

        public TextBuilder fontFamily(String fontFamily) {
            if (null != this.style) {
                style.setFontFamily(fontFamily);
            } else {
                this.style = Style.builder().buildFontFamily(fontFamily).build();
            }
            return this;
        }

        public TextBuilder link(String url) {
            this.url = url;
            // default blue color and underline
            if (null == this.style) {
                this.style = Style.builder().buildColor("0000FF").buildUnderlinePatterns(UnderlinePatterns.SINGLE)
                        .build();
            }
            return this;
        }

        public TextBuilder bookmark(String name) {
            this.bookmark = name;
            return this;
        }

        public TextBuilder mailto(String address, String subject) {
            StringBuilder sb = new StringBuilder(128);
            sb.append("mailto:").append(address).append("?subject=").append(subject);
            return link(sb.toString());
        }

        public TextBuilder anchor(String anchorName) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("anchor:").append(anchorName);
            return link(sb.toString());
        }

        @Override
        public TextRenderData create() {
            TextRenderData data = null;
            if (null != url) {
                data = new HyperlinkTextRenderData(text, url);
            } else if (null != bookmark) {
                data = new BookmarkTextRenderData(text, bookmark);
            } else {
                data = new TextRenderData(text);
            }
            data.setStyle(style);
            return data;
        }
    }

}
