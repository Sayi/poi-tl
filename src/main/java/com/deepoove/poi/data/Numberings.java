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

public class Numberings implements RenderDataBuilder<NumberingRenderData> {

    private NumberingRenderData data;

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
        inst.data = new NumberingRenderData(format, new ArrayList<>());
        return inst;
    }

    public Numberings addItem(ParagraphRenderData item) {
        data.getItems().add(item);
        return this;
    }

    public Numberings addItem(TextRenderData item) {
        data.getItems().add(Paragraphs.of().addText(item).create());
        return this;
    }

    public Numberings addItem(PictureRenderData item) {
        data.getItems().add(Paragraphs.of().addPicture(item).create());
        return this;
    }

    public Numberings addItem(String text) {
        this.addItem(Texts.of(text).create());
        return this;
    }

    @Override
    public NumberingRenderData create() {
        return data;
    }

}
