package com.deepoove.poi.tl.plugin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.pagination.PaginationRenderPolicy;
import com.deepoove.poi.tl.source.XWPFTestSupport;

public class PaginationRenderPolicyTest {

    @SuppressWarnings("serial")
    @Test
    public void testPagination() throws IOException {
        XWPFDocument doc = new XWPFDocument();
        doc.createParagraph().createRun().setText("page1");
        doc.createParagraph().createRun().setText("{{page}}");
        doc.createParagraph().createRun().setText("page2");

        Configure configure = Configure.builder().bind("page", new PaginationRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(doc, configure).render(new HashMap<String, Object>() {
            {
                put("page", true);
            }
        });

        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        XWPFParagraph paragraph = document.getParagraphArray(0);
        assertEquals("page1", paragraph.getParagraphText());
        XWPFParagraph paragraph1 = document.getParagraphArray(1);
        assertEquals("\n", paragraph1.getRuns().get(0).text());
        XWPFParagraph paragraph2 = document.getParagraphArray(2);
        assertEquals("page2", paragraph2.getParagraphText());
    }

}
