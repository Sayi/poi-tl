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
package com.deepoove.poi.template.run;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.processor.Visitor;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.util.ParagraphUtils;

/**
 * Basic docx template element: XWPFRun
 * 
 * @author Sayi
 * @version 0.0.1
 */
public class RunTemplate extends ElementTemplate {

    protected XWPFRun run;

    public RunTemplate() {
    }

    public RunTemplate(String tagName, XWPFRun run) {
        this.tagName = tagName;
        this.run = run;
    }

    public Integer getRunPos() {
        return ParagraphUtils.getRunPos(run);
    }

    /**
     * @return the run
     */
    public XWPFRun getRun() {
        return run;
    }

    /**
     * @param run the run to set
     */
    public void setRun(XWPFRun run) {
        this.run = run;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public RenderPolicy findPolicy(Configure config) {
        RenderPolicy policy = config.getCustomPolicy(tagName);
        if (null == policy) policy = config.getDefaultPolicy(sign);
        return null == policy ? config.getTemplatePolicy(this.getClass()) : policy;
    }

}
