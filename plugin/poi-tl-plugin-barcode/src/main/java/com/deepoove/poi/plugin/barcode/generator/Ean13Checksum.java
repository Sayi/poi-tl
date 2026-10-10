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
package com.deepoove.poi.plugin.barcode.generator;

/**
 * EAN-13 modulo 10 checksum.
 * <p>
 * A 12 digits input is completed with its checksum, a 13 digits input is
 * validated. A wrong checksum is always reported instead of being silently
 * corrected, because the 13 digits are the identifier declared by the caller and
 * a silent change would map to another product.
 *
 * @author Sayi
 */
public final class Ean13Checksum {

    private static final int DATA_LENGTH = 12;

    private Ean13Checksum() {
    }

    /**
     * @return the 13 digits code
     * @throws IllegalArgumentException
     *             if the content is not 12 or 13 digits, or if the checksum of a 13
     *             digits content is wrong
     */
    public static String complete(String content) {
        if (null == content) {
            throw new IllegalArgumentException("EAN-13 content must not be null!");
        }
        String code = content.trim();
        if (!code.matches("\\d{" + DATA_LENGTH + ",13}")) {
            throw new IllegalArgumentException(
                    "EAN-13 content must be 12 or 13 digits, but was: " + content);
        }
        if (code.length() == DATA_LENGTH) {
            return code + calculate(code);
        }
        String expected = calculate(code.substring(0, DATA_LENGTH));
        if (!expected.equals(code.substring(DATA_LENGTH))) {
            throw new IllegalArgumentException("EAN-13 checksum mismatch: the checksum of "
                    + code.substring(0, DATA_LENGTH) + " is " + expected + ", but the content declares "
                    + code.substring(DATA_LENGTH));
        }
        return code;
    }

    /**
     * @param data12
     *            the first 12 digits, the leftmost one is weighted by 1
     * @return the checksum digit
     */
    public static String calculate(String data12) {
        if (null == data12 || DATA_LENGTH != data12.length()) {
            throw new IllegalArgumentException("EAN-13 data must be exactly 12 digits!");
        }
        int sum = 0;
        for (int i = 0; i < DATA_LENGTH; i++) {
            int digit = data12.charAt(i) - '0';
            if (digit < 0 || digit > 9) {
                throw new IllegalArgumentException("EAN-13 data must contain digits only: " + data12);
            }
            sum += (0 == i % 2) ? digit : digit * 3;
        }
        return String.valueOf((10 - sum % 10) % 10);
    }

}
