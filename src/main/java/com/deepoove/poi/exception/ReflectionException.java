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
package com.deepoove.poi.exception;

import org.apache.commons.lang3.ClassUtils;

public class ReflectionException extends RuntimeException {

    private static final long serialVersionUID = -8702985242803837748L;

    public ReflectionException() {}

    public ReflectionException(String msg) {
        super(msg);
    }

    public ReflectionException(String msg, Exception e) {
        super(msg, e);
    }

    public ReflectionException(String name, Class<?> clazz, Exception e) {
        this("Error Reflect " + name + "from class " + ClassUtils.getShortClassName(clazz), e);
    }

}
