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

import java.util.List;

import org.apache.poi.xddf.usermodel.chart.XDDFBubbleChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.BubbleSeriesRenderData;
import com.deepoove.poi.data.ChartBubbleRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.util.ChartUtils;

/**
 * Render policy of a bubble chart.
 * <p>
 * Every series of a bubble chart has three dimensions, X, Y and the bubble
 * size. The policy replaces those values in the chart and rebuilds the
 * corresponding columns of the embedded workbook; the template chart must
 * already be a bubble chart.
 * </p>
 * 
 * @author Sayi
 */
public class BubbleChartTemplateRenderPolicy
        extends AbstractChartTemplateRenderPolicy<ChartBubbleRenderData> {

    /**
     * {@inheritDoc}
     * <p>
     * The data is validated first; every series must provide X, Y and size
     * values of the same length.
     * </p>
     */
    @Override
    public void doRender(ChartTemplate eleTemplate, ChartBubbleRenderData data, XWPFTemplate template)
            throws Exception {
        validate(data);
        XWPFChart chart = eleTemplate.getChart();
        XDDFChartData chartData = ChartUtils.getChartSeries(chart).get(0);
        if (!(chartData instanceof XDDFBubbleChartData)) {
            throw new RenderException("Chart template is not a bubble chart!");
        }

        XDDFBubbleChartData bubbleChartData = (XDDFBubbleChartData) chartData;
        List<BubbleSeriesRenderData> seriesDatas = data.getSeriesDatas();
        int orignSize = bubbleChartData.getSeriesCount();
        int currentSeriesSize = seriesDatas.size();

        int col = 0;
        int maxPoints = 0;
        for (int i = 0; i < currentSeriesSize; i++) {
            BubbleSeriesRenderData series = seriesDatas.get(i);
            int points = series.getxValues().length;
            if (points > maxPoints) {
                maxPoints = points;
            }

            XDDFNumericalDataSource<Number> xData = createNumbericalDataSource(chart, series.getxValues(), col);
            XDDFNumericalDataSource<Number> yData = createNumbericalDataSource(chart, series.getyValues(), col + 1);
            XDDFNumericalDataSource<Number> sizeData = createNumbericalDataSource(chart, series.getBubbleSizes(), col + 2);

            XDDFBubbleChartData.Series currentSeries = null;
            if (i < orignSize) {
                currentSeries = (XDDFBubbleChartData.Series) bubbleChartData.getSeries(i);
                currentSeries.replaceData(xData, yData);
                currentSeries.setBubbleSizes(sizeData);
            } else {
                currentSeries = (XDDFBubbleChartData.Series) bubbleChartData.addSeries(xData, yData);
                currentSeries.setBubbleSizes(sizeData);
            }

            String name = series.getName();
            currentSeries.setTitle(name, chart.setSheetTitle(name, col + 1));

            SeriesRenderData dummy = new SeriesRenderData();
            dummy.setColor(series.getColor());
            dummy.setShowDataLabels(series.getShowDataLabels());
            applySeriesStyle(currentSeries, dummy);

            col += 3;
        }

        removeExtraSeries(bubbleChartData, orignSize, currentSeriesSize);

        XSSFSheet sheet = chart.getWorkbook().getSheetAt(0);
        updateBubbleCTTable(sheet, seriesDatas, maxPoints);
        removeExtraSheetCell(sheet, maxPoints, orignSize * 3, currentSeriesSize * 3);

        plot(chart, bubbleChartData);
        setTitle(chart, data.getChartTitle());
        setAxisTitle(chart, data.getxAxisTitle(), data.getyAxisTitle());
    }

    private void updateBubbleCTTable(XSSFSheet sheet, List<BubbleSeriesRenderData> seriesDatas, int maxPoints) {
        int totalCols = seriesDatas.size() * 3;
        CTTable ctTable = getSheetTable(sheet);
        String prefix = totalCols >= 26 ? String.valueOf((char) ('A' + ((totalCols / 26) - 1))) : "";
        char c = (char) ('A' + (totalCols % 26));
        String ref = "A1:" + prefix + c + (maxPoints + 1);
        ctTable.setRef(ref);

        CTTableColumns tableColumns = ctTable.getTableColumns();
        tableColumns.setCount(totalCols);
        int size = tableColumns.sizeOfTableColumnArray();
        for (int i = size - 1; i >= 0; i--) {
            tableColumns.removeTableColumn(i);
        }

        int colId = 1;
        for (BubbleSeriesRenderData series : seriesDatas) {
            CTTableColumn colX = tableColumns.addNewTableColumn();
            colX.setId(colId++);
            colX.setName(series.getName() + " X");

            CTTableColumn colY = tableColumns.addNewTableColumn();
            colY.setId(colId++);
            colY.setName(series.getName() + " Y");

            CTTableColumn colSize = tableColumns.addNewTableColumn();
            colSize.setId(colId++);
            colSize.setName(series.getName() + " Size");
        }
    }

    private void validate(ChartBubbleRenderData data) {
        if (null == data) {
            throw new RenderException("ChartBubbleRenderData must not be null!");
        }
        List<BubbleSeriesRenderData> seriesDatas = data.getSeriesDatas();
        if (null == seriesDatas || seriesDatas.isEmpty()) {
            throw new RenderException("SeriesDatas in bubble chart must not be null or empty!");
        }
        for (BubbleSeriesRenderData series : seriesDatas) {
            if (null == series.getxValues() || null == series.getyValues() || null == series.getBubbleSizes()) {
                throw new RenderException(String.format("X, Y, and BubbleSizes in series [%s] must not be null!", series.getName()));
            }
            int xLen = series.getxValues().length;
            int yLen = series.getyValues().length;
            int sizeLen = series.getBubbleSizes().length;
            if (xLen != yLen || xLen != sizeLen) {
                throw new RenderException(String.format(
                        "The length of X (%d), Y (%d), and BubbleSizes (%d) in series [%s] must be the same!",
                        xLen, yLen, sizeLen, series.getName()));
            }
        }
    }

}
