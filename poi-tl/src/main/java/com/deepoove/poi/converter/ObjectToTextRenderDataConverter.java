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

import com.deepoove.poi.data.TextRenderData;

/**
 * Convert Object to TextRenderData
 * 
 * @author Sayi
 *
 */
public class ObjectToTextRenderDataConverter implements ToRenderDataConverter<Object, TextRenderData> {

    @Override
    public TextRenderData convert(Object source) throws Exception {
        if (null == source) return null;
        TextRenderData text = source instanceof TextRenderData ? (TextRenderData) source
                : new TextRenderData(source.toString());
        return null == text.getText() ? new TextRenderData("") : text;
    }

}
