package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.GrammarSymbol;
import com.deepoove.poi.template.ElementTemplate;

@DisplayName("Template resolver behavior")
public class ResolverBehaviorTest {

    @Test
    public void parsesTwoTagsInOneParagraph() throws Exception {
        List<ElementTemplate> elements = RenderAsserts.elementTemplates(RenderAsserts.paragraph("{{a}}{{b}}"));

        assertEquals(2, elements.size());
        assertEquals("a", elements.get(0).getTagName());
        assertEquals("b", elements.get(1).getTagName());
        assertEquals(Character.valueOf(GrammarSymbol.TEXT.getSymbol()), elements.get(0).getSign());
    }

    @Test
    public void parsesPictureAndTableSigns() throws Exception {
        List<ElementTemplate> elements = RenderAsserts.elementTemplates(RenderAsserts.paragraph("{{@img}}{{#grid}}"));

        assertEquals(2, elements.size());
        assertEquals(Character.valueOf(GrammarSymbol.IMAGE.getSymbol()), elements.get(0).getSign());
        assertEquals("img", elements.get(0).getTagName());
        assertEquals(Character.valueOf(GrammarSymbol.TABLE.getSymbol()), elements.get(1).getSign());
        assertEquals("grid", elements.get(1).getTagName());
    }

    @Test
    public void customDelimitersAreParsedAndRendered() throws Exception {
        Configure configure = Configure.builder().buildGrammar("${", "}").build();
        XWPFDocument template = RenderAsserts.paragraph("${name}");

        List<ElementTemplate> elements = RenderAsserts.elementTemplates(template, configure);
        assertEquals(1, elements.size());
        assertEquals("name", elements.get(0).getTagName());

        XWPFDocument again = RenderAsserts.paragraph("${name}");
        XWPFDocument document = RenderAsserts.render(again, configure, RenderAsserts.mapOf("name", "poi-tl"));
        assertEquals("poi-tl", document.getParagraphs().get(0).getText());
        RenderAsserts.assertNoTags(document, "${", "}");
        document.close();
    }

    @Test
    public void keepsLiteralTextAroundAResolvedTag() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("前{{name}}后"),
                RenderAsserts.mapOf("name", "中"));
        assertEquals("前中后", document.getParagraphs().get(0).getText());
        document.close();
    }

    @Test
    public void emptyRunBesideATagDoesNotShiftTheMatch() throws Exception {
        XWPFDocument template = new XWPFDocument();
        XWPFParagraph paragraph = template.createParagraph();
        paragraph.createRun().setText("");
        paragraph.createRun().setText("{{title}}");
        paragraph.createRun();

        List<ElementTemplate> elements = RenderAsserts.elementTemplates(template);
        assertEquals(1, elements.size());
        assertEquals("title", elements.get(0).getTagName());

        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("", "{{title}}"),
                RenderAsserts.mapOf("title", "你好"));
        assertEquals("你好", document.getParagraphs().get(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

}
