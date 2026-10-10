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

import com.deepoove.poi.converter.ToRenderDataConverter;

/**
 * Convert the tag data to {@link BarcodeRenderData}.
 * <p>
 * A {@link CharSequence}, {@link Number} or {@link Character} is rendered as a
 * QR Code, so that {@code {{%qrcode}}} with a plain string works. The barcode
 * type is deliberately <b>not</b> sniffed from the content, one dimensional
 * symbols must be requested explicitly with
 * {@link BarcodeRenderData#ofCode128(String)} and friends. Any other type is
 * rejected instead of being converted through {@code toString()}, which would
 * silently produce a qrcode whose content is something like {@code [B@1f2a3c}.
 *
 * @author Sayi
 */
public class ObjectToBarcodeRenderDataConverter implements ToRenderDataConverter<Object, BarcodeRenderData> {

    @Override
    public BarcodeRenderData convert(Object source) {
        if (null == source) {
            return null;
        }
        if (source instanceof BarcodeRenderData) {
            return (BarcodeRenderData) source;
        }
        if (source instanceof CharSequence || source instanceof Number || source instanceof Character) {
            return BarcodeRenderData.ofQrCode(source.toString()).build();
        }
        throw new IllegalArgumentException("Unsupported barcode data type: " + source.getClass().getName()
                + ", only String, Number, Character and BarcodeRenderData are supported");
    }

}
