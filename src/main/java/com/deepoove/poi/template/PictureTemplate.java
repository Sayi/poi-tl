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
package com.deepoove.poi.template;

import org.apache.poi.xwpf.usermodel.XWPFPicture;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.processor.Visitor;

/**
 * Picture docx template element: XWPFPicture
 * 
 * @author Sayi
 * @version 1.8.0
 */
public class PictureTemplate extends ElementTemplate {

    protected XWPFPicture picture;

    public PictureTemplate() {}

    public PictureTemplate(String tagName, XWPFPicture picture) {
        this.tagName = tagName;
        this.picture = picture;
    }

    public XWPFPicture getPicture() {
        return picture;
    }

    public void setPicture(XWPFPicture picture) {
        this.picture = picture;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public RenderPolicy findPolicy(Configure config) {
        RenderPolicy renderPolicy = config.getCustomPolicy(tagName);
        return null == renderPolicy ? config.getTemplatePolicy(this.getClass()) : renderPolicy;
    }

    @Override
    public String toString() {
        return "Pic::" + source;
    }

}
