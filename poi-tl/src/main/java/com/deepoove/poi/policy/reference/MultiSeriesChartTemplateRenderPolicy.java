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
 */
public class MultiSeriesChartTemplateRenderPolicy
        extends AbstractChartTemplateRenderPolicy<ChartMultiSeriesRenderData> {

    @Override
    public void doRender(ChartTemplate eleTemplate, ChartMultiSeriesRenderData data, XWPFTemplate template)
            throws Exception {
        XWPFChart chart = eleTemplate.getChart();
        List<XDDFChartData> chartSeries = chart.getChartSeries();
        validate(chartSeries, data);

        int totalSeriesCount = ensureSeriesCount(chart, chartSeries);
        int valueCol = 0;
        List<SeriesRenderData> usedSeriesDatas = new ArrayList<>();
        for (XDDFChartData chartData : chartSeries) {
            int orignSize = chartData.getSeriesCount();
            List<SeriesRenderData> currentSeriesData = null;
            if (chartSeries.size() <= 1) {
                // ignore combo type
                currentSeriesData = data.getSeriesDatas();
            } else {
                currentSeriesData = obtainSeriesData(chartData.getClass(), data.getSeriesDatas());
            }
            usedSeriesDatas.addAll(currentSeriesData);
            int currentSeriesSize = currentSeriesData.size();

            XDDFDataSource<?> categoriesData = createCategoryDataSource(chart, data.getCategories());
            for (int i = 0; i < currentSeriesSize; i++) {
                XDDFNumericalDataSource<? extends Number> valuesData = createValueDataSource(chart,
                        currentSeriesData.get(i).getValues(), valueCol);

                XDDFChartData.Series currentSeries = null;
                if (i < orignSize) {
                    currentSeries = chartData.getSeries(i);
                    valuesData.setFormatCode(currentSeries.getValuesData().getFormatCode());
                    currentSeries.replaceData(categoriesData, valuesData);
                } else {
                    // add series, should copy series with style
                    currentSeries = chartData.addSeries(categoriesData, valuesData);
                    processNewSeries(chartData, currentSeries);
                }
                String name = currentSeriesData.get(i).getName();
                currentSeries.setTitle(name, chart.setSheetTitle(name, valueCol + VALUE_START_COL));
                valueCol++;
            }
            // clear extra series
            removeExtraSeries(chartData, orignSize, currentSeriesSize);
        }

        XSSFSheet sheet = chart.getWorkbook().getSheetAt(0);
        updateCTTable(sheet, usedSeriesDatas);

        removeExtraSheetCell(sheet, data.getCategories().length, totalSeriesCount, usedSeriesDatas.size());

        for (XDDFChartData chartData : chartSeries) {
            plot(chart, chartData);
        }
        setTitle(chart, data.getChartTitle());
    }

    protected void processNewSeries(XDDFChartData chartData, Series addSeries) {
    }

    private int ensureSeriesCount(XWPFChart chart, List<XDDFChartData> chartSeries) throws IllegalAccessException {
        // hack for poi 4.1.1+: repair seriesCount value,
        int totalSeriesCount = chartSeries.stream().mapToInt(XDDFChartData::getSeriesCount).sum();
        Field field = ReflectionUtils.findField(XDDFChart.class, "seriesCount");
        field.setAccessible(true);
        field.set(chart, totalSeriesCount);
        return totalSeriesCount;
    }

    private void validate(List<XDDFChartData> chartSeries, ChartMultiSeriesRenderData data) {
        // validate combo
        if (chartSeries.size() >= 2) {
            long nullCount = data.getSeriesDatas().stream().filter(d -> null == d.getComboType()).count();
            if (nullCount > 0) throw new RenderException("Combo chart must set comboType field of series!");
        }
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
