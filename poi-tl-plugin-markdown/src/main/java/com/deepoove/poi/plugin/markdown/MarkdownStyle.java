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
package com.deepoove.poi.plugin.markdown;

import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;

import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.highlight.HighlightStyle;

/**
 * @author Sayi
 */
public class MarkdownStyle {

    private Style inlineCodeStyle;
    private HighlightStyle highlightStyle;
    private ParagraphStyle quoteStyle;
    private BorderStyle tableBorderStyle;
    private RowStyle tableHeaderStyle;

    private boolean showHeaderNumber;
    private String imagesDir = "";

    public static MarkdownStyle newStyle() {
        MarkdownStyle style = new MarkdownStyle();
        style.setInlineCodeStyle(Style.builder().buildColor("f44336").buildFontFamily("Monaco").build());
        style.setHighlightStyle(HighlightStyle.builder()
//              .withShowLine(true)
                .withTheme("zenburn")
                .withFontFamily("Consolas")
                .withFontSize(9.0f)
                .build());
        style.setQuoteStyle(ParagraphStyle.builder()
                .withIndentLeftChars(1.0f)
                .withLeftBorder(BorderStyle.builder()
                        .withColor("70ad47")
                        .withSize(48)
                        .withSpace(8)
                        .withType(XWPFBorderType.SINGLE)
                        .build())
                .build());

        style.setTableBorderStyle(
                BorderStyle.builder().withColor("BFBFBF").withSize(1).withType(XWPFBorderType.SINGLE).build());

        // table header style
        RowStyle headerStyle = new RowStyle();
        CellStyle cellStyle = new CellStyle();
        cellStyle.setBackgroundColor("5b9bd5");
        Style textStyle = new Style();
        textStyle.setColor("ffffff");
        cellStyle.setDefaultParagraphStyle(ParagraphStyle.builder().withDefaultTextStyle(textStyle).build());
        headerStyle.setDefaultCellStyle(cellStyle);
        style.setTableHeaderStyle(headerStyle);
        return style;
    }

    public Style getInlineCodeStyle() {
        return inlineCodeStyle;
    }

    public void setInlineCodeStyle(Style inlineCodeStyle) {
        this.inlineCodeStyle = inlineCodeStyle;
    }

    public HighlightStyle getHighlightStyle() {
        return highlightStyle;
    }

    public void setHighlightStyle(HighlightStyle highlightStyle) {
        this.highlightStyle = highlightStyle;
    }

    public ParagraphStyle getQuoteStyle() {
        return quoteStyle;
    }

    public void setQuoteStyle(ParagraphStyle quoteStyle) {
        this.quoteStyle = quoteStyle;
    }

    public BorderStyle getTableBorderStyle() {
        return tableBorderStyle;
    }

    public void setTableBorderStyle(BorderStyle tableBorderStyle) {
        this.tableBorderStyle = tableBorderStyle;
    }

    public RowStyle getTableHeaderStyle() {
        return tableHeaderStyle;
    }

    public void setTableHeaderStyle(RowStyle tableHeaderStyle) {
        this.tableHeaderStyle = tableHeaderStyle;
    }

    public boolean isShowHeaderNumber() {
        return showHeaderNumber;
    }

    public void setShowHeaderNumber(boolean showHeaderNumber) {
        this.showHeaderNumber = showHeaderNumber;
    }

    public String getImagesDir() {
        return imagesDir;
    }

    public void setImagesDir(String imagesDir) {
        this.imagesDir = imagesDir;
    }

}
