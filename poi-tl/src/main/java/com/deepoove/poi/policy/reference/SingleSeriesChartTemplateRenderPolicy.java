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

import java.util.Arrays;

import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xwpf.usermodel.XWPFChart;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.util.ChartUtils;

/**
 * single series chart
 * 
 * @author Sayi
 */
public class SingleSeriesChartTemplateRenderPolicy
        extends AbstractChartTemplateRenderPolicy<ChartSingleSeriesRenderData> {

    @Override
    public void doRender(ChartTemplate eleTemplate, ChartSingleSeriesRenderData data, XWPFTemplate template)
            throws Exception {
        XWPFChart chart = eleTemplate.getChart();
        XDDFChartData pie = ChartUtils.getChartSeries(chart).get(0);
        SeriesRenderData seriesDatas = data.getSeriesData();

        XDDFDataSource<?> categoriesData = createCategoryDataSource(chart, data.getCategories());
        XDDFNumericalDataSource<? extends Number> valuesData = createValueDataSource(chart, seriesDatas.getValues(), 0);

        XDDFChartData.Series currentSeries = pie.getSeries(0);
        currentSeries.replaceData(categoriesData, valuesData);
        currentSeries.setTitle(seriesDatas.getName(), chart.setSheetTitle(seriesDatas.getName(), VALUE_START_COL));
        updateCTTable(chart.getWorkbook().getSheetAt(0), Arrays.asList(seriesDatas));

        plot(chart, pie);
        setTitle(chart, data.getChartTitle());
    }

}
