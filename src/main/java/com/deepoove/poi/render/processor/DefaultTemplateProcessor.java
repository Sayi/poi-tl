package com.deepoove.poi.render.processor;

import java.util.List;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.run.RunTemplate;

public abstract class DefaultTemplateProcessor implements Visitor {

    protected XWPFTemplate template;
    protected RenderDataCompute renderDataCompute;

    public DefaultTemplateProcessor(XWPFTemplate template, RenderDataCompute renderDataCompute) {
        this.template = template;
        this.renderDataCompute = renderDataCompute;
    }

    protected void visitOther(MetaTemplate template) {
        // no-op
    }

    public void process(List<MetaTemplate> templates) {
        // process in order( or sort first)
        templates.forEach(template -> template.accept(this));
    }

    @Override
    public void visit(InlineIterableTemplate iterableTemplate) {
        visitOther(iterableTemplate);
    }

    @Override
    public void visit(RunTemplate runTemplate) {
        visitOther(runTemplate);
    }

    @Override
    public void visit(IterableTemplate iterableTemplate) {
        visitOther(iterableTemplate);
    }

}
