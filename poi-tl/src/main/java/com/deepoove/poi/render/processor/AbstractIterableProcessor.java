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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.resolver.Resolver;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

/**
 * Shared logic of the processors that render iterable (block) templates.
 * <p>
 * The value bound to the block tag decides what happens: a falsy value removes
 * the block, an {@link Iterable} repeats it once per element, {@code true}
 * keeps it once within the enclosing scope, and any other value keeps it once
 * bound to that value.
 * </p>
 */
public abstract class AbstractIterableProcessor extends DefaultTemplateProcessor implements Iteration {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a processor for iterable templates.
     *
     * @param template          the template being rendered
     * @param resolver          the resolver used to re-parse copied content
     * @param renderDataCompute the expression evaluator of the current data model
     */
    public AbstractIterableProcessor(XWPFTemplate template, Resolver resolver, RenderDataCompute renderDataCompute) {
        super(template, resolver, renderDataCompute);
    }

    /**
     * Evaluates the block tag and renders the surrounded content accordingly.
     *
     * @param iterableTemplate the iterable template to render
     */
    @Override
    public void visit(IterableTemplate iterableTemplate) {
        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(iterableTemplate);
        Object compute = renderDataCompute.compute(iterableTemplate.getStartMark().getTagName());

        if (null == compute || (compute instanceof Boolean && !(Boolean) compute)) {
            handleNever(iterableTemplate, bodyContainer);
            afterHandle(iterableTemplate, bodyContainer, true);
        } else if (compute instanceof Iterable) {
            handleIterable(iterableTemplate, bodyContainer, (Iterable<?>) compute);
            afterHandle(iterableTemplate, bodyContainer, false);
        } else {
            if (compute instanceof Boolean && (Boolean) compute) {
                handleOnceWithScope(iterableTemplate, renderDataCompute);
            } else {
                handleOnce(iterableTemplate, compute);
            }
            afterHandle(iterableTemplate, bodyContainer, false);
        }
    }

    /**
     * Removes the block marks and, depending on {@code remove}, the content
     * between them.
     *
     * @param iterableTemplate the block that has been handled
     * @param bodyContainer    the container holding the block
     * @param remove           whether the content between the marks must be removed
     */
    protected void afterHandle(IterableTemplate iterableTemplate, BodyContainer bodyContainer, boolean remove) {
        bodyContainer.clearPlaceholder(iterableTemplate.getStartRun(), remove);
        bodyContainer.clearPlaceholder(iterableTemplate.getEndRun(), remove);
    }

    /**
     * Removes the whole block because its tag is bound to a falsy value.
     *
     * @param iterableTemplate the block to remove
     * @param bodyContainer    the container holding the block
     */
    protected abstract void handleNever(IterableTemplate iterableTemplate, BodyContainer bodyContainer);

    /**
     * Renders the block once per element of the bound collection.
     *
     * @param iterableTemplate the block to repeat
     * @param bodyContainer    the container holding the block
     * @param compute          the collection to iterate
     */
    protected abstract void handleIterable(IterableTemplate iterableTemplate, BodyContainer bodyContainer,
            Iterable<?> compute);

    /**
     * Renders the block once, bound to the given value.
     *
     * @param iterableTemplate the block to render
     * @param compute          the value bound to the block tag
     */
    protected void handleOnce(IterableTemplate iterableTemplate, Object compute) {
        process(iterableTemplate.getTemplates(), compute);
    }

    /**
     * Renders the block once without introducing a new data scope.
     * <p>
     * Used when the tag is bound to {@code true}, so the nested tags keep
     * resolving against the enclosing model.
     * </p>
     *
     * @param iterableTemplate the block to render
     * @param dataCompute      the evaluator of the enclosing scope
     */
    protected void handleOnceWithScope(IterableTemplate iterableTemplate, RenderDataCompute dataCompute) {
        new DocumentProcessor(this.template, this.resolver, dataCompute).process(iterableTemplate.getTemplates());
    }

    /**
     * Renders the given templates against the given model.
     *
     * @param templates the templates to render
     * @param model     the model to evaluate the templates against
     */
    protected void process(List<MetaTemplate> templates, Object model) {
        RenderDataCompute dataCompute = template.getConfig().getRenderDataComputeFactory().newCompute(model);
        new DocumentProcessor(this.template, this.resolver, dataCompute).process(templates);
    }

}
