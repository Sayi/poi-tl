/*
 * Copyright 2014-2020 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.render.compute;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.render.compute.ELObjectRenderDataCompute;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.render.compute.SpELRenderDataCompute;

/**
 * @author Sayi
 * @version 1.7.0
 */
public class DefaultRenderDataComputeFactory implements RenderDataComputeFactory {

    private final Configure config;

    public DefaultRenderDataComputeFactory(Configure config) {
        this.config = config;
    }

    public RenderDataCompute newCompute(Object model) {
        RenderDataCompute render = null;
        switch (this.config.getElMode()) {
        case SPEL_MODE:
            render = new SpELRenderDataCompute(model);
            break;
        case POI_TL_STICT_MODE:
            render = new ELObjectRenderDataCompute(model, true);
            break;
        default:
            render = new ELObjectRenderDataCompute(model, false);
            break;
        }
        return render;
    }

}
