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
        TableBuilder inst = new TableBuilder();
        TableStyle style = new TableStyle();
        style.setWidthScalePattern(WidthScalePattern.FIT);
        inst.data.setTableStyle(style);
        return inst;
    }

    public static TableBuilder ofWidth(double cmWidth, double[] colCmWidths) {
        TableBuilder inst = new TableBuilder();
        inst.width(cmWidth, colCmWidths);
        return inst;
    }

    public static TableBuilder ofPercentWidth(String percent) {
        TableBuilder inst = new TableBuilder();
        TableStyle style = new TableStyle();
        style.setWidth(percent);
        inst.data.setTableStyle(style);
        return inst;
    }

    public static TableBuilder ofAutoWidth() {
        return ofPercentWidth("auto");
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
        }

        public TableBuilder width(double cmWidth, double[] colCmWidths) {
            TableStyle style = getTableStyle();
            style.setWidth(UnitUtils.cm2Twips(cmWidth) + "");
            if (null != colCmWidths) {
                int[] colWidths = Arrays.stream(colCmWidths).mapToInt(UnitUtils::cm2Twips).toArray();
                style.setColWidths(colWidths);
            }
            style.setWidthScalePattern(WidthScalePattern.NONE);
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
