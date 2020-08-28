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

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

public class CellStyle {

    private String backgroundColor;
    /**
     * vertical align in cell
     */
    private XWPFVertAlign align;
    /**
     * default horizontal align in paragraph of cell
     */
    private ParagraphAlignment defaultParagraphAlign;

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public XWPFVertAlign getAlign() {
        return align;
    }

    public void setAlign(XWPFVertAlign align) {
        this.align = align;
    }

    public ParagraphAlignment getDefaultParagraphAlign() {
        return defaultParagraphAlign;
    }

    public void setDefaultParagraphAlign(ParagraphAlignment defaultParagraphAlign) {
        this.defaultParagraphAlign = defaultParagraphAlign;
    }

}
