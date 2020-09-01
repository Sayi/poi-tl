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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Network pictures, local pictures, and BufferedImage are converted into byte
 * arrays
 * 
 * @author Sayi
 */
public final class BufferedImageUtils {

    private static Logger logger = LoggerFactory.getLogger(BufferedImageUtils.class);

    /**
     * Get picture byte array of BufferedImage
     * 
     * @param image
     * @param format format of the image
     * @return
     */
    public static byte[] getBufferByteArray(BufferedImage image, String format) {
        if (null == image) return null;
        String formatName = (StringUtils.isNotEmpty(format) && format.charAt(0) == '.') ? format.substring(1) : format;
        if (StringUtils.isEmpty(formatName)) formatName = "png";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, formatName, os);
        } catch (Exception e) {
            logger.error("getBufferByteArray error", e);
            return null;
        }
        return os.toByteArray();
    }

    /**
     * Get BufferedImage of url
     * 
     * @param urlPath
     * @return
     */
    public static BufferedImage getUrlBufferedImage(String urlPath) {
        URL url = null;
        BufferedImage bufferImage = null;
        try {
            url = new URL(urlPath);
            bufferImage = ImageIO.read(url);
            return bufferImage;
        } catch (Exception e) {
            logger.error("getUrlBufferedImage error, {}, {}", urlPath, e);
        }
        return null;

    }

    /**
     * Get BufferedImage of file
     * 
     * @param res
     * @return
     */
    public static BufferedImage getLocalBufferedImage(File res) {
        try {
            BufferedImage read = ImageIO.read(res);
            return read;
        } catch (FileNotFoundException e) {
            logger.error("FileNotFound", e);
        } catch (IOException e) {
            logger.error("getLocalBufferedImage IO error", e);
        }
        return null;
    }

    /**
     * Create RGB BufferedImage
     * 
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage newBufferImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        return image;
    }

}
