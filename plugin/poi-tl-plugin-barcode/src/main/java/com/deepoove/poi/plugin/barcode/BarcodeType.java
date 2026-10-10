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

import com.google.zxing.BarcodeFormat;

/**
 * Supported barcode and qrcode types.
 * <p>
 * The default margin is expressed in <b>modules</b>, but the unit semantics are
 * inherited from ZXing and therefore differ per writer:
 * <ul>
 * <li>{@link #QR_CODE}: ZXing applies the margin on <b>each side</b>
 * (default 4, as required by ISO/IEC 18004)</li>
 * <li>{@link #CODE_128}, {@link #CODE_39}, {@link #EAN_13}: ZXing applies the
 * margin as the <b>sum of both sides</b> (default 10, as required by the one
 * dimensional symbologies)</li>
 * <li>{@link #DATA_MATRIX}: ZXing's DataMatrixWriter ignores the margin hint,
 * so the plugin applies {@code margin} on <b>each side</b> by itself</li>
 * </ul>
 *
 * @author Sayi
 */
public enum BarcodeType {

    /**
     * QR Code, ISO/IEC 18004. Supports an embedded center logo.
     */
    QR_CODE(BarcodeFormat.QR_CODE, true, 120, 120, 4),

    /**
     * Code 128, ISO/IEC 15417. Full ASCII, high density.
     */
    CODE_128(BarcodeFormat.CODE_128, false, 200, 60, 10),

    /**
     * Code 39, ISO/IEC 16388. Industrial and asset management.
     */
    CODE_39(BarcodeFormat.CODE_39, false, 200, 60, 10),

    /**
     * Data Matrix, ISO/IEC 16022. Tiny footprint for parts and UDI.
     */
    DATA_MATRIX(BarcodeFormat.DATA_MATRIX, true, 80, 80, 1),

    /**
     * EAN-13, ISO/IEC 15420. Global retail and book numbering.
     */
    EAN_13(BarcodeFormat.EAN_13, false, 160, 60, 10);

    private final BarcodeFormat zxingFormat;
    private final boolean twoDimensional;
    private final int defaultWidth;
    private final int defaultHeight;
    private final int defaultMargin;

    BarcodeType(BarcodeFormat zxingFormat, boolean twoDimensional, int defaultWidth, int defaultHeight,
            int defaultMargin) {
        this.zxingFormat = zxingFormat;
        this.twoDimensional = twoDimensional;
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.defaultMargin = defaultMargin;
    }

    public BarcodeFormat getZxingFormat() {
        return zxingFormat;
    }

    public boolean isTwoDimensional() {
        return twoDimensional;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }

    /**
     * Default quiet zone in modules. See class comment for the unit semantics.
     */
    public int getDefaultMargin() {
        return defaultMargin;
    }

    /**
     * Whether a center logo can be embedded. Only {@link #QR_CODE} is supported,
     * because the Reed-Solomon error correction of QR Code can recover the covered
     * modules while the others cannot reliably do so.
     */
    public boolean supportsLogo() {
        return this == QR_CODE;
    }

    /**
     * Whether the human readable text is shown below the symbol by default.
     */
    public boolean isShowTextByDefault() {
        return !twoDimensional;
    }

}
