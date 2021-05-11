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
package com.deepoove.poi.plugin.highlight.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.deepoove.poi.util.ResourceLoader;

/**
 * @author Sayi
 *
 */
public class StylesheetParser {

    private static Map<String, List<SelectorStyle>> stylesheetCache = new ConcurrentHashMap<>();

    public static List<SelectorStyle> parse(String path) throws Exception {
        List<SelectorStyle> result = stylesheetCache.get(path);
        if (null != result) return result;

        String css = ResourceLoader.loadContent(path);
        int cursor = 0;
        int length = css.length();

        List<SelectorStyle> allSelectorStyles = new ArrayList<>();
        List<SelectorStyle> current = new ArrayList<>();
        String currentKey = "";
        boolean ignore = false;
        String ele = "";
        while (cursor < length) {
            char character = css.charAt(cursor);
            if (ignore && character != '*' && character != '/') {
                cursor++;
                ele = clearChar();
                continue;
            }
            switch (character) {
            case '{':
                String selector = ele;
                ele = clearChar();
                String[] split = selector.split(",");
                for (String select : split) {
                    current.add(new SelectorStyle(select.trim()));
                }
                break;
            case '}':
                String value = ele.trim();
                ele = clearChar();
                if (StringUtils.isNotBlank(currentKey)) {
                    for (SelectorStyle cs : current) {
                        cs.getPropertyValues().put(currentKey, value);
                    }
                    currentKey = "";
                }
                allSelectorStyles.addAll(current);
                ele = clearChar();
                current = new ArrayList<>();
                break;
            case '/':
                ele += character;
                if (ele.trim().endsWith("*/")) {
                    ignore = false;
                    ele = clearChar();
                }
                break;
            case '*':
                ele += character;
                if ("/*".equals(ele.trim())) {
                    ignore = true;
                    ele = clearChar();
                }
                break;
            case ':':
                currentKey = ele.trim();
                ele = clearChar();
                break;
            case ';':
                value = ele.trim();
                ele = clearChar();
                for (SelectorStyle cs : current) {
                    cs.getPropertyValues().put(currentKey, value);
                }
                currentKey = "";
                break;
            case '\n':
            case '\r':
                break;

            default:
                ele += character;
                break;
            }
            cursor++;
        }
        stylesheetCache.put(path, allSelectorStyles);
        return allSelectorStyles;
    }

    private static String clearChar() {
        return "";
    }

}
