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

package com.deepoove.poi.render.processor;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.resolver.Resolver;
import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.PictImageTemplate;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * process element template
 * 
 * @author Sayi
 *
 */
public class ElementProcessor extends DefaultTemplateProcessor {

    public ElementProcessor(XWPFTemplate template, Resolver resolver, RenderDataCompute renderDataCompute) {
        super(template, resolver, renderDataCompute);
    }

    @Override
    public void visit(PictureTemplate pictureTemplate) {
        visit((ElementTemplate) pictureTemplate);
    }

    @Override
    public void visit(PictImageTemplate pictImageTemplate) {
        visit((ElementTemplate) pictImageTemplate);
    }

    @Override
    public void visit(ChartTemplate chartTemplate) {
        visit((ElementTemplate) chartTemplate);
    }

    @Override
    public void visit(RunTemplate runTemplate) {
        visit((ElementTemplate) runTemplate);
    }

    void visit(ElementTemplate eleTemplate) {
        RenderPolicy policy = eleTemplate.findPolicy(template.getConfig());
        if (null != policy && policy instanceof DocxRenderPolicy) return;
        DelegatePolicy.invoke(policy, eleTemplate, renderDataCompute.compute(eleTemplate.getTagName()), template);
    }

}
