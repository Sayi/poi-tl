/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.render;

import com.deepoove.poi.config.ELMode;

/**
 * @author Sayi
 * @version 1.5.0
 */
public class RenderFactory {

    public static Render getRender(Object model, ELMode mode) {
        Render render = null;
        switch (mode) {
        case SPEL_MODE:
            render = new Render(new SpELRenderDataCompute(model));
            break;
        case POI_TL_STICT_MODE:
            render = new Render(new ELObjectRenderDataCompute(model, true));
            break;
        default:
            render = new Render(model);
            break;
        }
        return render;
    }

}
