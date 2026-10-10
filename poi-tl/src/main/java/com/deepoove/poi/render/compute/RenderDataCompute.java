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
 * Evaluates the expression written inside a template tag.
 * <p>
 * Implementations typically wrap an expression language such as the built-in
 * {@code DefaultEL} or Spring EL, and turn a tag name into the data bound to
 * it.
 * </p>
 * 
 * @author Sayi
 * @since 1.5.0
 */
@FunctionalInterface
public interface RenderDataCompute {

    /**
     * Evaluates one expression against the current data model.
     *
     * @param el the expression to evaluate
     * @return the value bound to the expression, or {@code null} when it cannot
     *         be resolved
     */
    Object compute(String el);

}
