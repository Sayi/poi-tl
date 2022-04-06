/*
 * Copyright 2014-2021 Sayi
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
import static com.deepoove.poi.data.NumberingFormat.DECIMAL_PARENTHESES_BUILDER;
import static com.deepoove.poi.data.NumberingFormat.LOWER_LETTER_BUILDER;
import static com.deepoove.poi.data.NumberingFormat.UPPER_LETTER_BUILDER;
import static com.deepoove.poi.data.NumberingFormat.UPPER_ROMAN_BUILDER;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TableHead;
import org.commonmark.node.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.data.*;
import com.deepoove.poi.data.Cells.CellBuilder;
import com.deepoove.poi.data.Documents.DocumentBuilder;
import com.deepoove.poi.data.NumberingFormat.Builder;
import com.deepoove.poi.data.Paragraphs.ParagraphBuilder;
import com.deepoove.poi.data.Rows.RowBuilder;
import com.deepoove.poi.data.Tables.TableBuilder;
import com.deepoove.poi.data.Texts.TextBuilder;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.plugin.highlight.HighlightRenderData;
import com.deepoove.poi.plugin.highlight.converter.HighlightToDocumentRenderDataConverter;
import com.deepoove.poi.plugin.markdown.MarkdownStyle;

/**
 * @author Sayi
 */
public class DocumentVisitor extends AbstractVisitor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DocumentVisitor.class);

    private static HighlightToDocumentRenderDataConverter highlightConverter = new HighlightToDocumentRenderDataConverter();

    private static List<Builder> multiLevelFormat = Arrays.asList(DECIMAL_PARENTHESES_BUILDER, LOWER_LETTER_BUILDER,
            UPPER_LETTER_BUILDER, UPPER_ROMAN_BUILDER);

    private DocumentBuilder of = Documents.of();
    private MarkdownStyle style;
    private int[] headerNumberArray = new int[10];
    private Iterator<Builder> iterator;

    public DocumentVisitor(MarkdownStyle style) {
        this.style = null == style ? new MarkdownStyle() : style;
    }

    @Override
    public void visit(Heading heading) {
        int level = heading.getLevel();
        resetHeaderNumberArray(level);

        ParagraphBuilder paraOf = Paragraphs.of().styleId(String.valueOf(level)).left().allowWordBreak();
        if (style.isShowHeaderNumber()) {
            paraOf.addText(getHeaderNumber(level));
        }
        DocumentRenderData renderData = parseNode(heading);
        if (!renderData.getContents().isEmpty()) {
            ParagraphRenderData headerParagraph = (ParagraphRenderData) renderData.getContents().get(0);
            paraOf.addParagraph(headerParagraph);
            paraOf.addText(Texts.of("").bookmark(evalText(headerParagraph)).create());
        }
        of.addParagraph(paraOf.create());
    }

    @Override
    public void visit(Paragraph paragraph) {
        of.addDocument(parseNode(paragraph));
    }

    private DocumentRenderData parseNode(Node block) {
        if (block instanceof FencedCodeBlock) {
            return parseFencedCodeBlock((FencedCodeBlock) block);
        }
        if (block instanceof IndentedCodeBlock) {
            return parseIndentedCodeBlock((IndentedCodeBlock) block);
        }
        DocumentBuilder docOf = Documents.of();
        Node node = block.getFirstChild();
        ParagraphBuilder paraOf = Paragraphs.of().left();
        while (node != null) {
            if (node instanceof Text) {
                paraOf.addText(((Text) node).getLiteral()).create();
            } else if (node instanceof StrongEmphasis || node instanceof Emphasis) {
                DocumentRenderData ret = parseNode(node);
                for (RenderData r : ret.getContents()) {
                    paraOf.addParagraph((ParagraphRenderData) r);
                }
            } else if (node instanceof Code) {
                TextBuilder ofCode = Texts.of(((Code) node).getLiteral()).style(style.getInlineCodeStyle());
                paraOf.addText(ofCode.create()).create();
            } else if (node instanceof SoftLineBreak || node instanceof HardLineBreak) {
                docOf.addParagraph(paraOf.create());
                paraOf = Paragraphs.of().left();
            } else if (node instanceof HtmlInline) {
                paraOf.addText(((HtmlInline) node).getLiteral()).create();
            } else if (node instanceof HtmlBlock) {
                paraOf.addText(((HtmlBlock) node).getLiteral()).create();
            } else if (node instanceof Image) {
                paraOf.addPicture(parsePicture((Image) node));
            } else if (node instanceof Link) {
                String destination = ((Link) node).getDestination();
                Node textLink = node.getFirstChild();
                if (textLink instanceof Text) {
                    paraOf.addText(Texts.of(((Text) textLink).getLiteral()).link(destination).create()).create();
                } else if (textLink instanceof Image) {
                    // TODO picture link
                    paraOf.addPicture(parsePicture((Image) textLink));
                }
            } else {
                LOGGER.warn("Current not support visit node: " + node);
            }
            node = node.getNext();
        }
        return docOf.addParagraph(paraOf.create()).create();
    }

    private PictureRenderData parsePicture(Image image) {
        String uri = image.getDestination();
        if (!uri.startsWith("http")) {
            // uri = style.getImagesDir() + uri;
            uri = Paths.get(style.getImagesDir(), uri).toFile().getAbsolutePath();
        }
        return Pictures.of(uri).altMeta(image.getTitle()).fitSize().create();
    }

    private DocumentRenderData parseIndentedCodeBlock(IndentedCodeBlock indentedCodeBlock) {
        return parseCode(indentedCodeBlock.getLiteral(), "java");
    }

    private DocumentRenderData parseFencedCodeBlock(FencedCodeBlock fencedCodeBlock) {
        return parseCode(fencedCodeBlock.getLiteral(), fencedCodeBlock.getInfo());

    }

    private DocumentRenderData parseCode(String code, String language) {
        HighlightRenderData highlight = new HighlightRenderData();
        highlight.setCode(code.endsWith("\n") ? code.substring(0, code.length() - 1) : code);
        highlight.setLanguage(language);
        highlight.setStyle(style.getHighlightStyle());
        try {
            DocumentRenderData apply = highlightConverter.convert(highlight);
            for (RenderData doc : apply.getContents()) {
                if (doc instanceof ParagraphRenderData) {
                    ParagraphStyle paragraphStyle = ((ParagraphRenderData) doc).getParagraphStyle();
                    if (null == paragraphStyle) {
                        paragraphStyle = new ParagraphStyle();
                        ((ParagraphRenderData) doc).setParagraphStyle(paragraphStyle);
                    }
                    paragraphStyle.setSpacing(1.0);
//                       paragraphStyle.setSpacing(0.0f);
//                       paragraphStyle.setSpacingRule(LineSpacingRule.AT_LEAST);
                }
            }
            return apply;
        } catch (Exception e) {
            throw new RenderException("Error Parse Code", e);
        }

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
        resetFormatIterator();
        of.addNumbering(parseList(orderedList, new NumberingRenderData(), 0));
    }

    @Override
    public void visit(BulletList bulletList) {
        resetFormatIterator();
        of.addNumbering(parseList(bulletList, new NumberingRenderData(), 0));
    }

    private void resetFormatIterator() {
        iterator = multiLevelFormat.iterator();
    }

    private NumberingRenderData parseList(ListBlock listBlock, NumberingRenderData numberingRenderData, int index) {
        List<NumberingFormat> formats = numberingRenderData.getFormats();
        if (listBlock instanceof BulletList) {
            formats.add(BULLET);
        } else if (null != iterator && iterator.hasNext()) {
            formats.add(iterator.next().build(index));
        } else {
            formats.add(DECIMAL_PARENTHESES_BUILDER.build(index));
        }

        List<NumberingItemRenderData> result = numberingRenderData.getItems();
        Node node = listBlock.getFirstChild();
        while (null != node) {
            if (node instanceof ListItem) {
                Node itemNode = node.getFirstChild();
                boolean first = true;
                while (null != itemNode) {
                    if (itemNode instanceof ListBlock) {
                        parseList((ListBlock) itemNode, numberingRenderData, index + 1);
//                    }  else if (first instanceof TableBlock) {
//                        TableRenderData tableRenderData = parseTable((TableBlock) first);
                    } else {
                        DocumentRenderData ret = parseNode(itemNode);
                        List<RenderData> contents = ret.getContents();
                        for (int i = 0; i < contents.size(); i++) {
                            RenderData content = contents.get(i);
                            ParagraphRenderData paragraph = (ParagraphRenderData) content;
                            if (first) {
                                result.add(new NumberingItemRenderData(index, paragraph));
                                first = false;
                            } else {
                                if (paragraph.getParagraphStyle() == null) {
                                    paragraph.setParagraphStyle(new ParagraphStyle());
                                }
                                paragraph.getParagraphStyle().setIndentLeftChars(index * 1.8);
                                result.add(new NumberingItemRenderData(-1, paragraph));
                            }
                        }
                    }
                    itemNode = itemNode.getNext();
                }
            }
            node = node.getNext();
        }
        return numberingRenderData;
    }

    @Override
    public void visit(BlockQuote blockQuote) {
        Node node = blockQuote.getFirstChild();
        boolean first = true;
        while (null != node) {
            if (node instanceof BlockQuote) {
                visit((BlockQuote) node);
            } else {
                DocumentRenderData ret = parseNode((Paragraph) node);
                List<RenderData> contents = ret.getContents();
                int size = contents.size();
                for (int i = 0; i < size; i++) {
                    RenderData content = contents.get(i);
                    ParagraphRenderData paragraph = (ParagraphRenderData) content;
                    paragraph.setParagraphStyle(style.getQuoteStyle());
                    ParagraphStyle paragraphStyle = paragraph.getParagraphStyle();
                    if (null == paragraphStyle) {
                        paragraphStyle = new ParagraphStyle();
                        paragraph.setParagraphStyle(paragraphStyle);
                    }

                    if (first) {
                        paragraphStyle.setSpacingBeforeLines(Double.valueOf(0.4));
                        first = false;
                    }
                    if (i == size - 1) {
                        paragraphStyle.setSpacingAfterLines(Double.valueOf(0.4));
                    }
                    of.addParagraph(paragraph);
                }
            }
            node = node.getNext();
        }
    }

    @Override
    public void visit(CustomBlock customBlock) {
        if (customBlock instanceof TableBlock) {
            visitTable((TableBlock) customBlock);
        } else {
            super.visit(customBlock);
        }
    }

    private void visitTable(TableBlock table) {
        of.addTable(parseTable(table));

    }

    private TableRenderData parseTable(TableBlock table) {
        TableBuilder tableOf = Tables.ofPercentWidth("100%");
        tableOf.cellMargin(0.19f, 0.19f, 0.19f, 0.19f);
        Node headOrBody = table.getFirstChild();
        while (null != headOrBody) {
            boolean isTH = headOrBody instanceof TableHead;
            Node row = headOrBody.getFirstChild();
            while (row != null) {
                RowBuilder rowOf = Rows.of();
                if (isTH) rowOf.rowStyle(style.getTableHeaderStyle());
                Node cell = row.getFirstChild();
                while (null != cell) {
                    CellBuilder cellOf = Cells.of();
                    DocumentRenderData ret = parseNode(cell);
                    if (ret.getContents().isEmpty()) {
                        rowOf.addCell(Cells.of("").create());
                    } else {
                        for (RenderData r : ret.getContents()) {
                            cellOf.addParagraph((ParagraphRenderData) r);
                        }
                        rowOf.addCell(cellOf.create());
                    }
                    cell = cell.getNext();
                }
                tableOf.addRow(rowOf.create());
                row = row.getNext();
            }
            headOrBody = headOrBody.getNext();
        }
        return tableOf.border(style.getTableBorderStyle()).create();

    }

    @Override
    public void visit(Image image) {
    }

    @Override
    public void visit(Text text) {
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
    }

    @Override
    public void visit(HtmlBlock node) {
        of.addParagraph(Paragraphs.of(((HtmlBlock) node).getLiteral()).create());
    }

    @Override
    protected void visitChildren(Node parent) {
        if (parent instanceof Document || parent instanceof Paragraph) {
            Node node = parent.getFirstChild();
            while (node != null) {
                // A subclass of this visitor might modify the node, resulting in getNext
                // returning a different node or no
                // node after visiting it. So get the next node before visiting.
                Node next = node.getNext();
                node.accept(this);
                node = next;
            }
        } else {
            LOGGER.warn("Current not support visit node: " + parent);
        }
    }

    public DocumentRenderData getResult() {
        return of.create();
    }

    private String evalText(ParagraphRenderData paragraph) {
        StringBuilder text = new StringBuilder();
        paragraph.getContents().stream().filter(r -> r instanceof TextRenderData).forEach(r -> {
            text.append(((TextRenderData) r).getText());
        });
        return text.toString();
    }

    private void resetHeaderNumberArray(int level) {
        headerNumberArray[level] += 1;
        for (int i = level + 1; i < headerNumberArray.length; i++) {
            headerNumberArray[i] = 0;
        }
    }

    private String getHeaderNumber(int level) {
        if (level == 1) return "";
        String str = StringUtils.join(Arrays.copyOfRange(headerNumberArray, 2, headerNumberArray.length), '.');
        String substring = str.substring(0, (level - 1) * 2 >= str.length() ? str.length() : (level - 1) * 2);
        return substring + " ";
    }

}
