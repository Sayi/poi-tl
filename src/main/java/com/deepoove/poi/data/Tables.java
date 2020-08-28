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

    public static Tables ofWidth(double cm) {
        Tables inst = new Tables();
        inst.data = new TableRenderData();
        TableV2Style style = new TableV2Style();
        style.setWidth(UnitUtils.cm2Twips(cm) + "");
        inst.data.setTableStyle(style);
        return inst;
    }

    public static Tables ofWidth(double cmWidth, double[] colCmWidths) {
        Tables inst = ofWidth(cmWidth);
        int[] colWidths = Arrays.stream(colCmWidths).mapToInt(UnitUtils::cm2Twips).toArray();
        inst.data.getTableStyle().setColWidths(colWidths);
        return inst;
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
