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
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find field based on field name or annotation @Name
 * 
 * @author Sayi
 *
 */
class FieldFinder {
    private static Logger logger = LoggerFactory.getLogger(FieldFinder.class);

    private static LinkedHashMap<Class<?>, Field[]> cache = new LinkedHashMap<Class<?>, Field[]>(32, 0.75f, true) {

        private static final long serialVersionUID = -4306886008010847817L;

        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<java.lang.Class<?>, Field[]> eldest) {
            // TODO The maximum number can be adjusted. If it is a business originating from
            // a large number of entities, this value should be increased to optimize
            // performance
            return size() > 20;
        };
    };

    static Field find(Class<?> objClass, String key) {
        Class<?> clazz = objClass;
        Field field = null;
        while (clazz != Object.class) {
            try {
                field = findInClazz(clazz, key);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // do nothing, go to super class
            } catch (Exception e) {
                logger.warn("Error read the property:" + key + " from " + objClass);
            }
            clazz = clazz.getSuperclass();
        }
        return null;

    }

    static Field findInClazz(Class<?> clazz, String key) throws NoSuchFieldException {
        Field field = null;
        try {
            field = clazz.getDeclaredField(key);
            return field;
        } catch (Exception e) {
        }

        Field[] fields = cache.get(clazz);
        if (null == fields) {
            fields = clazz.getDeclaredFields();
            cache.put(clazz, fields);
        }
        for (Field f : fields) {
            Name annotation = f.getAnnotation(Name.class);
            if (null != annotation && key.equals(annotation.value())) {
                return f;
            }
        }
        throw new NoSuchFieldException(key);
    }

}
