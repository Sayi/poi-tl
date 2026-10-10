package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("Table render behavior")
public class TableRenderBehaviorTest {

    @Test
    public void rendersTableRowsAndCells() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{#var}}"),
                RenderAsserts.mapOf("var", Tables.of(new String[][] { { "name", "qty" }, { "纸", "4" } }).create()));

        XWPFTable table = document.getTables().get(0);
        assertEquals(2, table.getNumberOfRows());
        assertEquals("name", RenderAsserts.cellText(table, 0, 0));
        assertEquals("qty", RenderAsserts.cellText(table, 0, 1));
        assertEquals("纸", RenderAsserts.cellText(table, 1, 0));
        assertEquals("4", RenderAsserts.cellText(table, 1, 1));
        RenderAsserts.assertNoTags(document);
        RenderAsserts.assertEveryCellHasParagraph(document);
        document.close();
    }

    @Test
    public void clearsTableTagWhenDataIsMissing() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{#var}}"), new HashMap<String, Object>());

        assertEquals(0, document.getTables().size());
        assertEquals("", RenderAsserts.paragraphText(document));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void loopsDataRowsBelowTheHeader() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.removeBodyElement(0);
        XWPFTable table = template.createTable(2, 2);
        table.getRow(0).getCell(0).setText("名称");
        table.getRow(0).getCell(1).setText("数量");
        setCellTag(table, 1, 0, "{{goods}}[name]");
        setCellTag(table, 1, 1, "[qty]");

        Configure configure = Configure.builder().bind("goods", new LoopRowTableRenderPolicy(true)).build();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("goods", Arrays.asList(item("墙纸", "4"), item("油漆", "2"), item("灯", "1")));

        XWPFTemplate rendered = XWPFTemplate.compile(template, configure).render(model);
        XWPFDocument document = XWPFTestSupport.readNewDocument(rendered);
        XWPFTable out = document.getTables().get(0);
        assertEquals(4, out.getNumberOfRows());
        assertEquals("名称", RenderAsserts.cellText(out, 0, 0));
        assertEquals("墙纸", RenderAsserts.cellText(out, 1, 0));
        assertEquals("4", RenderAsserts.cellText(out, 1, 1));
        assertEquals("油漆", RenderAsserts.cellText(out, 2, 0));
        assertEquals("灯", RenderAsserts.cellText(out, 3, 0));
        assertEquals("1", RenderAsserts.cellText(out, 3, 1));
        RenderAsserts.assertNoTags(document);
        RenderAsserts.assertEveryCellHasParagraph(document);
        document.close();
    }

    @Test
    public void removesTemplateRowWhenLoopIsEmpty() throws Exception {
        XWPFDocument template = new XWPFDocument();
        template.removeBodyElement(0);
        XWPFTable table = template.createTable(2, 1);
        table.getRow(0).getCell(0).setText("名称");
        setCellTag(table, 1, 0, "{{goods}}[name]");

        Configure configure = Configure.builder().bind("goods", new LoopRowTableRenderPolicy(true)).build();
        XWPFTemplate rendered = XWPFTemplate.compile(template, configure)
                .render(RenderAsserts.mapOf("goods", Collections.emptyList()));
        XWPFDocument document = XWPFTestSupport.readNewDocument(rendered);

        XWPFTable out = document.getTables().get(0);
        assertEquals(1, out.getNumberOfRows());
        assertEquals("名称", RenderAsserts.cellText(out, 0, 0));
        RenderAsserts.assertNoTags(document);
        RenderAsserts.assertEveryCellHasParagraph(document);
        document.close();
    }

    private static Map<String, String> item(String name, String qty) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("name", name);
        row.put("qty", qty);
        return row;
    }

    private static void setCellTag(XWPFTable table, int row, int col, String text) {
        RenderAsserts.setCellText(table.getRow(row).getCell(col), text);
    }

}
