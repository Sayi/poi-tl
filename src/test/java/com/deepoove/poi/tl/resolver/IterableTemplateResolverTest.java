package com.deepoove.poi.tl.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.run.RunTemplate;

@DisplayName("Iterable template resolver test case")
public class IterableTemplateResolverTest {

    @Test
    public void testIfResolver() throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_if1.docx");
        List<MetaTemplate> elementTemplates = template.getElementTemplates();

        List<MetaTemplate> inparagraph = elementTemplates.subList(0, 2);
        List<MetaTemplate> intable = elementTemplates.subList(2, 4);
        List<MetaTemplate> inheader = elementTemplates.subList(4, 6);
        testResolvedMetaTemplate(inparagraph);
        testResolvedMetaTemplate(intable);
        testResolvedMetaTemplate(inheader);

    }

    private void testResolvedMetaTemplate(List<MetaTemplate> elementTemplates) {
        assertTrue(elementTemplates.get(0) instanceof InlineIterableTemplate);
        assertEquals(elementTemplates.get(0).variable(), "{{?isShowTitle}}");
        assertEquals(((IterableTemplate) elementTemplates.get(0)).getTemplates().get(0).variable(), "{{title}}");

        assertTrue(elementTemplates.get(1) instanceof IterableTemplate);
        IterableTemplate iterable = (IterableTemplate) elementTemplates.get(1);
        assertEquals(iterable.variable(), "{{?showUser}}");

        List<MetaTemplate> iterableTemplates = iterable.getTemplates();
        assertTrue(iterableTemplates.get(0) instanceof RunTemplate);
        assertEquals(iterableTemplates.get(0).variable(), "{{user}}");

        assertTrue(iterableTemplates.get(1) instanceof InlineIterableTemplate);
        assertEquals(iterableTemplates.get(1).variable(), "{{?showDate}}");
        assertEquals(((IterableTemplate) iterableTemplates.get(1)).getTemplates().get(0).variable(), "{{date}}");

        assertTrue(iterableTemplates.get(2) instanceof IterableTemplate);
        assertEquals(iterableTemplates.get(2).variable(), "{{?showDate}}");
        assertEquals(((IterableTemplate) iterableTemplates.get(2)).getTemplates().get(0).variable(), "{{date}}");

        assertTrue(iterableTemplates.get(3) instanceof InlineIterableTemplate);
        assertEquals(iterableTemplates.get(3).variable(), "{{?showDate}}");
        assertEquals(((IterableTemplate) iterableTemplates.get(3)).getTemplates().get(0).variable(), "{{date}}");
    }

}
