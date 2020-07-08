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
import java.util.List;

import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xwpf.usermodel.XWPFChart;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData;
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
        XWPFChart chart = eleTemplate.getChart();
        XDDFChartData chartData = chart.getChartSeries().get(0);

        // hack for poi 4.1.1+: repair seriesCount value,
        Field field = ReflectionUtils.findField(XDDFChart.class, "seriesCount");
        field.setAccessible(true);
        field.set(chart, chartData.getSeriesCount());

        int orignSize = chartData.getSeriesCount();
        List<SeriesRenderData> seriesDatas = data.getSeriesDatas();
        int seriesSize = seriesDatas.size();

        XDDFDataSource<?> categoriesData = createCategoryDataSource(chart, data.getCategories());
        for (int i = 0; i < seriesSize; i++) {
            XDDFNumericalDataSource<? extends Number> valuesData = createValueDataSource(chart,
                    seriesDatas.get(i).getValues(), i);

            XDDFChartData.Series currentSeries = null;
            if (i < orignSize) {
                currentSeries = chartData.getSeries(i);
                currentSeries.replaceData(categoriesData, valuesData);
            } else {
                // add series, should copy series with style
                currentSeries = chartData.addSeries(categoriesData, valuesData);
            }
            String name = seriesDatas.get(i).getName();
            currentSeries.setTitle(name, chart.setSheetTitle(name, i + VALUE_START_COL));
        }

        XSSFSheet sheet = chart.getWorkbook().getSheetAt(0);
        updateCTTable(sheet, seriesDatas);

        // clear extra series
        removeExtraSeries(chartData, sheet, data.getCategories().length, orignSize, seriesSize);

        plot(chart, chartData);
        chart.setTitleText(data.getChartTitle());
        chart.setTitleOverlay(false);
    }

}
