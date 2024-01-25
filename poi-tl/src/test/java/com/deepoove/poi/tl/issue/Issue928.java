package com.deepoove.poi.tl.issue;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Issue928 {

    @Test
    public void test928() throws IOException {
        Map<String, Object> model = new LinkedHashMap<>();
        model.put("show1", false);
        XWPFTemplate.compile("src/test/resources/issue/928.docx").render(model).writeToFile("target/out_928.docx");
    }
}
