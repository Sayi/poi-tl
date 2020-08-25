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

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.style.Style;

public class Numberings implements RenderDataBuilder<NumbericRenderData> {

    private NumbericRenderData data;

    public static Numberings ofBullet() {
        return of(NumberingFormat.BULLET);
    }

    public static Numberings ofDecimal() {
        return of(NumberingFormat.DECIMAL);
    }

    public static Numberings ofDecimalParentheses() {
        return of(NumberingFormat.DECIMAL_PARENTHESES);
    }

    public static Numberings of(NumberingFormat format) {
        Numberings inst = new Numberings();
        inst.data = new NumbericRenderData(format, new ArrayList<RenderData>());
        return inst;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Numberings addItem(TextRenderData item) {
        ((List) data.getItems()).add(item);
        return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Numberings addItem(PictureRenderData item) {
        ((List) data.getItems()).add(item);
        return this;
    }

    public Numberings addItem(String text) {
        this.addItem(Texts.of(text).create());
        return this;
    }

    public Numberings style(Style fmtStyle) {
        data.setStyle(fmtStyle);
        return this;
    }

    @Override
    public NumbericRenderData create() {
        return data;
    }

}
