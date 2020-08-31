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

import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;

/**
 * Text Style
 * 
 * @author Sayi
 *
 */
public class Style implements Serializable {

    private static final long serialVersionUID = 1L;

    private String color;
    private String fontFamily;
    private int fontSize;
    private Boolean isBold;
    private Boolean isItalic;
    private Boolean isStrike;
    private Boolean isUnderLine;

    /**
     * text background highlight color
     */
    private STHighlightColor.Enum highlightColor;

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

    public Style(String fontFamily, int fontSize) {
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

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
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

    public Boolean isUnderLine() {
        return isUnderLine;
    }

    public void setUnderLine(Boolean isUnderLine) {
        this.isUnderLine = isUnderLine;
    }

    public STHighlightColor.Enum getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(STHighlightColor.Enum highlightColor) {
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

        public StyleBuilder buildFontSize(int fontSize) {
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

        public StyleBuilder buildUnderLine() {
            style.setUnderLine(true);
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
