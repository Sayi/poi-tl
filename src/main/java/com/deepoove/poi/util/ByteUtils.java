/*
 * Copyright 2014-2020 Sayi
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ByteUtils {

    private static Logger logger = LoggerFactory.getLogger(ByteUtils.class);

    /**
     * Get byte array of network url
     * 
     * @param urlPath
     * @return
     */
    public static byte[] getUrlByteArray(String urlPath) {
        try {
            return toByteArray(getUrlStream(urlPath));
        } catch (IOException e) {
            logger.error("getUrlPictureStream error,{},{}", urlPath, e);
        }
        return null;
    }

    /**
     * Get byte array of file
     * 
     * @param res
     * @return
     */
    public static byte[] getLocalByteArray(File res) {
        try {
            return Files.readAllBytes(res.toPath());
        } catch (IOException e) {
            logger.error("readAllBytes error", e);
        }
        return null;
    }

    /**
     * Get byte array of base64
     * 
     * @param base64
     * @return
     */
    public static byte[] getBase64ByteArray(String base64) {
        String encodingPrefix = "base64,";
        if (base64.contains(encodingPrefix)) {
            int contentStartIndex = base64.indexOf(encodingPrefix) + encodingPrefix.length();
            base64 = base64.substring(contentStartIndex);
        }
        boolean isBase64 = Base64.isBase64(base64);
        if (isBase64) {
            return Base64.decodeBase64(base64);
        }
        return null;
    }

    /**
     * Get byte array of stream
     * 
     * @param is
     * @return
     */
    public static byte[] toByteArray(InputStream is) {
        if (null == is) return null;
        try {
            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            logger.error("toByteArray error", e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        return null;
    }

    public static InputStream getUrlStream(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        return url.openConnection().getInputStream();
    }
}
