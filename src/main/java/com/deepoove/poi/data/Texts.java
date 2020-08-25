package com.deepoove.poi.data;

import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.StyleBuilder;

/**
 * @author Sayi
 *
 */
public class Texts implements RenderDataBuilder<TextRenderData> {

    private String text;
    private Style style;
    private String url;

    private Texts() {
    }

    public static Texts of(String text) {
        Texts inst = new Texts();
        inst.text = text;
        return inst;
    }

    public Texts style(Style style) {
        this.style = style;
        return this;
    }

    public Texts color(String color) {
        if (null != this.style) {
            style.setColor(color);
        } else {
            this.style = StyleBuilder.newBuilder().buildColor(color).build();
        }
        return this;
    }

    public Texts link(String url) {
        this.url = url;
        return this;
    }

    @Override
    public TextRenderData create() {
        TextRenderData data = null;
        if (null != url) {
            data = new HyperLinkTextRenderData(text, url);
            data.setStyle(style);
        } else {
            data = new TextRenderData(text, style);
        }
        return data;
    }

}
