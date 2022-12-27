package com.deepoove.poi.tl.issue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

public class Issue914 {

    @SuppressWarnings("serial")
    @Test
    public void test914() throws IOException {

        List<Integer> goods = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            goods.add(i);
        }

        XWPFTemplate.compile("src/test/resources/issue/914.docx").render(new HashMap<String, Object>() {
            {
                put("goods", goods);
            }
        }).writeToFile("target/out_914.docx");
    }
}
