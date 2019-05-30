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

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.template.ElementTemplate;

public class RenderContext {

    private ElementTemplate eleTemplate;
    private Object data;
    private XWPFTemplate template;

    public RenderContext(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        this.eleTemplate = eleTemplate;
        this.data = data;
        this.template = template;
    }

    public ElementTemplate getEleTemplate() {
        return eleTemplate;
    }

    public void setEleTemplate(ElementTemplate eleTemplate) {
        this.eleTemplate = eleTemplate;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public XWPFTemplate getTemplate() {
        return template;
    }

    public void setTemplate(XWPFTemplate template) {
        this.template = template;
    }

}
