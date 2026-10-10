package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Header, footer and table-cell tags")
public class HeaderFooterBehaviorTest {

    @Test
    public void rendersTagsInsideHeaderAndFooter() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("正文{{body}}");
        XWPFHeader header = template.createHeader(org.apache.poi.wp.usermodel.HeaderFooterType.DEFAULT);
        header.createParagraph().createRun().setText("页眉{{title}}");
        XWPFFooter footer = template.createFooter(org.apache.poi.wp.usermodel.HeaderFooterType.DEFAULT);
        footer.createParagraph().createRun().setText("页脚{{page}}");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("body", "主体");
        model.put("title", "poi-tl");
        model.put("page", "1");
        XWPFDocument document = RenderAsserts.render(template, model);

        assertEquals("正文主体", document.getParagraphs().get(0).getText());
        assertEquals("页眉poi-tl", document.getHeaderList().get(0).getParagraphs().get(0).getText());
        assertEquals("页脚1", document.getFooterList().get(0).getParagraphs().get(0).getText());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void rendersATagInsideAnExistingTableCell() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.removeBodyElement(0);
        XWPFTable table = template.createTable(1, 2);
        RenderAsserts.setCellText(table.getRow(0).getCell(0), "名");
        RenderAsserts.setCellText(table.getRow(0).getCell(1), "{{value}}");

        XWPFDocument document = RenderAsserts.render(template, RenderAsserts.mapOf("value", "墙纸"));

        assertEquals("墙纸", RenderAsserts.cellText(document.getTables().get(0), 0, 1));
        RenderAsserts.assertNoTags(document);
        RenderAsserts.assertEveryCellHasParagraph(document);
        document.close();
    }

    @Test
    public void falseIfInHeaderDropsOnlyTheBlock() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.createParagraph().createRun().setText("正文");
        XWPFHeader header = template.createHeader(org.apache.poi.wp.usermodel.HeaderFooterType.DEFAULT);
        header.createParagraph().createRun().setText("固定{{?show}}隐藏{{/show}}");

        XWPFDocument document = RenderAsserts.render(template, RenderAsserts.mapOf("show", false));
        String headerText = document.getHeaderList().get(0).getParagraphs().get(0).getText();
        assertEquals("固定", headerText);
        assertTrue(!headerText.contains("隐藏"));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

}
