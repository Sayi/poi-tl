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

/**
 * 样式构建器
 * 
 * @author Sayi
 *
 */
public class StyleBuilder {

    Style style;

    StyleBuilder() {
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
