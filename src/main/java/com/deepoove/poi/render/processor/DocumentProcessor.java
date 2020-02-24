package com.deepoove.poi.render.processor;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.run.RunTemplate;

public class DocumentProcessor extends DefaultTemplateProcessor {

    private ElementProcessor elementProcessor;
    private IterableProcessor iterableProcessor;
    private InlineIterableProcessor inlineIterableProcessor;

    public DocumentProcessor(XWPFTemplate template, RenderDataCompute renderDataCompute) {
        super(template, renderDataCompute);
        elementProcessor = new ElementProcessor(template, renderDataCompute);
        iterableProcessor = new IterableProcessor(template, renderDataCompute);
        inlineIterableProcessor = new InlineIterableProcessor(template, renderDataCompute);
    }

    @Override
    public void visit(InlineIterableTemplate iterableTemplate) {
        iterableTemplate.accept(inlineIterableProcessor);
    }

    @Override
    public void visit(IterableTemplate iterableTemplate) {
        iterableTemplate.accept(iterableProcessor);
    }

    @Override
    public void visit(RunTemplate runTemplate) {
        runTemplate.accept(elementProcessor);
    }

}
