package com.deepoove.poi.jsonmodel.support;

import com.deepoove.poi.config.PreRenderDataCastor;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.reference.AbstractTemplateRenderPolicy;
import com.google.gson.internal.LinkedTreeMap;

import net.jodah.typetools.TypeResolver;

/**
 * Pre-render castor that turns Gson JSON trees into typed render data.
 * <p>
 * When the bound data is a {@code LinkedTreeMap} and the render policy declares
 * a type argument, the map is converted into that type before rendering. This
 * lets a JSON model be used with the same policies as typed model objects.
 * </p>
 */
public class GsonPreRenderDataCastor implements PreRenderDataCastor {

    private GsonHandler gsonHandler = new DefaultGsonHandler();

    /**
     * {@inheritDoc}
     * <p>
     * The target type is resolved from the type argument of the policy: the
     * first one for an {@link AbstractRenderPolicy} and the second one for an
     * {@link AbstractTemplateRenderPolicy}. Data of any other shape is returned
     * unchanged.
     * </p>
     */
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

    /**
     * Returns the handler used to convert JSON trees.
     *
     * @return the Gson handler
     */
    public GsonHandler getGsonHandler() {
        return gsonHandler;
    }

    /**
     * Sets the handler used to convert JSON trees.
     *
     * @param gsonHandler the Gson handler to set
     */
    public void setGsonHandler(GsonHandler gsonHandler) {
        this.gsonHandler = gsonHandler;
    }

}
