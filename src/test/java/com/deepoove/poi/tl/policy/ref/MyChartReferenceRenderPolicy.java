package com.deepoove.poi.tl.policy.ref;

import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.reference.AbstractTemplateRenderPolicy;
import com.deepoove.poi.template.ChartTemplate;

public class MyChartReferenceRenderPolicy extends AbstractTemplateRenderPolicy<ChartTemplate, Void> {

    @Override
    public void doRender(ChartTemplate eleTemplate, Void t, XWPFTemplate template) throws Exception {
        XWPFChart chart = eleTemplate.getChart();
        String[] categories = new String[] { "中文", "English", "日本語", "português" };
        Double[] values1 = new Double[] { 15.0, 6.0, 18.0, 31.0 };
        Double[] values2 = new Double[] { 223.0, 119.0, 154.0, 442.0 };
        setBarData(chart, "MyChartReferenceRenderPolicy", new String[] { "countries", "speakers"},
                categories, values1, values2);
    }

    private static void setBarData(XWPFChart chart, String chartTitle, String[] series, String[] categories,
            Double[] values1, Double[] values2) throws Exception {
        final List<XDDFChartData> data = chart.getChartSeries();
        final XDDFBarChartData bar = (XDDFBarChartData) data.get(0);

        final int numOfPoints = categories.length;
        final String categoryDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 0, 0));
        final String valuesDataRange = chart.formatRange(new CellRangeAddress(1, numOfPoints, 1, 1));
        final String valuesDataRange2 = chart.formatRange(new CellRangeAddress(1, numOfPoints, 2, 2));
        final XDDFDataSource<?> categoriesData = XDDFDataSourcesFactory.fromArray(categories, categoryDataRange, 0);
        final XDDFNumericalDataSource<? extends Number> valuesData = XDDFDataSourcesFactory.fromArray(values1,
                valuesDataRange, 1);
        // values1[6] = 16.0; // if you ever want to change the underlying data
        final XDDFNumericalDataSource<? extends Number> valuesData2 = XDDFDataSourcesFactory.fromArray(values2,
                valuesDataRange2, 2);

        XDDFChartData.Series series1 = bar.getSeries().get(0);
        series1.replaceData(categoriesData, valuesData);
        series1.setTitle(series[0], chart.setSheetTitle(series[0], 1));
        XDDFChartData.Series series2 = bar.addSeries(categoriesData, valuesData2);
        series2.setTitle(series[1], chart.setSheetTitle(series[1], 2));
        //chart.setSheetTitle("", 0);
       CTTable ctTable = getSheetTable(chart.getWorkbook().getSheetAt(0));
       ctTable.setRef("A1:C5");
       CTTableColumns tableColumns = ctTable.getTableColumns();
       tableColumns.setCount(3);
       
       int size = tableColumns.sizeOfTableColumnArray();
       for (int i = size - 1; i >= 0; i--) {
           tableColumns.removeTableColumn(i);
       }
       
       for (int i = 1; i <= 3; i++) {
           CTTableColumn column = tableColumns.addNewTableColumn();
           column.setId(i);
           if (i >= 2) {
               column.setName(series[i-2]);
           }else {
               column.setName(" ");
               
           }
       }

        chart.plot(bar);
        chart.setTitleText(chartTitle); // https://stackoverflow.com/questions/30532612
        chart.setTitleOverlay(false);
    }
    
    private static void updateSheetTable(CTTable ctTable, String title, int index) {
        CTTableColumns tableColumnList = ctTable.getTableColumns();
        CTTableColumn column = null;
        int columnCount  = tableColumnList.getTableColumnList().size()-1;
        for( int i = columnCount; i < index; i++) {
            column = tableColumnList.addNewTableColumn();
            column.setId(i);
        }
        column = tableColumnList.getTableColumnArray(index);
        column.setName(title);
    }
    
    private static CTTable getSheetTable(XSSFSheet sheet) {
        if(sheet.getTables().size() == 0)
        {
            XSSFTable newTable = sheet.createTable(null);
            newTable.getCTTable().addNewTableColumns();
            sheet.getTables().add(newTable);
        }
        return sheet.getTables().get(0).getCTTable();
    }
    
    static CellReference setTitleInDataSheet(XWPFChart chart, String title, int column) throws Exception {
        XSSFWorkbook workbook = chart.getWorkbook();
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
        if (row == null)
            row = sheet.createRow(0);
        XSSFCell cell = row.getCell(column);
        if (cell == null)
            cell = row.createCell(column);
        cell.setCellValue(title);
        return new CellReference(sheet.getSheetName(), 0, column, true, true);
    }

}
