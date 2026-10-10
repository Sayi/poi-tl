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
 * 
 * @author Sayi
 * @deprecated misspelled, use {@link GrammarSymbol} instead.
 */
@Deprecated
public interface GramerSymbol {

    GrammarSymbol IMAGE = GrammarSymbol.IMAGE;

    GrammarSymbol TEXT = GrammarSymbol.TEXT;

    GrammarSymbol TEXT_ALIAS = GrammarSymbol.TEXT_ALIAS;

    GrammarSymbol TABLE = GrammarSymbol.TABLE;

    GrammarSymbol NUMBERING = GrammarSymbol.NUMBERING;

    GrammarSymbol DOCX_TEMPLATE = GrammarSymbol.DOCX_TEMPLATE;

    GrammarSymbol ITERABLE_START = GrammarSymbol.ITERABLE_START;

    GrammarSymbol BLOCK_END = GrammarSymbol.BLOCK_END;

}
