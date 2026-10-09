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
package com.deepoove.poi.data.builder;

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.RenderDataBuilder;
import com.deepoove.poi.data.SeriesRenderData;

/**
 * Dedicated builder to generate Gauge chart based on 180-degree half doughnut chart.
 * 
 * @author Sayi
 */
public class GaugeChartBuilder implements RenderDataBuilder<ChartSingleSeriesRenderData> {

    private String chartTitle;
    private double min = 0;
    private double max = 100;
    private Double currentValue;
    private String progressColor = "#52C41A";
    private String remainingColor = "#E8E8E8";
    private String progressLabel = "Progress";
    private String remainingLabel = "Remaining";
    private String baseLabel = "Base";
    private Boolean showDataLabels;

    private static class Segment {
        String name;
        double value;
        String color;

        Segment(String name, double value, String color) {
            this.name = name;
            this.value = value;
            this.color = color;
        }
    }

    private List<Segment> segments = new ArrayList<>();

    public GaugeChartBuilder(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public GaugeChartBuilder range(double min, double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public GaugeChartBuilder min(double min) {
        this.min = min;
        return this;
    }

    public GaugeChartBuilder max(double max) {
        this.max = max;
        return this;
    }

    public GaugeChartBuilder currentValue(double currentValue) {
        this.currentValue = currentValue;
        return this;
    }

    public GaugeChartBuilder progressColor(String color) {
        this.progressColor = color;
        return this;
    }

    public GaugeChartBuilder remainingColor(String color) {
        this.remainingColor = color;
        return this;
    }

    public GaugeChartBuilder progressLabel(String label) {
        this.progressLabel = label;
        return this;
    }

    public GaugeChartBuilder remainingLabel(String label) {
        this.remainingLabel = label;
        return this;
    }

    public GaugeChartBuilder addSegment(String name, double value, String color) {
        this.segments.add(new Segment(name, value, color));
        return this;
    }

    public GaugeChartBuilder showDataLabels(Boolean showDataLabels) {
        this.showDataLabels = showDataLabels;
        return this;
    }

    @Override
    public ChartSingleSeriesRenderData create() {
        if (!segments.isEmpty()) {
            return createSegmentedGauge();
        } else {
            return createSingleValueGauge();
        }
    }

    private ChartSingleSeriesRenderData createSingleValueGauge() {
        if (null == currentValue) {
            throw new IllegalArgumentException("Gauge chart currentValue must not be null!");
        }
        double totalSpan = max - min;
        if (totalSpan <= 0) {
            throw new IllegalArgumentException("Gauge chart max must be greater than min!");
        }

        double val = Math.max(0, Math.min(totalSpan, currentValue - min));
        double rem = totalSpan - val;

        String[] categories = new String[] { progressLabel, remainingLabel, baseLabel };
        Number[] values = new Number[] { val, rem, totalSpan };
        String[] colors = new String[] { progressColor, remainingColor, "transparent" };

        SeriesRenderData series = new SeriesRenderData("Gauge", values)
                .colors(colors)
                .showDataLabels(showDataLabels);

        ChartSingleSeriesRenderData data = new ChartSingleSeriesRenderData();
        data.setChartTitle(chartTitle);
        data.setCategories(categories);
        data.setSeriesData(series);
        return data;
    }

    private ChartSingleSeriesRenderData createSegmentedGauge() {
        double sum = 0;
        for (Segment s : segments) {
            sum += s.value;
        }

        int count = segments.size() + 1; // plus transparent base
        String[] categories = new String[count];
        Number[] values = new Number[count];
        String[] colors = new String[count];

        for (int i = 0; i < segments.size(); i++) {
            Segment s = segments.get(i);
            categories[i] = s.name;
            values[i] = s.value;
            colors[i] = s.color;
        }
        categories[count - 1] = baseLabel;
        values[count - 1] = sum;
        colors[count - 1] = "transparent";

        SeriesRenderData series = new SeriesRenderData("Gauge", values)
                .colors(colors)
                .showDataLabels(showDataLabels);

        ChartSingleSeriesRenderData data = new ChartSingleSeriesRenderData();
        data.setChartTitle(chartTitle);
        data.setCategories(categories);
        data.setSeriesData(series);
        return data;
    }

}
