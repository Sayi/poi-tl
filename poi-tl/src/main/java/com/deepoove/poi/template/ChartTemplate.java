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
package com.deepoove.poi.template;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.processor.Visitor;
import com.deepoove.poi.util.ChartUtils;
import com.deepoove.poi.xwpf.XDDFOfPieChartData;

/**
 * chart docx template element: XWPFChart
 * 
 * @author Sayi
 * @version 1.8.0
 */
public class ChartTemplate extends ElementTemplate {

    protected XWPFChart chart;
    protected ChartTypes chartType;
    protected XWPFRun run;

    public ChartTemplate(String tagName, XWPFChart chart, XWPFRun run) {
        this.tagName = tagName;
        this.chart = chart;
        this.run = run;
        this.chartType = readChartType(this.chart);
    }

    private ChartTypes readChartType(XWPFChart chart) {
        List<XDDFChartData> chartSeries = ChartUtils.getChartSeries(chart);
        if (CollectionUtils.isEmpty(chartSeries)) {
            return null;
        }
        XDDFChartData chartData = chartSeries.get(0);
        ChartTypes chartType = null;
        if (chartData.getClass() == XDDFAreaChartData.class) {
            chartType = ChartTypes.AREA;
        } else if (chartData.getClass() == XDDFArea3DChartData.class) {
            chartType = ChartTypes.AREA3D;
        } else if (chartData.getClass() == XDDFBarChartData.class) {
            chartType = ChartTypes.BAR;
        } else if (chartData.getClass() == XDDFBar3DChartData.class) {
            chartType = ChartTypes.BAR3D;
        } else if (chartData.getClass() == XDDFLineChartData.class) {
            chartType = ChartTypes.LINE;
        } else if (chartData.getClass() == XDDFLine3DChartData.class) {
            chartType = ChartTypes.LINE3D;
        } else if (chartData.getClass() == XDDFPieChartData.class || chartData.getClass() == XDDFOfPieChartData.class) {
            chartType = ChartTypes.PIE;
        } else if (chartData.getClass() == XDDFPie3DChartData.class) {
            chartType = ChartTypes.PIE3D;
        } else if (chartData.getClass() == XDDFRadarChartData.class) {
            chartType = ChartTypes.RADAR;
        } else if (chartData.getClass() == XDDFScatterChartData.class) {
            chartType = ChartTypes.SCATTER;
        } else if (chartData.getClass() == XDDFSurfaceChartData.class) {
            chartType = ChartTypes.SURFACE;
        } else if (chartData.getClass() == XDDFSurface3DChartData.class) {
            chartType = ChartTypes.SURFACE3D;
        } else if (chartData.getClass() == XDDFDoughnutChartData.class) {
            chartType = ChartTypes.DOUGHNUT;
        }
        return chartType;
    }

    public XWPFChart getChart() {
        return chart;
    }

    public void setChart(XWPFChart chart) {
        this.chart = chart;
    }

    public XWPFRun getRun() {
        return run;
    }

    public void setRun(XWPFRun run) {
        this.run = run;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public RenderPolicy findPolicy(Configure config) {
        RenderPolicy policy = config.getCustomPolicy(tagName);
        if (null == policy) {
            policy = config.getChartPolicy(chartType);
        }
        return null == policy ? config.getTemplatePolicy(this.getClass()) : policy;
    }

    @Override
    public String toString() {
        return "Chart::" + source;
    }

}
