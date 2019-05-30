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

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.config.Name;

/**
 * 寻找合适的属性，支持@Name注解
 * 
 * @author Sayi
 *
 */
class FieldFinder {
    private static Logger logger = LoggerFactory.getLogger(FieldFinder.class);

    private static LinkedHashMap<Class<?>, Field[]> cache = new LinkedHashMap<Class<?>, Field[]>(32,
            0.75f, true) {

        private static final long serialVersionUID = -4306886008010847817L;

        protected boolean removeEldestEntry(
                java.util.Map.Entry<java.lang.Class<?>, Field[]> eldest) {
            // TO DO 最大数可以被调整，如果是一个导出大量实体的业务，这个值应该增加来优化性能
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
                logger.error("Error read the property:" + key + " from " + objClass);
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
        } catch (Exception e) {}

        Field[] fields = cache.get(clazz);
        if (null == fields) {
            fields = clazz.getDeclaredFields();
            cache.put(clazz, fields);
        }
        for (Field f : fields) {
            Name annotation = f.getAnnotation(Name.class);
            if (null != annotation && key.equals(annotation.value())) return f;
        }
        throw new NoSuchFieldException(key);
    }

}
