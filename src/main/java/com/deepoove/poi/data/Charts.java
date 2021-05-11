/*
 * Copyright 2014-2021 Sayi
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

    public static ChartMultis ofLine(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofArea(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofBar3D(String chartTitle, String[] categories) {
        return ofMultiSeries(chartTitle, categories);
    }

    public static ChartMultis ofArea3D(String chartTitle, String[] categories) {
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

    public static abstract class ChartBuilder {
        protected String chartTitle;
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
    }

    /**
     * 
     * builder to build multi series chart
     *
     */
    public static class ChartMultis extends ChartBuilder implements RenderDataBuilder<ChartMultiSeriesRenderData> {
        private List<SeriesRenderData> seriesDatas = new ArrayList<>();

        private ChartMultis(String chartTitle, String[] categories) {
            super(chartTitle, categories);
        }

        public ChartMultis addSeries(String name, Number[] value) {
            checkLengh(value.length);
            seriesDatas.add(new SeriesRenderData(name, value));
            return this;
        }

        @Override
        public ChartMultiSeriesRenderData create() {
            ChartMultiSeriesRenderData data = new ChartMultiSeriesRenderData();
            data.setChartTitle(chartTitle);
            data.setCategories(categories);
            data.setSeriesDatas(seriesDatas);
            return data;
        }
    }

    /**
     * builder to build combo series chart
     *
     */
    public static class ChartCombos extends ChartBuilder implements RenderDataBuilder<ChartMultiSeriesRenderData> {
        private List<SeriesRenderData> seriesDatas = new ArrayList<>();

        private ChartCombos(String chartTitle, String[] categories) {
            super(chartTitle, categories);
        }

        public ChartCombos addBarSeries(String name, Number[] value) {
            addSeries(ComboType.BAR, name, value);
            return this;
        }

        public ChartCombos addLineSeries(String name, Number[] value) {
            addSeries(ComboType.LINE, name, value);
            return this;
        }

        public ChartCombos addAreaSeries(String name, Number[] value) {
            addSeries(ComboType.AREA, name, value);
            return this;
        }

        private void addSeries(ComboType type, String name, Number[] value) {
            checkLengh(value.length);
            SeriesRenderData seriesRenderData = new SeriesRenderData(name, value);
            seriesRenderData.setComboType(type);
            seriesDatas.add(seriesRenderData);
        }

        @Override
        public ChartMultiSeriesRenderData create() {
            ChartMultiSeriesRenderData data = new ChartMultiSeriesRenderData();
            data.setChartTitle(chartTitle);
            data.setCategories(categories);
            data.setSeriesDatas(seriesDatas);
            return data;
        }
    }

    /**
     * builder to build single series chart
     *
     */
    public static class ChartSingles extends ChartBuilder implements RenderDataBuilder<ChartSingleSeriesRenderData> {
        private SeriesRenderData series;

        private ChartSingles(String chartTitle, String[] categories) {
            super(chartTitle, categories);
        }

        public ChartSingles series(String name, Number[] value) {
            checkLengh(value.length);
            series = new SeriesRenderData(name, value);
            return this;
        }

        @Override
        public ChartSingleSeriesRenderData create() {
            ChartSingleSeriesRenderData data = new ChartSingleSeriesRenderData();
            data.setChartTitle(chartTitle);
            data.setCategories(categories);
            data.setSeriesData(series);
            return data;
        }
    }

}
