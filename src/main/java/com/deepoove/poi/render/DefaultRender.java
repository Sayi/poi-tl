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
package com.deepoove.poi.render;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.render.processor.DocumentProcessor;
import com.deepoove.poi.render.processor.LogProcessor;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * default render
 * 
 * @author Sayi
 * @since 1.7.0
 */
public class DefaultRender implements Render {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRender.class);

    public DefaultRender() {
    }

    @Override
    public void render(XWPFTemplate template, Object root) {
        Objects.requireNonNull(template, "Template must not be null.");
        Objects.requireNonNull(root, "Data root must not be null");

        LOGGER.info("Render template start...");

        RenderDataCompute renderDataCompute = template.getConfig().getRenderDataComputeFactory().newCompute(root);
        StopWatch watch = new StopWatch();
        try {

            watch.start();
            renderTemplate(template, renderDataCompute);
            renderInclude(template, renderDataCompute);

        } catch (Exception e) {
            if (e instanceof RenderException) throw (RenderException) e;
            throw new RenderException("Cannot render docx template, please check the Exception", e);
        } finally {
            watch.stop();
        }
        LOGGER.info("Successfully Render template in {} millis", TimeUnit.NANOSECONDS.toMillis(watch.getNanoTime()));
    }

    private void renderTemplate(XWPFTemplate template, RenderDataCompute renderDataCompute) {
        // log
        new LogProcessor().process(template.getElementTemplates());

        // render
        DocumentProcessor documentRender = new DocumentProcessor(template, template.getResolver(), renderDataCompute);
        documentRender.process(template.getElementTemplates());
    }

    private void renderInclude(XWPFTemplate template, RenderDataCompute renderDataCompute) throws IOException {
        List<MetaTemplate> elementTemplates = template.getElementTemplates();
        long docxCount = elementTemplates.stream()
                .filter(meta -> (meta instanceof RunTemplate
                        && ((RunTemplate) meta).findPolicy(template.getConfig()) instanceof DocxRenderPolicy))
                .count();
        if (docxCount >= 1) {
            template.reload(template.getXWPFDocument().generate());
            applyDocxPolicy(template, renderDataCompute, docxCount);
        }
    }

    private void applyDocxPolicy(XWPFTemplate template, RenderDataCompute renderDataCompute, long docxItems) {
        RenderPolicy policy = null;
        NiceXWPFDocument current = template.getXWPFDocument();
        List<MetaTemplate> elementTemplates = template.getElementTemplates();
        int k = 0;
        while (k < elementTemplates.size()) {
            for (k = 0; k < elementTemplates.size(); k++) {
                MetaTemplate metaTemplate = elementTemplates.get(k);
                if (!(metaTemplate instanceof RunTemplate)) continue;
                RunTemplate runTemplate = (RunTemplate) metaTemplate;
                policy = runTemplate.findPolicy(template.getConfig());
                if (!(policy instanceof DocxRenderPolicy)) {
                    continue;
                }

                LOGGER.info("Start render TemplateName:{}, Sign:{}, policy:{}", runTemplate.getTagName(),
                        runTemplate.getSign(), ClassUtils.getShortClassName(policy.getClass()));
                policy.render(runTemplate, renderDataCompute.compute(runTemplate.getTagName()), template);

                if (current != template.getXWPFDocument()) {
                    current = template.getXWPFDocument();
                    elementTemplates = template.getElementTemplates();
                    break;
                }
            }
        }
    }

}
