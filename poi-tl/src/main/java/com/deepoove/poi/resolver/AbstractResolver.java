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
package com.deepoove.poi.resolver;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.util.RegexUtils;

/**
 * The base resolver that creates the template pattern and the grammar pattern
 * from the config
 * <p>
 * The template pattern matches a whole tag, and the grammar pattern matches the
 * tag prefix and suffix.
 * </p>
 * 
 * @author Sayi
 * @version
 */
public abstract class AbstractResolver implements Resolver {

    protected final Configure config;

    protected Pattern templatePattern;
    protected Pattern grammarPattern;

    private static final String FORMAT_TEMPLATE = "{0}{1}{2}{3}";
    private static final String FORMAT_GRAMMAR = "({0})|({1})";

    /**
     * Create a resolver and build the patterns from the config
     * 
     * @param config template config
     */
    public AbstractResolver(Configure config) {
        this.config = config;
        patternCreated();
    }

    void patternCreated() {
        String sign = getGrammarRegex(config);
        String prefix = RegexUtils.escapeExprSpecialWord(config.getGrammarPrefix());
        String suffix = RegexUtils.escapeExprSpecialWord(config.getGrammarSuffix());

        templatePattern = Pattern
                .compile(MessageFormat.format(FORMAT_TEMPLATE, prefix, sign, config.getGrammarRegex(), suffix));
        grammarPattern = Pattern.compile(MessageFormat.format(FORMAT_GRAMMAR, prefix, suffix));
    }

    String getGrammarRegex(Configure config) {
        List<Character> grammarChar = new ArrayList<Character>(config.getGrammarChars());
        StringBuilder reg = new StringBuilder("(");
        for (int i = 0;; i++) {
            Character chara = grammarChar.get(i);
            String word = RegexUtils.escapeExprSpecialWord(chara.toString());
            if (i == grammarChar.size() - 1) {
                reg.append(word).append(")?");
                break;
            } else reg.append(word).append("|");
        }
        return reg.toString();
    }

    /**
     * Get the pattern that matches a whole tag
     * 
     * @return template pattern
     */
    public Pattern getTemplatePattern() {
        return templatePattern;
    }

    /**
     * Get the pattern that matches the tag prefix and suffix
     * 
     * @return grammar pattern
     */
    public Pattern getGrammarPattern() {
        return grammarPattern;
    }

    /**
     * Get the pattern that matches the tag prefix and suffix
     * 
     * @return grammar pattern
     * @deprecated misspelled, use {@link #getGrammarPattern()} instead.
     */
    @Deprecated
    public Pattern getGramerPattern() {
        return getGrammarPattern();
    }

}
