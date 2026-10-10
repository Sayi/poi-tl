package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;

import com.deepoove.poi.data.Texts;

@DisplayName("Inline text style behavior")
public class TextStyleBehaviorTest {

    @Test
    public void writesItalicSuperscriptAndFontSize() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{word}}"),
                RenderAsserts.mapOf("word", Texts.of("x2").italic().sup().fontSize(18).create()));

        XWPFRun run = document.getParagraphs().get(0).getRuns().get(0);
        assertEquals("x2", run.text());
        assertTrue(run.isItalic());
        assertEquals("superscript", run.getVerticalAlignment().toString());
        assertEquals(18, run.getFontSize());
        document.close();
    }

    @Test
    public void writesSubscript() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("H{{sub}}O"),
                RenderAsserts.mapOf("sub", Texts.of("2").sub().create()));

        XWPFRun run = findRun(document.getParagraphs().get(0), "2");
        assertEquals("subscript", run.getVerticalAlignment().toString());
        assertEquals("H2O", document.getParagraphs().get(0).getText());
        document.close();
    }

    @Test
    public void mailtoUsesAMailtoHyperlink() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{mail}}"),
                RenderAsserts.mapOf("mail", Texts.of("作者").mailto("a@deepoove.com", "poi-tl").create()));

        XWPFParagraph paragraph = document.getParagraphs().get(0);
        XWPFHyperlinkRun link = null;
        for (XWPFRun run : paragraph.getRuns()) {
            if (run instanceof XWPFHyperlinkRun) {
                link = (XWPFHyperlinkRun) run;
            }
        }
        assertEquals("作者", link.text());
        assertEquals("mailto:a@deepoove.com?subject=poi-tl", link.getHyperlink(document).getURL());
        assertEquals(UnderlinePatterns.SINGLE, link.getUnderline());
        document.close();
    }

    @Test
    public void bookmarkTextCreatesANamedBookmark() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{mark}}"),
                RenderAsserts.mapOf("mark", Texts.of("目录").bookmark("toc").create()));

        XWPFParagraph paragraph = document.getParagraphs().get(0);
        assertEquals("目录", paragraph.getText());
        assertEquals(1, paragraph.getCTP().sizeOfBookmarkStartArray());
        CTBookmark bookmark = paragraph.getCTP().getBookmarkStartArray(0);
        assertEquals("toc", bookmark.getName());
        document.close();
    }

    private static XWPFRun findRun(XWPFParagraph paragraph, String text) {
        for (XWPFRun run : paragraph.getRuns()) {
            if (text.equals(run.text())) {
                return run;
            }
        }
        throw new AssertionError("run not found: " + text);
    }

}
