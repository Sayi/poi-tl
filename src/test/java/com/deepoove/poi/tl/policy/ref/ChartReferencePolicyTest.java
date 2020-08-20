package com.deepoove.poi.tl.policy.ref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData.ComboType;

@DisplayName("Chart test case")
public class ChartReferencePolicyTest {

    @SuppressWarnings("serial")
    @Test
    public void testChart() throws Exception {

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/reference_chart.docx")
                .render(new HashMap<String, Object>() {
                    {

                        ChartMultiSeriesRenderData chart = createMultiSeriesChart();
                        put("barChart", chart);
                        put("VBarChart", chart);
                        put("3dBarChart", chart);
                        put("lineChart", chart);
                        put("redarChart", chart);
                        put("areaChart", chart);

                        ChartMultiSeriesRenderData combChart = createComboChart();
                        put("combChart", combChart);

                        ChartSingleSeriesRenderData pie = createSingleSeriesChart();
                        put("pieChart", pie);
                        put("doughnutChart", pie);

                    }

                    private ChartSingleSeriesRenderData createSingleSeriesChart() {
                        ChartSingleSeriesRenderData pie = new ChartSingleSeriesRenderData();
                        pie.setChartTitle("ChartTitle");
                        pie.setCategories(new String[] { "俄罗斯", "加拿大", "美国", "中国", "巴西", "澳大利亚", "印度" });
                        SeriesRenderData s0 = new SeriesRenderData();
                        s0.setName("countries");
                        s0.setValues(new Integer[] { 17098242, 9984670, 9826675, 9596961, 8514877, 7741220, 3287263 });
                        pie.setSeriesData(s0);
                        return pie;
                    }

                    private ChartMultiSeriesRenderData createMultiSeriesChart() {
                        ChartMultiSeriesRenderData chart = new ChartMultiSeriesRenderData();
                        chart.setChartTitle("ChartTitle");
                        chart.setCategories(new String[] { "中文", "English", "日本語", "português", "中文", "English", "日本語",
                                "português" });
                        List<SeriesRenderData> seriesRenderData = new ArrayList<SeriesRenderData>();
                        SeriesRenderData s0 = new SeriesRenderData();
                        s0.setName("countries");
                        s0.setValues(new Double[] { 15.0, 6.0, 18.0, 231.0, 150.0, 6.0, 118.0, 31.0 });
                        seriesRenderData.add(s0);
                        SeriesRenderData s1 = new SeriesRenderData();
                        s1.setName("speakers");
                        s1.setValues(new Double[] { 223.0, 119.0, 154.0, 142.0, 223.0, 119.0, 54.0, 42.0 });
                        seriesRenderData.add(s1);
                        SeriesRenderData s2 = new SeriesRenderData();
                        s2.setName("youngs");
                        s2.setValues(new Double[] { 323.0, 89.0, 54.0, 42.0, 223.0, 119.0, 54.0, 442.0 });
                        seriesRenderData.add(s2);
                        chart.setSeriesDatas(seriesRenderData);
                        return chart;
                    }

                    private ChartMultiSeriesRenderData createComboChart() {
                        ChartMultiSeriesRenderData combChart = new ChartMultiSeriesRenderData();
                        combChart.setChartTitle("ComboChartTitle");
                        combChart.setCategories(new String[] { "中文", "English", "日本語", "português" });
                        List<SeriesRenderData> combSeriesRenderData = new ArrayList<SeriesRenderData>();
                        SeriesRenderData b0 = new SeriesRenderData();
                        b0.setName("countries");
                        b0.setComboType(ComboType.BAR);
                        b0.setValues(new Double[] { 15.0, 6.0, 18.0, 231.0 });
                        combSeriesRenderData.add(b0);
                        SeriesRenderData b1 = new SeriesRenderData();
                        b1.setComboType(ComboType.BAR);
                        b1.setName("speakers");
                        b1.setValues(new Double[] { 223.0, 119.0, 154.0, 142.0 });
                        combSeriesRenderData.add(b1);
                        SeriesRenderData b2 = new SeriesRenderData();
                        b2.setComboType(ComboType.BAR);
                        b2.setName("新系列");
                        b2.setValues(new Double[] { 223.0, 119.0, 154.0, 142.0 });
                        combSeriesRenderData.add(b2);
                        SeriesRenderData c2 = new SeriesRenderData();
                        c2.setComboType(ComboType.LINE);
                        c2.setName("youngs");
                        c2.setValues(new Double[] { 323.0, 89.0, 54.0, 42.0 });
                        combSeriesRenderData.add(c2);
                        SeriesRenderData c1 = new SeriesRenderData();
                        c1.setComboType(ComboType.LINE);
                        c1.setName("新折线");
                        c1.setValues(new Double[] { 123.0, 59.0, 154.0, 42.0 });
                        combSeriesRenderData.add(c1);
                        combChart.setSeriesDatas(combSeriesRenderData);
                        return combChart;
                    }
                });

        template.writeToFile("out_reference_chart.docx");
    }

}
