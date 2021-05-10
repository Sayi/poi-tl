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
package com.deepoove.poi.policy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.CollectionUtils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * Nested/Merge/Include/Reference docx render
 * 
 * @author Sayi
 */
public class DocxRenderPolicy extends AbstractRenderPolicy<DocxRenderData> {

    @Override
    protected boolean validate(DocxRenderData data) {
        return null != data && null != data.getMergedDoc();
    }

    @Override
    protected void beforeRender(RenderContext<DocxRenderData> context) {
        clearPlaceholder(context, false);
    }

    @Override
    public void doRender(RenderContext<DocxRenderData> context) throws Exception {
        NiceXWPFDocument doc = context.getXWPFDocument();
        XWPFTemplate template = context.getTemplate();
        doc = doc.merge(new XWPFDocumentIterator(context.getData(), context.getConfig()), context.getRun());
        template.reload(doc);
    }

    // use iterator to retrieve XWPFTemplate objects, for gc
    class XWPFDocumentIterator implements Iterator<NiceXWPFDocument> {

        private Configure config;
        private byte[] bytes;
        private List<?> datas;
        private int length;

        int cursor = 0;

        XWPFDocumentIterator(DocxRenderData data, Configure config) {
            this.bytes = data.getMergedDoc();
            this.datas = data.getDataModels();
            this.config = config;
            this.length = null == this.datas ? 1 : this.datas.size();
        }

        @Override
        public boolean hasNext() {
            return cursor < length;
        }

        @Override
        public NiceXWPFDocument next() {
            if (!hasNext()) throw new NoSuchElementException("No instance of NiceXWPFDocument");
            if (CollectionUtils.isEmpty(datas) && 0 == cursor) {
                try {
                    cursor++;
                    return new NiceXWPFDocument(new ByteArrayInputStream(bytes));
                } catch (IOException e) {
                    throw new RenderException("Create XWPFDocument error", e);
                }
            } else {
                // TODO performance, should compile template only once?
                XWPFTemplate temp = XWPFTemplate.compile(new ByteArrayInputStream(bytes), config);
                temp.render(datas.get(cursor++));
                return temp.getXWPFDocument();
            }

        }
    }
}
