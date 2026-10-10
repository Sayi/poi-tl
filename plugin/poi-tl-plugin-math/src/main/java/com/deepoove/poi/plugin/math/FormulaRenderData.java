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

import com.deepoove.poi.data.RenderData;

/**
 * A formula rendered as native Word OMML, from LaTeX or from Presentation
 * MathML.
 * <p>
 * Holds only plain values, so the object is serializable and safe to share
 * across threads; the XML is built per document position at render time.
 *
 * @author Sayi
 */
public class FormulaRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private final String source;

    private final FormulaDialect dialect;

    private final boolean display;

    private FormulaStyle style;

    private String altMeta;

    FormulaRenderData(String source, FormulaDialect dialect, boolean display, FormulaStyle style, String altMeta) {
        this.source = source;
        this.dialect = dialect;
        this.display = display;
        this.style = style;
        this.altMeta = altMeta;
    }

    /**
     * @param source LaTeX math
     * @return a builder for an inline formula
     */
    public static FormulaRenderDataBuilder of(String source) {
        return new FormulaRenderDataBuilder(source, FormulaDialect.LATEX);
    }

    /**
     * @param source  formula text
     * @param dialect syntax of {@code source}
     * @return a builder for an inline formula
     */
    public static FormulaRenderDataBuilder of(String source, FormulaDialect dialect) {
        return new FormulaRenderDataBuilder(source, dialect);
    }

    public String getSource() {
        return source;
    }

    public FormulaDialect getDialect() {
        return dialect;
    }

    /**
     * @return true for a display equation ({@code m:oMathPara}), false for inline
     */
    public boolean isDisplay() {
        return display;
    }

    public FormulaStyle getStyle() {
        return style;
    }

    public void setStyle(FormulaStyle style) {
        this.style = style;
    }

    /**
     * @return text written when the formula cannot be built; null falls back to the
     *         source
     */
    public String getAltMeta() {
        return altMeta;
    }

    public void setAltMeta(String altMeta) {
        this.altMeta = altMeta;
    }

}
