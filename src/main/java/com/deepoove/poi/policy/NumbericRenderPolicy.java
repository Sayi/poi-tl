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

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;

/**
 * @author Sayi
 * @version 0.0.5
 */
public class NumbericRenderPolicy extends AbstractRenderPolicy<NumbericRenderData> {

    @Override
    protected boolean validate(NumbericRenderData data) {
        if (CollectionUtils.isEmpty(data.getNumbers())) {
            logger.debug("Empty NumbericRenderData datamodel: {}", data);
            return false;
        }
        return true;
    }

    @Override
    public void doRender(RunTemplate runTemplate, NumbericRenderData numbericData,
            XWPFTemplate template) throws Exception {
        XWPFRun run = runTemplate.getRun();
        Helper.renderNumberic(run, numbericData);

    }

    @Override
    protected void afterRender(RenderContext context) {
        clearPlaceholder(context, true);
    }

    public static class Helper {
        public static void renderNumberic(XWPFRun run, NumbericRenderData numbericData) {
            NiceXWPFDocument doc = (NiceXWPFDocument) run.getParent().getDocument();
            List<TextRenderData> datas = numbericData.getNumbers();
            Style fmtStyle = numbericData.getFmtStyle();

            BigInteger numID = doc.addNewNumbericId(numbericData.getNumFmt());

            XWPFParagraph paragraph;
            XWPFRun newRun;
            for (TextRenderData line : datas) {
                paragraph = doc.insertNewParagraph(run);
                paragraph.setNumID(numID);
                CTP ctp = paragraph.getCTP();
                CTPPr pPr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
                CTParaRPr pr = pPr.isSetRPr() ? pPr.getRPr() : pPr.addNewRPr();
                StyleUtils.styleRpr(pr, fmtStyle);
                newRun = paragraph.createRun();
                StyleUtils.styleRun(newRun, line.getStyle());
                newRun.setText(line.getText());
            }
        }
    }
}
