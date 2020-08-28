package com.deepoove.poi.data;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

import com.deepoove.poi.data.style.CellStyle;

public class Cells implements RenderDataBuilder<CellV2RenderData> {

    private CellV2RenderData data;

    private Cells() {
    }

    public static Cells of() {
        Cells inst = new Cells();
        inst.data = new CellV2RenderData();
        return inst;
    }

    public static Cells of(String text) {
        Cells inst = Cells.of();
        inst.data.addParagraph(Paragraphs.of(text).create());
        return inst;
    }

    public static Cells of(PictureRenderData picture) {
        Cells inst = Cells.of();
        inst.addParagraph(picture);
        return inst;
    }

    public static Cells of(TextRenderData text) {
        Cells inst = Cells.of();
        inst.addParagraph(text);
        return inst;
    }

    public Cells addParagraph(ParagraphRenderData para) {
        data.addParagraph(para);
        return this;
    }

    public Cells addParagraph(TextRenderData text) {
        data.addParagraph(Paragraphs.of().addText(text).create());
        return this;
    }

    public Cells addParagraph(String text) {
        data.addParagraph(Paragraphs.of().addText(text).create());
        return this;
    }

    public Cells addParagraph(PictureRenderData picture) {
        data.addParagraph(Paragraphs.of().addPicture(picture).create());
        return this;
    }

    public Cells bgColor(String color) {
        CellStyle defaultCellStyle = getCellStyle();
        defaultCellStyle.setBackgroundColor(color);
        return this;
    }

    private CellStyle getCellStyle() {
        CellStyle cellStyle = data.getCellStyle();
        if (null == cellStyle) {
            cellStyle = new CellStyle();
            data.setCellStyle(cellStyle);
        }
        return cellStyle;
    }

    public Cells center() {
        verticalCenter();
        horizontalCenter();
        return this;
    }

    public Cells verticalCenter() {
        CellStyle defaultCellStyle = getCellStyle();
        defaultCellStyle.setVertAlign(XWPFVertAlign.CENTER);
        return this;
    }

    public Cells horizontalCenter() {
        CellStyle defaultCellStyle = getCellStyle();
        defaultCellStyle.setDefaultParagraphAlign(ParagraphAlignment.CENTER);
        return this;
    }

    @Override
    public CellV2RenderData create() {
        return data;
    }

}
