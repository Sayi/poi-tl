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
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.render.RenderContext;

/**
 * Base class of a render policy that renders a value of type {@code T} as a
 * document.
 * <p>
 * The subclass supplies a {@link ToRenderDataConverter} that maps the bound value
 * to {@link DocumentRenderData}, and the converted contents are inserted by
 * {@link DocumentRenderPolicy.Helper#renderDocument}.
 * </p>
 * 
 * @author Sayi
 *
 * @param <T> the type of the data bound to the tag
 */
public abstract class AbstractDocumentConverterRenderPolicy<T> extends AbstractRenderPolicy<T> {

    protected final ToRenderDataConverter<T, DocumentRenderData> documentConverter;

    /**
     * Returns the converter used to turn the bound value into document render data.
     * 
     * @return the document render data converter
     */
    public abstract ToRenderDataConverter<T, DocumentRenderData> getDocumentRenderDataConverter();

    /**
     * Creates the policy and resolves its converter through
     * {@link #getDocumentRenderDataConverter()}.
     */
    public AbstractDocumentConverterRenderPolicy() {
        this.documentConverter = getDocumentRenderDataConverter();
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
     * It converts the data and inserts the resulting contents with
     * {@link DocumentRenderPolicy.Helper#renderDocument}.
     * </p>
     */
    @Override
    public void doRender(RenderContext<T> context) throws Exception {
        DocumentRenderPolicy.Helper.renderDocument(context.getRun(), documentConverter.convert(context.getData()));
    }

}
