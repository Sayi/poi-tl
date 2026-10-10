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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.officeDocument.x2006.math.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

import com.deepoove.poi.plugin.math.generator.OmmlBuilder;
import com.deepoove.poi.plugin.math.generator.ParsedFormula;

@DisplayName("OMML builder")
class OmmlBuilderTest {

    @Test
    void mathTextUsesTheMathNamespace() throws Exception {
        String xml = render("\\frac{a}{b}", false, null, null);
        assertTrue(xml.contains("<m:oMath>"), xml);
        assertTrue(xml.contains("<m:f>"), xml);
        assertTrue(xml.contains("<m:t>a</m:t>"), xml);
        assertFalse(xml.contains("<m:r><w:t"), "math text must be m:t, never w:t:\n" + xml);
    }

    @Test
    void noPropertiesAreWrittenWhenThereIsNothingToWrite() throws Exception {
        String xml = render("\\frac{a}{b}", false, null, null);
        assertFalse(xml.contains("<m:fPr>"), "an empty m:fPr is noise:\n" + xml);
        assertFalse(xml.contains("<m:ctrlPr>"), xml);
    }

    @Test
    void everySupportedStructureIsEmitted() throws Exception {
        Map<String, String> structures = new LinkedHashMap<String, String>();
        structures.put("\\frac{a}{b}", "<m:f>");
        structures.put("\\sqrt{x}", "<m:rad>");
        structures.put("x^2", "<m:sSup>");
        structures.put("x_i", "<m:sSub>");
        structures.put("x_i^2", "<m:sSubSup>");
        structures.put("\\sum_{i}^{n} i", "<m:nary>");
        structures.put("(a)", "<m:d>");
        structures.put("\\begin{matrix} a & b \\\\ c & d \\end{matrix}", "<m:m>");
        structures.put("\\begin{cases} a \\\\ b \\end{cases}", "<m:eqArr>");
        structures.put("\\overline{x}", "<m:bar>");
        structures.put("\\hat{x}", "<m:acc>");
        structures.put("\\sin x", "<m:func>");
        structures.put("\\lim_{x} f", "<m:limLow>");
        structures.put("\\overset{a}{b}", "<m:limUpp>");
        structures.put("\\binom{n}{k}", "<m:f>");
        structures.put("\\sqrt[3]{x}", "<m:deg>");

        for (Map.Entry<String, String> entry : structures.entrySet()) {
            String xml = render(entry.getKey(), false, null, null);
            assertTrue(xml.contains(entry.getValue()),
                    entry.getKey() + " should contain " + entry.getValue() + " but was:\n" + xml);
            assertFalse(xml.contains("<m:r><w:t"), entry.getKey() + " wrote w:t:\n" + xml);
        }
    }

    @Test
    void displayEquationIsAParagraphElement() throws Exception {
        String xml = render("\\frac{1}{2}", true, null, null);
        assertTrue(xml.contains("<m:oMathPara>"), xml);
        assertTrue(xml.contains("<m:oMathParaPr>"), xml);
        assertTrue(xml.contains("<m:jc m:val=\"center\""), xml);
    }

    @Test
    void uprightTextGetsThePlainMathStyle() throws Exception {
        String xml = render("\\text{if }x", false, null, null);
        assertTrue(xml.contains("<m:sty m:val=\"p\""), xml);
        assertTrue(xml.contains("xml:space=\"preserve\""), "significant spacing needs xml:space:\n" + xml);
    }

    @Test
    void textIsEscapedByXmlBeans() throws Exception {
        String xml = render("\\text{a < b & c}", false, null, null);
        assertTrue(xml.contains("&lt;"), xml);
        assertTrue(xml.contains("&amp;"), xml);
    }

    @Test
    void inheritedRunPropertiesReachEveryMathRun() throws Exception {
        CTRPr inherited = placeholderRunProperties();
        String xml = render("\\frac{a}{b}", false, null, inherited);
        assertTrue(xml.contains("<w:rPr>"), xml);
        assertTrue(xml.contains("<w:sz w:val=\"24\""), "inherited size missing:\n" + xml);
        assertTrue(xml.contains("<w:color w:val=\"C00000\""), "inherited color missing:\n" + xml);
        assertTrue(xml.contains("<m:ctrlPr>"), "the fraction bar should be formatted too:\n" + xml);
    }

    @Test
    void explicitStyleOverridesAreApplied() throws Exception {
        FormulaStyle style = new FormulaStyle().setFontSize(21).setColor("00B050").setBold(true);
        String xml = render("x", false, style, null);
        assertTrue(xml.contains("<w:sz w:val=\"21\""), xml);
        assertTrue(xml.contains("<w:color w:val=\"00B050\""), xml);
        assertTrue(xml.contains("<w:b/>"), xml);
    }

    @Test
    void mathFontIsAlwaysWrittenAndCanBeOverridden() throws Exception {
        // Word writes the math font on every math run, and so do we: without it the
        // run would fall back to the paragraph font, which has no math glyphs.
        String defaultFont = render("x", false, null, null);
        assertTrue(defaultFont.contains("<w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\" "
                + "w:cs=\"Cambria Math\"/>"), defaultFont);
        String custom = render("x", false, new FormulaStyle().setMathFont("XITS Math"), null);
        assertTrue(custom.contains("w:ascii=\"XITS Math\""), custom);
    }

    @Test
    void aBodyFontNeverLeaksIntoTheFormula() throws Exception {
        CTRPr inherited = runPropertiesOf("宋体");
        String xml = render("\\frac{a}{b}", false, null, inherited);
        assertTrue(xml.contains("<w:rFonts w:ascii=\"Cambria Math\" w:hAnsi=\"Cambria Math\" "
                + "w:cs=\"Cambria Math\" w:eastAsia=\"宋体\"/>"),
                "the math font must win, the East Asian font may stay inherited:\n" + xml);
        assertFalse(xml.contains("w:ascii=\"宋体\""), "the body font leaked into the math run:\n" + xml);
    }

    @Test
    void aLargeOperatorWithoutAnOperandHidesItsEmptySlot() throws Exception {
        // An empty m:e is drawn as an empty input box by Word.
        String xml = render("\\sum_{i=1}^{n}", false, null, null);
        assertTrue(xml.contains("<m:e><m:phant><m:phantPr><m:show m:val=\"off\"/>"
                + "<m:zeroWid m:val=\"on\"/></m:phantPr><m:e/></m:phant></m:e>"), xml);
        assertFalse(xml.contains("<m:e/></m:nary>"),
                "an empty m:e of m:nary would show an input box:\n" + xml);
        // with an operand nothing changes
        String withOperand = render("\\sum_{i=1}^{n} a_i", false, null, null);
        assertFalse(withOperand.contains("m:phant"), withOperand);
    }

    @Test
    void fontVariantsUseMathScriptAndEmphasisUsesMathStyle() throws Exception {
        assertTrue(render("\\mathcal{L}", false, null, null).contains("<m:scr m:val=\"script\""),
                render("\\mathcal{L}", false, null, null));
        assertTrue(render("\\mathfrak{g}", false, null, null).contains("<m:scr m:val=\"fraktur\""));
        assertTrue(render("\\mathsf{A}", false, null, null).contains("<m:scr m:val=\"sans-serif\""));
        assertTrue(render("\\mathtt{A}", false, null, null).contains("<m:scr m:val=\"monospace\""));
        assertTrue(render("\\mathbb{R}", false, null, null).contains("<m:scr m:val=\"double-struck\""));
        // a font variant is upright in OMML, so plain comes with it
        assertTrue(render("\\mathcal{L}", false, null, null).contains("<m:sty m:val=\"p\""));
        // emphasis only sets m:sty
        String bold = render("\\mathbf{x}", false, null, null);
        assertTrue(bold.contains("<m:sty m:val=\"b\""), bold);
        assertFalse(bold.contains("m:scr"), bold);
        assertTrue(render("\\boldsymbol{x}", false, null, null).contains("<m:sty m:val=\"bi\""));
    }

    @Test
    void matrixColumnAlignmentFollowsTheSpec() throws Exception {
        String xml = render("\\begin{array}{lr} a & b \\end{array}", false, null, null);
        assertTrue(xml.contains("<m:mcJc m:val=\"left\""), xml);
        assertTrue(xml.contains("<m:mcJc m:val=\"right\""), xml);
        assertTrue(xml.contains("<m:count m:val=\"1\""), xml);
    }

    @Test
    void noSubscriptOrSuperscriptHidesTheLimit() throws Exception {
        String xml = render("\\sum x", false, null, null);
        assertTrue(xml.contains("<m:subHide/>"), xml);
        assertTrue(xml.contains("<m:supHide/>"), xml);
    }

    // ────────────────────────────── helpers ──────────────────────────────

    /** The rPr of a placeholder run whose body font is {@code font}. */
    private static CTRPr runPropertiesOf(String font) {
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFRun run = document.createParagraph().createRun();
            run.setFontFamily(font);
            return (CTRPr) run.getCTR().getRPr().copy();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static CTRPr placeholderRunProperties() {
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setFontSize(12);
            run.setColor("C00000");
            run.setFontFamily("Cambria Math");
            return (CTRPr) run.getCTR().getRPr().copy();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    static String render(String latex, boolean display, FormulaStyle style, CTRPr inherited) throws Exception {
        return render(latex, FormulaDialect.LATEX, display, style, inherited);
    }

    static String render(String source, FormulaDialect dialect, boolean display, FormulaStyle style,
            CTRPr inherited) throws Exception {
        ParsedFormula formula = OmmlBuilder.parse(source, dialect);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (XWPFDocument document = new XWPFDocument()) {
            XWPFParagraph paragraph = document.createParagraph();
            if (display) {
                CTOMathPara paragraphMath = paragraph.getCTP().addNewOMathPara();
                paragraphMath.addNewOMathParaPr().addNewJc().setVal(STJc.CENTER);
                OmmlBuilder.fill(paragraphMath.addNewOMath(), formula, inherited, style);
            } else {
                CTOMath math = paragraph.getCTP().addNewOMath();
                OmmlBuilder.fill(math, formula, inherited, style);
            }
            document.write(out);
        }
        return documentXml(out.toByteArray());
    }

    static String documentXml(byte[] docx) throws Exception {
        try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(docx))) {
            ZipEntry entry;
            while (null != (entry = zip.getNextEntry())) {
                if ("word/document.xml".equals(entry.getName())) {
                    return read(zip);
                }
            }
        }
        throw new IllegalStateException("word/document.xml is missing");
    }

    private static String read(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

}
