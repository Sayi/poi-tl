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
package com.deepoove.poi.policy.reference;

import java.util.function.Supplier;

import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.template.PictImageTemplate;
import com.deepoove.poi.xwpf.CTPictWrapper;

public class DefaultPictImageTemplateRenderPolicy
        extends AbstractTemplateRenderPolicy<PictImageTemplate, PictureRenderData> {

    @Override
    public void doRender(PictImageTemplate pictImageTemplate, PictureRenderData data, XWPFTemplate template)
            throws Exception {
        CTPictWrapper t = pictImageTemplate.getPicture();
        Supplier<byte[]> supplier = data.getPictureSupplier();
        byte[] image = supplier.get();
        PictureType pictureType = data.getPictureType();
        if (null == pictureType) {
            pictureType = PictureType.suggestFileType(image);
        }
        XWPFRun run = pictImageTemplate.getRun();
        if (run.getParent().getPart() instanceof XWPFHeaderFooter) {
            XWPFHeaderFooter headerFooter = (XWPFHeaderFooter) run.getParent().getPart();
            setPictureReference(t, headerFooter.addPictureData(image, pictureType.type()));
        } else {
            setPictureReference(t, template.getXWPFDocument().addPictureData(image, pictureType.type()));
        }
    }

    private void setPictureReference(CTPictWrapper pictWrapper, String rid) {
        pictWrapper.setImageData(rid);
    }

}
