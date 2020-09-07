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
package com.deepoove.poi.policy;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * Numbering render
 * 
 * @author Sayi
 */
public class NumberingRenderPolicy extends AbstractRenderPolicy<NumberingRenderData> {

    @Override
    protected boolean validate(NumberingRenderData data) {
        return (null != data && CollectionUtils.isNotEmpty(data.getItems()));
    }

    @Override
    public void doRender(RenderContext<NumberingRenderData> context) throws Exception {
        Helper.renderNumbering(context.getRun(), context.getData());
    }

    @Override
    protected void afterRender(RenderContext<NumberingRenderData> context) {
        clearPlaceholder(context, true);
    }

    public static class Helper {

        public static void renderNumbering(XWPFRun run, NumberingRenderData data) throws Exception {
            List<ParagraphRenderData> items = data.getItems();
            BigInteger numID = ((NiceXWPFDocument) run.getParent().getDocument()).addNewNumberingId(data.getFormat());
            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
            for (ParagraphRenderData item : items) {
                XWPFParagraph paragraph = bodyContainer.insertNewParagraph(run);
                paragraph.setNumID(numID);
                XWPFRun createRun = paragraph.createRun();
                StyleUtils.styleParaRpr(paragraph, StyleUtils.retriveStyle(run));
                StyleUtils.styleRun(createRun, run);
                ParagraphRenderPolicy.Helper.renderParagraph(createRun, item);
            }
        }

    }
}
