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

import com.deepoove.poi.data.RenderDataBuilder;

/**
 * Fluent builder of {@link FormulaRenderData}.
 * <p>
 * The data object is created on {@link #build()}, so the builder itself is not
 * shared state; every built object is independent.
 *
 * @author Sayi
 */
public class FormulaRenderDataBuilder implements RenderDataBuilder<FormulaRenderData> {

    private final String source;
    private final FormulaDialect dialect;
    private final FormulaStyle style = new FormulaStyle();
    private boolean display;
    private String altMeta;

    FormulaRenderDataBuilder(String source, FormulaDialect dialect) {
        if (null == source) {
            throw new IllegalArgumentException("Formula source must not be null");
        }
        this.source = source;
        this.dialect = dialect;
    }

    /**
     * Render as a display equation, centred on its own line ({@code m:oMathPara}).
     */
    public FormulaRenderDataBuilder display() {
        this.display = true;
        return this;
    }

    /**
     * Render inline, in the flow of the surrounding text ({@code m:oMath}). Default.
     */
    public FormulaRenderDataBuilder inline() {
        this.display = false;
        return this;
    }

    /** @param fontSize Word half-points, e.g. 24 for 12pt */
    public FormulaRenderDataBuilder fontSize(int fontSize) {
        style.setFontSize(fontSize);
        return this;
    }

    /** @param color six digit hex RGB, e.g. {@code C00000} */
    public FormulaRenderDataBuilder color(String color) {
        style.setColor(color);
        return this;
    }

    public FormulaRenderDataBuilder bold(boolean bold) {
        style.setBold(bold);
        return this;
    }

    public FormulaRenderDataBuilder italic(boolean italic) {
        style.setItalic(italic);
        return this;
    }

    public FormulaRenderDataBuilder mathFont(String mathFont) {
        style.setMathFont(mathFont);
        return this;
    }

    public FormulaRenderDataBuilder align(FormulaAlign align) {
        style.setAlign(align);
        return this;
    }

    /** @param altMeta text used when the formula cannot be built */
    public FormulaRenderDataBuilder altMeta(String altMeta) {
        this.altMeta = altMeta;
        return this;
    }

    @Override
    public FormulaRenderData create() {
        return build();
    }

    public FormulaRenderData build() {
        return new FormulaRenderData(source, dialect, display, style, altMeta);
    }

}
