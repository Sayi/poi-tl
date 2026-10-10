package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.data.Numberings;

@DisplayName("Numbering render behavior")
public class NumberingRenderBehaviorTest {

    @Test
    public void rendersEachItemWithTheSameNumberingId() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{*list}}"),
                RenderAsserts.mapOf("list", Numberings.ofDecimal("甲", "乙", "丙").create()));

        List<XWPFParagraph> numbered = numberedParagraphs(document);
        assertEquals(3, numbered.size());
        assertEquals("甲", numbered.get(0).getText());
        assertEquals("乙", numbered.get(1).getText());
        assertEquals("丙", numbered.get(2).getText());
        assertNotNull(numbered.get(0).getNumID());
        assertEquals(numbered.get(0).getNumID(), numbered.get(1).getNumID());
        assertEquals(numbered.get(0).getNumID(), numbered.get(2).getNumID());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void acceptsAPlainStringList() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{*list}}"),
                RenderAsserts.mapOf("list", Arrays.asList("A", "B", "C")));

        List<XWPFParagraph> numbered = numberedParagraphs(document);
        assertEquals(Arrays.asList("A", "B", "C"), texts(numbered));
        assertTrue(document.getNumbering() != null);
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void clearsTheTagWhenTheListIsEmpty() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{*list}}"),
                RenderAsserts.mapOf("list", Numberings.ofBullet().create()));

        assertEquals("", RenderAsserts.paragraphText(document).trim());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    private static List<XWPFParagraph> numberedParagraphs(XWPFDocument document) {
        List<XWPFParagraph> numbered = new ArrayList<XWPFParagraph>();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (paragraph.getNumID() != null) {
                numbered.add(paragraph);
            }
        }
        return numbered;
    }

    private static List<String> texts(List<XWPFParagraph> paragraphs) {
        List<String> texts = new ArrayList<String>();
        for (XWPFParagraph paragraph : paragraphs) {
            texts.add(paragraph.getText());
        }
        return texts;
    }

}
