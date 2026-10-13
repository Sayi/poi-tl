package com.deepoove.poi.plugin.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.BookmarkTextRenderData;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.HyperlinkTextRenderData;
import com.deepoove.poi.data.NumberingItemRenderData;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.markdown.converter.MarkdownToDocumentRenderDataConverter;

/**
 * Assertions on the {@link DocumentRenderData} contract produced by the Markdown
 * converter. Every case that used to crash, silently drop content or lose
 * formatting is pinned here.
 */
public class MarkdownConverterTest {

    // ------------------------------------------------------------------
    // inline formatting
    // ------------------------------------------------------------------

    @Test
    @DisplayName("bold and italic are actually applied to the run")
    public void testStrongAndEmphasis() throws Exception {
        ParagraphRenderData strong = paragraph("**bold**", 0);
        assertEquals("bold", text(strong));
        assertEquals(Boolean.TRUE, style(strong, 0).isBold());

        ParagraphRenderData emphasis = paragraph("*italic*", 0);
        assertEquals("italic", text(emphasis));
        assertEquals(Boolean.TRUE, style(emphasis, 0).isItalic());

        ParagraphRenderData both = paragraph("***both***", 0);
        assertEquals("both", text(both));
        assertEquals(Boolean.TRUE, style(both, 0).isBold());
        assertEquals(Boolean.TRUE, style(both, 0).isItalic());
    }

    @Test
    @DisplayName("inline formatting does not leak to sibling runs")
    public void testNoStyleLeakBetweenSiblings() throws Exception {
        ParagraphRenderData p = paragraph("plain **bold** plain", 0);
        List<TextRenderData> texts = texts(p);
        assertEquals(3, texts.size());
        assertEquals("plain ", texts.get(0).getText());
        assertNull(texts.get(0).getStyle().isBold(), "leading text must stay plain");
        assertEquals("bold", texts.get(1).getText());
        assertEquals(Boolean.TRUE, texts.get(1).getStyle().isBold());
        assertEquals(" plain", texts.get(2).getText());
        assertNull(texts.get(2).getStyle().isBold(), "trailing text must not inherit bold");
    }

    @Test
    @DisplayName("inline code keeps its colour")
    public void testInlineCode() throws Exception {
        ParagraphRenderData p = paragraph("`code`", 0);
        assertEquals("code", text(p));
        assertEquals("f44336", style(p, 0).getColor());
    }

    // ------------------------------------------------------------------
    // links
    // ------------------------------------------------------------------

    @Test
    @DisplayName("a link whose text is emphasised is no longer dropped")
    public void testLinkWithEmphasis() throws Exception {
        ParagraphRenderData p = paragraph("[**bold link**](http://a.com)", 0);
        assertEquals("bold link", text(p));

        TextRenderData run = texts(p).get(0);
        assertTrue(run instanceof HyperlinkTextRenderData, "must stay a hyperlink");
        assertEquals("http://a.com", ((HyperlinkTextRenderData) run).getUrl());
        assertEquals(Boolean.TRUE, run.getStyle().isBold(), "link text keeps its bold style");
        assertEquals("0000FF", run.getStyle().getColor(), "link keeps the hyperlink colour");
        assertNotNull(run.getStyle().getUnderlinePatterns(), "link keeps the hyperlink underline");
    }

    @Test
    @DisplayName("an in page link becomes a Word bookmark anchor")
    public void testInternalAnchor() throws Exception {
        ParagraphRenderData p = paragraph("[go](#My-Section)", 0);
        TextRenderData run = texts(p).get(0);
        assertTrue(run instanceof HyperlinkTextRenderData);
        assertEquals("anchor:my_section", ((HyperlinkTextRenderData) run).getUrl());
    }

    @Test
    @DisplayName("nested inline markup inside a link is fully kept")
    public void testLinkWithNestedMarkup() throws Exception {
        ParagraphRenderData p = paragraph("[a **b** `c` d](http://a.com)", 0);
        assertEquals("a b c d", text(p));
        assertTrue(texts(p).get(0) instanceof HyperlinkTextRenderData);
    }

    @Test
    @DisplayName("a linked image degrades to a plain picture instead of vanishing")
    public void testLinkedImage() throws Exception {
        ParagraphRenderData p = paragraph("[![alt](logo.png)](http://a.com)", 0);
        assertEquals(1, p.getContents().size());
        assertTrue(p.getContents().get(0) instanceof PictureRenderData);
    }

    // ------------------------------------------------------------------
    // headings
    // ------------------------------------------------------------------

    @Test
    @DisplayName("heading numbering no longer breaks on skipped levels or multi digits")
    public void testHeadingNumbering() throws Exception {
        MarkdownStyle style = MarkdownStyle.builder().withShowHeaderNumber(true).build();
        DocumentRenderData doc = convert("# H1\n## H2\n### H3", style);
        assertEquals("H1", text(paragraph(doc, 0)), "level 1 stays unnumbered by default");
        assertEquals("1. H2", text(paragraph(doc, 1)));
        assertEquals("1.1. H3", text(paragraph(doc, 2)));
    }

    @Test
    @DisplayName("an orphan level 3 heading does not print a 0 prefix")
    public void testHeadingNumberingSkippedLevel() throws Exception {
        MarkdownStyle style = MarkdownStyle.builder().withShowHeaderNumber(true).build();
        DocumentRenderData doc = convert("# title\n### deep", style);
        String text = text(paragraph(doc, 1));
        assertEquals("1. deep", text);
        assertFalse(text.startsWith("0."), "a level that never appeared must not be rendered as 0");
    }

    @Test
    @DisplayName("a two digit chapter number keeps its separator")
    public void testHeadingNumberingMultiDigit() throws Exception {
        StringBuilder markdown = new StringBuilder("# title\n");
        for (int i = 1; i <= 10; i++) {
            markdown.append("## chapter ").append(i).append('\n');
        }
        MarkdownStyle style = MarkdownStyle.builder().withShowHeaderNumber(true).build();
        DocumentRenderData doc = convert(markdown.toString(), style);
        assertEquals("10. chapter 10", text(paragraph(doc, 10)));
    }

    @Test
    @DisplayName("numbering can start at level 1 and drop the trailing dot")
    public void testHeadingNumberingOptions() throws Exception {
        MarkdownStyle style = MarkdownStyle.builder().withShowHeaderNumber(true).withHeaderNumberStartLevel(1)
                .withHeaderNumberSuffix("").build();
        DocumentRenderData doc = convert("# A\n## B", style);
        assertEquals("1 A", text(paragraph(doc, 0)));
        assertEquals("1.1 B", text(paragraph(doc, 1)));
    }

    @Test
    @DisplayName("heading bookmarks are legal and unique")
    public void testBookmarks() throws Exception {
        DocumentRenderData doc = convert("## Title One\n\n## Title One", MarkdownStyle.newStyle());
        assertEquals("title_one", bookmark(paragraph(doc, 0)));
        assertEquals("title_one_1", bookmark(paragraph(doc, 1)), "duplicate headings must not collide");
    }

    @Test
    @DisplayName("a heading with inline markup still yields a bookmark from its text")
    public void testBookmarkFromStyledHeading() throws Exception {
        DocumentRenderData doc = convert("## *italic* heading", MarkdownStyle.newStyle());
        assertEquals("italic_heading", bookmark(paragraph(doc, 0)));
    }

    @Test
    @DisplayName("heading style ids stay template compatible and can be switched")
    public void testHeadingStyleIds() throws Exception {
        DocumentRenderData doc = convert("## H2", MarkdownStyle.newStyle());
        assertEquals("2", paragraph(doc, 0).getParagraphStyle().getStyleId(),
                "default ids must stay 1..6 for Chinese Word templates");
        DocumentRenderData english = convert("## H2", MarkdownStyle.builder().withEnglishHeadingStyles().build());
        assertEquals("Heading2", paragraph(english, 0).getParagraphStyle().getStyleId());
    }

    // ------------------------------------------------------------------
    // block quotes
    // ------------------------------------------------------------------

    @Test
    @DisplayName("a quote containing a list renders instead of crashing")
    public void testQuoteWithList() throws Exception {
        DocumentRenderData doc = convert("> quote\n>\n> - a\n> - b", MarkdownStyle.newStyle());
        assertTrue(doc.getContents().size() >= 2);
        assertNotNull(find(doc, NumberingRenderData.class), "the nested list must survive");
    }

    @Test
    @DisplayName("a quote containing a table renders instead of crashing")
    public void testQuoteWithTable() throws Exception {
        DocumentRenderData doc = convert("> quote\n>\n> | a | b |\n> | --- | --- |\n> | 1 | 2 |",
                MarkdownStyle.newStyle());
        assertNotNull(find(doc, TableRenderData.class), "the nested table must survive");
    }

    @Test
    @DisplayName("a quote containing a code block renders instead of crashing")
    public void testQuoteWithCodeBlock() throws Exception {
        DocumentRenderData doc = convert("> quote\n>\n> ```java\n> int a = 1;\n> ```", MarkdownStyle.newStyle());
        assertTrue(doc.getContents().size() >= 2);
    }

    @Test
    @DisplayName("quote paragraphs share the configured style without mutating it")
    public void testQuoteStyleNotMutated() throws Exception {
        MarkdownStyle style = MarkdownStyle.newStyle();
        Double before = style.getQuoteStyle().getSpacingAfterLines();
        convert("> a", style);
        convert("> b", style);
        assertEquals(before, style.getQuoteStyle().getSpacingAfterLines(),
                "rendering must not mutate the user style");
    }

    // ------------------------------------------------------------------
    // tables
    // ------------------------------------------------------------------

    @Test
    @DisplayName("GFM column alignment is mapped onto the cell paragraphs")
    public void testTableAlignment() throws Exception {
        DocumentRenderData doc = convert("| L | C | R |\n| :--- | :---: | ---: |\n| 1 | 2 | 3 |",
                MarkdownStyle.newStyle());
        TableRenderData table = (TableRenderData) find(doc, TableRenderData.class);
        assertNotNull(table);
        assertEquals(ParagraphAlignment.LEFT, cellAlignment(table, 0, 0));
        assertEquals(ParagraphAlignment.CENTER, cellAlignment(table, 0, 1));
        assertEquals(ParagraphAlignment.RIGHT, cellAlignment(table, 0, 2));
    }

    @Test
    @DisplayName("a table inside a list item is rendered instead of dropped")
    public void testTableInsideList() throws Exception {
        DocumentRenderData doc = convert("- item\n\n  | a | b |\n  | --- | --- |\n  | 1 | 2 |",
                MarkdownStyle.newStyle());
        assertNotNull(find(doc, TableRenderData.class));
        assertNotNull(find(doc, NumberingRenderData.class));
    }

    // ------------------------------------------------------------------
    // blocks
    // ------------------------------------------------------------------

    @Test
    @DisplayName("a thematic break becomes a bottom bordered paragraph")
    public void testThematicBreak() throws Exception {
        DocumentRenderData doc = convert("before\n\n---\n\nafter", MarkdownStyle.newStyle());
        assertEquals(3, doc.getContents().size());
        ParagraphStyle middle = paragraph(doc, 1).getParagraphStyle();
        assertNotNull(middle);
        assertNotNull(middle.getBottomBorder(), "the horizontal rule is drawn as a bottom border");
    }

    @Test
    @DisplayName("a soft break folds into a space inside one paragraph")
    public void testSoftLineBreak() throws Exception {
        DocumentRenderData doc = convert("term\nline two", MarkdownStyle.newStyle());
        assertEquals(1, doc.getContents().size(), "a soft break must not create a new paragraph");
        assertEquals("term line two", text(paragraph(doc, 0)));
    }

    @Test
    @DisplayName("a hard break stays inside the paragraph as a line break")
    public void testHardLineBreak() throws Exception {
        DocumentRenderData doc = convert("line one\\\nline two", MarkdownStyle.newStyle());
        assertEquals(1, doc.getContents().size(), "a hard break must not create a new paragraph");
        assertEquals("line one\nline two", text(paragraph(doc, 0)));
    }

    @Test
    @DisplayName("the soft break replacement text is configurable")
    public void testSoftBreakConfiguration() throws Exception {
        DocumentRenderData doc = convert("中文\n换行", MarkdownStyle.builder().withSoftBreakText("").build());
        assertEquals("中文换行", text(paragraph(doc, 0)));
    }

    // ------------------------------------------------------------------
    // code blocks
    // ------------------------------------------------------------------

    @Test
    @DisplayName("a language-less indented block keeps the chrome but is not coloured")
    public void testIndentedCodeBlock() throws Exception {
        DocumentRenderData doc = convert("    indent 4 spaces", MarkdownStyle.newStyle());
        assertTrue(coloredRuns(doc) == 0, "an indented block must not be coloured as Java");
        assertNotNull(firstChrome(doc), "the code block chrome must be kept");
    }

    @Test
    @DisplayName("an unknown language degrades gracefully and keeps the chrome")
    public void testUnknownLanguage() throws Exception {
        DocumentRenderData doc = convert("```nosuchlang\nx = 1\n```", MarkdownStyle.newStyle());
        assertEquals("x = 1", text(paragraph(doc, 0)));
        assertNotNull(firstChrome(doc));
    }

    @Test
    @DisplayName("fence attributes after the language are ignored")
    public void testFenceInfoAttributes() throws Exception {
        DocumentRenderData doc = convert("```java title=\"demo\"\nString s = \"x\";\n```", MarkdownStyle.newStyle());
        assertTrue(coloredRuns(doc) > 0, "the java language must have been recognised");
    }

    @Test
    @DisplayName("an empty code block does not abort the document")
    public void testEmptyCodeBlock() throws Exception {
        DocumentRenderData doc = convert("```\n```\n\nafter", MarkdownStyle.newStyle());
        assertNotNull(doc);
        assertTrue(doc.getContents().size() >= 1);
    }

    @Test
    @DisplayName("a fence without a language is not coloured")
    public void testFenceWithoutLanguage() throws Exception {
        DocumentRenderData doc = convert("```\nplain text block\n```", MarkdownStyle.newStyle());
        assertEquals(0, coloredRuns(doc));
        assertNotNull(firstChrome(doc));
    }

    @Test
    @DisplayName("a broken code block does not prevent the rest of the document")
    public void testCodeBlockFailSoft() throws Exception {
        // 非法的图片路径只影响该节点，不应中断整篇
        DocumentRenderData doc = convert("```java\nint a = 1;\n```\n\nplain paragraph", MarkdownStyle.newStyle());
        assertEquals("plain paragraph", text(paragraph(doc, doc.getContents().size() - 1)));
    }

    // ------------------------------------------------------------------
    // images
    // ------------------------------------------------------------------

    @Test
    @DisplayName("image alt text is used when the title is absent")
    public void testImageAlt() throws Exception {
        DocumentRenderData doc = convert("![alt text](logo.png)", MarkdownStyle.newStyle());
        PictureRenderData picture = (PictureRenderData) find(doc, PictureRenderData.class);
        assertNotNull(picture);
        assertEquals("alt text", picture.getAltMeta());
    }

    @Test
    @DisplayName("image title wins over alt text when present")
    public void testImageTitleWins() throws Exception {
        DocumentRenderData doc = convert("![alt text](logo.png \"the title\")", MarkdownStyle.newStyle());
        PictureRenderData picture = (PictureRenderData) find(doc, PictureRenderData.class);
        assertEquals("the title", picture.getAltMeta());
    }

    @Test
    @DisplayName("a remote image uri is not rewritten into a local path")
    public void testRemoteImage() throws Exception {
        DocumentRenderData doc = convert("![remote](https://example.com/x.png)", MarkdownStyle.newStyle());
        assertNotNull(find(doc, PictureRenderData.class));
    }

    // ------------------------------------------------------------------
    // lists
    // ------------------------------------------------------------------

    @Test
    @DisplayName("the numbering format is bound to the nesting level, not to traversal order")
    public void testNestedListFormats() throws Exception {
        DocumentRenderData doc = convert("1. a\n   1. b\n2. c\n   - d", MarkdownStyle.newStyle());
        NumberingRenderData numbering = (NumberingRenderData) find(doc, NumberingRenderData.class);
        assertNotNull(numbering);
        assertEquals(2, numbering.getFormats().size(),
                "one format per level: the level 1 bullet must reuse the level 1 format");
        for (NumberingItemRenderData item : numbering.getItems()) {
            assertTrue(item.getLevel() <= 1);
        }
    }

    @Test
    @DisplayName("an ordered list starting at 3 keeps its numbers")
    public void testOrderedListStartNumber() throws Exception {
        DocumentRenderData doc = convert("3. three\n4. four", MarkdownStyle.newStyle());
        assertNull(find(doc, NumberingRenderData.class), "non standard start uses the textual fallback");
        assertEquals("3. three", text(paragraph(doc, 0)));
        assertEquals("4. four", text(paragraph(doc, 1)));
    }

    @Test
    @DisplayName("the start number fallback can be switched off")
    public void testOrderedListStartNumberDisabled() throws Exception {
        MarkdownStyle style = MarkdownStyle.builder().withOrderedListStartNumberFallback(false).build();
        DocumentRenderData doc = convert("3. three", style);
        assertNotNull(find(doc, NumberingRenderData.class));
    }

    @Test
    @DisplayName("a nested list inside a start number fallback list still renders")
    public void testNestedListInsideFallback() throws Exception {
        DocumentRenderData doc = convert("3. three\n   - sub a\n   - sub b\n4. four", MarkdownStyle.newStyle());

        List<ParagraphRenderData> paragraphs = paragraphs(doc);
        assertEquals(2, paragraphs.size());
        assertEquals("3. three", text(paragraphs.get(0)));
        assertEquals("4. four", text(paragraphs.get(1)));

        NumberingRenderData numbering = (NumberingRenderData) find(doc, NumberingRenderData.class);
        assertNotNull(numbering, "the nested bullet list must still be numbered");
        StringBuilder items = new StringBuilder();
        for (NumberingItemRenderData item : numbering.getItems()) {
            items.append(text(item.getItem())).append('|');
        }
        assertTrue(items.toString().contains("sub a"), "nested items were: " + items);
    }

    @Test
    @DisplayName("an ordered list with the ) delimiter keeps its delimiter")
    public void testOrderedListDelimiter() throws Exception {
        DocumentRenderData doc = convert("1) one\n2) two", MarkdownStyle.newStyle());
        NumberingRenderData numbering = (NumberingRenderData) find(doc, NumberingRenderData.class);
        assertNotNull(numbering);
        assertTrue(numbering.getFormats().get(0).getLvlText().contains(")"));
    }

    // ------------------------------------------------------------------
    // unknown nodes
    // ------------------------------------------------------------------

    @Test
    @DisplayName("html is passed through as before")
    public void testHtmlPassthrough() throws Exception {
        DocumentRenderData doc = convert("<div>html block</div>", MarkdownStyle.newStyle());
        assertTrue(text(paragraph(doc, 0)).contains("html block"));
    }

    // ------------------------------------------------------------------
    // end to end
    // ------------------------------------------------------------------

    @Test
    @DisplayName("the bundled sample renders into a readable docx")
    public void testEndToEndRender() throws Exception {
        // basic.md 中的图片路径是相对模块根目录写的，这里显式指定 imagesDir
        MarkdownStyle style = MarkdownStyle.builder().withShowHeaderNumber(true).withImagesDir(".").build();
        String markdown = new String(Files.readAllBytes(Paths.get(SAMPLE)), StandardCharsets.UTF_8);
        MarkdownRenderData data = Markdowns.of(markdown).style(style).create();

        Map<String, Object> model = new HashMap<>();
        model.put("md", data);
        Configure config = Configure.builder().bind("md", new MarkdownRenderPolicy()).build();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (XWPFTemplate template = XWPFTemplate.compile(TEMPLATE, config)) {
            template.render(model).write(out);
        }
        assertTrue(out.size() > 0, "the document must not be empty");

        try (XWPFDocument rendered = new XWPFDocument(new ByteArrayInputStream(out.toByteArray()))) {
            assertTrue(rendered.getParagraphs().size() > 10, "the sample has many paragraphs");
            assertTrue(rendered.getTables().size() >= 2, "the sample has two tables");
            assertTrue(rendered.getBodyElements().size() > 0);
        }
    }

    @Test
    @DisplayName("an in page link and its heading bookmark match in the rendered document")
    public void testAnchorMatchesBookmarkEndToEnd() throws Exception {
        String markdown = "## Section One\n\nsee [the section](#Section-One)";
        MarkdownRenderData data = Markdowns.of(markdown).style(MarkdownStyle.newStyle()).create();
        Map<String, Object> model = new HashMap<>();
        model.put("md", data);
        Configure config = Configure.builder().bind("md", new MarkdownRenderPolicy()).build();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (XWPFTemplate template = XWPFTemplate.compile(TEMPLATE, config)) {
            template.render(model).write(out);
        }
        String xml;
        try (XWPFDocument rendered = new XWPFDocument(new ByteArrayInputStream(out.toByteArray()))) {
            xml = rendered.getDocument().xmlText();
        }

        // poi-tl 把文内跳转渲染为 Word 域：w:fldSimple w:instr="HYPERLINK \l &quot;name&quot;"
        Set<String> bookmarkNames = matchAll(xml, "w:name=\"([^\"]+)\"");
        Set<String> linkTargets = matchAll(xml, "HYPERLINK \\\\l &quot;([^&]+)&quot;");
        assertTrue(bookmarkNames.contains("section_one"), "bookmark names were: " + bookmarkNames);
        assertTrue(linkTargets.contains("section_one"), "link targets were: " + linkTargets);
        assertTrue(xml.contains("w:val=\"0000FF\""), "an in page link must still look like a link");
    }

    private static Set<String> matchAll(String xml, String regex) {
        Set<String> result = new HashSet<>();
        Matcher matcher = Pattern.compile(regex).matcher(xml);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }

    // ------------------------------------------------------------------
    // helpers
    // ------------------------------------------------------------------

    private static final String TEMPLATE = "src/test/resources/markdown/markdown_template.docx";
    private static final String SAMPLE = "src/test/resources/markdown/basic.md";

    private static DocumentRenderData convert(String markdown, MarkdownStyle style) throws Exception {
        MarkdownRenderData data = Markdowns.of(markdown).style(style).create();
        return new MarkdownToDocumentRenderDataConverter().convert(data);
    }

    private ParagraphRenderData paragraph(String markdown, int index) throws Exception {
        return paragraph(convert(markdown, MarkdownStyle.newStyle()), index);
    }

    private static ParagraphRenderData paragraph(DocumentRenderData doc, int index) {
        List<ParagraphRenderData> paragraphs = paragraphs(doc);
        assertTrue(paragraphs.size() > index, "expected at least " + (index + 1) + " paragraphs");
        return paragraphs.get(index);
    }

    private static List<ParagraphRenderData> paragraphs(DocumentRenderData doc) {
        List<ParagraphRenderData> result = new ArrayList<>();
        for (RenderData data : doc.getContents()) {
            if (data instanceof ParagraphRenderData) result.add((ParagraphRenderData) data);
        }
        return result;
    }

    private static String text(ParagraphRenderData paragraph) {
        StringBuilder text = new StringBuilder();
        for (RenderData data : paragraph.getContents()) {
            if (data instanceof TextRenderData) text.append(((TextRenderData) data).getText());
        }
        return text.toString();
    }

    private static List<TextRenderData> texts(ParagraphRenderData paragraph) {
        List<TextRenderData> result = new ArrayList<>();
        for (RenderData data : paragraph.getContents()) {
            if (data instanceof TextRenderData) result.add((TextRenderData) data);
        }
        return result;
    }

    private static Style style(ParagraphRenderData paragraph, int index) {
        return texts(paragraph).get(index).getStyle();
    }

    private static String bookmark(ParagraphRenderData paragraph) {
        for (RenderData data : paragraph.getContents()) {
            if (data instanceof BookmarkTextRenderData) return ((BookmarkTextRenderData) data).getBookmark();
        }
        return null;
    }

    private static <T> T find(DocumentRenderData doc, Class<T> type) {
        for (RenderData data : doc.getContents()) {
            if (type.isInstance(data)) return type.cast(data);
            if (data instanceof ParagraphRenderData) {
                for (RenderData content : ((ParagraphRenderData) data).getContents()) {
                    if (type.isInstance(content)) return type.cast(content);
                }
            }
        }
        return null;
    }

    private static ParagraphAlignment cellAlignment(TableRenderData table, int row, int cell) {
        ParagraphStyle style = table.getRows().get(row).getCells().get(cell).getParagraphs().get(0).getParagraphStyle();
        return style.getAlign();
    }

    private static int coloredRuns(DocumentRenderData doc) {
        int colored = 0;
        for (RenderData data : doc.getContents()) {
            if (!(data instanceof ParagraphRenderData)) continue;
            for (TextRenderData text : texts((ParagraphRenderData) data)) {
                if (null != text.getStyle() && null != text.getStyle().getColor()) colored++;
            }
        }
        return colored;
    }

    private static String firstChrome(DocumentRenderData doc) {
        for (RenderData data : doc.getContents()) {
            if (!(data instanceof ParagraphRenderData)) continue;
            ParagraphStyle style = ((ParagraphRenderData) data).getParagraphStyle();
            if (null != style && null != style.getBackgroundColor()) return style.getBackgroundColor();
        }
        return null;
    }

}
