/*
 * Copyright 2014-2026 Sayi
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBrType;

import com.deepoove.poi.xwpf.XWPFStructuredDocumentTagContent;

public final class ParagraphUtils {

    public static String trimLine(XWPFParagraph paragraph) {
        return null == paragraph ? "" : trimLine(paragraph.getText());
    }

    public static String trimLine(String value) {
        if (null == value) return "";
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
        List<XWPFRun> runs = getRunList(run);
        for (int i = 0; i < runs.size(); i++) {
            if (run == runs.get(i)) {
                return i;
            }
        }
        return null;
    }

    private static List<XWPFRun> getRunList(XWPFRun run) {
        IRunBody parent = run.getParent();
        if (parent instanceof XWPFParagraph) {
            XWPFParagraph paragraph = (XWPFParagraph) parent;
            return paragraph.getRuns();
        } else if (parent instanceof XWPFStructuredDocumentTagContent) {
            XWPFStructuredDocumentTagContent paragraph = (XWPFStructuredDocumentTagContent) parent;
            return paragraph.getRuns();
        }
        return new ArrayList<>();
    }

    public static boolean havePictures(XWPFParagraph paragraph) {
        if (null == paragraph) return false;
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            if (CollectionUtils.isNotEmpty(run.getEmbeddedPictures())) return true;
        }
        return false;
    }

    public static boolean havePageBreak(XWPFParagraph paragraph) {
        if (null == paragraph) return false;
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            CTR ctr = run.getCTR();
            if (null == ctr) continue;
            if (CollectionUtils.isNotEmpty(ctr.getLastRenderedPageBreakList())) return true;
            List<CTBr> brList = ctr.getBrList();
            if (CollectionUtils.isNotEmpty(brList)) {
                for (CTBr br : brList) {
                    if (STBrType.PAGE.equals(br.getType())) return true;
                }
            }
        }
        return false;
    }

    public static boolean haveObject(XWPFParagraph paragraph) {
        if (null == paragraph) return false;
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            CTR ctr = run.getCTR();
            if (null != ctr && CollectionUtils.isNotEmpty(ctr.getObjectList())) return true;
        }
        return false;
    }

}
