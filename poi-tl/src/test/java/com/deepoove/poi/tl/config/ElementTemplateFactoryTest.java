package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.resolver.DefaultElementTemplateFactory;
import com.deepoove.poi.resolver.ElementTemplateFactory;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.tl.source.XWPFTestSupport;
import com.deepoove.poi.util.RegexUtils;

public class ElementTemplateFactoryTest {

    @Test
    public void testConfigRenderDataCompute() throws IOException {

        ElementTemplateFactory elementTemplateFactory = new DefaultElementTemplateFactory() {

            @Override
            public RunTemplate createRunTemplate(Configure config, String tag, XWPFRun run) {
                RunTemplate runTemplate = super.createRunTemplate(config, tag, run);
                System.out.println(tag);
                int lastIndexOf = tag.lastIndexOf("?");
                if (-1 != lastIndexOf) {
                    runTemplate.setTagName(tag.substring(0, lastIndexOf));
                }
                return runTemplate;
            }
        };
        Configure config = Configure.builder().buildGrammerRegex(RegexUtils.createGeneral("{{", "}}"))
                .setElementTemplateFactory(elementTemplateFactory).build();

        XWPFDocument doc = new XWPFDocument();
        doc.createParagraph().createRun().setText("{{name?a=1&b=2}}");
        InputStream inputStream = XWPFTestSupport.readInputStream(doc);
        XWPFTemplate template = XWPFTemplate.compile(inputStream, config)
                .render(Collections.singletonMap("name", "123"));
        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        assertEquals("123", document.getParagraphArray(0).getText());

    }

}
