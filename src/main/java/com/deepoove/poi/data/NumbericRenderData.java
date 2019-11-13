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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;

import com.deepoove.poi.data.style.Style;

/**
 * @author Sayi
 * @version 0.0.5
 */
public class NumbericRenderData implements RenderData {

    /**
     * 1. 2. 3.
     */
    public static final Pair<Enum, String> FMT_DECIMAL = Pair.of(STNumberFormat.DECIMAL, "%1.");
    /**
     * 1) 2) 3)
     */
    public static final Pair<Enum, String> FMT_DECIMAL_PARENTHESES = Pair.of(STNumberFormat.DECIMAL,
            "%1)");
    /**
     * ● ● ●
     */
    public static final Pair<Enum, String> FMT_BULLET = Pair.of(STNumberFormat.BULLET, "●");
    /**
     * a. b. c.
     */
    public static final Pair<Enum, String> FMT_LOWER_LETTER = Pair.of(STNumberFormat.LOWER_LETTER,
            "%1.");
    /**
     * i ⅱ ⅲ
     */
    public static final Pair<Enum, String> FMT_LOWER_ROMAN = Pair.of(STNumberFormat.LOWER_ROMAN,
            "%1.");
    /**
     * A. B. C.
     */
    public static final Pair<Enum, String> FMT_UPPER_LETTER = Pair.of(STNumberFormat.UPPER_LETTER,
            "%1.");
    /**
     * Ⅰ Ⅱ Ⅲ
     */
    public static final Pair<Enum, String> FMT_UPPER_ROMAN = Pair.of(STNumberFormat.UPPER_ROMAN,
            "%1.");
    // /**
    // * 一、 二、 三、
    // */
    // public static final Pair<Enum, String> FMT_CHINESE_COUNTING_THOUSAND =
    // Pair
    // .of(STNumberFormat.CHINESE_COUNTING_THOUSAND, "%1、");
    // /**
    // * (一) (二) (三)
    // */
    // public static final Pair<Enum, String>
    // FMT_CHINESE_COUNTING_THOUSAND_PARENTHESES = Pair
    // .of(STNumberFormat.CHINESE_COUNTING_THOUSAND, "(%1)");

    /**
     * 文本、超链接、图片，暂不支持表格等
     */
    private List<? extends RenderData> numbers;

    private Pair<Enum, String> numFmt;

    private Style fmtStyle;

    public NumbericRenderData(Pair<Enum, String> numFmt, List<? extends RenderData> numbers) {
        this(numFmt, null, numbers);
    }

    /**
     * @param numFmt
     *            编号字符
     * @param fmtStyle
     *            编号样式
     * @param numbers
     *            列表内容
     */
    public NumbericRenderData(Pair<Enum, String> numFmt, Style fmtStyle,
            List<? extends RenderData> numbers) {
        this.numFmt = numFmt;
        this.numbers = numbers;
        this.fmtStyle = fmtStyle;
    }

    public NumbericRenderData(List<? extends RenderData> numbers) {
        this(FMT_BULLET, numbers);
    }

    public static NumbericRenderData build(String... text) {
        if (null == text) return null;
        List<TextRenderData> numbers = new ArrayList<TextRenderData>();
        for (String txt : text) {
            numbers.add(new TextRenderData(txt));
        }
        return new NumbericRenderData(numbers);
    }

    public static NumbericRenderData build(RenderData... data) {
        return new NumbericRenderData(null == data ? null : Arrays.asList(data));
    }

    public List<? extends RenderData> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<? extends RenderData> numbers) {
        this.numbers = numbers;
    }

    public Pair<Enum, String> getNumFmt() {
        return numFmt;
    }

    public void setNumFmt(Pair<Enum, String> numFmt) {
        this.numFmt = numFmt;
    }

    public Style getFmtStyle() {
        return fmtStyle;
    }

    public void setFmtStyle(Style fmtStyle) {
        this.fmtStyle = fmtStyle;
    }

}
