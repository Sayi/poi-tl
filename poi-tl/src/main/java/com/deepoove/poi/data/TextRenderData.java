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

import com.deepoove.poi.data.style.Style;

/**
 * Basic text template
 * 
 * @author Sayi
 *
 */
public class TextRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    protected Style style;

    /**
     * \n means line break
     */
    protected String text;

    public TextRenderData() {
    }

    public TextRenderData(String text) {
        this.text = text;
    }

    public TextRenderData(String color, String text) {
        this.style = Style.builder().buildColor(color).build();
        this.text = text;
    }

    public TextRenderData(String text, Style style) {
        this.style = style;
        this.text = text;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
