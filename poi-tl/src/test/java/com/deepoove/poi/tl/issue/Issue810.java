package com.deepoove.poi.tl.issue;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

public class Issue810 {

    @Test
    public void test810() throws IOException {
        Map<String, Object> mainData = new HashMap<>();
        Map<String, Object> rowData = new HashMap<>();
        rowData.put("data1", "data1");
        rowData.put("data2", "data2");
        rowData.put("data3", "data3");
        mainData.put("rows", Collections.singletonList(rowData));
        XWPFTemplate render = XWPFTemplate.compile("src/test/resources/issue/810.docx").render(mainData);
        XWPFDocument document = render.getXWPFDocument();
        XWPFParagraph paragraph = document.getParagraphArray(0);
        Assertions.assertEquals(paragraph.getText(), "data1\tdata1\tdata1");
    }
}
