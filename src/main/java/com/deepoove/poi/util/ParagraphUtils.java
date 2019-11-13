package com.deepoove.poi.util;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public final class ParagraphUtils {

    public static String trimLine(XWPFParagraph paragraph) {
        String value = paragraph.getText();
        int len = value.length();
        int st = 0;
        char[] val = value.toCharArray();

        while ((st < len) && (val[st] == '\n')) {
            st++;
        }
        while ((st < len) && (val[len - 1] == '\n')) {
            len--;
        }
        return (st > 0 || len < value.length()) ? value.substring(st, len) : value;
    }

}
