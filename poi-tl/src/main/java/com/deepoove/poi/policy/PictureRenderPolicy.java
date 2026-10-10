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
package com.deepoove.poi.policy;

import com.deepoove.poi.converter.ObjectToPictureRenderDataConverter;
import com.deepoove.poi.converter.ToRenderDataConverter;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
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
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Renders a tag as a picture
 * <p>
 * It converts the bound value into {@link PictureRenderData} and inserts the
 * image into the run of the tag. SVG data is converted to PNG, the picture is
 * sized from its {@link PictureStyle} and, when no size is set, the original size
 * or the {@code FIT} width scale pattern is applied. When rendering fails, the
 * alt text of the data is written instead.
 * </p>
 *
 * @author Sayi
 */
public class PictureRenderPolicy extends AbstractRenderPolicy<PictureRenderData> {

    private static ToRenderDataConverter<Object, PictureRenderData> converter = new ObjectToPictureRenderDataConverter();

    /**
     * Converts the bound value into picture render data.
     * 
     * @param source the value bound to the tag
     * @return the converted picture render data
     * @throws Exception when the conversion fails
     */
    @Override
    public PictureRenderData cast(Object source) throws Exception {
        return converter.convert(source);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The data is valid when it is not {@code null}.
     * </p>
     */
    @Override
    protected boolean validate(PictureRenderData data) {
        return null != data;
    }

    /**
     * {@inheritDoc}
     * <p>
     * It delegates to {@link Helper#renderPicture(XWPFRun, PictureRenderData)}.
     * </p>
     */
    @Override
    public void doRender(RenderContext<PictureRenderData> context) throws Exception {
        Helper.renderPicture(context.getRun(), context.getData());
    }

    /**
     * {@inheritDoc}
     * <p>
     * It clears the tag text without removing the paragraph that holds the picture.
     * </p>
     */
    @Override
    protected void afterRender(RenderContext<PictureRenderData> context) {
        clearPlaceholder(context, false);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Instead of failing the rendering, it writes the alt text of the data into the
     * run.
     * </p>
     */
    @Override
    protected void reThrowException(RenderContext<PictureRenderData> context, Exception e) {
        logger.info("Render picture " + context.getEleTemplate() + " error: {}", e.getMessage());
        String alt = context.getData().getAltMeta();
        context.getRun().setText(alt, 0);
    }

    /**
     * Utilities that insert pictures into a Word run.
     */
    public static class Helper {
        /**
         * Inserts the picture into the given run.
         * 
         * @param run     the run to write into
         * @param picture the picture render data
         * @throws Exception when the picture can not be read, converted or inserted
         */
        public static void renderPicture(XWPFRun run, PictureRenderData picture) throws Exception {
            byte[] imageBytes = picture.readPictureData();
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
            int svgScale = style.getSvgScale();

            if (pictureType == PictureType.SVG) {
                imageBytes = SVGConvertor.toPng(imageBytes, (float) width, (float) height, svgScale);
                pictureType = PictureType.PNG;
            }
            if (!isSetSize(style)) {
                BufferedImage original = BufferedImageUtils.readBufferedImage(imageBytes);
                width = original.getWidth();
                height = original.getHeight();
                if (style.getScalePattern() == WidthScalePattern.FIT) {
                    BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
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
                run.addPicture(stream, pictureType.type(), "Generated", Units.pixelToEMU(width),
                    Units.pixelToEMU(height));
            }
        }

        private static boolean isSetSize(PictureStyle style) {
            return (style.getWidth() != 0 || style.getHeight() != 0)
                && style.getScalePattern() == WidthScalePattern.NONE;
        }
    }
}