package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.data.Includes;

@DisplayName("Nested docx render behavior")
public class DocxIncludeBehaviorTest {

    @Test
    public void mergesSubdocumentTextInPlaceOfTheTag() throws Exception {
        XWPFDocument sub = RenderAsserts.paragraph("来自子文档");
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("主{{+sub}}文档"),
                RenderAsserts.mapOf("sub", Includes.ofBytes(RenderAsserts.toBytes(sub)).create()));

        assertTrue(RenderAsserts.paragraphText(document).contains("来自子文档"));
        assertTrue(RenderAsserts.paragraphText(document).contains("主"));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void emptySubdocumentKeepsTheSurroundingText() throws Exception {
        XWPFDocument sub = new XWPFDocument();
        sub.createParagraph();
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("主{{+sub}}尾"),
                RenderAsserts.mapOf("sub", Includes.ofBytes(RenderAsserts.toBytes(sub)).create()));

        String text = RenderAsserts.paragraphText(document).replace("\n", "");
        assertEquals("主尾", text);
        RenderAsserts.assertNoTags(document);
        document.close();
    }

}
