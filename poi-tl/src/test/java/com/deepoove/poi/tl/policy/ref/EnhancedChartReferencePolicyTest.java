/*
 * Copyright 2014-2026 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.tl.policy.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.BubbleSeriesRenderData;
import com.deepoove.poi.data.ChartBubbleRenderData;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData.ComboType;
import com.deepoove.poi.data.builder.GanttChartBuilder;
import com.deepoove.poi.data.builder.GaugeChartBuilder;

@DisplayName("Enhanced Chart capability test case")
public class EnhancedChartReferencePolicyTest {

    @Test
    public void testSeriesStyleAndDataLabels() throws Exception {
        Map<String, Object> datas = new HashMap<>();

        // Multi-series chart with custom colors and data labels
        SeriesRenderData series1 = new SeriesRenderData("营收", new Double[] { 100.0, 200.0, 150.0, 300.0 })
                .color("#2F5597")
                .showDataLabels(true);

        SeriesRenderData series2 = new SeriesRenderData("成本", new Double[] { 60.0, 110.0, 90.0, 180.0 })
                .color("#E74C3C")
                .showDataLabels(false);

        ChartMultiSeriesRenderData barChart = Charts.ofMultiSeries("经营指标", new String[] { "Q1", "Q2", "Q3", "Q4" })
                .addSeries(series1)
                .addSeries(series2)
                .setxAxisTitle("季度")
                .setyAxisTitle("金额 (万元)")
                .create();
        datas.put("barChart", barChart);

        // Single-series pie/doughnut chart with per-slice colors
        SeriesRenderData pieSeries = new SeriesRenderData("渠道占比", new Integer[] { 40, 35, 25 })
                .colors("#2ECC71", "#F39C12", "#E74C3C")
                .showDataLabels(true);

        ChartSingleSeriesRenderData pieChart = Charts.ofSingleSeries("渠道结构", new String[] { "线上直营", "经销商", "其他" })
                .series(pieSeries)
                .create();
        datas.put("pieChart", pieChart);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/reference_chart.docx").render(datas);
        File outFile = new File("target/out_enhanced_chart_style.docx");
        template.writeToFile(outFile.getAbsolutePath());

        assertTrue(outFile.exists());
        try (FileInputStream in = new FileInputStream(outFile);
                XWPFDocument doc = new XWPFDocument(in)) {
            assertNotNull(doc);
        }
    }

    @Test
    public void testSecondaryAxisComboChart() throws Exception {
        Map<String, Object> datas = new HashMap<>();

        ChartMultiSeriesRenderData comboChart = Charts.ofComboSeries("营收与增速双轴对比", new String[] { "2021", "2022", "2023", "2024" })
                .addBarSeries("营业收入(万元)", new Double[] { 1200.0, 1800.0, 2500.0, 3200.0 }, false) // Primary Left Axis
                .addLineSeries("同比增长率(%)", new Double[] { 15.0, 50.0, 38.8, 28.0 }, true)           // Secondary Right Axis
                .setxAxisTitle("年份")
                .setyAxisTitle("营收(万元)")
                .setSecondaryYAxisTitle("增速(%)")
                .create();
        datas.put("combChart", comboChart);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/reference_chart.docx").render(datas);
        File outFile = new File("target/out_secondary_axis_combo.docx");
        template.writeToFile(outFile.getAbsolutePath());

        assertTrue(outFile.exists());
        assertEquals("增速(%)", comboChart.getSecondaryYAxisTitle());
        assertTrue(comboChart.getSeriesDatas().get(1).isSecondaryAxis());
    }

    @Test
    public void testGanttChartBuilder() throws Exception {
        ChartMultiSeriesRenderData ganttData = Charts.ofGantt("系统研发甘特图")
                .task("需求分析", "2025-06-01", "2025-06-10")
                .task("架构设计", "2025-06-08", "2025-06-20")
                .task("核心编码", "2025-06-18", "2025-07-20")
                .task("集成测试", "2025-07-15", "2025-08-05")
                .taskBarColor("#1890FF")
                .showDataLabels(true)
                .create();

        assertNotNull(ganttData);
        assertEquals(2, ganttData.getSeriesDatas().size());

        SeriesRenderData offsetSeries = ganttData.getSeriesDatas().get(0);
        assertEquals("Offset", offsetSeries.getName());
        assertEquals("transparent", offsetSeries.getColor());

        SeriesRenderData durationSeries = ganttData.getSeriesDatas().get(1);
        assertEquals("Duration", durationSeries.getName());
        assertEquals("#1890FF", durationSeries.getColor());
        assertEquals(Boolean.TRUE, durationSeries.getShowDataLabels());

        // Test rendering Gantt data into template barChart slot
        Map<String, Object> datas = new HashMap<>();
        datas.put("barChart", ganttData);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/reference_chart.docx").render(datas);
        File outFile = new File("target/out_gantt_chart.docx");
        template.writeToFile(outFile.getAbsolutePath());
        assertTrue(outFile.exists());
    }

    @Test
    public void testGaugeChartBuilder() throws Exception {
        // 1. Single value KPI gauge
        ChartSingleSeriesRenderData singleGauge = Charts.ofGauge("系统健康得分")
                .range(0, 100)
                .currentValue(85)
                .progressColor("#52C41A")
                .remainingColor("#E8E8E8")
                .create();

        assertNotNull(singleGauge);
        assertEquals(3, singleGauge.getCategories().length);
        assertEquals("Progress", singleGauge.getCategories()[0]);
        assertEquals("Remaining", singleGauge.getCategories()[1]);
        assertEquals("Base", singleGauge.getCategories()[2]);
        SeriesRenderData singleSeries = singleGauge.getSeriesData();
        assertEquals(85.0, singleSeries.getValues()[0]);
        assertEquals(15.0, singleSeries.getValues()[1]);
        assertEquals(100.0, singleSeries.getValues()[2]);
        assertEquals("transparent", singleSeries.getColors()[2]);

        // 2. Segmented threshold gauge
        ChartSingleSeriesRenderData segmentedGauge = Charts.ofGauge("风险等级仪表盘")
                .addSegment("低危", 60, "#52C41A")
                .addSegment("中危", 25, "#FAAD14")
                .addSegment("高危", 15, "#F5222D")
                .create();

        assertNotNull(segmentedGauge);
        assertEquals(4, segmentedGauge.getCategories().length);
        SeriesRenderData segSeries = segmentedGauge.getSeriesData();
        assertEquals(100.0, segSeries.getValues()[3]); // total base
        assertEquals("transparent", segSeries.getColors()[3]);

        // Render into doughnutChart in template
        Map<String, Object> datas = new HashMap<>();
        datas.put("doughnutChart", singleGauge);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/reference_chart.docx").render(datas);
        File outFile = new File("target/out_gauge_chart.docx");
        template.writeToFile(outFile.getAbsolutePath());
        assertTrue(outFile.exists());
    }

    @Test
    public void testBubbleChartModelAndValidation() {
        ChartBubbleRenderData bubbleData = Charts.ofBubble("战略风险矩阵")
                .addSeries("业务单元A", new Number[] { 1, 2, 3 }, new Number[] { 10, 20, 30 }, new Number[] { 5, 8, 12 })
                .addSeries(new BubbleSeriesRenderData("业务单元B", new Number[] { 2, 4 }, new Number[] { 15, 25 }, new Number[] { 6, 10 })
                        .color("#FAAD14")
                        .showDataLabels(true))
                .setxAxisTitle("发生概率")
                .setyAxisTitle("影响程度")
                .create();

        assertNotNull(bubbleData);
        assertEquals("战略风险矩阵", bubbleData.getChartTitle());
        assertEquals(2, bubbleData.getSeriesDatas().size());
        assertEquals("发生概率", bubbleData.getxAxisTitle());
        assertEquals("影响程度", bubbleData.getyAxisTitle());
    }

}
