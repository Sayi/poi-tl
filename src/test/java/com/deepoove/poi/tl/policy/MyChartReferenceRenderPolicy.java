package com.deepoove.poi.tl.policy;

import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xwpf.usermodel.XWPFChart;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.ReferenceRenderPolicy;

public class MyChartReferenceRenderPolicy extends ReferenceRenderPolicy<XWPFChart> {
    
    public int index() {
        return 0;
    }
    
    public void doRender(XWPFChart chart, XWPFTemplate template) {
        String[] categories = new String[] {"中文","English","日本語","português"};
        Double[] values1 = new Double[] {15.0,6.0,18.0,31.0};
        Double[] values2 = new Double[] {223.0,119.0,154.0,442.0};
        setBarData(chart, "MyChartReferenceRenderPolicy", new String[] {"countries","speakers","language"}, categories , values1 , values2);
    }

    private static void setBarData(XWPFChart chart, String chartTitle, String[] series, String[] categories, Double[] values1, Double[] values2) {
        final List<XDDFChartData> data = chart.getChartSeries();
        final XDDFBarChartData bar = (XDDFBarChartData) data.get(0);

        final int numOfPoints = categories.length;
        final String categoryDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
        final String valuesDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 1, 1));
        final String valuesDataRange2 = chart.formatRange(new CellRangeAddress(1, numOfPoints, 2, 2));
        final XDDFDataSource<?> categoriesData = XDDFDataSourcesFactory.fromArray(categories, categoryDataRange, 0);
        final XDDFNumericalDataSource<? extends Number> valuesData = XDDFDataSourcesFactory.fromArray(values1, valuesDataRange, 1);
//        values1[6] = 16.0; // if you ever want to change the underlying data
        final XDDFNumericalDataSource<? extends Number> valuesData2 = XDDFDataSourcesFactory.fromArray(values2, valuesDataRange2, 2);

        XDDFChartData.Series series1 = bar.getSeries().get(0);
        series1.replaceData(categoriesData, valuesData);
        series1.setTitle(series[0], chart.setSheetTitle(series[0], 0));
        XDDFChartData.Series series2 = bar.addSeries(categoriesData, valuesData2);
        series2.setTitle(series[1], chart.setSheetTitle(series[1], 1));

        chart.plot(bar);
        chart.setTitleText(chartTitle); // https://stackoverflow.com/questions/30532612
        chart.setTitleOverlay(false);
    }

}
