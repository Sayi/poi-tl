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
package com.deepoove.poi.config;

/**
 * 默认模板语法
 * 
 * @author Sayi
 */
public enum GramerSymbol {

    /**
     * 图片
     */
    IMAGE('@'),

    /**
     * 文本
     */
    TEXT('\0'),

    /**
     * 表格
     */
    TABLE('#'),

    /**
     * 列表
     */
    NUMBERIC('*'),

    /**
     * word文档模板
     */
    DOCX_TEMPLATE('+');

    private char symbol;

    private GramerSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public String toString() {
        return String.valueOf(this.symbol);
    }

}
