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

/**
 * Skeleton {@link Visitor} that carries the state shared by all template
 * processors.
 * <p>
 * It holds the template being rendered, the resolver used to re-parse copied
 * content and the expression evaluator that provides the data model. The
 * {@code visit} methods default to {@link #visitOther(MetaTemplate)}, so
 * subclasses only need to override the template kinds they care about.
 * </p>
 */
public abstract class DefaultTemplateProcessor implements Visitor {

    protected XWPFTemplate template;
    protected final RenderDataCompute renderDataCompute;
    protected final Resolver resolver;

    /**
     * Creates a processor bound to the given render state.
     *
     * @param template          the template being rendered
     * @param resolver          the resolver used to re-parse copied content
     * @param renderDataCompute the expression evaluator of the current data model
     */
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

    /**
     * Handles any template kind that a subclass does not override.
     * <p>
     * The default implementation does nothing.
     * </p>
     *
     * @param template the template to handle
     */
    protected void visitOther(MetaTemplate template) {
        // no-op
    }

}
