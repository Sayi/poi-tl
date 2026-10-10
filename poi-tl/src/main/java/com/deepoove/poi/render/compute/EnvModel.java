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

package com.deepoove.poi.render.compute;

import java.util.Collections;
import java.util.Map;

/**
 * A data model together with its loop environment.
 * <p>
 * The root object is what template expressions are evaluated against, while the
 * environment carries the loop metadata injected by the iterator, for example
 * {@code _index} or {@code _is_last}. Expressions that do not mention
 * {@code #this} are resolved against the environment first.
 * </p>
 *
 * @see com.deepoove.poi.render.processor.EnvIterator
 */
public class EnvModel {

    private Object root;
    private Map<String, Object> env;

    /**
     * Creates a model without any loop environment.
     *
     * @param root the root object of the data model
     * @return the environment model
     */
    public static EnvModel ofModel(Object root) {
        return of(root, Collections.emptyMap());
    }

    /**
     * Creates a model with the given loop environment.
     *
     * @param root the root object of the data model
     * @param env  the loop environment variables
     * @return the environment model
     */
    public static EnvModel of(Object root, Map<String, Object> env) {
        EnvModel envModel = new EnvModel();
        envModel.root = root;
        envModel.env = env;
        return envModel;
    }

    /**
     * Returns the root object of the data model.
     *
     * @return the root object
     */
    public Object getRoot() {
        return root;
    }

    /**
     * Sets the root object of the data model.
     *
     * @param root the root object to set
     */
    public void setRoot(Object root) {
        this.root = root;
    }

    /**
     * Returns the loop environment variables.
     *
     * @return the environment variables
     */
    public Map<String, Object> getEnv() {
        return env;
    }

    /**
     * Sets the loop environment variables.
     *
     * @param env the environment variables to set
     */
    public void setEnv(Map<String, Object> env) {
        this.env = env;
    }

}
