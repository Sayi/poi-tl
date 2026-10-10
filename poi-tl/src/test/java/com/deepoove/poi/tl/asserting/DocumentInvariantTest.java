package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;

@DisplayName("Rendered document invariants")
public class DocumentInvariantTest {

    private static final byte[] PNG = new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x01 };

    @Test
    public void cellRenderKeepsAtLeastOneParagraph() throws Exception {
        XWPFDocument template = new XWPFDocument();
        XWPFTable table = template.createTable(1, 1);
        XWPFTableCell cell = table.getRow(0).getCell(0);
        RenderAsserts.setCellText(cell, "{{name}}");

        XWPFDocument document = RenderAsserts.render(template, RenderAsserts.mapOf("name", "单元格"));
        assertEquals("单元格", document.getTables().get(0).getRow(0).getCell(0).getText());
        RenderAsserts.assertEveryCellHasParagraph(document);
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void generatedTableCellsKeepAParagraph() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{#var}}"),
                RenderAsserts.mapOf("var", Tables.of(Rows.of("甲", "乙").create(), Rows.of("1", "2").create()).create()));

        RenderAsserts.assertEveryCellHasParagraph(document);
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void twoDocumentsDoNotSharePictureParts() throws Exception {
        Map<String, Object> model = pictureModel();
        XWPFDocument first = RenderAsserts.render(RenderAsserts.paragraph("{{@img}}"), model);
        XWPFDocument second = RenderAsserts.render(RenderAsserts.paragraph("{{@img}}"), pictureModel());

        assertEquals(1, first.getAllPictures().size());
        assertEquals(1, second.getAllPictures().size());
        assertTrue(first.getAllPictures().get(0).getPackagePart().getPartName().getName().contains("/word/media/"));
        assertTrue(second.getAllPictures().get(0).getPackagePart().getPartName().getName().contains("/word/media/"));
        RenderAsserts.assertNoTags(first);
        RenderAsserts.assertNoTags(second);
        first.close();
        second.close();
    }

    private static Map<String, Object> pictureModel() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("img", Pictures.ofBytes(PNG, PictureType.PNG).size(16, 16).create());
        return model;
    }

}
