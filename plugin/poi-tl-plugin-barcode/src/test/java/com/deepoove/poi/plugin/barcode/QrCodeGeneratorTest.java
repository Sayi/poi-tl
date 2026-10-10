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
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.zxing.Result;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeGeneratorTest {

    private static final String URL = "https://deepoove.com/poi-tl";

    @Test
    public void testSizeIsExact() throws Exception {
        BufferedImage square = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofQrCode(URL).size(120, 120)
                .build().readPictureData());
        Assertions.assertEquals(120, square.getWidth());
        Assertions.assertEquals(120, square.getHeight());

        BufferedImage rectangle = BarcodeDecodeSupport.toImage(BarcodeRenderData.ofQrCode(URL).size(200, 100)
                .build().readPictureData());
        Assertions.assertEquals(200, rectangle.getWidth());
        Assertions.assertEquals(100, rectangle.getHeight());
    }

    @Test
    public void testDecodeLoopback() throws Exception {
        byte[] png = BarcodeRenderData.ofQrCode(URL).size(150, 150).build().readPictureData();
        Result result = BarcodeDecodeSupport.decode(png);
        Assertions.assertEquals(URL, result.getText());
        Assertions.assertEquals(com.google.zxing.BarcodeFormat.QR_CODE, result.getBarcodeFormat());
    }

    @Test
    public void testDecodeLoopbackWithChinese() throws Exception {
        String content = "张三-合同编号-HZ2025-001";
        byte[] png = BarcodeRenderData.ofQrCode(content).size(160, 160).build().readPictureData();
        Assertions.assertEquals(content, BarcodeDecodeSupport.decode(png).getText());
    }

    @Test
    public void testDecodeLoopbackWithLogo() throws Exception {
        try (InputStream logo = getClass().getResourceAsStream("/barcode/logo.png")) {
            byte[] png = BarcodeRenderData.ofQrCode(URL)
                    .size(180, 180)
                    .logo(logo)
                    .errorCorrectionLevel(ErrorCorrectionLevel.H)
                    .build()
                    .readPictureData();
            Assertions.assertEquals(URL, BarcodeDecodeSupport.decode(png).getText());
        }
    }

    @Test
    public void testLogoUpgradesErrorCorrectionLevel() throws Exception {
        try (InputStream logo = getClass().getResourceAsStream("/barcode/logo.png")) {
            // level M is not enough for a big logo, the generator must upgrade it to H
            byte[] png = BarcodeRenderData.ofQrCode(URL).size(180, 180).logo(logo).build().readPictureData();
            Assertions.assertEquals(URL, BarcodeDecodeSupport.decode(png).getText());
        }
    }

    @Test
    public void testErrorCorrectionLevelIsApplied() throws Exception {
        BarcodeRenderData low = BarcodeRenderData.ofQrCode(URL).size(150, 150)
                .errorCorrectionLevel(ErrorCorrectionLevel.L).build();
        BarcodeRenderData high = BarcodeRenderData.ofQrCode(URL).size(150, 150)
                .errorCorrectionLevel(ErrorCorrectionLevel.H).build();
        Assertions.assertFalse(BarcodeDecodeSupport.samePixels(low.readPictureData(), high.readPictureData()),
                "different error correction levels must produce different symbols");
    }

    @Test
    public void testQuietZoneGrowsWithMargin() throws Exception {
        int white = Color.WHITE.getRGB();
        BufferedImage noMargin = BarcodeDecodeSupport.toImage(
                BarcodeRenderData.ofQrCode(URL).size(150, 150).margin(0).build().readPictureData());
        BufferedImage wideMargin = BarcodeDecodeSupport.toImage(
                BarcodeRenderData.ofQrCode(URL).size(150, 150).margin(4).build().readPictureData());
        Assertions.assertTrue(BarcodeDecodeSupport.countColor(wideMargin, white) > BarcodeDecodeSupport
                .countColor(noMargin, white), "a larger quiet zone means more background pixels");
    }

    @Test
    public void testColorsAreApplied() throws Exception {
        Color foreground = new Color(0x00, 0x52, 0xD9);
        byte[] png = BarcodeRenderData.ofQrCode(URL).size(150, 150).color(foreground, Color.WHITE).build()
                .readPictureData();
        BufferedImage image = BarcodeDecodeSupport.toImage(png);
        Assertions.assertTrue(BarcodeDecodeSupport.countColor(image, foreground.getRGB()) > 0);
        Assertions.assertEquals(URL, BarcodeDecodeSupport.decode(png).getText());
    }

    @Test
    public void testGeneratedBytesAreCached() {
        BarcodeRenderData data = BarcodeRenderData.ofQrCode(URL).size(120, 120).build();
        Assertions.assertSame(data.readPictureData(), data.readPictureData());
    }

}
