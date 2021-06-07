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
package com.deepoove.poi.data.style;

import java.io.Serializable;

import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import com.deepoove.poi.xwpf.XWPFShadingPattern;

/**
 * @author Sayi
 */
public class ParagraphStyle implements Serializable {
    private static final long serialVersionUID = 1L;
    private String styleId;
    private ParagraphAlignment align; // 对齐

    private Double indentLeftChars; // 缩进-左侧
    private Double indentRightChars;// 缩进-右侧
    private Double indentHangingChars;// 缩进-悬挂
    private Double indentFirstLineChars;// 缩进-首行

    private BorderStyle leftBorder; // 边框-左
    private BorderStyle rightBorder;// 边框-右
    private BorderStyle topBorder;// 边框-上
    private BorderStyle bottomBorder;// 边框-下

    private XWPFShadingPattern shadingPattern; // 底纹样式
    private String backgroundColor; // 底纹颜色

    private Boolean widowControl; // 孤行控制
    private Boolean keepLines; // 段中不分页
    private Boolean keepNext; // 与下段同页
    private Boolean pageBreakBefore; // 段前分页
    private Boolean allowWordBreak; // 单词断行

    private Double spacingBeforeLines; // 间距-段前-行
    private Double spacingAfterLines; // 间距-断后-行
    private Double spacingBefore; // 间距-段前-磅
    private Double spacingAfter; // 间距-断后-磅
    /**
     * Example: 1.5 spacing
     */
    private Double spacing;
    private LineSpacingRule spacingRule;

    // TODO ADD numbering style Class
    private Style glyphStyle;
    private Long numId;
    private Long lvl;

    private Style defaultTextStyle;

    private ParagraphStyle(Builder builder) {
        this.styleId = builder.styleId;
        this.align = builder.align;
        this.indentLeftChars = builder.indentLeftChars;
        this.indentRightChars = builder.indentRightChars;
        this.indentHangingChars = builder.indentHangingChars;
        this.indentFirstLineChars = builder.indentFirstLineChars;
        this.leftBorder = builder.leftBorder;
        this.rightBorder = builder.rightBorder;
        this.topBorder = builder.topBorder;
        this.bottomBorder = builder.bottomBorder;
        this.shadingPattern = builder.shadingPattern;
        this.backgroundColor = builder.backgroundColor;
        this.widowControl = builder.widowControl;
        this.keepLines = builder.keepLines;
        this.keepNext = builder.keepNext;
        this.pageBreakBefore = builder.pageBreakBefore;
        this.allowWordBreak = builder.allowWordBreak;
        this.spacingBeforeLines = builder.spacingBeforeLines;
        this.spacingAfterLines = builder.spacingAfterLines;
        this.spacingBefore = builder.spacingBefore;
        this.spacingAfter = builder.spacingAfter;
        this.spacing = builder.spacing;
        this.spacingRule = builder.spacingRule;
        this.glyphStyle = builder.glyphStyle;
        this.numId = builder.numId;
        this.lvl = builder.lvl;
        this.defaultTextStyle = builder.defaultTextStyle;
    }

    public ParagraphStyle() {
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public ParagraphAlignment getAlign() {
        return align;
    }

    public void setAlign(ParagraphAlignment align) {
        this.align = align;
    }

    public Double getIndentLeftChars() {
        return indentLeftChars;
    }

    public void setIndentLeftChars(Double indentLeftChars) {
        this.indentLeftChars = indentLeftChars;
    }

    public Double getIndentRightChars() {
        return indentRightChars;
    }

    public void setIndentRightChars(Double indentRightChars) {
        this.indentRightChars = indentRightChars;
    }

    public Double getIndentHangingChars() {
        return indentHangingChars;
    }

    public void setIndentHangingChars(Double indentHangingChars) {
        this.indentHangingChars = indentHangingChars;
    }

    public Double getIndentFirstLineChars() {
        return indentFirstLineChars;
    }

    public void setIndentFirstLineChars(Double indentFirstLineChars) {
        this.indentFirstLineChars = indentFirstLineChars;
    }

    public BorderStyle getLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(BorderStyle leftBorder) {
        this.leftBorder = leftBorder;
    }

    public BorderStyle getRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(BorderStyle rightBorder) {
        this.rightBorder = rightBorder;
    }

    public BorderStyle getTopBorder() {
        return topBorder;
    }

    public void setTopBorder(BorderStyle topBorder) {
        this.topBorder = topBorder;
    }

    public BorderStyle getBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(BorderStyle bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public XWPFShadingPattern getShadingPattern() {
        return shadingPattern;
    }

    public void setShadingPattern(XWPFShadingPattern shadingPattern) {
        this.shadingPattern = shadingPattern;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Boolean getWidowControl() {
        return widowControl;
    }

    public void setWidowControl(Boolean widowControl) {
        this.widowControl = widowControl;
    }

    public Boolean getKeepLines() {
        return keepLines;
    }

    public void setKeepLines(Boolean keepLines) {
        this.keepLines = keepLines;
    }

    public Boolean getKeepNext() {
        return keepNext;
    }

    public void setKeepNext(Boolean keepNext) {
        this.keepNext = keepNext;
    }

    public Boolean getPageBreakBefore() {
        return pageBreakBefore;
    }

    public void setPageBreakBefore(Boolean pageBreakBefore) {
        this.pageBreakBefore = pageBreakBefore;
    }

    public Boolean getAllowWordBreak() {
        return allowWordBreak;
    }

    public void setAllowWordBreak(Boolean allowWordBreak) {
        this.allowWordBreak = allowWordBreak;
    }

    public Double getSpacingBeforeLines() {
        return spacingBeforeLines;
    }

    public void setSpacingBeforeLines(Double spacingBeforeLines) {
        this.spacingBeforeLines = spacingBeforeLines;
    }

    public Double getSpacingAfterLines() {
        return spacingAfterLines;
    }

    public void setSpacingAfterLines(Double spacingAfterLines) {
        this.spacingAfterLines = spacingAfterLines;
    }

    public Double getSpacingBefore() {
        return spacingBefore;
    }

    public void setSpacingBefore(Double spacingBefore) {
        this.spacingBefore = spacingBefore;
    }

    public Double getSpacingAfter() {
        return spacingAfter;
    }

    public void setSpacingAfter(Double spacingAfter) {
        this.spacingAfter = spacingAfter;
    }

    public Double getSpacing() {
        return spacing;
    }

    public void setSpacing(Double spacing) {
        this.spacing = spacing;
    }

    public LineSpacingRule getSpacingRule() {
        return spacingRule;
    }

    public void setSpacingRule(LineSpacingRule spacingRule) {
        this.spacingRule = spacingRule;
    }

    public Style getGlyphStyle() {
        return glyphStyle;
    }

    public void setGlyphStyle(Style glyphStyle) {
        this.glyphStyle = glyphStyle;
    }

    public Long getNumId() {
        return numId;
    }

    public void setNumId(Long numId) {
        this.numId = numId;
    }

    public Long getLvl() {
        return lvl;
    }

    public void setLvl(Long lvl) {
        this.lvl = lvl;
    }

    public Style getDefaultTextStyle() {
        return defaultTextStyle;
    }

    public void setDefaultTextStyle(Style defaultTextStyle) {
        this.defaultTextStyle = defaultTextStyle;
    }

    /**
     * Creates builder to build {@link ParagraphStyle}.
     * 
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String styleId;
        private ParagraphAlignment align;
        private Double indentLeftChars;
        private Double indentRightChars;
        private Double indentHangingChars;
        private Double indentFirstLineChars;
        private BorderStyle leftBorder;
        private BorderStyle rightBorder;
        private BorderStyle topBorder;
        private BorderStyle bottomBorder;
        private XWPFShadingPattern shadingPattern;
        private String backgroundColor;
        private Boolean widowControl;
        private Boolean keepLines;
        private Boolean keepNext;
        private Boolean pageBreakBefore;
        private Boolean allowWordBreak;
        private Double spacingBeforeLines;
        private Double spacingAfterLines;
        private Double spacingBefore;
        private Double spacingAfter;
        private Double spacing;
        private LineSpacingRule spacingRule;
        private Style glyphStyle;
        private Long numId;
        private Long lvl;
        private Style defaultTextStyle;

        private Builder() {
        }

        public Builder withStyleId(String styleId) {
            this.styleId = styleId;
            return this;
        }

        public Builder withAlign(ParagraphAlignment align) {
            this.align = align;
            return this;
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

        public Builder withIndentFirstLineChars(double indentFirstLineChars) {
            this.indentFirstLineChars = indentFirstLineChars;
            return this;
        }

        public Builder withLeftBorder(BorderStyle leftBorder) {
            this.leftBorder = leftBorder;
            return this;
        }

        public Builder withRightBorder(BorderStyle rightBorder) {
            this.rightBorder = rightBorder;
            return this;
        }

        public Builder withTopBorder(BorderStyle topBorder) {
            this.topBorder = topBorder;
            return this;
        }

        public Builder withBottomBorder(BorderStyle bottomBorder) {
            this.bottomBorder = bottomBorder;
            return this;
        }

        public Builder withShadingPattern(XWPFShadingPattern shadingPattern) {
            this.shadingPattern = shadingPattern;
            return this;
        }

        public Builder withBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder withWidowControl(Boolean widowControl) {
            this.widowControl = widowControl;
            return this;
        }

        public Builder withKeepLines(Boolean keepLines) {
            this.keepLines = keepLines;
            return this;
        }

        public Builder withKeepNext(Boolean keepNext) {
            this.keepNext = keepNext;
            return this;
        }

        public Builder withPageBreakBefore(Boolean pageBreakBefore) {
            this.pageBreakBefore = pageBreakBefore;
            return this;
        }

        public Builder withAllowWordBreak(Boolean allowWordBreak) {
            this.allowWordBreak = allowWordBreak;
            return this;
        }

        public Builder withSpacingBeforeLines(double spacingBeforeLines) {
            this.spacingBeforeLines = spacingBeforeLines;
            return this;
        }

        public Builder withSpacingAfterLines(double spacingAfterLines) {
            this.spacingAfterLines = spacingAfterLines;
            return this;
        }

        public Builder withSpacingBefore(double spacingBefore) {
            this.spacingBefore = spacingBefore;
            return this;
        }

        public Builder withSpacingAfter(double spacingAfter) {
            this.spacingAfter = spacingAfter;
            return this;
        }

        public Builder withSpacing(double spacing) {
            this.spacing = spacing;
            return this;
        }

        public Builder withSpacingRule(LineSpacingRule spacingRule) {
            this.spacingRule = spacingRule;
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
