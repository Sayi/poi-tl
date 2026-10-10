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
package com.deepoove.poi.plugin.barcode;

import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.render.RenderContext;

/**
 * Render policy of the barcode plugin.
 * <p>
 * Register it with {@code Configure.builder().addPlugin('%', new BarcodeRenderPolicy())}
 * to enable the {@code {{%qrcode}}} syntax. The data can also be placed in the
 * built in picture tag {@code {{@qrcode}}}, because
 * {@link BarcodeRenderData} is a {@link com.deepoove.poi.data.PictureRenderData}.
 * <p>
 * The rendering itself is fully delegated to
 * {@link PictureRenderPolicy.Helper#renderPicture}, only the failure handling
 * differs: an invalid or too long content writes
 * {@link BarcodeRenderData#getAltMeta()} into the document instead of breaking
 * the whole rendering.
 *
 * @author Sayi
 */
public class BarcodeRenderPolicy extends AbstractRenderPolicy<BarcodeRenderData> {

    private static final String FALLBACK_ALT = "[barcode render failed]";

    private final ObjectToBarcodeRenderDataConverter converter = new ObjectToBarcodeRenderDataConverter();

    /**
     * Never throws: an unsupported data type clears the tag instead of aborting the
     * whole document, because {@code cast} runs outside of the exception handling of
     * {@link AbstractRenderPolicy#render}.
     */
    @Override
    public BarcodeRenderData cast(Object source) {
        try {
            return converter.convert(source);
        } catch (Exception e) {
            logger.warn("Unsupported barcode data, the template tag will be cleared: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Only {@code null} is rejected here, an empty or invalid content is handled in
     * {@link #doRender} so that it can fall back to the alt text.
     */
    @Override
    protected boolean validate(BarcodeRenderData data) {
        return null != data;
    }

    @Override
    public void doRender(RenderContext<BarcodeRenderData> context) throws Exception {
        PictureRenderPolicy.Helper.renderPicture(context.getRun(), context.getData());
    }

    @Override
    protected void afterRender(RenderContext<BarcodeRenderData> context) {
        clearPlaceholder(context, false);
    }

    @Override
    protected void reThrowException(RenderContext<BarcodeRenderData> context, Exception e) {
        logger.error("Render barcode {} error: {}", context.getEleTemplate(), e.getMessage());
        String altMeta = null != context.getData() ? context.getData().getAltMeta() : null;
        context.getRun().setText(null == altMeta || altMeta.isEmpty() ? FALLBACK_ALT : altMeta, 0);
    }

}
