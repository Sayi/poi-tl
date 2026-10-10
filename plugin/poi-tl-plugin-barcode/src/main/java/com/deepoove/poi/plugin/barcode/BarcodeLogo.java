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
package com.deepoove.poi.plugin.barcode;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

/**
 * The logo embedded into the center of a QR Code.
 * <p>
 * The image is always held as {@code byte[]} instead of {@link BufferedImage},
 * because {@link com.deepoove.poi.data.RenderData} is {@link Serializable} while
 * {@code BufferedImage} is not. The source stream is fully read and closed when
 * this object is created, so the caller is free to close it right after
 * {@code logo(InputStream)} returns.
 *
 * @author Sayi
 */
public class BarcodeLogo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The maximum ratio of the logo against the symbol size.
     */
    public static final double MAX_RATIO = 0.25;

    private static final double DEFAULT_RATIO = 0.20;

    private final byte[] imageBytes;

    private double ratio = DEFAULT_RATIO;

    /**
     * Corner radius of the padding, in pixel.
     */
    private int arc = 8;

    /**
     * The padding added around the logo, in pixel. The padding keeps the logo from
     * touching the dark modules, which would break the position detection pattern.
     */
    private int borderWidth = 2;

    private Color borderColor = Color.WHITE;

    public BarcodeLogo(byte[] imageBytes) {
        if (null == imageBytes || 0 == imageBytes.length) {
            throw new IllegalArgumentException("Logo image bytes must not be empty!");
        }
        this.imageBytes = imageBytes;
    }

    /**
     * Read the logo from a stream. The stream is fully read and closed here.
     */
    public BarcodeLogo(InputStream inputStream) {
        this(readFully(inputStream));
    }

    /**
     * Build the logo from an in memory image. The image is encoded to PNG
     * immediately so that this object stays serializable.
     */
    public BarcodeLogo(BufferedImage image) {
        this(toPngBytes(image));
    }

    private static byte[] readFully(InputStream inputStream) {
        if (null == inputStream) {
            throw new IllegalArgumentException("Logo input stream must not be null!");
        }
        try (InputStream in = inputStream; ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int read;
            while (-1 != (read = in.read(buffer))) {
                out.write(buffer, 0, read);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not read logo image: " + e.getMessage(), e);
        }
    }

    private static byte[] toPngBytes(BufferedImage image) {
        if (null == image) {
            throw new IllegalArgumentException("Logo image must not be null!");
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not encode logo image: " + e.getMessage(), e);
        }
    }

    /**
     * Decode the logo to an image. Called at generation time only.
     */
    public BufferedImage readImage() {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (null == image) {
                throw new IllegalArgumentException("Unsupported logo image format!");
            }
            return image;
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not decode logo image: " + e.getMessage(), e);
        }
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public double getRatio() {
        return ratio;
    }

    /**
     * @param ratio
     *            ratio against the symbol size, silently clamped to
     *            {@code (0, }{@link #MAX_RATIO}{@code ]}
     */
    public void setRatio(double ratio) {
        if (ratio <= 0) {
            this.ratio = DEFAULT_RATIO;
        } else {
            this.ratio = Math.min(ratio, MAX_RATIO);
        }
    }

    public int getArc() {
        return arc;
    }

    public void setArc(int arc) {
        this.arc = Math.max(0, arc);
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = Math.max(0, borderWidth);
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

}
