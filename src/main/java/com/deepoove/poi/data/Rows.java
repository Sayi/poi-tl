package com.deepoove.poi.data;

import java.util.Arrays;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.util.UnitUtils;

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

    public Rows addCell(CellRenderData cell) {
        data.addCell(cell);
        return this;
    }

    public Rows textColor(String color) {
        Style style = getDefaultTextStyle();
        style.setColor(color);
        return this;
    }

    @Override
    public RowRenderData create() {
        return data;
    }

}
