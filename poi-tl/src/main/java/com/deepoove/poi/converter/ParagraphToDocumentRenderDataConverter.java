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
package com.deepoove.poi.converter;

import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Documents.DocumentBuilder;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Paragraphs.ParagraphBuilder;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;

/**
 * Convert ParagraphRenderData to DocumentRenderData
 * 
 * @author Sayi
 *
 */
public class ParagraphToDocumentRenderDataConverter
        implements ToRenderDataConverter<ParagraphRenderData, DocumentRenderData> {

    protected final boolean SHOW_LINE;
    protected final int FIRST_LINE;

    public ParagraphToDocumentRenderDataConverter() {
        this(false);
    }

    public ParagraphToDocumentRenderDataConverter(boolean showLine) {
        SHOW_LINE = showLine;
        FIRST_LINE = 1;
    }

    @Override
    public DocumentRenderData convert(ParagraphRenderData para) throws Exception {
        int line = FIRST_LINE;
        DocumentBuilder of = Documents.of();
        ParagraphBuilder paragraphBuilder = null;
        for (RenderData data : para.getContents()) {
            if (null == paragraphBuilder) {
                paragraphBuilder = createParagraphBuilder(para, line++);
            }
            if (data instanceof TextRenderData) {
                String text = ((TextRenderData) data).getText();
                Style style = ((TextRenderData) data).getStyle();
                int cursor = 0;
                int position = text.indexOf("\n", cursor);
                while (position != -1) {
                    paragraphBuilder.addText(Texts.of(text.substring(cursor, position)).style(style).create());
                    of.addParagraph(paragraphBuilder.create());

                    paragraphBuilder = createParagraphBuilder(para, line++);
                    cursor = position + 1;
                    position = text.indexOf("\n", cursor);
                }
                paragraphBuilder.addText(Texts.of(text.substring(cursor)).style(style).create());
            } else if (data instanceof PictureRenderData) {
                paragraphBuilder.addPicture((PictureRenderData) data);
            }
        }
        ParagraphRenderData lastPara = paragraphBuilder.create();
        if (lastPara.getContents().size() > 0) {
            of.addParagraph(lastPara);
        }
        return of.create();
    }

    private ParagraphBuilder createParagraphBuilder(ParagraphRenderData para, int line) {
        ParagraphBuilder of = Paragraphs.of().paraStyle(para.getParagraphStyle());
        if (SHOW_LINE) {
            of.addText(Texts.of((line <= 9 ? "  " : " ") + line + " ").create());
        }
        return of;
    }

}
