/*
 * Copyright 2014-2019 the original author or authors.
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
package com.deepoove.poi.data.builder;

import com.deepoove.poi.data.style.Style;

/**
 * 样式构建器
 * 
 * @author Sayi
 *
 */
public class StyleBuilder {

    Style style;

    private StyleBuilder() {
        style = new Style();
    }

    public static StyleBuilder newBuilder() {
        return new StyleBuilder();
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

    public Style build() {
        return style;
    }

}
