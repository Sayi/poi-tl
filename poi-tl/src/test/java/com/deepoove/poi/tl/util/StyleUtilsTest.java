package com.deepoove.poi.tl.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.util.StyleUtils;

public class StyleUtilsTest {

    @Test
    public void testFontSize() throws IOException {
        try (XWPFDocument doc = new XWPFDocument()) {
            XWPFRun createRun = doc.createParagraph().createRun();
            createRun.setFontSize(14);

            Style build = Style.builder().buildFontSize(10.5).build();

            StyleUtils.styleRun(createRun, build);

            assertEquals(10.5, createRun.getFontSizeAsDouble());

            XWPFRun destRun = doc.createParagraph().createRun();

            StyleUtils.styleRun(destRun, createRun);

            assertEquals(10.5, destRun.getFontSizeAsDouble());
        }

    }

    @Test
    public void testToRgb() {
        assertEquals("11ff22", StyleUtils.toRgb("#1f2"));
        assertEquals("01645a", StyleUtils.toRgb("rgb(1,100,90)"));
        assertEquals("000000", StyleUtils.toRgb("black"));
    }

}
