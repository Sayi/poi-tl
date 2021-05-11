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

package com.deepoove.poi.render.compute;

import java.util.Collections;
import java.util.Map;

public class EnvModel {

    private Object root;
    private Map<String, Object> env;

    public static EnvModel ofModel(Object root) {
        return of(root, Collections.emptyMap());
    }

    public static EnvModel of(Object root, Map<String, Object> env) {
        EnvModel envModel = new EnvModel();
        envModel.root = root;
        envModel.env = env;
        return envModel;
    }

    public Object getRoot() {
        return root;
    }

    public void setRoot(Object root) {
        this.root = root;
    }

    public Map<String, Object> getEnv() {
        return env;
    }

    public void setEnv(Map<String, Object> env) {
        this.env = env;
    }

}
