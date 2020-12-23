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

package com.deepoove.poi.render.processor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.EnvModel;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.resolver.Resolver;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import com.deepoove.poi.xwpf.ParentContext;

public abstract class AbstractIterableProcessor extends DefaultTemplateProcessor implements Iteration {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public AbstractIterableProcessor(XWPFTemplate template, Resolver resolver, RenderDataCompute renderDataCompute) {
        super(template, resolver, renderDataCompute);
    }

    @Override
    public void visit(IterableTemplate iterableTemplate) {
        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(iterableTemplate);
        Object compute = renderDataCompute.compute(iterableTemplate.getStartMark().getTagName());

        if (null == compute || (compute instanceof Boolean && !(Boolean) compute)) {
            handleNever(iterableTemplate, bodyContainer);
        } else if (compute instanceof Iterable) {
            handleIterable(iterableTemplate, bodyContainer, (Iterable<?>) compute);
        } else {
            if (compute instanceof Boolean && (Boolean) compute) {
                handleOnceWithScope(iterableTemplate, renderDataCompute);
            } else {
                handleOnce(iterableTemplate, compute);
            }
        }

        afterHandle(iterableTemplate, bodyContainer);
    }

    protected void afterHandle(IterableTemplate iterableTemplate, BodyContainer bodyContainer) {
        bodyContainer.clearPlaceholder(iterableTemplate.getStartRun());
        bodyContainer.clearPlaceholder(iterableTemplate.getEndRun());
    }

    protected abstract void handleNever(IterableTemplate iterableTemplate, BodyContainer bodyContainer);

    protected abstract void handleIterable(IterableTemplate iterableTemplate, BodyContainer bodyContainer,
            Iterable<?> compute);
    
    protected void foreach(IterableTemplate iterableTemplate, ParentContext parentContext, IterableContext context,
            Iterator<?> iterator) {
        Map<String, Object> env = new HashMap<>();
        int index = 0;
        boolean hasNext = iterator.hasNext();
        while (hasNext) {
            Object root = iterator.next();
            hasNext = iterator.hasNext();
            env.put("_is_first", index == 0);
            env.put("_is_last", !hasNext);
            env.put("_has_next", hasNext);
            env.put("_is_even_item", index % 2 == 1);
            env.put("_is_odd_item", index % 2 == 0);
            env.put("_index", index++);
            next(iterableTemplate, parentContext, context, EnvModel.of(root, env));
        }
    }

    protected void handleOnce(IterableTemplate iterableTemplate, Object compute) {
        process(iterableTemplate.getTemplates(), compute);
    }

    protected void handleOnceWithScope(IterableTemplate iterableTemplate, RenderDataCompute dataCompute) {
        new DocumentProcessor(this.template, this.resolver, dataCompute).process(iterableTemplate.getTemplates());
    }

    protected void process(List<MetaTemplate> templates, Object model) {
        RenderDataCompute dataCompute = template.getConfig().getRenderDataComputeFactory().newCompute(model);
        new DocumentProcessor(this.template, this.resolver, dataCompute).process(templates);
    }

}
