package com.deepoove.poi.data;

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.style.ParagraphStyle;

public class ParagraphRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private List<RenderData> contents = new ArrayList<>();
    private ParagraphStyle paragraphStyle;

    public ParagraphRenderData addText(TextRenderData text) {
        contents.add(text);
        return this;
    }

    public ParagraphRenderData addText(String text) {
        contents.add(Texts.of(text).create());
        return this;
    }

    public ParagraphRenderData addPicture(PictureRenderData picture) {
        contents.add(picture);
        return this;
    }

    public List<RenderData> getContents() {
        return contents;
    }

    public void setContents(List<RenderData> contents) {
        this.contents = contents;
    }

    public ParagraphStyle getParagraphStyle() {
        return paragraphStyle;
    }

    public void setParagraphStyle(ParagraphStyle style) {
        this.paragraphStyle = style;
    }

}
