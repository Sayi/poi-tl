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
package com.deepoove.poi.plugin.markdown.converter;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;

import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.markdown.MarkdownStyle;

/**
 * Immutable inline style context used while walking the CommonMark AST.
 * <p>
 * Markdown inline markup is nested ({@code **bold and [link](url)**}), and
 * {@link Style} of poi-tl is a mutable Java bean without a copy constructor.
 * Carrying a single mutable {@code Style} through the tree would leak state to
 * sibling nodes, so this class keeps the "what is currently active" flags and
 * builds a fresh {@link Style} only at the leaf (a run of text).
 * </p>
 *
 * @author Sayi
 */
final class InlineContext {

    /** Default colour of a hyperlink, mirrors {@code Texts.TextBuilder#link}. */
    private static final String LINK_COLOR = "0000FF";

    static final InlineContext EMPTY = new InlineContext(false, false, false, null, null, null);

    private final boolean bold;
    private final boolean italic;
    private final boolean code;
    private final String url;
    private final String anchor;
    private final MarkdownStyle style;

    private InlineContext(boolean bold, boolean italic, boolean code, String url, String anchor, MarkdownStyle style) {
        this.bold = bold;
        this.italic = italic;
        this.code = code;
        this.url = url;
        this.anchor = anchor;
        this.style = style;
    }

    InlineContext withStyle(MarkdownStyle style) {
        return new InlineContext(bold, italic, code, url, anchor, style);
    }

    InlineContext bold() {
        return new InlineContext(true, italic, code, url, anchor, style);
    }

    InlineContext italic() {
        return new InlineContext(bold, true, code, url, anchor, style);
    }

    InlineContext code() {
        return new InlineContext(bold, italic, true, url, anchor, style);
    }

    InlineContext link(String url) {
        return new InlineContext(bold, italic, code, url, anchor, style);
    }

    InlineContext anchor(String anchor) {
        return new InlineContext(bold, italic, code, url, anchor, style);
    }

    /**
     * The hyperlink target of this run, {@code null} when the run is not linked.
     *
     * @return url or anchor expression
     */
    String getUrl() {
        return url;
    }

    /**
     * The bookmark anchor name when the run points to a heading inside the same
     * document, {@code null} otherwise.
     *
     * @return anchor name
     */
    String getAnchor() {
        return anchor;
    }

    boolean isLinked() {
        return null != url || null != anchor;
    }

    /**
     * Builds a fresh {@link Style} for a leaf run, overlaying the active inline
     * flags on top of the configured base styles.
     *
     * @return a new style, never {@code null}
     */
    Style resolve() {
        Style resolved = new Style();
        if (code) {
            overlay(resolved, null == style ? null : style.getInlineCodeStyle());
        }
        if (bold) {
            overlay(resolved, null == style ? null : style.getStrongStyle());
        }
        if (italic) {
            overlay(resolved, null == style ? null : style.getEmphasisStyle());
        }
        if (isLinked()) {
            // Texts.TextBuilder only adds the default hyperlink style when no style
            // is set at all; since a style is always set here, apply it ourselves
            // so that a bold link still looks like a link.
            if (null == resolved.getColor()) resolved.setColor(LINK_COLOR);
            if (null == resolved.getUnderlinePatterns()) resolved.setUnderlinePatterns(UnderlinePatterns.SINGLE);
        }
        return resolved;
    }

    /**
     * Copies every property that {@code source} has actually set onto
     * {@code target} (per-property overlay, not a wholesale replacement).
     *
     * @param target the style being built
     * @param source the style providing values, may be {@code null}
     */
    private static void overlay(Style target, Style source) {
        if (null == source) return;
        if (null != source.getColor()) target.setColor(source.getColor());
        if (null != source.getFontFamily()) target.setFontFamily(source.getFontFamily());
        if (null != source.getWesternFontFamily()) target.setWesternFontFamily(source.getWesternFontFamily());
        if (0 != source.getFontSize()) target.setFontSize(source.getFontSize());
        if (null != source.isBold()) target.setBold(source.isBold());
        if (null != source.isItalic()) target.setItalic(source.isItalic());
        if (null != source.isStrike()) target.setStrike(source.isStrike());
        if (null != source.getUnderlinePatterns()) target.setUnderlinePatterns(source.getUnderlinePatterns());
        if (null != source.getUnderlineColor()) target.setUnderlineColor(source.getUnderlineColor());
        if (null != source.getHighlightColor()) target.setHighlightColor(source.getHighlightColor());
        if (0 != source.getCharacterSpacing()) target.setCharacterSpacing(source.getCharacterSpacing());
        if (null != source.getVertAlign()) target.setVertAlign(source.getVertAlign());
    }

}
