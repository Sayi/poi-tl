/*
 * Copyright 2014-2024 Sayi
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

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Pictures;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert Object to PictureRenderData
 *
 * @author sayi
 *
 */
public class ObjectToPicturesRenderDataConverter    {

    public List<PictureRenderData> convert(Object source) throws Exception {
//        if (null == source || source instanceof List) return (List<PictureRenderData>) source;
        List<PictureRenderData> pictureRenderDataList = new ArrayList<>();
        
        if (source instanceof List) {
            for (Object o : ((List) source)) {
                pictureRenderDataList.add(Pictures.of((String) o).fitSize().create());
            }
        }
        return pictureRenderDataList;
    }

}
