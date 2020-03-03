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
package com.deepoove.poi.policy.ref;

import java.lang.reflect.ParameterizedType;

import org.apache.commons.lang3.ClassUtils;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * 下标引用渲染策略
 * 
 * @author Sayi
 * @version 1.6.0
 */
public abstract class IndexRefRenderPolicy<T> extends ReferenceRenderPolicy<T>
        implements DocumentIndex {

    protected Class<T> genericType;

    @SuppressWarnings("unchecked")
    public IndexRefRenderPolicy() {
        genericType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T locate(XWPFTemplate template) {
        int positon = index();
        logger.info("Try locate the {} object at position of {}...",
                ClassUtils.getSimpleName(genericType), positon);
        Object t = null;
        NiceXWPFDocument doc = template.getXWPFDocument();
        if (XWPFChart.class.equals(genericType)) {
            t = doc.getCharts().get(positon);
        } else if (XWPFTable.class.equals(genericType)) {
            t = doc.getTables().get(positon);
        } else if (XWPFPictureData.class.equals(genericType)) {
            t = doc.getAllPictures().get(positon);
        } else if (XWPFPicture.class.equals(genericType)) {
            t = doc.getAllEmbeddedPictures().get(positon);
        } else if (XWPFParagraph.class.equals(genericType)) {
            t = doc.getParagraphs().get(positon);
        }
        return (T) t;
    }
}
