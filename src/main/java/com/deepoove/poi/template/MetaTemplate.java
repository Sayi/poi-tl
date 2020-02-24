package com.deepoove.poi.template;

import com.deepoove.poi.render.processor.Visitor;

public interface MetaTemplate {

    void accept(Visitor visitor);

}
