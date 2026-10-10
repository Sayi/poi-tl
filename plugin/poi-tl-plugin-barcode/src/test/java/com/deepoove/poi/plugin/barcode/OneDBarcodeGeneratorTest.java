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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class OneDBarcodeGeneratorTest {

    private static final int TEXT_MARGIN = 3;
    private static final int TEXT_SIZE = 12;

    @Test
    public void testCode128DecodeLoopback() throws Exception {
        byte[] png = BarcodeRenderData.ofCode128("SF1029384756").size(240, 60).showText(false).build()
                .readPictureData();
        Result result = BarcodeDecodeSupport.decode(png);
        Assertions.assertEquals("SF1029384756", result.getText());
        Assertions.assertEquals(BarcodeFormat.CODE_128, result.getBarcodeFormat());
    }

    @Test
    public void testCode128WithoutTextKeepsRequestedHeight() throws Exception {
        BufferedImage image = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofCode128("SF1029384756")
                .size(240, 60).showText(false).build().readPictureData());
        Assertions.assertEquals(240, image.getWidth());
        Assertions.assertEquals(60, image.getHeight());
    }

    @Test
    public void testCode128HumanReadableTextIsDrawnBelowTheBars() throws Exception {
        int height = 60;
        int textHeight = textHeight();
        int barHeight = height - textHeight - TEXT_MARGIN;

        BufferedImage withText = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofCode128("SF1029384756")
                .size(240, height).showText(true).build().readPictureData());
        BufferedImage withoutText = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofCode128("SF1029384756")
                .size(240, height).showText(false).build().readPictureData());

        Assertions.assertTrue(hasForeground(withText, barHeight + TEXT_MARGIN, height - 1),
                "the human readable text must be drawn below the bars");
        Assertions.assertTrue(isAllBackground(withText, barHeight, barHeight + TEXT_MARGIN - 1),
                "the gap between the bars and the text must stay empty");
        Assertions.assertFalse(BarcodeDecodeSupport.samePixels(
                BarcodeRenderData.ofCode128("SF1029384756").size(240, height).showText(true).build().readPictureData(),
                BarcodeRenderData.ofCode128("SF1029384756").size(240, height).showText(false).build().readPictureData()),
                "showText must change the generated image");
        Assertions.assertTrue(hasForeground(withoutText, barHeight, height - 1),
                "without text the bars use the full height");
    }

    @Test
    public void testCode128WithTextStillDecodes() throws Exception {
        byte[] png = BarcodeRenderData.ofCode128("SF1029384756").size(300, 80).showText(true).build()
                .readPictureData();
        Assertions.assertEquals("SF1029384756", BarcodeDecodeSupport.decode(png).getText());
    }

    @Test
    public void testCode128RejectsNonAscii() {
        BarcodeRenderData data = BarcodeRenderData.ofCode128("运单号001").size(240, 60).build();
        IllegalArgumentException error = Assertions.assertThrows(IllegalArgumentException.class,
                data::readPictureData);
        Assertions.assertTrue(error.getMessage().contains("ASCII"), error.getMessage());
    }

    @Test
    public void testCode128ReportsOverflowInsteadOfSqueezingTheBars() {
        BarcodeRenderData data = BarcodeRenderData.ofCode128("SF1029384756-ABCDEFGHIJKLMNOPQRSTUVWXYZ-0123456789")
                .size(240, 60).build();
        IllegalArgumentException error = Assertions.assertThrows(IllegalArgumentException.class,
                data::readPictureData);
        Assertions.assertTrue(error.getMessage().contains("too long"), error.getMessage());
    }

    @Test
    public void testCode39LowercaseIsNormalized() throws Exception {
        byte[] png = BarcodeRenderData.ofCode39("asset-098").size(240, 60).showText(false).build().readPictureData();
        Assertions.assertEquals("ASSET-098", BarcodeDecodeSupport.decode(png).getText());
    }

    @Test
    public void testCode39DecodeLoopback() throws Exception {
        byte[] png = BarcodeRenderData.ofCode39("ASSET-2025-098").size(240, 60).showText(false).build()
                .readPictureData();
        Assertions.assertEquals("ASSET-2025-098", BarcodeDecodeSupport.decode(png).getText());
    }

    @Test
    public void testCode39RejectsUnsupportedCharacter() {
        BarcodeRenderData data = BarcodeRenderData.ofCode39("ASSET_098").size(240, 60).build();
        IllegalArgumentException error = Assertions.assertThrows(IllegalArgumentException.class,
                data::readPictureData);
        Assertions.assertTrue(error.getMessage().contains("Code 39"), error.getMessage());
    }

    @Test
    public void testEan13Checksum() {
        // externally known codes: ISBN 978-7-111-18777-6 and EAN 4006381333931
        Assertions.assertEquals("6", com.deepoove.poi.plugin.barcode.generator.Ean13Checksum.calculate("978711118777"));
        Assertions.assertEquals("1", com.deepoove.poi.plugin.barcode.generator.Ean13Checksum.calculate("400638133393"));
        Assertions.assertEquals("9787111187776",
                com.deepoove.poi.plugin.barcode.generator.Ean13Checksum.complete("978711118777"));
        Assertions.assertEquals("4006381333931",
                com.deepoove.poi.plugin.barcode.generator.Ean13Checksum.complete("400638133393"));
    }

    @Test
    public void testEan13CompletesTheChecksum() throws Exception {
        byte[] png = BarcodeRenderData.ofEan13("978711118777").size(200, 70).showText(true).build().readPictureData();
        Result result = BarcodeDecodeSupport.decode(png);
        Assertions.assertEquals("9787111187776", result.getText());
        Assertions.assertEquals(BarcodeFormat.EAN_13, result.getBarcodeFormat());
    }

    @Test
    public void testEan13AcceptsValidThirteenDigits() throws Exception {
        byte[] png = BarcodeRenderData.ofEan13("9787111187776").size(200, 70).showText(false).build()
                .readPictureData();
        Assertions.assertEquals("9787111187776", BarcodeDecodeSupport.decode(png).getText());
    }

    @Test
    public void testEan13RejectsWrongChecksum() {
        BarcodeRenderData data = BarcodeRenderData.ofEan13("9787111187779").size(200, 70).build();
        IllegalArgumentException error = Assertions.assertThrows(IllegalArgumentException.class,
                data::readPictureData);
        Assertions.assertTrue(error.getMessage().contains("checksum"), error.getMessage());
    }

    @Test
    public void testEan13RejectsNonDigits() {
        BarcodeRenderData data = BarcodeRenderData.ofEan13("97871111877X").size(200, 70).build();
        Assertions.assertThrows(IllegalArgumentException.class, data::readPictureData);
    }

    @Test
    public void testEan13OverflowIsReported() {
        BarcodeRenderData data = BarcodeRenderData.ofEan13("9787111187776").size(20, 20).build();
        Assertions.assertThrows(IllegalArgumentException.class, data::readPictureData);
    }

    private static int textHeight() {
        BufferedImage scratch = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = scratch.createGraphics();
        try {
            FontMetrics metrics = graphics.getFontMetrics(new Font(Font.SANS_SERIF, Font.PLAIN, TEXT_SIZE));
            return metrics.getAscent() + metrics.getDescent();
        } finally {
            graphics.dispose();
        }
    }

    private static boolean hasForeground(BufferedImage image, int fromY, int toY) {
        int background = Color.WHITE.getRGB();
        for (int y = Math.max(0, fromY); y <= Math.min(image.getHeight() - 1, toY); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getRGB(x, y) != background) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isAllBackground(BufferedImage image, int fromY, int toY) {
        return !hasForeground(image, fromY, toY);
    }

}
