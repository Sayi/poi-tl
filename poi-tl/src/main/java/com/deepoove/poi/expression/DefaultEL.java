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
package com.deepoove.poi.expression;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Default EL implementation
 * <p>
 * It evaluates a dot expression such as {@code user.name} against a model that
 * can be a Map or a Java bean, and caches the resolved values.
 * </p>
 * 
 * @author Sayi
 *
 */
public class DefaultEL {

    final Object model;

    final Map<String, Object> cache = new ConcurrentHashMap<String, Object>(32);

    // Same variable reference with SpEL
    private static final String THIS = "#this";

    /**
     * Create a default EL with the model
     * 
     * @param model model data
     */
    public DefaultEL(Object model) {
        this.model = model;
    }

    /**
     * Create a default EL with the model
     * 
     * @param model model data
     * @return a new {@link DefaultEL} instance
     */
    public static DefaultEL create(Object model) {
        return new DefaultEL(model);
    }

    /**
     * Evaluate the expression against the model
     * 
     * @param el expression, {@code #this} refers to the model itself
     * @return the evaluated value, or null if it cannot be found
     */
    public Object eval(String el) {
        if (THIS.equals(el)) {
            return model;
        }
        Dot dot = new Dot(el);
        return dot.eval(this);
    }

}
