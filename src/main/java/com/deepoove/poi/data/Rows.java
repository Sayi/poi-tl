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

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.util.UnitUtils;

/**
 * @author Sayi
 *
 */
public class Rows implements RenderDataBuilder<RowRenderData> {

    private RowRenderData data;

    private Rows() {
    }

    public static Rows of() {
        Rows inst = new Rows();
        inst.data = new RowRenderData();
        return inst;
    }

    public static Rows of(CellRenderData... cell) {
        Rows inst = Rows.of();
        if (null != cell) {
            Arrays.stream(cell).forEach(inst::addCell);
        }
        return inst;
    }

    public static Rows of(String... cell) {
        Rows inst = Rows.of();
        if (null != cell) {
            Arrays.stream(cell).map(text -> {
                return Cells.of(text).create();
            }).forEach(inst::addCell);
        }
        return inst;
    }

    public static Rows of(TextRenderData... cell) {
        Rows inst = Rows.of();
        if (null != cell) {
            Arrays.stream(cell).map(text -> {
                return Cells.of(text).create();
            }).forEach(inst::addCell);
        }
        return inst;
    }

    public Rows bgColor(String color) {
        CellStyle defaultCellStyle = getDefaultCellStyle();
        defaultCellStyle.setBackgroundColor(color);
        return this;
    }

    public Rows center() {
        verticalCenter();
        horizontalCenter();
        return this;
    }

    public Rows verticalCenter() {
        CellStyle defaultCellStyle = getDefaultCellStyle();
        defaultCellStyle.setVertAlign(XWPFVertAlign.CENTER);
        return this;
    }

    public Rows horizontalCenter() {
        ParagraphStyle defaultParaStyle = getDefaultParagraphStyle();
        defaultParaStyle.setAlign(ParagraphAlignment.CENTER);
        return this;
    }

    public Rows rowHeight(double cm) {
        RowStyle rowStyle = getRowStyle();
        rowStyle.setHeight(UnitUtils.cm2Twips(cm));
        return this;
    }

    public Rows addCell(CellRenderData cell) {
        data.addCell(cell);
        return this;
    }

    public Rows textColor(String color) {
        Style style = getDefaultTextStyle();
        style.setColor(color);
        return this;
    }

    public Rows textBold() {
        Style style = getDefaultTextStyle();
        style.setBold(true);
        return this;
    }

    public Rows textFontSize(int fontSize) {
        Style style = getDefaultTextStyle();
        style.setFontSize(fontSize);
        return this;
    }

    private CellStyle getDefaultCellStyle() {
        RowStyle rowStyle = getRowStyle();
        CellStyle defaultCellStyle = rowStyle.getDefaultCellStyle();
        if (null == defaultCellStyle) {
            defaultCellStyle = new CellStyle();
            rowStyle.setDefaultCellStyle(defaultCellStyle);
        }
        return defaultCellStyle;
    }

    private Style getDefaultTextStyle() {
        ParagraphStyle defaultParagraphStyle = getDefaultParagraphStyle();
        Style defaultTextStyle = defaultParagraphStyle.getDefaultTextStyle();
        if (null == defaultTextStyle) {
            defaultTextStyle = Style.builder().build();
            defaultParagraphStyle.setDefaultTextStyle(defaultTextStyle);
        }
        return defaultTextStyle;
    }

    private ParagraphStyle getDefaultParagraphStyle() {
        CellStyle cellStyle = getDefaultCellStyle();
        ParagraphStyle defaultParagraphStyle = cellStyle.getDefaultParagraphStyle();
        if (null == defaultParagraphStyle) {
            defaultParagraphStyle = ParagraphStyle.builder().build();
            cellStyle.setDefaultParagraphStyle(defaultParagraphStyle);
        }
        return defaultParagraphStyle;
    }

    private RowStyle getRowStyle() {
        RowStyle rowStyle = data.getRowStyle();
        if (null == rowStyle) {
            rowStyle = new RowStyle();
            data.setRowStyle(rowStyle);
        }
        return rowStyle;
    }

    @Override
    public RowRenderData create() {
        return data;
    }

}
