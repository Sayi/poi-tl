/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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

import org.apache.commons.collections4.CollectionUtils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * <p>
 * word模板List循环渲染后合并
 * </p>
 * 
 * @author Sayi
 * @version 1.3.0
 */
public class DocxRenderPolicy extends AbstractRenderPolicy<DocxRenderData> {

    @Override
    protected boolean validate(DocxRenderData data) {
        return null != data;
    }

    @Override
    protected void beforeRender(RenderContext<DocxRenderData> context) {
        clearPlaceholder(context, false);
    }

    @Override
    public void doRender(RenderContext<DocxRenderData> context) throws Exception {
        NiceXWPFDocument doc = context.getXWPFDocument();
        XWPFTemplate template = context.getTemplate();
        doc = doc.merge(new XWPFDocumentIterator(context.getData(), context.getConfig()),
                context.getRun());
        template.reload(doc);
    }

    // use iterator to retrieve XWPFTemplate objects, for gc
    class XWPFDocumentIterator implements Iterator<NiceXWPFDocument> {

        private Configure config;
        private byte[] docx;
        private List<?> datas;

        int cursor = 0;

        XWPFDocumentIterator(DocxRenderData data, Configure config) {
            this.docx = data.getDocx();
            this.datas = data.getRenderDatas();
            this.config = config;
        }

        @Override
        public boolean hasNext() {
            if (CollectionUtils.isEmpty(datas)) { return cursor != Integer.MAX_VALUE; }
            return cursor < datas.size();
        }

        @Override
        public NiceXWPFDocument next() {
            if (CollectionUtils.isEmpty(datas)) {
                if (cursor != Integer.MAX_VALUE) {
                    try {
                        cursor = Integer.MAX_VALUE;
                        // 待合并的文档不是模板
                        return new NiceXWPFDocument(new ByteArrayInputStream(docx));
                    } catch (IOException e) {
                        throw new RenderException("Next NiceXWPFDocument", e);
                    }
                }
            } else {
                // TODO performance, should compile template only once?
                XWPFTemplate temp = XWPFTemplate.compile(new ByteArrayInputStream(docx), config);
                temp.render(datas.get(cursor++));
                return temp.getXWPFDocument();
            }
            return null;
        }
    }
}
