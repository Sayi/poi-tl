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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("LaTeX subset parser")
class LatexParserTest {

    @Test
    void fractionAndNestedGroups() {
        FormulaNode.Fraction fraction = single("\\frac{a+b}{2}", FormulaNode.Fraction.class);
        assertEquals("a+b", text(fraction.getNumerator()));
        assertEquals("2", text(fraction.getDenominator()));
    }

    @Test
    void radicalWithAndWithoutDegree() {
        FormulaNode.Radical sqrt = single("\\sqrt{x}", FormulaNode.Radical.class);
        assertNull(sqrt.getDegree());
        FormulaNode.Radical cube = single("\\sqrt[3]{x}", FormulaNode.Radical.class);
        assertNotNull(cube.getDegree());
        assertEquals("3", text(first(cube.getDegree())));
    }

    @Test
    void scriptsNormalizeToTheSameShape() {
        for (String latex : new String[] { "x_1^2", "x^2_1" }) {
            FormulaNode.Script script = single(latex, FormulaNode.Script.class);
            assertNotNull(script.getSubscript(), latex);
            assertNotNull(script.getSuperscript(), latex);
            assertEquals("x", text(script.getBase()));
        }
        FormulaNode.Script sub = single("x_i", FormulaNode.Script.class);
        assertNull(sub.getSuperscript());
        FormulaNode.Script sup = single("x^2", FormulaNode.Script.class);
        assertNull(sup.getSubscript());
    }

    @Test
    void bigOperatorsCarryTheirOwnLimits() {
        FormulaNode.BigOperator sum = single("\\sum_{i=1}^{n} i", FormulaNode.BigOperator.class);
        assertEquals("\u2211", sum.getCharacter());
        assertTrue(sum.isUnderOver());
        assertNotNull(sum.getSubscript());
        assertNotNull(sum.getSuperscript());
        assertEquals("i", text(sum.getOperand()));

        FormulaNode.BigOperator integral = single("\\int_0^1 x", FormulaNode.BigOperator.class);
        assertEquals("\u222b", integral.getCharacter());
        assertTrue(!integral.isUnderOver());
        assertEquals("0", text(integral.getSubscript()));
        assertEquals("1", text(integral.getSuperscript()));
    }

    @Test
    void functionAndOperatorNames() {
        FormulaNode.Function function = single("\\sin x", FormulaNode.Function.class);
        assertEquals("sin", function.getName());
        assertEquals("x", text(function.getArgument()));

        // \lim is an operator, not a function: it does not swallow the following item.
        FormulaNode.Row row = row("\\lim_{x \\to 0} f");
        assertEquals(2, row.getItems().size());
        FormulaNode.Limit limit = (FormulaNode.Limit) row.getItems().get(0);
        assertTrue(!limit.isUpper());
        assertTrue(limit.getBody() instanceof FormulaNode.OperatorName);
        assertEquals("x\u21920", text(limit.getLimit()));
        assertEquals("f", text(row.getItems().get(1)));
    }

    @Test
    void delimiters() {
        FormulaNode.Delimiter bare = single("(a+b)", FormulaNode.Delimiter.class);
        assertEquals("(", bare.getBegin());
        assertEquals(")", bare.getEnd());

        FormulaNode.Delimiter leftRight = single("\\left[ x \\right]", FormulaNode.Delimiter.class);
        assertEquals("[", leftRight.getBegin());
        assertEquals("]", leftRight.getEnd());

        FormulaNode.Delimiter invisible = single("\\left. x \\right|", FormulaNode.Delimiter.class);
        assertEquals("", invisible.getBegin());
        assertEquals("|", invisible.getEnd());
    }

    @Test
    void binomialKeepsNoVisibleBrackets() {
        FormulaNode.Delimiter binomial = single("\\binom{n}{k}", FormulaNode.Delimiter.class);
        assertEquals("", binomial.getBegin());
        assertEquals("", binomial.getEnd());
        assertTrue(binomial.getBody() instanceof FormulaNode.Fraction);
    }

    @Test
    void accentsAndBars() {
        assertEquals("\u0302", single("\\hat{x}", FormulaNode.Accent.class).getCharacter());
        assertTrue(single("\\overline{x}", FormulaNode.Bar.class).isOver());
        assertTrue(!single("\\underline{x}", FormulaNode.Bar.class).isOver());
    }

    @Test
    void upperAndLowerLimits() {
        FormulaNode.Limit overset = single("\\overset{a}{b}", FormulaNode.Limit.class);
        assertTrue(overset.isUpper());
        assertEquals("b", text(overset.getBody()));
        assertEquals("a", text(overset.getLimit()));
        assertTrue(!single("\\underset{a}{b}", FormulaNode.Limit.class).isUpper());
    }

    @Test
    void matricesAndCases() {
        FormulaNode.Delimiter pmatrix = single("\\begin{pmatrix} a & b \\\\ c & d \\end{pmatrix}",
                FormulaNode.Delimiter.class);
        assertEquals("(", pmatrix.getBegin());
        FormulaNode.Matrix matrix = (FormulaNode.Matrix) pmatrix.getBody();
        assertEquals(2, matrix.getRows().size());
        assertEquals(2, matrix.getRows().get(0).size());
        assertTrue(!matrix.isEquationArray());

        FormulaNode.Delimiter cases = single("\\begin{cases} a & x>0 \\\\ b & x<0 \\end{cases}",
                FormulaNode.Delimiter.class);
        assertEquals("{", cases.getBegin());
        assertEquals("", cases.getEnd());
        assertTrue(((FormulaNode.Matrix) cases.getBody()).isEquationArray());

        FormulaNode.Matrix array = single("\\begin{array}{lr} a & b \\end{array}", FormulaNode.Matrix.class);
        assertEquals("lr", array.getColumnSpec());
    }

    @Test
    void textAndDoubleStruck() {
        FormulaNode.Row row = row("\\text{if }x");
        FormulaNode.Text text = (FormulaNode.Text) row.getItems().get(0);
        assertTrue(text.isUpright());
        assertEquals("if ", text.getText());
        assertEquals("x", text(row.getItems().get(1)));
        // \\mathbb goes through m:scr, so the letters stay ordinary letters
        FormulaNode.Styled blackboard = single("\\mathbb{R}", FormulaNode.Styled.class);
        assertEquals(FormulaNode.MathScript.DOUBLE_STRUCK, blackboard.getMathScript());
        assertEquals("R", text(blackboard.getBody()));
    }

    @Test
    void greekLettersFollowLatexNotUnicodeGuessing() {
        assertEquals("\u03f5", single("\\epsilon", FormulaNode.Text.class).getText());
        assertEquals("\u03b5", single("\\varepsilon", FormulaNode.Text.class).getText());
        assertEquals("\u03d5", single("\\phi", FormulaNode.Text.class).getText());
        assertEquals("\u03c6", single("\\varphi", FormulaNode.Text.class).getText());
    }

    @Test
    void fontVariantCommands() {
        assertEquals(FormulaNode.MathScript.SCRIPT,
                single("\\mathcal{L}", FormulaNode.Styled.class).getMathScript());
        assertEquals(FormulaNode.MathScript.FRAKTUR,
                single("\\mathfrak{g}", FormulaNode.Styled.class).getMathScript());
        assertEquals(FormulaNode.MathScript.SANS_SERIF,
                single("\\mathsf{A}", FormulaNode.Styled.class).getMathScript());
        assertEquals(FormulaNode.MathScript.MONOSPACE,
                single("\\mathtt{A}", FormulaNode.Styled.class).getMathScript());
        assertEquals(FormulaNode.MathScript.DOUBLE_STRUCK,
                single("\\mathbb{R}", FormulaNode.Styled.class).getMathScript());
        // emphasis, not a font variant
        assertEquals(FormulaNode.MathStyle.BOLD_ITALIC,
                single("\\boldsymbol{a}", FormulaNode.Styled.class).getMathStyle());
        assertEquals(FormulaNode.MathStyle.BOLD,
                single("\\mathbf{a}", FormulaNode.Styled.class).getMathStyle());
        // any letters are fine now, not just the eight double-struck ones
        assertNotNull(single("\\mathbb{ABC}", FormulaNode.Styled.class));
    }

    @Test
    void boxesPhantomsAndBraces() {
        assertTrue(single("\\boxed{x}", FormulaNode.Box.class).getBody() instanceof FormulaNode.Row);
        assertTrue(single("\\phantom{x}", FormulaNode.Phantom.class).getBody() instanceof FormulaNode.Row);
        FormulaNode.GroupChar over = single("\\overbrace{a+b}", FormulaNode.GroupChar.class);
        assertEquals("\u23de", over.getCharacter());
        assertTrue(over.isOver());
        FormulaNode.GroupChar under = single("\\underbrace{a+b}", FormulaNode.GroupChar.class);
        assertEquals("\u23df", under.getCharacter());
        assertTrue(!under.isOver());

        // \\prescript{pre-sup}{pre-sub}{base}, as in mathtools
        FormulaNode.PreScript pre = single("\\prescript{a}{b}{X}", FormulaNode.PreScript.class);
        assertEquals("X", text(pre.getBase()));
        assertEquals("b", text(pre.getSubscript()));
        assertEquals("a", text(pre.getSuperscript()));
    }

    @Test
    void emptyInputIsAnEmptyRow() {
        assertEquals(0, row("").getItems().size());
        assertEquals(0, row("   ").getItems().size());
    }

    @Test
    @DisplayName("everything outside the subset fails with a position")
    void unsupportedInputFailsLoudly() {
        assertMessage("{a", "Unclosed '{'");
        assertMessage("a}", "Unmatched '}'");
        assertMessage("\\foo", "Unsupported LaTeX command");
        assertMessage("a & b", "only allowed inside");
        assertMessage("\\left( x", "no matching '\\right'");
        assertMessage("\\over", "Use '\\frac");
        assertMessage("\\label{x}", "Numbering and cross references");
        assertMessage("\\color{red}", "Use the formula style");
        assertMessage("\\begin{align}", "Unsupported environment");
        assertMessage("\\frac{a}", "requires a '{...}' argument");
        assertMessage("\\right)", "without matching opening");
        assertMessage(")", "Unmatched ')'");
        assertMessage("\\begin{pmatrix} a", "is not closed");
        assertMessage("\\text x", "requires a '{...}' argument");
        assertMessage("\\text{x", "Unclosed '{' after '\\text'");
        assertMessage("x^", "Missing subscript or superscript body");
        assertMessage("x_1_2", "Duplicate subscript");
    }

    @Test
    void nestingIsBounded() {
        StringBuilder deep = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            deep.append('{');
        }
        deep.append('x');
        for (int i = 0; i < 40; i++) {
            deep.append('}');
        }
        assertMessage(deep.toString(), "nested deeper than");
    }

    // ────────────────────────────── helpers ──────────────────────────────

    private static FormulaNode.Row row(String latex) {
        return (FormulaNode.Row) LatexParser.parse(latex);
    }

    private static FormulaNode first(FormulaNode node) {
        return ((FormulaNode.Row) node).getItems().get(0);
    }

    private static String text(FormulaNode node) {
        if (node instanceof FormulaNode.Row) {
            List<FormulaNode> items = ((FormulaNode.Row) node).getItems();
            StringBuilder builder = new StringBuilder();
            for (FormulaNode item : items) {
                builder.append(text(item));
            }
            return builder.toString();
        }
        if (node instanceof FormulaNode.Text) return ((FormulaNode.Text) node).getText();
        if (node instanceof FormulaNode.OperatorName) return ((FormulaNode.OperatorName) node).getName();
        return "";
    }

    private static <T extends FormulaNode> T single(String latex, Class<T> type) {
        FormulaNode.Row row = row(latex);
        assertEquals(1, row.getItems().size(), latex);
        FormulaNode node = row.getItems().get(0);
        assertTrue(type.isInstance(node), latex + " produced " + node.getClass().getSimpleName());
        return type.cast(node);
    }

    private static void assertMessage(String latex, String expected) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> LatexParser.parse(latex),
                latex);
        assertTrue(error.getMessage().contains(expected),
                "expected '" + expected + "' in: " + error.getMessage());
    }

}
