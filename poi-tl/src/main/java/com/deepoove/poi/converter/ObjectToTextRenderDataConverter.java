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
package com.deepoove.poi.converter;

import com.deepoove.poi.data.TextRenderData;

/**
 * Convert Object to TextRenderData
 * <p>
 * A {@link TextRenderData} is used as is, any other object is converted with
 * its {@code toString()} value.
 * </p>
 * 
 * @author Sayi
 *
 */
public class ObjectToTextRenderDataConverter implements ToRenderDataConverter<Object, TextRenderData> {

    /**
     * Convert an object to {@link TextRenderData}
     * 
     * @param source source object
     * @return text render data, or null if the source is null
     * @throws Exception if the conversion fails
     */
    @Override
    public TextRenderData convert(Object source) throws Exception {
        if (null == source) return null;
        TextRenderData text = source instanceof TextRenderData ? (TextRenderData) source
                : new TextRenderData(source.toString());
        return null == text.getText() ? new TextRenderData("") : text;
    }

}
