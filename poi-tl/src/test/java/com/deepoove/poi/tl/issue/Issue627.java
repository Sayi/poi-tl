package com.deepoove.poi.tl.issue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.SeriesRenderData;

public class Issue627 {

    @Test
    public void test627() throws Exception {
        String[] nameArray = { "次数C", "影响时长C" };

        ChartMultiSeriesRenderData chart = Charts.ofComboSeries("BOMC不可提供监控服务情况", nameArray).create();

        Map<String, Integer[]> typeMap = new HashMap<>();
        Integer[] times = { 1, 2,  };
        Integer[] minutes = { 223, 334 };
        typeMap.put("time1", times);
        typeMap.put("time2", minutes);

        List<SeriesRenderData> countDatas = new ArrayList<>();
        for (String typeName : typeMap.keySet()) {
            SeriesRenderData renderData = new SeriesRenderData(typeName, typeMap.get(typeName));
            renderData.setComboType(SeriesRenderData.ComboType.LINE);
            countDatas.add(renderData);
        }

        chart.setSeriesDatas(countDatas);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("serviceChart", chart);

        XWPFTemplate.compile("src/test/resources/issue/627.docx").render(resultMap).writeToFile("out_issue_627.docx");

    }

}
