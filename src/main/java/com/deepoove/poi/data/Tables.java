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

import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.TableV2Style;
import com.deepoove.poi.util.UnitUtils;

/**
 * @author Sayi
 *
 */
public class Tables implements RenderDataBuilder<TableRenderData> {

    TableRenderData data;

    private Tables() {
    }

    public static Tables of(RowV2RenderData... row) {
        // default A4
        Tables inst = Tables.ofA4Width();
        if (null != row) {
            Arrays.stream(row).forEach(inst::addRow);
        }
        return inst;
    }

    public static Tables of(String[][] strings) {
        Tables inst = Tables.ofA4Width();
        if (null != strings) {
            Arrays.stream(strings).map(string -> {
                Rows row = Rows.of();
                Arrays.stream(string).map(text -> Cells.of(text).create()).forEach(row::addCell);
                return row.create();
            }).forEach(inst::addRow);
        }
        return inst;
    }

    public static Tables ofWidth(double cm) {
        return ofWidth(cm, null);
    }

    public static Tables ofA4Width() {
        return ofWidth(14.63f);
    }

    public static Tables ofA4NarrowWidth() {
        return ofWidth(18.45f);
    }

    public static Tables ofA4MediumWidth() {
        return ofWidth(17.17f);
    }

    public static Tables ofA4ExtendWidth() {
        return ofWidth(10.83f);
    }

    public static Tables ofWidth(double cmWidth, double[] colCmWidths) {
        Tables inst = new Tables();
        inst.data = new TableRenderData();
        inst.width(cmWidth, colCmWidths);
        return inst;
    }

    public static Tables ofPercentWidth(String percent) {
        Tables inst = new Tables();
        inst.data = new TableRenderData();
        TableV2Style style = new TableV2Style();
        style.setWidth(percent);
        inst.data.setTableStyle(style);
        return inst;
    }

    public static Tables ofAutoWidth() {
        return ofPercentWidth("auto");
    }

    public Tables width(double cmWidth, double[] colCmWidths) {
        TableV2Style style = getTableStyle();
        style.setWidth(UnitUtils.cm2Twips(cmWidth) + "");
        if (null != colCmWidths) {
            int[] colWidths = Arrays.stream(colCmWidths).mapToInt(UnitUtils::cm2Twips).toArray();
            style.setColWidths(colWidths);
        }
        return this;
    }

    private TableV2Style getTableStyle() {
        TableV2Style style = data.getTableStyle();
        if (null == style) {
            style = new TableV2Style();
            data.setTableStyle(style);
        }
        return style;
    }

    public Tables center() {
        TableV2Style style = getTableStyle();
        style.setAlign(TableRowAlign.CENTER);
        return this;
    }

    public Tables border(BorderStyle border) {
        data.getTableStyle().setLeftBorder(border);
        data.getTableStyle().setRightBorder(border);
        data.getTableStyle().setTopBorder(border);
        data.getTableStyle().setBottomBorder(border);
        data.getTableStyle().setInsideHBorder(border);
        data.getTableStyle().setInsideVBorder(border);
        return this;
    }

    public Tables addRow(RowV2RenderData row) {
        data.addRow(row);
        return this;
    }

    @Override
    public TableRenderData create() {
        return data;
    }

}
