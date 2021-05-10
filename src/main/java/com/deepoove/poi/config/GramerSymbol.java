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
package com.deepoove.poi.config;

/**
 * Built-in template syntax
 * 
 * @author Sayi
 */
public enum GramerSymbol {

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

    private GramerSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(this.symbol);
    }

}
