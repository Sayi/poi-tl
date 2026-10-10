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
package com.deepoove.poi.plugin.math;

import java.io.Serializable;
import java.util.Locale;

/**
 * Character formatting of a formula.
 * <p>
 * Every field defaults to {@code null}, which means "inherit from the
 * placeholder run". Only explicitly set fields override the inherited
 * formatting, so a formula follows the font and size of its paragraph by
 * default.
 *
 * @author Sayi
 */
public class FormulaStyle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Convenience value for {@link #setMathFont(String)}: Word's own math font.
     */
    public static final String DEFAULT_MATH_FONT = "Cambria Math";

    /**
     * Word half-points ({@code w:sz}); null inherits.
     */
    private Integer fontSize;

    /**
     * Six digit RGB such as {@code C00000}; null inherits.
     */
    private String color;

    /**
     * null inherits.
     */
    private Boolean bold;

    /**
     * null inherits; only meaningful for upright content such as {@code \text{}}.
     */
    private Boolean italic;

    /**
     * Explicit {@code w:rFonts} for the formula; null keeps Word's math font, which
     * is what you normally want.
     */
    private String mathFont;

    private FormulaAlign align = FormulaAlign.CENTER;

    public Integer getFontSize() {
        return fontSize;
    }

    public FormulaStyle setFontSize(Integer fontSize) {
        if (null != fontSize && fontSize <= 0) {
            throw new IllegalArgumentException("fontSize must be a positive half-point value, but was " + fontSize);
        }
        this.fontSize = fontSize;
        return this;
    }

    public String getColor() {
        return color;
    }

    public FormulaStyle setColor(String color) {
        this.color = checkColor(color);
        return this;
    }

    public Boolean getBold() {
        return bold;
    }

    public FormulaStyle setBold(Boolean bold) {
        this.bold = bold;
        return this;
    }

    public Boolean getItalic() {
        return italic;
    }

    public FormulaStyle setItalic(Boolean italic) {
        this.italic = italic;
        return this;
    }

    public String getMathFont() {
        return mathFont;
    }

    public FormulaStyle setMathFont(String mathFont) {
        this.mathFont = null == mathFont || mathFont.isEmpty() ? null : mathFont;
        return this;
    }

    public FormulaAlign getAlign() {
        return align;
    }

    public FormulaStyle setAlign(FormulaAlign align) {
        this.align = null == align ? FormulaAlign.CENTER : align;
        return this;
    }

    /**
     * @param color six digit RGB, or null
     * @return the normalized upper case color, or null
     */
    static String checkColor(String color) {
        if (null == color || color.isEmpty()) return null;
        String value = color.startsWith("#") ? color.substring(1) : color;
        if (!value.matches("[0-9A-Fa-f]{6}")) {
            throw new IllegalArgumentException(
                    "Color must be a six digit hex RGB value such as C00000, but was '" + color + "'");
        }
        return value.toUpperCase(Locale.ROOT);
    }

}
