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
package com.deepoove.poi.plugin.markdown.converter;

import static com.deepoove.poi.data.NumberingFormat.BULLET;
import static com.deepoove.poi.data.NumberingFormat.DECIMAL_BUILDER;
import static com.deepoove.poi.data.NumberingFormat.DECIMAL_PARENTHESES_BUILDER;
import static com.deepoove.poi.data.NumberingFormat.LOWER_LETTER_BUILDER;
import static com.deepoove.poi.data.NumberingFormat.UPPER_LETTER_BUILDER;
import static com.deepoove.poi.data.NumberingFormat.UPPER_ROMAN_BUILDER;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TableCell;
import org.commonmark.ext.gfm.tables.TableHead;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.Code;
import org.commonmark.node.CustomBlock;
import org.commonmark.node.Document;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.HtmlInline;
import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.ListBlock;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.converter.ParagraphToDocumentRenderDataConverter;
import com.deepoove.poi.data.Cells;
import com.deepoove.poi.data.Cells.CellBuilder;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Documents.DocumentBuilder;
import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.data.NumberingItemRenderData;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Paragraphs.ParagraphBuilder;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Rows.RowBuilder;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Tables.TableBuilder;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.Texts.TextBuilder;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.plugin.highlight.HighlightRenderData;
import com.deepoove.poi.plugin.highlight.converter.HighlightToDocumentRenderDataConverter;
import com.deepoove.poi.plugin.highlight.converter.ParagraphRenderer;
import com.deepoove.poi.plugin.markdown.MarkdownStyle;

/**
 * Walks the CommonMark AST and turns it into {@link DocumentRenderData}.
 * <p>
 * node types that are unknown are degraded to plain text instead of aborting
 * the whole conversion, and a single broken code block never aborts the
 * document.
 * </p>
 *
 * @author Sayi
 */
public class DocumentVisitor extends AbstractVisitor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DocumentVisitor.class);

    private static final HighlightToDocumentRenderDataConverter HIGHLIGHT_CONVERTER = new HighlightToDocumentRenderDataConverter();

    /** Font used when even the plain code block chrome can not be built. */
    private static final String FALLBACK_CODE_FONT = "Consolas";

    /** Space a soft line break is rendered as when nothing else is configured. */
    private static final String DEFAULT_SOFT_BREAK = " ";

    private final DocumentBuilder of = Documents.of();
    private final MarkdownStyle style;
    private final HeadingNumbering headingNumbering;
    private final BookmarkRegistry bookmarks = new BookmarkRegistry();
    private final InlineContext baseContext;

    public DocumentVisitor(MarkdownStyle style) {
        this.style = null == style ? new MarkdownStyle() : style;
        this.headingNumbering = new HeadingNumbering(this.style.getHeaderNumberStartLevel(),
                this.style.getHeaderNumberSuffix());
        this.baseContext = InlineContext.EMPTY.withStyle(this.style);
    }

    // ------------------------------------------------------------------
    // blocks
    // ------------------------------------------------------------------

    @Override
    public void visit(Heading heading) {
        int level = heading.getLevel();
        final ParagraphBuilder paraOf = Paragraphs.of().left().allowWordBreak();
        String styleId = style.getHeadingStyleId(level);
        if (null != styleId) paraOf.styleId(styleId);
        if (style.isShowHeaderNumber()) {
            String number = headingNumbering.next(level);
            if (!number.isEmpty()) paraOf.addText(number);
        }
        ParagraphRenderData headerParagraph = firstParagraph(parseBlock(heading));
        if (null != headerParagraph) {
            paraOf.addParagraph(headerParagraph);
            String text = plainText(headerParagraph);
            if (!text.isEmpty()) {
                // Word 书签名必须合法且唯一，另见 BookmarkRegistry
                paraOf.addText(Texts.of("").bookmark(bookmarks.register(text)).create());
            }
        }
        of.addParagraph(paraOf.create());
    }

    @Override
    public void visit(Paragraph paragraph) {
        of.addDocument(parseBlock(paragraph));
    }

    @Override
    public void visit(BlockQuote blockQuote) {
        appendQuote(blockQuote);
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        of.addDocument(parseIndentedCodeBlock(indentedCodeBlock));
    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        of.addDocument(parseFencedCodeBlock(fencedCodeBlock));
    }

    @Override
    public void visit(OrderedList orderedList) {
        renderList(orderedList, 0);
    }

    @Override
    public void visit(BulletList bulletList) {
        renderList(bulletList, 0);
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        of.addParagraph(Paragraphs.of().paraStyle(style.getThematicBreakStyle()).create());
    }

    @Override
    public void visit(HtmlBlock node) {
        of.addParagraph(Paragraphs.of(node.getLiteral()).create());
    }

    @Override
    public void visit(CustomBlock customBlock) {
        if (customBlock instanceof TableBlock) {
            of.addTable(parseTable((TableBlock) customBlock));
        } else {
            super.visit(customBlock);
        }
    }

    @Override
    protected void visitChildren(Node parent) {
        if (parent instanceof Document) {
            Node node = parent.getFirstChild();
            while (null != node) {
                // 先取 next：访问节点可能修改链表
                Node next = node.getNext();
                node.accept(this);
                node = next;
            }
        } else {
            LOGGER.warn("Current not support visit node: {}", parent);
        }
    }

    public DocumentRenderData getResult() {
        return of.create();
    }

    // ------------------------------------------------------------------
    // inline
    // ------------------------------------------------------------------

    /**
     * Parses the inline children of a block container (paragraph, heading or
     * table cell) into a document holding exactly one paragraph.
     *
     * @param block the container node
     * @return the render data, never {@code null}
     */
    private DocumentRenderData parseBlock(Node block) {
        if (block instanceof FencedCodeBlock) return parseFencedCodeBlock((FencedCodeBlock) block);
        if (block instanceof IndentedCodeBlock) return parseIndentedCodeBlock((IndentedCodeBlock) block);
        ParagraphBuilder paraOf = Paragraphs.of().left();
        appendInline(block, paraOf, baseContext);
        return Documents.of().addParagraph(paraOf.create()).create();
    }

    private void appendInline(Node parent, ParagraphBuilder paraOf, InlineContext ctx) {
        Node node = parent.getFirstChild();
        while (null != node) {
            Node next = node.getNext();
            if (node instanceof Text) {
                addText(paraOf, ((Text) node).getLiteral(), ctx);
            } else if (node instanceof StrongEmphasis) {
                appendInline(node, paraOf, ctx.bold());
            } else if (node instanceof Emphasis) {
                appendInline(node, paraOf, ctx.italic());
            } else if (node instanceof Code) {
                addText(paraOf, ((Code) node).getLiteral(), ctx.code());
            } else if (node instanceof SoftLineBreak) {
                addText(paraOf, softBreakText(), ctx);
            } else if (node instanceof HardLineBreak) {
                addText(paraOf, "\n", ctx);
            } else if (node instanceof Link) {
                appendLink((Link) node, paraOf, ctx);
            } else if (node instanceof Image) {
                appendPicture(paraOf, (Image) node, ctx);
            } else if (node instanceof HtmlInline) {
                addText(paraOf, ((HtmlInline) node).getLiteral(), ctx);
            } else if (node instanceof HtmlBlock) {
                addText(paraOf, ((HtmlBlock) node).getLiteral(), ctx);
            } else {
                // 未知节点（例如其它 GFM 扩展引入的节点）：降级为纯文本，绝不静默丢内容
                String fallback = plainTextOf(node);
                if (fallback.isEmpty()) {
                    LOGGER.warn("Current not support visit node: {}", node);
                } else {
                    LOGGER.warn("Current not support visit node: {}, degrade to plain text.", node);
                    addText(paraOf, fallback, ctx);
                }
            }
            node = next;
        }
    }

    private void appendLink(Link link, ParagraphBuilder paraOf, InlineContext ctx) {
        String destination = link.getDestination();
        InlineContext linkContext = ctx;
        if (null != destination && destination.startsWith("#")) {
            // 文内跳转：Word 内部书签必须写成 anchor:name
            linkContext = ctx.anchor(BookmarkRegistry.sanitize(destination.substring(1)));
        } else if (null != destination && !destination.isEmpty()) {
            linkContext = ctx.link(destination);
        }
        if (null == link.getFirstChild()) {
            LOGGER.warn("Link without content: {}", destination);
            return;
        }
        appendInline(link, paraOf, linkContext);
    }

    private void addText(ParagraphBuilder paraOf, String literal, InlineContext ctx) {
        if (null == literal || literal.isEmpty()) return;
        TextBuilder text = Texts.of(literal).style(ctx.resolve());
        if (null != ctx.getUrl()) {
            text.link(ctx.getUrl());
        } else if (null != ctx.getAnchor()) {
            text.anchor(ctx.getAnchor());
        }
        paraOf.addText(text.create());
    }

    private void appendPicture(ParagraphBuilder paraOf, Image image, InlineContext ctx) {
        if (ctx.isLinked()) {
            // poi-tl 的 PictureRenderData 不承载超链接，图片链接降级为普通图片
            LOGGER.debug("Picture hyperlink is not supported, render image only: {}", image.getDestination());
        }
        paraOf.addPicture(parsePicture(image));
    }

    private PictureRenderData parsePicture(Image image) {
        String alternative = image.getTitle();
        if (null == alternative || alternative.isEmpty()) {
            // ![alt](url) 的 alt 在子 Text 节点上，title 通常是空的
            alternative = plainTextOf(image);
        }
        return Pictures.of(resolveImageUri(image.getDestination())).altMeta(alternative).fitSize().create();
    }

    private String resolveImageUri(String uri) {
        if (null == uri || uri.isEmpty()) return uri;
        // 带协议（http/https/data/file/...）或绝对路径的图片交给 poi-tl 自己处理
        if (hasScheme(uri)) return uri;
        String imagesDir = style.getImagesDir();
        try {
            if (Paths.get(uri).isAbsolute()) return uri;
            // imagesDir 为 null 表示未配置，相对当前工作目录解析
            return (null == imagesDir ? Paths.get(uri) : Paths.get(imagesDir, uri)).toFile().getAbsolutePath();
        } catch (RuntimeException e) {
            LOGGER.warn("Illegal image uri: {}", uri, e);
            return uri;
        }
    }

    private static boolean hasScheme(String uri) {
        int colon = uri.indexOf(':');
        if (colon <= 0) return false;
        for (int i = 0; i < colon; i++) {
            char c = uri.charAt(i);
            boolean legal = 0 == i ? Character.isLetter(c)
                    : Character.isLetterOrDigit(c) || '+' == c || '-' == c || '.' == c;
            if (!legal) return false;
        }
        return true;
    }

    private String softBreakText() {
        String text = style.getSoftBreakText();
        return null == text ? DEFAULT_SOFT_BREAK : text;
    }

    // ------------------------------------------------------------------
    // block quotes
    // ------------------------------------------------------------------

    private void appendQuote(BlockQuote quote) {
        ParagraphStyle quoteStyle = style.getQuoteStyle();
        Node node = quote.getFirstChild();
        while (null != node) {
            Node next = node.getNext();
            if (node instanceof Paragraph) {
                for (RenderData data : parseBlock(node).getContents()) {
                    if (!(data instanceof ParagraphRenderData)) continue;
                    ParagraphRenderData paragraph = (ParagraphRenderData) data;
                    // 共享配置实例，渲染期不再修改它，避免引用样式在文档之间泄漏
                    if (null != quoteStyle) paragraph.setParagraphStyle(quoteStyle);
                    of.addParagraph(paragraph);
                }
            } else if (node instanceof BlockQuote) {
                appendQuote((BlockQuote) node);
            } else {
                appendNestedBlock(node);
            }
            node = next;
        }
    }

    /**
     * Renders a block that appeared inside a block quote. Previously every
     * child of a quote was blindly cast to a paragraph, which crashed on lists
     * and tables.
     *
     * @param node the nested block node
     */
    private void appendNestedBlock(Node node) {
        if (node instanceof FencedCodeBlock) {
            of.addDocument(parseFencedCodeBlock((FencedCodeBlock) node));
        } else if (node instanceof IndentedCodeBlock) {
            of.addDocument(parseIndentedCodeBlock((IndentedCodeBlock) node));
        } else if (node instanceof Heading) {
            visit((Heading) node);
        } else if (node instanceof BulletList) {
            renderList((BulletList) node, 0);
        } else if (node instanceof OrderedList) {
            renderList((OrderedList) node, 0);
        } else if (node instanceof TableBlock) {
            of.addTable(parseTable((TableBlock) node));
        } else if (node instanceof ThematicBreak) {
            visit((ThematicBreak) node);
        } else if (node instanceof HtmlBlock) {
            visit((HtmlBlock) node);
        } else {
            LOGGER.warn("Current not support visit node in blockquote: {}", node);
        }
    }

    // ------------------------------------------------------------------
    // code blocks
    // ------------------------------------------------------------------

    private DocumentRenderData parseFencedCodeBlock(FencedCodeBlock fencedCodeBlock) {
        return parseCode(fencedCodeBlock.getLiteral(), normalizeLanguage(fencedCodeBlock.getInfo()));
    }

    private DocumentRenderData parseIndentedCodeBlock(IndentedCodeBlock indentedCodeBlock) {
        // 缩进代码块没有语言信息：既不能硬编码 java（误导着色），也不适合
        // highlightAuto（同样会猜测），而是渲染成保留代码块外壳但不着色的段落
        return parsePlainCode(indentedCodeBlock.getLiteral());
    }

    /**
     * Extracts the language from a fence info string, which may carry extra
     * attributes, for example {@code java title="demo"}.
     *
     * @param info the raw info string
     * @return the language, or {@code null} when the block has no language
     */
    private static String normalizeLanguage(String info) {
        if (null == info) return null;
        String language = info.trim();
        for (int i = 0; i < language.length(); i++) {
            if (Character.isWhitespace(language.charAt(i))) {
                language = language.substring(0, i);
                break;
            }
        }
        return language.isEmpty() ? null : language;
    }

    private DocumentRenderData parseCode(String literal, String language) {
        String code = stripTrailingNewline(literal);
        if (null == language) return parsePlainCode(code);
        HighlightRenderData highlight = new HighlightRenderData();
        highlight.setCode(code);
        highlight.setLanguage(language);
        highlight.setStyle(style.getHighlightStyle());
        try {
            return applyCodeSpacing(HIGHLIGHT_CONVERTER.convert(highlight));
        } catch (Exception e) {
            // fail-soft：单个代码块失败不应中断整篇文档
            LOGGER.warn("Fail to highlight code block with language [{}], fallback to plain code block.", language, e);
            return parsePlainCode(code);
        }
    }

    /**
     * Builds a code block that keeps the code block chrome (monospace font,
     * background and borders) but applies no syntax colouring.
     *
     * @param literal the raw code
     * @return the render data
     */
    private DocumentRenderData parsePlainCode(String literal) {
        String code = stripTrailingNewline(literal);
        ParagraphRenderData paragraph;
        try {
            ParagraphRenderer renderer = new ParagraphRenderer(style.getHighlightStyle());
            renderer.onStart();
            renderer.onPushCodeBlock(code);
            renderer.onFinish();
            paragraph = renderer.getResult();
        } catch (Exception e) {
            LOGGER.warn("Fail to build plain code block, fallback to monospace paragraph.", e);
            return Documents.of().addParagraph(Paragraphs.of()
                    .addText(Texts.of(code).fontFamily(FALLBACK_CODE_FONT).create())
                    .create()).create();
        }
        if (paragraph.getContents().isEmpty()) {
            // 空代码块：ParagraphToDocumentRenderDataConverter 无法处理空内容
            return applyCodeSpacing(Documents.of().addParagraph(paragraph).create());
        }
        try {
            return applyCodeSpacing(new ParagraphToDocumentRenderDataConverter(false).convert(paragraph));
        } catch (Exception e) {
            LOGGER.warn("Fail to split plain code block into lines, keep it as one paragraph.", e);
            return applyCodeSpacing(Documents.of().addParagraph(paragraph).create());
        }
    }

    private static String stripTrailingNewline(String code) {
        if (null == code) return "";
        return code.endsWith("\n") ? code.substring(0, code.length() - 1) : code;
    }

    private static DocumentRenderData applyCodeSpacing(DocumentRenderData code) {
        for (RenderData data : code.getContents()) {
            if (!(data instanceof ParagraphRenderData)) continue;
            ParagraphRenderData paragraph = (ParagraphRenderData) data;
            ParagraphStyle paragraphStyle = paragraph.getParagraphStyle();
            if (null == paragraphStyle) {
                paragraphStyle = new ParagraphStyle();
                paragraph.setParagraphStyle(paragraphStyle);
            }
            paragraphStyle.setSpacing(1.0);
        }
        return code;
    }

    // ------------------------------------------------------------------
    // lists
    // ------------------------------------------------------------------

    private void renderList(ListBlock list, int level) {
        if (list instanceof OrderedList && style.isOrderedListStartNumberFallback()
                && ((OrderedList) list).getStartNumber() != 1) {
            renderTextNumberedList((OrderedList) list, level);
            return;
        }
        NumberingRenderData numbering = new NumberingRenderData();
        List<TableRenderData> tables = new ArrayList<>();
        fillNumbering(numbering, tables, list, level);
        completeFormats(numbering);
        if (!numbering.getItems().isEmpty()) {
            of.addNumbering(numbering);
        }
        // Word 的编号项只能承载段落，列表内的表格作为紧随其后的表格渲染
        for (TableRenderData table : tables) {
            of.addTable(table);
        }
    }

    /**
     * Fills the numbering data of one list tree.
     * <p>
     * A single {@link NumberingRenderData} is shared by the whole tree on
     * purpose: splitting it would make Word allocate a new numbering id and
     * restart the counters of the outer list.
     * </p>
     *
     * @param numbering the shared numbering data
     * @param tables    collector of tables found inside list items
     * @param list      the list to render
     * @param level     nesting level, starting at 0
     */
    private void fillNumbering(NumberingRenderData numbering, List<TableRenderData> tables, ListBlock list, int level) {
        registerFormat(numbering.getFormats(), level, formatOf(list, level));
        List<NumberingItemRenderData> items = numbering.getItems();

        Node node = list.getFirstChild();
        while (null != node) {
            Node next = node.getNext();
            if (node instanceof ListItem) {
                boolean first = true;
                Node child = node.getFirstChild();
                while (null != child) {
                    Node childNext = child.getNext();
                    if (child instanceof ListBlock) {
                        fillNumbering(numbering, tables, (ListBlock) child, level + 1);
                    } else if (child instanceof TableBlock) {
                        tables.add(parseTable((TableBlock) child));
                    } else if (child instanceof Paragraph) {
                        DocumentRenderData block = parseBlock(child);
                        for (RenderData data : block.getContents()) {
                            if (!(data instanceof ParagraphRenderData)) continue;
                            ParagraphRenderData paragraph = (ParagraphRenderData) data;
                            if (first) {
                                items.add(new NumberingItemRenderData(level, paragraph));
                                first = false;
                            } else {
                                indentParagraph(paragraph, level);
                                items.add(new NumberingItemRenderData(NumberingItemRenderData.LEVEL_NORMAL, paragraph));
                            }
                        }
                    } else {
                        appendDegradedItem(items, child, level);
                    }
                    child = childNext;
                }
            }
            node = next;
        }
    }

    /**
     * Degrades a block that can not be represented inside a numbering item into
     * an indented, unnumbered paragraph instead of dropping it.
     *
     * @param items the numbering items
     * @param node  the unsupported block
     * @param level nesting level
     */
    private void appendDegradedItem(List<NumberingItemRenderData> items, Node node, int level) {
        String text = plainTextOf(node);
        LOGGER.warn("Current not support node in list item: {}, degrade to plain text.", node);
        ParagraphRenderData paragraph = Paragraphs.of().left().addText(text).create();
        indentParagraph(paragraph, level);
        items.add(new NumberingItemRenderData(NumberingItemRenderData.LEVEL_NORMAL, paragraph));
    }

    private NumberingFormat formatOf(ListBlock list, int level) {
        if (list instanceof BulletList) return BULLET;
        if (level <= 0) {
            // 遵循 Markdown 的分隔符：有序列表可能是 "1." 也可能是 "1)"
            char delimiter = ((OrderedList) list).getDelimiter();
            return (')' == delimiter ? DECIMAL_PARENTHESES_BUILDER : DECIMAL_BUILDER).build(0);
        }
        if (1 == level) return LOWER_LETTER_BUILDER.build(level);
        if (2 == level) return UPPER_LETTER_BUILDER.build(level);
        if (3 == level) return UPPER_ROMAN_BUILDER.build(level);
        return DECIMAL_BUILDER.build(level);
    }

    /**
     * Binds one format per nesting level. The core resolves formats by index
     * ({@code formats.get(ilvl)}), so appending in traversal order would let a
     * bullet sub list claim the format of an ordered sub list. The first
     * listing type seen on a level wins.
     */
    private static void registerFormat(List<NumberingFormat> formats, int level, NumberingFormat format) {
        while (formats.size() <= level) {
            formats.add(null);
        }
        if (null == formats.get(level)) formats.set(level, format);
    }

    /** Fills level gaps so the core receives a dense format array. */
    private static void completeFormats(NumberingRenderData numbering) {
        List<NumberingFormat> formats = numbering.getFormats();
        for (int level = 0; level < formats.size(); level++) {
            if (null == formats.get(level)) formats.set(level, DECIMAL_BUILDER.build(level));
        }
    }

    private static void indentParagraph(ParagraphRenderData paragraph, int level) {
        ParagraphStyle paragraphStyle = paragraph.getParagraphStyle();
        if (null == paragraphStyle) {
            paragraphStyle = new ParagraphStyle();
            paragraph.setParagraphStyle(paragraphStyle);
        }
        paragraphStyle.setIndentLeftChars((level + 1) * 1.8);
    }

    /**
     * Renders an ordered list that does not start at 1 as plain paragraphs
     * carrying the original numbers, because Word numbering is created by the
     * core with a hard coded start value of 1.
     *
     * @param list  the ordered list
     * @param level nesting level
     */
    private void renderTextNumberedList(OrderedList list, int level) {
        int number = Math.max(1, list.getStartNumber());
        Node node = list.getFirstChild();
        while (null != node) {
            Node next = node.getNext();
            if (node instanceof ListItem) {
                renderTextNumberedItem((ListItem) node, level, number++);
            }
            node = next;
        }
    }

    private void renderTextNumberedItem(ListItem item, int level, int number) {
        boolean first = true;
        Node child = item.getFirstChild();
        while (null != child) {
            Node next = child.getNext();
            if (child instanceof ListBlock) {
                renderList((ListBlock) child, level + 1);
            } else if (child instanceof TableBlock) {
                of.addTable(parseTable((TableBlock) child));
            } else if (child instanceof Paragraph) {
                for (RenderData data : parseBlock(child).getContents()) {
                    if (!(data instanceof ParagraphRenderData)) continue;
                    ParagraphRenderData paragraph = (ParagraphRenderData) data;
                    indentParagraph(paragraph, level);
                    if (first) {
                        ParagraphBuilder paraOf = Paragraphs.of().paraStyle(paragraph.getParagraphStyle())
                                .addText(number + ". ");
                        paraOf.addParagraph(paragraph);
                        of.addParagraph(paraOf.create());
                        first = false;
                    } else {
                        of.addParagraph(paragraph);
                    }
                }
            } else {
                LOGGER.warn("Current not support node in list item: {}", child);
            }
            child = next;
        }
    }

    // ------------------------------------------------------------------
    // tables
    // ------------------------------------------------------------------

    private TableRenderData parseTable(TableBlock table) {
        TableBuilder tableOf = Tables.ofPercentWidth("100%");
        tableOf.cellMargin(0.19f, 0.19f, 0.19f, 0.19f);
        Node headOrBody = table.getFirstChild();
        while (null != headOrBody) {
            boolean isHead = headOrBody instanceof TableHead;
            Node row = headOrBody.getFirstChild();
            while (null != row) {
                RowBuilder rowOf = Rows.of();
                if (isHead && null != style.getTableHeaderStyle()) {
                    rowOf.rowStyle(style.getTableHeaderStyle());
                }
                Node cell = row.getFirstChild();
                while (null != cell) {
                    appendCell(rowOf, cell);
                    cell = cell.getNext();
                }
                tableOf.addRow(rowOf.create());
                row = row.getNext();
            }
            headOrBody = headOrBody.getNext();
        }
        return tableOf.border(style.getTableBorderStyle()).create();
    }

    private void appendCell(RowBuilder rowOf, Node cell) {
        ParagraphAlignment alignment = alignmentOf(cell);
        CellBuilder cellOf = Cells.of();
        for (RenderData data : parseBlock(cell).getContents()) {
            if (!(data instanceof ParagraphRenderData)) continue;
            ParagraphRenderData paragraph = (ParagraphRenderData) data;
            if (null != alignment) setAlignment(paragraph, alignment);
            cellOf.addParagraph(paragraph);
        }
        rowOf.addCell(cellOf.create());
    }

    private static ParagraphAlignment alignmentOf(Node cell) {
        if (!(cell instanceof TableCell)) return null;
        TableCell.Alignment alignment = ((TableCell) cell).getAlignment();
        if (null == alignment) return null;
        switch (alignment) {
        case CENTER:
            return ParagraphAlignment.CENTER;
        case RIGHT:
            return ParagraphAlignment.RIGHT;
        case LEFT:
        default:
            return ParagraphAlignment.LEFT;
        }
    }

    private static void setAlignment(ParagraphRenderData paragraph, ParagraphAlignment alignment) {
        ParagraphStyle paragraphStyle = paragraph.getParagraphStyle();
        if (null == paragraphStyle) {
            paragraphStyle = new ParagraphStyle();
            paragraph.setParagraphStyle(paragraphStyle);
        }
        paragraphStyle.setAlign(alignment);
    }

    // ------------------------------------------------------------------
    // helpers
    // ------------------------------------------------------------------

    private static ParagraphRenderData firstParagraph(DocumentRenderData document) {
        for (RenderData data : document.getContents()) {
            if (data instanceof ParagraphRenderData) return (ParagraphRenderData) data;
        }
        return null;
    }

    private static String plainText(ParagraphRenderData paragraph) {
        StringBuilder text = new StringBuilder();
        for (RenderData data : paragraph.getContents()) {
            if (data instanceof TextRenderData) text.append(((TextRenderData) data).getText());
        }
        return text.toString().trim();
    }

    private static String plainTextOf(Node node) {
        StringBuilder text = new StringBuilder();
        collectText(node, text);
        return text.toString();
    }

    private static void collectText(Node node, StringBuilder text) {
        Node child = node.getFirstChild();
        while (null != child) {
            if (child instanceof Text) {
                text.append(((Text) child).getLiteral());
            } else if (child instanceof Code) {
                text.append(((Code) child).getLiteral());
            } else if (child instanceof SoftLineBreak || child instanceof HardLineBreak) {
                text.append(' ');
            } else {
                collectText(child, text);
            }
            child = child.getNext();
        }
    }

}
