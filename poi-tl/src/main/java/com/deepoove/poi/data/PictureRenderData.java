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

import com.deepoove.poi.data.style.PictureStyle;

/**
 * Picture structure
 * 
 * @author Sayi
 */
public abstract class PictureRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    protected PictureType pictureType;
    protected PictureStyle pictureStyle;
    /**
     * When the picture does not exist, the altMeta displayed
     */
    protected String altMeta = "";

    public abstract byte[] readPictureData();

    public PictureStyle getPictureStyle() {
        return pictureStyle;
    }

    public void setPictureStyle(PictureStyle pictureStyle) {
        this.pictureStyle = pictureStyle;
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
