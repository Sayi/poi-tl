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

import com.deepoove.poi.converter.ToRenderDataConverter;

/**
 * Converts plain values into {@link FormulaRenderData}.
 * <p>
 * A {@link CharSequence} is always rendered inline: the shape is never guessed
 * from the content, so a display equation has to be asked for explicitly with
 * {@link Formulas#display(String)}. The dialect is taken from the syntax
 * marker, since a bare String cannot carry one.
 *
 * @author Sayi
 */
public class ObjectToFormulaRenderDataConverter implements ToRenderDataConverter<Object, FormulaRenderData> {

    @Override
    public FormulaRenderData convert(Object source) throws Exception {
        if (null == source) return null;
        if (source instanceof FormulaRenderData) return (FormulaRenderData) source;
        if (source instanceof CharSequence || source instanceof Number || source instanceof Character) {
            // A bare String carries no dialect, so the syntax marker decides: a formula
            // that starts a known MathML element is MathML, everything else is LaTeX.
            return Formulas.auto(source.toString());
        }
        throw new IllegalArgumentException("Unsupported data for FormulaRenderPolicy: " + source.getClass().getName());
    }

}
