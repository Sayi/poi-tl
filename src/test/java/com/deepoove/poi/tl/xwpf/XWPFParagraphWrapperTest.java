package com.deepoove.poi.tl.xwpf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFieldRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;

import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

public class XWPFParagraphWrapperTest {

    @Test
    public void testWrapper() throws FileNotFoundException, IOException {

        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();

        XWPFParagraphWrapper wrapper = new XWPFParagraphWrapper(paragraph);
        XWPFRun hyperRun = wrapper.insertNewHyperLinkRun(0, "http:deepoove.com");
        hyperRun.setText("Deepoove0");

        XWPFRun newRun = wrapper.insertNewRun(0);
        newRun.setText("website0");
        newRun = wrapper.insertNewRun(0);
        newRun.setText("website1");
        newRun = wrapper.insertNewRun(0);
        newRun.setText("website2");

        hyperRun = wrapper.insertNewHyperLinkRun(0, "http:deepoove.com");
        hyperRun.setText("Deepoove1");

        XWPFFieldRun fieldRun = wrapper.insertNewField(0);
        CTSimpleField ctField = fieldRun.getCTField();
        ctField.setInstr(" CREATEDATE  \\* MERGEFORMAT ");
        fieldRun.setText("2019");

        assertEquals(paragraph.getRuns().size(), 6);

        for (int i = 6; i >= 0; i--) {
            wrapper.removeRun(i);
        }
        assertEquals(paragraph.getRuns().size(), 0);

        doc.close();

    }

}
