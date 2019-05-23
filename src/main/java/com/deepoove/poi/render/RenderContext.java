package com.deepoove.poi.render;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.template.ElementTemplate;

public class RenderContext {

    private ElementTemplate eleTemplate;
    private Object data;
    private XWPFTemplate template;

    public RenderContext(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        this.eleTemplate = eleTemplate;
        this.data = data;
        this.template = template;
    }

    public ElementTemplate getEleTemplate() {
        return eleTemplate;
    }

    public void setEleTemplate(ElementTemplate eleTemplate) {
        this.eleTemplate = eleTemplate;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public XWPFTemplate getTemplate() {
        return template;
    }

    public void setTemplate(XWPFTemplate template) {
        this.template = template;
    }

}
