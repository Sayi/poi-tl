package com.deepoove.poi.tl.issue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

public class Issue834 {

    @Test
    public void test() throws IOException {
        Map<Object, Object> model = new HashMap<>();
        model.put("studyTitle", "test\r\ntest");
        XWPFTemplate.compile("src/test/resources/issue/834.docx").render(model).writeToFile("target/out_834.docx");
    }

}
