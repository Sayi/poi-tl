package com.deepoove.poi.tl.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.template.MetaTemplate;

@DisplayName("Running run paragraph resolver test case")
public class RunningRunParagraphTest {

    @Test
    public void testDefaultGramer() throws IOException {

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/resolver_running_run.docx");
        List<MetaTemplate> elementTemplates = template.getElementTemplates();

        assertEquals(elementTemplates.size(), 22);

        int i = 0;
        assertEquals(elementTemplates.get(i++).variable(), "{{@icon}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{author}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{date}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{dfa}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{fafd}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{author}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{madan}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{aad}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{date}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{ada}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{faf}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{dfa}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{dfdf}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{fafd}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{adaf}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{ada}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{yi}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{dafd}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{dfas}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{@af}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{table0}}");
        assertEquals(elementTemplates.get(i++).variable(), "{{table}}");

        template.close();
    }

    @Test
    public void testCustomGramer() throws IOException {

        Configure config = Configure.builder().buildGramer("${", "}").build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/resolver_running_run_custom.docx",
                config);
        List<MetaTemplate> elementTemplates = template.getElementTemplates();

        assertEquals(elementTemplates.size(), 22);

        int i = 0;
        assertEquals(elementTemplates.get(i++).variable(), "${@icon}");
        assertEquals(elementTemplates.get(i++).variable(), "${author}");
        assertEquals(elementTemplates.get(i++).variable(), "${date}");
        assertEquals(elementTemplates.get(i++).variable(), "${dfa}");
        assertEquals(elementTemplates.get(i++).variable(), "${fafd}");
        assertEquals(elementTemplates.get(i++).variable(), "${author}");
        assertEquals(elementTemplates.get(i++).variable(), "${madan}");
        assertEquals(elementTemplates.get(i++).variable(), "${aad}");
        assertEquals(elementTemplates.get(i++).variable(), "${date}");
        assertEquals(elementTemplates.get(i++).variable(), "${ada}");
        assertEquals(elementTemplates.get(i++).variable(), "${faf}");
        assertEquals(elementTemplates.get(i++).variable(), "${dfa}");
        assertEquals(elementTemplates.get(i++).variable(), "${dfdf}");
        assertEquals(elementTemplates.get(i++).variable(), "${fafd}");
        assertEquals(elementTemplates.get(i++).variable(), "${adaf}");
        assertEquals(elementTemplates.get(i++).variable(), "${ada}");
        assertEquals(elementTemplates.get(i++).variable(), "${yi}");
        assertEquals(elementTemplates.get(i++).variable(), "${dafd}");
        assertEquals(elementTemplates.get(i++).variable(), "${dfas}");
        assertEquals(elementTemplates.get(i++).variable(), "${@af}");
        assertEquals(elementTemplates.get(i++).variable(), "${table0}");
        assertEquals(elementTemplates.get(i++).variable(), "${table}");

        template.close();
    }

}
