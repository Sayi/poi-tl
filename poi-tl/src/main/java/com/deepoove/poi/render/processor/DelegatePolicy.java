/*
 * Copyright 2014-2021 Sayi
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
package com.deepoove.poi.render.processor;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.PreRenderDataCastor;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;

public class DelegatePolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(DelegatePolicy.class);

    public static void invoke(RenderPolicy policy, ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        Objects.requireNonNull(policy, "Cannot find render policy: [" + eleTemplate.getTagName() + "]");
        Object model = data;
        List<PreRenderDataCastor> preRenderDataCastors = template.getConfig().getPreRenderDataCastors();
        if (null != preRenderDataCastors) {
            for (PreRenderDataCastor preRenderDataCastor : preRenderDataCastors) {
                model = preRenderDataCastor.preCast(policy, model);
            }
        }
        LOGGER.info("Start render Template {}, Sign:{}, policy:{}", eleTemplate, eleTemplate.getSign(),
                ClassUtils.getShortClassName(policy.getClass()));
        policy.render(eleTemplate, model, template);
    }

}
