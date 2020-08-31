/*
 * Copyright 2014-2020 Sayi
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
package com.deepoove.poi.data.style;

import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

public class CellStyle {

    private String backgroundColor;
    /**
     * vertical align in cell
     */
    private XWPFVertAlign vertAlign;
    /**
     * default paragraph style for all paragraphs in the current cell
     */
    private ParagraphStyle defaultParagraphStyle;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public XWPFVertAlign getVertAlign() {
        return vertAlign;
    }

    public void setVertAlign(XWPFVertAlign align) {
        this.vertAlign = align;
    }

    public ParagraphStyle getDefaultParagraphStyle() {
        return defaultParagraphStyle;
    }

    public void setDefaultParagraphStyle(ParagraphStyle defaultParagraphStyle) {
        this.defaultParagraphStyle = defaultParagraphStyle;
    }

}
