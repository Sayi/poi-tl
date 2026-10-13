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

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Generates legal and unique Word bookmark names for headings.
 * <p>
 * Word requires a bookmark name to start with a letter, to contain only
 * letters, digits and underscores, to be at most 40 characters long and to be
 * unique inside the document. The previous implementation used the raw heading
 * text, which produced names with spaces and duplicated names.
 * </p>
 * <p>
 * The same normalisation is applied to the {@code #fragment} of an in-document
 * link, so {@code [go](#Title-One)} resolves to the bookmark generated for
 * {@code ## Title One}.
 * </p>
 *
 * @author Sayi
 */
final class BookmarkRegistry {

    /** Maximum length accepted by Word. */
    private static final int MAX_LENGTH = 40;

    /** Fallback name for a heading without any usable character. */
    private static final String FALLBACK = "heading";

    private final Set<String> used = new HashSet<>();

    /**
     * Registers a heading and returns its unique bookmark name.
     *
     * @param headingText plain text of the heading
     * @return a legal, document unique bookmark name
     */
    String register(String headingText) {
        String base = sanitize(headingText);
        String name = base;
        int index = 1;
        while (!used.add(name)) {
            String tail = "_" + index++;
            name = shorten(base, MAX_LENGTH - tail.length()) + tail;
        }
        return name;
    }

    /**
     * Normalises text into a bookmark name. Deterministic and idempotent, so it
     * is usable both for a heading and for the anchor of a link.
     *
     * @param text heading text or link fragment
     * @return a legal bookmark name
     */
    static String sanitize(String text) {
        StringBuilder sb = new StringBuilder();
        boolean lastWasSeparator = false;
        if (null != text) {
            String lower = text.toLowerCase(Locale.ROOT);
            for (int i = 0; i < lower.length(); i++) {
                char ch = lower.charAt(i);
                if (Character.isLetterOrDigit(ch)) {
                    sb.append(ch);
                    lastWasSeparator = false;
                } else if (sb.length() > 0 && !lastWasSeparator) {
                    sb.append('_');
                    lastWasSeparator = true;
                }
            }
        }
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '_') {
            sb.setLength(sb.length() - 1);
        }
        if (sb.length() == 0) return FALLBACK;
        // a bookmark must start with a letter
        if (Character.isDigit(sb.charAt(0))) sb.insert(0, 'h');
        return shorten(sb.toString(), MAX_LENGTH);
    }

    private static String shorten(String value, int max) {
        if (max < 1) return value;
        return value.length() <= max ? value : value.substring(0, max);
    }

}
