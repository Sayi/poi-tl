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
package com.deepoove.poi.data.style;

import java.io.Serializable;

import com.deepoove.poi.util.UnitUtils;

/**
 * @author Sayi
 *
 */
public class RowStyle implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * in twips
     * 
     * @see #{@link UnitUtils#cm2Twips()}
     */
    private int height;

    /**
     * atleast、auto、exact
     */
    private String heightRule;

    /**
     * Specifies that the current row should be repeated at the top each new page on
     * which the table is displayed. This can be specified for multiple rows to
     * generate a multi-row header. Note that if the row is not the first row, then
     * the property will be ignored.
     */
    private boolean repeated;

    /**
     * default cell style for all cells in the current row
     */
    private CellStyle defaultCellStyle;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getHeightRule() {
        return heightRule;
    }

    public void setHeightRule(String heightRule) {
        this.heightRule = heightRule;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public CellStyle getDefaultCellStyle() {
        return defaultCellStyle;
    }

    public void setDefaultCellStyle(CellStyle defaultCellStyle) {
        this.defaultCellStyle = defaultCellStyle;
    }

}
