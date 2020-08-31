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

import java.io.Serializable;

import com.deepoove.poi.xwpf.NumFormat;

/**
 * @author Sayi
 *
 */
public class NumberingFormat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 1. 2. 3.
     */
    public static final NumberingFormat DECIMAL = new NumberingFormat(NumFormat.DECIMAL, "%1.");
    /**
     * 1) 2) 3)
     */
    public static final NumberingFormat DECIMAL_PARENTHESES = new NumberingFormat(NumFormat.DECIMAL, "%1)");
    /**
     * ● ● ●
     */
    public static final NumberingFormat BULLET = new NumberingFormat(NumFormat.BULLET, "●");
    /**
     * a. b. c.
     */
    public static final NumberingFormat LOWER_LETTER = new NumberingFormat(NumFormat.LOWER_LETTER, "%1.");
    /**
     * i ⅱ ⅲ
     */
    public static final NumberingFormat LOWER_ROMAN = new NumberingFormat(NumFormat.LOWER_ROMAN, "%1.");
    /**
     * A. B. C.
     */
    public static final NumberingFormat UPPER_LETTER = new NumberingFormat(NumFormat.UPPER_LETTER, "%1.");
    /**
     * Ⅰ Ⅱ Ⅲ
     */
    public static final NumberingFormat UPPER_ROMAN = new NumberingFormat(NumFormat.UPPER_ROMAN, "%1.");

    private int numFmt;
    private String lvlText;

    NumberingFormat() {
    }

    public NumberingFormat(NumFormat numFmt, String lvlText) {
        this.numFmt = numFmt.getValue();
        this.lvlText = lvlText;
    }

    public NumberingFormat(int numFmt, String lvlText) {
        this.numFmt = numFmt;
        this.lvlText = lvlText;
    }

    public int getNumFmt() {
        return numFmt;
    }

    public String getLvlText() {
        return lvlText;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lvlText == null) ? 0 : lvlText.hashCode());
        result = prime * result + numFmt;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        NumberingFormat other = (NumberingFormat) obj;
        if (lvlText == null) {
            if (other.lvlText != null) return false;
        } else if (!lvlText.equals(other.lvlText)) return false;
        if (numFmt != other.numFmt) return false;
        return true;
    }

}
