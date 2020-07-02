package com.deepoove.poi.policy.reference;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.template.ChartTemplate;

public class ChartTemplateRenderPolicy extends AbstractTemplateRenderPolicy<ChartTemplate, Object> {

    @Override
    public void doRender(ChartTemplate eleTemplate, Object data, XWPFTemplate template) throws Exception {
        // no-op
        System.out.println("\t" + data);

    }

}
