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

public enum XWPFShadingPattern {

    // STShd

    NIL(1),
    CLEAR(2),
    SOLID(3),
    HORZ_STRIPE(4),
    VERT_STRIPE(5),
    REVERSE_DIAG_STRIPE(6),
    DIAG_STRIPE(7),
    HORZ_CROSS(8),
    DIAG_CROSS(9),
    THIN_HORZ_STRIPE(10),
    THIN_VERT_STRIPE(11),
    THIN_REVERSE_DIAG_STRIPE(12),
    THIN_DIAG_STRIPE(13),
    THIN_HORZ_CROSS(14),
    THIN_DIAG_CROSS(15),
    PCT_5(16),
    PCT_10(17),
    PCT_12(18),
    PCT_15(19),
    PCT_20(20),
    PCT_25(21),
    PCT_30(22),
    PCT_35(23),
    PCT_37(24),
    PCT_40(25),
    PCT_45(26),
    PCT_50(27),
    PCT_55(28),
    PCT_60(29),
    PCT_62(30),
    PCT_65(31),
    PCT_70(32),
    PCT_75(33),
    PCT_80(34),
    PCT_85(35),
    PCT_87(36),
    PCT_90(37),
    PCT_95(38);

    private static Map<Integer, XWPFShadingPattern> imap = new HashMap<>();

    static {
        for (XWPFShadingPattern p : values()) {
            imap.put(p.getValue(), p);
        }
    }

    private final int value;

    private XWPFShadingPattern(int val) {
        value = val;
    }

    public static XWPFShadingPattern valueOf(int type) {
        XWPFShadingPattern err = imap.get(type);
        if (err == null) throw new IllegalArgumentException("Unknown shading pattern: " + type);
        return err;
    }

    public int getValue() {
        return value;
    }

}
