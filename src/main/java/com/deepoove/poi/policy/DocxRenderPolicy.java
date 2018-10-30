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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * <p>
 * word模板List循环渲染后合并
 * </p>
 * 
 * @author Sayi
 * @version 1.3.0
 */
public class DocxRenderPolicy extends AbstractRenderPolicy {
    @Override
    protected boolean validate(Object data) {
        return null != data;
    }

    @Override
    public void doRender(RunTemplate runTemplate, Object data, XWPFTemplate template)
            throws Exception {
        NiceXWPFDocument doc = template.getXWPFDocument();
        XWPFRun run = runTemplate.getRun();
        // 优先清空标签
        clearPlaceholder(run);

        List<NiceXWPFDocument> docMerges = getMergedDocxs((DocxRenderData) data,
                template.getConfig());
        try {
            doc = doc.merge(docMerges, run);
        } catch (Exception e) {
            logger.error("merge docx error", e);
        }

        template.reload(doc);
    }

    private List<NiceXWPFDocument> getMergedDocxs(DocxRenderData data, Configure configure) {
        List<NiceXWPFDocument> docs = new ArrayList<NiceXWPFDocument>();
        File docx = data.getDocx();
        List<?> dataList = data.getDataList();
        if (null == dataList || dataList.isEmpty()) {
            try {
                // 待合并的文档不是模板
                docs.add(new NiceXWPFDocument(new FileInputStream(docx)));
            } catch (Exception e) {
                logger.error("Cannot get the merged docx.", e);
            }
        } else {
            for (int i = 0; i < dataList.size(); i++) {
                XWPFTemplate temp = XWPFTemplate.compile(docx, configure);
                temp.render(dataList.get(i));
                docs.add(temp.getXWPFDocument());
            }
        }
        return docs;
    }

}
