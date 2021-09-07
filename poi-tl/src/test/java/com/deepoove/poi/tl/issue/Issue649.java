package com.deepoove.poi.tl.issue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.Includes;

public class Issue649 {

    @SuppressWarnings("serial")
    @Test
    public void test649() throws Exception {
        ChartSingleSeriesRenderData chartSingleSeriesRenderData = Charts
                .ofSingleSeries("123", new String[] { "第一季度", "第二季度", "第三季度", "第四季度" })
                .series("纯利润", new Integer[] { 15, 30, 5, 60 })
                .create();

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/649.docx");
        template.render(new HashMap<String, Object>() {
            {
                put("sub", Includes.ofLocal("src/test/resources/issue/649_MERGE.docx")
                        .setRenderModel(new HashMap<String, Object>() {
                            {
                                put("xxx", chartSingleSeriesRenderData);
                            }
                        })
                        .create());
            }
        }).writeToFile("out_issue_649.docx");

    }

}
