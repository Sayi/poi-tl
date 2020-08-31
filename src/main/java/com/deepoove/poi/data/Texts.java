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
package com.deepoove.poi.data;

import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.StyleBuilder;

/**
 * @author Sayi
 *
 */
public class Texts implements RenderDataBuilder<TextRenderData> {

    private String text;
    private Style style;
    private String url;

    private Texts() {
    }

    public static Texts of(String text) {
        Texts inst = new Texts();
        inst.text = text;
        return inst;
    }

    public Texts style(Style style) {
        this.style = style;
        return this;
    }

    public Texts color(String color) {
        if (null != this.style) {
            style.setColor(color);
        } else {
            this.style = Style.builder().buildColor(color).build();
        }
        return this;
    }

    public Texts bold() {
        if (null != this.style) {
            style.setBold(true);
        } else {
            this.style = Style.builder().buildBold().build();
        }
        return this;
    }

    public Texts sup() {
        if (null != this.style) {
            style.setVertAlign("superscript");;
        } else {
            this.style = Style.builder().buildSuper().build();
        }
        return this;
    }

    public Texts link(String url) {
        this.url = url;
        // 链接默认蓝色加下划线
        if (null == this.style) {
            this.style = Style.builder().buildColor("0000FF").buildUnderLine().build();
        }
        return this;
    }

    public Texts mailto(String address, String subject) {
        StringBuilder sb = new StringBuilder(128);
        sb.append("mailto:").append(address).append("?subject=").append(subject);
        return link(sb.toString());
    }

    public Texts anchor(String anchorName) {
        StringBuilder sb = new StringBuilder(32);
        sb.append("anchor:").append(anchorName);
        return link(sb.toString());
    }

    @Override
    public TextRenderData create() {
        TextRenderData data = null;
        if (null != url) {
            data = new HyperLinkTextRenderData(text, url);
            data.setStyle(style);
        } else {
            data = new TextRenderData(text, style);
        }
        return data;
    }

}
