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
package com.deepoove.poi.plugin.field;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;

import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

public class SimpleFieldRenderPolicy extends AbstractRenderPolicy<String> {

    @Override
    public void doRender(RenderContext<String> context) throws Exception {

        RunTemplate runTemplate = (RunTemplate) context.getEleTemplate();
        XWPFRun run = context.getRun();
        run.setText("", 0);

        XWPFParagraph paragraph = (XWPFParagraph) run.getParent();

        XWPFParagraphWrapper paragraphWrapper = new XWPFParagraphWrapper(paragraph);

        XWPFRun startRun = paragraphWrapper.insertNewRun(runTemplate.getRunPos());
        StyleUtils.styleRun(startRun, run);
        CTR ctr = startRun.getCTR();
        CTFldChar addNewFldChar = ctr.addNewFldChar();
        addNewFldChar.setFldCharType(STFldCharType.BEGIN);

        XWPFRun instrRun = paragraphWrapper.insertNewRun(runTemplate.getRunPos());
        StyleUtils.styleRun(instrRun, run);
        ctr = instrRun.getCTR();
        CTText addNewInstrText = ctr.addNewInstrText();
        // "EQ \\o\\ac(□, 1)"
        addNewInstrText.setStringValue(context.getData());

        XWPFRun sepRun = paragraphWrapper.insertNewRun(runTemplate.getRunPos());
        StyleUtils.styleRun(sepRun, run);
        ctr = sepRun.getCTR();
        addNewFldChar = ctr.addNewFldChar();
        addNewFldChar.setFldCharType(STFldCharType.SEPARATE);

        XWPFRun endRun = paragraphWrapper.insertNewRun(runTemplate.getRunPos());
        StyleUtils.styleRun(endRun, run);
        ctr = endRun.getCTR();
        addNewFldChar = ctr.addNewFldChar();
        addNewFldChar.setFldCharType(STFldCharType.END);

    }

}
