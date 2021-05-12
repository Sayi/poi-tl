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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
            logger.info("Read contents from local path:{}", res.toPath());
            return Files.readAllBytes(res.toPath());
        } catch (IOException e) {
            logger.error("Read all bytes error", e);
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

    /**
     * url to stream
     * 
     * @param urlPath
     * @return
     * @throws IOException
     */
    public static InputStream getUrlStream(String urlPath) throws IOException {
        logger.info("Read contents from remote uri:{}", urlPath);
        URL url = new URL(urlPath);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");
        InputStream inputStream = connection.getInputStream();
        if (connection instanceof HttpURLConnection) {
            if (200 != ((HttpURLConnection) connection).getResponseCode()) {
                throw new IOException("get url " + urlPath + " content error, response status: "
                        + ((HttpURLConnection) connection).getResponseCode());
            }
        }
        return inputStream;
    }

    /**
     * Tests if the bytes starts with the specified prefix.
     * 
     * @param bytes
     * @param prefix
     * @return
     */
    public static boolean startsWith(byte[] bytes, byte[] prefix) {
        if (bytes == prefix) return true;
        if (null == prefix || null == bytes || bytes.length < prefix.length) return false;
        for (int i = 0; i < prefix.length; i++) {
            if (bytes[i] != prefix[i]) return false;
        }
        return true;
    }

    /**
     * Tests if the bytes ends with the specified suffix.
     * 
     * @param bytes
     * @param suffix
     * @return
     */
    public static boolean endsWith(byte[] bytes, byte[] suffix) {
        if (bytes == suffix) return true;
        if (null == suffix || null == bytes || bytes.length < suffix.length) return false;
        int length = bytes.length - suffix.length;
        for (int i = suffix.length - 1; i >= 0; i--) {
            if (bytes[length + i] != suffix[i]) return false;
        }
        return true;
    }
}
