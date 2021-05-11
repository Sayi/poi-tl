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
package com.deepoove.poi.data.style;

import java.io.Serializable;

import com.deepoove.poi.xwpf.WidthScalePattern;

/**
 * @author Sayi
 *
 */
public class PictureStyle implements Serializable {

    private static final long serialVersionUID = 1L;

    private int width;
    private int height;
    /**
     * FIT：scale fit(Original size exceeds page width) or keep original size
     */
    private WidthScalePattern scalePattern = WidthScalePattern.NONE;

    private PictureAlign align;

    public static enum PictureAlign {
        LEFT,
        CENTER,
        RIGHT;
    }

    public PictureAlign getAlign() {
        return align;
    }

    public void setAlign(PictureAlign align) {
        this.align = align;
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

    public WidthScalePattern getScalePattern() {
        return scalePattern;
    }

    public void setScalePattern(WidthScalePattern scalePattern) {
        this.scalePattern = scalePattern;
    }

}
