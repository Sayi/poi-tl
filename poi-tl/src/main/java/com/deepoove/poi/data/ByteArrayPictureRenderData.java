package com.deepoove.poi.data;

public class ByteArrayPictureRenderData extends PictureRenderData {

    private static final long serialVersionUID = 1L;

    private byte[] bytes;

    public ByteArrayPictureRenderData(byte[] bytes) {
        this(bytes, null);
    }

    public ByteArrayPictureRenderData(byte[] bytes, PictureType pictureType) {
        this.bytes = bytes;
        this.setPictureType(pictureType);
    }

    @Override
    public byte[] readPictureData() {
        return bytes;
    }

}
