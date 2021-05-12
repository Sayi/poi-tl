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

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.style.ParagraphStyle;

/**
 * @author Sayi
 */
public class ParagraphRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private List<RenderData> contents = new ArrayList<>();
    private ParagraphStyle paragraphStyle;

    public ParagraphRenderData addText(TextRenderData text) {
        contents.add(text);
        return this;
    }

    public ParagraphRenderData addText(String text) {
        contents.add(Texts.of(text).create());
        return this;
    }

    public ParagraphRenderData addPicture(PictureRenderData picture) {
        contents.add(picture);
        return this;
    }

    public List<RenderData> getContents() {
        return contents;
    }

    public void setContents(List<RenderData> contents) {
        this.contents = contents;
    }

    public ParagraphStyle getParagraphStyle() {
        return paragraphStyle;
    }

    public void setParagraphStyle(ParagraphStyle style) {
        this.paragraphStyle = style;
    }

    @Override
    public String toString() {
        return "ParagraphRenderData [contents=" + contents + "]";
    }

}
