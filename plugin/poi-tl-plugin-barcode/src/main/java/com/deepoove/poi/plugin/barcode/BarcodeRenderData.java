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

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.plugin.barcode.generator.ZxingBarcodeGenerator;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Barcode and qrcode render data.
 * <p>
 * It extends {@link PictureRenderData} on purpose: a barcode in a docx is an
 * OpenXML drawing picture, so the whole picture pipeline (relationship id, EMU,
 * paragraph alignment, page fit) is reused instead of being reimplemented here.
 * As a consequence the data can be rendered by the dedicated barcode policy
 * {@code {{%tag}}} as well as by the built in picture policy {@code {{@tag}}}
 * without any extra configuration.
 *
 * @author Sayi
 */
public class BarcodeRenderData extends PictureRenderData {

    private static final long serialVersionUID = 1L;

    private final String content;

    private final BarcodeType barcodeType;

    /**
     * {@code null} means "not set by the caller", the generator then applies
     * {@link ErrorCorrectionLevel#M}, or {@link ErrorCorrectionLevel#H} when a logo
     * is embedded.
     */
    private ErrorCorrectionLevel errorCorrectionLevel;

    private BarcodeLogo logo;

    /**
     * Generated lazily by {@link #readPictureData()}. Not serialized on purpose,
     * it is just a cache.
     */
    private transient volatile byte[] cachedBytes;

    BarcodeRenderData(String content, BarcodeType barcodeType) {
        this.content = content;
        this.barcodeType = barcodeType;
        this.pictureType = PictureType.PNG;
        this.pictureStyle = new BarcodeStyle();
    }

    public static QrCodeBuilder ofQrCode(String content) {
        return new QrCodeBuilder(new BarcodeRenderData(content, BarcodeType.QR_CODE));
    }

    public static BarcodeBuilder ofCode128(String content) {
        return new BarcodeBuilder(new BarcodeRenderData(content, BarcodeType.CODE_128));
    }

    public static BarcodeBuilder ofCode39(String content) {
        return new BarcodeBuilder(new BarcodeRenderData(content, BarcodeType.CODE_39));
    }

    public static BarcodeBuilder ofDataMatrix(String content) {
        return new BarcodeBuilder(new BarcodeRenderData(content, BarcodeType.DATA_MATRIX));
    }

    public static BarcodeBuilder ofEan13(String content) {
        return new BarcodeBuilder(new BarcodeRenderData(content, BarcodeType.EAN_13));
    }

    /**
     * The barcode style. If the inherited picture style was replaced by a plain
     * {@link PictureStyle}, the geometry is carried over into a fresh
     * {@link BarcodeStyle} so that the cast can never fail at generation time.
     */
    public BarcodeStyle getBarcodeStyle() {
        PictureStyle style = getPictureStyle();
        if (style instanceof BarcodeStyle) {
            return (BarcodeStyle) style;
        }
        BarcodeStyle barcodeStyle = new BarcodeStyle();
        if (null != style) {
            barcodeStyle.setWidth(style.getWidth());
            barcodeStyle.setHeight(style.getHeight());
            barcodeStyle.setAlign(style.getAlign());
            barcodeStyle.setScalePattern(style.getScalePattern());
            barcodeStyle.setSvgScale(style.getSvgScale());
        }
        setPictureStyle(barcodeStyle);
        return barcodeStyle;
    }

    @Override
    public byte[] readPictureData() {
        byte[] bytes = cachedBytes;
        if (null != bytes) {
            return bytes;
        }
        applyDefaultSize(getBarcodeStyle());
        bytes = ZxingBarcodeGenerator.toPng(this);
        cachedBytes = bytes;
        return bytes;
    }

    /**
     * The symbol must always carry an explicit size, otherwise the picture renderer
     * would fall back to the raw pixel size of the generated PNG.
     */
    private void applyDefaultSize(BarcodeStyle style) {
        if (style.getWidth() <= 0 || style.getHeight() <= 0) {
            if (style.getWidth() <= 0) {
                style.setWidth(barcodeType.getDefaultWidth());
            }
            if (style.getHeight() <= 0) {
                style.setHeight(barcodeType.getDefaultHeight());
            }
            style.setScalePattern(WidthScalePattern.NONE);
        }
    }

    public String getContent() {
        return content;
    }

    public BarcodeType getBarcodeType() {
        return barcodeType;
    }

    public ErrorCorrectionLevel getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public void setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
    }

    public BarcodeLogo getLogo() {
        return logo;
    }

    public void setLogo(BarcodeLogo logo) {
        this.logo = logo;
    }

}
