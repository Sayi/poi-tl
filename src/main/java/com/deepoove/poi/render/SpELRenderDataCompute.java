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

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 基于Spring Expression Language的计算
 * 
 * @author Sayi
 * @since 1.5.0
 */
public class SpELRenderDataCompute implements RenderDataCompute {

    ExpressionParser parser;
    EvaluationContext context;

    public SpELRenderDataCompute(Object root) {
        parser = new SpelExpressionParser();
        context = new StandardEvaluationContext(root);
    }

    @Override
    public Object compute(String el) {
        // mark: 无法计算或者读取表达式，会直接抛异常
        return parser.parseExpression(el).getValue(context);
    }

}
