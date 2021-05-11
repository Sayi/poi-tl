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
package com.deepoove.poi.resolver;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.util.RegexUtils;

/**
 * initial pattern by config
 * 
 * @author Sayi
 * @version
 */
public abstract class AbstractResolver implements Resolver {

    protected final Configure config;

    protected Pattern templatePattern;
    protected Pattern gramerPattern;

    private static final String FORMAT_TEMPLATE = "{0}{1}{2}{3}";
    private static final String FORMAT_GRAMER = "({0})|({1})";

    public AbstractResolver(Configure config) {
        this.config = config;
        patternCreated();
    }

    void patternCreated() {
        String sign = getGramarRegex(config);
        String prefix = RegexUtils.escapeExprSpecialWord(config.getGramerPrefix());
        String suffix = RegexUtils.escapeExprSpecialWord(config.getGramerSuffix());

        templatePattern = Pattern
                .compile(MessageFormat.format(FORMAT_TEMPLATE, prefix, sign, config.getGrammerRegex(), suffix));
        gramerPattern = Pattern.compile(MessageFormat.format(FORMAT_GRAMER, prefix, suffix));
    }

    String getGramarRegex(Configure config) {
        List<Character> gramerChar = new ArrayList<Character>(config.getGramerChars());
        StringBuilder reg = new StringBuilder("(");
        for (int i = 0;; i++) {
            Character chara = gramerChar.get(i);
            String word = RegexUtils.escapeExprSpecialWord(chara.toString());
            if (i == gramerChar.size() - 1) {
                reg.append(word).append(")?");
                break;
            } else reg.append(word).append("|");
        }
        return reg.toString();
    }

    public Pattern getTemplatePattern() {
        return templatePattern;
    }

    public Pattern getGramerPattern() {
        return gramerPattern;
    }

}
