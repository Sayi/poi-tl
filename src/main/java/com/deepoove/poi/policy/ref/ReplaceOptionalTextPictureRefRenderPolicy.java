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
package com.deepoove.poi.policy.ref;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBlip;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBlipFillProperties;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.ByteUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * 根据可选文字替换图片内容
 * 
 * @author Sayi
 * @version
 */
public class ReplaceOptionalTextPictureRefRenderPolicy extends OptionalTextPictureRefRenderPolicy {

    private final String optionalText;
    private byte[] data;
    private int fomart;

    /**
     * 替换匹配可选文字的图片
     * 
     * @param optionalText
     *            可选文字
     * @param replaceStream
     *            替换的图片流
     * @param fomart
     *            参见XWPFDocument.PICTURE_TYPE_PNG
     */
    public ReplaceOptionalTextPictureRefRenderPolicy(String optionalText, InputStream replaceStream,
            int fomart) {
        this(optionalText, ByteUtils.toByteArray(replaceStream), fomart);
    }

    /**
     * 替换匹配可选文字的图片
     * 
     * @param optionalText
     *            可选文字
     * @param replaceData
     *            替换的图片字节
     * @param fomart
     *            参见XWPFDocument.PICTURE_TYPE_PNG
     */
    public ReplaceOptionalTextPictureRefRenderPolicy(String optionalText, byte[] replaceData,
            int fomart) {
        this.optionalText = optionalText;
        this.data = replaceData;
        this.fomart = fomart;
    }

    @Override
    public String optionalText() {
        return optionalText;
    }

    @Override
    public void doRender(XWPFPicture t, XWPFTemplate template) throws Exception {
        logger.info("Replace the picture data for the reference object: {}", t);
        NiceXWPFDocument doc = template.getXWPFDocument();
        try (InputStream ins = new ByteArrayInputStream(data)) {
            String relationId = doc.addPictureData(ins, fomart);
            CTPicture ctPic = t.getCTPicture();
            CTBlipFillProperties bill = ctPic.getBlipFill();
            CTBlip blip = bill.getBlip();
            blip.setEmbed(relationId);
        }
    }

}
