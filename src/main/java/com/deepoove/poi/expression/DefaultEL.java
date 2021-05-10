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
package com.deepoove.poi.expression;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * default el expression
 * 
 * @author Sayi
 *
 */
public class DefaultEL {

    final Object model;

    final Map<String, Object> cache = new ConcurrentHashMap<String, Object>(32);

    // Same variable reference with SpEL
    private static final String THIS = "#this";

    public DefaultEL(Object model) {
        this.model = model;
    }

    public static DefaultEL create(Object model) {
        return new DefaultEL(model);
    }

    public Object eval(String el) {
        if (THIS.equals(el)) {
            return model;
        }
        Dot dot = new Dot(el);
        return dot.eval(this);
    }

}
