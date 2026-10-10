/*
 * Copyright 2014-2026 Sayi
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
package com.deepoove.poi.config;

/**
 * Built-in template syntax
 * <p>
 * Each constant holds the sign char that prefixes a tag, for example
 * {@code {{@name}}} for a picture and {@code {{name}}} for text.
 * </p>
 * 
 * @author Sayi
 */
public enum GrammarSymbol {

    /**
     * Picture in the template
     */
    IMAGE('@'),

    /**
     * Text in the template
     */
    TEXT('\0'),

    /**
     * Text in the template, Text alias, compatible with #this, and not conflicting
     * with existing table writing: {{=#this}}
     */
    TEXT_ALIAS('='),

    /**
     * Table in the template
     */
    TABLE('#'),

    /**
     * Numbering in the template
     */
    NUMBERING('*'),

    /**
     * Nested/Merge/Include/Reference in the template
     */
    DOCX_TEMPLATE('+'),

    /**
     * Block(if & for each) start
     */
    ITERABLE_START('?'),

    /**
     * Block end
     */
    BLOCK_END('/');

    private char symbol;

    private GrammarSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Get the sign char of the grammar symbol
     * 
     * @return sign char of the grammar symbol
     */
    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(this.symbol);
    }

}
