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
package com.deepoove.poi.data.style;

import java.io.Serializable;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

/**
 * 表格样式
 * 
 * @author Sayi
 *
 */
public class TableStyle implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 背景色
     */
    private String backgroundColor;

    /**
     * 对齐方式 STJc.LEFT 左对齐 STJc.CENTER 居中对齐 STJc.RIGHT 右对齐
     */
    private STJc.Enum align;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public STJc.Enum getAlign() {
        return align;
    }

    public void setAlign(STJc.Enum align) {
        this.align = align;
    }

}
