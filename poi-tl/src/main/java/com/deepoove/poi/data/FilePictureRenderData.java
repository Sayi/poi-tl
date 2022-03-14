/*
 * Copyright 2014-2021 Sayi
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
