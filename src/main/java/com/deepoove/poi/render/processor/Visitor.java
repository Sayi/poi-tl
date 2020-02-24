package com.deepoove.poi.render.processor;

import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.run.RunTemplate;

public interface Visitor {

    void visit(RunTemplate runTemplate);

    void visit(IterableTemplate iterableTemplate);

    void visit(InlineIterableTemplate iterableTemplate);

}
