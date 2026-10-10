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
 * An opaque handle to a parsed formula.
 * <p>
 * It exists so a caller can turn source text into a formula <em>before</em>
 * touching the document: a syntax error then leaves the placeholder untouched,
 * which is what the fallback path needs. The syntax tree itself stays package
 * private, so passing this handle is the only thing an outside caller can do
 * with it.
 *
 * @author Sayi
 */
public final class ParsedFormula {

    private final FormulaNode node;

    ParsedFormula(FormulaNode node) {
        this.node = node;
    }

    FormulaNode node() {
        return node;
    }

}
