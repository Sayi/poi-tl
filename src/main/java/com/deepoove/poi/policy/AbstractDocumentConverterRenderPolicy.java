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
package com.deepoove.poi.policy;

import com.deepoove.poi.converter.ToRenderDataConverter;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.render.RenderContext;

/**
 * Use document renderer policy render type <T>
 * 
 * @author Sayi
 *
 * @param <T>
 */
public abstract class AbstractDocumentConverterRenderPolicy<T> extends AbstractRenderPolicy<T> {

    protected final ToRenderDataConverter<T, DocumentRenderData> documentConverter;

    public abstract ToRenderDataConverter<T, DocumentRenderData> getDocumentRenderDataConverter();

    public AbstractDocumentConverterRenderPolicy() {
        this.documentConverter = getDocumentRenderDataConverter();
    }

    @Override
    protected void afterRender(RenderContext<T> context) {
        super.clearPlaceholder(context, true);
    }

    @Override
    public void doRender(RenderContext<T> context) throws Exception {
        DocumentRenderPolicy.Helper.renderDocument(context.getRun(), documentConverter.convert(context.getData()));
    }

}
