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
 * 
 * @author Sayi
 */
public abstract class AbstractRenderPolicy<T> implements RenderPolicy {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        // type safe
        T model = null;
        try {
            model = (T) data;
        } catch (ClassCastException e) {
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

    public abstract void doRender(RenderContext<T> context) throws Exception;

    protected boolean validate(T data) {
        return true;
    }

    protected void beforeRender(RenderContext<T> context) {
    }

    protected void afterRender(RenderContext<T> context) {
    }

    protected void reThrowException(RenderContext<T> context, Exception e) {
        throw new RenderException("Unable to render template " + context.getEleTemplate(), e);
    }

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
     * @param context
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
