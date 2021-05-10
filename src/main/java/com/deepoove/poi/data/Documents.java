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
package com.deepoove.poi.data;

/**
 * Factory method to create {@link DocumentRenderData}
 * 
 * @author Sayi
 *
 */
public class Documents {

    private Documents() {
    }

    public static DocumentBuilder of() {
        return new DocumentBuilder();
    }

    /**
     * Builder to build {@link DocumentRenderData}
     *
     */
    public static class DocumentBuilder implements RenderDataBuilder<DocumentRenderData> {

        private DocumentRenderData data;

        private DocumentBuilder() {
            data = new DocumentRenderData();
        }

        public DocumentBuilder addParagraph(ParagraphRenderData paragraph) {
            data.addParagraph(paragraph);
            return this;
        }

        public DocumentBuilder addNumbering(NumberingRenderData numbering) {
            data.addNumbering(numbering);
            return this;
        }

        public DocumentBuilder addTable(TableRenderData table) {
            data.addTable(table);
            return this;
        }

        public DocumentBuilder addDocument(DocumentRenderData document) {
            data.getContents().addAll(document.getContents());
            return this;
        }

        @Override
        public DocumentRenderData create() {
            return data;
        }
    }

}
