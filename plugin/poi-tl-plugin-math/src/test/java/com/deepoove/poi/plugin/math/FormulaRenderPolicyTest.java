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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

@DisplayName("FormulaRenderPolicy end to end")
class FormulaRenderPolicyTest {

    private static final String FALLBACK = "[formula] ";

    @Test
    void dollarGrammarRendersAnInlineFormula() throws Exception {
        byte[] out = render(paragraphTemplate("前 ", "{{$eq}}", " 后"), plugin(), data("eq", "\\frac{a}{b}"));
        String xml = OmmlBuilderTest.documentXml(out);
        assertTrue(xml.contains("<m:oMath>"), xml);
        assertTrue(xml.contains("<m:f>"), xml);
        assertTrue(xml.contains("<m:t>a</m:t>"), xml);
        assertFalse(xml.contains("$eq"), "the placeholder must be gone:\n" + xml);
        assertFalse(xml.contains("<m:r><w:t"), "math text must use m:t:\n" + xml);
        assertTrue(written(out).contains("前 "), "surrounding text survives");
    }

    @Test
    void bindGrammarWorksToo() throws Exception {
        byte[] out = render(paragraphTemplate("{{equation}}"), bound("equation"), data("equation", "x^2"));
        String xml = OmmlBuilderTest.documentXml(out);
        assertTrue(xml.contains("<m:sSup>"), xml);
        assertFalse(xml.contains("equation"));
    }

    @Test
    void plainStringsAreAcceptedAndRenderedInline() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(), data("eq", "\\sqrt{2}"));
        assertTrue(OmmlBuilderTest.documentXml(out).contains("<m:rad>"));
    }

    @Test
    void twoFormulasInOneParagraphBothRender() throws Exception {
        byte[] out = render(paragraphTemplate("A", "{{$one}}", "B", "{{$two}}", "C"), plugin(),
                data("one", "1", "two", "2"));
        String xml = OmmlBuilderTest.documentXml(out);
        assertEquals(2, count(xml, "<m:oMath>"), xml);
        assertFalse(xml.contains("$one"));
        assertFalse(xml.contains("$two"));
    }

    @Test
    void formulaInsideATableCell() throws Exception {
        XWPFDocument template = new XWPFDocument();
        XWPFTable table = template.createTable(1, 1);
        table.getRow(0).getCell(0).getParagraphs().get(0).createRun().setText("{{$cell}}");
        byte[] out = render(template, plugin(), data("cell", "\\frac{1}{2}"));
        assertTrue(OmmlBuilderTest.documentXml(out).contains("<m:f>"));
        try (XWPFDocument reopened = new XWPFDocument(new ByteArrayInputStream(out))) {
            assertEquals(1, reopened.getTables().size());
        }
    }

    @Test
    void formulaInsideAForeachBlock() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("{{?items}}");
        template.createParagraph().createRun().setText("{{$eq}}");
        template.createParagraph().createRun().setText("{{/items}}");

        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        items.add(data("eq", "\\frac{1}{2}"));
        items.add(data("eq", "\\frac{3}{4}"));
        byte[] out = render(template, plugin(), data("items", items));

        String xml = OmmlBuilderTest.documentXml(out);
        assertEquals(2, count(xml, "<m:f>"), "both iterations should render a formula:\n" + xml);
        assertFalse(xml.contains("$eq"), "no placeholder may survive:\n" + xml);
        assertTrue(written(out).indexOf("$eq") < 0);
    }

    @Test
    void displayEquationIsPlacedInItsOwnParagraphMath() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(),
                data("eq", Formulas.display("\\int_0^1 x")));
        String xml = OmmlBuilderTest.documentXml(out);
        assertTrue(xml.contains("<m:oMathPara>"), xml);
        assertTrue(xml.contains("<m:jc m:val=\"center\""), xml);
    }

    @Test
    void styleIsInheritedFromThePlaceholderRun() throws Exception {
        XWPFDocument template = new XWPFDocument();
        XWPFParagraph paragraph = template.createParagraph();
        org.apache.poi.xwpf.usermodel.XWPFRun run = paragraph.createRun();
        run.setText("{{$eq}}");
        run.setFontSize(12);
        run.setColor("C00000");
        byte[] out = render(template, plugin(), data("eq", "x"));
        String xml = OmmlBuilderTest.documentXml(out);
        assertTrue(xml.contains("<w:sz w:val=\"24\""), xml);
        assertTrue(xml.contains("<w:color w:val=\"C00000\""), xml);
    }

    @Test
    void unsupportedFormulaFallsBackToTheSource() throws Exception {
        byte[] out = render(paragraphTemplate("{{$broken}}"), plugin(), data("broken", "\\foo"));
        String xml = OmmlBuilderTest.documentXml(out);
        assertTrue(xml.contains(FALLBACK + "\\foo"), xml);
        assertFalse(xml.contains("<m:oMath>"), xml);
        written(out);
    }

    @Test
    void altMetaOverridesTheFallbackText() throws Exception {
        FormulaRenderData data = FormulaRenderData.of("\\foo").altMeta("公式渲染失败").build();
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(), data("eq", data));
        assertTrue(OmmlBuilderTest.documentXml(out).contains("公式渲染失败"));
    }

    @Test
    void emptyFormulaFallsBack() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(), data("eq", "   "));
        assertTrue(OmmlBuilderTest.documentXml(out).contains(FALLBACK));
    }

    @Test
    void unsupportedDataTypeClearsTheTagWithoutBreakingTheDocument() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(), data("eq", new byte[] { 1, 2, 3 }));
        String xml = OmmlBuilderTest.documentXml(out);
        assertFalse(xml.contains("<m:oMath>"), xml);
        assertFalse(xml.contains("$eq"), "the tag is cleared:\n" + xml);
        written(out);
    }

    @Test
    void withoutThePluginTheTagIsLeftUntouched() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), Configure.createDefault(), data("eq", "x"));
        String xml = OmmlBuilderTest.documentXml(out);
        assertFalse(xml.contains("<m:oMath>"), xml);
        assertTrue(xml.contains("$eq"), "an unregistered grammar char is not consumed:\n" + xml);
    }

    @Test
    void mathMlRendersThroughTheSamePolicy() throws Exception {
        byte[] out = render(paragraphTemplate("前 ", "{{$eq}}", " 后"), plugin(),
                data("eq", Formulas.mathml("<mfrac><mi>a</mi><mi>b</mi></mfrac>").build()));
        String xml = OmmlBuilderTest.documentXml(out);
        assertTrue(xml.contains("<m:oMath>"), xml);
        assertTrue(xml.contains("<m:f>"), xml);
        assertTrue(xml.contains("<m:t>a</m:t>"), xml);
        assertFalse(xml.contains("$eq"), xml);
        assertTrue(written(out).contains("前 "), "surrounding text survives");
    }

    @Test
    void mathMlDisplayEquation() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(),
                data("eq", Formulas.mathml("<msqrt><mi>x</mi></msqrt>").display().align(FormulaAlign.RIGHT).build()));
        String xml = OmmlBuilderTest.documentXml(out);
        assertTrue(xml.contains("<m:oMathPara>"), xml);
        assertTrue(xml.contains("<m:jc m:val=\"right\""), xml);
        assertTrue(xml.contains("<m:rad>"), xml);
    }

    @Test
    void aPlainMathMlStringIsDetectedByItsSyntax() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(),
                data("eq", "<mfrac><mi>1</mi><mi>2</mi></mfrac>"));
        assertTrue(OmmlBuilderTest.documentXml(out).contains("<m:f>"));
        assertTrue(Formulas.isMathMl("<mfrac/>"));
        assertTrue(Formulas.isMathMl("  <math><mi>x</mi></math>"));
        assertFalse(Formulas.isMathMl("\\frac{1}{2}"));
        assertFalse(Formulas.isMathMl("<"));
        assertFalse(Formulas.isMathMl(null));
    }

    @Test
    void bothDialectsLiveInOneDocument() throws Exception {
        byte[] out = render(paragraphTemplate("A", "{{$latex}}", "B", "{{$mathml}}", "C"), plugin(),
                data("latex", "\\frac{a}{b}", "mathml", Formulas.mathml("<msup><mi>x</mi><mn>2</mn></msup>").build()));
        String xml = OmmlBuilderTest.documentXml(out);
        assertEquals(2, count(xml, "<m:oMath>"), xml);
        assertTrue(xml.contains("<m:f>"), xml);
        assertTrue(xml.contains("<m:sSup>"), xml);
    }

    @Test
    void mathMlInsideATableCell() throws Exception {
        XWPFDocument template = new XWPFDocument();
        XWPFTable table = template.createTable(1, 1);
        table.getRow(0).getCell(0).getParagraphs().get(0).createRun().setText("{{$cell}}");
        byte[] out = render(template, plugin(),
                data("cell", Formulas.mathml("<msqrt><mi>x</mi></msqrt>").build()));
        assertTrue(OmmlBuilderTest.documentXml(out).contains("<m:rad>"));
        written(out);
    }

    @Test
    void brokenMathMlFallsBackWithoutLeavingAnEmptyMathElement() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(), data("eq", Formulas.mathml("<mfoo/>").build()));
        String xml = OmmlBuilderTest.documentXml(out);
        // The source is written back verbatim, so it appears escaped in the XML.
        assertTrue(written(out).contains(FALLBACK + "<mfoo/>"), written(out));
        assertFalse(xml.contains("<m:oMath>"), "no orphan oMath may be left behind:\n" + xml);
        written(out);
    }

    @Test
    void contentMathMlFallsBackToo() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(),
                data("eq", Formulas.mathml("<apply><plus/><ci>x</ci></apply>").build()));
        assertTrue(OmmlBuilderTest.documentXml(out).contains(FALLBACK));
        written(out);
    }

    @Test
    void unknownEntityInMathMlIsReportedInTheFallbackPath() throws Exception {
        byte[] out = render(paragraphTemplate("{{$eq}}"), plugin(), data("eq", Formulas.mathml("<mi>&Sqrt;</mi>").build()));
        assertTrue(OmmlBuilderTest.documentXml(out).contains(FALLBACK));
        written(out);
    }

    // ────────────────────────────── helpers ──────────────────────────────

    private static Configure plugin() {
        return Configure.builder().addPlugin('$', new FormulaRenderPolicy()).build();
    }

    private static Configure bound(String tag) {
        return Configure.builder().bind(tag, new FormulaRenderPolicy()).build();
    }

    private static XWPFDocument paragraphTemplate(String... runs) {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        for (String run : runs) {
            paragraph.createRun().setText(run);
        }
        return document;
    }

    private static Map<String, Object> data(String key, Object value) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(key, value);
        return data;
    }

    private static Map<String, Object> data(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> data = data(key1, value1);
        data.put(key2, value2);
        return data;
    }

    private static byte[] render(XWPFDocument template, Configure configure, Map<String, Object> data)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (XWPFTemplate compiled = XWPFTemplate.compile(template, configure)) {
            compiled.render(data, out);
        }
        return out.toByteArray();
    }

    /** Reopens the result to prove the package is still valid. */
    private static String written(byte[] docx) throws IOException {
        try (XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(docx))) {
            StringBuilder text = new StringBuilder();
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                text.append(paragraph.getText());
            }
            return text.toString();
        }
    }

    private static int count(String text, String needle) {
        int found = 0;
        int index = 0;
        while ((index = text.indexOf(needle, index)) >= 0) {
            found++;
            index += needle.length();
        }
        return found;
    }

}
