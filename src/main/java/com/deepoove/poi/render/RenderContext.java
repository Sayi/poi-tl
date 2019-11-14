/*
 * Copyright 2014-2019 the original author or authors.
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
package com.deepoove.poi.render;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * 模板标签上下文
 * 
 * @author Sayi
 */
public class RenderContext<T> {

    private final ElementTemplate eleTemplate;
    private final T data;
    private final XWPFTemplate template;

    public RenderContext(ElementTemplate eleTemplate, T data, XWPFTemplate template) {
        this.eleTemplate = eleTemplate;
        this.data = data;
        this.template = template;
    }

    public ElementTemplate getEleTemplate() {
        return eleTemplate;
    }

    public T getData() {
        return data;
    }

    public XWPFTemplate getTemplate() {
        return template;
    }

    public NiceXWPFDocument getXWPFDocument() {
        return this.template.getXWPFDocument();
    }

    public XWPFRun getRun() {
        return ((RunTemplate) eleTemplate).getRun();
    }

    public Configure getConfig() {
        return getTemplate().getConfig();
    }

    public Object getTagSource() {
        return getEleTemplate().getSource();
    }

}
