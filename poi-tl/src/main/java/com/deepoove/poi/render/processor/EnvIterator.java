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
package com.deepoove.poi.render.processor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import com.deepoove.poi.render.compute.EnvModel;

public class EnvIterator {

    public static void foreach(Iterator<?> iterator, Consumer<EnvModel> consumer) {
        int index = 0;
        boolean hasNext = iterator.hasNext();
        while (hasNext) {
            Object root = iterator.next();
            hasNext = iterator.hasNext();
            consumer.accept(EnvModel.of(root, makeEnv(index++, hasNext)));
        }
    }

    public static Map<String, Object> makeEnv(int index, boolean hasNext) {
        Map<String, Object> env = new HashMap<>();
        env.put("_is_first", index == 0);
        env.put("_is_last", !hasNext);
        env.put("_has_next", hasNext);
        env.put("_is_even_item", index % 2 == 1);
        env.put("_is_odd_item", index % 2 == 0);
        env.put("_index", index);
        return env;
    }

}
