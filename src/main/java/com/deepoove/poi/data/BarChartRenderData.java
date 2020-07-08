package com.deepoove.poi.data;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class BarChartRenderData implements RenderData {

    private String chartTitle;
    private String[] categories;
    private List<SeriesRenderData> seriesDatas;

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

    public static void main(String[] args) {
        BarChartRenderData bar = new BarChartRenderData();
        bar.setCategories(new String[] { "中文", "English", "日本語", "português" });
        List<SeriesRenderData> seriesRenderData = new ArrayList<SeriesRenderData>();
        SeriesRenderData s0 = new SeriesRenderData();
        s0.setName("countries");
        s0.setData(new Double[] { 15.0, 6.0, 18.0, 31.0 });
        seriesRenderData.add(s0);
        SeriesRenderData s1 = new SeriesRenderData();
        s1.setName("speakers");
        s1.setData(new Double[] { 223.0, 119.0, 154.0, 442.0 });
        seriesRenderData.add(s1);
        bar.setSeriesDatas(seriesRenderData);
        System.out.println(JSON.toJSONString(bar));

        System.out.println((char) ('A' + 2));
    }

}
