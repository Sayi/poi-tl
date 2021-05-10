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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.exception.ExpressionEvalException;

/**
 * dot expression
 * 
 * @author Sayi
 *
 */
public class Dot {
    private Logger logger = LoggerFactory.getLogger(Dot.class);

    private String el;
    private Dot target;
    private String key;

    final static Pattern EL_PATTERN = Pattern.compile("^[^\\.]+(\\.[^\\.]+)*$");

    public Dot(String el) {
        Objects.requireNonNull(el, "EL cannot be null.");
        if (!EL_PATTERN.matcher(el).matches()) {
            throw new ExpressionEvalException("Error EL fomart: " + el);
        }

        this.el = el;
        int dotIndex = el.lastIndexOf(".");
        if (-1 == dotIndex) {
            this.key = el;
        } else {
            this.key = el.substring(dotIndex + 1);
            this.target = new Dot(el.substring(0, dotIndex));
        }
    }

    public Object eval(DefaultEL elObject) {
        if (elObject.cache.containsKey(el)) return elObject.cache.get(el);
        Object result = null != target ? result = evalKey(target.eval(elObject)) : evalKey(elObject.model);
        if (null != result) elObject.cache.put(el, result);
        return result;
    }

    private Object evalKey(Object obj) {
        if (null == obj) {
            throw new ExpressionEvalException("Error eval " + key + ", the value of " + target + " is null");
        }
        final Class<?> objClass = obj.getClass();
        if (obj instanceof String || obj instanceof Number || obj instanceof java.util.Date || obj instanceof Collection
                || obj instanceof Boolean || objClass.isArray() || objClass.isPrimitive()) {
            throw new ExpressionEvalException(
                    "Error eval " + key + ", the type of " + target + "must be Hash, but is " + objClass);
        }

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).get(key);
        }

        // introspector
        Method readMethod = ReadMethodFinder.find(objClass, key);
        if (null != readMethod) {
            try {
                return readMethod.invoke(obj);
            } catch (Exception e) {
                logger.info("Introspector {} fail: {}", key, e.getMessage());
            }
        }

        // reflect
        Field field = FieldFinder.find(objClass, key);
        if (null == field) {
            throw new ExpressionEvalException("Cannot find property " + key + " from " + objClass);
        } else {
            try {
                return field.get(obj);
            } catch (Exception e) {
                throw new ExpressionEvalException("Error read the property:" + key + " from " + objClass);
            }
        }
    }

    public String getEl() {
        return el;
    }

    public Dot getTarget() {
        return target;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return null == el ? "[root el]" : el;
    }

}
