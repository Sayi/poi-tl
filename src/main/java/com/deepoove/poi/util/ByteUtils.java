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
import java.nio.file.Path;

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
        Path path = res.toPath();
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            logger.error("readAllBytes error", e);
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
            try {
                is.close();
            } catch (IOException e) {
                logger.error("close stream error", e);
            }
        }
        return null;
    }

    public static InputStream getUrlStream(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        return url.openConnection().getInputStream();
    }
}
