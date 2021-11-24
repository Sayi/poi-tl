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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ooxml.POIXMLDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;

public class PoitlIOUtils {

    private static Logger logger = LoggerFactory.getLogger(PoitlIOUtils.class);

    public static InputStream docToInputStream(POIXMLDocument doc) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            doc.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } finally {
            closeQuietlyMulti(doc, out);
        }
    }

    public static InputStream templateToInputStream(XWPFTemplate doc) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            doc.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } finally {
            closeQuietlyMulti(doc, out);
        }
    }

    public static void closeLoggerQuietly(final Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (final IOException ignored) {
                logger.error("Close failed", ignored);
            }
        }
    }

    public static void closeQuietlyMulti(final Closeable... cls) {
        for (Closeable c : cls) {
            closeQuietly(c);
        }
    }

    public static void closeQuietly(final Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (final IOException ignored) {
            }
        }
    }

}
