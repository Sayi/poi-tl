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

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

/**
 * @author Sayi
 *
 * @Deprecated use {@link DocumentRenderPolicy} instead
 */
public class ListRenderPolicy extends AbstractRenderPolicy<List<Object>> {

    @Override
    protected boolean validate(List<Object> data) {
        return (null != data && !data.isEmpty());
    }

    @Override
    public void doRender(RenderContext<List<Object>> context) throws Exception {
        XWPFRun run = context.getRun();
        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
        List<Object> datas = context.getData();
        for (Object data : datas) {
            if (data instanceof TextRenderData) {
                XWPFRun createRun = bodyContainer.insertNewParagraph(run).createRun();
                StyleUtils.styleRun(createRun, run);
                TextRenderPolicy.Helper.renderTextRun(createRun, data);
            } else if (data instanceof TableRenderData) {
                TableRenderPolicy.Helper.renderTable(run, (TableRenderData) data);
            } else if (data instanceof NumberingRenderData) {
                NumberingRenderPolicy.Helper.renderNumbering(run, (NumberingRenderData) data);
            } else if (data instanceof PictureRenderData) {
                PictureRenderPolicy.Helper.renderPicture(bodyContainer.insertNewParagraph(run).createRun(),
                        (PictureRenderData) data);
            }
        }
    }

    @Override
    protected void afterRender(RenderContext<List<Object>> context) {
        clearPlaceholder(context, true);
    }

}
