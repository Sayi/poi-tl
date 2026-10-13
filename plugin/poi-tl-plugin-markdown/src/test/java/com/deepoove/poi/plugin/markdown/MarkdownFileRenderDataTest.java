package com.deepoove.poi.plugin.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Robustness of {@link FileMarkdownRenderData}: every sidebar shape, external
 * links, path traversal, charset and error reporting.
 */
public class MarkdownFileRenderDataTest {

    @TempDir
    Path tempDir;

    /** The docsify root. */
    private Path root;

    @BeforeEach
    public void setUp() throws IOException {
        root = Files.createDirectories(tempDir.resolve("docsify"));
        write(root.resolve("alpha.md"), "# Alpha content\n");
        write(root.resolve("bold.md"), "# Bold content\n");
        write(root.resolve("nested/deep.md"), "# Deep content\n");
        // 文档根目录之外的文件，用于验证路径穿越防护
        write(tempDir.resolve("secret.md"), "# SECRET CONTENT\n");
    }

    @Test
    @DisplayName("a plain link sidebar no longer renders an empty document")
    public void testNonListSidebar() throws IOException {
        write(root.resolve("_sidebar.md"), "[Alpha](alpha.md)\n");
        String markdown = data(root).getMarkdown();
        assertFalse(markdown.trim().isEmpty(), "a non list sidebar must not silently produce nothing");
        assertTrue(markdown.contains("Alpha content"));
    }

    @Test
    @DisplayName("nested entries, plain text categories and styled links are all supported")
    public void testSidebarShapes() throws IOException {
        write(root.resolve("_sidebar.md"),
                "- [Alpha](alpha.md)\n"
                        + "- Category title\n"
                        + "  - [**Bold** item](bold.md)\n"
                        + "  - [Deep](nested/deep.md)\n");
        String markdown = data(root).getMarkdown();
        assertTrue(markdown.contains("## Alpha"), "top level entry becomes a level 2 heading");
        assertTrue(markdown.contains("## Category title"), "a plain text category becomes a heading");
        assertTrue(markdown.contains("### Bold item"), "nested entries go one level deeper");
        assertTrue(markdown.contains("Alpha content"));
        assertTrue(markdown.contains("Bold content"));
        assertTrue(markdown.contains("Deep content"), "a link into a sub folder is resolved");
    }

    @Test
    @DisplayName("a directory link resolves README.md inside it")
    public void testDirectoryLink() throws IOException {
        write(root.resolve("guide/README.md"), "# Guide content\n");
        write(root.resolve("_sidebar.md"), "- [Guide](guide)\n");
        assertTrue(data(root).getMarkdown().contains("Guide content"));
    }

    @Test
    @DisplayName("external links and anchors are skipped instead of being read from disk")
    public void testExternalLinksSkipped() throws IOException {
        write(root.resolve("_sidebar.md"),
                "- [Site](https://example.com)\n"
                        + "- [Mail](mailto:a@b.com)\n"
                        + "- [Anchor](#local)\n"
                        + "- [Alpha](alpha.md)\n");
        String markdown = data(root).getMarkdown();
        assertTrue(markdown.contains("Alpha content"));
        assertFalse(markdown.contains("SECRET CONTENT"));
    }

    @Test
    @DisplayName("a sidebar can not escape the documentation root")
    public void testPathTraversalRejected() throws IOException {
        write(root.resolve("_sidebar.md"), "- [Escape](../secret.md)\n");
        String markdown = data(root).getMarkdown();
        assertFalse(markdown.contains("SECRET CONTENT"), "path traversal must be rejected");
    }

    @Test
    @DisplayName("imagesDir defaults to the folder of the markdown file")
    public void testImagesDirDefault() throws IOException {
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(root.resolve("alpha.md").toString());
        assertNull(data.getStyle(), "the style is created lazily when needed");
        data.getMarkdown();
        assertNotNull(data.getStyle());
        assertEquals(root.toAbsolutePath().toString(), data.getStyle().getImagesDir());
    }

    @Test
    @DisplayName("an explicit imagesDir is never overwritten")
    public void testImagesDirExplicitWins() throws IOException {
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(root.resolve("alpha.md").toString());
        data.setStyle(MarkdownStyle.builder().withImagesDir("/custom/images").build());
        data.getMarkdown();
        assertEquals("/custom/images", data.getStyle().getImagesDir());
    }

    @Test
    @DisplayName("content is read as UTF-8 by default")
    public void testUtf8Default() throws IOException {
        write(root.resolve("cn.md"), "# 中文标题\n\n正文内容\n");
        assertEquals(StandardCharsets.UTF_8.name(), data(root).getCharsetName());
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(root.resolve("cn.md").toString());
        assertTrue(data.getMarkdown().contains("中文标题"));
    }

    @Test
    @DisplayName("a missing file fails with an actionable message")
    public void testMissingFile() {
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(tempDir.resolve("does-not-exist.md").toString());
        IllegalStateException error = assertThrows(IllegalStateException.class, data::getMarkdown);
        assertTrue(error.getMessage().contains("does-not-exist.md"), "the message must name the path");
    }

    @Test
    @DisplayName("a non docsify directory fails with an actionable message")
    public void testNotDocsify() throws IOException {
        Path empty = Files.createDirectories(tempDir.resolve("plain"));
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(empty.toString());
        IllegalStateException error = assertThrows(IllegalStateException.class, data::getMarkdown);
        assertTrue(error.getMessage().contains("_sidebar.md"));
    }

    @Test
    @DisplayName("a directory falls back to README.md when there is no sidebar")
    public void testReadmeFallback() throws IOException {
        Path clean = Files.createDirectories(tempDir.resolve("readme-only"));
        write(clean.resolve("README.md"), "# Root readme\n");
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(clean.toString());
        assertTrue(data.getMarkdown().contains("Root readme"));
    }

    @Test
    @DisplayName("an explicitly empty imagesDir means the working directory and is kept")
    public void testEmptyImagesDirRespected() throws IOException {
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(root.resolve("alpha.md").toString());
        // 空串是"显式使用工作目录"，不能被自动推导覆盖（CLI 依赖该语义）
        data.setStyle(MarkdownStyle.builder().withImagesDir("").build());
        data.getMarkdown();
        assertEquals("", data.getStyle().getImagesDir());
    }

    @Test
    @DisplayName("the Markdowns factory builds file and docsify configurations")
    public void testMarkdownsFactory() throws IOException {
        write(root.resolve("_sidebar.md"), "- [Alpha](alpha.md)\n");

        MarkdownRenderData byFile = Markdowns.ofFile(root.resolve("alpha.md")).create();
        assertTrue(byFile instanceof FileMarkdownRenderData);
        assertTrue(byFile.getMarkdown().contains("Alpha content"));

        MarkdownRenderData byDocsify = Markdowns.ofDocsify(root)
                .style(Markdowns.style().withShowHeaderNumber(true))
                .create();
        assertTrue(byDocsify instanceof FileMarkdownRenderData);
        assertTrue(byDocsify.getStyle().isShowHeaderNumber(), "the builder style must be applied");
        assertTrue(byDocsify.getMarkdown().contains("Alpha content"));
    }

    private FileMarkdownRenderData data(Path path) {
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(path.toString());
        return data;
    }

    private static void write(Path file, String content) throws IOException {
        Files.createDirectories(file.getParent());
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));
    }

}
