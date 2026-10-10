package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.plugin.bookmark.BookmarkRenderPolicy;
import com.deepoove.poi.plugin.pagination.PaginationRenderPolicy;

@DisplayName("Configure and plugin render behavior")
public class ConfigureBehaviorTest {

    @Test
    public void discardHandlerKeepsTheUnresolvedTag() throws Exception {
        Configure configure = Configure.builder().setValidErrorHandler(new Configure.DiscardHandler()).build();
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("Hello {{name}}"), configure,
                new HashMap<String, Object>());

        assertEquals("Hello {{name}}", document.getParagraphs().get(0).getText());
        document.close();
    }

    @Test
    public void abortHandlerThrowsWhenTheValueIsMissing() {
        Configure configure = Configure.builder().setValidErrorHandler(new Configure.AbortHandler()).build();
        RenderException error = assertThrows(RenderException.class, () -> RenderAsserts.render(
                RenderAsserts.paragraph("{{name}}"), configure, new HashMap<String, Object>()));
        assertTrue(error.getMessage().contains("{{name}}"));
    }

    @Test
    public void strictElThrowsForAMissingBeanProperty() {
        Configure configure = Configure.builder().useDefaultEL(true).build();
        RenderException error = assertThrows(RenderException.class,
                () -> RenderAsserts.render(RenderAsserts.paragraph("{{missing}}"), configure, new User()));
        assertTrue(error.getCause() != null || error.getMessage() != null);
    }

    @Test
    public void lenientElClearsAMissingBeanProperty() throws Exception {
        Configure configure = Configure.builder().useDefaultEL(false).build();
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{missing}}"), configure, new User());

        assertEquals("", RenderAsserts.paragraphText(document));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void truePaginationInsertsAPageBreak() throws Exception {
        Configure configure = Configure.builder().bind("page", new PaginationRenderPolicy()).build();
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("page1");
        template.createParagraph().createRun().setText("{{page}}");
        template.createParagraph().createRun().setText("page2");

        XWPFDocument document = RenderAsserts.render(template, configure, RenderAsserts.mapOf("page", true));
        assertEquals("page1", document.getParagraphArray(0).getText());
        assertEquals("page2", document.getParagraphArray(2).getText());
        assertTrue(document.getParagraphArray(1).getCTP().xmlText().contains("w:type=\"page\"")
                || document.getParagraphArray(1).getRuns().get(0).getCTR().xmlText().contains("w:type=\"page\""));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void falsePaginationDoesNotInsertAPageBreak() throws Exception {
        Configure configure = Configure.builder().bind("page", new PaginationRenderPolicy()).build();
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("{{page}}");
        template.createParagraph().createRun().setText("next");

        XWPFDocument document = RenderAsserts.render(template, configure, RenderAsserts.mapOf("page", false));
        assertEquals("", document.getParagraphArray(0).getText());
        assertEquals("next", document.getParagraphArray(1).getText());
        assertTrue(!document.getDocument().xmlText().contains("w:type=\"page\""));
        document.close();
    }

    @Test
    public void bookmarkPluginNamesTheBookmarkAfterTheRenderedText() throws Exception {
        Configure configure = Configure.builder().bind("anchor", new BookmarkRenderPolicy()).build();
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{anchor}}"), configure,
                RenderAsserts.mapOf("anchor", "章节"));

        assertEquals("章节", document.getParagraphs().get(0).getText());
        assertEquals("章节", document.getParagraphs().get(0).getCTP().getBookmarkStartArray(0).getName());
        document.close();
    }

    public static class User {
        public String getName() {
            return "Sayi";
        }
    }

}
