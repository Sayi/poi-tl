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
package com.deepoove.poi.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.SimpleValue;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHighlight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPBdr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTUnderline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHexColorAuto;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHexColorRGB;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;

import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.Style.StyleBuilder;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.deepoove.poi.xwpf.XWPFHighlightColor;
import com.deepoove.poi.xwpf.XWPFShadingPattern;

/**
 * set style for run, paragraph, table...
 * 
 * @author Sayi
 */
public final class StyleUtils {

    /**
     * set run style by style
     * 
     * @param run
     * @param style
     */
    public static void styleRun(XWPFRun run, Style style) {
        if (null == run || null == style) return;
        CTRPr pr = getRunProperties(run);
        String color = style.getColor();
        if (StringUtils.isNotBlank(color)) {
            // run.setColor(color);
            // issue 326
            CTColor ctColor = pr.isSetColor() ? pr.getColor() : pr.addNewColor();
            ctColor.setVal(color);
            if (ctColor.isSetThemeColor()) ctColor.unsetThemeColor();
        }
        double fontSize = style.getFontSize();
        if (0 != fontSize && -1 != fontSize) {
            BigDecimal bd = BigDecimal.valueOf(fontSize);
            CTHpsMeasure ctSize = pr.isSetSz() ? pr.getSz() : pr.addNewSz();
            ctSize.setVal(bd.multiply(BigDecimal.valueOf(2)).setScale(0, RoundingMode.HALF_UP).toBigInteger());
        }
        String fontFamily = style.getFontFamily();
        if (StringUtils.isNotBlank(fontFamily)) {
            run.setFontFamily(fontFamily);
            CTFonts fonts = pr.isSetRFonts() ? pr.getRFonts() : pr.addNewRFonts();
            fonts.setAscii(fontFamily);
            fonts.setHAnsi(fontFamily);
            fonts.setCs(fontFamily);
            fonts.setEastAsia(fontFamily);
        }
        XWPFHighlightColor highlightColor = style.getHighlightColor();
        if (null != highlightColor) {
            CTHighlight highlight = pr.isSetHighlight() ? pr.getHighlight() : pr.addNewHighlight();
            highlight.setVal(STHighlightColor.Enum.forInt(highlightColor.getValue()));
        }
        Boolean bold = style.isBold();
        if (null != bold) run.setBold(bold);
        Boolean italic = style.isItalic();
        if (null != italic) run.setItalic(italic);
        Boolean strike = style.isStrike();
        if (null != strike) run.setStrikeThrough(strike);
        UnderlinePatterns underlinePatern = style.getUnderlinePatterns();
        if (null != underlinePatern) {
            run.setUnderline(underlinePatern);
            if (null != style.getUnderlineColor()) {
                run.setUnderlineColor(style.getUnderlineColor());
            }
        }
        int point = style.getCharacterSpacing();
        // in twentieths of a point
        if (0 != point && -1 != point) run.setCharacterSpacing(UnitUtils.point2Twips(point));
        String vertAlign = style.getVertAlign();
        if (StringUtils.isNotBlank(vertAlign)) {
            run.setVerticalAlignment(vertAlign);
        }
    }

    /**
     * set run style by other run
     * 
     * @param dest
     * @param src
     */
    public static void styleRun(XWPFRun dest, XWPFRun src) {
        if (null == dest || null == src) return;
        if (StringUtils.isNotEmpty(src.getStyle())) dest.setStyle(src.getStyle());
        if (Boolean.TRUE.equals(src.isBold())) dest.setBold(src.isBold());
        if (StringUtils.isNotBlank(src.getColor())) dest.setColor(src.getColor());
        if (0 != src.getCharacterSpacing()) dest.setCharacterSpacing(src.getCharacterSpacing());
        if (StringUtils.isNotBlank(src.getFontFamily())) dest.setFontFamily(src.getFontFamily());
        CTRPr pr = src.getCTR().getRPr();
        BigDecimal fontSize = (pr != null && pr.isSetSz())
                ? new BigDecimal(pr.getSz().getVal()).divide(BigDecimal.valueOf(2)).setScale(1, RoundingMode.HALF_UP)
                : null;
        if (null != fontSize) {
            CTRPr destPr = getRunProperties(dest);
            CTHpsMeasure ctSize = destPr.isSetSz() ? destPr.getSz() : destPr.addNewSz();
            ctSize.setVal(fontSize.multiply(BigDecimal.valueOf(2)).setScale(0, RoundingMode.HALF_UP).toBigInteger());
        }
        if (Boolean.TRUE.equals(src.isItalic())) dest.setItalic(src.isItalic());
        if (Boolean.TRUE.equals(src.isStrikeThrough())) dest.setStrikeThrough(src.isStrikeThrough());
        if (UnderlinePatterns.NONE != src.getUnderline()) dest.setUnderline(src.getUnderline());
        if (null != src.getUnderlineColor()) dest.setUnderlineColor(src.getUnderlineColor());
    }

    /**
     * set paragraph style
     * 
     * @param paragraph
     * @param style
     */
    public static void styleParagraph(XWPFParagraph paragraph, ParagraphStyle style) {
        if (null == paragraph || null == style) return;
        stylePpr(paragraph, style);
        styleParaRpr(paragraph, style.getGlyphStyle());
    }

    /**
     * set table style
     * 
     * @param table
     * @param tableStyle
     */
    public static void styleTable(XWPFTable table, TableStyle tableStyle) {
        if (null == table || null == tableStyle) return;

        String width = tableStyle.getWidth();
        int[] colWidths = tableStyle.getColWidths();
        if (tableStyle.getWidthScalePattern() == WidthScalePattern.FIT) {
            int pageWidth = PageTools.pageWidth(table);
            width = String.valueOf(pageWidth);
            if (null != colWidths) {
                int sum = Arrays.stream(colWidths).sum();
                if (sum == 100) {
                    colWidths = Arrays.stream(colWidths).map(w -> w * pageWidth / 100).toArray();
                }
            }
        }
        TableTools.setWidth(table, width, colWidths);

        TableTools.setBorder(table::setLeftBorder, tableStyle.getLeftBorder());
        TableTools.setBorder(table::setRightBorder, tableStyle.getRightBorder());
        TableTools.setBorder(table::setTopBorder, tableStyle.getTopBorder());
        TableTools.setBorder(table::setBottomBorder, tableStyle.getBottomBorder());
        TableTools.setBorder(table::setInsideHBorder, tableStyle.getInsideHBorder());
        TableTools.setBorder(table::setInsideVBorder, tableStyle.getInsideVBorder());

        if (null != tableStyle.getAlign()) {
            table.setTableAlignment(tableStyle.getAlign());
        }

        table.setCellMargins(tableStyle.getTopCellMargin(), tableStyle.getLeftCellMargin(),
                tableStyle.getBottomCellMargin(), tableStyle.getRightCellMargin());

    }

    /**
     * set row style
     * 
     * @param row
     * @param rowStyle
     */
    public static void styleTableRow(XWPFTableRow row, RowStyle rowStyle) {
        if (null == row || null == rowStyle) return;
        int height = rowStyle.getHeight();
        if (0 != height) {
            row.setHeight(height);
            CTRow ctRow = row.getCtRow();
            CTTrPr properties = (ctRow.isSetTrPr()) ? ctRow.getTrPr() : ctRow.addNewTrPr();
            CTHeight h = properties.sizeOfTrHeightArray() == 0 ? properties.addNewTrHeight()
                    : properties.getTrHeightArray(0);
            String heightRule = rowStyle.getHeightRule();
            if ("exact".equals(heightRule)) h.setHRule(STHeightRule.EXACT);
            else if ("atleast".equals(heightRule)) h.setHRule(STHeightRule.AT_LEAST);
            else h.setHRule(STHeightRule.AUTO);
        }
    }

    /**
     * set cell style
     * 
     * @param cell
     * @param cellStyle
     */
    public static void styleTableCell(XWPFTableCell cell, CellStyle cellStyle) {
        if (null == cell || null == cellStyle) return;
        if (null != cellStyle.getVertAlign()) {
            cell.setVerticalAlignment(cellStyle.getVertAlign());
        }
        if (null != cellStyle.getBackgroundColor()) {
            CTTc ctTc = cell.getCTTc();
            CTTcPr pr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
            CTShd shd = pr.isSetShd() ? pr.getShd() : pr.addNewShd();
            XWPFShadingPattern shadingPattern = cellStyle.getShadingPattern();
            if (null == shadingPattern) {
                shd.setVal(STShd.CLEAR);
            } else {
                shd.setVal(STShd.Enum.forInt(shadingPattern.getValue()));
            }
            shd.setColor("auto");
            shd.setFill(cellStyle.getBackgroundColor());
        }
    }

    /**
     * set w:rPr style
     * 
     * @param pr
     * @param style
     */
    private static void styleParaRpr(CTParaRPr pr, Style style) {
        if (null == pr || null == style) return;
        if (StringUtils.isNotBlank(style.getColor())) {
            CTColor color = pr.isSetColor() ? pr.getColor() : pr.addNewColor();
            color.setVal(style.getColor());
        }

        if (null != style.isItalic()) {
            CTOnOff italic = pr.isSetI() ? pr.getI() : pr.addNewI();
            italic.setVal(style.isItalic() ? STOnOff.TRUE : STOnOff.FALSE);
        }

        if (null != style.isBold()) {
            CTOnOff bold = pr.isSetB() ? pr.getB() : pr.addNewB();
            bold.setVal(style.isBold() ? STOnOff.TRUE : STOnOff.FALSE);
        }

        if (0 != style.getFontSize() && -1 != style.getFontSize()) {
            BigDecimal bd = BigDecimal.valueOf(style.getFontSize());
            CTHpsMeasure ctSize = pr.isSetSz() ? pr.getSz() : pr.addNewSz();
            ctSize.setVal(bd.multiply(BigDecimal.valueOf(2)).setScale(0, RoundingMode.HALF_UP).toBigInteger());
        }

        if (null != style.isStrike()) {
            CTOnOff strike = pr.isSetStrike() ? pr.getStrike() : pr.addNewStrike();
            strike.setVal(style.isStrike() ? STOnOff.TRUE : STOnOff.FALSE);
        }

        UnderlinePatterns underlinePatern = style.getUnderlinePatterns();
        if (null != underlinePatern) {
            CTUnderline underline = pr.isSetU() ? pr.getU() : pr.addNewU();
            underline.setVal(STUnderline.Enum.forInt(underlinePatern.getValue()));
            if (null != style.getUnderlineColor()) {
                String color = style.getUnderlineColor();
                SimpleValue svColor = null;
                if (color.equals("auto")) {
                    STHexColorAuto hexColor = STHexColorAuto.Factory.newInstance();
                    hexColor.set(STHexColorAuto.Enum.forString(color));
                    svColor = (SimpleValue) hexColor;
                } else {
                    STHexColorRGB rgbColor = STHexColorRGB.Factory.newInstance();
                    rgbColor.setStringValue(color);
                    svColor = (SimpleValue) rgbColor;
                }
                underline.setColor(svColor);
            }
        }

        if (StringUtils.isNotBlank(style.getFontFamily())) {
            CTFonts fonts = pr.isSetRFonts() ? pr.getRFonts() : pr.addNewRFonts();
            String fontFamily = style.getFontFamily();
            fonts.setAscii(fontFamily);
            if (!fonts.isSetHAnsi()) {
                fonts.setHAnsi(fontFamily);
            }
            if (!fonts.isSetCs()) {
                fonts.setCs(fontFamily);
            }
            if (!fonts.isSetEastAsia()) {
                fonts.setEastAsia(fontFamily);
            }
        }
    }

    public static void styleParaRpr(XWPFParagraph paragraph, Style style) {
        if (null == paragraph || null == style) return;
        CTP ctp = paragraph.getCTP();
        CTPPr pPr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
        CTParaRPr pr = pPr.isSetRPr() ? pPr.getRPr() : pPr.addNewRPr();
        StyleUtils.styleParaRpr(pr, style);
    }

    public static void stylePpr(XWPFParagraph paragraph, ParagraphStyle style) {
        if (null == paragraph || null == style) return;
        if (null != style.getAlign()) {
            paragraph.setAlignment(style.getAlign());
        }

        if (0 != style.getSpacing()) {
            paragraph.setSpacingBetween(style.getSpacing(),
                    null == style.getSpacingRule() ? LineSpacingRule.AUTO : style.getSpacingRule());
        }
        if (0 != style.getSpacingBeforeLines()) {
            paragraph.setSpacingBeforeLines(
                    new BigInteger(String.valueOf(Math.round(style.getSpacingBeforeLines() * 100.0))).intValue());
        }
        if (0 != style.getSpacingAfterLines()) {
            paragraph.setSpacingAfterLines(
                    new BigInteger(String.valueOf(Math.round(style.getSpacingAfterLines() * 100.0))).intValue());
        }

        CTP ctp = paragraph.getCTP();
        CTPPr pr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
        CTInd indent = pr.isSetInd() ? pr.getInd() : pr.addNewInd();
        if (0 != style.getIndentLeftChars()) {
            BigInteger bi = new BigInteger(String.valueOf(Math.round(style.getIndentLeftChars() * 100.0)));
            indent.setLeftChars(bi);
            if (indent.isSetLeft()) indent.unsetLeft();
        }
        if (0 != style.getIndentRightChars()) {
            BigInteger bi = new BigInteger(String.valueOf(Math.round(style.getIndentRightChars() * 100.0)));
            indent.setRightChars(bi);
            if (indent.isSetRight()) indent.unsetRight();
        }
        if (0 != style.getIndentHangingChars()) {
            BigInteger bi = new BigInteger(String.valueOf(Math.round(style.getIndentHangingChars() * 100.0)));
            indent.setHangingChars(bi);
            if (indent.isSetHanging()) indent.unsetHanging();
        }
        if (0 != style.getIndentFirstLineChars()) {
            BigInteger bi = new BigInteger(String.valueOf(Math.round(style.getIndentFirstLineChars() * 100.0)));
            indent.setFirstLineChars(bi);
            if (indent.isSetFirstLine()) indent.unsetFirstLine();
        }

        CTPBdr ct = pr.isSetPBdr() ? pr.getPBdr() : pr.addNewPBdr();
        BorderStyle leftBorder = style.getLeftBorder();
        if (null != leftBorder) {
            CTBorder b = ct.isSetLeft() ? ct.getLeft() : ct.addNewLeft();
            b.setVal(STBorder.Enum.forString(leftBorder.getType().toString().toLowerCase()));
            b.setSz(BigInteger.valueOf(leftBorder.getSize()));
            b.setSpace(BigInteger.valueOf(4));
            b.setColor(leftBorder.getColor());
        }

        if (null != style.getBackgroundColor()) {
            CTShd shd = pr.isSetShd() ? pr.getShd() : pr.addNewShd();
            XWPFShadingPattern shadingPattern = style.getShadingPattern();
            if (null == shadingPattern) {
                shd.setVal(STShd.CLEAR);
            } else {
                shd.setVal(STShd.Enum.forInt(shadingPattern.getValue()));
            }
            shd.setColor("auto");
            shd.setFill(style.getBackgroundColor());
        }

        if (null != style.getStyleId()) {
            paragraph.setStyle(style.getStyleId());
        }

        if (null != style.getKeepLines()) {
            CTOnOff ctKeepLines = pr.isSetKeepLines() ? pr.getKeepLines() : pr.addNewKeepLines();
            ctKeepLines.setVal(style.getKeepLines() ? STOnOff.TRUE : STOnOff.FALSE);
        }
        if (null != style.getKeepNext()) {
            paragraph.setKeepNext(style.getKeepNext());
        }
        if (null != style.getPageBreakBefore()) {
            paragraph.setPageBreak(style.getPageBreakBefore());
        }
        if (null != style.getWidowControl()) {
            CTOnOff ctWC = pr.isSetWidowControl() ? pr.getWidowControl() : pr.addNewWidowControl();
            ctWC.setVal(style.getWidowControl() ? STOnOff.TRUE : STOnOff.FALSE);
        }
        if (null != style.getWordWrap()) {
//            paragraph.setWordWrapped(style.getWordWrap());
            CTOnOff ctWW = pr.isSetWordWrap() ? pr.getWordWrap() : pr.addNewWordWrap();
            ctWW.setVal(style.getWordWrap() ? STOnOff.X_0 : STOnOff.X_1);
        }

        if (-1 != style.getNumId()) {
            paragraph.setNumID(BigInteger.valueOf(style.getNumId()));
        }
        if (-1 != style.getLvl()) {
            paragraph.setNumILvl(BigInteger.valueOf(style.getLvl()));
        }
    }

    public static Style retriveStyle(XWPFRun run) {
        if (null == run) return null;
        StyleBuilder builder = Style.builder().buildColor(run.getColor()).buildFontFamily(run.getFontFamily())
                .buildFontSize(run.getFontSize());
        if (run.isBold()) builder.buildBold();
        if (run.isItalic()) builder.buildItalic();
        if (run.isStrikeThrough()) builder.buildStrike();
        return builder.build();
    }

    private static CTRPr getRunProperties(XWPFRun run) {
        return run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
    }

}
