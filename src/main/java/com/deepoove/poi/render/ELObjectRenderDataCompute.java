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
package com.deepoove.poi.render;

import com.deepoove.poi.el.ELObject;
import com.deepoove.poi.exception.ExpressionEvalException;

/**
 * 基于ELObject的计算
 * 
 * @author Sayi
 * @version 1.5.0
 */
public class ELObjectRenderDataCompute implements RenderDataCompute {

    private ELObject elObject;
    private boolean isStrict;

    public ELObjectRenderDataCompute(Object root, boolean isStrict) {
        elObject = ELObject.create(root);
        this.isStrict = isStrict;
    }

    @Override
    public Object compute(String el) {
        try {
            return elObject.eval(el);
        } catch (ExpressionEvalException e) {
            if (isStrict)
                throw e;
            // mark：无法计算或者读取表达式，默认返回null
            return null;
        }
    }

}
