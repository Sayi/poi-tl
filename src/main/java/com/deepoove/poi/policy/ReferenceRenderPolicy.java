/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.policy;

import java.lang.reflect.ParameterizedType;

import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;

/**
 * 引用渲染策略：引用Word文档中某个对象进行渲染，对象是通过文档中的位置index()来引用。
 * 
 * @author Sayi
 * @version 1.6.0
 * @param <T>
 *            对象类型
 */
public abstract class ReferenceRenderPolicy<T> {

    private Class<T> genericType;

    @SuppressWarnings("unchecked")
    public ReferenceRenderPolicy() {
        genericType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    /**
     * 对象的位置
     * 
     * @return
     */
    public abstract int index();

    /**
     * 引用对象渲染
     * 
     * @param t
     * @param template
     */
    public abstract void doRender(T t, XWPFTemplate template);

    // 可以覆盖这个方法改写获取引用的方式
    @SuppressWarnings("unchecked")
    protected T get(XWPFTemplate template) {
        int positon = index();
        Object t = null;
        NiceXWPFDocument doc = template.getXWPFDocument();
        if (XWPFChart.class.equals(genericType)) {
            t = doc.getCharts().get(positon);
        } else if (XWPFTable.class.equals(genericType)) {
            t = doc.getTables().get(positon);
        } else if (XWPFPicture.class.equals(genericType)) {
            t = doc.getAllPictures().get(positon);
        } else if (XWPFParagraph.class.equals(genericType)) {
            t = doc.getParagraphs().get(positon);
        }
        return (T) t;
    }

    public void render(XWPFTemplate template) {
        doRender(get(template), template);
    }

}
