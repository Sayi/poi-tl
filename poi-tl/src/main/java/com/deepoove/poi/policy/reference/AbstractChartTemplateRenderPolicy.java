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
package com.deepoove.poi.policy.reference;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTitle;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTx;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;

import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.template.ChartTemplate;

public abstract class AbstractChartTemplateRenderPolicy<T> extends AbstractTemplateRenderPolicy<ChartTemplate, T> {

    protected final int FIRST_ROW = 1;
    protected final int CATEGORY_COL = 0;
    protected final int VALUE_START_COL = 1;

    protected XDDFCategoryDataSource createCategoryDataSource(XWPFChart chart, String[] categories) {
        return XDDFDataSourcesFactory.fromArray(categories,
                chart.formatRange(new CellRangeAddress(FIRST_ROW, categories.length, CATEGORY_COL, CATEGORY_COL)),
                CATEGORY_COL);
    }

    protected <N extends Number> XDDFNumericalDataSource<Number> createValueDataSource(XWPFChart chart, N[] data,
            int index) {
        return XDDFDataSourcesFactory.fromArray(data,
                chart.formatRange(
                        new CellRangeAddress(FIRST_ROW, data.length, index + VALUE_START_COL, index + VALUE_START_COL)),
                index + VALUE_START_COL);
    }

    protected void removeExtraSeries(final XDDFChartData chartData, final int orignSize, final int seriesSize) {
        if (orignSize - seriesSize > 0) {
            // clear extra series
            for (int j = orignSize - 1; j >= seriesSize; j--) {
                chartData.removeSeries(j);
            }
        }
    }

    protected void removeExtraSheetCell(XSSFSheet sheet, final int numOfPoints, final int orignSize,
            final int seriesSize) {
        if (orignSize - seriesSize > 0) {
            // clear extra sheet column
            for (int i = 0; i < numOfPoints + 1; i++) {
                for (int j = orignSize; j > seriesSize; j--) {
                    XSSFRow row = sheet.getRow(i);
                    if (null == row)
                        continue;
                    XSSFCell cell = row.getCell(j);
                    if (null != cell)
                        row.removeCell(cell);
                }
            }
        }
    }

    protected void updateCTTable(XSSFSheet sheet, List<SeriesRenderData> seriesDatas) {
        final int seriesSize = seriesDatas.size();
        final int numOfPoints = seriesDatas.get(0).getValues().length;

        CTTable ctTable = getSheetTable(sheet);
        String prefix = seriesSize >= 26 ? String.valueOf((char) ('A' + ((seriesSize / 26) - 1))) : "";
        char c = (char) ('A' + (seriesSize % 26));
        String ref = "A1:" + prefix + c + (numOfPoints + 1);
        ctTable.setRef(ref);
        CTTableColumns tableColumns = ctTable.getTableColumns();
        tableColumns.setCount(seriesSize + 1);

        int size = tableColumns.sizeOfTableColumnArray();
        for (int i = size - 1; i >= 0; i--) {
            tableColumns.removeTableColumn(i);
        }

        CTTableColumn column = tableColumns.addNewTableColumn();
        // category
        column.setId(1);
        column.setName(" ");
        // series
        for (int i = 0; i < seriesSize; i++) {
            column = tableColumns.addNewTableColumn();
            column.setId(1 + i + 1);
            column.setName(seriesDatas.get(i).getName());
        }
    }

    protected CTTable getSheetTable(XSSFSheet sheet) {
        if (sheet.getTables().size() == 0) {
            XSSFTable newTable = sheet.createTable(null);
            newTable.getCTTable().addNewTableColumns();
            sheet.getTables().add(newTable);
        }
        return sheet.getTables().get(0).getCTTable();
    }

    @SuppressWarnings("deprecation")
    protected void plot(XWPFChart chart, XDDFChartData data) throws Exception {
        XSSFSheet sheet = chart.getWorkbook().getSheetAt(0);
        Method method = XDDFChart.class.getDeclaredMethod("fillSheet", XSSFSheet.class, XDDFDataSource.class,
                XDDFNumericalDataSource.class);
        method.setAccessible(true);
        for (XDDFChartData.Series series : data.getSeries()) {
            boolean numeric = series.getCategoryData().isNumeric();
            if (!numeric) {
                Method getAxDSMethod = series.getClass().getDeclaredMethod("getAxDS");
                getAxDSMethod.setAccessible(true);
                CTAxDataSource axDataSource = (CTAxDataSource) getAxDSMethod.invoke(series);
                if (axDataSource.isSetNumRef()) {
                    axDataSource.unsetNumRef();
                }
                if (axDataSource.isSetNumLit()) {
                    axDataSource.unsetNumLit();
                }
            }
            series.plot();
            method.invoke(chart, sheet, series.getCategoryData(), series.getValuesData());
        }
    }

    protected void setTitle(XWPFChart chart, String title) {
        if (null == title && chart.getCTChart().isSetTitle()) {
            chart.getCTChart().unsetTitle();
            return;
        }
        boolean isSet = false;
        CTTitle ctTitle = chart.getCTChart().getTitle();
        if (null != ctTitle) {
            if (!ctTitle.isSetTx()) {
                ctTitle.addNewTx();
            }
            CTTx tx = ctTitle.getTx();
            if (tx.isSetStrRef()) {
                tx.unsetStrRef();
            }
            if (!tx.isSetRich()) {
                tx.addNewRich();
            }
            CTTextBody body = tx.getRich();
            if (body.sizeOfPArray() > 0) {
                // remove all but first paragraph
                for (int i = body.sizeOfPArray() - 1; i > 0; i--) {
                    body.removeP(i);
                }
                CTTextParagraph pArray = body.getPArray(0);
                if (pArray.sizeOfRArray() > 0) {
                    for (int i = pArray.sizeOfRArray() - 1; i > 0; i--) {
                        pArray.removeR(i);
                    }
                    CTRegularTextRun rArray = pArray.getRArray(0);
                    rArray.setT(title);
                    isSet = true;
                }
            }
        }
        if (!isSet) {
            chart.setTitleText(title);
            chart.setTitleOverlay(false);
        }
    }

}
