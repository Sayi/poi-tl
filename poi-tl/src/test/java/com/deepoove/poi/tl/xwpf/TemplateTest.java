package com.deepoove.poi.tl.xwpf;

import java.io.IOException;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.style.Style;

public class TemplateTest {

    @Test
    public void testCreateDocument() throws IOException {
        DocumentRenderData data = Documents.of().addParagraph(Paragraphs.of("this a paragraph").create()).create();
        XWPFTemplate.create()
                .render(Collections.singletonMap(XWPFTemplate.TEMPLATE_TAG_NAME, data))
                .writeToFile("out_generate.docx");

        XWPFTemplate.create(Style.builder().buildFontFamily("微软雅黑").buildFontSize(18f).build())
                .render(Collections.singletonMap(XWPFTemplate.TEMPLATE_TAG_NAME, data))
                .writeToFile("out_generate_style.docx");
    }

}
