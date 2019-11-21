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
package com.deepoove.poi.policy;

import org.apache.commons.lang3.ClassUtils;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.util.ParagraphUtils;

/**
 * 提供了数据校验、渲染、清空模板标签、异常处理的通用逻辑
 * 
 * @author Sayi
 * @version
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
            throw new RenderException(
                    "Error Render Data format for template: " + eleTemplate.getSource(), e);
        }

        // validate
        RenderContext<T> context = new RenderContext<T>(eleTemplate, model, template);
        if (!validate(model)) {
            postValidError(context);
            return;
        }

        // do render
        try {
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
    };

    protected void beforeRender(RenderContext<T> context) {}

    protected void afterRender(RenderContext<T> context) {}

    protected void reThrowException(RenderContext<T> context, Exception e) {
        throw new RenderException("Render template " + context.getEleTemplate() + " error", e);
    }

    protected void postValidError(RenderContext<T> context) {
        ValidErrorHandler errorHandler = context.getConfig().getValidErrorHandler();
        logger.debug("The data [{}] of the template {} is illegal, will apply error handler [{}]",
                context.getData(), context.getTagSource(),
                ClassUtils.getSimpleName(errorHandler.getClass()));
        errorHandler.handler(context);
    }

    public interface ValidErrorHandler {
        void handler(RenderContext<?> context);
    }

    public static class DiscardHandler implements ValidErrorHandler {

        @Override
        public void handler(RenderContext<?> context) {}

    }

    public static class ClearHandler implements ValidErrorHandler {

        @Override
        public void handler(RenderContext<?> context) {
            clearPlaceholder(context, false);
        }

    }

    public static class AbortHandler implements ValidErrorHandler {

        @Override
        public void handler(RenderContext<?> context) {
            throw new RenderException("Validate the data of the element " + context.getTagSource()
                    + " error, data may be illegal: " + context.getData());
        }

    }

    /**
     * 
     * 对于不在当前标签位置的操作，需要清除标签
     * 
     * @param context
     * @param clearParagraph
     * 
     */
    public static void clearPlaceholder(RenderContext<?> context, boolean clearParagraph) {
        XWPFRun run = context.getRun();
        IRunBody parent = run.getParent();
        String text = run.text();
        run.setText("", 0);
        if (clearParagraph && (parent instanceof XWPFParagraph)) {
            String paragraphText = ParagraphUtils.trimLine((XWPFParagraph) parent);
            // 段落就是当前标签则删除段落
            if (text.equals(paragraphText)) {
                XWPFDocument doc = context.getXWPFDocument();
                int pos = doc.getPosOfParagraph((XWPFParagraph) parent);
                // TODO p inside table for-each cell's p and remove
                doc.removeBodyElement(pos);
            } 
        }
    }

}
