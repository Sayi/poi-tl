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
package com.deepoove.poi.policy.reference;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFNoFillProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFAreaChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFBubbleChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataPoint;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFDoughnutChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFPieChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFRadarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFScatterChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAreaSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTAxDataSource;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBubbleSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTRadarSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTScatterSer;
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
import com.deepoove.poi.util.ChartUtils;

/**
 * Base class of the render policies that fill an existing chart.
 * <p>
 * It provides the shared plumbing of chart rendering: building the data sources
 * of the embedded workbook, plotting the series, styling them and updating the
 * chart and axis titles. Concrete policies only have to map their render data
 * onto these helpers.
 * </p>
 *
 * @param <T> the type of the data bound to the chart template
 */
public abstract class AbstractChartTemplateRenderPolicy<T> extends AbstractTemplateRenderPolicy<ChartTemplate, T> {

    protected final int FIRST_ROW = 1;

    /**
     * Creates a category data source backed by the embedded workbook.
     *
     * @param chart      the chart whose workbook is filled
     * @param categories the category values
     * @param col        the workbook column index
     * @return the data source
     */
    protected XDDFDataSource<?> createStringDataSource(XWPFChart chart, String[] categories, int col) {
        return XDDFDataSourcesFactory.fromArray(categories,
                chart.formatRange(new CellRangeAddress(FIRST_ROW, categories.length, col, col)), col);
    }

    /**
     * Creates a numerical data source backed by the embedded workbook.
     *
     * @param chart the chart whose workbook is filled
     * @param data  the numerical values
     * @param col   the workbook column index
     * @param <N>   the number type of the values
     * @return the data source
     */
    protected <N extends Number> XDDFNumericalDataSource<Number> createNumbericalDataSource(XWPFChart chart, N[] data,
            int col) {
        return XDDFDataSourcesFactory.fromArray(data,
                chart.formatRange(new CellRangeAddress(FIRST_ROW, data.length, col, col)), col);
    }

    /**
     * Removes the series that are no longer present in the new data.
     *
     * @param chartData  the chart data to trim
     * @param orignSize  the number of series currently in the chart
     * @param seriesSize the number of series of the new data
     */
    protected void removeExtraSeries(final XDDFChartData chartData, final int orignSize, final int seriesSize) {
        if (orignSize - seriesSize > 0) {
            // clear extra series
            for (int j = orignSize - 1; j >= seriesSize; j--) {
                chartData.removeSeries(j);
            }
        }
    }

    /**
     * Removes the workbook cells that are no longer covered by the new data.
     *
     * @param sheet       the sheet backing the chart
     * @param numOfPoints the number of categories
     * @param orignSize   the number of series currently in the sheet
     * @param seriesSize  the number of series of the new data
     */
    protected void removeExtraSheetCell(XSSFSheet sheet, final int numOfPoints, final int orignSize,
            final int seriesSize) {
        if (orignSize - seriesSize > 0) {
            // clear extra sheet column
            for (int i = 0; i < numOfPoints + 1; i++) {
                for (int j = orignSize; j > seriesSize; j--) {
                    XSSFRow row = sheet.getRow(i);
                    if (null == row) continue;
                    XSSFCell cell = row.getCell(j);
                    if (null != cell) row.removeCell(cell);
                }
            }
        }
    }

    /**
     * Rewrites the column definitions of the workbook table backing the chart.
     *
     * @param sheet      the sheet backing the chart
     * @param seriesDatas the series of the new data
     */
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
        column.setName(sheet.getRow(0).getCell(0).getStringCellValue());
        // series
        for (int i = 0; i < seriesSize; i++) {
            column = tableColumns.addNewTableColumn();
            column.setId(1 + i + 1);
            column.setName(seriesDatas.get(i).getName());
        }
    }

    /**
     * Returns the workbook table backing the chart, creating it when absent.
     *
     * @param sheet the sheet backing the chart
     * @return the table definition
     */
    protected CTTable getSheetTable(XSSFSheet sheet) {
        if (sheet.getTables().size() == 0) {
            XSSFTable newTable = sheet.createTable(null);
            newTable.getCTTable().addNewTableColumns();
            sheet.getTables().add(newTable);
        }
        return sheet.getTables().get(0).getCTTable();
    }

    /**
     * Plots the given series and fills the embedded workbook accordingly.
     * <p>
     * String categories are stored as literal data so that the workbook keeps
     * the expected layout.
     * </p>
     *
     * @param chart the chart to plot
     * @param data  the chart data to draw
     * @throws Exception when the underlying POI reflection fails
     */
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

    /**
     * Sets the chart title, removing the existing one when {@code title} is
     * null.
     *
     * @param chart the chart to update
     * @param title the new title, or {@code null} to remove it
     */
    protected void setTitle(XWPFChart chart, String title) {
        if (null == title && chart.getCTChart().isSetTitle()) {
            chart.getCTChart().unsetTitle();
            return;
        }
        CTTitle ctTitle = chart.getCTChart().getTitle();
        boolean isSet = setCTTitle(ctTitle, title);
        if (!isSet) {
            chart.setTitleText(title);
            chart.setTitleOverlay(false);
        }
    }

    /**
     * Sets the titles of the horizontal and vertical axes.
     *
     * @param chart      the chart to update
     * @param xAxisTitle the title of the horizontal axis, may be {@code null}
     * @param yAxisTitle the title of the vertical axis, may be {@code null}
     */
    protected void setAxisTitle(XWPFChart chart, String xAxisTitle, String yAxisTitle) {
        setAxisTitle(chart, xAxisTitle, yAxisTitle, null);
    }

    /**
     * Sets the titles of the horizontal, vertical and secondary vertical axes.
     *
     * @param chart               the chart to update
     * @param xAxisTitle          the title of the horizontal axis, may be
     *                            {@code null}
     * @param yAxisTitle          the title of the vertical axis, may be
     *                            {@code null}
     * @param secondaryYAxisTitle the title of the secondary vertical axis, may
     *                            be {@code null}
     */
    protected void setAxisTitle(XWPFChart chart, String xAxisTitle, String yAxisTitle, String secondaryYAxisTitle) {
        if (null == xAxisTitle && null == yAxisTitle && null == secondaryYAxisTitle) return;
        Map<Long, XDDFValueAxis> valueAxes = ChartUtils.getValueAxes(chart);
        if (valueAxes.isEmpty()) return;
        for (XDDFValueAxis valueAxe : valueAxes.values()) {
            AxisPosition position = valueAxe.getPosition();
            if (position == AxisPosition.BOTTOM || position == AxisPosition.TOP) {
                if (null != xAxisTitle) {
                    valueAxe.setTitle(xAxisTitle);
                }
            }
            if (position == AxisPosition.LEFT) {
                if (null != yAxisTitle) {
                    valueAxe.setTitle(yAxisTitle);
                }
            }
            if (position == AxisPosition.RIGHT) {
                if (null != secondaryYAxisTitle) {
                    valueAxe.setTitle(secondaryYAxisTitle);
                } else if (null != yAxisTitle) {
                    valueAxe.setTitle(yAxisTitle);
                }
            }
        }
    }

    /**
     * Applies the color, per point colors and data label settings of one series.
     *
     * @param series     the series to style
     * @param seriesData the styling configuration
     */
    protected void applySeriesStyle(XDDFChartData.Series series, SeriesRenderData seriesData) {
        if (null == series || null == seriesData) return;
        applySeriesColor(series, seriesData.getColor());
        applySeriesDataPointColors(series, seriesData.getColors());
        applySeriesDataLabels(series, seriesData.getShowDataLabels());
    }

    /**
     * Applies the fill color of one series.
     * <p>
     * {@code transparent} and {@code none} remove the fill instead of setting a
     * color.
     * </p>
     *
     * @param series the series to style
     * @param color  the color as {@code #RGB} or {@code #RRGGBB}
     */
    protected void applySeriesColor(XDDFChartData.Series series, String color) {
        if (StringUtils.isBlank(color)) return;
        if ("transparent".equalsIgnoreCase(color) || "none".equalsIgnoreCase(color)) {
            series.setFillProperties(new XDDFNoFillProperties());
            if (series instanceof XDDFLineChartData.Series) {
                XDDFLineProperties lineProps = new XDDFLineProperties();
                lineProps.setFillProperties(new XDDFNoFillProperties());
                ((XDDFLineChartData.Series) series).setLineProperties(lineProps);
            }
            return;
        }

        byte[] rgb = parseHexRgb(color);
        if (null != rgb) {
            XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(rgb));
            series.setFillProperties(fill);
            if (series instanceof XDDFLineChartData.Series) {
                XDDFLineProperties lineProps = new XDDFLineProperties();
                lineProps.setFillProperties(fill);
                ((XDDFLineChartData.Series) series).setLineProperties(lineProps);
            }
        }
    }

    /**
     * Applies one color per data point of a series.
     *
     * @param series the series to style
     * @param colors the colors as {@code #RGB} or {@code #RRGGBB}
     */
    protected void applySeriesDataPointColors(XDDFChartData.Series series, String[] colors) {
        if (null == colors) return;
        for (int i = 0; i < colors.length; i++) {
            String color = colors[i];
            if (StringUtils.isBlank(color)) continue;
            XDDFDataPoint dataPoint = series.getDataPoint(i);
            if (null == dataPoint) continue;
            if ("transparent".equalsIgnoreCase(color) || "none".equalsIgnoreCase(color)) {
                dataPoint.setFillProperties(new XDDFNoFillProperties());
            } else {
                byte[] rgb = parseHexRgb(color);
                if (null != rgb) {
                    dataPoint.setFillProperties(new XDDFSolidFillProperties(XDDFColor.from(rgb)));
                }
            }
        }
    }

    /**
     * Shows or hides the value labels of one series.
     *
     * @param series         the series to style
     * @param showDataLabels whether the labels must be shown, may be {@code null}
     *                       to leave the setting untouched
     */
    protected void applySeriesDataLabels(XDDFChartData.Series series, Boolean showDataLabels) {
        if (null == showDataLabels) return;
        CTDLbls dLbls = getOrCreateDLbls(series);
        if (null == dLbls) return;
        if (Boolean.TRUE.equals(showDataLabels)) {
            if (dLbls.isSetDelete()) {
                dLbls.unsetDelete();
            }
            if (!dLbls.isSetShowVal()) {
                dLbls.addNewShowVal();
            }
            dLbls.getShowVal().setVal(true);
        } else {
            if (!dLbls.isSetDelete()) {
                dLbls.addNewDelete();
            }
            dLbls.getDelete().setVal(true);
            if (dLbls.isSetShowVal()) {
                dLbls.getShowVal().setVal(false);
            }
        }
    }

    private CTDLbls getOrCreateDLbls(XDDFChartData.Series series) {
        if (series instanceof XDDFBarChartData.Series) {
            CTBarSer s = ((XDDFBarChartData.Series) series).getCTBarSer();
            return s.isSetDLbls() ? s.getDLbls() : s.addNewDLbls();
        } else if (series instanceof XDDFLineChartData.Series) {
            CTLineSer s = ((XDDFLineChartData.Series) series).getCTLineSer();
            return s.isSetDLbls() ? s.getDLbls() : s.addNewDLbls();
        } else if (series instanceof XDDFAreaChartData.Series) {
            CTAreaSer s = ((XDDFAreaChartData.Series) series).getCTAreaSer();
            return s.isSetDLbls() ? s.getDLbls() : s.addNewDLbls();
        } else if (series instanceof XDDFPieChartData.Series) {
            CTPieSer s = ((XDDFPieChartData.Series) series).getCTPieSer();
            return s.isSetDLbls() ? s.getDLbls() : s.addNewDLbls();
        } else if (series instanceof XDDFDoughnutChartData.Series) {
            CTPieSer s = ((XDDFDoughnutChartData.Series) series).getCTPieSer();
            return s.isSetDLbls() ? s.getDLbls() : s.addNewDLbls();
        } else if (series instanceof XDDFRadarChartData.Series) {
            CTRadarSer s = ((XDDFRadarChartData.Series) series).getCTRadarSer();
            return s.isSetDLbls() ? s.getDLbls() : s.addNewDLbls();
        } else if (series instanceof XDDFScatterChartData.Series) {
            CTScatterSer s = ((XDDFScatterChartData.Series) series).getCTScatterSer();
            return s.isSetDLbls() ? s.getDLbls() : s.addNewDLbls();
        } else if (series instanceof XDDFBubbleChartData.Series) {
            CTBubbleSer s = ((XDDFBubbleChartData.Series) series).getCTBubbleSer();
            return s.isSetDLbls() ? s.getDLbls() : s.addNewDLbls();
        }
        return null;
    }

    /**
     * Parses a hex color into its RGB bytes.
     *
     * @param color the color as {@code #RGB} or {@code #RRGGBB}
     * @return the RGB bytes, or {@code null} when the color is blank or invalid
     */
    protected byte[] parseHexRgb(String color) {
        if (StringUtils.isBlank(color)) return null;
        String hex = color.trim();
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }
        if (hex.length() == 3) {
            char r = hex.charAt(0);
            char g = hex.charAt(1);
            char b = hex.charAt(2);
            hex = "" + r + r + g + g + b + b;
        }
        if (hex.length() == 6) {
            try {
                int r = Integer.parseInt(hex.substring(0, 2), 16);
                int g = Integer.parseInt(hex.substring(2, 4), 16);
                int b = Integer.parseInt(hex.substring(4, 6), 16);
                return new byte[] { (byte) r, (byte) g, (byte) b };
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private boolean setCTTitle(CTTitle ctTitle, String title) {
        boolean isSet = false;
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
        return isSet;
    }

    /**
     * Converts string categories into their numeric values.
     *
     * @param categories the categories to convert
     * @return the numeric values
     */
    protected Double[] toNumberArray(String[] categories) {
        return Stream.of(categories).mapToDouble(Double::parseDouble).boxed().toArray(Double[]::new);
    }

}
