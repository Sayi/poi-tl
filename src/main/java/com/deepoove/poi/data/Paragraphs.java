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

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.Style;

/**
 * @author Sayi
 *
 */
public class Paragraphs implements RenderDataBuilder<ParagraphRenderData> {

    private List<RenderData> contents = new ArrayList<>();
    private ParagraphStyle paragraphStyle;

    private Paragraphs() {
    }

    public static Paragraphs of() {
        Paragraphs inst = new Paragraphs();
        return inst;
    }

    public static Paragraphs of(String text) {
        Paragraphs inst = Paragraphs.of();
        inst.addText(text);
        return inst;
    }
    
    public static Paragraphs of(TextRenderData text) {
        Paragraphs inst = Paragraphs.of();
        inst.addText(text);
        return inst;
    }

    public Paragraphs addText(TextRenderData text) {
        contents.add(text);
        return this;
    }

    public Paragraphs addText(String text) {
        contents.add(Texts.of(text).create());
        return this;
    }

    public Paragraphs addPicture(PictureRenderData picture) {
        contents.add(picture);
        return this;
    }

    public Paragraphs paraStyle(ParagraphStyle style) {
        this.paragraphStyle = style;
        return this;
    }

    public Paragraphs glyphStyle(Style style) {
        if (null == this.paragraphStyle) {
            this.paragraphStyle = ParagraphStyle.builder().withGlyphStyle(style).build();
        } else {
            this.paragraphStyle.setGlyphStyle(style);
        }
        return this;
    }

    public Paragraphs left() {
        if (null == this.paragraphStyle) {
            this.paragraphStyle = ParagraphStyle.builder().withAlign(ParagraphAlignment.LEFT).build();
        } else {
            this.paragraphStyle.setAlign(ParagraphAlignment.LEFT);
        }
        return this;
    }

    public Paragraphs center() {
        if (null == this.paragraphStyle) {
            this.paragraphStyle = ParagraphStyle.builder().withAlign(ParagraphAlignment.CENTER).build();
        } else {
            this.paragraphStyle.setAlign(ParagraphAlignment.CENTER);
        }
        return this;
    }

    @Override
    public ParagraphRenderData create() {
        ParagraphRenderData data = new ParagraphRenderData();
        data.setContents(contents);
        data.setParagraphStyle(paragraphStyle);
        return data;
    }

}
