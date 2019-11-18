/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.util;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * 正则工具类
 * 
 * @author Sayi
 * @version
 */
public final class RegexUtils {

    /**
     * 通用全能的正则表达式Pattern
     */
    public static final String REGEX_GENERAL = "((?!{0})(?!{1}).)*";

    public static String escapeExprSpecialWord(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}",
                    "|" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    public static String createGeneral(String prefix, String suffix) {
        return MessageFormat.format(REGEX_GENERAL, escapeExprSpecialWord(prefix),
                escapeExprSpecialWord(suffix));
    }
}
