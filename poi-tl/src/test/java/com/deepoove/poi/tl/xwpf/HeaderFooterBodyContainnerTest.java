package com.deepoove.poi.tl.xwpf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.HeaderFooterBodyContainer;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

public class HeaderFooterBodyContainnerTest {

    BodyContainer container;
    NiceXWPFDocument document = new NiceXWPFDocument();
    XWPFHeaderFooter header;
    XWPFParagraph paragraph;
    XWPFTable table;

    @BeforeEach
    public void init() {
        header = document.createHeader(HeaderFooterType.DEFAULT);
        container = new HeaderFooterBodyContainer(header);
        
        // p-t-p-t-p-t
        header.createParagraph();
        header.createTable(2, 2);
        paragraph = header.createParagraph();
        table = header.createTable(2, 2);
        header.createParagraph();
        header.createTable(2, 2);

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

        assertEquals(header.getTables().size(), 2);
        assertEquals(header.getParagraphs().size(), 2);
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
        assertEquals(header.getParagraphs().size(), 4);
        assertEquals(container.getPosOfParagraph(paragraph), 3);

    };

    @Test
    void testInsertNewParagraph() {
        XWPFRun createRun = paragraph.createRun();
        container.insertNewParagraph(createRun);
        assertEquals(header.getParagraphs().size(), 4);
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

        assertEquals(header.getTables().size(), 4);
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
        assertEquals(header.getTables().size(), 4);
        assertEquals(container.getPosOfParagraph(paragraph), 3);

    }

    @Test
    void testClearPlaceholder() {
        XWPFRun createRun = paragraph.createRun();
        container.clearPlaceholder(createRun);

        assertEquals(header.getParagraphs().size(), 2);
    }

    @Test
    void testClearPlaceholder2() {
        XWPFRun createRun = paragraph.createRun();
        createRun.setText("123");
        createRun = paragraph.createRun();
        createRun.setText("456");

        container.clearPlaceholder(createRun);

        assertEquals(header.getParagraphs().size(), 3);
    }

}
