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

import java.nio.file.Path;
import java.nio.file.Paths;

import com.deepoove.poi.data.RenderDataBuilder;

/**
 * Shortcut factory of {@link MarkdownRenderData}, in the style of the core
 * {@code Texts} / {@code Pictures} / {@code Tables} facades and of the
 * {@code Formulas} facade of the math plugin.
 * <p>
 * The plain constructor and {@link MarkdownStyle#newStyle()} keep working; this
 * class only adds a fluent entry point.
 * </p>
 *
 * @author Sayi
 */
public final class Markdowns {

    private Markdowns() {
    }

    /**
     * @param markdown Markdown source
     * @return a builder for an inline Markdown document
     */
    public static MarkdownRenderDataBuilder of(String markdown) {
        MarkdownRenderData data = new MarkdownRenderData();
        data.setMarkdown(markdown);
        return new MarkdownRenderDataBuilder(data);
    }

    /**
     * @param filePath path of a Markdown file
     * @return a builder reading its content from the file
     */
    public static MarkdownRenderDataBuilder ofFile(String filePath) {
        return ofFile(Paths.get(filePath));
    }

    /**
     * @param file path of a Markdown file
     * @return a builder reading its content from the file
     */
    public static MarkdownRenderDataBuilder ofFile(Path file) {
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(file.toString());
        return new MarkdownRenderDataBuilder(data);
    }

    /**
     * @param directory root of a docsify documentation
     * @return a builder expanding the whole documentation
     */
    public static MarkdownRenderDataBuilder ofDocsify(String directory) {
        return ofDocsify(Paths.get(directory));
    }

    /**
     * @param directory root of a docsify documentation
     * @return a builder expanding the whole documentation
     */
    public static MarkdownRenderDataBuilder ofDocsify(Path directory) {
        FileMarkdownRenderData data = new FileMarkdownRenderData();
        data.setPath(directory.toString());
        return new MarkdownRenderDataBuilder(data);
    }

    /**
     * @return a builder of {@link MarkdownStyle}
     */
    public static MarkdownStyle.Builder style() {
        return MarkdownStyle.builder();
    }

    /**
     * Builder of {@link MarkdownRenderData}.
     *
     * @author Sayi
     */
    public static final class MarkdownRenderDataBuilder implements RenderDataBuilder<MarkdownRenderData> {

        private final MarkdownRenderData data;

        private MarkdownRenderDataBuilder(MarkdownRenderData data) {
            this.data = data;
        }

        /**
         * @param markdown Markdown source, replacing the file based content
         * @return this builder
         */
        public MarkdownRenderDataBuilder markdown(String markdown) {
            data.setMarkdown(markdown);
            return this;
        }

        /**
         * @param style the rendering style
         * @return this builder
         */
        public MarkdownRenderDataBuilder style(MarkdownStyle style) {
            data.setStyle(style);
            return this;
        }

        /**
         * @param style a style builder
         * @return this builder
         */
        public MarkdownRenderDataBuilder style(MarkdownStyle.Builder style) {
            return style(style.build());
        }

        @Override
        public MarkdownRenderData create() {
            return data;
        }
    }

}
