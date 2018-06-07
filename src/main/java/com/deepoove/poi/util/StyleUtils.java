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

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTColor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

import com.deepoove.poi.data.style.Style;

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
        if (null != color) run.setColor(color);
        if (0 != fontSize) run.setFontSize(fontSize);
        if (null != fontFamily) {
            run.setFontFamily(fontFamily);
            CTRPr pr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
            CTFonts fonts = pr.isSetRFonts() ? pr.getRFonts() : pr.addNewRFonts();
            fonts.setAscii(fontFamily);
            fonts.setHAnsi(fontFamily);
            fonts.setCs(fontFamily);
            fonts.setEastAsia(fontFamily);
        }
        if (null != bold) run.setBold(bold);
        if (null != italic) run.setItalic(italic);
        if (null != strike) run.setStrikeThrough(strike);
        if (Boolean.TRUE.equals(underLine)){
            run.setUnderline(UnderlinePatterns.SINGLE);
        }
    }

    /**
     * 重复样式
     * 
     * @param destRun
     * @param srcRun
     */
    public static void styleRun(XWPFRun destRun, XWPFRun srcRun) {
        if (null == destRun || null == srcRun) return;
        destRun.setBold(srcRun.isBold());
        destRun.setColor(srcRun.getColor());
        // destRun.setCharacterSpacing(srcRun.getCharacterSpacing());
        destRun.setFontFamily(srcRun.getFontFamily());
        int fontSize = srcRun.getFontSize();
        if (-1 != fontSize) destRun.setFontSize(fontSize);
        destRun.setItalic(srcRun.isItalic());
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
        if (null != fmtStyle.getColor()) {
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

        if (null != fmtStyle.getFontFamily()) {
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

}
