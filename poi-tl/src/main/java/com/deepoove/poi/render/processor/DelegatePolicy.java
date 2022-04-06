package com.deepoove.poi.render.processor;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.PreRenderDataCastor;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;

public class DelegatePolicy {

    public static void invoke(RenderPolicy policy, ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        Object model = data;
        PreRenderDataCastor preRenderDataCastor = template.getConfig().getPreRenderDataCastor();
        if (null != preRenderDataCastor) {
            model = preRenderDataCastor.preCast(policy, data);
        }
        policy.render(eleTemplate, model, template);
    }

}
