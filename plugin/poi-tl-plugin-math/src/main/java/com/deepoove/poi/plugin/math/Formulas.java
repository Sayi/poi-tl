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

import java.util.regex.Pattern;

/**
 * Shortcut factory of {@link FormulaRenderData}, in the style of the core
 * {@code Pictures} / {@code Texts} / {@code Tables} facades.
 * <p>
 * Both syntaxes are first class: {@link #latex(String)} and {@link #mathml(String)}
 * return a builder, so styling and display mode work the same way for each.
 *
 * @author Sayi
 */
public final class Formulas {

    /**
     * Presentation MathML root or fragment, used only to recognise the syntax -
     * never to guess the meaning of the formula.
     */
    private static final Pattern MATHML_MARKER = Pattern.compile(
            "^\\s*<(math|mrow|mfrac|msqrt|mroot|msub|msup|msubsup|munder|mover|munderover|mtable|mfenced"
                    + "|mstyle|mtext|ms|mspace|mi|mn|mo|mpadded|mphantom|menclose|mmultiscripts|semantics)\\b");

    private Formulas() {
    }

    /**
     * @param source LaTeX math
     * @return a builder for an inline formula
     */
    public static FormulaRenderDataBuilder latex(String source) {
        return FormulaRenderData.of(source, FormulaDialect.LATEX);
    }

    /**
     * @param source Presentation MathML, a full {@code <math>} document or a fragment
     * @return a builder for an inline formula
     */
    public static FormulaRenderDataBuilder mathml(String source) {
        return FormulaRenderData.of(source, FormulaDialect.MATHML);
    }

    /**
     * @param source LaTeX math
     * @return an inline formula
     */
    public static FormulaRenderData of(String source) {
        return inline(source);
    }

    /**
     * @param source LaTeX math
     * @return an inline formula ({@code m:oMath})
     */
    public static FormulaRenderData inline(String source) {
        return latex(source).build();
    }

    /**
     * @param source LaTeX math
     * @return a display equation, centred ({@code m:oMathPara})
     */
    public static FormulaRenderData display(String source) {
        return latex(source).display().build();
    }

    /**
     * @param source LaTeX math
     * @param align  horizontal alignment of the display equation
     * @return a display equation
     */
    public static FormulaRenderData display(String source, FormulaAlign align) {
        return latex(source).display().align(align).build();
    }

    /**
     * Picks the syntax from an unambiguous marker: a formula whose first
     * non-blank character starts a known MathML element is MathML, everything
     * else is LaTeX. No LaTeX source can start with {@code <mi} or {@code <mfrac},
     * so this is a syntax test rather than a guess.
     *
     * @param source LaTeX or Presentation MathML
     * @return an inline formula in the detected syntax
     */
    public static FormulaRenderData auto(String source) {
        return isMathMl(source) ? mathml(source).build() : latex(source).build();
    }

    /**
     * @param source formula text, may be null
     * @return true when {@code source} starts with a known MathML element
     */
    public static boolean isMathMl(String source) {
        return null != source && MATHML_MARKER.matcher(source).find();
    }

}
