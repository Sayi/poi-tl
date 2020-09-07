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
package com.deepoove.poi.policy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.render.RenderContext;

/**
 * picture render
 * 
 * @author Sayi
 *
 */
public class PictureRenderPolicy extends AbstractRenderPolicy<PictureRenderData> {

    @Override
    protected boolean validate(PictureRenderData data) {
        if (null == data) return false;
        if (null == data.getPictureType()) {
            throw new RenderException("PictureRenderData must set picture type!");
        }
        return true;
    }

    @Override
    public void doRender(RenderContext<PictureRenderData> context) throws Exception {
        Helper.renderPicture(context.getRun(), context.getData());
    }

    @Override
    protected void afterRender(RenderContext<PictureRenderData> context) {
        clearPlaceholder(context, false);
    }

    @Override
    protected void reThrowException(RenderContext<PictureRenderData> context, Exception e) {
        logger.info("Render picture " + context.getEleTemplate() + " error: {}", e.getMessage());
        context.getRun().setText(context.getData().getAltMeta(), 0);
    }

    public static class Helper {
        public static void renderPicture(XWPFRun run, PictureRenderData picture) throws Exception {
            if (null == picture.getImage()) {
                throw new IllegalStateException("Can't get input data from picture!");
            }
            try (InputStream stream = new ByteArrayInputStream(picture.getImage())) {
                run.addPicture(stream, picture.getPictureType().type(), "Generated",
                        Units.pixelToEMU(picture.getWidth()), Units.pixelToEMU(picture.getHeight()));
            }
        }
    }
}
