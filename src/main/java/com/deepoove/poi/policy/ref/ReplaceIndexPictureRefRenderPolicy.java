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
 * 根据图片的位置替换内容
 * 
 * @author Sayi
 * @version
 */
public class ReplaceIndexPictureRefRenderPolicy extends IndexRefRenderPolicy<XWPFPicture> {

    private final int index;
    private byte[] data;
    private int fomart;

    /**
     * @param index
     *            图片位置
     * @param replaceStream
     * @param fomart
     *            参见XWPFDocument.PICTURE_TYPE_PNG
     */
    public ReplaceIndexPictureRefRenderPolicy(int index, InputStream replaceStream, int fomart) {
        this(index, ByteUtils.toByteArray(replaceStream), fomart);
    }

    /**
     * @param index
     *            图片位置
     * @param replaceData
     * @param fomart
     *            参见XWPFDocument.PICTURE_TYPE_PNG
     */
    public ReplaceIndexPictureRefRenderPolicy(int index, byte[] replaceData, int fomart) {
        this.index = index;
        this.data = replaceData;
        this.fomart = fomart;
    }

    @Override
    public int index() {
        return index;
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
