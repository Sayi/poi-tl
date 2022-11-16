package com.deepoove.poi.tl.issue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

public class Issue898 {

    @Test
    public void test898() throws IOException {
        Map<String, Object> data = new HashMap<>();
//        data.put("name", "Sayi");
        XWPFTemplate.compile("src/test/resources/issue/898.docx").render(data).writeToFile("target/out_898.docx");
    }
}
