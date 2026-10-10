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

import com.deepoove.poi.data.style.PictureStyle;

/**
 * Style of a barcode. Width, height, alignment and scale pattern are inherited
 * from {@link PictureStyle}, everything else is barcode specific.
 *
 * @author Sayi
 */
public class BarcodeStyle extends PictureStyle {

    private static final long serialVersionUID = 1L;

    /**
     * Quiet zone. {@code null} means the default of {@link BarcodeType}. The unit is
     * module, but the "each side" or "both sides" semantics depends on the writer,
     * see {@link BarcodeType}.
     */
    private Integer margin;

    private Color foregroundColor = Color.BLACK;

    private Color backgroundColor = Color.WHITE;

    /**
     * Whether the human readable text is drawn below the symbol. {@code null} means
     * the default of {@link BarcodeType}, and it is ignored by two dimensional
     * symbols.
     */
    private Boolean showText;

    private Font textFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

    /**
     * Gap between the bars and the human readable text, in pixel.
     */
    private int textMargin = 3;

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Boolean getShowText() {
        return showText;
    }

    public void setShowText(Boolean showText) {
        this.showText = showText;
    }

    public Font getTextFont() {
        return textFont;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public int getTextMargin() {
        return textMargin;
    }

    public void setTextMargin(int textMargin) {
        this.textMargin = textMargin;
    }

}
