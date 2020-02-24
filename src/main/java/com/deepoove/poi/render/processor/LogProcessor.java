package com.deepoove.poi.render.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.run.RunTemplate;

public class LogProcessor implements Visitor {

    private static Logger log = LoggerFactory.getLogger(LogProcessor.class);

    private String indentState;

    public LogProcessor() {
        this.indentState = "";
    }

    public LogProcessor(String indent) {
        this.indentState = indent;
    }

    public void process(List<MetaTemplate> templates) {
        templates.forEach(template -> template.accept(this));
    }

    @Override
    public void visit(InlineIterableTemplate iterableTemplate) {
        visit((IterableTemplate) iterableTemplate);
    }

    @Override
    public void visit(RunTemplate runTemplate) {
        log.info("{}{}", indentState, runTemplate);
    }

    @Override
    public void visit(IterableTemplate iterableTemplate) {
        log.info("{}{}", indentState, iterableTemplate.getStartMark());
        new LogProcessor(indentState + "  ").process(iterableTemplate.getTemplates());
        log.info("{}{}", indentState, iterableTemplate.getEndMark());
    }
}
