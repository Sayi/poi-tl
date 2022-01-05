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

package com.deepoove.poi.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;

import com.deepoove.poi.xwpf.XDDFOfPieChartData;

public final class ChartUtils {

    public static List<XDDFChartData> getChartSeries(XWPFChart chart) {
        List<XDDFChartData> series = new LinkedList<>();
        List<XDDFChartData> chartSeries = chart.getChartSeries();
        series.addAll(chartSeries);
        CTPlotArea plotArea = chart.getCTChart().getPlotArea();
        for (int i = 0; i < plotArea.sizeOfOfPieChartArray(); i++) {
            CTOfPieChart barChart = plotArea.getOfPieChartArray(i);
            series.add(new XDDFOfPieChartData(chart, barChart));
        }
        return series;
    }

}
