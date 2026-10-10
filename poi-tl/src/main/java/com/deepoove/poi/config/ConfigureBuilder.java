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
package com.deepoove.poi.config;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;

import com.deepoove.poi.config.Configure.ValidErrorHandler;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.compute.DefaultELRenderDataCompute;
import com.deepoove.poi.render.compute.RenderDataComputeFactory;
import com.deepoove.poi.render.compute.SpELRenderDataCompute;
import com.deepoove.poi.resolver.ElementTemplateFactory;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.util.RegexUtils;

/**
 * Builder to build {@link Configure}
 * <p>
 * It customizes the template grammar, the expression language, the render
 * policies and the render hooks, then builds a {@link Configure} instance.
 * </p>
 * 
 * @author Sayi
 *
 */
public class ConfigureBuilder {
    private Configure config;
    private boolean usedSpringEL;
    private boolean changeRegex;

    /**
     * Create a builder with a default config
     */
    ConfigureBuilder() {
        config = new Configure();
    }

    /**
     * Customize the tag prefix and suffix of the template syntax
     * 
     * @param prefix tag prefix
     * @param suffix tag suffix
     * @return builder
     */
    public ConfigureBuilder buildGrammar(String prefix, String suffix) {
        config.grammarPrefix = prefix;
        config.grammarSuffix = suffix;
        return this;
    }

    /**
     * Customize the start symbol of the if &amp; foreach block
     * 
     * @param c block start char
     * @return builder
     */
    public ConfigureBuilder buildIterableLeft(char c) {
        config.iterable = Pair.of(c, config.iterable.getRight());
        return this;
    }

    /**
     * Customize the tag regular expression
     * 
     * @param reg tag regex
     * @return builder
     */
    public ConfigureBuilder buildGrammarRegex(String reg) {
        changeRegex = true;
        config.grammarRegex = reg;
        return this;
    }

    /**
     * @param prefix tag prefix
     * @param suffix tag suffix
     * @return builder
     * @deprecated misspelled, use {@link #buildGrammar(String, String)} instead.
     */
    @Deprecated
    public ConfigureBuilder buildGramer(String prefix, String suffix) {
        return buildGrammar(prefix, suffix);
    }

    /**
     * @param c block start char
     * @return builder
     * @deprecated misspelled, use {@link #buildIterableLeft(char)} instead.
     */
    @Deprecated
    public ConfigureBuilder buidIterableLeft(char c) {
        return buildIterableLeft(c);
    }

    /**
     * @param reg tag regex
     * @return builder
     * @deprecated misspelled, use {@link #buildGrammarRegex(String)} instead.
     */
    @Deprecated
    public ConfigureBuilder buildGrammerRegex(String reg) {
        return buildGrammarRegex(reg);
    }

    /**
     * Use Spring Expression Language in strict mode
     * 
     * @return this builder
     */
    public ConfigureBuilder useSpringEL() {
        return useSpringEL(true);
    }

    /**
     * Use Spring Expression Language
     * 
     * @param isStrict whether the expression is parsed in strict mode
     * @return this builder
     */
    public ConfigureBuilder useSpringEL(boolean isStrict) {
        usedSpringEL = true;
        return setRenderDataComputeFactory(model -> new SpELRenderDataCompute(model, isStrict));
    }

    /**
     * Use Spring Expression Language with custom static methods
     * 
     * @param spELFunction function name to static method map
     * @return this builder
     */
    public ConfigureBuilder useSpringEL(Map<String, Method> spELFunction) {
        usedSpringEL = true;
        return setRenderDataComputeFactory(model -> new SpELRenderDataCompute(model, true, spELFunction));
    }

    /**
     * Use the default expression language
     * 
     * @param isStrict whether the expression is parsed in strict mode
     * @return this builder
     */
    public ConfigureBuilder useDefaultEL(boolean isStrict) {
        usedSpringEL = false;
        return setRenderDataComputeFactory(model -> new DefaultELRenderDataCompute(model, isStrict));
    }

    /**
     * Set the handler of a valid render data error, such as a null or illegal
     * value
     * 
     * @param handler valid error handler
     * @return this builder
     */
    public ConfigureBuilder setValidErrorHandler(ValidErrorHandler handler) {
        config.handler = handler;
        return this;
    }

    /**
     * Set the factory of the render data compute
     * 
     * @param renderDataComputeFactory render data compute factory
     * @return this builder
     */
    public ConfigureBuilder setRenderDataComputeFactory(RenderDataComputeFactory renderDataComputeFactory) {
        config.renderDataComputeFactory = renderDataComputeFactory;
        return this;
    }

    /**
     * Set the factory of the element template
     * 
     * @param elementTemplateFactory element template factory
     * @return this builder
     */
    public ConfigureBuilder setElementTemplateFactory(ElementTemplateFactory elementTemplateFactory) {
        config.elementTemplateFactory = elementTemplateFactory;
        return this;
    }

    /**
     * Set the castors that cast the render data before rendering
     * 
     * @param providers list of pre render data castors
     * @return this builder
     */
    public ConfigureBuilder setPreRenderDataCastors(List<PreRenderDataCastor> providers) {
        config.preRenderDataCastors = providers;
        return this;
    }

    /**
     * Add a castor that casts the render data before rendering
     * 
     * @param provider pre render data castor
     * @return this builder
     */
    public ConfigureBuilder addPreRenderDataCastor(PreRenderDataCastor provider) {
        config.preRenderDataCastors.add(provider);
        return this;
    }

    /**
     * Add a grammar plugin bound to a grammar char
     * 
     * @param c      grammar char
     * @param policy render policy of the grammar char
     * @return this builder
     */
    public ConfigureBuilder addPlugin(char c, RenderPolicy policy) {
        config.plugin(c, policy);
        return this;
    }

    /**
     * Add a template plugin bound to a template class
     * 
     * @param clazz  template class
     * @param policy render policy of the template class
     * @return this builder
     */
    public ConfigureBuilder addPlugin(Class<? extends MetaTemplate> clazz, RenderPolicy policy) {
        config.plugin(clazz, policy);
        return this;
    }

    /**
     * Add a chart plugin bound to a chart type
     * 
     * @param chartType chart type
     * @param policy    render policy of the chart type
     * @return this builder
     */
    public ConfigureBuilder addPlugin(ChartTypes chartType, RenderPolicy policy) {
        config.plugin(chartType, policy);
        return this;
    }

    /**
     * Bind a render policy to a tag name
     * 
     * @param tagName tag name
     * @param policy  render policy of the tag name
     * @return this builder
     */
    public ConfigureBuilder bind(String tagName, RenderPolicy policy) {
        config.customPolicy(tagName, policy);
        return this;
    }

    /**
     * Bind a render policy to multiple tag names
     * 
     * @param policy   render policy of the tag names
     * @param tagNames tag names
     * @return this builder
     */
    public ConfigureBuilder bind(RenderPolicy policy, String... tagNames) {
        Stream.of(tagNames).forEach(tagName -> config.customPolicy(tagName, policy));
        return this;
    }

    /**
     * Build the config
     * <p>
     * When Spring Expression Language is enabled and the tag regular expression
     * was not customized, the regular expression is rebuilt from the tag prefix
     * and suffix.
     * </p>
     * 
     * @return the built {@link Configure} instance
     */
    public Configure build() {
        if (usedSpringEL && !changeRegex) {
            config.grammarRegex = RegexUtils.createGeneral(config.grammarPrefix, config.grammarSuffix);
        }
        return config;
    }
}