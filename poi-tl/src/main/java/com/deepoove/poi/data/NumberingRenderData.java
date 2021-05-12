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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sayi
 */
public class NumberingRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    /**
     * format for numbering
     */
    private List<NumberingFormat> multiFormats = new ArrayList<>();

    /**
     * each item in numbering
     */
    private List<NumberingItemRenderData> items = new ArrayList<>();

    public NumberingRenderData() {
    }

    public NumberingRenderData(List<ParagraphRenderData> items) {
        this(NumberingFormat.BULLET, items);
    }

    public NumberingRenderData(NumberingFormat format, List<ParagraphRenderData> items) {
        this.multiFormats.add(format);
        if (null != items) {
            items.forEach(item -> this.items.add(new NumberingItemRenderData(0, item)));
        }
    }

    public NumberingRenderData(TextRenderData... text) {
        this(NumberingFormat.BULLET, text);
    }

    public NumberingRenderData(NumberingFormat format, TextRenderData... text) {
        this.multiFormats.add(format);
        if (null != text) {
            this.items = Arrays.stream(text).map(data -> {
                return new NumberingItemRenderData(0, Paragraphs.of(data).create());
            }).collect(Collectors.toList());
        }
    }

    @Deprecated
    public static NumberingRenderData build(String... text) {
        if (null != text) {
            return new NumberingRenderData(Arrays.stream(text).map(TextRenderData::new).map(data -> {
                return Paragraphs.of(data).create();
            }).collect(Collectors.toList()));
        }
        return new NumberingRenderData(NumberingFormat.BULLET, (TextRenderData) null);
    }

    public static NumberingRenderData build(TextRenderData... text) {
        return new NumberingRenderData(NumberingFormat.BULLET, text);
    }

    public List<NumberingFormat> getFormats() {
        return multiFormats;
    }

    public void setFormats(List<NumberingFormat> formats) {
        this.multiFormats = formats;
    }

    public List<NumberingItemRenderData> getItems() {
        return items;
    }

    public void setItems(List<NumberingItemRenderData> items) {
        this.items = items;
    }

}
