package com.deepoove.poi.tl.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.resolver.RunningRunParagraph;
import com.deepoove.poi.resolver.TemplateResolver;
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

    @Test
    public void testCRBR() throws IOException {
        TemplateResolver templateResolver = new TemplateResolver(Configure.createDefault());
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun createRun = paragraph.createRun();
        createRun.setText("{{Hi}}", 0);
        createRun.addBreak();
        createRun.addCarriageReturn();
        createRun.setText("{{world}}2", 1);
        assertEquals(1, paragraph.getRuns().size());
        String text = paragraph.getParagraphText();

        new RunningRunParagraph(paragraph, templateResolver.getTemplatePattern()).refactorRun();
        assertEquals(text, paragraph.getParagraphText());
        assertEquals(5, paragraph.getRuns().size());
        assertEquals("{{Hi}}", paragraph.getRuns().get(0).getText(0));
        assertEquals("\n", paragraph.getRuns().get(1).toString());
        assertEquals("\n", paragraph.getRuns().get(2).toString());
        assertEquals("{{world}}", paragraph.getRuns().get(3).getText(0));
        assertEquals("2", paragraph.getRuns().get(4).getText(0));

        doc.close();

    }

    @Test
    public void testInsertRun() throws IOException {
        TemplateResolver templateResolver = new TemplateResolver(Configure.createDefault());
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun createRun = paragraph.createRun();
        createRun.setText("{{Hi}}AA", 0);
        XWPFHyperlinkRun hyperlinkRun = paragraph.createHyperlinkRun("http://deepoove.com");
        hyperlinkRun.setText("http://deepoove.com");

        assertEquals(2, paragraph.getRuns().size());

        new RunningRunParagraph(paragraph, templateResolver.getTemplatePattern()).refactorRun();
        assertEquals(3, paragraph.getRuns().size());
        assertEquals("{{Hi}}", paragraph.getRuns().get(0).getText(0));
        assertEquals("AA", paragraph.getRuns().get(1).toString());
        assertEquals("http://deepoove.com", paragraph.getRuns().get(2).toString());
        CTP ctp = paragraph.getCTP();
        XWPFParagraph xwpfParagraph = new XWPFParagraph(ctp, paragraph.getBody());
        assertEquals(3, xwpfParagraph.getRuns().size());
        assertEquals("{{Hi}}", xwpfParagraph.getRuns().get(0).getText(0));
        assertEquals("AA", xwpfParagraph.getRuns().get(1).toString());
        assertEquals("http://deepoove.com", xwpfParagraph.getRuns().get(2).toString());

        doc.close();

    }

}
