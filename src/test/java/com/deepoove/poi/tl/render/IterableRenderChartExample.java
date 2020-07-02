package com.deepoove.poi.tl.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

@DisplayName("Foreach chart example")
public class IterableRenderChartExample {

    @BeforeEach
    public void init() {}

    @SuppressWarnings("serial")
    @Test
    public void testRenderChart() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                put("name", "Poi-tl");
            }
        };

        List<Map<String, Object>> mores = new ArrayList<Map<String, Object>>();
        mores.add(data);
        mores.add(data);
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("mores", mores);

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_chart.docx");
        template.render(datas);
        template.writeToFile("out_iterable_chart.docx");
    }

}
