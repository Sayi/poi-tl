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
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.XDDFBubbleChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.NumberingRenderPolicy;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.policy.reference.BubbleChartTemplateRenderPolicy;
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
 * <p>
 * It holds the template grammar (tag prefix, tag suffix and tag regular
 * expression), the built-in and custom {@link RenderPolicy} bindings, the
 * {@link RenderDataComputeFactory}, the {@link ElementTemplateFactory} and the
 * {@link ValidErrorHandler} used while resolving and rendering a template.
 * </p>
 * 
 * @author Sayi
 */
public class Configure implements Cloneable {

    /**
     * regular expression: Chinese, letters, numbers and underscores
     */
    public static final String DEFAULT_GRAMMAR_REGEX = "((#)?[\\w\\u4e00-\\u9fa5]+(\\.[\\w\\u4e00-\\u9fa5]+)*)?";

    /**
     * regular expression: Chinese, letters, numbers and underscores
     * 
     * @deprecated misspelled, use {@link #DEFAULT_GRAMMAR_REGEX} instead.
     */
    @Deprecated
    public static final String DEFAULT_GRAMER_REGEX = DEFAULT_GRAMMAR_REGEX;

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
     * template by xddf chart data class: Medium priority
     */
    protected final Map<Class<? extends XDDFChartData>, RenderPolicy> DEFAULT_CHART_DATA_POLICYS = new HashMap<>();

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
    protected Pair<Character, Character> iterable = Pair.of(GrammarSymbol.ITERABLE_START.getSymbol(),
            GrammarSymbol.BLOCK_END.getSymbol());

    /**
     * tag prefix
     */
    protected String grammarPrefix = "{{";

    /**
     * tag suffix
     */
    protected String grammarSuffix = "}}";

    /**
     * tag regular expression
     */
    protected String grammarRegex = DEFAULT_GRAMMAR_REGEX;

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
        plugin(GrammarSymbol.TEXT, new TextRenderPolicy());
        plugin(GrammarSymbol.TEXT_ALIAS, new TextRenderPolicy());
        plugin(GrammarSymbol.IMAGE, new PictureRenderPolicy());
        plugin(GrammarSymbol.TABLE, new TableRenderPolicy());
        plugin(GrammarSymbol.NUMBERING, new NumberingRenderPolicy());
        plugin(GrammarSymbol.DOCX_TEMPLATE, new DocxRenderPolicy());

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

        pluginChart(XDDFBubbleChartData.class, new BubbleChartTemplateRenderPolicy());

        plugin(PictureTemplate.class, new DefaultPictureTemplateRenderPolicy());
        plugin(PictImageTemplate.class, new DefaultPictImageTemplateRenderPolicy());
        plugin(ChartTemplate.class, new DefaultChartTemplateRenderPolicy());
    }

    /**
     * Create the default config
     * <p>
     * The default config registers all the built-in plugins and uses the default
     * expression language.
     * </p>
     * 
     * @return the default {@link Configure} instance
     */
    public static Configure createDefault() {
        return builder().build();
    }

    /**
     * Create a builder to build {@link Configure}
     * 
     * @return a new {@link ConfigureBuilder} instance
     */
    public static ConfigureBuilder builder() {
        return new ConfigureBuilder();
    }

    /**
     * Add a grammar plugin bound to a grammar char
     * 
     * @param c      grammar char
     * @param policy render policy of the grammar char
     * @return this config
     */
    public Configure plugin(char c, RenderPolicy policy) {
        DEFAULT_POLICYS.put(Character.valueOf(c), policy);
        return this;
    }

    Configure plugin(GrammarSymbol symbol, RenderPolicy policy) {
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

    /**
     * Add a chart plugin bound to a XDDF chart data class
     * 
     * @param chartDataClass XDDF chart data class
     * @param policy         render policy of the chart data class
     * @return this config
     */
    public Configure pluginChart(Class<? extends XDDFChartData> chartDataClass, RenderPolicy policy) {
        DEFAULT_CHART_DATA_POLICYS.put(chartDataClass, policy);
        return this;
    }

    /**
     * Bind a render policy to a tag name, it has the highest priority
     * 
     * @param tagName tag name
     * @param policy  render policy of the tag name
     */
    public void customPolicy(String tagName, RenderPolicy policy) {
        CUSTOM_POLICYS.put(tagName, policy);
    }

    /**
     * Get the render policy bound to a template class
     * 
     * @param clazz template class
     * @return render policy, or null if no policy is bound to the template class
     */
    public RenderPolicy getTemplatePolicy(Class<?> clazz) {
        return DEFAULT_TEMPLATE_POLICYS.get(clazz);
    }

    /**
     * Get the render policy bound to a tag name
     * 
     * @param tagName tag name
     * @return render policy, or null if the tag name is not bound
     */
    public RenderPolicy getCustomPolicy(String tagName) {
        return CUSTOM_POLICYS.get(tagName);
    }

    /**
     * Get the render policy bound to a grammar char
     * 
     * @param sign grammar char
     * @return render policy, or null if the grammar char is not bound
     */
    public RenderPolicy getDefaultPolicy(Character sign) {
        return DEFAULT_POLICYS.get(sign);
    }

    /**
     * Get the render policy bound to a chart type
     * 
     * @param type chart type
     * @return render policy, or null if the chart type is not bound
     */
    public RenderPolicy getChartPolicy(ChartTypes type) {
        return DEFAULT_CHART_POLICYS.get(type);
    }

    /**
     * Get the render policy bound to a XDDF chart data class
     * 
     * @param chartDataClass XDDF chart data class
     * @return render policy, or null if the chart data class is not bound
     */
    public RenderPolicy getChartPolicy(Class<? extends XDDFChartData> chartDataClass) {
        return DEFAULT_CHART_DATA_POLICYS.get(chartDataClass);
    }

    /**
     * Get all the render policies bound to grammar chars
     * 
     * @return grammar char to render policy map
     */
    public Map<Character, RenderPolicy> getDefaultPolicys() {
        return DEFAULT_POLICYS;
    }

    /**
     * Get all the render policies bound to tag names
     * 
     * @return tag name to render policy map
     */
    public Map<String, RenderPolicy> getCustomPolicys() {
        return CUSTOM_POLICYS;
    }

    /**
     * Get all the render policies bound to chart types
     * 
     * @return chart type to render policy map
     */
    public Map<ChartTypes, RenderPolicy> getChartPolicys() {
        return DEFAULT_CHART_POLICYS;
    }

    /**
     * Get the grammar chars, including the registered grammar chars and the
     * if/foreach block start and end chars
     * 
     * @return grammar chars
     */
    public Set<Character> getGrammarChars() {
        Set<Character> ret = new HashSet<Character>(DEFAULT_POLICYS.keySet());
        // ? /
        ret.add(iterable.getKey());
        ret.add(iterable.getValue());
        return ret;
    }

    /**
     * Get the tag prefix, the default value is "{{"
     * 
     * @return tag prefix
     */
    public String getGrammarPrefix() {
        return grammarPrefix;
    }

    /**
     * Get the tag suffix, the default value is "}}"
     * 
     * @return tag suffix
     */
    public String getGrammarSuffix() {
        return grammarSuffix;
    }

    /**
     * Get the tag regular expression
     * 
     * @return tag regular expression
     */
    public String getGrammarRegex() {
        return grammarRegex;
    }

    /**
     * Get the grammar chars
     * 
     * @return grammar chars
     * @deprecated misspelled, use {@link #getGrammarChars()} instead.
     */
    @Deprecated
    public Set<Character> getGramerChars() {
        return getGrammarChars();
    }

    /**
     * Get the tag prefix
     * 
     * @return tag prefix
     * @deprecated misspelled, use {@link #getGrammarPrefix()} instead.
     */
    @Deprecated
    public String getGramerPrefix() {
        return getGrammarPrefix();
    }

    /**
     * Get the tag suffix
     * 
     * @return tag suffix
     * @deprecated misspelled, use {@link #getGrammarSuffix()} instead.
     */
    @Deprecated
    public String getGramerSuffix() {
        return getGrammarSuffix();
    }

    /**
     * Get the tag regular expression
     * 
     * @return tag regular expression
     * @deprecated misspelled, use {@link #getGrammarRegex()} instead.
     */
    @Deprecated
    public String getGrammerRegex() {
        return getGrammarRegex();
    }

    /**
     * Get the handler of a valid render data error, such as a null or illegal
     * value
     * 
     * @return valid error handler
     */
    public ValidErrorHandler getValidErrorHandler() {
        return handler;
    }

    /**
     * Get the factory of the render data compute
     * 
     * @return render data compute factory
     */
    public RenderDataComputeFactory getRenderDataComputeFactory() {
        return renderDataComputeFactory;
    }

    /**
     * Get the factory of the element template
     * 
     * @return element template factory
     */
    public ElementTemplateFactory getElementTemplateFactory() {
        return elementTemplateFactory;
    }

    /**
     * Get the start and end chars of the if &amp; foreach block
     * 
     * @return pair of the block start char and the block end char
     */
    public Pair<Character, Character> getIterable() {
        return iterable;
    }

    /**
     * Get the custom static methods of SpEL
     * 
     * @return function name to static method map
     */
    public Map<String, Method> getSpELFunction() {
        return spELFunction;
    }

    /**
     * Get the castors that cast the render data before rendering
     * 
     * @return list of pre render data castors
     */
    public List<PreRenderDataCastor> getPreRenderDataCastors() {
        return preRenderDataCastors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Configure Info").append(":\n");
        sb.append("  Basic grammar: ").append(grammarPrefix).append(grammarSuffix).append("\n");
        sb.append("  If and foreach grammar: ").append(grammarPrefix).append(iterable.getLeft())
                .append(grammarSuffix);
        sb.append(grammarPrefix).append(iterable.getRight()).append(grammarSuffix).append("\n");
        sb.append("  Regex:").append(grammarRegex).append("\n");
        sb.append("  Valid Error Handler: ").append(handler.getClass().getSimpleName()).append("\n");
        sb.append("  Default Plugin: ").append("\n");
        DEFAULT_POLICYS.forEach((chara, policy) -> {
            sb.append("    ").append(grammarPrefix).append(chara.charValue()).append(grammarSuffix);
            sb.append("->").append(policy.getClass().getSimpleName()).append("\n");
        });
        sb.append("  Bind Plugin: ").append("\n");
        CUSTOM_POLICYS.forEach((str, policy) -> {
            sb.append("    ").append(grammarPrefix).append(str).append(grammarSuffix);
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

    /**
     * Copy a config with a new tag prefix and suffix
     * <p>
     * The tag regular expression is rebuilt from the new prefix and suffix.
     * </p>
     * 
     * @param prefix new tag prefix
     * @param suffix new tag suffix
     * @return the copied config
     * @throws CloneNotSupportedException if the config cannot be cloned
     */
    public Configure copy(String prefix, String suffix) throws CloneNotSupportedException {
        Configure clone = clone();
        clone.grammarPrefix = prefix;
        clone.grammarSuffix = suffix;
        clone.grammarRegex = RegexUtils.createGeneral(clone.grammarPrefix, clone.grammarSuffix);
        return clone;
    }

    /**
     * The handler of a valid render data error, such as a null or illegal value
     */
    public interface ValidErrorHandler {
        /**
         * Handle the render data error of a tag
         * 
         * @param context render context of the tag
         */
        void handler(RenderContext<?> context);
    }

    public static class DiscardHandler implements ValidErrorHandler {
        /**
         * {@inheritDoc}
         * <p>
         * Discard the error silently.
         * </p>
         */
        @Override
        public void handler(RenderContext<?> context) {
            // no-op
        }
    }

    public static class ClearHandler implements ValidErrorHandler {
        /**
         * {@inheritDoc}
         * <p>
         * Clear the placeholder of the tag.
         * </p>
         */
        @Override
        public void handler(RenderContext<?> context) {
            XWPFRun run = context.getRun();
            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
            bodyContainer.clearPlaceholder(run);
        }
    }

    public static class AbortHandler implements ValidErrorHandler {
        /**
         * {@inheritDoc}
         * <p>
         * Abort the render by throwing a {@link RenderException}.
         * </p>
         */
        @Override
        public void handler(RenderContext<?> context) {
            throw new RenderException("Non-existent variable and a variable with illegal value for "
                    + context.getTagSource() + ", data: " + context.getData());
        }
    }

}
