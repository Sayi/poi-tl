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
package com.deepoove.poi.el;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象点缀法访问
 * 
 * @author Sayi
 * @since 1.4.0
 *
 */
public class ELObject {

    final Object model;

    final Map<String, Object> cache = new ConcurrentHashMap<String, Object>(32);

    public ELObject(Object model) {
        this.model = model;
    }

    public static ELObject create(Object model) {
        return new ELObject(model);
    }

    public Object eval(String el) {
        Dot dot = new Dot(el);
        return dot.eval(this);
    }

}
