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
package com.deepoove.poi.data;

import java.util.Arrays;

import org.apache.poi.xwpf.usermodel.TableRowAlign;

import com.deepoove.poi.data.Rows.RowBuilder;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.util.UnitUtils;

/**
 * Factory method to create {@link TableRenderData}
 * 
 * @author Sayi
 */
public class Tables {
    private Tables() {
    }

    public static TableBuilder of(RowRenderData... row) {
        TableBuilder inst = ofPercentWidth("100%");
        if (null != row) {
            Arrays.stream(row).forEach(inst::addRow);
        }
        return inst;
    }

    public static TableBuilder of(String[][] strings) {
        TableBuilder inst = ofPercentWidth("100%");
        if (null != strings) {
            Arrays.stream(strings).map(string -> {
                RowBuilder row = Rows.of();
                Arrays.stream(string).map(text -> Cells.of(text).create()).forEach(row::addCell);
                return row.create();
            }).forEach(inst::addRow);
        }
        return inst;
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

    public static TableBuilder ofWidth(double cm) {
        return ofWidth(cm, null);
    }

    public static TableBuilder ofWidth(double cm, double[] colWidthsCm) {
        return new TableBuilder().width(cm, colWidthsCm);
    }

    public static TableBuilder ofPercentWidth(String percent) {
        return ofPercentWidth(percent, null);
    }

    public static TableBuilder ofPercentWidth(String percent, int[] colWidthsPercent) {
        return new TableBuilder().percentWidth(percent, colWidthsPercent);
    }

    public static TableBuilder ofAutoWidth() {
        return new TableBuilder().autoWidth();
    }

    public static TableRenderData create(RowRenderData... row) {
        return of(row).create();
    }

    /**
     * Builder to build {@link TableRenderData}
     */
    public static class TableBuilder implements RenderDataBuilder<TableRenderData> {

        private TableRenderData data;

        private TableBuilder() {
            data = new TableRenderData();
            border(BorderStyle.DEFAULT);
            cellMargin(0, 0.19f, 0, 0.19f).indentation(0);
        }

        public TableBuilder width(double widthCm, double[] colWidthsCm) {
            TableStyle style = getTableStyle();
            style.setWidth(UnitUtils.cm2Twips(widthCm) + "");
            if (null != colWidthsCm) {
                int[] colWidths = Arrays.stream(colWidthsCm).mapToInt(UnitUtils::cm2Twips).toArray();
                style.setColWidths(colWidths);
            }
            return this;
        }

        public TableBuilder percentWidth(String percent, int[] colWidthsPercent) {
            TableStyle style = getTableStyle();
            if (null != colWidthsPercent) {
                int sum = Arrays.stream(colWidthsPercent).sum();
                if (sum != 100) {
                    throw new IllegalArgumentException("The sum of the percentages must be 100");
                }
            }
            style.setWidth(percent);
            style.setColWidths(colWidthsPercent);
            return this;
        }

        public TableBuilder autoWidth() {
            TableStyle style = getTableStyle();
            style.setWidth("auto");
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

        public TableBuilder left() {
            TableStyle style = getTableStyle();
            style.setAlign(TableRowAlign.LEFT);
            return this;
        }

        public TableBuilder center() {
            TableStyle style = getTableStyle();
            style.setAlign(TableRowAlign.CENTER);
            return this;
        }

        public TableBuilder right() {
            TableStyle style = getTableStyle();
            style.setAlign(TableRowAlign.RIGHT);
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

        public TableBuilder indentation(double indent) {
            TableStyle tableStyle = getTableStyle();
            tableStyle.setIndentation(indent);
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
