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

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public final class ParagraphUtils {

    public static String trimLine(XWPFParagraph paragraph) {
        return trimLine(paragraph.getText());
    }

    public static String trimLine(String value) {
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

    public static Integer getRunPos(XWPFRun run) {
        XWPFParagraph paragraph = (XWPFParagraph) run.getParent();
        List<XWPFRun> runs = paragraph.getRuns();
        for (int i = 0; i < runs.size(); i++) {
            if (run == runs.get(i)) {
                return i;
            }
        }
        return null;
    }

    public static boolean havePictures(XWPFParagraph paragraph) {
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            if (CollectionUtils.isNotEmpty(run.getEmbeddedPictures())) return true;
        }
        return false;
    }

    public static boolean havePageBreak(XWPFParagraph paragraph) {
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            if (CollectionUtils.isNotEmpty(run.getCTR().getLastRenderedPageBreakList())) return true;
        }
        return false;
    }

    public static boolean haveObject(XWPFParagraph paragraph) {
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            if (CollectionUtils.isNotEmpty(run.getCTR().getObjectList())) return true;
        }
        return false;
    }

}
