/*
 * Copyright 2014-2021 Sayi
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

import java.io.Serializable;

import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;

import com.deepoove.poi.xwpf.XWPFShadingPattern;

public class CellStyle implements Serializable {

    private static final long serialVersionUID = 1L;
    private String backgroundColor;
    private XWPFShadingPattern shadingPattern;
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

    public XWPFShadingPattern getShadingPattern() {
        return shadingPattern;
    }

    public void setShadingPattern(XWPFShadingPattern shadingPattern) {
        this.shadingPattern = shadingPattern;
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
