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

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

/**
 * Document Render
 * 
 * @author Sayi
 */
public class DocumentRenderPolicy extends AbstractRenderPolicy<DocumentRenderData> {

    @Override
    protected boolean validate(DocumentRenderData data) {
        return null != data && !data.getContents().isEmpty();
    }

    @Override
    protected void afterRender(RenderContext<DocumentRenderData> context) {
        clearPlaceholder(context, true);
    }

    @Override
    public void doRender(RenderContext<DocumentRenderData> context) throws Exception {
        Helper.renderDocument(context.getRun(), context.getData());
    }

    public static class Helper {
        public static void renderDocument(XWPFRun run, DocumentRenderData data) throws Exception {
            List<RenderData> contents = data.getContents();
            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
            for (RenderData item : contents) {
                XWPFParagraph paragraph = bodyContainer.insertNewParagraph(run);
                XWPFRun createRun = paragraph.createRun();
                StyleUtils.styleParagraph(paragraph, run.getParent());
                StyleUtils.styleRun(createRun, run);
                if (item instanceof ParagraphRenderData) {
                    ParagraphRenderPolicy.Helper.renderParagraph(createRun, (ParagraphRenderData) item);
                } else if (item instanceof TableRenderData) {
                    TableRenderPolicy.Helper.renderTable(createRun, (TableRenderData) item);
                    BodyContainerFactory.getBodyContainer(createRun).clearPlaceholder(createRun);
                } else if (item instanceof NumberingRenderData) {
                    NumberingRenderPolicy.Helper.renderNumbering(createRun, (NumberingRenderData) item);
                    BodyContainerFactory.getBodyContainer(createRun).clearPlaceholder(createRun);
                }
            }
        }
    }

}
