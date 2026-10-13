/*
 * Copyright 2014-2026 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.plugin.markdown;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Heading;
import org.commonmark.node.Link;
import org.commonmark.node.ListBlock;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Markdown render data that reads its content from the file system.
 * <p>
 * The configured path may point to a single Markdown file or to a
 * <a href="https://docsify.js.org/">docsify</a> directory. A directory is
 * expanded through its {@code _sidebar.md} (or {@code README.md}) so that the
 * whole documentation is rendered into one document.
 * </p>
 * <p>
 * Sidebars of any shape are accepted: nested lists, plain text section titles
 * and links with styled text. External links and in page anchors are skipped
 * instead of being read from disk, and every resolved path is verified to stay
 * inside the documentation folder.
 * </p>
 *
 * @author Sayi
 */
public class FileMarkdownRenderData extends MarkdownRenderData {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMarkdownRenderData.class);

    private static final String SIDEBAR = "_sidebar.md";
    private static final String README = "README.md";

    /** Heading level of a top level sidebar entry, nested entries go deeper. */
    private static final int SIDEBAR_BASE_LEVEL = 2;

    private String path;

    /**
     * Charset name used to read the Markdown files. Stored as a name rather than
     * a {@link Charset} because {@code Charset} is not {@code Serializable} and
     * this class travels inside {@code RenderData}.
     */
    private String charsetName = StandardCharsets.UTF_8.name();

    /**
     * {@inheritDoc}
     * <p>
     * The content is read from {@link #getPath()} instead of being set directly;
     * a directory is expanded through its {@code _sidebar.md} or
     * {@code README.md}.
     * </p>
     *
     * @throws IllegalStateException when the path can not be read
     */
    @Override
    public String getMarkdown() {
        try {
            applyDefaultImagesDir();
            return suggest();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read markdown at path: " + path, e);
        }
    }

    private String suggest() throws IOException {
        Path base = Paths.get(path);
        if (!Files.isDirectory(base)) {
            if (!Files.isRegularFile(base)) {
                throw new IllegalStateException("Markdown file not found: " + path);
            }
            return read(base);
        }
        Path sidebar = base.resolve(SIDEBAR);
        if (Files.isRegularFile(sidebar)) {
            return expandSidebar(base, sidebar);
        }
        Path readme = base.resolve(README);
        if (Files.isRegularFile(readme)) {
            return read(readme);
        }
        throw new IllegalStateException(
                "Directory is not a docsify folder, neither " + SIDEBAR + " nor " + README + " exists: " + path);
    }

    /**
     * Expands a docsify sidebar into a single Markdown document: every entry
     * becomes a heading followed by the content of the linked file.
     *
     * @param base    the documentation root
     * @param sidebar the {@code _sidebar.md} file
     * @return the aggregated Markdown
     * @throws IOException when a file can not be read
     */
    private String expandSidebar(Path base, Path sidebar) throws IOException {
        List<SidebarEntry> entries = new ArrayList<>();
        // depth -1 so that the outermost list becomes level 0 (rendered as "## ")
        collectEntries(parser().parse(read(sidebar)), -1, entries);
        if (entries.isEmpty()) {
            LOGGER.warn("No sidebar entry found in {}", sidebar);
        }

        StringBuilder all = new StringBuilder();
        for (SidebarEntry entry : entries) {
            int level = Math.max(1, Math.min(6, entry.depth + SIDEBAR_BASE_LEVEL));
            all.append('\n').append(headingMarks(level)).append(' ').append(entry.title).append('\n');
            if (null == entry.destination) continue;
            if (isExternal(entry.destination)) {
                LOGGER.debug("Skip non local sidebar link: {}", entry.destination);
                continue;
            }
            Path target = resolveTarget(base, entry.destination);
            if (null != target) {
                all.append(read(target));
            }
        }
        return all.toString();
    }

    /**
     * Recursively walks the sidebar AST and collects its entries. Any shape is
     * tolerated, so a plain text category, a nested list or a styled link never
     * aborts the expansion.
     *
     * @param parent the node to walk
     * @param depth  list nesting depth, {@code -1} at the document root
     * @param out    collector of the entries
     */
    private static void collectEntries(Node parent, int depth, List<SidebarEntry> out) {
        Node node = parent.getFirstChild();
        while (null != node) {
            Node next = node.getNext();
            if (node instanceof ListBlock) {
                collectEntries(node, depth + 1, out);
            } else if (node instanceof ListItem || node instanceof Paragraph || node instanceof Heading) {
                collectEntries(node, depth, out);
            } else if (node instanceof Link) {
                out.add(new SidebarEntry(depth, plainText(node), ((Link) node).getDestination()));
            } else if (node instanceof Text) {
                String title = ((Text) node).getLiteral().trim();
                if (!title.isEmpty()) {
                    out.add(new SidebarEntry(depth, title, null));
                }
            }
            node = next;
        }
    }

    /**
     * Resolves a sidebar destination to a readable Markdown file, refusing to
     * escape the documentation root.
     *
     * @param base        the documentation root
     * @param destination the raw link target
     * @return the resolved file, or {@code null} when it must be skipped
     */
    private Path resolveTarget(Path base, String destination) {
        String link = destination;
        int hash = link.indexOf('#');
        if (hash >= 0) link = link.substring(0, hash);
        int query = link.indexOf('?');
        if (query >= 0) link = link.substring(0, query);
        if (link.isEmpty()) return null;

        Path root = base.toAbsolutePath().normalize();
        Path candidate = root.resolve(link).normalize();
        if (!candidate.startsWith(root)) {
            LOGGER.warn("Illegal path traversal in sidebar, skip: {}", destination);
            return null;
        }
        if (Files.isRegularFile(candidate)) {
            if (isMarkdown(candidate)) return candidate;
            LOGGER.warn("Sidebar link is not a markdown file, skip: {}", destination);
            return null;
        }
        if (Files.isDirectory(candidate)) {
            Path readme = candidate.resolve(README);
            if (Files.isRegularFile(readme)) return readme;
            LOGGER.warn("Sidebar directory has no {}, skip: {}", README, destination);
            return null;
        }
        // 允许省略 .md 后缀
        Path withSuffix = root.resolve(link + ".md").normalize();
        if (withSuffix.startsWith(root) && Files.isRegularFile(withSuffix)) return withSuffix;

        LOGGER.warn("Sidebar link target not found, skip: {}", destination);
        return null;
    }

    /**
     * Sets the default image folder to the folder of the Markdown file, so that
     * relative images resolve against the document instead of the working
     * directory. An explicitly configured folder always wins; {@code null} means
     * "not configured" and is the only value replaced here.
     */
    private void applyDefaultImagesDir() {
        if (null == path) return;
        MarkdownStyle style = getStyle();
        if (null == style) {
            style = new MarkdownStyle();
            setStyle(style);
        }
        if (null != style.getImagesDir()) return;
        Path base = Paths.get(path).toAbsolutePath();
        Path folder = Files.isDirectory(base) ? base : base.getParent();
        if (null != folder) {
            style.setImagesDir(folder.toString());
        }
    }

    private String read(Path file) throws IOException {
        return new String(Files.readAllBytes(file), charset());
    }

    private Charset charset() {
        if (null == charsetName || charsetName.isEmpty()) return StandardCharsets.UTF_8;
        try {
            return Charset.forName(charsetName);
        } catch (RuntimeException e) {
            LOGGER.warn("Unsupported charset [{}], fallback to UTF-8.", charsetName);
            return StandardCharsets.UTF_8;
        }
    }

    private static boolean isMarkdown(Path file) {
        String name = file.getFileName().toString().toLowerCase(Locale.ROOT);
        return name.endsWith(".md") || name.endsWith(".markdown");
    }

    private static boolean isExternal(String destination) {
        if (null == destination || destination.isEmpty()) return true;
        if (destination.startsWith("#") || destination.startsWith("//")) return true;
        int colon = destination.indexOf(':');
        if (colon <= 0) return false;
        for (int i = 0; i < colon; i++) {
            char c = destination.charAt(i);
            boolean legal = 0 == i ? Character.isLetter(c)
                    : Character.isLetterOrDigit(c) || '+' == c || '-' == c || '.' == c;
            if (!legal) return false;
        }
        return true;
    }

    private static String headingMarks(int level) {
        char[] marks = new char[level];
        Arrays.fill(marks, '#');
        return new String(marks);
    }

    /**
     * Plain text of a node, used for link titles that may contain inline markup.
     *
     * @param node the node
     * @return its text content
     */
    private static String plainText(Node node) {
        StringBuilder text = new StringBuilder();
        collectText(node, text);
        return text.toString().trim();
    }

    private static void collectText(Node node, StringBuilder text) {
        Node child = node.getFirstChild();
        while (null != child) {
            if (child instanceof Text) {
                text.append(((Text) child).getLiteral());
            } else {
                collectText(child, text);
            }
            child = child.getNext();
        }
    }

    private static Parser parser() {
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        return Parser.builder().extensions(extensions).build();
    }

    /**
     * Returns the file or directory the Markdown content is read from.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the file or directory the Markdown content is read from.
     *
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the name of the charset used to read the Markdown files
     */
    public String getCharsetName() {
        return charsetName;
    }

    /**
     * @param charsetName the name of the charset used to read the Markdown files
     */
    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    /**
     * @return the charset used to read the Markdown files
     */
    public Charset getCharset() {
        return charset();
    }

    /**
     * @param charset the charset used to read the Markdown files, UTF-8 by default
     */
    public void setCharset(Charset charset) {
        this.charsetName = null == charset ? null : charset.name();
    }

    /**
     * A single entry of a docsify sidebar.
     */
    private static final class SidebarEntry {

        private final int depth;
        private final String title;
        private final String destination;

        private SidebarEntry(int depth, String title, String destination) {
            this.depth = depth;
            this.title = title;
            this.destination = destination;
        }
    }

}
