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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;

public class DataMatrixGeneratorTest {

    private static final String UDI = "(01)00845678901234(17)251231";

    @Test
    public void testDecodeLoopback() throws Exception {
        byte[] png = BarcodeRenderData.ofDataMatrix(UDI).size(80, 80).build().readPictureData();
        Result result = BarcodeDecodeSupport.decode(png);
        Assertions.assertEquals(UDI, result.getText());
        Assertions.assertEquals(BarcodeFormat.DATA_MATRIX, result.getBarcodeFormat());
    }

    @Test
    public void testSizeIsExact() throws Exception {
        BufferedImage image = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofDataMatrix(UDI).size(80, 80)
                .build().readPictureData());
        Assertions.assertEquals(80, image.getWidth());
        Assertions.assertEquals(80, image.getHeight());
    }

    @Test
    public void testMarginShrinksTheSymbol() throws Exception {
        int foreground = Color.BLACK.getRGB();
        BufferedImage noMargin = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofDataMatrix(UDI)
                .size(80, 80).margin(0).build().readPictureData());
        BufferedImage margin = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofDataMatrix(UDI)
                .size(80, 80).margin(3).build().readPictureData());
        // ZXing's DataMatrixWriter ignores EncodeHintType.MARGIN, the plugin applies
        // it itself, a wider quiet zone therefore means a smaller symbol
        Assertions.assertTrue(BarcodeDecodeSupport.countColor(margin, foreground) < BarcodeDecodeSupport
                .countColor(noMargin, foreground), "the margin must be applied by the plugin");
    }

    @Test
    public void testQuietZoneIsClean() throws Exception {
        BufferedImage image = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofDataMatrix(UDI).size(80, 80)
                .build().readPictureData());
        Assertions.assertTrue(BarcodeDecodeSupport.isBorderClean(image, Color.WHITE.getRGB()),
                "the default margin of 1 module must keep the border free of modules");
        Assertions.assertEquals(UDI, BarcodeDecodeSupport.decode(image).getText());
    }

    @Test
    public void testModuleScalingKeepsIntegerModules() throws Exception {
        // the plugin rasterizes the bare 1 pixel per module symbol itself, so every
        // module must cover exactly scale * scale pixels without any interpolation
        BitMatrix symbol = new MultiFormatWriter().encode(UDI, BarcodeFormat.DATA_MATRIX, 1, 1, null);
        int scale = Math.min(80 / (symbol.getWidth() + 2), 80 / (symbol.getHeight() + 2));
        Assertions.assertTrue(scale >= 1, "the test content must fit into 80x80");
        int darkModules = 0;
        for (int y = 0; y < symbol.getHeight(); y++) {
            for (int x = 0; x < symbol.getWidth(); x++) {
                if (symbol.get(x, y)) {
                    darkModules++;
                }
            }
        }
        BufferedImage image = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofDataMatrix(UDI).size(80, 80)
                .build().readPictureData());
        Assertions.assertEquals(darkModules * scale * scale,
                BarcodeDecodeSupport.countColor(image, Color.BLACK.getRGB()),
                "every module must be scaled by the same integer factor");
    }

    @Test
    public void testOverflowIsReported() {
        BarcodeRenderData data = BarcodeRenderData.ofDataMatrix(UDI).size(10, 10).build();
        IllegalArgumentException error = Assertions.assertThrows(IllegalArgumentException.class,
                data::readPictureData);
        Assertions.assertTrue(error.getMessage().contains("too long"), error.getMessage());
    }

}
