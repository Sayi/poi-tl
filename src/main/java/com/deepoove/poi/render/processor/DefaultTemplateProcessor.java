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
package com.deepoove.poi.render.processor;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.resolver.Resolver;
import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.PictImageTemplate;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.template.run.RunTemplate;

public abstract class DefaultTemplateProcessor implements Visitor {

    protected XWPFTemplate template;
    protected final RenderDataCompute renderDataCompute;
    protected final Resolver resolver;

    public DefaultTemplateProcessor(XWPFTemplate template, final Resolver resolver,
            final RenderDataCompute renderDataCompute) {
        this.template = template;
        this.resolver = resolver;
        this.renderDataCompute = renderDataCompute;
    }

    @Override
    public void visit(InlineIterableTemplate iterableTemplate) {
        visitOther(iterableTemplate);
    }

    @Override
    public void visit(RunTemplate runTemplate) {
        visitOther(runTemplate);
    }

    @Override
    public void visit(PictureTemplate pictureTemplate) {
        visitOther(pictureTemplate);
    }

    @Override
    public void visit(PictImageTemplate pictImageTemplate) {
        visitOther(pictImageTemplate);
    }

    @Override
    public void visit(ChartTemplate chartTemplate) {
        visitOther(chartTemplate);
    }

    @Override
    public void visit(IterableTemplate iterableTemplate) {
        visitOther(iterableTemplate);
    }

    protected void visitOther(MetaTemplate template) {
        // no-op
    }

}
