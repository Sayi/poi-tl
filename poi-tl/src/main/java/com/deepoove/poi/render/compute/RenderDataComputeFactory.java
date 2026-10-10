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

/**
 * Factory of {@link RenderDataCompute} instances.
 * <p>
 * Registering a custom factory on the configuration is the way to plug another
 * expression language, for example OGNL or MVEL, into poi-tl.
 * </p>
 * 
 * @author Sayi
 * @version 1.7.0
 */
@FunctionalInterface
public interface RenderDataComputeFactory {

    /**
     * Creates an evaluator for the given environment model.
     *
     * @param model the data model together with its loop environment
     * @return the evaluator
     */
    RenderDataCompute newCompute(EnvModel model);

    /**
     * Creates an evaluator for a plain data model.
     *
     * @param model the root object of the data model
     * @return the evaluator
     */
    default RenderDataCompute newCompute(Object model) {
        return newCompute(model instanceof EnvModel ? (EnvModel)model : EnvModel.ofModel(model));
    }

}
