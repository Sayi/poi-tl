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

/**
 * Bubble chart render data
 * 
 * @author Sayi
 */
public class ChartBubbleRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private String chartTitle;
    private String xAxisTitle;
    private String yAxisTitle;
    private List<BubbleSeriesRenderData> seriesDatas = new ArrayList<>();

    public ChartBubbleRenderData() {
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public String getxAxisTitle() {
        return xAxisTitle;
    }

    public void setxAxisTitle(String xAxisTitle) {
        this.xAxisTitle = xAxisTitle;
    }

    public String getyAxisTitle() {
        return yAxisTitle;
    }

    public void setyAxisTitle(String yAxisTitle) {
        this.yAxisTitle = yAxisTitle;
    }

    public List<BubbleSeriesRenderData> getSeriesDatas() {
        return seriesDatas;
    }

    public void setSeriesDatas(List<BubbleSeriesRenderData> seriesDatas) {
        this.seriesDatas = seriesDatas;
    }

}
