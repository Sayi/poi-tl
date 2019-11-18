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
package com.deepoove.poi.data;

import com.deepoove.poi.data.builder.StyleBuilder;
import com.deepoove.poi.data.style.Style;

/**
 * 文本数据
 * 
 * @author Sayi
 * @version 0.0.3
 *
 */
public class TextRenderData implements RenderData {
    protected Style style;

    /**
     * \n 表示换行，表格内换行参见：https://github.com/Sayi/poi-tl/issues/295
     */
    protected String text;

    public TextRenderData() {}

    public TextRenderData(String text) {
        this.text = text;
    }

    public TextRenderData(String color, String text) {
        this.style = StyleBuilder.newBuilder().buildColor(color).build();
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

}
