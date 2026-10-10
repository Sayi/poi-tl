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

/**
 * Built-in template syntax.
 * <p>
 * Deprecated misspelled alias of {@link GrammarSymbol}, kept for backward
 * compatibility.
 * </p>
 * 
 * @author Sayi
 * @deprecated misspelled, use {@link GrammarSymbol} instead.
 */
@Deprecated
public interface GramerSymbol {

    /** Picture in the template, alias of {@link GrammarSymbol#IMAGE} */
    GrammarSymbol IMAGE = GrammarSymbol.IMAGE;

    /** Text in the template, alias of {@link GrammarSymbol#TEXT} */
    GrammarSymbol TEXT = GrammarSymbol.TEXT;

    /** Text alias in the template, alias of {@link GrammarSymbol#TEXT_ALIAS} */
    GrammarSymbol TEXT_ALIAS = GrammarSymbol.TEXT_ALIAS;

    /** Table in the template, alias of {@link GrammarSymbol#TABLE} */
    GrammarSymbol TABLE = GrammarSymbol.TABLE;

    /** Numbering in the template, alias of {@link GrammarSymbol#NUMBERING} */
    GrammarSymbol NUMBERING = GrammarSymbol.NUMBERING;

    /** Nested docx in the template, alias of {@link GrammarSymbol#DOCX_TEMPLATE} */
    GrammarSymbol DOCX_TEMPLATE = GrammarSymbol.DOCX_TEMPLATE;

    /** Block start, alias of {@link GrammarSymbol#ITERABLE_START} */
    GrammarSymbol ITERABLE_START = GrammarSymbol.ITERABLE_START;

    /** Block end, alias of {@link GrammarSymbol#BLOCK_END} */
    GrammarSymbol BLOCK_END = GrammarSymbol.BLOCK_END;

}
