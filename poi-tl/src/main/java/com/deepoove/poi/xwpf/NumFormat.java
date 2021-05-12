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

public enum NumFormat {

    // STNumberFormat

    DECIMAL(1),
    UPPER_ROMAN(2),
    LOWER_ROMAN(3),
    UPPER_LETTER(4),
    LOWER_LETTER(5),
    ORDINAL(6),
    CARDINAL_TEXT(7),
    ORDINAL_TEXT(8),
    HEX(9),
    CHICAGO(10),
    IDEOGRAPH_DIGITAL(11),
    JAPANESE_COUNTING(12),
    AIUEO(13),
    IROHA(14),
    DECIMAL_FULL_WIDTH(15),
    DECIMAL_HALF_WIDTH(16),
    JAPANESE_LEGAL(17),
    JAPANESE_DIGITAL_TEN_THOUSAND(18),
    DECIMAL_ENCLOSED_CIRCLE(19),
    DECIMAL_FULL_WIDTH_2(20),
    AIUEO_FULL_WIDTH(21),
    IROHA_FULL_WIDTH(22),
    DECIMAL_ZERO(23),
    BULLET(24),
    GANADA(25),
    CHOSUNG(26),
    DECIMAL_ENCLOSED_FULLSTOP(27),
    DECIMAL_ENCLOSED_PAREN(28),
    DECIMAL_ENCLOSED_CIRCLE_CHINESE(29),
    IDEOGRAPH_ENCLOSED_CIRCLE(30),
    IDEOGRAPH_TRADITIONAL(31),
    IDEOGRAPH_ZODIAC(32),
    IDEOGRAPH_ZODIAC_TRADITIONAL(33),
    TAIWANESE_COUNTING(34),
    IDEOGRAPH_LEGAL_TRADITIONAL(35),
    TAIWANESE_COUNTING_THOUSAND(36),
    TAIWANESE_DIGITAL(37),
    CHINESE_COUNTING(38),
    CHINESE_LEGAL_SIMPLIFIED(39),
    CHINESE_COUNTING_THOUSAND(40);

    private static Map<Integer, NumFormat> imap = new HashMap<>();

    static {
        for (NumFormat p : values()) {
            imap.put(p.getValue(), p);
        }
    }

    private final int value;

    private NumFormat(int val) {
        value = val;
    }

    public static NumFormat valueOf(int type) {
        NumFormat err = imap.get(type);
        if (err == null) throw new IllegalArgumentException("Unknown num fmt: " + type);
        return err;
    }

    public int getValue() {
        return value;
    }

}
