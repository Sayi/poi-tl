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
package com.deepoove.poi.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFRun.FontCharRange;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTUnderline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHexColorAuto;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHexColorRGB;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;

import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.ParagraphStyle.Builder;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.Style.StyleBuilder;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.xwpf.CssRgb;
import com.deepoove.poi.xwpf.XWPFHighlightColor;
import com.deepoove.poi.xwpf.XWPFOnOff;
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
            run.setFontFamily(fontFamily, FontCharRange.eastAsia);
            run.setFontFamily(fontFamily, FontCharRange.ascii);
            run.setFontFamily(fontFamily, FontCharRange.hAnsi);
            run.setFontFamily(fontFamily, FontCharRange.cs);
        }
        String westernFontFamily = style.getWesternFontFamily();
        if (StringUtils.isNotBlank(westernFontFamily)) {
            run.setFontFamily(westernFontFamily, FontCharRange.ascii);
            run.setFontFamily(westernFontFamily, FontCharRange.hAnsi);
            run.setFontFamily(westernFontFamily, FontCharRange.cs);
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
        if (StringUtils.isNotBlank(src.getFontFamily(FontCharRange.ascii)))
            dest.setFontFamily(src.getFontFamily(), FontCharRange.ascii);
        if (StringUtils.isNotBlank(src.getFontFamily(FontCharRange.eastAsia)))
            dest.setFontFamily(src.getFontFamily(), FontCharRange.eastAsia);
        if (StringUtils.isNotBlank(src.getFontFamily(FontCharRange.hAnsi)))
            dest.setFontFamily(src.getFontFamily(), FontCharRange.hAnsi);
        if (StringUtils.isNotBlank(src.getFontFamily(FontCharRange.cs)))
            dest.setFontFamily(src.getFontFamily(), FontCharRange.cs);
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
     * set paragraph style by other run body
     * 
     * @param dest
     * @param src
     */
    public static void styleParagraph(XWPFParagraph dest, IRunBody src) {
        if (null == dest || null == src || !(src instanceof XWPFParagraph)) return;
        XWPFParagraph srcParagraph = (XWPFParagraph) src;
        styleParagraph(dest, retriveParagraphStyle(srcParagraph));
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

        TableTools.setWidth(table, tableStyle.getWidth(), tableStyle.getColWidths());

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

        if (null != tableStyle.getIndentation()) {
            CTTbl ctTbl = table.getCTTbl();
            CTTblPr tPr = (ctTbl.getTblPr() != null) ? ctTbl.getTblPr() : ctTbl.addNewTblPr();
            CTTblWidth tw = tPr.isSetTblInd() ? tPr.getTblInd() : tPr.addNewTblInd();
            tw.setType(STTblWidth.DXA);
            tw.setW(BigInteger.valueOf(UnitUtils.cm2Twips(tableStyle.getIndentation())));
        }

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
        CTRow ctRow = row.getCtRow();
        CTTrPr properties = (ctRow.isSetTrPr()) ? ctRow.getTrPr() : ctRow.addNewTrPr();
        if (0 != height) {
            row.setHeight(height);
            CTHeight h = properties.sizeOfTrHeightArray() == 0 ? properties.addNewTrHeight()
                    : properties.getTrHeightArray(0);
            String heightRule = rowStyle.getHeightRule();
            if ("exact".equals(heightRule)) h.setHRule(STHeightRule.EXACT);
            else if ("atleast".equals(heightRule)) h.setHRule(STHeightRule.AT_LEAST);
            else h.setHRule(STHeightRule.AUTO);
        }

        boolean repeated = rowStyle.isRepeated();
        if (repeated) {
            CTOnOff tblHeader = properties.sizeOfTblHeaderArray() == 0 ? properties.addNewTblHeader()
                    : properties.getTblHeaderArray(0);
            tblHeader.setVal(XWPFOnOff.ON);
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
            italic.setVal(style.isItalic() ? XWPFOnOff.ON : XWPFOnOff.OFF);
        }

        if (null != style.isBold()) {
            CTOnOff bold = pr.isSetB() ? pr.getB() : pr.addNewB();
            bold.setVal(style.isBold() ? XWPFOnOff.ON : XWPFOnOff.OFF);
        }

        if (0 != style.getFontSize() && -1 != style.getFontSize()) {
            BigDecimal bd = BigDecimal.valueOf(style.getFontSize());
            CTHpsMeasure ctSize = pr.isSetSz() ? pr.getSz() : pr.addNewSz();
            ctSize.setVal(bd.multiply(BigDecimal.valueOf(2)).setScale(0, RoundingMode.HALF_UP).toBigInteger());
        }

        if (null != style.isStrike()) {
            CTOnOff strike = pr.isSetStrike() ? pr.getStrike() : pr.addNewStrike();
            strike.setVal(style.isStrike() ? XWPFOnOff.ON : XWPFOnOff.OFF);
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

        CTFonts fonts = pr.isSetRFonts() ? pr.getRFonts() : pr.addNewRFonts();
        String fontFamily = style.getFontFamily();
        if (StringUtils.isNotBlank(fontFamily)) {
            fonts.setEastAsia(fontFamily);
            fonts.setAscii(fontFamily);
            fonts.setHAnsi(fontFamily);
            fonts.setCs(fontFamily);
        }
        String westernFontFamily = style.getWesternFontFamily();
        if (StringUtils.isNotBlank(westernFontFamily)) {
            fonts.setAscii(westernFontFamily);
            fonts.setHAnsi(westernFontFamily);
            fonts.setCs(westernFontFamily);
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

        if (null != style.getSpacing()) {
            paragraph.setSpacingBetween(style.getSpacing(),
                    null == style.getSpacingRule() ? LineSpacingRule.AUTO : style.getSpacingRule());
        }
        if (null != style.getSpacingBeforeLines()) {
            paragraph.setSpacingBeforeLines(
                    new BigInteger(String.valueOf(Math.round(style.getSpacingBeforeLines() * 100.0))).intValue());
        }
        if (null != style.getSpacingAfterLines()) {
            paragraph.setSpacingAfterLines(
                    new BigInteger(String.valueOf(Math.round(style.getSpacingAfterLines() * 100.0))).intValue());
        }
        if (null != style.getSpacingBefore()) {
            paragraph.setSpacingBefore(UnitUtils.point2Twips(style.getSpacingBefore()));
        }
        if (null != style.getSpacingAfter()) {
            paragraph.setSpacingAfter(UnitUtils.point2Twips(style.getSpacingAfter()));
        }

        CTP ctp = paragraph.getCTP();
        CTPPr pr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
        CTInd indent = pr.isSetInd() ? pr.getInd() : pr.addNewInd();
        if (null != style.getIndentLeftChars()) {
            BigInteger bi = new BigInteger(String.valueOf(Math.round(style.getIndentLeftChars() * 100.0)));
            indent.setLeftChars(bi);
            if (indent.isSetLeft()) indent.unsetLeft();
        }
        if (null != style.getIndentRightChars()) {
            BigInteger bi = new BigInteger(String.valueOf(Math.round(style.getIndentRightChars() * 100.0)));
            indent.setRightChars(bi);
            if (indent.isSetRight()) indent.unsetRight();
        }
        if (null != style.getIndentHangingChars()) {
            BigInteger bi = new BigInteger(String.valueOf(Math.round(style.getIndentHangingChars() * 100.0)));
            indent.setHangingChars(bi);
            if (indent.isSetHanging()) indent.unsetHanging();
        }
        if (null != style.getIndentFirstLineChars()) {
            BigInteger bi = new BigInteger(String.valueOf(Math.round(style.getIndentFirstLineChars() * 100.0)));
            indent.setFirstLineChars(bi);
            if (indent.isSetFirstLine()) indent.unsetFirstLine();
        }

        CTPBdr ct = pr.isSetPBdr() ? pr.getPBdr() : pr.addNewPBdr();
        if (null != style.getLeftBorder()) {
            styleCTBorder(ct.isSetLeft() ? ct.getLeft() : ct.addNewLeft(), style.getLeftBorder());
        }
        if (null != style.getTopBorder()) {
            styleCTBorder(ct.isSetTop() ? ct.getTop() : ct.addNewTop(), style.getTopBorder());
        }
        if (null != style.getRightBorder()) {
            styleCTBorder(ct.isSetRight() ? ct.getRight() : ct.addNewRight(), style.getRightBorder());
        }
        if (null != style.getBottomBorder()) {
            styleCTBorder(ct.isSetBottom() ? ct.getBottom() : ct.addNewBottom(), style.getBottomBorder());
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
            ctKeepLines.setVal(style.getKeepLines() ? XWPFOnOff.ON : XWPFOnOff.OFF);
        }
        if (null != style.getKeepNext()) {
            paragraph.setKeepNext(style.getKeepNext());
        }
        if (null != style.getPageBreakBefore()) {
            paragraph.setPageBreak(style.getPageBreakBefore());
        }
        if (null != style.getWidowControl()) {
            CTOnOff ctWC = pr.isSetWidowControl() ? pr.getWidowControl() : pr.addNewWidowControl();
            ctWC.setVal(style.getWidowControl() ? XWPFOnOff.ON : XWPFOnOff.OFF);
        }
        if (null != style.getAllowWordBreak()) {
//            paragraph.setWordWrapped(style.getWordWrap());
            CTOnOff ctWW = pr.isSetWordWrap() ? pr.getWordWrap() : pr.addNewWordWrap();
            ctWW.setVal(style.getAllowWordBreak() ? XWPFOnOff.OFF : XWPFOnOff.ON);
        }

        if (null != style.getNumId()) {
            paragraph.setNumID(BigInteger.valueOf(style.getNumId()));
        }
        if (null != style.getLvl()) {
            paragraph.setNumILvl(BigInteger.valueOf(style.getLvl()));
        }
    }

    public static void styleCTBorder(CTBorder b, BorderStyle style) {
        if (null != style.getType()) b.setVal(STBorder.Enum.forString(style.getType().toString().toLowerCase()));
        b.setSz(BigInteger.valueOf(style.getSize()));
        b.setSpace(BigInteger.valueOf(style.getSpace()));
        if (null != style.getColor()) b.setColor(style.getColor());
    }

    public static Style retriveStyle(XWPFRun run) {
        if (null == run) return null;
        StyleBuilder builder = Style.builder()
                .buildColor(run.getColor())
                .buildFontFamily(run.getFontFamily(FontCharRange.eastAsia))
                .buildWesternFontFamily(run.getFontFamily(FontCharRange.ascii))
                .buildFontSize(run.getFontSize());
        if (run.isBold()) builder.buildBold();
        if (run.isItalic()) builder.buildItalic();
        if (run.isStrikeThrough()) builder.buildStrike();
        return builder.build();
    }

    public static ParagraphStyle retriveParagraphStyle(XWPFParagraph paragraph) {
        if (null == paragraph) return null;
        Builder builder = ParagraphStyle.builder();
        CTP ctp = paragraph.getCTP();
        CTPPr pr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
        if (pr.isSetWordWrap()) {
            if (pr.getWordWrap().getVal() == XWPFOnOff.X_1 || pr.getWordWrap().getVal() == XWPFOnOff.FALSE
                    || pr.getWordWrap().getVal() == XWPFOnOff.OFF) {
                builder.withAllowWordBreak(true);
            }
        }
        if (pr.isSetPBdr()) {
            CTPBdr ct = pr.getPBdr();
            if (ct.isSetLeft()) {
                builder.withLeftBorder(retriveBorderStyle(ct.getLeft()));
            }
            if (ct.isSetTop()) {
                builder.withTopBorder(retriveBorderStyle(ct.getTop()));
            }
            if (ct.isSetRight()) {
                builder.withRightBorder(retriveBorderStyle(ct.getRight()));
            }
            if (ct.isSetBottom()) {
                builder.withBottomBorder(retriveBorderStyle(ct.getBottom()));
            }
        }
        if (pr.isSetShd()) {
            CTShd shd = pr.getShd();
            builder.withShadingPattern(XWPFShadingPattern.valueOf(shd.getVal().intValue()));
            if (shd.isSetFill()) builder.withBackgroundColor(shd.xgetFill().getStringValue());
        }
        builder.withAlign(paragraph.getAlignment());
        int spacingBeforeLines = paragraph.getSpacingBeforeLines();
        if (-1 != spacingBeforeLines) {
            builder.withSpacingBeforeLines(spacingBeforeLines / 100.0f);
        }
        int spacingAfterLines = paragraph.getSpacingAfterLines();
        if (-1 != spacingAfterLines) {
            builder.withSpacingAfterLines(spacingAfterLines / 100.0f);
        }
        int spacingBefore = paragraph.getSpacingBefore();
        if (-1 != spacingBefore) {
            builder.withSpacingBefore(UnitUtils.twips2Point(spacingBefore));
        }
        int spacingAfter = paragraph.getSpacingAfter();
        if (-1 != spacingAfter) {
            builder.withSpacingAfter(UnitUtils.twips2Point(spacingAfter));
        }
        double spacingBetween = paragraph.getSpacingBetween();
        if (-1 != spacingBetween) {
            builder.withSpacing(spacingBetween);
            builder.withSpacingRule(paragraph.getSpacingLineRule());
        }

        return builder.build();
    }

    public static BorderStyle retriveBorderStyle(CTBorder border) {
        BorderStyle.Builder borderBuilder = BorderStyle.builder();
        if (border.isSetColor()) borderBuilder.withColor(border.xgetColor().getStringValue());
        if (border.isSetSz()) borderBuilder.withSize(border.getSz().intValue());
        if (border.getVal() != null)
            borderBuilder.withType(XWPFBorderType.valueOf(border.getVal().toString().toUpperCase()));
        return borderBuilder.build();
    }

    public static Style retriveStyleFromCss(Map<String, String> propertyValues) {
        StyleBuilder builder = Style.builder();
        if (propertyValues != null) {
            String style = propertyValues.get("font-style");
            String weight = propertyValues.get("font-weight");
            String color = propertyValues.get("color");
            String size = propertyValues.get("font-size");
            if (StringUtils.isNotBlank(style) && "italic".equalsIgnoreCase(style)) {
                builder.buildItalic();
            }
            if (StringUtils.isNotBlank(size)) {
//                builder.buildFontSize(fontSize);
            }
            if (StringUtils.isNotBlank(weight)) {
                builder.buildBold();
            }
            if (StringUtils.isNotBlank(color)) {
                String rgb = toRgb(color);
                builder.buildColor(rgb);
            }
        } else {
            return null;
        }
        return builder.build();
    }

    public static ParagraphStyle retriveParagraphStyleFromCss(Map<String, String> propertyValues) {
        Builder builder = ParagraphStyle.builder();
        if (propertyValues != null) {
            String background = propertyValues.get("background");
            String color = propertyValues.get("color");
            if (StringUtils.isNotBlank(background)) {
                builder.withBackgroundColor(toRgb(background));
            }
            if (StringUtils.isNotBlank(color)) {
                String rgb = toRgb(color);
                builder.withDefaultTextStyle(Style.builder().buildColor(rgb).build());
            }
        } else {
            return null;
        }
        return builder.build();
    }

    public static String toRgb(String color) {
        // rgb() or rgba()
        if (color.toUpperCase().startsWith("RGB")) {
            String val = color.substring(color.indexOf("(") + 1, color.lastIndexOf(")"));
            String[] rgbArr = val.split(",");
            return String.format("%02x%02x%02x", Integer.valueOf(rgbArr[0]), Integer.valueOf(rgbArr[1]),
                    Integer.valueOf(rgbArr[2]));
        }
        // css color name
        try {
            CssRgb valueOf = CssRgb.valueOf(color.toUpperCase());
            if (null != valueOf) return valueOf.getRgb().substring(1);
        } catch (Exception e) {
        }
        if (!color.startsWith("#")) {
            // throw new IllegalArgumentException("Unable to Rgb color:" + color);
            return null;
        }
        // #RRGGBB
        if (color.length() == 7) return color.substring(1);
        // #RGB
        if (color.length() == 4) {
            return String.format("%c%c%c%c%c%c", color.charAt(1), color.charAt(1), color.charAt(2), color.charAt(2),
                    color.charAt(3), color.charAt(3));
        }
        return color.length() > 7 ? color.substring(1, 7) : color.substring(1);
    }

    private static CTRPr getRunProperties(XWPFRun run) {
        return run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
    }

}
