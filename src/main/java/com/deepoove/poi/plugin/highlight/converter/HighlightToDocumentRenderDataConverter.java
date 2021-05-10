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
package com.deepoove.poi.plugin.highlight.converter;

import com.codewaves.codehighlight.core.Highlighter;
import com.codewaves.codehighlight.core.Highlighter.HighlightResult;
import com.codewaves.codehighlight.core.StyleRenderer;
import com.codewaves.codehighlight.core.StyleRendererFactory;
import com.deepoove.poi.converter.ParagraphToDocumentRenderDataConverter;
import com.deepoove.poi.converter.ToRenderDataConverter;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.plugin.highlight.HighlightRenderData;

/**
 * Convert HighlightRenderData to DocumentRenderData
 * 
 * @author Sayi
 *
 */
public class HighlightToDocumentRenderDataConverter
        implements ToRenderDataConverter<HighlightRenderData, DocumentRenderData> {

    @Override
    public DocumentRenderData convert(HighlightRenderData data) throws Exception {
        Highlighter<ParagraphRenderData> highlighter = new Highlighter<>(
                new StyleRendererFactory<ParagraphRenderData>() {
                    @Override
                    public StyleRenderer<ParagraphRenderData> create(String languageName) {
                        return new ParagraphRenderer(data.getStyle());
                    }
                });
        HighlightResult<ParagraphRenderData> result = null;
        if (null == data.getLanguage()) {
            result = highlighter.highlightAuto(data.getCode(), null);
        } else {
            result = highlighter.highlight(data.getLanguage(), data.getCode());
        }
        ParagraphToDocumentRenderDataConverter converter = new ParagraphToDocumentRenderDataConverter(
                null == data.getStyle() ? false : data.getStyle().isShowLine());
        return converter.convert(result.getResult());
    }

}
