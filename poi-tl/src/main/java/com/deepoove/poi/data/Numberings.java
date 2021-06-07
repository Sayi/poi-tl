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

/**
 * Factory to create {@link NumberingRenderData}
 * 
 * @author Sayi
 */
public class Numberings {

    private Numberings() {
    }

    public static NumberingBuilder ofBullet() {
        return of(NumberingFormat.BULLET);
    }

    public static NumberingBuilder ofDecimal() {
        return of(NumberingFormat.DECIMAL);
    }

    public static NumberingBuilder ofDecimal(String... text) {
        NumberingBuilder inst = of(NumberingFormat.DECIMAL);
        if (null != text) {
            Arrays.stream(text).forEach(inst::addItem);
        }
        return inst;
    }

    public static NumberingBuilder ofDecimalParentheses() {
        return of(NumberingFormat.DECIMAL_PARENTHESES);
    }

    public static NumberingBuilder ofDecimalParentheses(String... text) {
        NumberingBuilder inst = of(NumberingFormat.DECIMAL_PARENTHESES);
        if (null != text) {
            Arrays.stream(text).forEach(inst::addItem);
        }
        return inst;
    }

    public static NumberingBuilder of(NumberingFormat format) {
        return new NumberingBuilder(format);
    }

    public static NumberingBuilder of(TextRenderData... text) {
        NumberingBuilder inst = ofBullet();
        if (null != text) {
            Arrays.stream(text).forEach(inst::addItem);
        }
        return inst;
    }

    public static NumberingBuilder of(String... text) {
        NumberingBuilder inst = ofBullet();
        if (null != text) {
            Arrays.stream(text).forEach(inst::addItem);
        }
        return inst;
    }

    public static NumberingRenderData create(String... text) {
        return of(text).create();
    }

    /**
     * Builder to build {@link NumberingRenderData}
     */
    public static class NumberingBuilder implements RenderDataBuilder<NumberingRenderData> {

        private NumberingRenderData data;

        private NumberingBuilder(NumberingFormat format) {
            data = new NumberingRenderData(format, new ArrayList<>());
        }

        public NumberingBuilder addItem(ParagraphRenderData item) {
            data.getItems().add(new NumberingItemRenderData(0, item));
            return this;
        }

        public NumberingBuilder addItem(TextRenderData item) {
            data.getItems().add(new NumberingItemRenderData(0, Paragraphs.of(item).create()));
            return this;
        }

        public NumberingBuilder addItem(PictureRenderData item) {
            data.getItems().add(new NumberingItemRenderData(0, Paragraphs.of(item).create()));
            return this;
        }

        public NumberingBuilder addItem(String text) {
            this.addItem(Texts.of(text).create());
            return this;
        }

        @Override
        public NumberingRenderData create() {
            return data;
        }
    }

}
