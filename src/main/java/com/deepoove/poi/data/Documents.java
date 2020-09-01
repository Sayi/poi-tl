/*
 * Copyright 2014-2020 Sayi
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
 * Builder to build {@link DocumentRenderData}
 * 
 * @author Sayi
 *
 */
public class Documents implements RenderDataBuilder<DocumentRenderData> {

    private DocumentRenderData data;

    private Documents() {
    }

    public static Documents of() {
        Documents inst = new Documents();
        inst.data = new DocumentRenderData();
        return inst;
    }

    public Documents addParagraph(ParagraphRenderData paragraph) {
        data.addParagraph(paragraph);
        return this;
    }

    public Documents addNumbering(NumberingRenderData numbering) {
        data.addNumbering(numbering);
        return this;
    }

    public Documents addTable(TableRenderData table) {
        data.addTable(table);
        return this;
    }

    @Override
    public DocumentRenderData create() {
        return data;
    }

}
