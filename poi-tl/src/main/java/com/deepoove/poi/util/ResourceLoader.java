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
package com.deepoove.poi.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.utils.IOUtils;

public class ResourceLoader {

    public static String loadContent(String url) throws IOException {
        ClassLoader cl = getDefaultClassLoader();
        InputStream stream = null != cl ? cl.getResourceAsStream(url) : ClassLoader.getSystemResourceAsStream(url);
        try {
            return new String(IOUtils.toByteArray(stream));
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    public static InputStream load(String url) throws IOException {
        ClassLoader cl = getDefaultClassLoader();
        return null != cl ? cl.getResourceAsStream(url) : ClassLoader.getSystemResourceAsStream(url);
    }

    private static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ResourceLoader.class.getClassLoader();
            if (cl == null) {
                // getClassLoader() returning null indicates the bootstrap ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    // Cannot access system ClassLoader - oh well, maybe the caller can live with
                    // null...
                }
            }
        }
        return cl;
    }

}
