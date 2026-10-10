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
package com.deepoove.poi.plugin.toc;

import org.apache.poi.xwpf.usermodel.XWPFFieldRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.xwpf.XWPFOnOff;

/**
 * Inserts a Word table of contents field in place of the tag.
 * <p>
 * The generated field is marked dirty so that Word refreshes the entries when the
 * document is opened. The bound data is ignored; bind this policy to a dedicated
 * tag name.
 * </p>
 */
public class TOCRenderPolicy implements RenderPolicy {

    /**
     * Replaces the tag with a dirty {@code TOC} field.
     *
     * @param eleTemplate the tag to render
     * @param data        the bound data, ignored by this policy
     * @param template    the template instance being rendered
     */
    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        XWPFRun run = ((RunTemplate) eleTemplate).getRun();
        run.setText("", 0);

        XWPFParagraph tocPara = (XWPFParagraph) run.getParent();
        XWPFFieldRun fieldRun = tocPara.insertNewFieldRun(0);

        CTSimpleField toc = fieldRun.getCTField();
        toc.setInstr("TOC \\o");
        toc.setDirty(XWPFOnOff.ON);
    }

}