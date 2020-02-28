package com.deepoove.poi.tl.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.run.RunTemplate;

@DisplayName("Running run paragraph resolver test case")
public class RunningRunParagraphTest {

    @Test
    public void testDefaultGramer() throws IOException {

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/resolver_running_run.docx");
        List<MetaTemplate> elementTemplates = template.getElementTemplates();

        assertEquals(elementTemplates.size(), 22);

        int i = 0;
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{@icon}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{author}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{date}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{dfa}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{fafd}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{author}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{madan}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{aad}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{date}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{ada}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{faf}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{dfa}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{dfdf}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{fafd}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{adaf}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{ada}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{yi}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{dafd}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{dfas}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{@af}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{table0}}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "{{table}}");
        
        template.close();
    }
    @Test
    public void testCustomGramer() throws IOException {
        
        Configure config = Configure.newBuilder().buildGramer("${", "}").build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/resolver_running_run_custom.docx", config);
        List<MetaTemplate> elementTemplates = template.getElementTemplates();
        
        assertEquals(elementTemplates.size(), 22);
        
        int i = 0;
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${@icon}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${author}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${date}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${dfa}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${fafd}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${author}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${madan}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${aad}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${date}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${ada}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${faf}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${dfa}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${dfdf}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${fafd}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${adaf}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${ada}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${yi}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${dafd}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${dfas}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${@af}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${table0}");
        assertEquals(((RunTemplate) elementTemplates.get(i++)).getSource(), "${table}");
        
        template.close();
    }

}
