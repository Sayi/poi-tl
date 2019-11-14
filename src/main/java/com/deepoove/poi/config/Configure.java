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
package com.deepoove.poi.config;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deepoove.poi.policy.AbstractRenderPolicy.ClearHandler;
import com.deepoove.poi.policy.AbstractRenderPolicy.ValidErrorHandler;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.policy.NumbericRenderPolicy;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.ReferenceRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.util.RegexUtils;

/**
 * 插件化配置
 * 
 * @author Sayi
 * @version 1.0.0
 */
public class Configure {

    // defalut expression
    private static final String DEFAULT_GRAMER_REGEX = "[\\w\\u4e00-\\u9fa5]+(\\.[\\w\\u4e00-\\u9fa5]+)*";

    // Highest priority
    private Map<String, RenderPolicy> customPolicys = new HashMap<String, RenderPolicy>();
    // Low priority
    private Map<Character, RenderPolicy> defaultPolicys = new HashMap<Character, RenderPolicy>();

    /**
     * 引用渲染策略
     */
    private List<ReferenceRenderPolicy<?>> referencePolicies = new ArrayList<>();

    /**
     * 语法前缀
     */
    private String gramerPrefix = "{{";
    /**
     * 语法后缀
     */
    private String gramerSuffix = "}}";

    /**
     * 默认支持中文、字母、数字、下划线的正则
     */
    private String grammerRegex = DEFAULT_GRAMER_REGEX;

    /**
     * 模板表达式模式，默认为POI_TL_MODE
     */
    private ELMode elMode = ELMode.POI_TL_STANDARD_MODE;

    /**
     * 渲染数据校验不通过时的处理策略
     * <ul>
     * <li>DiscardHandler: 什么都不做</li>
     * <li>ClearHandler: 清空标签</li>
     * <li>AbortHandler: 抛出异常</li>
     * </ul>
     */
    private ValidErrorHandler handler = new ClearHandler();

    private Configure() {
        plugin(GramerSymbol.TEXT, new TextRenderPolicy());
        plugin(GramerSymbol.IMAGE, new PictureRenderPolicy());
        plugin(GramerSymbol.TABLE, new MiniTableRenderPolicy());
        plugin(GramerSymbol.NUMBERIC, new NumbericRenderPolicy());
        plugin(GramerSymbol.DOCX_TEMPLATE, new DocxRenderPolicy());
    }

    /**
     * 获取默认配置
     */
    public static Configure createDefault() {
        return newBuilder().build();
    }

    /**
     * 获取构建器
     */
    public static ConfigureBuilder newBuilder() {
        return new ConfigureBuilder();
    }

    /**
     * 新增语法插件
     * 
     * @param c
     *            模板语法
     * @param policy
     *            策略
     */
    public Configure plugin(char c, RenderPolicy policy) {
        defaultPolicys.put(Character.valueOf(c), policy);
        return this;
    }

    Configure plugin(GramerSymbol symbol, RenderPolicy policy) {
        defaultPolicys.put(symbol.getSymbol(), policy);
        return this;
    }

    /**
     * 自定义模板
     * 
     * @param tagName
     *            模板名称
     * @param policy
     *            策略
     */
    public void customPolicy(String tagName, RenderPolicy policy) {
        customPolicys.put(tagName, policy);
    }

    /**
     * 新增引用渲染策略
     * 
     * @param policy
     */
    public void referencePolicy(ReferenceRenderPolicy<?> policy) {
        referencePolicies.add(policy);
    }

    /**
     * 获取标签策略
     * 
     * @param tagName
     *            模板名称
     * @param sign
     *            语法
     */
    public RenderPolicy getPolicy(String tagName, Character sign) {
        RenderPolicy policy = getCustomPolicy(tagName);
        return null == policy ? getDefaultPolicy(sign) : policy;
    }

    public List<ReferenceRenderPolicy<?>> getReferencePolicies() {
        return referencePolicies;
    }

    public Map<Character, RenderPolicy> getDefaultPolicys() {
        return defaultPolicys;
    }

    public Map<String, RenderPolicy> getCustomPolicys() {
        return customPolicys;
    }

    public Set<Character> getGramerChars() {
        return defaultPolicys.keySet();
    }

    public String getGramerPrefix() {
        return gramerPrefix;
    }

    public String getGramerSuffix() {
        return gramerSuffix;
    }

    private RenderPolicy getCustomPolicy(String tagName) {
        return customPolicys.get(tagName);
    }

    private RenderPolicy getDefaultPolicy(Character sign) {
        return defaultPolicys.get(sign);
    }

    public String getGrammerRegex() {
        return grammerRegex;
    }

    public ELMode getElMode() {
        return elMode;
    }

    public ValidErrorHandler getValidErrorHandler() {
        return handler;
    }

    public static class ConfigureBuilder {
        private static String regexForAllPattern = "((?!{0})(?!{1}).)*";
        private boolean regexForAll;
        private Configure config = new Configure();

        public ConfigureBuilder() {}

        public ConfigureBuilder buildGramer(String prefix, String suffix) {
            config.gramerPrefix = prefix;
            config.gramerSuffix = suffix;
            return this;
        }

        /**
         * 设置模板表达式模式
         * 
         * @return
         */
        public ConfigureBuilder setElMode(ELMode mode) {
            config.elMode = mode;
            return this;
        }

        public ConfigureBuilder buildGrammerRegex(String reg) {
            config.grammerRegex = reg;
            return this;
        }

        public ConfigureBuilder supportGrammerRegexForAll() {
            this.regexForAll = true;
            return this;
        }

        public ConfigureBuilder buildValidErrorHandler(ValidErrorHandler handler) {
            config.handler = handler;
            return this;
        }

        public ConfigureBuilder addPlugin(char c, RenderPolicy policy) {
            config.plugin(c, policy);
            return this;
        }

        public ConfigureBuilder customPolicy(String tagName, RenderPolicy policy) {
            config.customPolicy(tagName, policy);
            return this;
        }

        public ConfigureBuilder referencePolicy(ReferenceRenderPolicy<?> policy) {
            config.referencePolicy(policy);
            return this;
        }

        public Configure build() {
            if (config.elMode == ELMode.SPEL_MODE) {
                supportGrammerRegexForAll();
            }
            if (regexForAll) {
                buildGrammerRegex(MessageFormat.format(regexForAllPattern,
                        RegexUtils.escapeExprSpecialWord(config.gramerPrefix),
                        RegexUtils.escapeExprSpecialWord(config.gramerSuffix)));
            }
            return config;
        }

    }

}
