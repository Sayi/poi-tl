package com.deepoove.poi.config;

import com.deepoove.poi.policy.RenderPolicy;

public interface PreRenderDataCastor {

    Object preCast(RenderPolicy policy, Object data);

}
