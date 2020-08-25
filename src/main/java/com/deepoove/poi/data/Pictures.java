package com.deepoove.poi.data;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import com.deepoove.poi.util.BytePictureUtils;

public class Pictures implements RenderDataBuilder<PictureRenderData> {

    PictureRenderData data;

    public static Pictures ofLocal(String src) {
        Pictures inst = new Pictures();
        inst.data = new PictureRenderData(0, 0, src);
        return inst;
    }

    public static Pictures ofUrl(String url, PictureType pictureType) {
        Pictures inst = new Pictures();
        inst.data = new PictureRenderData(0, 0, pictureType, BytePictureUtils.getUrlByteArray(url));
        return inst;
    }

    public static Pictures ofStream(InputStream inputStream, PictureType pictureType) {
        Pictures inst = new Pictures();
        inst.data = new PictureRenderData(0, 0, pictureType, inputStream);
        return inst;
    }

    public static Pictures ofBufferedImage(BufferedImage image, PictureType pictureType) {
        Pictures inst = new Pictures();
        inst.data = new PictureRenderData(0, 0, pictureType, image);
        return inst;
    }

    public static Pictures ofBytes(byte[] bytes, PictureType pictureType) {
        Pictures inst = new Pictures();
        inst.data = new PictureRenderData(0, 0, pictureType, bytes);
        return inst;
    }

    public Pictures size(int width, int height) {
        data.setWidth(width);
        data.setHeight(height);
        return this;
    }

    public Pictures altMeta(String altMeta) {
        data.setAltMeta(altMeta);
        return this;
    }

    @Override
    public PictureRenderData create() {
        return data;
    }

}
