/*
 * Copyright 2014-2026 Sayi
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
package com.deepoove.poi.config;

import com.deepoove.poi.policy.RenderPolicy;

/**
 * Castor that casts the render data before rendering
 * <p>
 * It can be used to convert the value of a tag to the render data expected by
 * the bound {@link RenderPolicy}.
 * </p>
 */
public interface PreRenderDataCastor {

    /**
     * Cast the render data of a tag before rendering
     * 
     * @param policy render policy bound to the tag
     * @param data   original data of the tag
     * @return the casted data
     */
    Object preCast(RenderPolicy policy, Object data);

}
