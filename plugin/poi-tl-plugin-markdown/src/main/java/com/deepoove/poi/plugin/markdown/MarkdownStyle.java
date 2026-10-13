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
package com.deepoove.poi.plugin.markdown;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;

import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.highlight.HighlightStyle;

/**
 * Style of the Markdown rendering.
 * <p>
 * Every property has a usable default, so both {@code new MarkdownStyle()} and
 * {@link #builder()} return a complete configuration. The no argument
 * constructor is kept for frameworks that instantiate the style reflectively
 * (for example Gson when the style travels inside a JSON data model).
 * </p>
 *
 * @author Sayi
 */
public class MarkdownStyle implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default Word style ids of the six heading levels.
     * <p>
     * These are <em>not</em> the English {@code Heading1..Heading6}: a template
     * produced by a Chinese Word installation carries {@code w:styleId="1"} ...
     * {@code "6"} (the bundled {@code markdown_template.docx} does too), so
     * changing this default would silently strip the formatting of every
     * existing template. Use {@link #headingStyleId(int, String)} to switch.
     * </p>
     */
    private static final String[] DEFAULT_HEADING_STYLE_IDS = { "1", "2", "3", "4", "5", "6" };

    private Style inlineCodeStyle = defaultInlineCodeStyle();
    private HighlightStyle highlightStyle = defaultHighlightStyle();
    private ParagraphStyle quoteStyle = defaultQuoteStyle();
    private BorderStyle tableBorderStyle = defaultTableBorderStyle();
    private RowStyle tableHeaderStyle = defaultTableHeaderStyle();
    private Style strongStyle = defaultStrongStyle();
    private Style emphasisStyle = defaultEmphasisStyle();
    private ParagraphStyle thematicBreakStyle = defaultThematicBreakStyle();

    private String[] headingStyleIds = DEFAULT_HEADING_STYLE_IDS.clone();

    private boolean showHeaderNumber;
    private int headerNumberStartLevel = 2;
    private String headerNumberSuffix = ".";
    /**
     * Folder the relative images are resolved against. {@code null} means
     * "decide automatically": {@link FileMarkdownRenderData} then uses the
     * folder of the Markdown file, while an inline document falls back to the
     * working directory. An empty string is an explicit request for the working
     * directory and is never overridden.
     */
    private String imagesDir;
    private String softBreakText = " ";
    private boolean orderedListStartNumberFallback = true;

    public MarkdownStyle() {
    }

    private MarkdownStyle(Builder builder) {
        this.inlineCodeStyle = builder.inlineCodeStyle;
        this.highlightStyle = builder.highlightStyle;
        this.quoteStyle = builder.quoteStyle;
        this.tableBorderStyle = builder.tableBorderStyle;
        this.tableHeaderStyle = builder.tableHeaderStyle;
        this.strongStyle = builder.strongStyle;
        this.emphasisStyle = builder.emphasisStyle;
        this.thematicBreakStyle = builder.thematicBreakStyle;
        this.headingStyleIds = builder.headingStyleIds.clone();
        this.showHeaderNumber = builder.showHeaderNumber;
        this.headerNumberStartLevel = builder.headerNumberStartLevel;
        this.headerNumberSuffix = builder.headerNumberSuffix;
        this.imagesDir = builder.imagesDir;
        this.softBreakText = builder.softBreakText;
        this.orderedListStartNumberFallback = builder.orderedListStartNumberFallback;
    }

    /**
     * Creates a style with all defaults.
     *
     * @return a new style
     */
    public static MarkdownStyle newStyle() {
        return new MarkdownStyle();
    }

    /**
     * @return a builder initialised with the default style
     */
    public static Builder builder() {
        return new Builder();
    }

    // ------------------------------------------------------------------
    // defaults
    // ------------------------------------------------------------------

    private static Style defaultInlineCodeStyle() {
        return Style.builder().buildColor("f44336").buildFontFamily("Monaco").build();
    }

    private static HighlightStyle defaultHighlightStyle() {
        return HighlightStyle.builder().withTheme("zenburn").withFontFamily("Consolas").withFontSize(9.0f).build();
    }

    private static ParagraphStyle defaultQuoteStyle() {
        // Spacing is part of the default style on purpose: the former
        // implementation mutated the shared quote style while rendering each
        // paragraph, which leaked spacing between documents.
        return ParagraphStyle.builder()
                .withIndentLeftChars(1.0f)
                .withSpacingAfterLines(0.4)
                .withLeftBorder(BorderStyle.builder()
                        .withColor("70ad47")
                        .withSize(48)
                        .withSpace(8)
                        .withType(XWPFBorderType.SINGLE)
                        .build())
                .build();
    }

    private static BorderStyle defaultTableBorderStyle() {
        return BorderStyle.builder().withColor("BFBFBF").withSize(1).withType(XWPFBorderType.SINGLE).build();
    }

    private static RowStyle defaultTableHeaderStyle() {
        RowStyle headerStyle = new RowStyle();
        CellStyle cellStyle = new CellStyle();
        cellStyle.setBackgroundColor("5b9bd5");
        Style textStyle = new Style();
        textStyle.setColor("ffffff");
        cellStyle.setDefaultParagraphStyle(ParagraphStyle.builder().withDefaultTextStyle(textStyle).build());
        headerStyle.setDefaultCellStyle(cellStyle);
        return headerStyle;
    }

    private static Style defaultStrongStyle() {
        return Style.builder().buildBold().build();
    }

    private static Style defaultEmphasisStyle() {
        return Style.builder().buildItalic().build();
    }

    private static ParagraphStyle defaultThematicBreakStyle() {
        return ParagraphStyle.builder()
                .withBottomBorder(BorderStyle.builder()
                        .withColor("BFBFBF")
                        .withSize(6)
                        .withType(XWPFBorderType.SINGLE)
                        .build())
                .build();
    }

    // ------------------------------------------------------------------
    // accessors
    // ------------------------------------------------------------------

    public Style getInlineCodeStyle() {
        return inlineCodeStyle;
    }

    public void setInlineCodeStyle(Style inlineCodeStyle) {
        this.inlineCodeStyle = inlineCodeStyle;
    }

    public HighlightStyle getHighlightStyle() {
        return highlightStyle;
    }

    public void setHighlightStyle(HighlightStyle highlightStyle) {
        this.highlightStyle = highlightStyle;
    }

    public ParagraphStyle getQuoteStyle() {
        return quoteStyle;
    }

    public void setQuoteStyle(ParagraphStyle quoteStyle) {
        this.quoteStyle = quoteStyle;
    }

    public BorderStyle getTableBorderStyle() {
        return tableBorderStyle;
    }

    public void setTableBorderStyle(BorderStyle tableBorderStyle) {
        this.tableBorderStyle = tableBorderStyle;
    }

    public RowStyle getTableHeaderStyle() {
        return tableHeaderStyle;
    }

    public void setTableHeaderStyle(RowStyle tableHeaderStyle) {
        this.tableHeaderStyle = tableHeaderStyle;
    }

    /**
     * Style applied to text wrapped in {@code **...**}.
     *
     * @return the strong style, bold by default
     */
    public Style getStrongStyle() {
        return strongStyle;
    }

    public void setStrongStyle(Style strongStyle) {
        this.strongStyle = strongStyle;
    }

    /**
     * Style applied to text wrapped in {@code *...*}.
     *
     * @return the emphasis style, italic by default
     */
    public Style getEmphasisStyle() {
        return emphasisStyle;
    }

    public void setEmphasisStyle(Style emphasisStyle) {
        this.emphasisStyle = emphasisStyle;
    }

    /**
     * Paragraph style used to draw a Markdown thematic break ({@code ---}).
     *
     * @return the thematic break style, a bottom bordered paragraph by default
     */
    public ParagraphStyle getThematicBreakStyle() {
        return thematicBreakStyle;
    }

    public void setThematicBreakStyle(ParagraphStyle thematicBreakStyle) {
        this.thematicBreakStyle = thematicBreakStyle;
    }

    public boolean isShowHeaderNumber() {
        return showHeaderNumber;
    }

    public void setShowHeaderNumber(boolean showHeaderNumber) {
        this.showHeaderNumber = showHeaderNumber;
    }

    /**
     * First heading level taking part in numbering. {@code 1} numbers every
     * heading, {@code 2} (the default) leaves a level 1 heading unnumbered and
     * uses it as the document title.
     *
     * @return the first numbered heading level, 1..6
     */
    public int getHeaderNumberStartLevel() {
        return headerNumberStartLevel;
    }

    public void setHeaderNumberStartLevel(int headerNumberStartLevel) {
        this.headerNumberStartLevel = headerNumberStartLevel;
    }

    /**
     * Text appended after the last counter of a heading number, {@code "."} by
     * default ({@code 1.}, {@code 1.1.}). Set it to an empty string for
     * {@code 1}, {@code 1.1}.
     *
     * @return the trailing text of a heading number
     */
    public String getHeaderNumberSuffix() {
        return headerNumberSuffix;
    }

    public void setHeaderNumberSuffix(String headerNumberSuffix) {
        this.headerNumberSuffix = headerNumberSuffix;
    }

    /**
     * @return the folder of the relative images, {@code null} when it should be
     *         decided automatically, {@code ""} for an explicit working directory
     */
    public String getImagesDir() {
        return imagesDir;
    }

    public void setImagesDir(String imagesDir) {
        this.imagesDir = imagesDir;
    }

    /**
     * Text a Markdown soft line break is rendered as. CommonMark folds a soft
     * break into a space; Chinese documents sometimes prefer no separator at
     * all, which can be configured here.
     *
     * @return the replacement text of a soft line break
     */
    public String getSoftBreakText() {
        return softBreakText;
    }

    public void setSoftBreakText(String softBreakText) {
        this.softBreakText = softBreakText;
    }

    /**
     * Whether an ordered list that does not start at 1 is rendered as plain
     * indented paragraphs carrying the original numbers.
     * <p>
     * Word numbering is created by the poi-tl core with a hard coded start
     * value of 1 and {@code NumberingFormat} transports no start attribute, so
     * a list written as {@code 3. item} would otherwise be printed as
     * {@code 1)}.
     * </p>
     *
     * @return {@code true} when the textual fallback is enabled
     */
    public boolean isOrderedListStartNumberFallback() {
        return orderedListStartNumberFallback;
    }

    public void setOrderedListStartNumberFallback(boolean orderedListStartNumberFallback) {
        this.orderedListStartNumberFallback = orderedListStartNumberFallback;
    }

    /**
     * @return a copy of the configured heading style ids
     */
    public String[] getHeadingStyleIds() {
        return headingStyleIds.clone();
    }

    public void setHeadingStyleIds(String[] headingStyleIds) {
        this.headingStyleIds = null == headingStyleIds ? new String[0] : headingStyleIds.clone();
    }

    /**
     * Word style id of a heading level.
     *
     * @param level heading level, 1..6
     * @return the style id, or {@code null} when not configured
     */
    public String getHeadingStyleId(int level) {
        if (null == headingStyleIds || level < 1 || level > headingStyleIds.length) return null;
        String styleId = headingStyleIds[level - 1];
        return null == styleId || styleId.isEmpty() ? null : styleId;
    }

    /**
     * Sets the Word style id of a heading level, so that templates naming their
     * headings {@code Heading1..Heading6} can be supported.
     *
     * @param level   heading level, 1..6
     * @param styleId Word style id, {@code null} to fall back to inline formatting
     * @return this style, for chaining
     */
    public MarkdownStyle headingStyleId(int level, String styleId) {
        if (level >= 1 && level <= 6) {
            if (null == headingStyleIds || headingStyleIds.length < 6) {
                headingStyleIds = Arrays.copyOf(DEFAULT_HEADING_STYLE_IDS, 6);
            }
            headingStyleIds[level - 1] = styleId;
        }
        return this;
    }

    /**
     * Switches all six heading levels to the English Word style names
     * {@code Heading1..Heading6}.
     *
     * @return this style, for chaining
     */
    public MarkdownStyle useEnglishHeadingStyles() {
        this.headingStyleIds = new String[] { "Heading1", "Heading2", "Heading3", "Heading4", "Heading5", "Heading6" };
        return this;
    }

    /**
     * Builder of {@link MarkdownStyle}.
     *
     * @author Sayi
     */
    public static final class Builder {

        private Style inlineCodeStyle = defaultInlineCodeStyle();
        private HighlightStyle highlightStyle = defaultHighlightStyle();
        private ParagraphStyle quoteStyle = defaultQuoteStyle();
        private BorderStyle tableBorderStyle = defaultTableBorderStyle();
        private RowStyle tableHeaderStyle = defaultTableHeaderStyle();
        private Style strongStyle = defaultStrongStyle();
        private Style emphasisStyle = defaultEmphasisStyle();
        private ParagraphStyle thematicBreakStyle = defaultThematicBreakStyle();
        private String[] headingStyleIds = DEFAULT_HEADING_STYLE_IDS.clone();
        private boolean showHeaderNumber;
        private int headerNumberStartLevel = 2;
        private String headerNumberSuffix = ".";
        private String imagesDir;
        private String softBreakText = " ";
        private boolean orderedListStartNumberFallback = true;

        private Builder() {
        }

        public Builder withInlineCodeStyle(Style inlineCodeStyle) {
            this.inlineCodeStyle = inlineCodeStyle;
            return this;
        }

        public Builder withHighlightStyle(HighlightStyle highlightStyle) {
            this.highlightStyle = highlightStyle;
            return this;
        }

        public Builder withQuoteStyle(ParagraphStyle quoteStyle) {
            this.quoteStyle = quoteStyle;
            return this;
        }

        public Builder withTableBorderStyle(BorderStyle tableBorderStyle) {
            this.tableBorderStyle = tableBorderStyle;
            return this;
        }

        public Builder withTableHeaderStyle(RowStyle tableHeaderStyle) {
            this.tableHeaderStyle = tableHeaderStyle;
            return this;
        }

        public Builder withStrongStyle(Style strongStyle) {
            this.strongStyle = strongStyle;
            return this;
        }

        public Builder withEmphasisStyle(Style emphasisStyle) {
            this.emphasisStyle = emphasisStyle;
            return this;
        }

        public Builder withThematicBreakStyle(ParagraphStyle thematicBreakStyle) {
            this.thematicBreakStyle = thematicBreakStyle;
            return this;
        }

        public Builder withShowHeaderNumber(boolean showHeaderNumber) {
            this.showHeaderNumber = showHeaderNumber;
            return this;
        }

        public Builder withHeaderNumberStartLevel(int headerNumberStartLevel) {
            this.headerNumberStartLevel = headerNumberStartLevel;
            return this;
        }

        public Builder withHeaderNumberSuffix(String headerNumberSuffix) {
            this.headerNumberSuffix = headerNumberSuffix;
            return this;
        }

        public Builder withImagesDir(String imagesDir) {
            this.imagesDir = imagesDir;
            return this;
        }

        public Builder withSoftBreakText(String softBreakText) {
            this.softBreakText = softBreakText;
            return this;
        }

        public Builder withOrderedListStartNumberFallback(boolean fallback) {
            this.orderedListStartNumberFallback = fallback;
            return this;
        }

        public Builder withHeadingStyleIds(String[] headingStyleIds) {
            this.headingStyleIds = null == headingStyleIds ? new String[0] : headingStyleIds.clone();
            return this;
        }

        public Builder withHeadingStyleId(int level, String styleId) {
            if (level >= 1 && level <= 6) {
                if (headingStyleIds.length < 6) {
                    headingStyleIds = Arrays.copyOf(DEFAULT_HEADING_STYLE_IDS, 6);
                }
                headingStyleIds[level - 1] = styleId;
            }
            return this;
        }

        public Builder withEnglishHeadingStyles() {
            this.headingStyleIds = new String[] { "Heading1", "Heading2", "Heading3", "Heading4", "Heading5",
                    "Heading6" };
            return this;
        }

        public MarkdownStyle build() {
            return new MarkdownStyle(this);
        }
    }

}
