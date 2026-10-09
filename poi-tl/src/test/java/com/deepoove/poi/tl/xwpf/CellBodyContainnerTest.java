package com.deepoove.poi.tl.xwpf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.util.ParagraphUtils;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.CellBodyContainer;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

public class CellBodyContainnerTest {

    BodyContainer container;
    NiceXWPFDocument document = new NiceXWPFDocument();
    XWPFTableCell cell;
    XWPFParagraph paragraph;
    XWPFTable table;

    @BeforeEach
    public void init() {
        XWPFTable xwpfTable = document.createTable();
        XWPFTableRow createRow = xwpfTable.createRow();
        cell = createRow.createCell();
        container = new CellBodyContainer(cell);

        // cell.addParagraph()有bug，不会更新bodyElements

        // p-t-p-t-p-t
        XWPFParagraph insertpos = cell.getParagraphArray(0);
        container.insertNewParagraph(insertpos.getCTP().newCursor());
        container.insertNewTable(insertpos.createRun(), 2, 2);
        paragraph = container.insertNewParagraph(insertpos.getCTP().newCursor());
        table = container.insertNewTable(insertpos.createRun(), 2, 2);
        container.insertNewParagraph(insertpos.getCTP().newCursor());
        container.insertNewTable(insertpos.createRun(), 2, 2);

        container.removeBodyElement(6);

    }

    @AfterEach
    public void destroy() throws IOException {
        document.close();
    }

    @Test
    void testGetPosOfParagraphCTP() {
        assertEquals(container.getPosOfParagraphCTP(paragraph.getCTP()), 2);
    }

    @Test
    void testRemoveBodyElement() {
        container.removeBodyElement(3);
        container.removeBodyElement(2);

        assertEquals(cell.getTables().size(), 2);
        assertEquals(cell.getParagraphs().size(), 2);
    }

    @Test
    void testGetPosOfParagraph() {
        assertEquals(container.getPosOfParagraph(paragraph), 2);
    }

    @Test
    void testGetBodyElements() {
        List<IBodyElement> bodyElements = container.getBodyElements();
        assertEquals(bodyElements.size(), 6);
    }

    @Test
    void testInsertNewParagraphByCursor() {
        XmlCursor newCursor = paragraph.getCTP().newCursor();
        container.insertNewParagraph(newCursor);
        assertEquals(cell.getParagraphs().size(), 4);
        assertEquals(container.getPosOfParagraph(paragraph), 3);

    };

    @Test
    void testInsertNewParagraph() {
        XWPFRun createRun = paragraph.createRun();
        container.insertNewParagraph(createRun);
        assertEquals(cell.getParagraphs().size(), 4);
        assertEquals(container.getPosOfParagraph(paragraph), 3);
    }

    @Test
    void testGetParaPos() {
        assertEquals(container.getParaPos(paragraph), 1);
    }

    @Test
    void testInsertNewTbl() {
        XmlCursor newCursor = paragraph.getCTP().newCursor();
        container.insertNewTbl(newCursor);

        assertEquals(cell.getTables().size(), 4);
        assertEquals(container.getPosOfParagraph(paragraph), 3);
    }

    @Test
    void testGetTablePos() {
        assertEquals(container.getTablePos(table), 1);
    }

    @Test
    void testInsertNewTable() {
        XWPFRun createRun = paragraph.createRun();
        container.insertNewTable(createRun, 2, 2);
        assertEquals(cell.getTables().size(), 4);
        assertEquals(container.getPosOfParagraph(paragraph), 3);

    }

    @Test
    void testClearPlaceholder() {
        XWPFRun createRun = paragraph.createRun();
        container.clearPlaceholder(createRun);

        assertEquals(cell.getParagraphs().size(), 2);
    }

    @Test
    void testClearPlaceholder2() {
        XWPFRun createRun = paragraph.createRun();
        createRun.setText("123");
        createRun = paragraph.createRun();
        createRun.setText("456");

        container.clearPlaceholder(createRun);

        assertEquals(cell.getParagraphs().size(), 3);
    }

    @Test
    void testTrimLineNullSafe() {
        assertEquals("", ParagraphUtils.trimLine((String) null));
        assertEquals("", ParagraphUtils.trimLine((XWPFParagraph) null));
    }

    @Test
    void testHavePageBreakWithHardBreak() {
        XWPFParagraph p = cell.addParagraph();
        assertFalse(ParagraphUtils.havePageBreak(p));
        XWPFRun run = p.createRun();
        run.addBreak(BreakType.PAGE);
        assertTrue(ParagraphUtils.havePageBreak(p));
    }

    @Test
    void testClearPlaceholderNeverDeletesLastParagraph() {
        NiceXWPFDocument doc = new NiceXWPFDocument();
        XWPFTable t = doc.createTable(1, 1);
        XWPFTableCell singleCell = t.getRow(0).getCell(0);
        assertEquals(1, singleCell.getParagraphs().size());

        CellBodyContainer cellContainer = new CellBodyContainer(singleCell);
        XWPFParagraph singleParagraph = singleCell.getParagraphArray(0);
        XWPFRun run = singleParagraph.createRun();
        run.setText("placeholder");

        cellContainer.clearPlaceholder(run, true);
        assertEquals(1, singleCell.getParagraphs().size(), "Table cell must retain at least one paragraph according to OpenXML spec");
    }

}
