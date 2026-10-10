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
package com.deepoove.poi.render.compute;

import com.deepoove.poi.exception.ExpressionEvalException;
import com.deepoove.poi.expression.DefaultEL;

/**
 * Default expression evaluator backed by {@link DefaultEL}.
 * <p>
 * Loop variables are resolved against the environment before the root model,
 * unless the expression explicitly uses {@code #this}. In non-strict mode an
 * expression that cannot be evaluated resolves to {@code null} instead of
 * failing the rendering.
 * </p>
 * 
 * @author Sayi
 */
public class DefaultELRenderDataCompute implements RenderDataCompute {

    private DefaultEL elObject;
    private DefaultEL envObject;
    private boolean isStrict;

    /**
     * Creates an evaluator for the given environment model.
     *
     * @param model    the data model together with its loop environment
     * @param isStrict whether an unresolvable expression must throw instead of
     *                 resolving to {@code null}
     */
    public DefaultELRenderDataCompute(EnvModel model, boolean isStrict) {
        this.elObject = DefaultEL.create(model.getRoot());
        if (null != model.getEnv() && !model.getEnv().isEmpty()) {
            this.envObject = DefaultEL.create(model.getEnv());
        }
        this.isStrict = isStrict;
    }

    /**
     * {@inheritDoc}
     * <p>
     * The loop environment is tried first for expressions that do not contain
     * {@code #this}; when it yields no value, the root model is evaluated.
     * </p>
     */
    @Override
    public Object compute(String el) {
        try {
            if (null != envObject && !el.contains("#this")) {
                try {
                    Object val = envObject.eval(el);
                    if (null != val) {
                        return val;
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
            return elObject.eval(el);
        } catch (ExpressionEvalException e) {
            if (isStrict) throw e;
            // Cannot calculate the expression, the default returns null
            return null;
        }
    }

}
