package com.deepoove.poi.tl.plugin;

import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.pagination.PaginationRenderPolicy;

public class PaginationRenderPolicyTest {

    @SuppressWarnings("serial")
    @Test
    public void testPagination() throws IOException {
        XWPFDocument doc = new XWPFDocument();
        doc.createParagraph().createRun().setText("page1");
        doc.createParagraph().createRun().setText("{{page}}");
        doc.createParagraph().createRun().setText("page2");

        Configure configure = Configure.builder().bind("page", new PaginationRenderPolicy()).build();
        XWPFTemplate.compile(doc, configure).render(new HashMap<String, Object>() {
            {
                put("page", true);
            }
        }).writeToFile("out_pagination.docx");
        ;
    }

}
