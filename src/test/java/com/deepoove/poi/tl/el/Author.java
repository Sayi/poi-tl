package com.deepoove.poi.tl.el;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TextRenderData;

public class Author {

    private String name;
    private TextRenderData alias;
    private PictureRenderData avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextRenderData getAlias() {
        return alias;
    }

    public void setAlias(TextRenderData alias) {
        this.alias = alias;
    }

    public PictureRenderData getAvatar() {
        return avatar;
    }

    public void setAvatar(PictureRenderData avatar) {
        this.avatar = avatar;
    }

}
