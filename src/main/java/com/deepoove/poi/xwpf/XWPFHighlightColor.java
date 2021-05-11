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
package com.deepoove.poi.xwpf;

import java.util.HashMap;
import java.util.Map;

public enum XWPFHighlightColor {

    // STHighlightColor

    BLACK(1),
    BLUE(2),
    CYAN(3),
    GREEN(4),
    MAGENTA(5),
    RED(6),
    YELLOW(7),
    WHITE(8),
    DARK_BLUE(9),
    DARK_CYAN(10),
    DARK_GREEN(11),
    DARK_MAGENTA(12),
    DARK_RED(13),
    DARK_YELLOW(14),
    DARK_GRAY(15),
    LIGHT_GRAY(16),
    NONE(17);

    private static Map<Integer, XWPFHighlightColor> imap = new HashMap<>();

    static {
        for (XWPFHighlightColor p : values()) {
            imap.put(p.getValue(), p);
        }
    }

    private final int value;

    private XWPFHighlightColor(int val) {
        value = val;
    }

    public static XWPFHighlightColor valueOf(int type) {
        XWPFHighlightColor err = imap.get(type);
        if (err == null) throw new IllegalArgumentException("Unknown HighlightColor : " + type);
        return err;
    }

    public int getValue() {
        return value;
    }

}
