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
package com.deepoove.poi.data;

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.SeriesRenderData.ComboType;
import com.deepoove.poi.data.builder.GanttChartBuilder;
import com.deepoove.poi.data.builder.GaugeChartBuilder;

/**
 * Factory method to create chart
 * 
 * @author Sayi
 *
 */
public class Charts {

    public static ChartMultis ofBar(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofStackedBar(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofLine(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofArea(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofStackedArea(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofBar3D(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofStackedBar3D(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofArea3D(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofStackedArea3D(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofLine3D(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofRadar(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartSingles ofPie(String chartTitle, String[] categories) {
        return ofSingleSeries(chartTitle, categories);
    }

    public static ChartSingles ofPie3D(String chartTitle, String[] categories) {
        return ofSingleSeries(chartTitle, categories);
    }

    public static ChartSingles ofDoughnut(String chartTitle, String[] categories) {
        return ofSingleSeries(chartTitle, categories);
    }

    public static ChartMultis ofMultiSeries(String chartTitle, String[] categories) {
        return new ChartMultis(chartTitle, categories);
    }

    public static ChartCombos ofComboSeries(String chartTitle, String[] categories) {
        return new ChartCombos(chartTitle, categories);
    }

    public static ChartSingles ofSingleSeries(String chartTitle, String[] categories) {
        return new ChartSingles(chartTitle, categories);
    }

    public static ChartBubbles ofBubble(String chartTitle) {
        return new ChartBubbles(chartTitle);
    }

    public static GanttChartBuilder ofGantt(String chartTitle) {
        return new GanttChartBuilder(chartTitle);
    }

    public static GaugeChartBuilder ofGauge(String chartTitle) {
        return new GaugeChartBuilder(chartTitle);
    }

    public static interface ChartSetting<T extends RenderData> {
        ChartBuilder<T> setxAsixTitle(String xAxisTitle);

        default ChartBuilder<T> setxAxisTitle(String xAxisTitle) {
            return setxAsixTitle(xAxisTitle);
        }

        ChartBuilder<T> setyAsixTitle(String yAxisTitle);

        default ChartBuilder<T> setyAxisTitle(String yAxisTitle) {
            return setyAsixTitle(yAxisTitle);
        }
    }

    public static abstract class ChartBuilder<T extends RenderData> implements RenderDataBuilder<T>, ChartSetting<T> {
        protected String chartTitle;
        protected String xAxisTitle;
        protected String yAxisTitle;
        protected String[] categories;

        protected ChartBuilder(String chartTitle, String[] categories) {
            this.chartTitle = chartTitle;
            this.categories = categories;
        }

        protected void checkLengh(int length) {
            if (categories.length != length) {
                throw new IllegalArgumentException(
                        "The length of categories and series values in chart must be the same!");
            }
        }

        public ChartBuilder<T> setxAsixTitle(String xAxisTitle) {
            this.xAxisTitle = xAxisTitle;
            return this;
        }

        public ChartBuilder<T> setxAxisTitle(String xAxisTitle) {
            return setxAsixTitle(xAxisTitle);
        }

        public ChartBuilder<T> setyAsixTitle(String yAxisTitle) {
            this.yAxisTitle = yAxisTitle;
            return this;
        }

        public ChartBuilder<T> setyAxisTitle(String yAxisTitle) {
            return setyAsixTitle(yAxisTitle);
        }
    }

    /**
     * 
     * builder to build multi series chart
     *
     */
    public static class ChartMultis extends ChartBuilder<ChartMultiSeriesRenderData> {
        private List<SeriesRenderData> seriesDatas = new ArrayList<>();

        private ChartMultis(String chartTitle, String[] categories) {
            super(chartTitle, categories);
        }

        public ChartMultis addSeries(String name, Number[] value) {
            checkLengh(value.length);
            seriesDatas.add(new SeriesRenderData(name, value));
            return this;
        }

        public ChartMultis addSeries(SeriesRenderData series) {
            checkLengh(series.getValues().length);
            seriesDatas.add(series);
            return this;
        }

        @Override
        public ChartMultis setxAsixTitle(String xAxisTitle) {
            super.setxAsixTitle(xAxisTitle);
            return this;
        }

        @Override
        public ChartMultis setxAxisTitle(String xAxisTitle) {
            super.setxAxisTitle(xAxisTitle);
            return this;
        }

        @Override
        public ChartMultis setyAsixTitle(String yAxisTitle) {
            super.setyAsixTitle(yAxisTitle);
            return this;
        }

        @Override
        public ChartMultis setyAxisTitle(String yAxisTitle) {
            super.setyAxisTitle(yAxisTitle);
            return this;
        }

        @Override
        public ChartMultiSeriesRenderData create() {
            ChartMultiSeriesRenderData data = new ChartMultiSeriesRenderData();
            data.setChartTitle(chartTitle);
            data.setxAxisTitle(xAxisTitle);
            data.setyAxisTitle(yAxisTitle);
            data.setCategories(categories);
            data.setSeriesDatas(seriesDatas);
            return data;
        }
    }

    /**
     * builder to build combo series chart
     *
     */
    public static class ChartCombos extends ChartBuilder<ChartMultiSeriesRenderData> {
        private List<SeriesRenderData> seriesDatas = new ArrayList<>();
        private String secondaryYAxisTitle;

        private ChartCombos(String chartTitle, String[] categories) {
            super(chartTitle, categories);
        }

        public ChartCombos addBarSeries(String name, Number[] value) {
            return addBarSeries(name, value, false);
        }

        public ChartCombos addBarSeries(String name, Number[] value, boolean secondaryAxis) {
            addSeries(ComboType.BAR, name, value, secondaryAxis);
            return this;
        }

        public ChartCombos addLineSeries(String name, Number[] value) {
            return addLineSeries(name, value, false);
        }

        public ChartCombos addLineSeries(String name, Number[] value, boolean secondaryAxis) {
            addSeries(ComboType.LINE, name, value, secondaryAxis);
            return this;
        }

        public ChartCombos addAreaSeries(String name, Number[] value) {
            return addAreaSeries(name, value, false);
        }

        public ChartCombos addAreaSeries(String name, Number[] value, boolean secondaryAxis) {
            addSeries(ComboType.AREA, name, value, secondaryAxis);
            return this;
        }

        public ChartCombos addSeries(SeriesRenderData series) {
            checkLengh(series.getValues().length);
            seriesDatas.add(series);
            return this;
        }

        public ChartCombos setSecondaryYAsixTitle(String secondaryYAxisTitle) {
            this.secondaryYAxisTitle = secondaryYAxisTitle;
            return this;
        }

        public ChartCombos setSecondaryYAxisTitle(String secondaryYAxisTitle) {
            this.secondaryYAxisTitle = secondaryYAxisTitle;
            return this;
        }

        @Override
        public ChartCombos setxAsixTitle(String xAxisTitle) {
            super.setxAsixTitle(xAxisTitle);
            return this;
        }

        @Override
        public ChartCombos setxAxisTitle(String xAxisTitle) {
            super.setxAxisTitle(xAxisTitle);
            return this;
        }

        @Override
        public ChartCombos setyAsixTitle(String yAxisTitle) {
            super.setyAsixTitle(yAxisTitle);
            return this;
        }

        @Override
        public ChartCombos setyAxisTitle(String yAxisTitle) {
            super.setyAxisTitle(yAxisTitle);
            return this;
        }

        private void addSeries(ComboType type, String name, Number[] value, boolean secondaryAxis) {
            checkLengh(value.length);
            SeriesRenderData seriesRenderData = new SeriesRenderData(name, value);
            seriesRenderData.setComboType(type);
            seriesRenderData.setSecondaryAxis(secondaryAxis);
            seriesDatas.add(seriesRenderData);
        }

        @Override
        public ChartMultiSeriesRenderData create() {
            ChartMultiSeriesRenderData data = new ChartMultiSeriesRenderData();
            data.setChartTitle(chartTitle);
            data.setxAxisTitle(xAxisTitle);
            data.setyAxisTitle(yAxisTitle);
            data.setSecondaryYAxisTitle(secondaryYAxisTitle);
            data.setCategories(categories);
            data.setSeriesDatas(seriesDatas);
            return data;
        }
    }

    /**
     * builder to build single series chart
     *
     */
    public static class ChartSingles extends ChartBuilder<ChartSingleSeriesRenderData> {
        private SeriesRenderData series;

        private ChartSingles(String chartTitle, String[] categories) {
            super(chartTitle, categories);
        }

        public ChartSingles series(String name, Number[] value) {
            checkLengh(value.length);
            series = new SeriesRenderData(name, value);
            return this;
        }

        public ChartSingles series(SeriesRenderData series) {
            checkLengh(series.getValues().length);
            this.series = series;
            return this;
        }

        @Override
        public ChartSingles setxAsixTitle(String xAxisTitle) {
            super.setxAsixTitle(xAxisTitle);
            return this;
        }

        @Override
        public ChartSingles setxAxisTitle(String xAxisTitle) {
            super.setxAxisTitle(xAxisTitle);
            return this;
        }

        @Override
        public ChartSingles setyAsixTitle(String yAxisTitle) {
            super.setyAsixTitle(yAxisTitle);
            return this;
        }

        @Override
        public ChartSingles setyAxisTitle(String yAxisTitle) {
            super.setyAxisTitle(yAxisTitle);
            return this;
        }

        @Override
        public ChartSingleSeriesRenderData create() {
            ChartSingleSeriesRenderData data = new ChartSingleSeriesRenderData();
            data.setChartTitle(chartTitle);
            data.setxAxisTitle(xAxisTitle);
            data.setyAxisTitle(yAxisTitle);
            data.setCategories(categories);
            data.setSeriesData(series);
            return data;
        }
    }

    /**
     * builder to build bubble chart
     */
    public static class ChartBubbles implements RenderDataBuilder<ChartBubbleRenderData> {
        private String chartTitle;
        private String xAxisTitle;
        private String yAxisTitle;
        private List<BubbleSeriesRenderData> seriesDatas = new ArrayList<>();

        private ChartBubbles(String chartTitle) {
            this.chartTitle = chartTitle;
        }

        public ChartBubbles addSeries(String name, Number[] xValues, Number[] yValues, Number[] bubbleSizes) {
            seriesDatas.add(new BubbleSeriesRenderData(name, xValues, yValues, bubbleSizes));
            return this;
        }

        public ChartBubbles addSeries(BubbleSeriesRenderData series) {
            seriesDatas.add(series);
            return this;
        }

        public ChartBubbles setxAsixTitle(String xAxisTitle) {
            this.xAxisTitle = xAxisTitle;
            return this;
        }

        public ChartBubbles setxAxisTitle(String xAxisTitle) {
            return setxAsixTitle(xAxisTitle);
        }

        public ChartBubbles setyAsixTitle(String yAxisTitle) {
            this.yAxisTitle = yAxisTitle;
            return this;
        }

        public ChartBubbles setyAxisTitle(String yAxisTitle) {
            return setyAsixTitle(yAxisTitle);
        }

        @Override
        public ChartBubbleRenderData create() {
            ChartBubbleRenderData data = new ChartBubbleRenderData();
            data.setChartTitle(chartTitle);
            data.setxAxisTitle(xAxisTitle);
            data.setyAxisTitle(yAxisTitle);
            data.setSeriesDatas(seriesDatas);
            return data;
        }
    }

}
