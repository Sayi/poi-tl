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

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;

/**
 * Factory method to create {@link CellRenderData}
 * 
 * @author Sayi
 *
 */
public class Cells {

    private Cells() {
    }

    public static CellBuilder of() {
        CellBuilder inst = new CellBuilder();
        return inst;
    }

    public static CellBuilder of(String text) {
        CellBuilder inst = of();
        inst.addParagraph(Paragraphs.of(text).create());
        return inst;
    }

    public static CellBuilder of(PictureRenderData picture) {
        CellBuilder inst = of();
        inst.addParagraph(picture);
        return inst;
    }

    public static CellBuilder of(TextRenderData text) {
        CellBuilder inst = of();
        inst.addParagraph(text);
        return inst;
    }

    public static CellRenderData create(String text) {
        return of(text).create();
    }

    /**
     * Builder to build {@link CellRenderData}
     *
     */
    public static class CellBuilder implements RenderDataBuilder<CellRenderData> {

        private CellRenderData data;

        private CellBuilder() {
            data = new CellRenderData();
        }

        public CellBuilder addParagraph(ParagraphRenderData para) {
            data.addParagraph(para);
            return this;
        }

        public CellBuilder addParagraph(TextRenderData text) {
            data.addParagraph(Paragraphs.of().addText(text).create());
            return this;
        }

        public CellBuilder addParagraph(String text) {
            data.addParagraph(Paragraphs.of().addText(text).create());
            return this;
        }

        public CellBuilder addParagraph(PictureRenderData picture) {
            data.addParagraph(Paragraphs.of().addPicture(picture).create());
            return this;
        }

        public CellBuilder bgColor(String color) {
            CellStyle defaultCellStyle = getCellStyle();
            defaultCellStyle.setBackgroundColor(color);
            return this;
        }

        public CellBuilder center() {
            verticalCenter();
            horizontalCenter();
            return this;
        }

        public CellBuilder verticalCenter() {
            CellStyle defaultCellStyle = getCellStyle();
            defaultCellStyle.setVertAlign(XWPFVertAlign.CENTER);
            return this;
        }

        public CellBuilder horizontalCenter() {
            ParagraphStyle defaultParagraphStyle = getDefaultParagraphStyle();
            defaultParagraphStyle.setAlign(ParagraphAlignment.CENTER);
            return this;
        }

        public CellBuilder horizontalLeft() {
            ParagraphStyle defaultParagraphStyle = getDefaultParagraphStyle();
            defaultParagraphStyle.setAlign(ParagraphAlignment.LEFT);
            return this;
        }

        public CellBuilder horizontalRight() {
            ParagraphStyle defaultParagraphStyle = getDefaultParagraphStyle();
            defaultParagraphStyle.setAlign(ParagraphAlignment.RIGHT);
            return this;
        }

        private CellStyle getCellStyle() {
            CellStyle cellStyle = data.getCellStyle();
            if (null == cellStyle) {
                cellStyle = new CellStyle();
                data.setCellStyle(cellStyle);
            }
            return cellStyle;
        }

        private ParagraphStyle getDefaultParagraphStyle() {
            CellStyle cellStyle = getCellStyle();
            ParagraphStyle defaultParagraphStyle = cellStyle.getDefaultParagraphStyle();
            if (null == defaultParagraphStyle) {
                defaultParagraphStyle = ParagraphStyle.builder().build();
                cellStyle.setDefaultParagraphStyle(defaultParagraphStyle);
            }
            return defaultParagraphStyle;
        }

        @Override
        public CellRenderData create() {
            return data;
        }
    }

}
