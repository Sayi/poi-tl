package com.deepoove.poi.tl.source;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFShapeProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;

public class ChartRenderTest {

    public void testChartRender() throws Exception {

//        Map<String, Object> datas = new HashMap<String, Object>() {
//            {
//                put("chart", "");
//            }
//        };

//        Configure configure = Configure.newBuilder().customPolicy("chart", new ChartRenderPolicy()) // 自定义标签text的策略：不是文本，是图片
//                .build();
//        XWPFTemplate.compile("src/test/resources/chart.docx", configure).render(datas)
//                .writeToFile("out_chart.docx");

    }

    public void testChart() throws Exception {
        try (XWPFDocument doc = new XWPFDocument()) {

            XWPFChart chart = doc.createChart(5143500, 2495550);
            XSSFTable newTable = chart.getWorkbook().getSheetAt(0).createTable(null);
            CTTableColumns addNewTableColumns = newTable.getCTTable().addNewTableColumns();
            addNewTableColumns.setCount(1);
            CTTableColumn addNewTableColumn = addNewTableColumns.addNewTableColumn();
            addNewTableColumn.setId(0);
            chart.getWorkbook().getSheetAt(0).getTables().add(newTable);

            // XDDFChartLegend legend = chart.getOrAddLegend();
            // legend.setPosition(LegendPosition.TOP_RIGHT);

            String[] series = new String[] { "countries", "speakers", "language" };
            String[] categories = new String[] { "中文", "English", "日本語", "português" };
            Double[] values1 = new Double[] { 58.0, 40.0, 38.0, 118.0 };
            // Double[] values2 = new Double[] {315.0,243.0,699.0,378.0};
            // XDDFDataSource<String> cat =
            // XDDFDataSourcesFactory.fromStringCellRange(sheet,
            // new CellRangeAddress(0, 0, 0, NUM_OF_COLUMNS - 1));
            // XDDFNumericalDataSource<Double> val =
            // XDDFDataSourcesFactory.fromNumericCellRange(sheet,
            // new CellRangeAddress(1, 1, 0, NUM_OF_COLUMNS - 1));
            final int numOfPoints = categories.length;
            final String categoryDataRange = chart
                    .formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
            final String valuesDataRange = chart
                    .formatRange(new CellRangeAddress(1, numOfPoints, 1, 1));
            // final String valuesDataRange2 = chart.formatRange(new
            // CellRangeAddress(1, numOfPoints, 2, 2));
            final XDDFDataSource<?> categoriesData = XDDFDataSourcesFactory.fromArray(categories,
                    categoryDataRange, 0);
            final XDDFNumericalDataSource<? extends Number> valuesData = XDDFDataSourcesFactory
                    .fromArray(values1, valuesDataRange, 1);
            // values1[6] = 16.0; // if you ever want to change the underlying
            // data
            // final XDDFNumericalDataSource<? extends Number> valuesData2 =
            // XDDFDataSourcesFactory.fromArray(values2, valuesDataRange2, 2);

            // XDDFChartData data = new
            // XDDFPieChartData(chart.getCTChart().getPlotArea().addNewPieChart());
            XDDFCategoryAxis categoryAxis = chart.createCategoryAxis(AxisPosition.LEFT);
            XDDFValueAxis createValueAxis = chart.createValueAxis(AxisPosition.BOTTOM);
            XDDFChartData data = chart.createData(ChartTypes.BAR, categoryAxis, createValueAxis);
            // XDDFChartData data = new
            // XDDFBarChartData(chart.getCTChart().getPlotArea().addNewBarChart(),
            // null, null);
            // data.setVaryColors(true);
            Series addSeries = data.addSeries(categoriesData, valuesData);
            addSeries.setTitle(series[0], chart.setSheetTitle(series[0], 0));
            // Series series2 = data.addSeries(categoriesData, valuesData2);
            // series2.setTitle(series[1], chart.setSheetTitle(series[1], 1));
            chart.plot(data);
            chart.setTitleText("My Bar Chart");
            chart.setTitleOverlay(false);

            chart.getWorkbook().getSheetAt(0).removeTable(newTable);

            try (FileOutputStream out = new FileOutputStream("out_chart_demo.docx")) {
                doc.write(out);
            }
        }
    }

    public void testBar() throws IOException, InvalidFormatException {
        try (XWPFDocument doc = new XWPFDocument()) {
            XWPFChart chart = doc.createChart(5143500, 2495550);
            chart.setTitleText("BarChart");
            chart.setTitleOverlay(false);
            XDDFChartLegend legend = chart.getOrAddLegend();
            legend.setPosition(LegendPosition.TOP_RIGHT);

            // Use a category axis for the bottom axis.
            XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            bottomAxis.setTitle("x");
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
            leftAxis.setTitle("f(x)");
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            String[] series = new String[] { "countries", "speakers", "language" };
            String[] categories = new String[] { "中文", "English", "日本語", "português" };
            Double[] values1 = new Double[] { 58.0, 40.0, 38.0, 118.0 };
            Double[] values2 = new Double[] { 315.0, 243.0, 699.0, 378.0 };
            final int numOfPoints = categories.length;
            final String categoryDataRange = chart
                    .formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
            final String valuesDataRange = chart
                    .formatRange(new CellRangeAddress(1, numOfPoints, 1, 1));
            final String valuesDataRange2 = chart
                    .formatRange(new CellRangeAddress(1, numOfPoints, 2, 2));
            final XDDFDataSource<?> categoriesData = XDDFDataSourcesFactory.fromArray(categories,
                    categoryDataRange, 0);
            final XDDFNumericalDataSource<? extends Number> valuesData = XDDFDataSourcesFactory
                    .fromArray(values1, valuesDataRange, 1);
            // values1[6] = 16.0; // if you ever want to change the underlying
            // data
            final XDDFNumericalDataSource<? extends Number> valuesData2 = XDDFDataSourcesFactory
                    .fromArray(values2, valuesDataRange2, 2);

            XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
            XDDFChartData.Series series1 = data.addSeries(categoriesData, valuesData);
            series1.setTitle("2x", null);
//            XDDFChartData.Series series2 = data.addSeries(categoriesData, valuesData2);
//            series2.setTitle("3x", null);
            chart.plot(data);

            // in order to transform a bar chart into a column chart, you just
            // need to change the bar direction
            XDDFBarChartData bar = (XDDFBarChartData) data;
            bar.setBarDirection(BarDirection.BAR);
            // looking for "Stacked Bar Chart"? uncomment the following line
            // bar.setBarGrouping(BarGrouping.STACKED);

            solidFillSeries(data, 0, PresetColor.CHARTREUSE);
//            solidFillSeries(data, 1, PresetColor.TURQUOISE);

            // Write the output to a file
            try (FileOutputStream out = new FileOutputStream("out_bar_demo.docx")) {
                doc.write(out);
            }
        }
    }

    private static void solidFillSeries(XDDFChartData data, int index, PresetColor color) {
        XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
        XDDFChartData.Series series = data.getSeries().get(index);
        XDDFShapeProperties properties = series.getShapeProperties();
        if (properties == null) {
            properties = new XDDFShapeProperties();
        }
        properties.setFillProperties(fill);
        series.setShapeProperties(properties);
    }

}
