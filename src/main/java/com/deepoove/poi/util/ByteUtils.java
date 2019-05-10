/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ByteUtils {

    private static Logger logger = LoggerFactory.getLogger(ByteUtils.class);

    /**
     * 通过网络地址获取byte数组
     * 
     * @param urlPath
     *            网络地址
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
     * 获取文件byte数组
     * 
     * @param res
     *            文件
     * @return
     */
    public static byte[] getLocalByteArray(File res) {
        try {
            return toByteArray(new FileInputStream(res));
        } catch (FileNotFoundException e) {
            logger.error("FileNotFound", e);
        }
        return null;
    }

    /**
     * 流转换成byte数组
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
        }
        finally {
            try {
                is.close();
            } catch (IOException e) {
                logger.error("close stream error", e);
            }
        }
        return null;
    }

    /**
     * 加载网络流
     * 
     * @param urlPath
     * @return
     * @throws IOException
     */
    public static InputStream getUrlStream(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        return url.openConnection().getInputStream();
    }
}
