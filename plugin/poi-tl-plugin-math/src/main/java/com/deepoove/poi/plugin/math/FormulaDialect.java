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

/**
 * The source syntax of a formula.
 *
 * @author Sayi
 */
public enum FormulaDialect {

    /** LaTeX math, for example {@code \frac{a}{b}}. */
    LATEX,

    /** Presentation MathML, for example {@code <mfrac><mi>a</mi><mi>b</mi></mfrac>}. */
    MATHML

}
