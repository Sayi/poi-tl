package com.deepoove.poi.tl.policy.ref;

import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFPieChartData;
import org.apache.poi.xwpf.usermodel.XWPFChart;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.reference.AbstractTemplateRenderPolicy;
import com.deepoove.poi.template.ChartTemplate;

public class MyPieChartReferenceRenderPolicy extends AbstractTemplateRenderPolicy<ChartTemplate, Object> {

    @Override
    public void doRender(ChartTemplate eleTemplate, Object t, XWPFTemplate template) throws Exception {
        XWPFChart chart = eleTemplate.getChart();
        final List<XDDFChartData> data = chart.getChartSeries();
        final XDDFPieChartData pie = (XDDFPieChartData) data.get(0);
//        XDDFDataSource<String> countries = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(0, 0, 0, 6));
        String[] categories = new String[] {"俄罗斯","加拿大","美国","中国","巴西","澳大利亚","印度"};
        final int numOfPoints = categories.length;
        final String categoryDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
        final XDDFDataSource<?> categoriesData = XDDFDataSourcesFactory.fromArray(categories, categoryDataRange, 0);
        //数据1，
//      XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, 1, 0, 6));
//        XDDFNumericalDataSource<Integer> values = XDDFDataSourcesFactory.fromArray(new Integer[] {17098242,9984670,9826675,9596961,8514877,7741220,3287263}, null);
        Integer[] datas = new Integer[] {17098242,9984670,9826675,9596961,8514877,7741220,3287263};
        final String valuesDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 1, 1));
        final XDDFNumericalDataSource<? extends Number> valuesData = XDDFDataSourcesFactory.fromArray(datas,
                valuesDataRange, 1);
        //设置为可变颜色
        pie.setVaryColors(true);
        //图表加载数据 
        XDDFChartData.Series series1 = pie.getSeries().get(0);
        series1.replaceData(categoriesData, valuesData);
        series1.setTitle("Title", chart.setSheetTitle("Title", 1));


        //绘制
        chart.plot(pie);
        chart.setTitleText("Title");
        chart.setTitleOverlay(false);

    }

}
