package com.deepoove.poi.render.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;

public abstract class AbstractIterableProcessor extends DefaultTemplateProcessor {
    
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final int TIMES_NEVER = 0;
    protected static final int TIMES_ONCE = 1;
    protected static final int TIMES_N = 2;

    public AbstractIterableProcessor(XWPFTemplate template, RenderDataCompute renderDataCompute) {
        super(template, renderDataCompute);
    }

    protected int conditionTimes(Object compute) {
        if (null == compute) return TIMES_NEVER;
        if (compute instanceof Boolean) return (Boolean) compute ? TIMES_ONCE : TIMES_NEVER;
        if (compute instanceof Iterable) {
            return TIMES_N;
        }
        return TIMES_ONCE;
    }

}
