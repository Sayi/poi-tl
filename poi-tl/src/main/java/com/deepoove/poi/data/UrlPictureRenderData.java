package com.deepoove.poi.data;

import com.deepoove.poi.util.ByteUtils;

public class UrlPictureRenderData extends PictureRenderData {

    private static final long serialVersionUID = 1L;

    private String url;

    public UrlPictureRenderData(String url) {
        this(url, null);
    }

    public UrlPictureRenderData(String url, PictureType pictureType) {
        this.url = url.replace("tp=webp", "tp=png");
        this.setPictureType(pictureType);
    }

    @Override
    public byte[] readPictureData() {
        return ByteUtils.getUrlByteArray(url);
    }

}
