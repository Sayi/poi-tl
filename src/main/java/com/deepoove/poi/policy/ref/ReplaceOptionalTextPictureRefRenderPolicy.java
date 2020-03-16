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
package com.deepoove.poi.policy.ref;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBlip;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBlipFillProperties;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.ByteUtils;
import com.deepoove.poi.util.ReflectionUtils;
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
    private int format;

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
    public ReplaceOptionalTextPictureRefRenderPolicy(String optionalText, InputStream replaceStream, int fomart) {
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
    public ReplaceOptionalTextPictureRefRenderPolicy(String optionalText, byte[] replaceData, int format) {
        this.optionalText = optionalText;
        this.data = replaceData;
        this.format = format;
    }

    @Override
    public String optionalText() {
        return optionalText;
    }

    @Override
    public void doRender(List<XWPFPicture> pictures, XWPFTemplate template) throws Exception {
        NiceXWPFDocument doc = template.getXWPFDocument();
        String docId = null;
        String hid = null;
        String fid = null;
        for (XWPFPicture t : pictures) {
            logger.info("Replace the picture data for the reference object: {}", t);
            XWPFRun run = (XWPFRun) ReflectionUtils.getValue("run", t);
            if (run.getParent().getPart() instanceof XWPFHeader) {
                XWPFHeaderFooter headerFooter = (XWPFHeaderFooter) run.getParent().getPart();
                setPictureReference(t, hid == null ? hid = headerFooter.addPictureData(data, format) : hid);
            } else if (run.getParent().getPart() instanceof XWPFFooter) {
                XWPFHeaderFooter headerFooter = (XWPFHeaderFooter) run.getParent().getPart();
                setPictureReference(t, fid == null ? fid = headerFooter.addPictureData(data, format) : fid);
            } else {
                setPictureReference(t, docId == null ? docId = doc.addPictureData(data, format) : docId);
            }
        }
    }

    private void setPictureReference(XWPFPicture t, String relationId) {
        CTPicture ctPic = t.getCTPicture();
        CTBlipFillProperties bill = ctPic.getBlipFill();
        CTBlip blip = bill.getBlip();
        blip.setEmbed(relationId);
    }

}
