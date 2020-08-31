package com.deepoove.poi.data;

import java.util.Arrays;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.util.UnitUtils;

public class Rows implements RenderDataBuilder<RowV2RenderData> {

    private RowV2RenderData data;

    private Rows() {
    }

    public static Rows of() {
        Rows inst = new Rows();
        inst.data = new RowV2RenderData();
        return inst;
    }

    public static Rows of(CellV2RenderData... cell) {
        Rows inst = Rows.of();
        if (null != cell) {
            Arrays.stream(cell).forEach(inst::addCell);
        }
        return inst;
    }

    public static Rows of(String... cellText) {
        Rows inst = Rows.of();
        if (null != cellText) {
            Arrays.stream(cellText).map(text -> {
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
        CellStyle defaultCellStyle = getDefaultCellStyle();
        defaultCellStyle.setDefaultParagraphAlign(ParagraphAlignment.CENTER);
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

    private RowStyle getRowStyle() {
        RowStyle rowStyle = data.getRowStyle();
        if (null == rowStyle) {
            rowStyle = new RowStyle();
            data.setRowStyle(rowStyle);
        }
        return rowStyle;
    }

    public Rows addCell(CellV2RenderData cell) {
        data.addCell(cell);
        return this;
    }

    @Override
    public RowV2RenderData create() {
        return data;
    }

}
