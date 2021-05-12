package com.deepoove.poi.tl.policy.ref;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.Charts;

@DisplayName("Chart test case")
public class ChartReferencePolicyTest {

    @Test
    public void testChart() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>();
        ChartMultiSeriesRenderData chart = createMultiSeriesChart();
        datas.put("barChart", chart);
        datas.put("VBarChart", chart);
        datas.put("3dBarChart", chart);
        datas.put("lineChart", chart);
        datas.put("redarChart", chart);
        datas.put("areaChart", chart);

        ChartMultiSeriesRenderData combChart = createComboChart();
        datas.put("combChart", combChart);

        ChartSingleSeriesRenderData pie = createSingleSeriesChart();
        datas.put("pieChart", pie);
        datas.put("doughnutChart", pie);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/reference_chart.docx").render(datas);
        template.writeToFile("out_reference_chart.docx");
    }

    private ChartSingleSeriesRenderData createSingleSeriesChart() {
        return Charts.ofSingleSeries("ChartTitle", new String[] { "俄罗斯", "加拿大", "美国", "中国", "巴西", "澳大利亚", "印度" })
                .series("countries", new Integer[] { 17098242, 9984670, 9826675, 9596961, 8514877, 7741220, 3287263 })
                .create();
    }

    private ChartMultiSeriesRenderData createMultiSeriesChart() {
        return Charts
                .ofMultiSeries("CustomTitle",
                        new String[] { "中文", "English", "日本語", "português", "中文", "English", "日本語", "português" })
                .addSeries("countries", new Double[] { 15.0, 6.0, 18.0, 231.0, 150.0, 6.0, 118.0, 31.0 })
                .addSeries("speakers", new Double[] { 223.0, 119.0, 154.0, 142.0, 223.0, 119.0, 54.0, 42.0 })
                .addSeries("youngs", new Double[] { 323.0, 89.0, 54.0, 42.0, 223.0, 119.0, 54.0, 442.0 }).create();
    }

    private ChartMultiSeriesRenderData createComboChart() {
        return Charts.ofComboSeries("ComboChartTitle", new String[] { "中文", "English", "日本語", "português" })
                .addBarSeries("countries", new Double[] { 15.0, 6.0, 18.0, 231.0 })
                .addBarSeries("speakers", new Double[] { 223.0, 119.0, 154.0, 142.0 })
                .addBarSeries("NewBar", new Double[] { 223.0, 119.0, 154.0, 142.0 })
                .addLineSeries("youngs", new Double[] { 323.0, 89.0, 54.0, 42.0 })
                .addLineSeries("NewLine", new Double[] { 123.0, 59.0, 154.0, 42.0 }).create();
    }

}
