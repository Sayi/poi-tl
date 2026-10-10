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


/**
 * Immutable per-render context: the character formatting plus the math style
 * inherited from an enclosing {@code \mathrm} / {@code \mathbf} / {@code \mathit}.
 *
 * @author Sayi
 */
final class OmmlContext {

    private static final OmmlContext EMPTY = new OmmlContext(OmmlStyle.of(null, null), null, null);

    private final OmmlStyle style;

    private final FormulaNode.MathStyle mathStyle;

    private final FormulaNode.MathScript mathScript;

    private OmmlContext(OmmlStyle style, FormulaNode.MathStyle mathStyle, FormulaNode.MathScript mathScript) {
        this.style = style;
        this.mathStyle = mathStyle;
        this.mathScript = mathScript;
    }

    static OmmlContext of(OmmlStyle style) {
        return null == style ? EMPTY : new OmmlContext(style, null, null);
    }

    OmmlContext withMathStyle(FormulaNode.MathStyle mathStyle) {
        return null == mathStyle ? this : new OmmlContext(style, mathStyle, mathScript);
    }

    OmmlContext withMathScript(FormulaNode.MathScript mathScript) {
        return null == mathScript ? this : new OmmlContext(style, mathStyle, mathScript);
    }

    OmmlStyle style() {
        return style;
    }

    boolean hasRpr() {
        return style.hasRpr();
    }

    /**
     * @return the emphasis, or null to leave Word's automatic math italic
     */
    FormulaNode.MathStyle mathStyle() {
        return mathStyle;
    }

    /**
     * @return the {@code m:scr} value, or null for the default math font
     */
    FormulaNode.MathScript mathScript() {
        return mathScript;
    }

}
