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
package com.deepoove.poi.policy;

import org.apache.commons.lang3.ClassUtils;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure.ValidErrorHandler;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

/**
 * General logic for data verification, rendering, clearing template tags, and
 * exception handling
 * <p>
 * Subclasses implement {@link #doRender(RenderContext)} and may hook into the
 * lifecycle by overriding {@link #validate(Object)}, {@link #beforeRender} and
 * {@link #afterRender}. The public {@link #render} method drives these steps and
 * routes any failure to {@link #reThrowException}.
 * </p>
 * 
 * @param <T> the type of the data bound to the tag
 * @author Sayi
 */
public abstract class AbstractRenderPolicy<T> implements RenderPolicy {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Casts the raw tag value to the type handled by this policy.
     * 
     * @param source the value bound to the tag
     * @return the value cast to {@code T}
     * @throws Exception when the value can not be cast
     */
    @SuppressWarnings("unchecked")
    protected T cast(Object source) throws Exception {
        // type safe
        return (T) source;
    }

    /**
     * {@inheritDoc}
     * <p>
     * It casts the data, runs the validation, the before/do/after render lifecycle
     * and delegates failures to {@link #reThrowException}.
     * </p>
     */
    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        T model = null;
        try {
            model = cast(data);
        } catch (Exception e) {
            throw new RenderException("Error Render Data format for template: " + eleTemplate.getSource(), e);
        }

        RenderContext<T> context = new RenderContext<T>(eleTemplate, model, template);
        try {
            // validate
            if (!validate(model)) {
                postValidError(context);
                return;
            }

            // do render
            beforeRender(context);
            doRender(context);
            afterRender(context);
        } catch (Exception e) {
            reThrowException(context, e);
        }

    }

    /**
     * Renders the data into the document.
     * 
     * @param context the context of the tag being rendered
     * @throws Exception when the rendering fails
     */
    public abstract void doRender(RenderContext<T> context) throws Exception;

    /**
     * Validates the data before rendering.
     * 
     * @param data the data bound to the tag
     * @return {@code true} when the data can be rendered
     */
    protected boolean validate(T data) {
        return true;
    }

    /**
     * Hook invoked before {@link #doRender(RenderContext)}, for example to clear the
     * tag placeholder.
     * 
     * @param context the context of the tag being rendered
     */
    protected void beforeRender(RenderContext<T> context) {
    }

    /**
     * Hook invoked after {@link #doRender(RenderContext)} completed, for example to
     * clear the tag placeholder.
     * 
     * @param context the context of the tag being rendered
     */
    protected void afterRender(RenderContext<T> context) {
    }

    /**
     * Handles an exception thrown while rendering the tag.
     * <p>
     * The default implementation wraps the exception into a
     * {@link RenderException}. Subclasses may recover from the failure instead of
     * rethrowing.
     * </p>
     * 
     * @param context the context of the tag being rendered
     * @param e       the exception that occurred
     */
    protected void reThrowException(RenderContext<T> context, Exception e) {
        throw new RenderException("Unable to render template " + context.getEleTemplate(), e);
    }

    /**
     * Applies the configured error handler when the data is not valid.
     * 
     * @param context the context of the tag being rendered
     */
    protected void postValidError(RenderContext<T> context) {
        ValidErrorHandler errorHandler = context.getConfig().getValidErrorHandler();
        logger.info("The data [{}] of the template {} is illegal, will apply error handler [{}]", context.getData(),
                context.getTagSource(), ClassUtils.getSimpleName(errorHandler.getClass()));
        errorHandler.handler(context);
    }

    /**
     * For operations that are not in the current tag position, the tag needs to be
     * cleared
     * 
     * @param context        the context of the tag being rendered
     * @param clearParagraph if clear paragraph
     * 
     */
    protected void clearPlaceholder(RenderContext<?> context, boolean clearParagraph) {
        XWPFRun run = context.getRun();
        if (clearParagraph) {
            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
            bodyContainer.clearPlaceholder(run);
        } else {
            run.setText("", 0);
        }
    }

}
