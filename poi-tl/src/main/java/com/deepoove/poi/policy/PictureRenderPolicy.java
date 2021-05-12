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
package com.deepoove.poi.policy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Supplier;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.data.style.PictureStyle.PictureAlign;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.BufferedImageUtils;
import com.deepoove.poi.util.SVGConvertor;
import com.deepoove.poi.util.UnitUtils;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.deepoove.poi.xwpf.XWPFRunWrapper;

/**
 * picture render
 * 
 * @author Sayi
 */
public class PictureRenderPolicy extends AbstractRenderPolicy<Object> {

    @Override
    protected boolean validate(Object data) {
        if (null == data) return false;
        if (data instanceof PictureRenderData) {
            return null != ((PictureRenderData) data).getPictureSupplier();
        }
        return true;
    }

    @Override
    public void doRender(RenderContext<Object> context) throws Exception {
        Helper.renderPicture(context.getRun(), wrapper(context.getData()));
    }

    @Override
    protected void afterRender(RenderContext<Object> context) {
        clearPlaceholder(context, false);
    }

    @Override
    protected void reThrowException(RenderContext<Object> context, Exception e) {
        logger.info("Render picture " + context.getEleTemplate() + " error: {}", e.getMessage());
        String alt = "";
        if (context.getData() instanceof PictureRenderData) {
            alt = ((PictureRenderData) context.getData()).getAltMeta();
        }
        context.getRun().setText(alt, 0);
    }

    private static PictureRenderData wrapper(Object object) {
        if (object instanceof PictureRenderData) return (PictureRenderData) object;
        return Pictures.of(object.toString()).fitSize().create();
    }

    public static class Helper {
        public static void renderPicture(XWPFRun run, PictureRenderData picture) throws Exception {
            Supplier<byte[]> supplier = picture.getPictureSupplier();
            byte[] imageBytes = supplier.get();
            if (null == imageBytes) {
                throw new IllegalStateException("Can't read picture byte arrays!");
            }
            PictureType pictureType = picture.getPictureType();
            if (null == pictureType) {
                pictureType = PictureType.suggestFileType(imageBytes);
            }
            if (null == pictureType) {
                throw new RenderException("PictureRenderData must set picture type!");
            }

            PictureStyle style = picture.getPictureStyle();
            if (null == style) style = new PictureStyle();
            int width = style.getWidth();
            int height = style.getHeight();

            if (pictureType == PictureType.SVG) {
                imageBytes = SVGConvertor.toPng(imageBytes, (float) width, (float) height);
                pictureType = PictureType.PNG;
            }
            if (!isSetSize(style)) {
                BufferedImage original = BufferedImageUtils.readBufferedImage(imageBytes);
                width = original.getWidth();
                height = original.getHeight();
                if (style.getScalePattern() == WidthScalePattern.FIT) {
                    BodyContainer bodyContainer = BodyContainerFactory
                            .getBodyContainer(((IBodyElement) run.getParent()).getBody());
                    int pageWidth = UnitUtils
                            .twips2Pixel(bodyContainer.elementPageWidth((IBodyElement) run.getParent()));
                    if (width > pageWidth) {
                        double ratio = pageWidth / (double) width;
                        width = pageWidth;
                        height = (int) (height * ratio);
                    }
                }
            }
            try (InputStream stream = new ByteArrayInputStream(imageBytes)) {
                PictureAlign align = style.getAlign();
                if (null != align && run.getParent() instanceof XWPFParagraph) {
                    ((XWPFParagraph) run.getParent()).setAlignment(ParagraphAlignment.valueOf(align.ordinal() + 1));
                }
                XWPFRunWrapper wrapper = new XWPFRunWrapper(run, false);
                wrapper.addPicture(stream, pictureType.type(), "Generated", Units.pixelToEMU(width),
                        Units.pixelToEMU(height));
            }
        }

        private static boolean isSetSize(PictureStyle style) {
            return (style.getWidth() != 0 || style.getHeight() != 0)
                    && style.getScalePattern() == WidthScalePattern.NONE;
        }
    }
}