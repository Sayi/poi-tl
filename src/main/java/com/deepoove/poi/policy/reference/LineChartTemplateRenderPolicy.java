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

import java.util.List;

import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xwpf.usermodel.XWPFChart;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.LineChartRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.template.ChartTemplate;

public class LineChartTemplateRenderPolicy extends AbstractChartTemplateRenderPolicy<LineChartRenderData> {

    @Override
    public void doRender(ChartTemplate eleTemplate, LineChartRenderData data, XWPFTemplate template) throws Exception {
        XWPFChart chart = eleTemplate.getChart();
        XDDFChartData line = chart.getChartSeries().get(0);

        List<Series> orignSeries = line.getSeries();
        int orignSize = orignSeries.size();
        List<SeriesRenderData> seriesDatas = data.getSeriesDatas();
        int seriesSize = seriesDatas.size();

        XDDFDataSource<?> categoriesData = createCategoryDataSource(chart, data.getCategories());
        for (int i = 0; i < seriesSize; i++) {
            XDDFNumericalDataSource<? extends Number> valuesData = createValueDataSource(chart,
                    seriesDatas.get(i).getData(), i);

            XDDFChartData.Series currentSeries = null;
            if (i < orignSize) {
                currentSeries = orignSeries.get(i);
                currentSeries.replaceData(categoriesData, valuesData);
            } else {
                // add series, should copy series with style
                currentSeries = line.addSeries(categoriesData, valuesData);
            }
            String name = seriesDatas.get(i).getName();
            currentSeries.setTitle(name, chart.setSheetTitle(name, i + VALUE_START_COL));
        }

        XSSFSheet sheet = chart.getWorkbook().getSheetAt(0);
        updateCTTable(sheet, seriesDatas);

        // clear extra series
        removeExtraSeries(line, sheet, data.getCategories().length, orignSeries, seriesSize);

        chart.plot(line);
        chart.setTitleText(data.getChartTitle());
        chart.setTitleOverlay(false);
    }

}
