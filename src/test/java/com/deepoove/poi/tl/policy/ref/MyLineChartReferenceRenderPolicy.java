package com.deepoove.poi.tl.policy.ref;

import java.util.List;

import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xwpf.usermodel.XWPFChart;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.reference.AbstractTemplateRenderPolicy;
import com.deepoove.poi.template.ChartTemplate;

public class MyLineChartReferenceRenderPolicy extends AbstractTemplateRenderPolicy<ChartTemplate, Object> {

    @Override
    public void doRender(ChartTemplate eleTemplate, Object t, XWPFTemplate template) throws Exception {
        XWPFChart chart = eleTemplate.getChart();
        final List<XDDFChartData> data = chart.getChartSeries();
        final XDDFLineChartData line = (XDDFLineChartData) data.get(0);
//        XDDFDataSource<String> countries = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(0, 0, 0, 6));
        XDDFCategoryDataSource countries = XDDFDataSourcesFactory.fromArray(new String[] {"俄罗斯","加拿大","美国","中国","巴西","澳大利亚","印度"}, null);
        //数据1，
//      XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, 1, 0, 6));
        XDDFNumericalDataSource<Integer> values0 = XDDFDataSourcesFactory.fromArray(new Integer[] {17098242,9984670,9826675,9596961,8514877,7741220,3287263}, null);
        XDDFNumericalDataSource<Integer> values1 = XDDFDataSourcesFactory.fromArray(new Integer[] {27098242,9984670,9826675,9596961,8514877,7741220,3287263}, null);
        XDDFNumericalDataSource<Integer> values2 = XDDFDataSourcesFactory.fromArray(new Integer[] {37098242,9984670,9826675,9596961,8514877,7741220,3287263}, null);
        XDDFNumericalDataSource<Integer> values3 = XDDFDataSourcesFactory.fromArray(new Integer[] {47098242,9984670,9826675,9596961,8514877,7741220,3287263}, null);
        //图表加载数据 
        XDDFChartData.Series series0 = line.getSeries().get(0);
        series0.replaceData(countries, values0);
//        series0.setTitle("A", null);
        XDDFChartData.Series series1 = line.getSeries().get(1);
        series1.replaceData(countries, values1);
        XDDFChartData.Series series2 = line.getSeries().get(2);
        series2.replaceData(countries, values2);
        XDDFLineChartData.Series series3 = (org.apache.poi.xddf.usermodel.chart.XDDFLineChartData.Series) line.addSeries(countries, values3);
        series3.setSmooth(false);
        series3.setMarkerSize((short) 6);
        series3.setMarkerStyle(MarkerStyle.NONE);
//        series3.setTitle("D3", null);


        //绘制
        chart.plot(line);
        chart.setTitleText("Title");
        chart.setTitleOverlay(false);

    }

}
