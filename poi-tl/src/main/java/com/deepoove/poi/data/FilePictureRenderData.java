package com.deepoove.poi.data;

import java.io.File;

import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.util.ByteUtils;

public class FilePictureRenderData extends PictureRenderData {

    private static final long serialVersionUID = 1L;

    private String path;

    public FilePictureRenderData(String path) {
        this(path, null);
    }

    public FilePictureRenderData(String path, PictureType pictureType) {
        this.path = path;
        this.setPictureType(pictureType);
    }

    public FilePictureRenderData(int w, int h, String path) {
        this(path);
        this.pictureStyle = new PictureStyle();
        this.pictureStyle.setWidth(w);
        this.pictureStyle.setHeight(h);
    }

    @Override
    public byte[] readPictureData() {
        return ByteUtils.getLocalByteArray(new File(path));
    }

}
