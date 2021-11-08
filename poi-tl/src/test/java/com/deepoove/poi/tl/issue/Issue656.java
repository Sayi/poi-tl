package com.deepoove.poi.tl.issue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.Charts.ChartMultis;

public class Issue656 {

    @SuppressWarnings("serial")
    @Test
    public void test656() throws Exception {
        ChartMultis ofMultiSeries = Charts.ofMultiSeries("ChartTitle", new String[] { "中文", "English" });

        for (int i = 0; i < 100; i++) {
            ofMultiSeries.addSeries("countries" + i, new Double[] { 15.0 + i, 6.0 + i });
        }
        ChartMultiSeriesRenderData chart = ofMultiSeries.create();

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/656.docx");
        template.render(new HashMap<String, Object>() {
            {
                put("chart", chart);
            }
        }).writeToFile("out_issue_656.docx");

    }

}
