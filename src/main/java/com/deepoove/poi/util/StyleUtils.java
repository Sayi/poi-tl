/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.util;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHighlight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHighlightColor.Enum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;

import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;

/**
 * 样式工具类
 * 
 * @author Sayi
 * @version
 */
public final class StyleUtils {

    /**
     * 设置run的样式
     * 
     * @param run
     * @param style
     */
    public static void styleRun(XWPFRun run, Style style) {
        if (null == run || null == style) return;
        String color = style.getColor();
        String fontFamily = style.getFontFamily();
        int fontSize = style.getFontSize();
        Boolean bold = style.isBold();
        Boolean italic = style.isItalic();
        Boolean strike = style.isStrike();
        Boolean underLine = style.isUnderLine();
        Enum highlightColor = style.getHighlightColor();
        CTRPr pr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
        if (StringUtils.isNotBlank(color)) {
            // run.setColor(color);
            // issue 326
            CTColor ctColor = pr.isSetColor() ? pr.getColor() : pr.addNewColor();
            ctColor.setVal(color);
            if (ctColor.isSetThemeColor()) ctColor.unsetThemeColor();
        }
        if (0 != fontSize) run.setFontSize(fontSize);
        if (StringUtils.isNotBlank(fontFamily)) {
            run.setFontFamily(fontFamily);
            CTFonts fonts = pr.isSetRFonts() ? pr.getRFonts() : pr.addNewRFonts();
            fonts.setAscii(fontFamily);
            fonts.setHAnsi(fontFamily);
            fonts.setCs(fontFamily);
            fonts.setEastAsia(fontFamily);
        }
        if (null != highlightColor) {
            CTHighlight highlight = pr.isSetHighlight() ? pr.getHighlight() : pr.addNewHighlight();
            STHighlightColor hColor = highlight.xgetVal();
            if (hColor == null) {
                hColor = STHighlightColor.Factory.newInstance();
            }
            STHighlightColor.Enum val = STHighlightColor.Enum.forString(highlightColor.toString());
            if (val != null) {
                hColor.setStringValue(val.toString());
                highlight.xsetVal(hColor);
            }
        }
        if (null != bold) run.setBold(bold);
        if (null != italic) run.setItalic(italic);
        if (null != strike) run.setStrikeThrough(strike);
        if (Boolean.TRUE.equals(underLine)) {
            run.setUnderline(UnderlinePatterns.SINGLE);
        }
    }

    /**
     * 重复样式
     * 
     * @param destRun
     *            新建的run
     * @param srcRun
     *            原始run
     */
    public static void styleRun(XWPFRun destRun, XWPFRun srcRun) {
        if (null == destRun || null == srcRun) return;
        CTR ctr = srcRun.getCTR();
        if (ctr.isSetRPr() && ctr.getRPr().isSetRStyle()) {
            String val = ctr.getRPr().getRStyle().getVal();
            if (StringUtils.isNotBlank(val)) {
                CTRPr pr = destRun.getCTR().isSetRPr() ? destRun.getCTR().getRPr()
                        : destRun.getCTR().addNewRPr();
                CTString rStyle = pr.isSetRStyle() ? pr.getRStyle() : pr.addNewRStyle();
                rStyle.setVal(val);
            }
        }
        if (Boolean.TRUE.equals(srcRun.isBold())) destRun.setBold(srcRun.isBold());
        destRun.setColor(srcRun.getColor());
        // destRun.setCharacterSpacing(srcRun.getCharacterSpacing());
        if (StringUtils.isNotBlank(srcRun.getFontFamily()))
            destRun.setFontFamily(srcRun.getFontFamily());
        int fontSize = srcRun.getFontSize();
        if (-1 != fontSize) destRun.setFontSize(fontSize);
        if (Boolean.TRUE.equals(srcRun.isItalic())) destRun.setItalic(srcRun.isItalic());
        if (Boolean.TRUE.equals(srcRun.isStrikeThrough()))
            destRun.setStrikeThrough(srcRun.isStrikeThrough());
        destRun.setUnderline(srcRun.getUnderline());
    }

    /**
     * 设置w:rPr的样式
     * 
     * @param pr
     * @param fmtStyle
     */
    public static void styleRpr(CTParaRPr pr, Style fmtStyle) {
        if (null == pr || null == fmtStyle) return;
        if (StringUtils.isNotBlank(fmtStyle.getColor())) {
            CTColor color = pr.isSetColor() ? pr.getColor() : pr.addNewColor();
            color.setVal(fmtStyle.getColor());
        }

        if (null != fmtStyle.isItalic()) {
            CTOnOff italic = pr.isSetI() ? pr.getI() : pr.addNewI();
            italic.setVal(fmtStyle.isItalic() ? STOnOff.TRUE : STOnOff.FALSE);
        }

        if (null != fmtStyle.isBold()) {
            CTOnOff bold = pr.isSetB() ? pr.getB() : pr.addNewB();
            bold.setVal(fmtStyle.isBold() ? STOnOff.TRUE : STOnOff.FALSE);
        }

        if (0 != fmtStyle.getFontSize()) {
            BigInteger bint = new BigInteger("" + fmtStyle.getFontSize());
            CTHpsMeasure ctSize = pr.isSetSz() ? pr.getSz() : pr.addNewSz();
            ctSize.setVal(bint.multiply(new BigInteger("2")));
        }

        if (null != fmtStyle.isStrike()) {
            CTOnOff strike = pr.isSetStrike() ? pr.getStrike() : pr.addNewStrike();
            strike.setVal(fmtStyle.isStrike() ? STOnOff.TRUE : STOnOff.FALSE);
        }

        if (StringUtils.isNotBlank(fmtStyle.getFontFamily())) {
            CTFonts fonts = pr.isSetRFonts() ? pr.getRFonts() : pr.addNewRFonts();
            String fontFamily = fmtStyle.getFontFamily();
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

    public static void styleTable(XWPFTable table, TableStyle style) {
        if (null == table || null == style) return;
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (null == tblPr) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
        if (null != style.getAlign()) {
            CTJc jc = tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc();
            jc.setVal(style.getAlign());
        }
        if (StringUtils.isNotBlank(style.getBackgroundColor())) {
            CTShd ctshd = tblPr.isSetShd() ? tblPr.getShd() : tblPr.addNewShd();
            ctshd.setColor("auto");
            ctshd.setVal(STShd.CLEAR);
            ctshd.setFill(style.getBackgroundColor());
        }
    }

    public static void styleTableParagraph(XWPFParagraph par, TableStyle style) {
        if (null != par && null != style && null != style.getAlign()) {
            CTP ctp = par.getCTP();
            CTPPr CTPpr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
            CTJc jc = CTPpr.isSetJc() ? CTPpr.getJc() : CTPpr.addNewJc();
            jc.setVal(style.getAlign());
        }

    }

    public static void styleParagraph(XWPFParagraph paragraph, Style style) {
        if (null == paragraph || null == style) return;
        CTP ctp = paragraph.getCTP();
        CTPPr pPr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
        CTParaRPr pr = pPr.isSetRPr() ? pPr.getRPr() : pPr.addNewRPr();
        StyleUtils.styleRpr(pr, style);
    }

}
