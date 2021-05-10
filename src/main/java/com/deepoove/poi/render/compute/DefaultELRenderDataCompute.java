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
package com.deepoove.poi.render.compute;

import com.deepoove.poi.exception.ExpressionEvalException;
import com.deepoove.poi.expression.DefaultEL;

/**
 * default expression compute
 * 
 * @author Sayi
 */
public class DefaultELRenderDataCompute implements RenderDataCompute {

    private DefaultEL elObject;
    private DefaultEL envObject;
    private boolean isStrict;

    public DefaultELRenderDataCompute(EnvModel model, boolean isStrict) {
        this.elObject = DefaultEL.create(model.getRoot());
        if (null != model.getEnv() && !model.getEnv().isEmpty()) {
            this.envObject = DefaultEL.create(model.getEnv());
        }
        this.isStrict = isStrict;
    }

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
