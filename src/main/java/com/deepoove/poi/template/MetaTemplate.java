package com.deepoove.poi.template;

import com.deepoove.poi.render.processor.Visitor;

public interface MetaTemplate {
    
    String variable();

    void accept(Visitor visitor);

}
