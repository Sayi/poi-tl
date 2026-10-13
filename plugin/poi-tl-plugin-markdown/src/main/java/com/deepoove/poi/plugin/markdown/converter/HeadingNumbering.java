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
package com.deepoove.poi.plugin.markdown.converter;

import java.util.Arrays;

/**
 * Multi level heading numbering, e.g. {@code 1.}, {@code 1.1.}, {@code 1.1.1.}.
 * <p>
 * Different from the former string-slicing implementation, the number is
 * assembled from explicit per level counters, so a chapter number with more
 * than one digit ({@code 10.}) no longer breaks the layout, and levels that
 * never appeared are skipped instead of being rendered as {@code 0}
 * (a lone {@code ###} no longer prints {@code 0.1.}).
 * </p>
 *
 * @author Sayi
 */
final class HeadingNumbering {

    /** Markdown headings are limited to level 1..6. */
    private static final int MAX_LEVEL = 6;

    private final int startLevel;
    private final String suffix;
    private final int[] counters = new int[MAX_LEVEL + 1];

    /**
     * @param startLevel the first heading level that takes part in numbering, 1..6
     * @param suffix     trailing text appended after the last counter, usually "." or ""
     */
    HeadingNumbering(int startLevel, String suffix) {
        this.startLevel = Math.max(1, Math.min(MAX_LEVEL, startLevel));
        this.suffix = null == suffix ? "" : suffix;
    }

    /**
     * Advances the counters and returns the number prefix of the given heading.
     *
     * @param level heading level, 1..6
     * @return the number text ending with a space, or an empty string when the
     *         level is not numbered
     */
    String next(int level) {
        int current = Math.max(1, Math.min(MAX_LEVEL, level));
        if (current < startLevel) return "";

        counters[current]++;
        Arrays.fill(counters, current + 1, counters.length, 0);

        StringBuilder number = new StringBuilder();
        for (int l = startLevel; l <= current; l++) {
            if (counters[l] == 0) continue;
            if (number.length() > 0) number.append('.');
            number.append(counters[l]);
        }
        number.append(suffix);
        return number.append(' ').toString();
    }

}
