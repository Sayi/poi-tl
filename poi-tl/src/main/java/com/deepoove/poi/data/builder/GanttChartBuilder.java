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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.RenderDataBuilder;
import com.deepoove.poi.data.SeriesRenderData;

/**
 * Dedicated builder to generate Gantt chart based on stacked horizontal bar chart.
 * 
 * @author Sayi
 */
public class GanttChartBuilder implements RenderDataBuilder<ChartMultiSeriesRenderData> {

    private String chartTitle;
    private String xAxisTitle = "Days";
    private String yAxisTitle;
    private String offsetSeriesName = "Offset";
    private String durationSeriesName = "Duration";
    private String taskBarColor = "#2F5597";
    private Boolean showDataLabels;
    private boolean reverseOrder = true;

    private List<GanttTask> tasks = new ArrayList<>();

    public GanttChartBuilder(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public static class GanttTask {
        private String name;
        private long offsetDays;
        private long durationDays;

        public GanttTask(String name, long offsetDays, long durationDays) {
            this.name = name;
            this.offsetDays = offsetDays;
            this.durationDays = durationDays;
        }

        public String getName() {
            return name;
        }

        public long getOffsetDays() {
            return offsetDays;
        }

        public long getDurationDays() {
            return durationDays;
        }
    }

    private static class DateTask {
        String name;
        Date startDate;
        Date endDate;

        DateTask(String name, Date startDate, Date endDate) {
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    private List<DateTask> dateTasks = new ArrayList<>();

    public GanttChartBuilder task(String taskName, int offsetDays, int durationDays) {
        this.tasks.add(new GanttTask(taskName, offsetDays, durationDays));
        return this;
    }

    public GanttChartBuilder task(String taskName, String startDateStr, String endDateStr) {
        Date start = parseDate(startDateStr);
        Date end = parseDate(endDateStr);
        this.dateTasks.add(new DateTask(taskName, start, end));
        return this;
    }

    public GanttChartBuilder task(String taskName, Date startDate, Date endDate) {
        this.dateTasks.add(new DateTask(taskName, startDate, endDate));
        return this;
    }

    public GanttChartBuilder taskBarColor(String hexColor) {
        this.taskBarColor = hexColor;
        return this;
    }

    public GanttChartBuilder offsetSeriesName(String name) {
        this.offsetSeriesName = name;
        return this;
    }

    public GanttChartBuilder durationSeriesName(String name) {
        this.durationSeriesName = name;
        return this;
    }

    public GanttChartBuilder showDataLabels(Boolean showDataLabels) {
        this.showDataLabels = showDataLabels;
        return this;
    }

    public GanttChartBuilder reverseOrder(boolean reverseOrder) {
        this.reverseOrder = reverseOrder;
        return this;
    }

    public GanttChartBuilder xAxisTitle(String xAxisTitle) {
        this.xAxisTitle = xAxisTitle;
        return this;
    }

    public GanttChartBuilder yAxisTitle(String yAxisTitle) {
        this.yAxisTitle = yAxisTitle;
        return this;
    }

    @Override
    public ChartMultiSeriesRenderData create() {
        List<GanttTask> resolvedTasks = new ArrayList<>(this.tasks);

        if (!dateTasks.isEmpty()) {
            long minTime = Long.MAX_VALUE;
            for (DateTask dt : dateTasks) {
                if (dt.startDate.getTime() < minTime) {
                    minTime = dt.startDate.getTime();
                }
            }
            long msPerDay = 24L * 60 * 60 * 1000;
            for (DateTask dt : dateTasks) {
                long offset = (dt.startDate.getTime() - minTime) / msPerDay;
                long duration = (dt.endDate.getTime() - dt.startDate.getTime()) / msPerDay;
                if (duration <= 0) duration = 1;
                resolvedTasks.add(new GanttTask(dt.name, offset, duration));
            }
        }

        if (resolvedTasks.isEmpty()) {
            throw new IllegalArgumentException("Gantt chart tasks must not be empty!");
        }

        if (reverseOrder) {
            Collections.reverse(resolvedTasks);
        }

        int size = resolvedTasks.size();
        String[] categories = new String[size];
        Number[] offsetValues = new Number[size];
        Number[] durationValues = new Number[size];

        for (int i = 0; i < size; i++) {
            GanttTask t = resolvedTasks.get(i);
            categories[i] = t.getName();
            offsetValues[i] = t.getOffsetDays();
            durationValues[i] = t.getDurationDays();
        }

        SeriesRenderData offsetSeries = new SeriesRenderData(offsetSeriesName, offsetValues)
                .color("transparent")
                .showDataLabels(false);

        SeriesRenderData durationSeries = new SeriesRenderData(durationSeriesName, durationValues)
                .color(taskBarColor)
                .showDataLabels(showDataLabels);

        List<SeriesRenderData> seriesList = new ArrayList<>();
        seriesList.add(offsetSeries);
        seriesList.add(durationSeries);

        ChartMultiSeriesRenderData chartData = new ChartMultiSeriesRenderData();
        chartData.setChartTitle(chartTitle);
        chartData.setxAxisTitle(xAxisTitle);
        chartData.setyAxisTitle(yAxisTitle);
        chartData.setCategories(categories);
        chartData.setSeriesDatas(seriesList);

        return chartData;
    }

    private Date parseDate(String dateStr) {
        String[] patterns = new String[] { "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd", "yyyyMMdd" };
        for (String pattern : patterns) {
            try {
                return new SimpleDateFormat(pattern).parse(dateStr);
            } catch (ParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Unable to parse date string: " + dateStr);
    }

}
