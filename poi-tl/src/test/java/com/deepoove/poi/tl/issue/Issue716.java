package com.deepoove.poi.tl.issue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.Charts;

public class Issue716 {

    @Test
    public void test716() throws Exception {

        ChartSingleSeriesRenderData pie = Charts
                .ofSingleSeries("自律企业", new String[] { "购买服务企业", "未购买服务企业", "有隐患企业", "无隐患企业" })
                .series("aaa", new Integer[] { 10, 3, 30, 5 })
                .create();
        Map<String, Object> param = new HashMap<>();
        param.put("cccc", pie);
        XWPFTemplate.compile("src/test/resources/issue/716.docx").render(param).writeToFile("target/out_issue_716.docx");

    }

}
