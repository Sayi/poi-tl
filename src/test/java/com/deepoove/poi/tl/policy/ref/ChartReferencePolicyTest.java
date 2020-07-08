package com.deepoove.poi.tl.policy.ref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.BarChartRenderData;
import com.deepoove.poi.data.LineChartRenderData;
import com.deepoove.poi.data.PieChartRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.policy.reference.BarChartTemplateRenderPolicy;
import com.deepoove.poi.policy.reference.LineChartTemplateRenderPolicy;
import com.deepoove.poi.policy.reference.PieChartTemplateRenderPolicy;
import com.deepoove.poi.policy.reference.RedarChartTemplateRenderPolicy;

@DisplayName("Chart ReferencePolicy test case")
public class ChartReferencePolicyTest {

    @Test
    public void testBarChart() throws Exception {

        Configure configure = Configure.newBuilder().bind("barChart", new BarChartTemplateRenderPolicy())
                .bind("VBarChart", new BarChartTemplateRenderPolicy())
                .bind("pieChart", new PieChartTemplateRenderPolicy())
                .bind("redarChart", new RedarChartTemplateRenderPolicy())
                .bind("lineChart", new LineChartTemplateRenderPolicy()).build();

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/reference_chart.docx", configure)
                .render(new HashMap<String, Object>() {
                    {
                        BarChartRenderData bar = new BarChartRenderData();
                        bar.setChartTitle("ChartTitle");
                        bar.setCategories(new String[] { "中文", "English", "日本語", "português", "中文", "English", "日本語", "português" });
                        List<SeriesRenderData> seriesRenderData = new ArrayList<SeriesRenderData>();
                        SeriesRenderData s0 = new SeriesRenderData();
                        s0.setName("countries");
                        s0.setData(new Double[] { 15.0, 6.0, 18.0, 31.0,15.0, 6.0, 18.0, 31.0 });
                        seriesRenderData.add(s0);
                        SeriesRenderData s1 = new SeriesRenderData();
                        s1.setName("speakers");
                        s1.setData(new Double[] { 223.0, 119.0, 154.0, 442.0,223.0, 119.0, 154.0, 442.0 });
                        seriesRenderData.add(s1);
                        bar.setSeriesDatas(seriesRenderData);
                        put("barChart", bar);
                        put("VBarChart", bar);
                        
                        PieChartRenderData pie = new PieChartRenderData();
                        pie.setChartTitle("ChartTitle");
                        pie.setCategories(new String[] {"俄罗斯","加拿大","美国","中国","巴西","澳大利亚","印度"});
                        s0 = new SeriesRenderData();
                        s0.setName("countries");
                        s0.setData(new Integer[] {17098242,9984670,9826675,9596961,8514877,7741220,3287263});
                        pie.setSeriesData(s0);
                        put("pieChart", pie);
                        
                        LineChartRenderData line = new LineChartRenderData();
                        line.setChartTitle("ChartTitle");
                        line.setCategories(new String[] {"俄罗斯","加拿大","美国","中国","巴西","澳大利亚","印度"});
                        seriesRenderData = new ArrayList<SeriesRenderData>();
                        s0 = new SeriesRenderData();
                        s0.setName("countries");
                        s0.setData(new Integer[] {17098242,9984670,9826675,9596961,8514877,7741220,1287263});
                        seriesRenderData.add(s0);
                        s1 = new SeriesRenderData();
                        s1.setName("speakers");
                        s1.setData(new Integer[] {27098242,9984670,9826675,9596961,8514877,7741220,3287263});
                        seriesRenderData.add(s1);
                        SeriesRenderData s2 = new SeriesRenderData();
                        s2.setName("youngs");
                        s2.setData(new Integer[] {37098242,9984670,9826675,9596961,8514877,7741220,2287263});
                        seriesRenderData.add(s2);
                        SeriesRenderData s3 = new SeriesRenderData();
                        s3.setName("olds");
                        s3.setData(new Integer[] {47098242,9984670,9826675,9596961,8514877,7741220,5287263});
                        seriesRenderData.add(s3);
                        line.setSeriesDatas(seriesRenderData);
                        put("lineChart", line);
                        put("redarChart", line);
                    }
                });

        template.writeToFile("out_reference_chart.docx");
    }

}
