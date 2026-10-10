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
package com.deepoove.poi.plugin.math.generator;

import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTR;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTRPR;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTText;
import org.openxmlformats.schemas.officeDocument.x2006.math.STScript;
import org.openxmlformats.schemas.officeDocument.x2006.math.STStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

import com.deepoove.poi.plugin.math.FormulaDialect;
import com.deepoove.poi.plugin.math.FormulaStyle;

/**
 * Turns a {@link FormulaNode} tree into native OMML.
 * <p>
 * The two API traps of the generated schema bindings are handled here once, so
 * no other class has to know about them:
 * <ul>
 * <li>the math text element is {@code CTR.addNewT2()} - {@code addNewT()} writes a
 * plain {@code w:t} into the math run;</li>
 * <li>the word run properties of a math run are {@code CTR.setRPr2()} -
 * {@code setRPr()} writes {@code m:rPr}, the math properties.</li>
 * </ul>
 *
 * @author Sayi
 */
public final class OmmlBuilder {

    private OmmlBuilder() {
    }

    /**
     * Appends a formula to an existing {@code m:oMath}.
     * <p>
     * This is the only public entry of the generator: the syntax tree stays an
     * implementation detail, so the dialect front ends can evolve freely.
     *
     * @param source  LaTeX math or Presentation MathML
     * @param dialect syntax of {@code source}
     * @return an opaque handle, pass it to
     *         {@link #fill(CTOMath, ParsedFormula, CTRPr, FormulaStyle)}
     */
    public static ParsedFormula parse(String source, FormulaDialect dialect) {
        FormulaNode node = FormulaDialect.MATHML == dialect ? MathMlParser.parse(source) : LatexParser.parse(source);
        return new ParsedFormula(node);
    }

    /**
     * Appends an already parsed formula to an existing {@code m:oMath}.
     *
     * @param math      target, already attached to the paragraph
     * @param formula   result of {@link #parse(String, FormulaDialect)}
     * @param inherited detached copy of the placeholder run's {@code w:rPr}, or null
     * @param style     explicit overrides, or null
     */
    public static void fill(CTOMath math, ParsedFormula formula, CTRPr inherited, FormulaStyle style) {
        formula.node().render(OmmlContainer.of(math), OmmlContext.of(OmmlStyle.of(inherited, style)));
    }

    static void fill(CTOMathArg argument, FormulaNode node, OmmlContext ctx) {
        node.render(OmmlContainer.of(argument), ctx);
    }

    static void run(OmmlContainer target, String text, boolean upright, OmmlContext ctx) {
        if (null == text || text.isEmpty()) return;
        CTR run = target.addRun();
        applyMathStyle(run, upright, ctx);
        // unconditional: a math run always carries the math font
        ctx.style().applyTo(run.addNewRPr2());
        CTText mathText = run.addNewT2();
        mathText.setStringValue(text);
        if (needsPreserve(text)) {
            mathText.setSpace(SpaceAttribute.Space.PRESERVE);
        }
    }

    static void ctrl(CTCtrlPr target, OmmlContext ctx) {
        ctx.style().applyTo(target.addNewRPr());
    }

    /**
     * Writes the two orthogonal halves of the math style.
     * <p>
     * An explicit emphasis (from {@code \mathrm} / {@code \mathbf} / mathvariant)
     * wins over the upright default, so bold text stays bold. A font variant is
     * always upright - every {@code m:scr} value is - so it implies plain.
     */
    private static void applyMathStyle(CTR run, boolean upright, OmmlContext ctx) {
        FormulaNode.MathStyle emphasis = ctx.mathStyle();
        FormulaNode.MathScript script = ctx.mathScript();
        if (null == emphasis) {
            emphasis = upright || null != script ? FormulaNode.MathStyle.PLAIN : null;
        }
        if (null == emphasis && null == script) return;
        CTRPR properties = run.addNewRPr();
        if (null != script) properties.addNewScr().setVal(toScr(script));
        if (null != emphasis) properties.addNewSty().setVal(toSty(emphasis));
    }

    static STScript.Enum toScr(FormulaNode.MathScript mathScript) {
        if (FormulaNode.MathScript.SCRIPT == mathScript) return STScript.SCRIPT;
        if (FormulaNode.MathScript.FRAKTUR == mathScript) return STScript.FRAKTUR;
        if (FormulaNode.MathScript.DOUBLE_STRUCK == mathScript) return STScript.DOUBLE_STRUCK;
        if (FormulaNode.MathScript.SANS_SERIF == mathScript) return STScript.SANS_SERIF;
        return STScript.MONOSPACE;
    }

    static STStyle.Enum toSty(FormulaNode.MathStyle mathStyle) {
        if (FormulaNode.MathStyle.BOLD_ITALIC == mathStyle) return STStyle.BI;
        if (FormulaNode.MathStyle.BOLD == mathStyle) return STStyle.B;
        if (FormulaNode.MathStyle.ITALIC == mathStyle) return STStyle.I;
        return STStyle.P;
    }

    /**
     * XmlBeans does not add {@code xml:space} on its own, and Word collapses
     * significant spacing without it, so every text containing whitespace is
     * marked.
     */
    private static boolean needsPreserve(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) <= ' ') return true;
        }
        return false;
    }

}
