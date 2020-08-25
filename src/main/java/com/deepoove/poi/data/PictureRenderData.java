/*
 * Copyright 2014-2020 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import com.deepoove.poi.util.BytePictureUtils;
import com.deepoove.poi.util.ByteUtils;

/**
 * 图片渲染数据
 * 
 * @author Sayi
 * @version 0.0.3
 *
 */
public class PictureRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private int width;
    private int height;

    /**
     * 二进制数据
     */
    private byte[] image;

    /**
     * 当图片不存在时，显示的文字
     */
    private String altMeta = "";

    /**
     * 类型
     */
    private PictureType pictureType;

    PictureRenderData() {
    }

    /**
     * 根据本地路径构建图片数据源
     * 
     * @param width  宽度
     * @param height 高度
     * @param path   本地图片路径
     */
    public PictureRenderData(int width, int height, String path) {
        this(width, height, new File(path));
    }

    /**
     * 根据File文件构建图片数据源
     * 
     * @param width
     * @param height
     * @param picture
     */
    public PictureRenderData(int width, int height, File picture) {
        this(width, height, PictureType.suggestFileType(picture.getPath()), ByteUtils.getLocalByteArray(picture));
    }

    /**
     * 根据流构建图片数据源
     * 
     * @param width
     * @param height
     * @param pictureType
     * @param inputStream
     */
    public PictureRenderData(int width, int height, PictureType pictureType, InputStream inputStream) {
        this(width, height, pictureType, ByteUtils.toByteArray(inputStream));
    }

    /**
     * 根据BufferedImage构建图片数据源
     * 
     * @param width
     * @param height
     * @param pictureType
     * @param image
     */
    public PictureRenderData(int width, int height, PictureType pictureType, BufferedImage image) {
        this(width, height, pictureType, BytePictureUtils.getBufferByteArray(image, pictureType.format()));
    }

    /**
     * create picture by picture type and byte[]
     * 
     * @param width
     * @param height
     * @param pictureType
     * @param data        {@link BytePictureUtils}
     */
    public PictureRenderData(int width, int height, PictureType pictureType, byte[] data) {
        this.width = width;
        this.height = height;
        this.pictureType = pictureType;
        this.image = data;
    }

    @Deprecated
    public PictureRenderData(int width, int height, String format, InputStream input) {
        this(width, height, format, ByteUtils.toByteArray(input));
    }

    @Deprecated
    public PictureRenderData(int width, int height, String format, BufferedImage image) {
        this(width, height, format, BytePictureUtils.getBufferByteArray(image, format));
    }

    /**
     * 根据字节数组构建图片数据源
     * 
     * @param width  宽度
     * @param height 高度
     * @param format 标识图片后缀，如.png、.jpg等
     * @param data   图片byte[]数据，可以通过工具类{@link BytePictureUtils}生成
     */
    @Deprecated
    public PictureRenderData(int width, int height, String format, byte[] data) {
        this.width = width;
        this.height = height;
        this.pictureType = PictureType.suggestFileType(format);
        this.image = data;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getAltMeta() {
        return altMeta;
    }

    public void setAltMeta(String altMeta) {
        this.altMeta = altMeta;
    }

    public PictureType getPictureType() {
        return pictureType;
    }

    public void setPictureType(PictureType pictureType) {
        this.pictureType = pictureType;
    }

}
