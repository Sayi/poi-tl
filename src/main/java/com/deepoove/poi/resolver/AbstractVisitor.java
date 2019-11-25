package com.deepoove.poi.resolver;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.util.RegexUtils;

/**
 * 初始化标签规则
 * 
 * @author Sayi
 * @version
 */
public abstract class AbstractVisitor implements Visitor {

    // config
    protected final Configure config;

    // regex pattern
    protected Pattern templatePattern;
    protected Pattern gramerPattern;

    private static final String FORMAT_TEMPLATE = "{0}{1}{2}{3}";
    private static final String FORMAT_GRAMER = "({0})|({1})";

    public AbstractVisitor(Configure config) {
        this.config = config;
        patternCreated();
    }

    private void patternCreated() {
        String sign = getGramarRegex(config);
        String prefix = RegexUtils.escapeExprSpecialWord(config.getGramerPrefix());
        String suffix = RegexUtils.escapeExprSpecialWord(config.getGramerSuffix());

        templatePattern = Pattern.compile(MessageFormat.format(FORMAT_TEMPLATE, prefix, sign,
                config.getGrammerRegex(), suffix));
        gramerPattern = Pattern.compile(MessageFormat.format(FORMAT_GRAMER, prefix, suffix));
    }

    private String getGramarRegex(Configure config) {
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

}
