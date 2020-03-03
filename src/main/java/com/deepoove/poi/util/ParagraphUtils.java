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
