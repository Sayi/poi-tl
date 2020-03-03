package com.deepoove.poi.render.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

public abstract class AbstractIterableProcessor extends DefaultTemplateProcessor implements Iteration {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public AbstractIterableProcessor(XWPFTemplate template, RenderDataCompute renderDataCompute) {
        super(template, renderDataCompute);
    }

    @Override
    public void visit(IterableTemplate iterableTemplate) {
        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(iterableTemplate);
        Object compute = renderDataCompute.compute(iterableTemplate.getStartMark().getTagName());

        if (null == compute || (compute instanceof Boolean && !(Boolean) compute)) {
            handleNever(iterableTemplate, bodyContainer);
        } else if (compute instanceof Iterable) {
            handleIterable(iterableTemplate, bodyContainer, (Iterable<?>) compute);
        } else {
            handleOnce(iterableTemplate, compute);
        }

        afterHandle(iterableTemplate, bodyContainer);
    }

    protected void afterHandle(IterableTemplate iterableTemplate, BodyContainer bodyContainer) {
        bodyContainer.clearPlaceholder(iterableTemplate.getStartRun());
        bodyContainer.clearPlaceholder(iterableTemplate.getEndRun());
    }

    protected abstract void handleNever(IterableTemplate iterableTemplate, BodyContainer bodyContainer);

    protected abstract void handleIterable(IterableTemplate iterableTemplate, BodyContainer bodyContainer,
            Iterable<?> compute);

    protected void handleOnce(IterableTemplate iterableTemplate, Object compute) {
        process(iterableTemplate.getTemplates(), compute);
    }

    protected void process(List<MetaTemplate> templates, Object model) {
        RenderDataCompute dataCompute = template.getConfig().getRenderDataComputeFactory().newCompute(model);
        new DocumentProcessor(this.template, dataCompute).process(templates);
    }

}
