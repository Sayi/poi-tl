package com.deepoove.poi.plugin.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.highlight.HighlightStyle;

/**
 * {@code RenderData} is {@code Serializable}, so the style it carries must be
 * serializable as well. This used to fail with a
 * {@code NotSerializableException} whenever a style had been configured.
 */
public class MarkdownSerializationTest {

    @Test
    @DisplayName("MarkdownRenderData with a full style survives a serialization round trip")
    public void testRoundTrip() throws Exception {
        MarkdownStyle style = MarkdownStyle.builder()
                .withShowHeaderNumber(true)
                .withHeaderNumberStartLevel(1)
                .withHeaderNumberSuffix("")
                .withSoftBreakText("")
                .withImagesDir("/tmp/images")
                .withOrderedListStartNumberFallback(false)
                .withStrongStyle(Style.builder().buildBold().buildColor("FF0000").build())
                .withEmphasisStyle(Style.builder().buildItalic().build())
                .withHighlightStyle(HighlightStyle.builder().withTheme("github").withFontFamily("Menlo")
                        .withFontSize(10.0f).withShowLine(true).build())
                .build();
        MarkdownRenderData data = Markdowns.of("**x**").style(style).create();

        MarkdownRenderData copy = roundTrip(data);
        MarkdownStyle copied = copy.getStyle();
        assertNotNull(copied);
        assertEquals("**x**", copy.getMarkdown());
        assertTrue(copied.isShowHeaderNumber());
        assertEquals(1, copied.getHeaderNumberStartLevel());
        assertEquals("", copied.getHeaderNumberSuffix());
        assertEquals("", copied.getSoftBreakText());
        assertEquals("/tmp/images", copied.getImagesDir());
        assertEquals(false, copied.isOrderedListStartNumberFallback());
        assertEquals("FF0000", copied.getStrongStyle().getColor());
        assertEquals(Boolean.TRUE, copied.getEmphasisStyle().isItalic());

        HighlightStyle highlight = copied.getHighlightStyle();
        assertNotNull(highlight);
        assertEquals("github", highlight.getTheme());
        assertEquals("Menlo", highlight.getFontFamily());
        assertEquals(10.0, highlight.getFontSize(), 0.001);
        assertTrue(highlight.isShowLine());

        assertEquals("1", copied.getHeadingStyleId(1), "heading style ids survive as well");
        assertEquals("2", copied.getHeadingStyleId(2));
    }

    @Test
    @DisplayName("FileMarkdownRenderData is serializable, including its charset")
    public void testFileRenderDataRoundTrip() throws Exception {
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath("/tmp/docs/README.md");
        data.setCharset(java.nio.charset.StandardCharsets.ISO_8859_1);

        FileMarkdownRenderData copy = roundTrip(data);
        assertEquals("/tmp/docs/README.md", copy.getPath());
        assertEquals("ISO-8859-1", copy.getCharsetName());
    }

    @Test
    @DisplayName("a default style is complete, so a bare new MarkdownStyle() is usable")
    public void testDefaultStyleIsComplete() {
        MarkdownStyle style = new MarkdownStyle();
        assertNotNull(style.getInlineCodeStyle());
        assertNotNull(style.getHighlightStyle());
        assertNotNull(style.getQuoteStyle());
        assertNotNull(style.getTableBorderStyle());
        assertNotNull(style.getTableHeaderStyle());
        assertNotNull(style.getStrongStyle());
        assertNotNull(style.getEmphasisStyle());
        assertNotNull(style.getThematicBreakStyle());
        assertEquals("1", style.getHeadingStyleId(1));
        assertEquals("6", style.getHeadingStyleId(6));
        assertEquals(Boolean.TRUE, style.getStrongStyle().isBold());
        assertEquals(Boolean.TRUE, style.getEmphasisStyle().isItalic());
    }

    @SuppressWarnings("unchecked")
    private static <T> T roundTrip(T value) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            out.writeObject(value);
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))) {
            return (T) in.readObject();
        }
    }

}
