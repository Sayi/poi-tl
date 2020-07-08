package com.deepoove.poi.tl.policy.ref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.policy.reference.MultiSeriesChartTemplateRenderPolicy;
import com.deepoove.poi.policy.reference.SingleSeriesChartTemplateRenderPolicy;

@DisplayName("Chart test case")
public class ChartReferencePolicyTest {

    @SuppressWarnings("serial")
    @Test
    public void testChart() throws Exception {

        Configure configure = Configure.newBuilder().bind("barChart", new MultiSeriesChartTemplateRenderPolicy())
                .bind("VBarChart", new MultiSeriesChartTemplateRenderPolicy())
                .bind("pieChart", new SingleSeriesChartTemplateRenderPolicy())
                .bind("redarChart", new MultiSeriesChartTemplateRenderPolicy())
                .bind("lineChart", new MultiSeriesChartTemplateRenderPolicy()).build();

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/reference_chart.docx", configure)
                .render(new HashMap<String, Object>() {
                    {
                        ChartMultiSeriesRenderData bar = new ChartMultiSeriesRenderData();
                        bar.setChartTitle("ChartTitle");
                        bar.setCategories(new String[] { "中文", "English", "日本語", "português", "中文", "English", "日本語",
                                "português" });
                        List<SeriesRenderData> seriesRenderData = new ArrayList<SeriesRenderData>();
                        SeriesRenderData s0 = new SeriesRenderData();
                        s0.setName("countries");
                        s0.setValues(new Double[] { 15.0, 6.0, 18.0, 31.0, 15.0, 6.0, 18.0, 31.0 });
                        seriesRenderData.add(s0);
                        SeriesRenderData s1 = new SeriesRenderData();
                        s1.setName("speakers");
                        s1.setValues(new Double[] { 223.0, 119.0, 154.0, 442.0, 223.0, 119.0, 154.0, 442.0 });
                        seriesRenderData.add(s1);
                        SeriesRenderData s2 = new SeriesRenderData();
                        s2.setName("youngs");
                        s2.setValues(new Double[] { 323.0, 119.0, 154.0, 442.0, 223.0, 119.0, 154.0, 442.0 });
                        seriesRenderData.add(s2);
                        bar.setSeriesDatas(seriesRenderData);
                        put("barChart", bar);
                        put("VBarChart", bar);
                        put("lineChart", bar);
                        put("redarChart", bar);

                        ChartSingleSeriesRenderData pie = new ChartSingleSeriesRenderData();
                        pie.setChartTitle("ChartTitle");
                        pie.setCategories(new String[] { "俄罗斯", "加拿大", "美国", "中国", "巴西", "澳大利亚", "印度" });
                        s0 = new SeriesRenderData();
                        s0.setName("countries");
                        s0.setValues(new Integer[] { 17098242, 9984670, 9826675, 9596961, 8514877, 7741220, 3287263 });
                        pie.setSeriesData(s0);
                        put("pieChart", pie);

                    }
                });

        template.writeToFile("out_reference_chart.docx");
    }

}
