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
package com.deepoove.poi.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.NumberingRenderPolicy;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.policy.reference.DefaultChartTemplateRenderPolicy;
import com.deepoove.poi.policy.reference.DefaultPictImageTemplateRenderPolicy;
import com.deepoove.poi.policy.reference.DefaultPictureTemplateRenderPolicy;
import com.deepoove.poi.policy.reference.MultiSeriesChartTemplateRenderPolicy;
import com.deepoove.poi.policy.reference.SingleSeriesChartTemplateRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.render.compute.DefaultELRenderDataCompute;
import com.deepoove.poi.render.compute.RenderDataComputeFactory;
import com.deepoove.poi.resolver.DefaultElementTemplateFactory;
import com.deepoove.poi.resolver.ElementTemplateFactory;
import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.PictImageTemplate;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.util.RegexUtils;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

/**
 * The config of template
 * 
 * @author Sayi
 */
public class Configure implements Cloneable {

    /**
     * regular expression: Chinese, letters, numbers and underscores
     */
    public static final String DEFAULT_GRAMER_REGEX = "((#)?[\\w\\u4e00-\\u9fa5]+(\\.[\\w\\u4e00-\\u9fa5]+)*)?";

    /**
     * template by bind: Highest priority
     */
    protected final Map<String, RenderPolicy> CUSTOM_POLICYS = new HashMap<String, RenderPolicy>();

    /**
     * template by xwpfRun: Medium priority
     */
    protected final Map<Character, RenderPolicy> DEFAULT_POLICYS = new HashMap<Character, RenderPolicy>();

    /**
     * template by xwpfchart: Medium priority
     */
    protected final Map<ChartTypes, RenderPolicy> DEFAULT_CHART_POLICYS = new EnumMap<ChartTypes, RenderPolicy>(
            ChartTypes.class);

    /**
     * template by element template: Lowest priority
     */
    protected final Map<Class<? extends MetaTemplate>, RenderPolicy> DEFAULT_TEMPLATE_POLICYS = new HashMap<>();

    /**
     * if & for each
     * <p>
     * eg. {{?user}} Hello, World {{/user}}
     * </p>
     */
    protected Pair<Character, Character> iterable = Pair.of(GramerSymbol.ITERABLE_START.getSymbol(),
            GramerSymbol.BLOCK_END.getSymbol());

    /**
     * tag prefix
     */
    protected String gramerPrefix = "{{";

    /**
     * tag suffix
     */
    protected String gramerSuffix = "}}";

    /**
     * tag regular expression
     */
    protected String grammerRegex = DEFAULT_GRAMER_REGEX;

    /**
     * the factory of render data compute
     */
    protected RenderDataComputeFactory renderDataComputeFactory = model -> new DefaultELRenderDataCompute(model, false);

    /**
     * the factory of resolver run template
     */
    protected ElementTemplateFactory elementTemplateFactory = new DefaultElementTemplateFactory();

    /**
     * the policy of process tag for valid render data error(null or illegal)
     */
    protected ValidErrorHandler handler = new ClearHandler();

    /**
     * sp el custom static method
     */
    protected Map<String, Method> spELFunction = new HashMap<String, Method>();

    /**
     * pre render data castor
     */
    protected List<PreRenderDataCastor> preRenderDataCastors = new ArrayList<>();

    Configure() {
        plugin(GramerSymbol.TEXT, new TextRenderPolicy());
        plugin(GramerSymbol.TEXT_ALIAS, new TextRenderPolicy());
        plugin(GramerSymbol.IMAGE, new PictureRenderPolicy());
        plugin(GramerSymbol.TABLE, new TableRenderPolicy());
        plugin(GramerSymbol.NUMBERING, new NumberingRenderPolicy());
        plugin(GramerSymbol.DOCX_TEMPLATE, new DocxRenderPolicy());

        RenderPolicy multiSeriesRenderPolicy = new MultiSeriesChartTemplateRenderPolicy();
        plugin(ChartTypes.AREA, multiSeriesRenderPolicy);
        plugin(ChartTypes.AREA3D, multiSeriesRenderPolicy);
        plugin(ChartTypes.BAR, multiSeriesRenderPolicy);
        plugin(ChartTypes.BAR3D, multiSeriesRenderPolicy);
        plugin(ChartTypes.LINE, multiSeriesRenderPolicy);
        plugin(ChartTypes.LINE3D, multiSeriesRenderPolicy);
        plugin(ChartTypes.RADAR, multiSeriesRenderPolicy);
        plugin(ChartTypes.SCATTER, multiSeriesRenderPolicy);

        RenderPolicy singleSeriesRenderPolicy = new SingleSeriesChartTemplateRenderPolicy();
        plugin(ChartTypes.PIE, singleSeriesRenderPolicy);
        plugin(ChartTypes.PIE3D, singleSeriesRenderPolicy);
        plugin(ChartTypes.DOUGHNUT, singleSeriesRenderPolicy);

        plugin(PictureTemplate.class, new DefaultPictureTemplateRenderPolicy());
        plugin(PictImageTemplate.class, new DefaultPictImageTemplateRenderPolicy());
        plugin(ChartTemplate.class, new DefaultChartTemplateRenderPolicy());
    }

    /**
     * create default config
     * 
     * @return
     */
    public static Configure createDefault() {
        return builder().build();
    }

    /**
     * Builder to build {@link Configure}
     * 
     * @return
     */
    public static ConfigureBuilder builder() {
        return new ConfigureBuilder();
    }

    /**
     * add grammar plugin
     * 
     * @param c      grammar char
     * @param policy render function
     */
    public Configure plugin(char c, RenderPolicy policy) {
        DEFAULT_POLICYS.put(Character.valueOf(c), policy);
        return this;
    }

    Configure plugin(GramerSymbol symbol, RenderPolicy policy) {
        DEFAULT_POLICYS.put(symbol.getSymbol(), policy);
        return this;
    }

    Configure plugin(Class<? extends MetaTemplate> clazz, RenderPolicy policy) {
        DEFAULT_TEMPLATE_POLICYS.put(clazz, policy);
        return this;
    }

    Configure plugin(ChartTypes chartType, RenderPolicy policy) {
        DEFAULT_CHART_POLICYS.put(chartType, policy);
        return this;
    }

    public void customPolicy(String tagName, RenderPolicy policy) {
        CUSTOM_POLICYS.put(tagName, policy);
    }

    public RenderPolicy getTemplatePolicy(Class<?> clazz) {
        return DEFAULT_TEMPLATE_POLICYS.get(clazz);
    }

    public RenderPolicy getCustomPolicy(String tagName) {
        return CUSTOM_POLICYS.get(tagName);
    }

    public RenderPolicy getDefaultPolicy(Character sign) {
        return DEFAULT_POLICYS.get(sign);
    }

    public RenderPolicy getChartPolicy(ChartTypes type) {
        return DEFAULT_CHART_POLICYS.get(type);
    }

    public Map<Character, RenderPolicy> getDefaultPolicys() {
        return DEFAULT_POLICYS;
    }

    public Map<String, RenderPolicy> getCustomPolicys() {
        return CUSTOM_POLICYS;
    }

    public Map<ChartTypes, RenderPolicy> getChartPolicys() {
        return DEFAULT_CHART_POLICYS;
    }

    public Set<Character> getGramerChars() {
        Set<Character> ret = new HashSet<Character>(DEFAULT_POLICYS.keySet());
        // ? /
        ret.add(iterable.getKey());
        ret.add(iterable.getValue());
        return ret;
    }

    public String getGramerPrefix() {
        return gramerPrefix;
    }

    public String getGramerSuffix() {
        return gramerSuffix;
    }

    public String getGrammerRegex() {
        return grammerRegex;
    }

    public ValidErrorHandler getValidErrorHandler() {
        return handler;
    }

    public RenderDataComputeFactory getRenderDataComputeFactory() {
        return renderDataComputeFactory;
    }

    public ElementTemplateFactory getElementTemplateFactory() {
        return elementTemplateFactory;
    }

    public Pair<Character, Character> getIterable() {
        return iterable;
    }

    public Map<String, Method> getSpELFunction() {
        return spELFunction;
    }

    public List<PreRenderDataCastor> getPreRenderDataCastors() {
        return preRenderDataCastors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Configure Info").append(":\n");
        sb.append("  Basic gramer: ").append(gramerPrefix).append(gramerSuffix).append("\n");
        sb.append("  If and foreach gramer: ").append(gramerPrefix).append(iterable.getLeft()).append(gramerSuffix);
        sb.append(gramerPrefix).append(iterable.getRight()).append(gramerSuffix).append("\n");
        sb.append("  Regex:").append(grammerRegex).append("\n");
        sb.append("  Valid Error Handler: ").append(handler.getClass().getSimpleName()).append("\n");
        sb.append("  Default Plugin: ").append("\n");
        DEFAULT_POLICYS.forEach((chara, policy) -> {
            sb.append("    ").append(gramerPrefix).append(chara.charValue()).append(gramerSuffix);
            sb.append("->").append(policy.getClass().getSimpleName()).append("\n");
        });
        sb.append("  Bind Plugin: ").append("\n");
        CUSTOM_POLICYS.forEach((str, policy) -> {
            sb.append("    ").append(gramerPrefix).append(str).append(gramerSuffix);
            sb.append("->").append(policy.getClass().getSimpleName()).append("\n");
        });
        sb.append("  Chart Plugin: ").append("\n");
        DEFAULT_CHART_POLICYS.forEach((type, policy) -> {
            sb.append("    ").append(type);
            sb.append("->").append(policy.getClass().getSimpleName()).append("\n");
        });
        sb.append("  Template Plugin: ").append("\n");
        DEFAULT_TEMPLATE_POLICYS.forEach((clazz, policy) -> {
            sb.append("    ").append(clazz.getSimpleName());
            sb.append("->").append(policy.getClass().getSimpleName()).append("\n");
        });
        sb.append("  SpELFunction: ").append("\n");
        spELFunction.forEach((str, method) -> {
            sb.append("    ").append(str);
            sb.append("->").append(method.toString()).append("\n");
        });
        return sb.toString();
    }

    @Override
    protected Configure clone() throws CloneNotSupportedException {
        // shallow clone
        return (Configure) super.clone();
    }

    public Configure copy(String prefix, String suffix) throws CloneNotSupportedException {
        Configure clone = clone();
        clone.gramerPrefix = prefix;
        clone.gramerSuffix = suffix;
        clone.grammerRegex = RegexUtils.createGeneral(clone.gramerPrefix, clone.gramerSuffix);
        return clone;
    }

    public interface ValidErrorHandler {
        void handler(RenderContext<?> context);
    }

    public static class DiscardHandler implements ValidErrorHandler {
        @Override
        public void handler(RenderContext<?> context) {
            // no-op
        }
    }

    public static class ClearHandler implements ValidErrorHandler {
        @Override
        public void handler(RenderContext<?> context) {
            XWPFRun run = context.getRun();
            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
            bodyContainer.clearPlaceholder(run);
        }
    }

    public static class AbortHandler implements ValidErrorHandler {
        @Override
        public void handler(RenderContext<?> context) {
            throw new RenderException("Non-existent variable and a variable with illegal value for "
                    + context.getTagSource() + ", data: " + context.getData());
        }
    }

}
