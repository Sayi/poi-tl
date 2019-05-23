/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.data.style;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;

/**
 * 样式
 * 
 * @author Sayi
 * @version 0.0.3
 *
 */
public class Style {

    /**
     * 文字颜色
     */
    private String color;
    /**
     * 字体
     */
    private String fontFamily;
    /**
     * 字体大小
     */
    private int fontSize;
    /**
     * 粗体
     */
    private Boolean isBold;
    /**
     * 斜体
     */
    private Boolean isItalic;
    /**
     * 删除线
     */
    private Boolean isStrike;
    /**
     * 下划线
     */
    private Boolean isUnderLine;

    /**
     * 文本背景突出显示颜色
     */
    private STHighlightColor.Enum highlightColor;

    public Style() {}

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

}
