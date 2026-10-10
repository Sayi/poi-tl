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

package com.deepoove.poi.template;

import com.deepoove.poi.render.processor.Visitor;

/**
 * A meta template resolved from a word template.
 * <p>
 * Meta templates are the intermediate representation produced by a
 * {@link com.deepoove.poi.resolver.Resolver}: every tag found in the document
 * becomes one meta template, which is later rendered by a
 * {@link com.deepoove.poi.policy.RenderPolicy}.
 * </p>
 */
public interface MetaTemplate {

    /**
     * Returns the variable name of this meta template, for example {@code title}
     * for the tag {@code {{title}}}.
     *
     * @return the variable name
     */
    String variable();

    /**
     * Accepts a visitor, dispatching to the overloaded {@code visit} method that
     * matches the concrete meta template type.
     *
     * @param visitor the visitor to accept
     */
    void accept(Visitor visitor);

}
