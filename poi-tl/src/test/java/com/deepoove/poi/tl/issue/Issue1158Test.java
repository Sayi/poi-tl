package com.deepoove.poi.tl.issue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.exception.RenderException;

@DisplayName("Issue 1158: Stacked Bar Chart & Validation")
public class Issue1158Test {

    @Test
    public void testStackedBarChartSuccess() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>();
        ChartMultiSeriesRenderData chart = Charts
                .ofStackedBar("质量问题分布", new String[] { "类别1", "类别2" })
                .addSeries("系列1", new Double[] { 4.3, 2.4 })
                .addSeries("系列2", new Double[] { 2.5, 4.4 })
                .addSeries("系列3", new Double[] { 3.5, 2.0 })
                .create();
        datas.put("barChart", chart);
        datas.put("VBarChart", chart);
        datas.put("3dBarChart", chart);
        datas.put("lineChart", chart);
        datas.put("redarChart", chart);
        datas.put("areaChart", chart);

        ChartMultiSeriesRenderData combChart = Charts.ofComboSeries("ComboChartTitle", new String[] { "A", "B" })
                .addBarSeries("b", new Double[] { 1.0, 2.0 })
                .addLineSeries("l", new Double[] { 2.0, 3.0 })
                .create();
        datas.put("combChart", combChart);

        ChartSingleSeriesRenderData pie = Charts.ofSingleSeries("PieTitle", new String[] { "A", "B" })
                .series("s", new Integer[] { 1, 2 })
                .create();
        datas.put("pieChart", pie);
        datas.put("doughnutChart", pie);

        ChartMultiSeriesRenderData scatter = Charts.ofMultiSeries("ScatterTitle", new String[] { "1", "2" })
                .addSeries("s1", new Integer[] { 3, 4 })
                .create();
        datas.put("scatterChart", scatter);
        datas.put("relatechart", scatter);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/reference_chart.docx").render(datas);
        template.writeToFile("target/out_issue_1158.docx");
    }

    @Test
    public void testMultiSeriesMismatchedLengthThrowsRenderException() {
        Map<String, Object> datas = new HashMap<String, Object>();
        String[] nameArr = new String[] { "严重", "主要", "次要" };
        ChartMultiSeriesRenderData bargraph = Charts.ofMultiSeries(" ", nameArr).create();
        List<SeriesRenderData> list = new ArrayList<>();
        for (String name : nameArr) {
            SeriesRenderData s = new SeriesRenderData();
            s.setName(name);
            s.setValues(new Number[] { 10 }); // length 1 vs categories length 3
            list.add(s);
        }
        bargraph.setSeriesDatas(list);
        datas.put("barChart", bargraph);

        RenderException exception = Assertions.assertThrows(RenderException.class, () -> {
            XWPFTemplate.compile("src/test/resources/template/reference_chart.docx").render(datas);
        });

        Throwable rootCause = exception.getCause() != null ? exception.getCause() : exception;
        Assertions.assertTrue(rootCause instanceof RenderException);
        Assertions.assertTrue(rootCause.getMessage().contains("The length of categories (3) and series [严重] values (1) in chart must be the same!"));
    }

    @Test
    public void testSingleSeriesMismatchedLengthThrowsRenderException() {
        Map<String, Object> datas = new HashMap<String, Object>();
        ChartSingleSeriesRenderData pie = new ChartSingleSeriesRenderData();
        pie.setCategories(new String[] { "A", "B", "C" });
        SeriesRenderData series = new SeriesRenderData("Single", new Integer[] { 1, 2 }); // length 2 vs categories length 3
        pie.setSeriesData(series);
        datas.put("pieChart", pie);

        RenderException exception = Assertions.assertThrows(RenderException.class, () -> {
            XWPFTemplate.compile("src/test/resources/template/reference_chart.docx").render(datas);
        });

        Throwable rootCause = exception.getCause() != null ? exception.getCause() : exception;
        Assertions.assertTrue(rootCause instanceof RenderException);
        Assertions.assertTrue(rootCause.getMessage().contains("The length of categories (3) and series [Single] values (2) in chart must be the same!"));
    }

}
