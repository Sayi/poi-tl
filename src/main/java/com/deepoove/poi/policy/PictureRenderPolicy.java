/*
 * Copyright 2014-2015 the original author or authors.
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
package com.deepoove.poi.policy;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.render.RenderContext;

public class PictureRenderPolicy extends AbstractRenderPolicy<PictureRenderData> {

    @Override
    protected boolean validate(PictureRenderData data) {
        return (null != data && (null != data.getData() || null != data.getPath()));
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
        public static final int EMU = 9525;

        public static void renderPicture(XWPFRun run, PictureRenderData picture) throws Exception {
            int suggestFileType = suggestFileType(picture.getPath());
            InputStream ins = null;
            try {
                ins = null == picture.getData() ? new FileInputStream(picture.getPath())
                        : new ByteArrayInputStream(picture.getData());
                run.addPicture(ins, suggestFileType, "Generated", picture.getWidth() * EMU,
                        picture.getHeight() * EMU);
            }
            finally {
                IOUtils.closeQuietly(ins);
            }
        }

        public static int suggestFileType(String imgFile) {
            int format = 0;

            if (imgFile.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
            else if (imgFile.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
            else if (imgFile.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
            else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg"))
                format = XWPFDocument.PICTURE_TYPE_JPEG;
            else if (imgFile.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
            else if (imgFile.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
            else if (imgFile.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
            else if (imgFile.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
            else if (imgFile.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
            else if (imgFile.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
            else if (imgFile.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
            else {
                throw new RenderException("Unsupported picture: " + imgFile
                        + ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");
            }
            return format;
        }

    }
}
