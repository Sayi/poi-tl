package com.deepoove.poi.tl.issue;

import com.deepoove.poi.XWPFTemplate;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

public class Issue935 {

    @Test
    public void test() throws IOException {
        Map<Object, Object> data = new HashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(data);
        list.add(data);
        XWPFTemplate.compile("src/test/resources/issue/935.docx")
                    .render(Collections.singletonMap("datas", list))
                    .writeToFile("target/out_935.docx");
    }
}
