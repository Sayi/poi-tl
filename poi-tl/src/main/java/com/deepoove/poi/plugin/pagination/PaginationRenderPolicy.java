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
package com.deepoove.poi.plugin.pagination;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;

/**
 * Inserts a page break where the tag sits.
 * <p>
 * Accepts a {@link Boolean}: when the value is {@code true} a page break is added
 * after the placeholder is cleared, otherwise the tag is simply removed. Use it to
 * start a new printed page conditionally.
 * </p>
 */
public class PaginationRenderPolicy extends AbstractRenderPolicy<Boolean> {

    /**
     * {@inheritDoc}
     * <p>
     * Clears the placeholder and adds a page break when the bound value is
     * {@code Boolean.TRUE}.
     * </p>
     *
     * @param context the render context holding the target run and the boolean flag
     * @throws Exception if the break cannot be added
     */
    @Override
    public void doRender(RenderContext<Boolean> context) throws Exception {
        XWPFRun run = context.getRun();
        clearPlaceholder(context, false);
        if (Boolean.TRUE.equals(context.getData())) {
            run.addBreak(BreakType.PAGE);
        }
    }

}
