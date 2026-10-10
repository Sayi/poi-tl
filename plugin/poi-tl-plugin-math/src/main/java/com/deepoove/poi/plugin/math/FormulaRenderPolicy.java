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
package com.deepoove.poi.plugin.math;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.officeDocument.x2006.math.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.plugin.math.generator.OmmlBuilder;
import com.deepoove.poi.plugin.math.generator.ParsedFormula;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.ParagraphUtils;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

/**
 * Renders a LaTeX or Presentation MathML formula as native Word OMML in place of
 * the template tag.
 * <p>
 * Register it explicitly with
 * {@code Configure.builder().addPlugin('$', new FormulaRenderPolicy())} to enable
 * the {@code {{$equation}}} syntax, or bind it to a tag name. There is no
 * zero-configuration {@code {{@tag}}} channel: {@code FormulaRenderData} is not a
 * picture, so the built in image policy does not apply.
 *
 * @author Sayi
 */
public class FormulaRenderPolicy extends AbstractRenderPolicy<FormulaRenderData> {

    private static final String FALLBACK_PREFIX = "[formula] ";

    private final ObjectToFormulaRenderDataConverter converter = new ObjectToFormulaRenderDataConverter();

    /**
     * Never throws: an unsupported data type clears the tag instead of aborting the
     * whole document, because {@code cast} runs outside of the exception handling of
     * {@link AbstractRenderPolicy#render}.
     */
    @Override
    public FormulaRenderData cast(Object source) {
        try {
            return converter.convert(source);
        } catch (Exception e) {
            logger.warn("Unsupported latex data, the template tag will be cleared: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Only {@code null} is rejected here; an empty or invalid formula is handled in
     * {@link #doRender} so that it can fall back to the alt text.
     */
    @Override
    protected boolean validate(FormulaRenderData data) {
        return null != data;
    }

    @Override
    public void doRender(RenderContext<FormulaRenderData> context) throws Exception {
        XWPFRun run = context.getRun();
        if (!(run.getParent() instanceof XWPFParagraph)) {
            throw new RenderException("Latex placeholder must sit in a paragraph: " + context.getEleTemplate());
        }
        XWPFParagraph paragraph = (XWPFParagraph) run.getParent();

        FormulaRenderData data = context.getData();
        String source = data.getSource();
        if (null == source || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Formula source is empty");
        }

        // Parse before touching the document: on failure the placeholder run is still
        // in place and the fallback text can be written into it, and no orphan
        // m:oMath is left behind.
        ParsedFormula formula = OmmlBuilder.parse(source, data.getDialect());

        // Both are read before the run is removed.
        CTRPr inherited = run.getCTR().isSetRPr() ? (CTRPr) run.getCTR().getRPr().copy() : null;
        Integer runPos = ParagraphUtils.getRunPos(run);
        if (null == runPos) {
            throw new RenderException("Latex placeholder run cannot be located: " + context.getEleTemplate());
        }

        CTP ctp = paragraph.getCTP();
        if (data.isDisplay()) {
            CTOMathPara paragraphMath = ctp.addNewOMathPara();
            paragraphMath.addNewOMathParaPr().addNewJc().setVal(toJc(data.getStyle()));
            OmmlBuilder.fill(paragraphMath.addNewOMath(), formula, inherited, data.getStyle());
            OmmlInserter.moveBefore(paragraphMath, run.getCTR());
        } else {
            CTOMath math = ctp.addNewOMath();
            OmmlBuilder.fill(math, formula, inherited, data.getStyle());
            OmmlInserter.moveBefore(math, run.getCTR());
        }
        new XWPFParagraphWrapper(paragraph).removeRun(runPos);
    }

    /**
     * The placeholder run was removed by {@link #doRender}, so there is nothing left
     * to clear.
     */
    @Override
    protected void afterRender(RenderContext<FormulaRenderData> context) {
    }

    @Override
    protected void reThrowException(RenderContext<FormulaRenderData> context, Exception e) {
        logger.error("Render formula {} error: {}", context.getEleTemplate(), e.getMessage());
        FormulaRenderData data = context.getData();
        String altMeta = null == data ? null : data.getAltMeta();
        if (null == altMeta || altMeta.isEmpty()) {
            // Write the source back: it keeps the intent readable, unlike a generic
            // "render failed" note, and never drops information silently.
            altMeta = FALLBACK_PREFIX + (null == data ? "" : data.getSource());
        }
        context.getRun().setText(altMeta, 0);
    }

    private static STJc.Enum toJc(FormulaStyle style) {
        FormulaAlign align = null == style ? null : style.getAlign();
        if (FormulaAlign.LEFT == align) return STJc.LEFT;
        if (FormulaAlign.RIGHT == align) return STJc.RIGHT;
        if (FormulaAlign.CENTER_GROUP == align) return STJc.CENTER_GROUP;
        return STJc.CENTER;
    }

}
