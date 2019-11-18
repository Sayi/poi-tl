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
package com.deepoove.poi.render;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.util.ObjectUtils;

/**
 * 渲染器，支持表达式计算接口RenderDataCompute的扩展
 * 
 * @author Sayi
 * @version
 * @since 1.5.0
 */
public class Render {

    private static final Logger LOGGER = LoggerFactory.getLogger(Render.class);

    private RenderDataCompute renderDataCompute;

    /**
     * 默认计算器为ELObjectRenderDataCompute
     * 
     * @param root
     */
    public Render(Object root) {
        ObjectUtils.requireNonNull(root, "Data root must not be null");
        renderDataCompute = new ELObjectRenderDataCompute(root, false);
    }

    public Render(RenderDataCompute dataCompute) {
        this.renderDataCompute = dataCompute;
    }

    public void render(XWPFTemplate template) {
        ObjectUtils.requireNonNull(template, "Template must not be null.");
        LOGGER.info("Render the template file start...");
        StopWatch watch = new StopWatch();
        try {
            watch.start();
            int docxCount = applyNormalPolicy(template);
            if (docxCount >= 1) {
                template.reload(template.getXWPFDocument().generate());
                applyDocxPolicy(template, docxCount);
            }
        } catch (Exception e) {
            throw new RenderException("Render docx failed", e);
        }
        finally {
            watch.stop();
        }
        LOGGER.info("Successfully Render the template file in {} millis" , TimeUnit.NANOSECONDS.toMillis(watch.getNanoTime()));
    }

    private int applyNormalPolicy(XWPFTemplate template) {
        RenderPolicy policy = null;
        int docxItems = 0;
        List<ElementTemplate> elementTemplates = template.getElementTemplates();
        for (ElementTemplate runTemplate : elementTemplates) {
            policy = findPolicy(template.getConfig(), runTemplate);
            if (policy instanceof DocxRenderPolicy) {
                docxItems++;
            } else {
                doRender(runTemplate, policy, template);
            }
        }
        return docxItems;
    }

    private void applyDocxPolicy(XWPFTemplate template, int docxItems) {
        List<ElementTemplate> elementTemplates = null;
        RenderPolicy policy = null;
        NiceXWPFDocument current = template.getXWPFDocument();
        for (int i = 0; i < docxItems; i++) {
            elementTemplates = template.getElementTemplates();
            if (elementTemplates.isEmpty()) {
                break;
            }

            for (ElementTemplate runTemplate : elementTemplates) {
                policy = findPolicy(template.getConfig(), runTemplate);
                if (!(policy instanceof DocxRenderPolicy)) {
                    continue;
                }
                doRender(runTemplate, policy, template);

                // 没有最终合并，继续下一个合并
                if (current == template.getXWPFDocument()) {
                    i++;
                } else {
                    current = template.getXWPFDocument();
                    break;
                }
            }
        }
    }

    private RenderPolicy findPolicy(Configure config, ElementTemplate runTemplate) {
        RenderPolicy policy = config.getPolicy(runTemplate.getTagName(), runTemplate.getSign());
        if (null == policy) { throw new RenderException(
                "Cannot find render policy: [" + runTemplate.getTagName() + "]"); }
        return policy;
    }

    private void doRender(ElementTemplate ele, RenderPolicy policy, XWPFTemplate template) {
        LOGGER.info("Start render TemplateName:{}, Sign:{}, policy:{}", ele.getTagName(),
                ele.getSign(), ClassUtils.getShortClassName(policy.getClass()));
        policy.render(ele, renderDataCompute.compute(ele.getTagName()), template);
    }

}
