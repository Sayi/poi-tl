package com.deepoove.poi.render.processor;

import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.xwpf.ParentContext;

public interface Iteration {

    void next(IterableTemplate iterable, ParentContext parentContext, int start, int end, Object model);

}
