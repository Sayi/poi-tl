package com.deepoove.poi.tl.xwpf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.tl.source.XWPFTestSupport;

public class TemplateTest {

    @Test
    public void testCreateDocument() throws IOException {
        String text = "this a paragraph";
        DocumentRenderData data = Documents.of().addParagraph(Paragraphs.of(text).create()).create();
        XWPFTemplate template = XWPFTemplate.create(data);

        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        XWPFParagraph paragraph = document.getParagraphArray(0);
        assertNull(paragraph.getRuns().get(0).getFontFamily());
        assertEquals(text, paragraph.getText());

        template = XWPFTemplate.create(data, Style.builder().buildFontFamily("微软雅黑").buildFontSize(18f).build());

        document = XWPFTestSupport.readNewDocument(template);
        paragraph = document.getParagraphArray(0);
        assertEquals("微软雅黑", paragraph.getRuns().get(0).getFontFamily());
        assertEquals(text, paragraph.getText());
    }

}
