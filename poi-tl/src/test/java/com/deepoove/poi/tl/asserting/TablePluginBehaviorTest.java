package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopColumnTableRenderPolicy;
import com.deepoove.poi.plugin.table.RemoveTableColumnRenderPolicy;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("Table plugin structure")
public class TablePluginBehaviorTest {

    @Test
    public void loopColumnExpandsEachItemIntoAColumn() throws Exception {
        XWPFDocument template = table(2, 2);
        RenderAsserts.setCellText(template.getTables().get(0).getRow(0).getCell(0), "名称");
        RenderAsserts.setCellText(template.getTables().get(0).getRow(1).getCell(0), "数量");
        setWidth(template.getTables().get(0).getRow(0).getCell(1), 1200);
        setWidth(template.getTables().get(0).getRow(1).getCell(1), 1200);
        grid(template.getTables().get(0), 1200, 1200);
        RenderAsserts.setCellText(template.getTables().get(0).getRow(0).getCell(1), "{{cols}}[name]");
        RenderAsserts.setCellText(template.getTables().get(0).getRow(1).getCell(1), "[qty]");

        Configure configure = Configure.builder().bind("cols", new LoopColumnTableRenderPolicy(true)).build();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("cols", Arrays.asList(item("墙纸", "4"), item("油漆", "2")));
        XWPFDocument document = render(template, configure, model);

        XWPFTable table = document.getTables().get(0);
        assertEquals(3, table.getRow(0).getTableCells().size());
        assertEquals("名称", RenderAsserts.cellText(table, 0, 0));
        assertEquals("墙纸", RenderAsserts.cellText(table, 0, 1));
        assertEquals("油漆", RenderAsserts.cellText(table, 0, 2));
        assertEquals("4", RenderAsserts.cellText(table, 1, 1));
        assertEquals("2", RenderAsserts.cellText(table, 1, 2));
        RenderAsserts.assertNoTags(document);
        RenderAsserts.assertEveryCellHasParagraph(document);
        document.close();
    }

    @Test
    public void emptyLoopColumnRemovesTheTemplateColumn() throws Exception {
        XWPFDocument template = table(1, 2);
        RenderAsserts.setCellText(template.getTables().get(0).getRow(0).getCell(0), "保留");
        setWidth(template.getTables().get(0).getRow(0).getCell(1), 800);
        grid(template.getTables().get(0), 800, 800);
        RenderAsserts.setCellText(template.getTables().get(0).getRow(0).getCell(1), "{{cols}}[name]");

        Configure configure = Configure.builder().bind("cols", new LoopColumnTableRenderPolicy(true)).build();
        XWPFDocument document = render(template, configure, RenderAsserts.mapOf("cols", Arrays.asList()));

        XWPFTable table = document.getTables().get(0);
        assertEquals(1, table.getRow(0).getTableCells().size());
        assertEquals("保留", RenderAsserts.cellText(table, 0, 0));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void removeColumnDropsOnlyTheMarkedColumn() throws Exception {
        XWPFDocument template = table(2, 3);
        XWPFTable source = template.getTables().get(0);
        RenderAsserts.setCellText(source.getRow(0).getCell(0), "甲");
        RenderAsserts.setCellText(source.getRow(0).getCell(1), "{{drop}}");
        RenderAsserts.setCellText(source.getRow(0).getCell(2), "丙");
        RenderAsserts.setCellText(source.getRow(1).getCell(0), "1");
        RenderAsserts.setCellText(source.getRow(1).getCell(1), "删");
        RenderAsserts.setCellText(source.getRow(1).getCell(2), "3");

        Configure configure = Configure.builder().bind("drop", new RemoveTableColumnRenderPolicy()).build();
        XWPFDocument document = render(template, configure, RenderAsserts.mapOf("drop", true));

        XWPFTable table = document.getTables().get(0);
        assertEquals(2, table.getRow(0).getTableCells().size());
        assertEquals("甲", RenderAsserts.cellText(table, 0, 0));
        assertEquals("丙", RenderAsserts.cellText(table, 0, 1));
        assertEquals("1", RenderAsserts.cellText(table, 1, 0));
        assertEquals("3", RenderAsserts.cellText(table, 1, 1));
        assertFalse(table.getText().contains("删"));
        document.close();
    }

    private static XWPFDocument table(int rows, int cols) {
        XWPFDocument document = new XWPFDocument();
        document.removeBodyElement(0);
        document.createTable(rows, cols);
        return document;
    }

    private static void grid(XWPFTable table, int... widths) {
        CTTblGrid grid = table.getCTTbl().getTblGrid();
        if (grid == null) {
            grid = table.getCTTbl().addNewTblGrid();
        }
        while (grid.sizeOfGridColArray() > 0) {
            grid.removeGridCol(0);
        }
        for (int width : widths) {
            grid.addNewGridCol().setW(java.math.BigInteger.valueOf(width));
        }
    }

    private static void setWidth(XWPFTableCell cell, int dxa) {
        cell.getCTTc().addNewTcPr().addNewTcW().setW(java.math.BigInteger.valueOf(dxa));
        cell.getCTTc().getTcPr().getTcW().setType(STTblWidth.DXA);
    }

    private static XWPFDocument render(XWPFDocument template, Configure configure, Object model) throws Exception {
        return XWPFTestSupport.readNewDocument(XWPFTemplate.compile(template, configure).render(model));
    }

    private static Map<String, String> item(String name, String qty) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("name", name);
        row.put("qty", qty);
        return row;
    }

}
