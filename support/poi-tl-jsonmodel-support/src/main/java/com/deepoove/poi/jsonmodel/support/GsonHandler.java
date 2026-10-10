/*
 * Copyright 2014-2022 Sayi
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
package com.deepoove.poi.jsonmodel.support;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Gson based conversion of a JSON model into typed render data.
 * <p>
 * Gson deserializes unknown objects into {@code LinkedTreeMap} instances. This
 * handler re-serializes such a map and reads it back as the type expected by
 * the render policy, which is how a plain JSON model can be bound to typed
 * render data.
 * </p>
 */
public interface GsonHandler {

    /**
     * Returns the parser used to read JSON, and by default also to write it.
     *
     * @return the Gson instance
     */
    Gson parser();

    /**
     * Returns the writer used to re-serialize a JSON model.
     *
     * @return the Gson instance, by default {@link #parser()}
     */
    default Gson writer() {
        return parser();
    }

    /**
     * Converts a JSON tree into the given type.
     *
     * @param source the JSON tree
     * @param type   the target type
     * @param <T>    the target type
     * @return the converted value
     */
    default <T> T castJsonToType(LinkedTreeMap<?, ?> source, Type type) {
        return parser().fromJson(writer().toJson(source), type);
    }

    /**
     * Converts a JSON tree into the given class.
     *
     * @param source the JSON tree
     * @param clazz  the target class
     * @param <T>    the target type
     * @return the converted value
     */
    default <T> T castJsonToClass(LinkedTreeMap<?, ?> source, Class<T> clazz) {
        return parser().fromJson(writer().toJson(source), clazz);
    }

    /**
     * Converts JSON text into the given type.
     *
     * @param source the JSON text
     * @param type   the target type
     * @param <T>    the target type
     * @return the converted value
     */
    default <T> T castJsonToType(String source, Type type) {
        return parser().fromJson(source, type);
    }

    /**
     * Converts JSON text into the given class.
     *
     * @param source the JSON text
     * @param clazz  the target class
     * @param <T>    the target type
     * @return the converted value
     */
    default <T> T castJsonToClass(String source, Class<T> clazz) {
        return parser().fromJson(source, clazz);
    }

}
