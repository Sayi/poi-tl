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
package com.deepoove.poi.policy.reference;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.template.ChartTemplate;

/**
 * Default render policy of a chart template, which keeps the chart as it is.
 * <p>
 * It is used when the chart of the template is not bound to any data, so the
 * chart defined in the template is preserved in the output document.
 * </p>
 */
public class DefaultChartTemplateRenderPolicy extends AbstractChartTemplateRenderPolicy<Object> {

    /**
     * {@inheritDoc}
     * <p>
     * This implementation does nothing on purpose.
     * </p>
     */
    @Override
    public void doRender(ChartTemplate eleTemplate, Object data, XWPFTemplate template) throws Exception {
        // no-op

    }

}
