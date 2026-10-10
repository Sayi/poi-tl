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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFAreaChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFScatterChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTCatAx;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTUnsignedInt;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx;
import org.openxmlformats.schemas.drawingml.x2006.chart.STAxPos;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData.ComboType;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.util.ChartUtils;
import com.deepoove.poi.util.ReflectionUtils;

/**
 * Render policy of a multi-series chart.
 * <p>
 * It replaces the series of the chart with the bound data, adds or removes
 * series as needed and keeps the workbook in sync. A combo chart, where one
 * series is drawn as bars, areas or lines, and an optional secondary value axis
 * are supported as well.
 * </p>
 * 
 * @author Sayi
 */
public class MultiSeriesChartTemplateRenderPolicy
        extends AbstractChartTemplateRenderPolicy<ChartMultiSeriesRenderData> {

    /**
     * {@inheritDoc}
     * <p>
     * The data is validated first; categories and every series must have the
     * same length, and a combo chart must declare the combo type of each
     * series.
     * </p>
     */
    @Override
    public void doRender(ChartTemplate eleTemplate, ChartMultiSeriesRenderData data, XWPFTemplate template)
            throws Exception {
        XWPFChart chart = eleTemplate.getChart();
        List<XDDFChartData> chartSeries = chart.getChartSeries();
        validate(chartSeries, data);
        ensureSecondaryAxis(chart, chartSeries, data);

        int totalSeriesCount = ensureSeriesCount(chart, chartSeries);
        int valueCol = 1;
        List<SeriesRenderData> usedSeriesDatas = new ArrayList<>();
        for (XDDFChartData chartData : chartSeries) {
            int orignSize = chartData.getSeriesCount();
            List<SeriesRenderData> currentSeriesData = null;
            if (chartSeries.size() <= 1) {
                // ignore combo type
                currentSeriesData = data.getSeriesDatas();
            } else {
                currentSeriesData = obtainSeriesData(chartData.getClass(), data.getSeriesDatas());
            }
            usedSeriesDatas.addAll(currentSeriesData);
            int currentSeriesSize = currentSeriesData.size();

            XDDFDataSource<?> categoriesData = null;
            if (chartData instanceof XDDFScatterChartData) {
                categoriesData = createNumbericalDataSource(chart, toNumberArray(data.getCategories()), 0);
            } else {
                categoriesData = createStringDataSource(chart, data.getCategories(), 0);
            }
            for (int i = 0; i < currentSeriesSize; i++) {
                XDDFNumericalDataSource<? extends Number> valuesData = createNumbericalDataSource(chart,
                        currentSeriesData.get(i).getValues(), valueCol);

                XDDFChartData.Series currentSeries = null;
                if (i < orignSize) {
                    currentSeries = chartData.getSeries(i);
                    valuesData.setFormatCode(currentSeries.getValuesData().getFormatCode());
                    currentSeries.replaceData(categoriesData, valuesData);
                } else {
                    // add series, should copy series with style
                    currentSeries = chartData.addSeries(categoriesData, valuesData);
                    processNewSeries(chartData, currentSeries);
                }
                String name = currentSeriesData.get(i).getName();
                currentSeries.setTitle(name, chart.setSheetTitle(name, valueCol));
                applySeriesStyle(currentSeries, currentSeriesData.get(i));
                valueCol++;
            }
            // clear extra series
            removeExtraSeries(chartData, orignSize, currentSeriesSize);
        }

        XSSFSheet sheet = chart.getWorkbook().getSheetAt(0);
        updateCTTable(sheet, usedSeriesDatas);

        removeExtraSheetCell(sheet, data.getCategories().length, totalSeriesCount, usedSeriesDatas.size());

        for (XDDFChartData chartData : chartSeries) {
            plot(chart, chartData);
        }
        setTitle(chart, data.getChartTitle());
        setAxisTitle(chart, data.getxAxisTitle(), data.getyAxisTitle(), data.getSecondaryYAxisTitle());
    }

    private void ensureSecondaryAxis(XWPFChart chart, List<XDDFChartData> chartSeries,
            ChartMultiSeriesRenderData data) {
        boolean hasSecondarySeries = data.getSeriesDatas().stream().anyMatch(SeriesRenderData::isSecondaryAxis);
        if (!hasSecondarySeries && null == data.getSecondaryYAxisTitle()) {
            return;
        }

        Map<Long, XDDFValueAxis> valueAxes = ChartUtils.getValueAxes(chart);
        boolean hasRightAxis = valueAxes.values().stream()
                .anyMatch(ax -> ax.getPosition() == AxisPosition.RIGHT);
        if (hasRightAxis) {
            return;
        }

        if (chartSeries.size() <= 1) {
            return;
        }

        for (XDDFChartData chartData : chartSeries) {
            List<SeriesRenderData> currentSeries = obtainSeriesData(chartData.getClass(), data.getSeriesDatas());
            boolean containsSecondary = currentSeries.stream().anyMatch(SeriesRenderData::isSecondaryAxis);
            if (containsSecondary) {
                attachSecondaryAxis(chart, chartData);
                break;
            }
        }
    }

    private void attachSecondaryAxis(XWPFChart chart, XDDFChartData chartData) {
        try {
            CTPlotArea plotArea = chart.getCTChart().getPlotArea();
            if (plotArea.sizeOfValAxArray() == 0) return;
            CTValAx primaryValAx = plotArea.getValAxArray(0);

            long newAxId = primaryValAx.getAxId().getVal() + 1000;
            while (isAxisIdExists(plotArea, newAxId)) {
                newAxId++;
            }

            CTValAx secondaryValAx = plotArea.addNewValAx();
            secondaryValAx.addNewAxId().setVal(newAxId);
            secondaryValAx.addNewScaling().addNewOrientation().setVal(primaryValAx.getScaling().getOrientation().getVal());
            secondaryValAx.addNewAxPos().setVal(STAxPos.R);
            if (null != primaryValAx.getCrossAx()) {
                secondaryValAx.addNewCrossAx().setVal(primaryValAx.getCrossAx().getVal());
            }
            if (primaryValAx.isSetCrosses()) {
                secondaryValAx.addNewCrosses().setVal(primaryValAx.getCrosses().getVal());
            }

            Object ctChart = ReflectionUtils.getValue("chart", chartData);
            if (ctChart instanceof XmlObject) {
                XmlCursor cursor = ((XmlObject) ctChart).newCursor();
                try {
                    int axIdCount = 0;
                    if (cursor.toFirstChild()) {
                        do {
                            if ("axId".equals(cursor.getName().getLocalPart())) {
                                axIdCount++;
                                if (axIdCount == 2) {
                                    cursor.setAttributeText(new QName("val"), String.valueOf(newAxId));
                                    break;
                                }
                            }
                        } while (cursor.toNextSibling());
                    }
                } finally {
                    cursor.dispose();
                }
            }

            XDDFValueAxis secondaryAxis = new XDDFValueAxis(secondaryValAx);
            chartData.getValueAxes().clear();
            chartData.getValueAxes().add(secondaryAxis);
        } catch (Exception e) {
            // fallback gracefully
        }
    }

    private boolean isAxisIdExists(CTPlotArea plotArea, long id) {
        for (CTValAx ax : plotArea.getValAxArray()) {
            if (ax.getAxId().getVal() == id) return true;
        }
        for (CTCatAx ax : plotArea.getCatAxArray()) {
            if (ax.getAxId().getVal() == id) return true;
        }
        return false;
    }

    protected void processNewSeries(XDDFChartData chartData, Series addSeries) {
    }

    private int ensureSeriesCount(XWPFChart chart, List<XDDFChartData> chartSeries) throws IllegalAccessException {
        // hack for poi 4.1.1+: repair seriesCount value,
        int totalSeriesCount = chartSeries.stream().mapToInt(XDDFChartData::getSeriesCount).sum();
        Field field = ReflectionUtils.findField(XDDFChart.class, "seriesCount");
        field.setAccessible(true);
        field.set(chart, totalSeriesCount);
        return totalSeriesCount;
    }

    private void validate(List<XDDFChartData> chartSeries, ChartMultiSeriesRenderData data) {
        if (null == data) {
            throw new RenderException("ChartMultiSeriesRenderData must not be null!");
        }
        if (null == data.getCategories()) {
            throw new RenderException("Categories in chart must not be null!");
        }
        if (null == data.getSeriesDatas()) {
            throw new RenderException("SeriesDatas in chart must not be null!");
        }
        int categoryLength = data.getCategories().length;
        for (SeriesRenderData series : data.getSeriesDatas()) {
            if (null == series.getValues()) {
                throw new RenderException(String.format("Values in series [%s] must not be null!", series.getName()));
            }
            if (series.getValues().length != categoryLength) {
                throw new RenderException(String.format(
                        "The length of categories (%d) and series [%s] values (%d) in chart must be the same!",
                        categoryLength, series.getName(), series.getValues().length));
            }
        }
        // validate combo
        if (chartSeries.size() >= 2) {
            long nullCount = data.getSeriesDatas().stream().filter(d -> null == d.getComboType()).count();
            if (nullCount > 0) throw new RenderException("Combo chart must set comboType field of series!");
        }
    }

    private List<SeriesRenderData> obtainSeriesData(Class<? extends XDDFChartData> clazz,
            List<SeriesRenderData> seriesDatas) {
        Predicate<SeriesRenderData> predicate = data -> {
            return false;
        };
        if (clazz.equals(XDDFBarChartData.class)) {
            predicate = data -> ComboType.BAR == data.getComboType();
        } else if (clazz.equals(XDDFAreaChartData.class)) {
            predicate = data -> ComboType.AREA == data.getComboType();
        } else if (clazz.equals(XDDFLineChartData.class)) {
            predicate = data -> ComboType.LINE == data.getComboType();
        }
        return seriesDatas.stream().filter(predicate).collect(Collectors.toList());
    }

}
