/*
 * Copyright 2014-2020 Sayi
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
package com.deepoove.poi.policy.reference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.poi.xddf.usermodel.chart.XDDFAreaChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xwpf.usermodel.XWPFChart;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData.ComboType;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.util.ReflectionUtils;

/**
 * multi series chart
 * 
 * @author Sayi
 * @version 1.8.0
 */
public class MultiSeriesChartTemplateRenderPolicy
        extends AbstractChartTemplateRenderPolicy<ChartMultiSeriesRenderData> {

    @Override
    public void doRender(ChartTemplate eleTemplate, ChartMultiSeriesRenderData data, XWPFTemplate template)
            throws Exception {
        if (null == data) return;
        XWPFChart chart = eleTemplate.getChart();
        List<XDDFChartData> chartSeries = chart.getChartSeries();
        // validate combo
        if (chartSeries.size() >= 2) {
            long nullCount = data.getSeriesDatas().stream().filter(d -> null == d.getComboType()).count();
            if (nullCount > 0) throw new RenderException("Combo chart must set comboType field of series!");
        }

        // hack for poi 4.1.1+: repair seriesCount value,
        int totalSeriesCount = chartSeries.stream().mapToInt(XDDFChartData::getSeriesCount).sum();
        Field field = ReflectionUtils.findField(XDDFChart.class, "seriesCount");
        field.setAccessible(true);
        field.set(chart, totalSeriesCount);

        int valueCol = 0;
        List<SeriesRenderData> usedSeriesDatas = new ArrayList<>();
        for (XDDFChartData chartData : chartSeries) {
            int orignSize = chartData.getSeriesCount();
            List<SeriesRenderData> seriesDatas = null;
            if (chartSeries.size() <= 1) {
                // ignore combo type
                seriesDatas = data.getSeriesDatas();
            } else {
                seriesDatas = obtainSeriesData(chartData.getClass(), data.getSeriesDatas());
            }
            usedSeriesDatas.addAll(seriesDatas);
            int seriesSize = seriesDatas.size();

            XDDFDataSource<?> categoriesData = createCategoryDataSource(chart, data.getCategories());
            for (int i = 0; i < seriesSize; i++) {
                XDDFNumericalDataSource<? extends Number> valuesData = createValueDataSource(chart,
                        seriesDatas.get(i).getValues(), valueCol);

                XDDFChartData.Series currentSeries = null;
                if (i < orignSize) {
                    currentSeries = chartData.getSeries(i);
                    currentSeries.replaceData(categoriesData, valuesData);
                } else {
                    // add series, should copy series with style
                    currentSeries = chartData.addSeries(categoriesData, valuesData);
                    processNewSeries(chartData, currentSeries);
                }
                String name = seriesDatas.get(i).getName();
                currentSeries.setTitle(name, chart.setSheetTitle(name, valueCol + VALUE_START_COL));
                valueCol++;
            }
            // clear extra series
            removeExtraSeries(chartData, orignSize, seriesSize);
        }

        XSSFSheet sheet = chart.getWorkbook().getSheetAt(0);
        updateCTTable(sheet, usedSeriesDatas);

        removeExtraSheetCell(sheet, data.getCategories().length, totalSeriesCount, usedSeriesDatas.size());

        for (XDDFChartData chartData : chartSeries) {
            plot(chart, chartData);
        }
        chart.setTitleText(data.getChartTitle());
        chart.setTitleOverlay(false);
    }

    protected void processNewSeries(XDDFChartData chartData, Series addSeries) {
    }

    private List<SeriesRenderData> obtainSeriesData(Class<? extends XDDFChartData> clazz,
            List<SeriesRenderData> seriesDatas) {
        Predicate<SeriesRenderData> predicate = data -> {
            return false;
        };
        if (clazz.equals(XDDFBarChartData.class)) {
            predicate = data -> ComboType.BAR == data.getComboType();
        } else if (clazz.equals(XDDFAreaChartData.class)) {
            predicate = data -> ComboType.AREA == data.getComboType();
        } else if (clazz.equals(XDDFLineChartData.class)) {
            predicate = data -> ComboType.LINE == data.getComboType();
        }
        return seriesDatas.stream().filter(predicate).collect(Collectors.toList());
    }

}
