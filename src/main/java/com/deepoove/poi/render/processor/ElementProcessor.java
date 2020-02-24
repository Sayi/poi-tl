package com.deepoove.poi.render.processor;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

public class ElementProcessor extends DefaultTemplateProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElementProcessor.class);

    public ElementProcessor(XWPFTemplate template, RenderDataCompute renderDataCompute) {
        super(template, renderDataCompute);
    }

    @Override
    public void visit(RunTemplate runTemplate) {
        RenderPolicy policy = runTemplate.findPolicy(template.getConfig());
        if (null == policy) {
            throw new RenderException(
                    "Cannot find render policy: [" + ((ElementTemplate) runTemplate).getTagName() + "]");
        }
        if (policy instanceof DocxRenderPolicy) {
            return;
        } else {
            LOGGER.info("Start render TemplateName:{}, Sign:{}, policy:{}", runTemplate.getTagName(),
                    runTemplate.getSign(), ClassUtils.getShortClassName(policy.getClass()));
            policy.render(((ElementTemplate) runTemplate), renderDataCompute.compute(runTemplate.getTagName()),
                    template);
        }

    }

}
