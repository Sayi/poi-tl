package com.deepoove.poi.render;

import com.deepoove.poi.el.ELObject;

public class ELObjectRenderDataCompute implements RenderDataCompute {

    private ELObject elObject;

    public ELObjectRenderDataCompute(Object root) {
        elObject = ELObject.create(root);
    }

    @Override
    public Object compute(String el) {
        return elObject.eval(el);
    }

}
