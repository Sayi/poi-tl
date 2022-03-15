/*
 * Copyright 2014-2022 Sayi
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

import java.util.Iterator;

import com.deepoove.poi.data.*;
import com.deepoove.poi.data.Numberings.NumberingBuilder;

/**
 * Convert Object to NumberingRenderData
 * 
 * @author Sayi
 *
 */
public class ObjectToNumberingRenderDataConverter implements ToRenderDataConverter<Object, NumberingRenderData> {

    @Override
    public NumberingRenderData convert(Object source) throws Exception {
        if (null == source || source instanceof NumberingRenderData) return (NumberingRenderData) source;
        NumberingBuilder ofBullet = Numberings.ofBullet();
        if (source instanceof Iterable) {
            Iterator<?> iterator = ((Iterable<?>) source).iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                if (next instanceof TextRenderData) {
                    ofBullet.addItem((TextRenderData) next);
                } else if (next instanceof PictureRenderData) {
                    ofBullet.addItem((PictureRenderData) next);
                } else if (next instanceof ParagraphRenderData) {
                    ofBullet.addItem((ParagraphRenderData) next);
                } else {
                    ofBullet.addItem(next.toString());
                }
            }
        }
        return ofBullet.create();
    }

}
