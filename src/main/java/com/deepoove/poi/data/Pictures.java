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

import com.deepoove.poi.util.BufferedImageUtils;
import com.deepoove.poi.util.ByteUtils;

/**
 * Builder to build {@link PictureRenderData} instances.
 * 
 * @author Sayi
 *
 */
public class Pictures implements RenderDataBuilder<PictureRenderData> {

    private PictureRenderData data;

    private Pictures() {
    }

    public static Pictures ofLocal(String src) {
        return Pictures.ofBytes(ByteUtils.getLocalByteArray(new File(src)), PictureType.suggestFileType(src));
    }

    public static Pictures ofUrl(String url, PictureType pictureType) {
        return ofBytes(ByteUtils.getUrlByteArray(url), pictureType);
    }

    public static Pictures ofStream(InputStream inputStream, PictureType pictureType) {
        return ofBytes(ByteUtils.toByteArray(inputStream), pictureType);
    }

    public static Pictures ofBufferedImage(BufferedImage image, PictureType pictureType) {
        return ofBytes(BufferedImageUtils.getBufferByteArray(image, pictureType.format()), pictureType);
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
