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
import java.awt.image.BufferedImage;
import java.io.InputStream;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Fluent builder for QR Code, it adds the center logo and the error correction
 * level on top of {@link BarcodeBuilder}.
 * <p>
 * Every inherited fluent method is overridden to return {@code QrCodeBuilder} so
 * that the documented chain
 * {@code ofQrCode(x).size(w, h).logo(in).errorCorrectionLevel(H).build()} compiles
 * without any cast.
 *
 * @author Sayi
 */
public class QrCodeBuilder extends BarcodeBuilder {

    public QrCodeBuilder(BarcodeRenderData data) {
        super(data);
    }

    /**
     * Embed a logo into the center of the qrcode. The stream is read and closed
     * here, the caller does not need to keep it open until rendering.
     */
    public QrCodeBuilder logo(InputStream inputStream) {
        data.setLogo(new BarcodeLogo(inputStream));
        return this;
    }

    public QrCodeBuilder logo(byte[] imageBytes) {
        data.setLogo(new BarcodeLogo(imageBytes));
        return this;
    }

    public QrCodeBuilder logo(BufferedImage image) {
        data.setLogo(new BarcodeLogo(image));
        return this;
    }

    public QrCodeBuilder logo(BarcodeLogo logo) {
        data.setLogo(logo);
        return this;
    }

    /**
     * Explicit error correction level. When it is not set, a qrcode with a logo is
     * upgraded to {@link ErrorCorrectionLevel#H} automatically, otherwise
     * {@link ErrorCorrectionLevel#M} is used.
     */
    public QrCodeBuilder errorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        data.setErrorCorrectionLevel(errorCorrectionLevel);
        return this;
    }

    @Override
    public QrCodeBuilder size(int width, int height) {
        super.size(width, height);
        return this;
    }

    @Override
    public QrCodeBuilder size(int size) {
        super.size(size);
        return this;
    }

    @Override
    public QrCodeBuilder sizeInCm(double widthCm, double heightCm) {
        super.sizeInCm(widthCm, heightCm);
        return this;
    }

    @Override
    public QrCodeBuilder margin(int margin) {
        super.margin(margin);
        return this;
    }

    @Override
    public QrCodeBuilder color(Color foreground, Color background) {
        super.color(foreground, background);
        return this;
    }

    @Override
    public QrCodeBuilder showText(boolean showText) {
        super.showText(showText);
        return this;
    }

    @Override
    public QrCodeBuilder textFont(Font textFont) {
        super.textFont(textFont);
        return this;
    }

    @Override
    public QrCodeBuilder textMargin(int textMargin) {
        super.textMargin(textMargin);
        return this;
    }

    @Override
    public QrCodeBuilder left() {
        super.left();
        return this;
    }

    @Override
    public QrCodeBuilder center() {
        super.center();
        return this;
    }

    @Override
    public QrCodeBuilder right() {
        super.right();
        return this;
    }

    @Override
    public QrCodeBuilder altMeta(String altMeta) {
        super.altMeta(altMeta);
        return this;
    }

}
