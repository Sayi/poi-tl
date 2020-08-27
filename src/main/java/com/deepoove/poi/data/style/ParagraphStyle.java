package com.deepoove.poi.data.style;

import java.io.Serializable;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

public class ParagraphStyle implements Serializable {
    private static final long serialVersionUID = 1L;
    private double indentLeftChars;
    private double indentRightChars;
    private double indentHangingChars;

    private ParagraphAlignment align;

    /**
     * spacing倍行距
     */
    private double spacing;

    private Style glyphStyle;

    public ParagraphStyle() {
    }

    private ParagraphStyle(Builder builder) {
        this.indentLeftChars = builder.indentLeftChars;
        this.indentRightChars = builder.indentRightChars;
        this.indentHangingChars = builder.indentHangingChars;
        this.align = builder.align;
        this.spacing = builder.spacing;
        this.glyphStyle = builder.glyphStyle;
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

        public ParagraphStyle build() {
            return new ParagraphStyle(this);
        }
    }

}
