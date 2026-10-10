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
package com.deepoove.poi.policy.reference;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;

/**
 * Base class of the render policies that operate on an existing document part.
 * <p>
 * Unlike the policies extending
 * {@link com.deepoove.poi.policy.AbstractRenderPolicy}, these policies render a
 * template that already exists in the document, such as a chart or a picture,
 * and replace its content instead of inserting new content at the tag position.
 * </p>
 *
 * @param <E> the element template this policy renders
 * @param <T> the type of the data bound to the template
 */
public abstract class AbstractTemplateRenderPolicy<E extends ElementTemplate, T> implements RenderPolicy {

    /**
     * {@inheritDoc}
     * <p>
     * The data is cast to the expected type and passed to
     * {@link #doRender(ElementTemplate, Object, XWPFTemplate)}; a null data
     * model leaves the template untouched, and any failure is wrapped into a
     * {@link RenderException}.
     * </p>
     */
    @SuppressWarnings("unchecked")
    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        if (null == data) return;
        // type safe
        T model = null;
        try {
            model = (T) data;
        } catch (ClassCastException e) {
            throw new RenderException("Error Render Data format for template: " + eleTemplate, e);
        }

        try {
            doRender((E) eleTemplate, model, template);
        } catch (Exception e) {
            if (e instanceof RenderException)
                throw (RenderException) e;
            else
                throw new RenderException("TemplateRenderPolicy render error", e);
        }

    }

    /**
     * Renders the given element template with the given data.
     *
     * @param eleTemplate the element template to render
     * @param data        the data bound to the template
     * @param template    the template instance being rendered
     * @throws Exception when rendering fails
     */
    public abstract void doRender(E eleTemplate, T data, XWPFTemplate template) throws Exception;

}
