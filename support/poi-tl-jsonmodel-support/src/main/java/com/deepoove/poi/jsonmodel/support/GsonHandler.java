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

public interface GsonHandler {

    Gson parser();

    default Gson writer() {
        return parser();
    }

    default <T> T castJsonToType(LinkedTreeMap<?, ?> source, Type type) {
        return parser().fromJson(writer().toJson(source), type);
    }

    default <T> T castJsonToClass(LinkedTreeMap<?, ?> source, Class<T> clazz) {
        return parser().fromJson(writer().toJson(source), clazz);
    }

    default <T> T castJsonToType(String source, Type type) {
        return parser().fromJson(source, type);
    }

    default <T> T castJsonToClass(String source, Class<T> clazz) {
        return parser().fromJson(source, clazz);
    }

}
