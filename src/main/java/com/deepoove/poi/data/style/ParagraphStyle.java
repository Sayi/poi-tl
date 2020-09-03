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
package com.deepoove.poi.data.style;

import java.io.Serializable;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 * @author Sayi
 */
public class ParagraphStyle implements Serializable {
    private static final long serialVersionUID = 1L;
    private double indentLeftChars;
    private double indentRightChars;
    private double indentHangingChars;

    private ParagraphAlignment align;

    /**
     * Example: 1.5 spacing
     */
    private double spacing;

    // TODO ADD numbering style Class
    private Style glyphStyle;
    private long numId = -1;
    private long lvl = -1;

    private Style defaultTextStyle;

    private ParagraphStyle(Builder builder) {
        this.indentLeftChars = builder.indentLeftChars;
        this.indentRightChars = builder.indentRightChars;
        this.indentHangingChars = builder.indentHangingChars;
        this.align = builder.align;
        this.spacing = builder.spacing;
        this.glyphStyle = builder.glyphStyle;
        this.numId = builder.numId;
        this.lvl = builder.lvl;
        this.defaultTextStyle = builder.defaultTextStyle;
    }

    public ParagraphStyle() {
    }

    public double getIndentLeftChars() {
        return indentLeftChars;
    }

    public void setIndentLeftChars(double indentLeftChars) {
        this.indentLeftChars = indentLeftChars;
    }

    public double getIndentRightChars() {
        return indentRightChars;
    }

    public void setIndentRightChars(double indentRightChars) {
        this.indentRightChars = indentRightChars;
    }

    public double getIndentHangingChars() {
        return indentHangingChars;
    }

    public void setIndentHangingChars(double indentHangingChars) {
        this.indentHangingChars = indentHangingChars;
    }

    public ParagraphAlignment getAlign() {
        return align;
    }

    public void setAlign(ParagraphAlignment align) {
        this.align = align;
    }

    public double getSpacing() {
        return spacing;
    }

    public void setSpacing(double spacing) {
        this.spacing = spacing;
    }

    public Style getGlyphStyle() {
        return glyphStyle;
    }

    public void setGlyphStyle(Style glyphStyle) {
        this.glyphStyle = glyphStyle;
    }

    public Style getDefaultTextStyle() {
        return defaultTextStyle;
    }

    public void setDefaultTextStyle(Style defaultTextStyle) {
        this.defaultTextStyle = defaultTextStyle;
    }

    public long getNumId() {
        return numId;
    }

    public void setNumId(long numId) {
        this.numId = numId;
    }

    public long getLvl() {
        return lvl;
    }

    public void setLvl(long lvl) {
        this.lvl = lvl;
    }

    /**
     * Creates builder to build {@link ParagraphStyle}.
     * 
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link ParagraphStyle}.
     */
    public static final class Builder {
        private double indentLeftChars;
        private double indentRightChars;
        private double indentHangingChars;
        private ParagraphAlignment align;
        private double spacing;
        private Style glyphStyle;
        private long numId = -1;
        private long lvl = -1;
        private Style defaultTextStyle;

        private Builder() {
        }

        public Builder withIndentLeftChars(double indentLeftChars) {
            this.indentLeftChars = indentLeftChars;
            return this;
        }

        public Builder withIndentRightChars(double indentRightChars) {
            this.indentRightChars = indentRightChars;
            return this;
        }

        public Builder withIndentHangingChars(double indentHangingChars) {
            this.indentHangingChars = indentHangingChars;
            return this;
        }

        public Builder withAlign(ParagraphAlignment align) {
            this.align = align;
            return this;
        }

        public Builder withSpacing(double spacing) {
            this.spacing = spacing;
            return this;
        }

        public Builder withGlyphStyle(Style glyphStyle) {
            this.glyphStyle = glyphStyle;
            return this;
        }

        public Builder withNumId(long numId) {
            this.numId = numId;
            return this;
        }

        public Builder withLvl(long lvl) {
            this.lvl = lvl;
            return this;
        }

        public Builder withDefaultTextStyle(Style defaultTextStyle) {
            this.defaultTextStyle = defaultTextStyle;
            return this;
        }

        public ParagraphStyle build() {
            return new ParagraphStyle(this);
        }
    }

}
