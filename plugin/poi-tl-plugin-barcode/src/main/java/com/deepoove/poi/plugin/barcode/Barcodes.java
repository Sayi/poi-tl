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

/**
 * Factory method to build {@link BarcodeRenderData} instances, following the
 * {@code Pictures} / {@code Texts} / {@code Tables} naming of poi-tl.
 *
 * @author Sayi
 */
public final class Barcodes {

    private Barcodes() {
    }

    public static QrCodeBuilder ofQrCode(String content) {
        return BarcodeRenderData.ofQrCode(content);
    }

    public static BarcodeBuilder ofCode128(String content) {
        return BarcodeRenderData.ofCode128(content);
    }

    public static BarcodeBuilder ofCode39(String content) {
        return BarcodeRenderData.ofCode39(content);
    }

    public static BarcodeBuilder ofDataMatrix(String content) {
        return BarcodeRenderData.ofDataMatrix(content);
    }

    public static BarcodeBuilder ofEan13(String content) {
        return BarcodeRenderData.ofEan13(content);
    }

}
