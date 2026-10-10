/*
 * Copyright 2014-2026 Sayi
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
package com.deepoove.poi.policy;

import com.deepoove.poi.converter.ToRenderDataConverter;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.render.RenderContext;

/**
 * Base class of a render policy that renders a value of type {@code T} as a
 * paragraph.
 * <p>
 * The subclass supplies a {@link ToRenderDataConverter} that maps the bound value
 * to {@link ParagraphRenderData}, and the converted contents are written by
 * {@link ParagraphRenderPolicy.Helper}.
 * </p>
 * 
 * @author Sayi
 *
 * @param <T> the type of the data bound to the tag
 */
public abstract class AbstractParagraphConverterRenderPolicy<T> extends AbstractRenderPolicy<T> {

    protected final ToRenderDataConverter<T, ParagraphRenderData> paragraphConverter;

    /**
     * Returns the converter used to turn the bound value into paragraph render data.
     * 
     * @return the paragraph render data converter
     */
    public abstract ToRenderDataConverter<T, ParagraphRenderData> getParagraphRenderDataConverter();

    /**
     * Creates the policy and resolves its converter through
     * {@link #getParagraphRenderDataConverter()}.
     */
    public AbstractParagraphConverterRenderPolicy() {
        this.paragraphConverter = getParagraphRenderDataConverter();
    }

    /**
     * {@inheritDoc}
     * <p>
     * It clears the placeholder paragraph that held the tag.
     * </p>
     */
    @Override
    protected void afterRender(RenderContext<T> context) {
        super.clearPlaceholder(context, true);
    }

    /**
     * {@inheritDoc}
     * <p>
     * It converts the data and writes the resulting contents with
     * {@link ParagraphRenderPolicy.Helper}.
     * </p>
     */
    @Override
    public void doRender(RenderContext<T> context) throws Exception {
        ParagraphRenderPolicy.Helper.renderParagraph(context.getRun(), paragraphConverter.convert(context.getData()));
    }

}
