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

import org.apache.commons.lang3.tuple.Pair;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;

import com.deepoove.poi.data.style.Style;

/**
 * @author Sayi
 * @version 0.0.5
 */
public class NumbericRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    /**
     * 编号格式
     */
    private NumberingFormat format;
    /**
     * 文本、超链接、图片等
     */
    private List<? extends RenderData> items;

    /**
     * 编号文本样式
     */
    private Style style;

    public NumbericRenderData(NumberingFormat format, List<? extends RenderData> items) {
        this(format, null, items);
    }

    public NumbericRenderData(NumberingFormat format, Style style, List<? extends RenderData> items) {
        this.format = format;
        this.items = items;
        this.style = style;
    }

    public NumbericRenderData(List<? extends RenderData> items) {
        this(NumberingFormat.BULLET, items);
    }

    public static NumbericRenderData build(String... text) {
        if (null == text) return null;
        return new NumbericRenderData(Arrays.stream(text).map(TextRenderData::new).collect(Collectors.toList()));
    }

    @Deprecated
    public NumbericRenderData(Pair<Enum, String> numFmt, List<? extends RenderData> numbers) {
        this(numFmt, null, numbers);
    }

    @Deprecated
    public NumbericRenderData(Pair<Enum, String> numFmt, Style style, List<? extends RenderData> numbers) {
        this.format = new NumberingFormat(numFmt.getLeft(), numFmt.getRight());
        this.items = numbers;
        this.style = style;
    }

    /**
     * 1. 2. 3.
     */
    @Deprecated
    public static final Pair<Enum, String> FMT_DECIMAL = Pair.of(STNumberFormat.DECIMAL, "%1.");
    /**
     * 1) 2) 3)
     */
    @Deprecated
    public static final Pair<Enum, String> FMT_DECIMAL_PARENTHESES = Pair.of(STNumberFormat.DECIMAL, "%1)");
    /**
     * ● ● ●
     */
    @Deprecated
    public static final Pair<Enum, String> FMT_BULLET = Pair.of(STNumberFormat.BULLET, "●");
    /**
     * a. b. c.
     */
    @Deprecated
    public static final Pair<Enum, String> FMT_LOWER_LETTER = Pair.of(STNumberFormat.LOWER_LETTER, "%1.");
    /**
     * i ⅱ ⅲ
     */
    @Deprecated
    public static final Pair<Enum, String> FMT_LOWER_ROMAN = Pair.of(STNumberFormat.LOWER_ROMAN, "%1.");
    /**
     * A. B. C.
     */
    @Deprecated
    public static final Pair<Enum, String> FMT_UPPER_LETTER = Pair.of(STNumberFormat.UPPER_LETTER, "%1.");
    /**
     * Ⅰ Ⅱ Ⅲ
     */
    @Deprecated
    public static final Pair<Enum, String> FMT_UPPER_ROMAN = Pair.of(STNumberFormat.UPPER_ROMAN, "%1.");

    public List<? extends RenderData> getItems() {
        return items;
    }

    public void setItems(List<? extends RenderData> items) {
        this.items = items;
    }

    public NumberingFormat getFormat() {
        return format;
    }

    public void setFormat(NumberingFormat format) {
        this.format = format;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style fmtStyle) {
        this.style = fmtStyle;
    }

}
