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
package com.deepoove.poi.plugin.bookmark;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;

import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

/**
 * Book mark plug-in
 * @Deprecated use BookmarkTextRenderData instead.
 */
public class BookmarkRenderPolicy extends TextRenderPolicy {

    @Override
    public void doRender(RenderContext<Object> context) throws Exception {
        Helper.renderTextRun(context.getRun(), context.getData());

        XWPFRun run = context.getRun();
        XWPFParagraphWrapper wapper = new XWPFParagraphWrapper((XWPFParagraph) run.getParent());
        CTBookmark bookmarkStart = wapper.insertNewBookmark(run);

        Object renderData = context.getData();
        TextRenderData data = renderData instanceof TextRenderData ? (TextRenderData) renderData
                : new TextRenderData(renderData.toString());

        String text = null == data.getText() ? "" : data.getText();
        bookmarkStart.setName(text);
    }

}
