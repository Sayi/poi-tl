package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Iterable environment variables")
public class IterableEnvBehaviorTest {

    @Test
    public void exposesIndexAndFirstLastFlags() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun()
                .setText("{{?items}}{{_index}}-{{name}}-{{_is_first}}-{{_is_last}};{{/items}}");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", Arrays.asList(named("甲"), named("乙")));
        XWPFDocument document = RenderAsserts.render(template, model);

        String text = RenderAsserts.paragraphText(document).replace("\n", "");
        assertEquals("0-甲-true-false;1-乙-false-true;", text);
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void stringItemExposesIndexButNotTheStringItself() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("{{?items}}{{_index}}:{{#this}};{{/items}}");

        XWPFDocument document = RenderAsserts.render(template, RenderAsserts.mapOf("items", Arrays.asList("甲", "乙")));
        String text = RenderAsserts.paragraphText(document).replace("\n", "");
        assertEquals("0:;1:;", text);
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void nullIterableDropsTheBlock() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("头{{?items}}不会出现{{/items}}尾");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", null);
        XWPFDocument document = RenderAsserts.render(template, model);
        assertEquals("头尾", document.getParagraphs().get(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    private static Map<String, Object> named(String name) {
        return RenderAsserts.mapOf("name", name);
    }

}
