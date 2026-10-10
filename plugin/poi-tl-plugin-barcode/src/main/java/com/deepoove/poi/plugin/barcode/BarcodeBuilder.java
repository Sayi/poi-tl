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

import com.deepoove.poi.data.RenderDataBuilder;
import com.deepoove.poi.data.style.PictureStyle.PictureAlign;
import com.deepoove.poi.util.UnitUtils;
import com.deepoove.poi.xwpf.WidthScalePattern;

/**
 * Fluent builder for barcode render data. It offers the API shared by all
 * barcode types, see {@link QrCodeBuilder} for the qrcode specific API.
 *
 * @author Sayi
 */
public class BarcodeBuilder implements RenderDataBuilder<BarcodeRenderData> {

    protected final BarcodeRenderData data;

    public BarcodeBuilder(BarcodeRenderData data) {
        this.data = data;
    }

    public BarcodeBuilder size(int width, int height) {
        BarcodeStyle style = data.getBarcodeStyle();
        style.setWidth(width);
        style.setHeight(height);
        style.setScalePattern(WidthScalePattern.NONE);
        return this;
    }

    /**
     * Square size, handy for qrcode and data matrix.
     */
    public BarcodeBuilder size(int size) {
        return size(size, size);
    }

    public BarcodeBuilder sizeInCm(double widthCm, double heightCm) {
        return size(UnitUtils.cm2Pixel(widthCm), UnitUtils.cm2Pixel(heightCm));
    }

    /**
     * Quiet zone in modules, {@code null} keeps the default of the barcode type.
     * See {@link BarcodeType} for the per writer unit semantics.
     */
    public BarcodeBuilder margin(int margin) {
        data.getBarcodeStyle().setMargin(margin);
        return this;
    }

    public BarcodeBuilder color(Color foreground, Color background) {
        BarcodeStyle style = data.getBarcodeStyle();
        style.setForegroundColor(foreground);
        style.setBackgroundColor(background);
        return this;
    }

    /**
     * Whether to draw the human readable text below the symbol. Only takes effect on
     * one dimensional barcodes.
     */
    public BarcodeBuilder showText(boolean showText) {
        data.getBarcodeStyle().setShowText(showText);
        return this;
    }

    public BarcodeBuilder textFont(Font textFont) {
        data.getBarcodeStyle().setTextFont(textFont);
        return this;
    }

    public BarcodeBuilder textMargin(int textMargin) {
        data.getBarcodeStyle().setTextMargin(textMargin);
        return this;
    }

    public BarcodeBuilder left() {
        data.getBarcodeStyle().setAlign(PictureAlign.LEFT);
        return this;
    }

    public BarcodeBuilder center() {
        data.getBarcodeStyle().setAlign(PictureAlign.CENTER);
        return this;
    }

    public BarcodeBuilder right() {
        data.getBarcodeStyle().setAlign(PictureAlign.RIGHT);
        return this;
    }

    /**
     * The text rendered when the barcode can not be generated.
     */
    public BarcodeBuilder altMeta(String altMeta) {
        data.setAltMeta(altMeta);
        return this;
    }

    public BarcodeRenderData build() {
        return data;
    }

    @Override
    public BarcodeRenderData create() {
        return build();
    }

}
