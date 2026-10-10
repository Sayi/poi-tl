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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class BarcodeRenderDataTest {

    @Test
    public void testDefaults() {
        BarcodeRenderData data = BarcodeRenderData.ofQrCode("https://deepoove.com").build();
        Assertions.assertEquals(BarcodeType.QR_CODE, data.getBarcodeType());
        Assertions.assertEquals("https://deepoove.com", data.getContent());
        Assertions.assertEquals(PictureType.PNG, data.getPictureType());
        Assertions.assertTrue(data.getPictureStyle() instanceof BarcodeStyle);
        Assertions.assertNull(data.getBarcodeStyle().getMargin(), "margin must stay unset so that the type default applies");
        Assertions.assertNull(data.getErrorCorrectionLevel());
    }

    @Test
    public void testDefaultMarginPerType() {
        Assertions.assertEquals(4, BarcodeType.QR_CODE.getDefaultMargin(), "ISO/IEC 18004 quiet zone");
        Assertions.assertEquals(10, BarcodeType.CODE_128.getDefaultMargin());
        Assertions.assertEquals(10, BarcodeType.CODE_39.getDefaultMargin());
        Assertions.assertEquals(10, BarcodeType.EAN_13.getDefaultMargin());
        Assertions.assertEquals(1, BarcodeType.DATA_MATRIX.getDefaultMargin(), "ISO/IEC 16022 quiet zone");
        Assertions.assertTrue(BarcodeType.QR_CODE.supportsLogo());
        Assertions.assertFalse(BarcodeType.DATA_MATRIX.supportsLogo());
        Assertions.assertFalse(BarcodeType.CODE_128.supportsLogo());
    }

    @Test
    public void testDefaultSizeIsWrittenIntoStyle() {
        BarcodeRenderData data = BarcodeRenderData.ofQrCode("https://deepoove.com").build();
        data.readPictureData();
        Assertions.assertEquals(120, data.getBarcodeStyle().getWidth());
        Assertions.assertEquals(120, data.getBarcodeStyle().getHeight());
        Assertions.assertEquals(WidthScalePattern.NONE, data.getBarcodeStyle().getScalePattern());
    }

    @Test
    public void testExplicitSizeIsNotOverridden() {
        BarcodeRenderData data = BarcodeRenderData.ofCode128("SF1029384756").size(300, 90).build();
        data.readPictureData();
        Assertions.assertEquals(300, data.getBarcodeStyle().getWidth());
        Assertions.assertEquals(90, data.getBarcodeStyle().getHeight());
    }

    @Test
    public void testBuildAndCreateReturnSameInstance() {
        BarcodeBuilder builder = BarcodeRenderData.ofCode39("ASSET-2025-098");
        BarcodeRenderData built = builder.build();
        Assertions.assertSame(built, builder.create());
    }

    @Test
    public void testDocumentedQrCodeChain() throws Exception {
        try (InputStream logo = getClass().getResourceAsStream("/barcode/logo.png")) {
            BarcodeRenderData data = BarcodeRenderData.ofQrCode("https://deepoove.com/poi-tl")
                    .size(120, 120)
                    .logo(logo)
                    .errorCorrectionLevel(ErrorCorrectionLevel.H)
                    .build();
            Assertions.assertEquals(ErrorCorrectionLevel.H, data.getErrorCorrectionLevel());
            Assertions.assertNotNull(data.getLogo());
            Assertions.assertTrue(data.readPictureData().length > 0);
        }
    }

    @Test
    public void testBarcodesFacade() {
        Assertions.assertEquals(BarcodeType.QR_CODE, Barcodes.ofQrCode("a").build().getBarcodeType());
        Assertions.assertEquals(BarcodeType.CODE_128, Barcodes.ofCode128("a").build().getBarcodeType());
        Assertions.assertEquals(BarcodeType.CODE_39, Barcodes.ofCode39("A").build().getBarcodeType());
        Assertions.assertEquals(BarcodeType.DATA_MATRIX, Barcodes.ofDataMatrix("a").build().getBarcodeType());
        Assertions.assertEquals(BarcodeType.EAN_13, Barcodes.ofEan13("978711118777").build().getBarcodeType());
    }

    @Test
    public void testLogoIsReadEagerlySoTheStreamCanBeClosed() throws Exception {
        File logo = new File("src/test/resources/barcode/logo.png");
        BarcodeRenderData data;
        try (InputStream in = new FileInputStream(logo)) {
            data = BarcodeRenderData.ofQrCode("https://deepoove.com/poi-tl").size(150, 150).logo(in).build();
        }
        // the stream is closed at this point, rendering must still work
        Assertions.assertTrue(data.readPictureData().length > 0);
    }

    @Test
    public void testLogoRatioIsClamped() {
        BarcodeLogo logo = new BarcodeLogo(new byte[] { 1, 2, 3 });
        logo.setRatio(0.9);
        Assertions.assertEquals(BarcodeLogo.MAX_RATIO, logo.getRatio(), 0.0001);
        logo.setRatio(-1);
        Assertions.assertTrue(logo.getRatio() > 0 && logo.getRatio() <= BarcodeLogo.MAX_RATIO);
    }

    @Test
    public void testStyleRepairedWhenPlainPictureStyleIsSet() {
        BarcodeRenderData data = BarcodeRenderData.ofQrCode("https://deepoove.com").build();
        PictureStyle plain = new PictureStyle();
        plain.setWidth(200);
        plain.setHeight(100);
        data.setPictureStyle(plain);
        BarcodeStyle style = data.getBarcodeStyle();
        Assertions.assertEquals(200, style.getWidth());
        Assertions.assertEquals(100, style.getHeight());
    }

    @Test
    public void testSerializableWithLogo() throws Exception {
        BarcodeRenderData data = BarcodeRenderData.ofQrCode("https://deepoove.com/poi-tl")
                .size(140, 140)
                .logo(getClass().getResourceAsStream("/barcode/logo.png"))
                .build();
        data.readPictureData();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            out.writeObject(data);
        }
        BarcodeRenderData copy;
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()))) {
            copy = (BarcodeRenderData) in.readObject();
        }
        Assertions.assertEquals(data.getContent(), copy.getContent());
        Assertions.assertNotNull(copy.getLogo());
        // the generated bytes are a transient cache, they must be rebuilt
        Assertions.assertTrue(copy.readPictureData().length > 0);
    }

    @Test
    public void testEmptyContentFailsAtGenerationNotAtBuild() {
        BarcodeRenderData data = BarcodeRenderData.ofQrCode("").build();
        Assertions.assertThrows(IllegalArgumentException.class, data::readPictureData);
    }

}
