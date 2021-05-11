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

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;

import com.deepoove.poi.xwpf.XWPFHighlightColor;

/**
 * Text Style
 * 
 * @author Sayi
 *
 */
public class Style implements Serializable {

    private static final long serialVersionUID = 1L;

    private String color;
    private String fontFamily; // east Asia font
    private String westernFontFamily; // western font
    private double fontSize;
    private Boolean isBold;
    private Boolean isItalic;
    private Boolean isStrike;
    private UnderlinePatterns underlinePatterns;
    private String underlineColor;
    private XWPFHighlightColor highlightColor;

    /**
     * point unit(pt)
     */
    private int characterSpacing;

    /**
     * baseline, superscript, subscript
     */
    private String vertAlign;

    public Style() {
    }

    public static StyleBuilder builder() {
        return new StyleBuilder();
    }

    public Style(String color) {
        this.color = color;
    }

    public Style(String fontFamily, double fontSize) {
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public Boolean isBold() {
        return isBold;
    }

    public void setBold(Boolean isBold) {
        this.isBold = isBold;
    }

    public Boolean isItalic() {
        return isItalic;
    }

    public void setItalic(Boolean isItalic) {
        this.isItalic = isItalic;
    }

    public Boolean isStrike() {
        return isStrike;
    }

    public void setStrike(Boolean isStrike) {
        this.isStrike = isStrike;
    }

    public UnderlinePatterns getUnderlinePatterns() {
        return underlinePatterns;
    }

    public void setUnderlinePatterns(UnderlinePatterns underlinePatterns) {
        this.underlinePatterns = underlinePatterns;
    }

    public String getUnderlineColor() {
        return underlineColor;
    }

    public void setUnderlineColor(String underlineColor) {
        this.underlineColor = underlineColor;
    }

    public XWPFHighlightColor getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(XWPFHighlightColor highlightColor) {
        this.highlightColor = highlightColor;
    }

    public int getCharacterSpacing() {
        return characterSpacing;
    }

    public void setCharacterSpacing(int characterSpacing) {
        this.characterSpacing = characterSpacing;
    }

    public String getVertAlign() {
        return vertAlign;
    }

    public void setVertAlign(String vertAlign) {
        this.vertAlign = vertAlign;
    }

    public String getWesternFontFamily() {
        return westernFontFamily;
    }

    public void setWesternFontFamily(String westernFontFamily) {
        this.westernFontFamily = westernFontFamily;
    }

    public static final class StyleBuilder {

        private Style style;

        private StyleBuilder() {
            style = new Style();
        }

        public StyleBuilder buildColor(String color) {
            style.setColor(color);
            return this;
        }

        public StyleBuilder buildFontFamily(String fontFamily) {
            style.setFontFamily(fontFamily);
            return this;
        }

        public StyleBuilder buildWesternFontFamily(String fontFamily) {
            style.setWesternFontFamily(fontFamily);
            return this;
        }

        public StyleBuilder buildFontSize(double fontSize) {
            style.setFontSize(fontSize);
            return this;
        }

        public StyleBuilder buildBold() {
            style.setBold(true);
            return this;
        }

        public StyleBuilder buildItalic() {
            style.setItalic(true);
            return this;
        }

        public StyleBuilder buildStrike() {
            style.setStrike(true);
            return this;
        }

        public StyleBuilder buildUnderlineColor(String color) {
            style.setUnderlineColor(color);
            return this;
        }

        public StyleBuilder buildUnderlinePatterns(UnderlinePatterns pattern) {
            style.setUnderlinePatterns(pattern);
            return this;
        }

        public StyleBuilder buildSuper() {
            style.setVertAlign("superscript");
            return this;
        }

        public StyleBuilder buildSub() {
            style.setVertAlign("subscript");
            return this;
        }

        public Style build() {
            return style;
        }

    }

}
