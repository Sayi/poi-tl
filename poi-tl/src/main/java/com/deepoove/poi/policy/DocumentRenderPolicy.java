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
 * <p>
 * The contents of the {@link DocumentRenderData} are inserted in order at the tag
 * position. Paragraphs, tables and numbering lists are supported and each of them
 * is rendered by the matching policy helper.
 * </p>
 * 
 * @author Sayi
 */
public class DocumentRenderPolicy extends AbstractRenderPolicy<DocumentRenderData> {

    /**
     * {@inheritDoc}
     * <p>
     * The data is valid when it is not {@code null} and has at least one content.
     * </p>
     */
    @Override
    protected boolean validate(DocumentRenderData data) {
        return null != data && !data.getContents().isEmpty();
    }

    /**
     * {@inheritDoc}
     * <p>
     * It clears the placeholder paragraph that held the tag.
     * </p>
     */
    @Override
    protected void afterRender(RenderContext<DocumentRenderData> context) {
        clearPlaceholder(context, true);
    }

    /**
     * {@inheritDoc}
     * <p>
     * It delegates to {@link Helper#renderDocument(XWPFRun, DocumentRenderData)}.
     * </p>
     */
    @Override
    public void doRender(RenderContext<DocumentRenderData> context) throws Exception {
        Helper.renderDocument(context.getRun(), context.getData());
    }

    /**
     * Utilities that insert block level contents into a document.
     */
    public static class Helper {
        /**
         * Inserts the contents of the data one by one at the tag position.
         * 
         * @param run  the run holding the tag
         * @param data the document render data
         * @throws Exception when a content can not be rendered
         */
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
