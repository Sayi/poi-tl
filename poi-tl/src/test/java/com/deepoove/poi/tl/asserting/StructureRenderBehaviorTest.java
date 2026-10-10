package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.policy.DocumentRenderPolicy;
import com.deepoove.poi.policy.ListRenderPolicy;
import com.deepoove.poi.policy.ParagraphRenderPolicy;

@DisplayName("Paragraph, list and document structure")
public class StructureRenderBehaviorTest {

    @Test
    public void paragraphKeepsSeveralStyledRunsAndAlignment() throws Exception {
        Configure configure = Configure.builder().bind("para", new ParagraphRenderPolicy()).build();
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{para}}"), configure, RenderAsserts.mapOf("para",
                Paragraphs.of().addText(Texts.of("粗").bold().create()).addText("细").center().create()));

        XWPFParagraph paragraph = document.getParagraphs().get(0);
        assertEquals("粗细", paragraph.getText());
        assertEquals(ParagraphAlignment.CENTER, paragraph.getAlignment());
        assertTrue(paragraph.getRuns().get(0).isBold());
        assertEquals("细", paragraph.getRuns().get(1).text());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void listPolicyRendersTextItemsAsParagraphs() throws Exception {
        Configure configure = Configure.builder().bind("items", new ListRenderPolicy()).build();
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{items}}"), configure,
                RenderAsserts.mapOf("items", Arrays.asList(Texts.of("甲").create(), Texts.of("乙").create())));

        assertEquals("甲", document.getParagraphArray(0).getText());
        assertEquals("乙", document.getParagraphArray(1).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void emptyListClearsThePlaceholderParagraph() throws Exception {
        Configure configure = Configure.builder().bind("items", new ListRenderPolicy()).build();
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("前{{items}}"), configure,
                RenderAsserts.mapOf("items", Arrays.asList()));

        assertEquals("前", RenderAsserts.paragraphText(document));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void documentPolicyRendersParagraphThenTable() throws Exception {
        Configure configure = Configure.builder().bind("doc", new DocumentRenderPolicy()).build();
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{doc}}"), configure,
                RenderAsserts.mapOf("doc", Documents.of()
                        .addParagraph(Paragraphs.of("标题").create())
                        .addTable(Tables.of(new String[][] { { "甲", "乙" } }).create())
                        .create()));

        assertEquals("标题", document.getParagraphArray(0).getText());
        assertEquals(1, document.getTables().size());
        assertEquals("甲", RenderAsserts.cellText(document.getTables().get(0), 0, 0));
        assertEquals("乙", RenderAsserts.cellText(document.getTables().get(0), 0, 1));
        RenderAsserts.assertNoTags(document);
        RenderAsserts.assertEveryCellHasParagraph(document);
        document.close();
    }

    @Test
    public void emptyDocumentDataClearsTheTag() throws Exception {
        Configure configure = Configure.builder().bind("doc", new DocumentRenderPolicy()).build();
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{doc}}"), configure,
                RenderAsserts.mapOf("doc", Documents.of().create()));

        assertEquals("", RenderAsserts.paragraphText(document));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void mergedTableCellsShareAGridSpan() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{#var}}"),
                RenderAsserts.mapOf("var", Tables.of(Rows.of("甲", "乙", "丙").create(), Rows.of("1", "2", "3").create())
                        .mergeRule(com.deepoove.poi.data.MergeCellRule.builder()
                                .map(com.deepoove.poi.data.MergeCellRule.Grid.of(0, 0),
                                        com.deepoove.poi.data.MergeCellRule.Grid.of(0, 1))
                                .build())
                        .create()));

        assertEquals("甲", RenderAsserts.cellText(document.getTables().get(0), 0, 0));
        assertTrue(document.getTables().get(0).getRow(0).getCell(0).getCTTc().getTcPr().getGridSpan().getVal().intValue() >= 2);
        RenderAsserts.assertEveryCellHasParagraph(document);
        document.close();
    }

}
