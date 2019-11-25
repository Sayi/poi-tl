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
package com.deepoove.poi.policy.ref;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;

/**
 * 引用渲染策略，即通过引用的word对象操作文档
 * 
 * @author Sayi
 * @version 1.6.0
 */
public abstract class ReferenceRenderPolicy<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定位引用对象
     * 
     * @param template
     * @return
     */
    protected abstract T locate(XWPFTemplate template);

    /**
     * 操作引用对象
     * 
     * @param t
     *            引用对象
     * @param template
     *            模板
     */
    public abstract void doRender(T t, XWPFTemplate template) throws Exception;

    public void render(XWPFTemplate template) {
        try {
            T locate = locate(template);
            logger.info("Located the {} object: {}", ClassUtils.getSimpleName(locate.getClass()),
                    locate);
            doRender(locate, template);
        } catch (Exception e) {
            throw new RenderException("ReferenceRenderPolicy render error", e);
        }
    }
}
