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
package com.deepoove.poi.plugin.barcode.generator;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import com.deepoove.poi.plugin.barcode.BarcodeLogo;
import com.deepoove.poi.plugin.barcode.BarcodeRenderData;
import com.deepoove.poi.plugin.barcode.BarcodeStyle;
import com.deepoove.poi.plugin.barcode.BarcodeType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * The barcode image engine, stateless and free of side effects.
 * <p>
 * Beyond calling ZXing, it takes care of the things ZXing does not do for a
 * document: an explicit quiet zone for Data Matrix, the human readable text
 * below one dimensional symbols, the center logo of QR Code, the overflow
 * detection and the PNG encoding.
 *
 * @author Sayi
 */
public final class ZxingBarcodeGenerator {

    /**
     * ZXing's writers read the character set (or not) on their own, the hint is only
     * meaningful for the two dimensional symbologies.
     */
    private static final String UTF8 = "UTF-8";

    private static final String CODE_39_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%";

    private static final int ASCII_MAX = 127;

    private ZxingBarcodeGenerator() {
    }

    public static byte[] toPng(BarcodeRenderData data) {
        if (null == data) {
            throw new IllegalArgumentException("Barcode render data must not be null!");
        }
        BarcodeType type = data.getBarcodeType();
        BarcodeStyle style = data.getBarcodeStyle();
        String content = data.getContent();
        if (null == content || content.isEmpty()) {
            throw new IllegalArgumentException("Barcode content must not be empty!");
        }
        String encoded = normalize(type, content);

        int width = style.getWidth() > 0 ? style.getWidth() : type.getDefaultWidth();
        int height = style.getHeight() > 0 ? style.getHeight() : type.getDefaultHeight();
        int margin = null != style.getMargin() ? Math.max(0, style.getMargin()) : type.getDefaultMargin();
        boolean withLogo = type.supportsLogo() && null != data.getLogo();
        boolean showText = !type.isTwoDimensional()
                && (null != style.getShowText() ? style.getShowText() : type.isShowTextByDefault());

        BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = canvas.createGraphics();
        try {
            graphics.setColor(style.getBackgroundColor());
            graphics.fillRect(0, 0, width, height);

            // the text area is reserved before encoding, the bars must never be drawn
            // over the human readable text
            int barHeight = height;
            if (showText) {
                FontMetrics metrics = graphics.getFontMetrics(style.getTextFont());
                int textHeight = metrics.getAscent() + metrics.getDescent();
                barHeight = Math.max(height / 2, height - textHeight - Math.max(0, style.getTextMargin()));
            }

            if (BarcodeType.DATA_MATRIX == type) {
                drawDataMatrix(graphics, encoded, style, width, height, margin);
            } else {
                BitMatrix matrix = encode(encoded, type, width, barHeight, margin, data.getErrorCorrectionLevel(),
                        withLogo);
                checkOverflow(matrix, type, width, barHeight);
                drawBitMatrix(graphics, matrix, style, 0, 0);
            }

            if (showText) {
                drawText(graphics, encoded, style, width, barHeight);
            }
            if (withLogo) {
                drawLogo(graphics, data.getLogo(), width, height);
            }
        } finally {
            graphics.dispose();
        }
        return writePng(canvas);
    }

    private static BitMatrix encode(String content, BarcodeType type, int width, int height, int margin,
            ErrorCorrectionLevel level, boolean withLogo) {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.MARGIN, margin);
        if (type.isTwoDimensional()) {
            hints.put(EncodeHintType.CHARACTER_SET, UTF8);
        }
        if (BarcodeType.QR_CODE == type) {
            hints.put(EncodeHintType.ERROR_CORRECTION,
                    null != level ? level : (withLogo ? ErrorCorrectionLevel.H : ErrorCorrectionLevel.M));
        }
        try {
            return new MultiFormatWriter().encode(content, type.getZxingFormat(), width, height, hints);
        } catch (WriterException e) {
            throw new IllegalArgumentException(
                    "Can not encode " + type + " content [" + content + "]: " + e.getMessage(), e);
        }
    }

    /**
     * ZXing renders one dimensional symbols wider than requested when the content
     * does not fit, see {@code OneDimensionalCodeWriter#renderResult}. The docx
     * renderer would then squeeze that wider image back into the style width and the
     * bar to space ratio would be destroyed, so it is reported instead.
     */
    private static void checkOverflow(BitMatrix matrix, BarcodeType type, int width, int height) {
        if (matrix.getWidth() > width || matrix.getHeight() > height) {
            throw new IllegalArgumentException(String.format(
                    "Content is too long for %s in %dx%d pixels, the encoded symbol is %dx%d pixels. "
                            + "Increase the barcode size or shorten the content.",
                    type, width, height, matrix.getWidth(), matrix.getHeight()));
        }
    }

    /**
     * ZXing's DataMatrixWriter ignores {@link EncodeHintType#MARGIN}, but encoding at
     * 1x1 returns the bare symbol at exactly one pixel per module. That gives us the
     * full control on the quiet zone and keeps every module an integer number of
     * pixels.
     */
    private static void drawDataMatrix(Graphics2D graphics, String content, BarcodeStyle style, int width, int height,
            int margin) {
        BitMatrix symbol = encode(content, BarcodeType.DATA_MATRIX, 1, 1, 0, null, false);
        int symbolWidth = symbol.getWidth();
        int symbolHeight = symbol.getHeight();
        int scale = Math.min(width / (symbolWidth + 2 * margin), height / (symbolHeight + 2 * margin));
        if (scale < 1) {
            throw new IllegalArgumentException(String.format(
                    "Content is too long for %s in %dx%d pixels with margin %d, the symbol alone needs %dx%d pixels. "
                            + "Increase the barcode size or shorten the content.",
                    BarcodeType.DATA_MATRIX, width, height, margin, symbolWidth, symbolHeight));
        }
        int offsetX = (width - symbolWidth * scale) / 2;
        int offsetY = (height - symbolHeight * scale) / 2;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        for (int y = 0; y < symbolHeight; y++) {
            for (int x = 0; x < symbolWidth; x++) {
                graphics.setColor(symbol.get(x, y) ? style.getForegroundColor() : style.getBackgroundColor());
                graphics.fillRect(offsetX + x * scale, offsetY + y * scale, scale, scale);
            }
        }
    }

    private static void drawBitMatrix(Graphics2D graphics, BitMatrix matrix, BarcodeStyle style, int offsetX,
            int offsetY) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int on = style.getForegroundColor().getRGB();
        int off = style.getBackgroundColor().getRGB();
        int[] pixels = new int[width * height];
        for (int y = 0, index = 0; y < height; y++) {
            for (int x = 0; x < width; x++, index++) {
                pixels[index] = matrix.get(x, y) ? on : off;
            }
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.drawImage(image, offsetX, offsetY, null);
    }

    private static void drawText(Graphics2D graphics, String content, BarcodeStyle style, int width, int barHeight) {
        Font font = style.getTextFont();
        FontMetrics metrics = graphics.getFontMetrics(font);
        graphics.setFont(font);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setColor(style.getForegroundColor());
        int x = Math.max(0, (width - metrics.stringWidth(content)) / 2);
        int y = barHeight + Math.max(0, style.getTextMargin()) + metrics.getAscent();
        graphics.drawString(content, x, y);
    }

    private static void drawLogo(Graphics2D graphics, BarcodeLogo logo, int width, int height) {
        BufferedImage image = logo.readImage();
        double ratio = logo.getRatio() > 0 ? Math.min(logo.getRatio(), BarcodeLogo.MAX_RATIO) : BarcodeLogo.MAX_RATIO;
        int maxWidth = Math.max(1, (int) Math.floor(width * ratio));
        int maxHeight = Math.max(1, (int) Math.floor(height * ratio));
        double scale = Math.min(maxWidth / (double) image.getWidth(), maxHeight / (double) image.getHeight());
        int logoWidth = Math.max(1, (int) Math.floor(image.getWidth() * scale));
        int logoHeight = Math.max(1, (int) Math.floor(image.getHeight() * scale));
        int x = (width - logoWidth) / 2;
        int y = (height - logoHeight) / 2;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int padding = logo.getBorderWidth();
        if (padding > 0) {
            graphics.setColor(logo.getBorderColor());
            graphics.fillRoundRect(x - padding, y - padding, logoWidth + padding * 2, logoHeight + padding * 2,
                    logo.getArc(), logo.getArc());
        }
        graphics.drawImage(image, x, y, logoWidth, logoHeight, null);
    }

    private static String normalize(BarcodeType type, String content) {
        switch (type) {
            case CODE_39:
                return normalizeCode39(content);
            case CODE_128:
                normalizeCode128(content);
                return content;
            case EAN_13:
                return Ean13Checksum.complete(content);
            default:
                return content;
        }
    }

    /**
     * Code 39 is defined on upper case characters only, the encoded content and the
     * human readable text are therefore both normalized here.
     */
    private static String normalizeCode39(String content) {
        String upper = content.toUpperCase(Locale.ENGLISH);
        for (int i = 0; i < upper.length(); i++) {
            char character = upper.charAt(i);
            if (CODE_39_CHARS.indexOf(character) < 0) {
                throw new IllegalArgumentException("Code 39 does not support the character '" + character
                        + "', supported characters are: " + CODE_39_CHARS);
            }
        }
        return upper;
    }

    /**
     * Code 128 encodes ASCII only, ZXing would otherwise fail with a message about
     * an ASCII value which is of no help to the caller.
     */
    private static void normalizeCode128(String content) {
        for (int i = 0; i < content.length(); i++) {
            char character = content.charAt(i);
            if (character > ASCII_MAX) {
                throw new IllegalArgumentException("Code 128 supports ASCII characters only, but found '" + character
                        + "' at index " + i + ". Use QR Code or Data Matrix for non ASCII content.");
            }
        }
    }

    private static byte[] writePng(BufferedImage image) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Can not write barcode image: " + e.getMessage(), e);
        }
    }

}
