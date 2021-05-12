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

import java.io.Serializable;
import java.text.MessageFormat;

import com.deepoove.poi.xwpf.NumFormat;

/**
 * @author Sayi
 */
public class NumberingFormat implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final NumberingFormat BULLET = new NumberingFormat(NumFormat.BULLET, "●");
    public static final NumberingFormat DECIMAL;
    public static final NumberingFormat DECIMAL_PARENTHESES;
    public static final NumberingFormat LOWER_LETTER;
    public static final NumberingFormat LOWER_ROMAN;
    public static final NumberingFormat UPPER_LETTER;
    public static final NumberingFormat UPPER_ROMAN;

    /**
     * 1. 2. 3.
     */
    public static final Builder DECIMAL_BUILDER;
    /**
     * 1) 2) 3)
     */
    public static final Builder DECIMAL_PARENTHESES_BUILDER;

    /**
     * a. b. c.
     */
    public static final Builder LOWER_LETTER_BUILDER;
    /**
     * i ⅱ ⅲ
     */
    public static final Builder LOWER_ROMAN_BUILDER;
    /**
     * A. B. C.
     */
    public static final Builder UPPER_LETTER_BUILDER;
    /**
     * Ⅰ Ⅱ Ⅲ
     */
    public static final Builder UPPER_ROMAN_BUILDER;

    static {
        DECIMAL_BUILDER = builder("%{0}.").withNumFmt(NumFormat.DECIMAL);
        DECIMAL_PARENTHESES_BUILDER = builder("%{0})").withNumFmt(NumFormat.DECIMAL);

        LOWER_LETTER_BUILDER = builder("%{0}.").withNumFmt(NumFormat.LOWER_LETTER);
        LOWER_ROMAN_BUILDER = builder("%{0}.").withNumFmt(NumFormat.LOWER_ROMAN);
        UPPER_LETTER_BUILDER = builder("%{0}.").withNumFmt(NumFormat.UPPER_LETTER);
        UPPER_ROMAN_BUILDER = builder("%{0}.").withNumFmt(NumFormat.UPPER_ROMAN);

        DECIMAL = DECIMAL_BUILDER.build(0);
        DECIMAL_PARENTHESES = DECIMAL_PARENTHESES_BUILDER.build(0);
        LOWER_LETTER = LOWER_LETTER_BUILDER.build(0);
        LOWER_ROMAN = LOWER_ROMAN_BUILDER.build(0);
        UPPER_LETTER = UPPER_LETTER_BUILDER.build(0);
        UPPER_ROMAN = UPPER_ROMAN_BUILDER.build(0);
    }

    private int numFmt;
    private String lvlText;

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

    public static Builder builder(String lvlTemplate) {
        return new Builder(lvlTemplate);
    }

    public static final class Builder {
        private NumFormat numFmt;
        private String lvlTemplate;

        private Builder(String lvlTemplate) {
            this.lvlTemplate = lvlTemplate;
        }

        public Builder withNumFmt(NumFormat numFmt) {
            this.numFmt = numFmt;
            return this;
        }

        public NumberingFormat build(int level) {
            return new NumberingFormat(this.numFmt, MessageFormat.format(lvlTemplate, ++level));
        }
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

    @Override
    public String toString() {
        return "NumberingFormat [numFmt=" + numFmt + ", lvlText=" + lvlText + "]";
    }

}
