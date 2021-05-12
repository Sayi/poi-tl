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
package com.deepoove.poi.xwpf;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;

public class XWPFPageSize {

    private int width;
    private int height;
    private XWPFPageOrientation orient;

    public XWPFPageSize(CTPageSz pgSz) {
        this.width = pgSz.getW().intValue();
        this.height = pgSz.getH().intValue();
        if (pgSz.isSetOrient()) {
            orient = XWPFPageOrientation.valueOf(pgSz.getOrient().intValue());
        } else {
            orient = XWPFPageOrientation.PORTRAIT;
        }
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

    public XWPFPageOrientation getOrient() {
        return orient;
    }

    public void setOrient(XWPFPageOrientation orient) {
        this.orient = orient;
    }

}
