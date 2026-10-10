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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The two front ends must agree on the OMML they produce.
 * <p>
 * Comparing the whole {@code word/document.xml} turns "both parsers are
 * correct" into a single assertion, and it is the cheapest way to keep the two
 * dialects from drifting apart.
 *
 * @author Sayi
 */
@DisplayName("LaTeX and MathML produce the same OMML")
class DialectEquivalenceTest {

    @Test
    void equivalentFormulasRenderByteIdentically() throws Exception {
        String[][] pairs = {
                { "\\frac{a}{b}", "<mfrac><mi>a</mi><mi>b</mi></mfrac>" },
                { "\\frac{a}{b}", "<mfrac bevelled=\"false\"><mi>a</mi><mi>b</mi></mfrac>" },
                { "\\sqrt{x}", "<msqrt><mi>x</mi></msqrt>" },
                { "\\sqrt[3]{x}", "<mroot><mi>x</mi><mn>3</mn></mroot>" },
                // <msqrt> takes any number of children: the content is one inferred row
                { "\\sqrt{x+1}", "<msqrt><mi>x</mi><mo>+</mo><mn>1</mn></msqrt>" },
                { "x^{2+1}", "<msup><mi>x</mi><mn>2</mn><mo>+</mo><mn>1</mn></msup>" },
                { "x^2", "<msup><mi>x</mi><mn>2</mn></msup>" },
                { "x_i", "<msub><mi>x</mi><mi>i</mi></msub>" },
                { "x_i^2", "<msubsup><mi>x</mi><mi>i</mi><mn>2</mn></msubsup>" },

                { "\\sum_{i}^{n} i", "<munderover><mo>&#x2211;</mo><mi>i</mi><mi>n</mi></munderover><mi>i</mi>" },
                { "\\int_0^1 x", "<msubsup><mo>&#x222B;</mo><mn>0</mn><mn>1</mn></msubsup><mi>x</mi>" },

                { "\\overline{x}", "<mover accent=\"true\"><mi>x</mi><mo>&#xAF;</mo></mover>" },
                { "\\underline{x}", "<munder accentunder=\"true\"><mi>x</mi><mo>_</mo></munder>" },
                { "\\hat{x}", "<mover accent=\"true\"><mi>x</mi><mo>^</mo></mover>" },
                { "\\vec{x}", "<mover accent=\"true\"><mi>x</mi><mo>&#x2192;</mo></mover>" },
                { "\\overbrace{x}", "<mover accent=\"true\"><mi>x</mi><mo>&#x23DE;</mo></mover>" },
                { "\\underbrace{x}", "<munder accentunder=\"true\"><mi>x</mi><mo>&#x23DF;</mo></munder>" },

                { "\\overset{a}{b}", "<mover><mi>b</mi><mi>a</mi></mover>" },
                { "\\underset{a}{b}", "<munder><mi>b</mi><mi>a</mi></munder>" },
                { "\\lim_{x} f", "<munder><mo>lim</mo><mi>x</mi></munder><mi>f</mi>" },

                { "\\sin x", "<mi>sin</mi><mo>&#x2061;</mo><mi>x</mi>" },
                { "\\text{if}", "<mtext>if</mtext>" },
                { "\\mathbb{R}", "<mi mathvariant=\"double-struck\">R</mi>" },
                { "\\mathrm{sin}", "<mstyle mathvariant=\"normal\"><mi>sin</mi></mstyle>" },
                { "\\mathbf{x}", "<mstyle mathvariant=\"bold\"><mi>x</mi></mstyle>" },

                { "(a+b)", "<mfenced separators=\"\"><mi>a</mi><mo>+</mo><mi>b</mi></mfenced>" },
                // the same thing written with plain <mo> fences, which tools prefer
                { "(a+b)", "<mo>(</mo><mi>a</mi><mo>+</mo><mi>b</mi><mo>)</mo>" },
                { "\\left[ x \\right]", "<mo>[</mo><mi>x</mi><mo>]</mo>" },
                { "\\left\\{ x \\right\\}", "<mo>{</mo><mi>x</mi><mo>}</mo>" },
                { "\\begin{cases} a & x>0 \\\\ b & x<0 \\end{cases}",
                        "<mo>{</mo><mtable>"
                                + "<mtr><mtd><mi>a</mi></mtd><mtd><mi>x</mi><mo>&gt;</mo><mn>0</mn></mtd></mtr>"
                                + "<mtr><mtd><mi>b</mi></mtd><mtd><mi>x</mi><mo>&lt;</mo><mn>0</mn></mtd></mtr>"
                                + "</mtable>" },
                // font variants: OMML keeps m:scr and m:sty apart
                { "\\mathcal{L}", "<mi mathvariant=\"script\">L</mi>" },
                { "\\mathfrak{g}", "<mi mathvariant=\"fraktur\">g</mi>" },
                { "\\mathsf{A}", "<mi mathvariant=\"sans-serif\">A</mi>" },
                { "\\mathtt{A}", "<mi mathvariant=\"monospace\">A</mi>" },
                { "\\mathbf{x}", "<mi mathvariant=\"bold\">x</mi>" },
                { "\\boldsymbol{x}", "<mi mathvariant=\"bold-italic\">x</mi>" },
                { "\\mathbb{R}", "<mi mathvariant=\"double-struck\">R</mi>" },
                { "\\binom{n}{k}",
                        "<mfenced open=\"\" close=\"\" separators=\"\"><mfrac><mi>n</mi><mi>k</mi></mfrac></mfenced>" },
                { "\\boxed{x}", "<mpadded><mi>x</mi></mpadded>" },
                { "\\phantom{x}", "<mphantom><mi>x</mi></mphantom>" },
                { "\\prescript{a}{b}{X}",
                        "<mmultiscripts><mi>X</mi><mprescripts/><mi>b</mi><mi>a</mi></mmultiscripts>" },
                { "\\begin{pmatrix} a & b \\\\ c & d \\end{pmatrix}",
                        "<mfenced open=\"(\" close=\")\"><mtable>"
                                + "<mtr><mtd><mi>a</mi></mtd><mtd><mi>b</mi></mtd></mtr>"
                                + "<mtr><mtd><mi>c</mi></mtd><mtd><mi>d</mi></mtd></mtr></mtable></mfenced>" },
                { "\\begin{array}{lr} a & b \\end{array}",
                        "<mtable columnalign=\"left right\">"
                                + "<mtr><mtd><mi>a</mi></mtd><mtd><mi>b</mi></mtd></mtr></mtable>" },
                { "\\alpha + \\beta", "<mi>&#x3B1;</mi><mo>+</mo><mi>&#x3B2;</mi>" } };

        for (String[] pair : pairs) {
            String fromLatex = OmmlBuilderTest.render(pair[0], false, null, null);
            String fromMathMl = OmmlBuilderTest.render(pair[1], FormulaDialect.MATHML, false, null, null);
            assertEquals(fromLatex, fromMathMl, pair[0] + "  <->  " + pair[1] + "\n");
        }
    }

    @Test
    @DisplayName("where the two syntaxes ask for different things, the output differs")
    void intentionalDifferencesStayDifferent() throws Exception {
        // mtable without a column spec is a plain matrix, never an equation array:
        // <mtable> carries no "this is a cases block" flag.
        assertNotEquals(OmmlBuilderTest.render("\\begin{cases} a \\\\ b \\end{cases}", false, null, null),
                OmmlBuilderTest.render("<mtable><mtr><mtd><mi>a</mi></mtd></mtr>"
                        + "<mtr><mtd><mi>b</mi></mtd></mtr></mtable>", FormulaDialect.MATHML, false, null, null));
        // msub on a big operator asks for side scripts, munder for limits above.
        assertNotEquals(OmmlBuilderTest.render("\\sum_{i} x", false, null, null),
                OmmlBuilderTest.render("<msub><mo>&#x2211;</mo><mi>i</mi></msub><mi>x</mi>",
                        FormulaDialect.MATHML, false, null, null));
        // A separator between mfenced children is a real list.
        assertNotEquals(OmmlBuilderTest.render("(a+b)", false, null, null),
                OmmlBuilderTest.render("<mfenced><mi>a</mi><mi>b</mi></mfenced>", FormulaDialect.MATHML, false,
                        null, null));
    }

}
