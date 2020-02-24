package com.deepoove.poi.template;

import com.deepoove.poi.render.processor.Visitor;

public class InlineIterableTemplate extends IterableTemplate {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(startMark);
        templates.forEach(temp -> sb.append(temp).append(" "));
        sb.append(endMark);
        return sb.toString();
    }

}
