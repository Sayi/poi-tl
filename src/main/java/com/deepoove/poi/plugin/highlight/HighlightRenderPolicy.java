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
package com.deepoove.poi.plugin.highlight;

import com.deepoove.poi.converter.ToRenderDataConverter;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.plugin.highlight.converter.HighlightToDocumentRenderDataConverter;
import com.deepoove.poi.policy.AbstractDocumentConverterRenderPolicy;

/**
 * highlight render policy
 * 
 * @author Sayi
 *
 */
public class HighlightRenderPolicy extends AbstractDocumentConverterRenderPolicy<HighlightRenderData> {

    @Override
    public ToRenderDataConverter<HighlightRenderData, DocumentRenderData> getDocumentRenderDataConverter() {
        return new HighlightToDocumentRenderDataConverter();
    }

}
