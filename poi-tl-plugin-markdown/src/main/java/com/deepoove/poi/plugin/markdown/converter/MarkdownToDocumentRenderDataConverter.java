/*
 * Copyright 2014-2021 Sayi
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

import java.util.Arrays;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import com.deepoove.poi.converter.ToRenderDataConverter;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.plugin.markdown.MarkdownRenderData;

/**
 * MarkdownRenderData to DocumentRenderData Converter
 * 
 * @author Sayi
 */
public class MarkdownToDocumentRenderDataConverter
        implements ToRenderDataConverter<MarkdownRenderData, DocumentRenderData> {

    private Parser parser;

    public MarkdownToDocumentRenderDataConverter() {
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        this.parser = Parser.builder().extensions(extensions).build();
    }

    @Override
    public DocumentRenderData convert(MarkdownRenderData data) throws Exception {
        Node node = parser.parse(data.getMarkdown());
        DocumentVisitor visitor = new DocumentVisitor(data.getStyle());
        node.accept(visitor);
        return visitor.getResult();
    }

}
