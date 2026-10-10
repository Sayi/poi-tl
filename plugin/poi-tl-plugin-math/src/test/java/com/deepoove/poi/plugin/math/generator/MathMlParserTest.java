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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Presentation MathML parser")
class MathMlParserTest {

    @Test
    void everySupportedElementMapsToItsNode() {
        assertTrue(only("<mfrac><mi>a</mi><mi>b</mi></mfrac>") instanceof FormulaNode.Fraction);
        assertTrue(only("<msqrt><mi>x</mi></msqrt>") instanceof FormulaNode.Radical);
        assertTrue(only("<mroot><mi>x</mi><mn>3</mn></mroot>") instanceof FormulaNode.Radical);
        assertTrue(only("<msup><mi>x</mi><mn>2</mn></msup>") instanceof FormulaNode.Script);
        assertTrue(only("<msub><mi>x</mi><mi>i</mi></msub>") instanceof FormulaNode.Script);
        assertTrue(only("<msubsup><mi>x</mi><mi>i</mi><mn>2</mn></msubsup>") instanceof FormulaNode.Script);
        assertTrue(only("<mfenced><mi>a</mi></mfenced>") instanceof FormulaNode.Delimiter);
        assertTrue(only("<mtable><mtr><mtd><mi>a</mi></mtd></mtr></mtable>") instanceof FormulaNode.Matrix);
        assertTrue(only("<mstyle mathvariant=\"bold\"><mi>x</mi></mstyle>") instanceof FormulaNode.Styled);
        assertTrue(only("<mpadded><mi>x</mi></mpadded>") instanceof FormulaNode.Box);
        assertTrue(only("<mphantom><mi>x</mi></mphantom>") instanceof FormulaNode.Phantom);
        assertTrue(only("<menclose notation=\"box\"><mi>x</mi></menclose>") instanceof FormulaNode.BorderBox);
        assertTrue(only("<menclose notation=\"top\"><mi>x</mi></menclose>") instanceof FormulaNode.Bar);
        assertTrue(only("<menclose notation=\"bottom\"><mi>x</mi></menclose>") instanceof FormulaNode.Bar);
        assertTrue(only("<mmultiscripts><mi>X</mi><mprescripts/><mi>b</mi><mi>a</mi></mmultiscripts>")
                instanceof FormulaNode.PreScript);
        assertTrue(only("<mover accent=\"true\"><mi>x</mi><mo>^</mo></mover>") instanceof FormulaNode.Accent);
        assertTrue(only("<mover accent=\"true\"><mi>x</mi><mo>&#xAF;</mo></mover>") instanceof FormulaNode.Bar);
        assertTrue(only("<mover accent=\"true\"><mi>x</mi><mo>&#x23DE;</mo></mover>")
                instanceof FormulaNode.GroupChar);
        assertTrue(only("<mover><mi>b</mi><mi>a</mi></mover>") instanceof FormulaNode.Limit);
        assertTrue(first("<munderover><mo>&#x2211;</mo><mi>i</mi><mi>n</mi></munderover><mi>x</mi>")
                instanceof FormulaNode.BigOperator);
    }

    @Test
    void largeOperatorLimitsPlacementIsWhatTheAuthorAskedFor() {
        FormulaNode.BigOperator above = (FormulaNode.BigOperator) first(
                "<munderover><mo>&#x2211;</mo><mi>i</mi><mi>n</mi></munderover><mi>x</mi>");
        assertTrue(above.isUnderOver());

        // msubsup means side scripts, which is m:nary with subSup - and it is how
        // MathML writes an integral together with its limits.
        FormulaNode.BigOperator side = (FormulaNode.BigOperator) first(
                "<msubsup><mo>&#x222B;</mo><mn>0</mn><mn>1</mn></msubsup><mi>x</mi>");
        assertTrue(!side.isUnderOver());
        assertEquals("x", text(side.getOperand()));
    }

    @Test
    void fragmentsAndNamespaces() {
        // no <math> root and no namespace
        assertTrue(only("<mfrac><mi>a</mi><mi>b</mi></mfrac>") instanceof FormulaNode.Fraction);
        // a namespaced document
        assertTrue(only("<math xmlns=\"http://www.w3.org/1998/Math/MathML\">"
                + "<mfrac><mi>a</mi><mi>b</mi></mfrac></math>") instanceof FormulaNode.Fraction);
        // a prefixed document
        assertTrue(only("<m:math xmlns:m=\"http://www.w3.org/1998/Math/MathML\">"
                + "<m:mfrac><m:mi>a</m:mi><m:mi>b</m:mi></m:mfrac></m:math>") instanceof FormulaNode.Fraction);
        // several top level elements in one fragment
        assertEquals(3, row("<mi>a</mi><mo>+</mo><mi>b</mi>").getItems().size());
    }

    @Test
    void wrongNamespaceIsRejected() {
        assertMessage("<math xmlns=\"http://example.com/other\"><mi>x</mi></math>", "Unsupported MathML namespace");
    }

    @Test
    void namedEntitiesAreTranslatedAndTheRestAreReported() {
        // A named entity XML does not define, but that formulas use all the time.
        assertEquals(2, row("<mi>a</mi>&InvisibleTimes;<mi>b</mi>").getItems().size());
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> MathMlParser.parse("<mi>&Sqrt;</mi>"));
        assertTrue(error.getMessage().contains("&Sqrt;"), error.getMessage());
        assertTrue(error.getMessage().contains("&#x2062;"), error.getMessage());
    }

    @Test
    void functionApplicationIsRecognised() {
        FormulaNode.Function function = (FormulaNode.Function) only(
                "<mi>sin</mi><mo>&#x2061;</mo><mi>x</mi>");
        assertEquals("sin", function.getName());
        assertEquals("x", text(function.getArgument()));
        // a multi character identifier that is not a known function stays a name
        FormulaNode.Text name = (FormulaNode.Text) only("<mi>ABC</mi>");
        assertEquals("ABC", name.getText());
        assertTrue(name.isUpright());
    }

    @Test
    void operatorNamesBecomeTheOperatorNode() {
        assertEquals("lim", ((FormulaNode.OperatorName) only("<mo>lim</mo>")).getName());
        FormulaNode.Limit limit = (FormulaNode.Limit) only("<munder><mo>lim</mo><mi>x</mi></munder>");
        assertTrue(limit.getBody() instanceof FormulaNode.OperatorName);
        assertEquals("lim", ((FormulaNode.OperatorName) limit.getBody()).getName());
        assertTrue(!limit.isUpper());
    }

    @Test
    void fencesCarryTheirDelimitersAndSeparators() {
        FormulaNode.Delimiter invisible = (FormulaNode.Delimiter) only(
                "<mfenced open=\"\" close=\"\"><mfrac><mi>n</mi><mi>k</mi></mfrac></mfenced>");
        assertEquals("", invisible.getBegin());
        assertEquals("", invisible.getEnd());
        assertEquals(1, invisible.getArguments().size());

        FormulaNode.Delimiter list = (FormulaNode.Delimiter) only(
                "<mfenced separators=\";\"><mi>a</mi><mi>b</mi></mfenced>");
        assertEquals(2, list.getArguments().size());
        assertEquals(";", list.getSeparator());

        // an empty separator means one expression, not a list
        FormulaNode.Delimiter grouped = (FormulaNode.Delimiter) only(
                "<mfenced separators=\"\"><mi>a</mi><mo>+</mo><mi>b</mi></mfenced>");
        assertEquals(1, grouped.getArguments().size());
    }

    @Test
    void tablesCarryTheirColumnAlignment() {
        FormulaNode.Matrix matrix = (FormulaNode.Matrix) only(
                "<mtable columnalign=\"left right\"><mtr><mtd><mi>a</mi></mtd><mtd><mi>b</mi></mtd></mtr></mtable>");
        assertEquals("lr", matrix.getColumnSpec());
        assertEquals(1, matrix.getRows().size());
        assertEquals(2, matrix.getRows().get(0).size());

        // a single value applies to every column
        FormulaNode.Matrix repeated = (FormulaNode.Matrix) only(
                "<mtable columnalign=\"right\"><mtr><mtd><mi>a</mi></mtd><mtd><mi>b</mi></mtd></mtr></mtable>");
        assertEquals("rr", repeated.getColumnSpec());
    }

    @Test
    void mathVariantIsHonoured() {
        assertTrue(((FormulaNode.Text) only("<mi mathvariant=\"normal\">x</mi>")).isUpright());
        assertTrue(!((FormulaNode.Text) only("<mi>x</mi>")).isUpright());
        FormulaNode.Styled bold = (FormulaNode.Styled) only("<mstyle mathvariant=\"bold\"><mi>x</mi></mstyle>");
        assertEquals(FormulaNode.MathStyle.BOLD, bold.getMathStyle());
        FormulaNode.Styled boldItalic = (FormulaNode.Styled) only(
                "<mstyle mathvariant=\"bold-italic\"><mi>x</mi></mstyle>");
        assertEquals(FormulaNode.MathStyle.BOLD_ITALIC, boldItalic.getMathStyle());
        assertEquals(FormulaNode.MathScript.DOUBLE_STRUCK,
                ((FormulaNode.Styled) only("<mi mathvariant=\"double-struck\">R</mi>")).getMathScript());
        assertEquals(FormulaNode.MathScript.SCRIPT,
                ((FormulaNode.Styled) only("<mstyle mathvariant=\"script\"><mi>L</mi></mstyle>")).getMathScript());
        assertEquals(FormulaNode.MathScript.FRAKTUR,
                ((FormulaNode.Styled) only("<mi mathvariant=\"fraktur\">g</mi>")).getMathScript());
        assertEquals(FormulaNode.MathScript.SANS_SERIF,
                ((FormulaNode.Styled) only("<mi mathvariant=\"sans-serif\">A</mi>")).getMathScript());
        assertEquals(FormulaNode.MathScript.MONOSPACE,
                ((FormulaNode.Styled) only("<mi mathvariant=\"monospace\">A</mi>")).getMathScript());
        // emphasis and font variant are orthogonal, so a combined value sets both
        FormulaNode.Styled combined = (FormulaNode.Styled) only("<mi mathvariant=\"bold-sans-serif\">A</mi>");
        assertEquals(FormulaNode.MathStyle.BOLD, combined.getMathStyle());
        assertEquals(FormulaNode.MathScript.SANS_SERIF, combined.getMathScript());
    }

    @Test
    void surplusChildrenFormAnInferredRow() {
        // the everyday Radicand: <msqrt> takes any number of children
        FormulaNode.Radical sqrt = (FormulaNode.Radical) only("<msqrt><mi>x</mi><mo>+</mo><mn>1</mn></msqrt>");
        assertEquals("x+1", text(sqrt.getRadicand()));
        assertNull(sqrt.getDegree());

        // elsewhere the surplus belongs to the last argument
        FormulaNode.Script script = (FormulaNode.Script) only("<msup><mi>x</mi><mn>2</mn><mo>+</mo><mn>1</mn></msup>");
        assertEquals("x", text(script.getBase()));
        assertEquals("2+1", text(script.getSuperscript()));
    }

    @Test
    void pairedFencesBecomeADelimiter() {
        FormulaNode.Delimiter pair = (FormulaNode.Delimiter) first("<mo>(</mo><mi>a</mi><mo>+</mo><mi>b</mi><mo>)</mo>");
        assertEquals("(", pair.getBegin());
        assertEquals(")", pair.getEnd());
        assertEquals(1, pair.getArguments().size());
        assertEquals("a+b", text(pair.getBody()));

        // nested pairs close in order
        FormulaNode.Delimiter nested = (FormulaNode.Delimiter) first(
                "<mo>(</mo><mi>a</mi><mo>[</mo><mi>b</mi><mo>]</mo><mo>)</mo>");
        assertEquals("(", nested.getBegin());
        assertTrue(nested.getBody() instanceof FormulaNode.Row);

        // a lone fence is left as a literal glyph
        assertTrue(!(first("<mo>(</mo><mi>a</mi>") instanceof FormulaNode.Delimiter));

        // so is an unmatched closing fence
        assertTrue(!(first("<mi>a</mi><mo>)</mo>") instanceof FormulaNode.Delimiter));
    }

    @Test
    void braceAroundATableIsACasesBlock() {
        FormulaNode.Delimiter cases = (FormulaNode.Delimiter) first(
                "<mo>{</mo><mtable><mtr><mtd><mi>a</mi></mtd><mtd><mi>x</mi></mtd></mtr>"
                        + "<mtr><mtd><mi>b</mi></mtd><mtd><mi>y</mi></mtd></mtr></mtable>");
        assertEquals("{", cases.getBegin());
        assertEquals("", cases.getEnd());
        FormulaNode.Matrix matrix = (FormulaNode.Matrix) cases.getBody();
        assertTrue(matrix.isEquationArray());
        assertEquals(2, matrix.getRows().size());

        // a parenthesised table stays a matrix
        FormulaNode.Delimiter pmatrix = (FormulaNode.Delimiter) first(
                "<mo>(</mo><mtable><mtr><mtd><mi>a</mi></mtd></mtr></mtable><mo>)</mo>");
        assertEquals("(", pmatrix.getBegin());
        assertEquals(")", pmatrix.getEnd());
        assertTrue(!((FormulaNode.Matrix) pmatrix.getBody()).isEquationArray());
    }

    @Test
    void spacesBecomeTheirWidth() {
        assertEquals(2, row("<mspace width=\"2em\"/>").getItems().size());
        assertEquals(1, row("<mspace width=\"0.5em\"/>").getItems().size());
        assertEquals(1, row("<mspace width=\"3pt\"/>").getItems().size());
        assertEquals(0, row("<mspace width=\"-1em\"/>").getItems().size());
        assertEquals(0, row("<mspace/>").getItems().size());
    }

    @Test
    void multiScriptsKeepBothSides() {
        FormulaNode.Script script = (FormulaNode.Script) only(
                "<mmultiscripts><mi>X</mi><mi>i</mi><mn>2</mn><mprescripts/><mi>b</mi><mi>a</mi></mmultiscripts>");
        assertNotNull(script.getSubscript());
        assertNotNull(script.getSuperscript());
        FormulaNode.PreScript pre = (FormulaNode.PreScript) script.getBase();
        assertEquals("X", text(pre.getBase()));
        assertEquals("b", text(pre.getSubscript()));
        assertEquals("a", text(pre.getSuperscript()));
    }

    @Test
    void semanticsPrefersThePresentationTreeAndFallsBackToTex() {
        FormulaNode.Row preferred = row("<semantics><mrow><mi>a</mi></mrow>"
                + "<annotation encoding=\"application/x-tex\">\\frac{1}{2}</annotation></semantics>");
        assertEquals(1, preferred.getItems().size());
        assertTrue(preferred.getItems().get(0) instanceof FormulaNode.Text);

        // nothing presentational: the TeX annotation still gives a formula
        FormulaNode.Row fromTex = row("<semantics>"
                + "<annotation-xml encoding=\"application/x-tex\">\\frac{1}{2}</annotation-xml></semantics>");
        assertEquals(1, fromTex.getItems().size());
        assertTrue(fromTex.getItems().get(0) instanceof FormulaNode.Fraction);
    }

    @Test
    void malformedInputIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> MathMlParser.parse(""));
        assertThrows(IllegalArgumentException.class, () -> MathMlParser.parse("   "));
        assertMessage("<mfrac><mi>a</mi></mfrac>", "needs at least 2 child elements, found 1");
        assertMessage("<msqrt/>", "needs at least 1 child element, found 0");
        assertMessage("<msubsup><mi>x</mi><mi>i</mi></msubsup>", "needs at least 3 child elements, found 2");
        assertMessage("<munder><mo>&#x2211;</mo></munder>", "needs at least 2 child elements, found 1");
        assertMessage("<mfrac><mi>a</mi>", "Invalid MathML");
        assertMessage("<apply><plus/></apply>", "Content MathML");
        assertMessage("<ci>x</ci>", "Content MathML");
        assertMessage("<mfoo><mi>x</mi></mfoo>", "Unsupported MathML element '<mfoo>'");
        assertMessage("<menclose notation=\"circle\"><mi>x</mi></menclose>", "no OMML equivalent");
        assertMessage("<semantics><annotation>x</annotation></semantics>", "without a presentation tree");
    }

    @Test
    void externalEntitiesAreNeverResolved() {
        assertThrows(IllegalArgumentException.class,
                () -> MathMlParser.parse("<!DOCTYPE math [<!ENTITY x SYSTEM \"file:///etc/passwd\">]><mi>&x;</mi>"));
    }

    // ────────────────────────────── helpers ──────────────────────────────

    private static FormulaNode.Row row(String mathml) {
        return (FormulaNode.Row) MathMlParser.parse(mathml);
    }

    /** Asserts the fragment produced exactly one node and returns it. */
    private static FormulaNode only(String mathml) {
        FormulaNode.Row row = row(mathml);
        assertEquals(1, row.getItems().size(), mathml);
        return row.getItems().get(0);
    }

    private static FormulaNode first(String mathml) {
        return row(mathml).getItems().get(0);
    }

    private static String text(FormulaNode node) {
        if (node instanceof FormulaNode.Row) {
            StringBuilder builder = new StringBuilder();
            for (FormulaNode item : ((FormulaNode.Row) node).getItems()) {
                builder.append(text(item));
            }
            return builder.toString();
        }
        if (node instanceof FormulaNode.Text) return ((FormulaNode.Text) node).getText();
        if (node instanceof FormulaNode.OperatorName) return ((FormulaNode.OperatorName) node).getName();
        return "";
    }

    private static void assertMessage(String mathml, String expected) {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> MathMlParser.parse(mathml), mathml);
        assertTrue(error.getMessage().contains(expected),
                "expected '" + expected + "' in: " + error.getMessage());
    }

}
