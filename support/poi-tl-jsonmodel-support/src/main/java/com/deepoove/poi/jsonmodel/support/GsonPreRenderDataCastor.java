package com.deepoove.poi.jsonmodel.support;

import com.deepoove.poi.config.PreRenderDataCastor;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.reference.AbstractTemplateRenderPolicy;
import com.google.gson.internal.LinkedTreeMap;

import net.jodah.typetools.TypeResolver;

public class GsonPreRenderDataCastor implements PreRenderDataCastor {

    private GsonHandler gsonHandler = new DefaultGsonHandler();

    @SuppressWarnings("rawtypes")
    @Override
    public Object preCast(RenderPolicy policy, Object data) {
        if (null != data && data instanceof LinkedTreeMap) {
            if (policy instanceof AbstractRenderPolicy) {
                Class<?>[] typeArguments = TypeResolver.resolveRawArguments(AbstractRenderPolicy.class,
                        policy.getClass());
                return gsonHandler.castJsonToClass((LinkedTreeMap) data, typeArguments[0]);
            } else if (policy instanceof AbstractTemplateRenderPolicy) {
                Class<?>[] typeArguments = TypeResolver.resolveRawArguments(AbstractTemplateRenderPolicy.class,
                        policy.getClass());
                return gsonHandler.castJsonToClass((LinkedTreeMap) data, typeArguments[1]);
            }
        }
        return data;
    }

    public GsonHandler getGsonHandler() {
        return gsonHandler;
    }

    public void setGsonHandler(GsonHandler gsonHandler) {
        this.gsonHandler = gsonHandler;
    }

}
