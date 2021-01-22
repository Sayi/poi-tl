/*
 * Copyright 2014-2020 Sayi
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
package com.deepoove.poi.data;

import java.util.Arrays;

import org.apache.poi.xwpf.usermodel.TableRowAlign;

import com.deepoove.poi.data.Rows.RowBuilder;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.util.UnitUtils;
import com.deepoove.poi.xwpf.WidthScalePattern;

/**
 * Factory method to create {@link TableRenderData}
 * 
 * @author Sayi
 *
 */
public class Tables {
    private Tables() {
    }

    public static TableBuilder of(RowRenderData... row) {
        TableBuilder inst = ofFitWidth();
        if (null != row) {
            Arrays.stream(row).forEach(inst::addRow);
        }
        return inst;
    }

    public static TableBuilder of(String[][] strings) {
        TableBuilder inst = ofFitWidth();
        if (null != strings) {
            Arrays.stream(strings).map(string -> {
                RowBuilder row = Rows.of();
                Arrays.stream(string).map(text -> Cells.of(text).create()).forEach(row::addCell);
                return row.create();
            }).forEach(inst::addRow);
        }
        return inst;
    }

    public static TableBuilder ofWidth(double cm) {
        return ofWidth(cm, null);
    }

    public static TableBuilder ofA4Width() {
        return ofWidth(14.63f);
    }

    public static TableBuilder ofA4NarrowWidth() {
        return ofWidth(18.45f);
    }

    public static TableBuilder ofA4MediumWidth() {
        return ofWidth(17.17f);
    }

    public static TableBuilder ofA4ExtendWidth() {
        return ofWidth(10.83f);
    }

    public static TableBuilder ofFitWidth() {
        return ofFitWidth(null);
    }

    public static TableBuilder ofFitWidth(int[] colWidthsPercent) {
        return new TableBuilder().fitWidth(colWidthsPercent);
    }

    public static TableBuilder ofWidth(double widthCm, double[] colWidthsCm) {
        return new TableBuilder().width(widthCm, colWidthsCm);
    }

    public static TableBuilder ofPercentWidth(String percent) {
        return new TableBuilder().percentOrAutoWidth(percent);
    }

    public static TableBuilder ofAutoWidth() {
        return new TableBuilder().percentOrAutoWidth("auto");
    }

    public static TableRenderData create(RowRenderData... row) {
        return of(row).create();
    }

    /**
     * Builder to build {@link TableRenderData}
     *
     */
    public static class TableBuilder implements RenderDataBuilder<TableRenderData> {

        private TableRenderData data;

        private TableBuilder() {
            data = new TableRenderData();
            border(BorderStyle.DEFAULT);
            cellMargin(0, 0.19f, 0, 0.19f);
        }

        public TableBuilder width(double widthCm, double[] colWidthsCm) {
            TableStyle style = getTableStyle();
            style.setWidth(UnitUtils.cm2Twips(widthCm) + "");
            if (null != colWidthsCm) {
                int[] colWidths = Arrays.stream(colWidthsCm).mapToInt(UnitUtils::cm2Twips).toArray();
                style.setColWidths(colWidths);
            }
            style.setWidthScalePattern(WidthScalePattern.NONE);
            return this;
        }

        public TableBuilder fitWidth(int[] colWidthsPercent) {
            TableStyle style = getTableStyle();
            if (null != colWidthsPercent) {
                int sum = Arrays.stream(colWidthsPercent).sum();
                if (sum != 100) {
                    throw new IllegalArgumentException("The sum of the percentages must be 100");
                }
            }
            style.setWidthScalePattern(WidthScalePattern.FIT);
            style.setColWidths(colWidthsPercent);
            return this;
        }

        public TableBuilder percentOrAutoWidth(String percentOrAutoWidth) {
            TableStyle style = getTableStyle();
            style.setWidthScalePattern(WidthScalePattern.NONE);
            style.setWidth(percentOrAutoWidth);
            return this;
        }

        private TableStyle getTableStyle() {
            TableStyle style = data.getTableStyle();
            if (null == style) {
                style = new TableStyle();
                data.setTableStyle(style);
            }
            return style;
        }

        public TableBuilder center() {
            TableStyle style = getTableStyle();
            style.setAlign(TableRowAlign.CENTER);
            return this;
        }

        public TableBuilder border(BorderStyle border) {
            getTableStyle().setLeftBorder(border);
            getTableStyle().setRightBorder(border);
            getTableStyle().setTopBorder(border);
            getTableStyle().setBottomBorder(border);
            getTableStyle().setInsideHBorder(border);
            getTableStyle().setInsideVBorder(border);
            return this;
        }

        public TableBuilder cellMargin(double topCm, double leftCm, double bottomCm, double rightCm) {
            TableStyle tableStyle = getTableStyle();
            tableStyle.setTopCellMargin(UnitUtils.cm2Twips(topCm));
            tableStyle.setLeftCellMargin(UnitUtils.cm2Twips(leftCm));
            tableStyle.setBottomCellMargin(UnitUtils.cm2Twips(bottomCm));
            tableStyle.setRightCellMargin(UnitUtils.cm2Twips(rightCm));
            return this;
        }

        public TableBuilder addRow(RowRenderData row) {
            data.addRow(row);
            return this;
        }

        public TableBuilder mergeRule(MergeCellRule rule) {
            data.setMergeRule(rule);
            return this;
        }

        @Override
        public TableRenderData create() {
            return data;
        }
    }

}
