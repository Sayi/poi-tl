package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("If and foreach block behavior")
public class BlockRenderBehaviorTest {

    @Test
    public void dropsTheBlockWhenIfIsFalse() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("前");
        template.createParagraph().createRun().setText("{{?show}}隐藏{{/show}}");
        template.createParagraph().createRun().setText("后");

        XWPFDocument document = RenderAsserts.render(template, RenderAsserts.mapOf("show", false));

        assertEquals(2, document.getParagraphs().size());
        assertEquals("前", document.getParagraphArray(0).getText());
        assertEquals("后", document.getParagraphArray(1).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void keepsAndRendersTheBlockWhenIfIsTrue() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("{{?show}}Hello {{name}}{{/show}}");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("show", true);
        model.put("name", "poi-tl");
        XWPFDocument document = RenderAsserts.render(template, model);

        assertEquals("Hello poi-tl", document.getParagraphArray(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void nestedFalseIfKeepsOnlyTheOuterText() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("{{?outer}}外{{?inner}}内{{/inner}}{{/outer}}");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("outer", true);
        model.put("inner", false);
        XWPFDocument document = RenderAsserts.render(template, model);

        assertEquals("外", document.getParagraphArray(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void repeatsEachItemInOrder() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("{{?items}}{{name}}{{/items}}");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", Arrays.asList(named("甲"), named("乙"), named("丙")));
        XWPFDocument document = RenderAsserts.render(template, model);

        assertEquals("甲乙丙", RenderAsserts.paragraphText(document).replace("\n", ""));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void removesTheBlockWhenTheIterableIsEmpty() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("头{{?items}}{{name}}{{/items}}尾");

        XWPFDocument document = RenderAsserts.render(template, RenderAsserts.mapOf("items", Collections.emptyList()));

        assertEquals("头尾", document.getParagraphArray(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void falseIfInsideACellDropsOnlyThatRowContent() throws Exception {
        XWPFDocument template = new XWPFDocument();
        XWPFTable table = template.createTable(2, 1);
        table.getRow(0).getCell(0).setText("保留");
        RenderAsserts.setCellText(table.getRow(1).getCell(0), "{{?show}}隐藏{{/show}}");

        XWPFDocument document = RenderAsserts.render(template, RenderAsserts.mapOf("show", false));
        XWPFTable out = document.getTables().get(0);
        assertEquals("保留", RenderAsserts.cellText(out, 0, 0));
        assertFalse(out.getRow(1).getCell(0).getText().contains("隐藏"));
        RenderAsserts.assertNoTags(document);
        RenderAsserts.assertEveryCellHasParagraph(document);
        document.close();
    }

    private static Map<String, Object> named(String name) {
        return RenderAsserts.mapOf("name", name);
    }

}
