package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.data.Texts;

@DisplayName("Text render behavior")
public class TextRenderBehaviorTest {

    @Test
    public void replacesPlainTextAndDropsTheTag() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("Hello {{name}}"),
                RenderAsserts.mapOf("name", "poi-tl"));

        assertEquals("Hello poi-tl", document.getParagraphs().get(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void clearsMissingValueByDefault() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("Hello {{name}}!"),
                new HashMap<String, Object>());

        assertEquals("Hello !", document.getParagraphs().get(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void rendersEmptyStringWithoutLeavingTheTag() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("A{{name}}B"),
                RenderAsserts.mapOf("name", ""));

        assertEquals("AB", document.getParagraphs().get(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void splitsNewlinesIntoCarriageReturns() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{text}}"),
                RenderAsserts.mapOf("text", "hi\nhello"));

        XWPFParagraph paragraph = document.getParagraphs().get(0);
        assertEquals("hi\nhello", paragraph.getText());
        assertTrue(paragraph.getRuns().get(0).getCTR().sizeOfCrArray() >= 1
                || paragraph.getRuns().get(0).getCTR().xmlText().contains("<w:cr"));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void appliesBoldAndColorOnTheRun() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{word}}"),
                RenderAsserts.mapOf("word", Texts.of("deepoove").bold().color("FF0000").create()));

        XWPFRun run = document.getParagraphs().get(0).getRuns().get(0);
        assertEquals("deepoove", run.text());
        assertTrue(run.isBold());
        assertEquals("FF0000", RenderAsserts.runColor(run));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void writesHyperlinkTextAndUrl() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("see {{link}}"),
                RenderAsserts.mapOf("link", Texts.of("site").link("http://deepoove.com").create()));

        XWPFParagraph paragraph = document.getParagraphs().get(0);
        assertEquals("see site", paragraph.getText());
        List<XWPFHyperlinkRun> links = new java.util.ArrayList<XWPFHyperlinkRun>();
        for (XWPFRun run : paragraph.getRuns()) {
            if (run instanceof XWPFHyperlinkRun) {
                links.add((XWPFHyperlinkRun) run);
            }
        }
        assertEquals(1, links.size());
        assertEquals("site", links.get(0).text());
        assertEquals("http://deepoove.com", links.get(0).getHyperlink(document).getURL());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void joinsATagSplitAcrossRuns() throws Exception {
        Map<String, Object> model = RenderAsserts.mapOf("name", "poi-tl");
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{na", "me}}"), model);

        assertEquals("poi-tl", document.getParagraphs().get(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void keepsTextAroundATag() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("前{{name}}后"),
                RenderAsserts.mapOf("name", "中"));

        assertEquals("前中后", document.getParagraphs().get(0).getText());
        document.close();
    }

}
