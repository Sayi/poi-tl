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
package com.deepoove.poi.plugin.math.generator;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

import com.deepoove.poi.plugin.math.FormulaAlign;
import com.deepoove.poi.plugin.math.FormulaStyle;

/**
 * The character formatting applied to every run of a formula: the placeholder
 * run's {@code w:rPr} merged with the explicit {@link FormulaStyle}.
 * <p>
 * Mutating a fresh {@code w:rPr} on the target run is deliberate: it avoids
 * detached XmlBeans objects and keeps the whole style application local.
 *
 * @author Sayi
 */
final class OmmlStyle {

    private final CTRPr inherited;

    private final FormulaStyle style;

    private OmmlStyle(CTRPr inherited, FormulaStyle style) {
        this.inherited = inherited;
        this.style = style;
    }

    /**
     * @param inherited detached copy of the placeholder run's {@code w:rPr}, or null
     * @param style     explicit overrides, or null
     */
    static OmmlStyle of(CTRPr inherited, FormulaStyle style) {
        return new OmmlStyle(inherited, style);
    }

    private void applyMathFont(CTRPr target) {
        String mathFont = null == style || null == style.getMathFont() ? FormulaStyle.DEFAULT_MATH_FONT
                : style.getMathFont();
        CTFonts fonts = target.sizeOfRFontsArray() > 0 ? target.getRFontsArray(0) : target.addNewRFonts();
        fonts.setAscii(mathFont);
        fonts.setHAnsi(mathFont);
        fonts.setCs(mathFont);
    }

    /**
     * @return true when writing a {@code m:ctrlPr} would actually carry something.
     *         A math run always gets one, because it always carries the math font;
     *         a control property is optional and Microsoft's own MathML converter
     *         never writes one.
     */
    boolean hasRpr() {
        return null != inherited || hasCharacterFormatting();
    }

    private boolean hasCharacterFormatting() {
        if (null == style) return false;
        return null != style.getFontSize() || null != style.getColor() || null != style.getBold()
                || null != style.getItalic() || null != style.getMathFont();
    }

    FormulaAlign align() {
        return null == style || null == style.getAlign() ? FormulaAlign.CENTER : style.getAlign();
    }

    /**
     * Writes the merged formatting into a freshly created {@code w:rPr}.
     * <p>
     * The math font is always written, and always first. Word writes it on every
     * math run too, and it is not decoration: without it the run inherits the
     * paragraph font, and a body font has no math glyphs - a formula would render
     * in the wrong face, or not at all on older Office. Only the East Asian font
     * stays inherited, so CJK inside {@code \text{}} keeps the body font.
     */
    void applyTo(CTRPr target) {
        if (null != inherited) {
            target.set(inherited);
        }
        applyMathFont(target);
        if (null == style) return;
        Integer fontSize = style.getFontSize();
        if (null != fontSize) {
            target.addNewSz().setVal(fontSize);
            target.addNewSzCs().setVal(fontSize);
        }
        String color = style.getColor();
        if (null != color) {
            target.addNewColor().setVal(color);
        }
        if (Boolean.TRUE.equals(style.getBold())) {
            target.addNewB();
        }
        if (Boolean.TRUE.equals(style.getItalic())) {
            target.addNewI();
        }
    }

}
