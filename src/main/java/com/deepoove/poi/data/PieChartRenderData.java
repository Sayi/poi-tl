package com.deepoove.poi.data;

public class PieChartRenderData implements RenderData {

    private String chartTitle;
    private String[] categories;
    private SeriesRenderData seriesData;

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

    public SeriesRenderData getSeriesData() {
        return seriesData;
    }

    public void setSeriesData(SeriesRenderData seriesData) {
        this.seriesData = seriesData;
    }

}
