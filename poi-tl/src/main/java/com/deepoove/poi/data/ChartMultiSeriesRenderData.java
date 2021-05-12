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

import java.util.List;

/**
 * multi series chart: Bar(3D), Line(3D), Radar， Area(3D)
 * 
 * @author Sayi
 */
public class ChartMultiSeriesRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private String chartTitle;
    private String[] categories;
    private List<SeriesRenderData> seriesDatas;

    public ChartMultiSeriesRenderData() {
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public List<SeriesRenderData> getSeriesDatas() {
        return seriesDatas;
    }

    public void setSeriesDatas(List<SeriesRenderData> seriesDatas) {
        this.seriesDatas = seriesDatas;
    }

}
