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
package com.deepoove.poi.data;

import static com.deepoove.poi.util.ByteUtils.endsWith;
import static com.deepoove.poi.util.ByteUtils.startsWith;

/**
 * @author Sayi
 * @see org.apache.poi.xwpf.usermodel.Document
 */
public enum PictureType {
    /**
     * Extended windows meta file
     */
    EMF(2),

    /**
     * Windows Meta File
     */
    WMF(3),

    /**
     * Mac PICT format
     */
    PICT(4),

    /**
     * JPEG format
     */
    JPEG(5),

    /**
     * PNG format
     */
    PNG(6),

    /**
     * Device independent bitmap
     */
    DIB(7),

    /**
     * GIF image format
     */
    GIF(8),

    /**
     * Tag Image File (.tiff)
     */
    TIFF(9),

    /**
     * Encapsulated Postscript (.eps)
     */
    EPS(10),

    /**
     * Windows Bitmap (.bmp)
     */
    BMP(11),

    /**
     * WordPerfect graphics (.wpg)
     */
    WPG(12),

    /**
     * .svg
     */
    SVG(13);

    private int type;

    PictureType(int type) {
        this.type = type;
    }

    public int type() {
        return this.type;
    }

    public String format() {
        return super.name().toLowerCase();
    }

    public static PictureType suggestFileType(String imgFile) {
        PictureType format = null;

        imgFile = imgFile.toLowerCase();
        if (imgFile.endsWith(".emf")) format = EMF;
        else if (imgFile.endsWith(".wmf")) format = WMF;
        else if (imgFile.endsWith(".pict")) format = PICT;
        else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg")) format = JPEG;
        else if (imgFile.endsWith(".png")) format = PNG;
        else if (imgFile.endsWith(".dib")) format = DIB;
        else if (imgFile.endsWith(".gif")) format = GIF;
        else if (imgFile.endsWith(".tiff")) format = TIFF;
        else if (imgFile.endsWith(".eps")) format = EPS;
        else if (imgFile.endsWith(".bmp")) format = BMP;
        else if (imgFile.endsWith(".wpg")) format = WPG;
        else {
            throw new IllegalArgumentException(
                    "Unsupported picture: " + imgFile + ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");
        }
        return format;
    }

    /**
     * https://en.wikipedia.org/wiki/Magic_number_%28programming%29
     */
    public static PictureType suggestFileType(byte[] bytes) {
        if (startsWith(bytes, "GIF89a".getBytes()) || startsWith(bytes, "GIF87a".getBytes())) {
            return GIF;
        }
        if (startsWith(bytes, new byte[] { (byte) 0xFF, (byte) 0xD8 })
                || endsWith(bytes, new byte[] { (byte) 0xFF, (byte) 0xD9 })) {
            return JPEG;
        }
        if (startsWith(bytes, new byte[] { (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47 })) {
            return PNG;
        }
        if (startsWith(bytes, new byte[] { (byte) 0x49, (byte) 0x49, (byte) 0x2A, (byte) 0x00 })
                || startsWith(bytes, new byte[] { (byte) 0x4D, (byte) 0x4D, (byte) 0x00, (byte) 0x2A })) {
            return TIFF;
        }
        if (startsWith(bytes, "BM".getBytes())) {
            return BMP;
        }
        String str = new String(bytes);
        // TODO better compare
        if (str.substring(0, 100).indexOf("<svg") != -1 || str.substring(str.length() - 10).indexOf("</svg>") != -1) {
            return SVG;
        }
        throw new IllegalArgumentException("Unable to identify the picture type from byte");
    }

}
