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
    private NumberingFormat format;
    /**
     * each item in numbering
     */
    private List<ParagraphRenderData> items;

    NumberingRenderData() {
    }

    public NumberingRenderData(List<ParagraphRenderData> items) {
        this(NumberingFormat.BULLET, items);
    }

    public NumberingRenderData(NumberingFormat format, List<ParagraphRenderData> items) {
        this.format = format;
        this.items = items;
    }

    public NumberingRenderData(TextRenderData... text) {
        this(NumberingFormat.BULLET, text);
    }

    public NumberingRenderData(NumberingFormat format, TextRenderData... text) {
        this.format = format;
        if (null != text) {
            this.items = Arrays.stream(text).map(data -> {
                return Paragraphs.of(data).create();
            }).collect(Collectors.toList());
        }
    }

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

    public List<ParagraphRenderData> getItems() {
        return items;
    }

    public void setItems(List<ParagraphRenderData> items) {
        this.items = items;
    }

    public NumberingFormat getFormat() {
        return format;
    }

    public void setFormat(NumberingFormat format) {
        this.format = format;
    }

}
