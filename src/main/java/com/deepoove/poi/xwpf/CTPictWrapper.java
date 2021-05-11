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
package com.deepoove.poi.xwpf;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;

import com.microsoft.schemas.vml.CTImageData;
import com.microsoft.schemas.vml.CTShape;

public class CTPictWrapper {

    private CTPicture ctPicture;
    private CTShape ctShape;

    public CTPictWrapper(CTPicture ctPicture) {
        this.ctPicture = ctPicture;

        XmlCursor newCursor = this.ctPicture.newCursor();
        try {
            boolean child = newCursor.toLastChild();
            if (!child) return;
            XmlObject xmlObject = newCursor.getObject();
            if (null == xmlObject || !(xmlObject instanceof CTShape)) return;
            CTShape ctShape = (CTShape) xmlObject;
            this.ctShape = ctShape;
        } finally {
            newCursor.dispose();
        }
    }

    public String getShapeAlt() {
        return null == ctShape ? null : ctShape.getAlt();
    }

    public void setImageData(String rid) {
        if (CollectionUtils.isNotEmpty(ctShape.getImagedataList())) {
            CTImageData imageData = ctShape.getImagedataArray(0);
            imageData.setId2(rid);
        }
    }

    public CTPicture getCtPicture() {
        return ctPicture;
    }

}
