package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.Configure.ValidErrorHandler;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.tl.source.XWPFTestSupport;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

public class Issue687 {

    @SuppressWarnings("serial")
    @Test
    public void test687() throws Exception {

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/687.docx")
                .render(new HashMap<String, Object>() {
                });

        XWPFDocument newDocument = XWPFTestSupport.readNewDocument(template);
        assertEquals(1, newDocument.getParagraphs().size());
        
        ConfigureBuilder builder = Configure.builder();
        builder.setValidErrorHandler(new ValidErrorHandler() {
            
            @Override
            public void handler(RenderContext<?> context) {
                XWPFRun run = context.getRun();
                BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
                bodyContainer.clearPlaceholder(run);
            }
        });

    }

}
