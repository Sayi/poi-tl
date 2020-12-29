/*
 * Copyright 2014-2020 Sayi
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
import java.util.Map;

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
 * 
 * @author Sayi
 *
 */
public class ConfigureBuilder {
    private Configure config;
    private boolean usedSpringEL;
    private boolean changeRegex;

    ConfigureBuilder() {
        config = new Configure();
    }

    public ConfigureBuilder buildGramer(String prefix, String suffix) {
        config.gramerPrefix = prefix;
        config.gramerSuffix = suffix;
        return this;
    }

    public ConfigureBuilder buidIterableLeft(char c) {
        config.iterable = Pair.of(c, config.iterable.getRight());
        return this;
    }

    public ConfigureBuilder buildGrammerRegex(String reg) {
        changeRegex = true;
        config.grammerRegex = reg;
        return this;
    }

    public ConfigureBuilder useSpringEL() {
        return useSpringEL(true);
    }

    public ConfigureBuilder useSpringEL(boolean isStrict) {
        usedSpringEL = true;
        return setRenderDataComputeFactory(model -> new SpELRenderDataCompute(model, isStrict));
    }

    public ConfigureBuilder useSpringEL(Map<String, Method> spELFunction) {
        usedSpringEL = true;
        return setRenderDataComputeFactory(model -> new SpELRenderDataCompute(model, true, spELFunction));
    }

    public ConfigureBuilder useDefaultEL(boolean isStrict) {
        usedSpringEL = false;
        return setRenderDataComputeFactory(model -> new DefaultELRenderDataCompute(model, isStrict));
    }

    public ConfigureBuilder setValidErrorHandler(ValidErrorHandler handler) {
        config.handler = handler;
        return this;
    }

    public ConfigureBuilder setRenderDataComputeFactory(RenderDataComputeFactory renderDataComputeFactory) {
        config.renderDataComputeFactory = renderDataComputeFactory;
        return this;
    }

    public ConfigureBuilder setElementTemplateFactory(ElementTemplateFactory elementTemplateFactory) {
        config.elementTemplateFactory = elementTemplateFactory;
        return this;
    }

    public ConfigureBuilder addPlugin(char c, RenderPolicy policy) {
        config.plugin(c, policy);
        return this;
    }

    public ConfigureBuilder addPlugin(Class<? extends MetaTemplate> clazz, RenderPolicy policy) {
        config.plugin(clazz, policy);
        return this;
    }

    public ConfigureBuilder addPlugin(ChartTypes chartType, RenderPolicy policy) {
        config.plugin(chartType, policy);
        return this;
    }

    public ConfigureBuilder bind(String tagName, RenderPolicy policy) {
        config.customPolicy(tagName, policy);
        return this;
    }

    public Configure build() {
        if (usedSpringEL && !changeRegex) {
            config.grammerRegex = RegexUtils.createGeneral(config.gramerPrefix, config.gramerSuffix);
        }
        return config;
    }
}