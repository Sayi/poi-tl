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

import javax.xml.namespace.QName;

import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTRelId;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObjectData;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.impl.CTNonVisualDrawingPropsImpl;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;

public class CTDrawingWrapper {

    private CTDrawing ctDrawing;
    private CTNonVisualDrawingProps docPr;

    public CTDrawingWrapper(CTDrawing ctDrawing) {
        this.ctDrawing = ctDrawing;
        if (ctDrawing.sizeOfAnchorArray() > 0) {
            CTAnchor anchorArray = ctDrawing.getAnchorArray(0);
            this.docPr = anchorArray.getDocPr();

        } else if (ctDrawing.sizeOfInlineArray() > 0) {
            CTInline inline = ctDrawing.getInlineArray(0);
            this.docPr = inline.getDocPr();
        }
    }

    public String getTitle() {
        if (null != docPr) {
            QName TITLE = new QName("", "title");
            CTNonVisualDrawingPropsImpl docPrImpl = (CTNonVisualDrawingPropsImpl) docPr;
            synchronized (docPrImpl.monitor()) {
                // check_orphaned();
                SimpleValue localSimpleValue = null;
                localSimpleValue = (SimpleValue) docPrImpl.get_store().find_attribute_user(TITLE);
                if (localSimpleValue == null) {
                    return null;
                }
                return localSimpleValue.getStringValue();
            }
            // String descr = docPr.getDescr();
        }
        return null;
    }

    public String getDesc() {
        return null == docPr ? null : docPr.getDescr();
    }

    public String getChartId() {
        CTGraphicalObjectData graphicData = null;
        if (ctDrawing.sizeOfAnchorArray() > 0) {
            CTAnchor anchorArray = ctDrawing.getAnchorArray(0);
            graphicData = anchorArray.getGraphic().getGraphicData();

        } else if (ctDrawing.sizeOfInlineArray() > 0) {
            CTInline inline = ctDrawing.getInlineArray(0);
            graphicData = inline.getGraphic().getGraphicData();
        }
        XmlCursor newCursor = graphicData.newCursor();
        try {
            boolean child = newCursor.toChild(0);
            if (!child) return null;
            XmlObject xmlObject = newCursor.getObject();
            if (null == xmlObject || !(xmlObject instanceof CTRelId)) return null;
            CTRelId cchart = (CTRelId) xmlObject;
            return cchart.getId();
        } finally {
            newCursor.dispose();
        }
    }

}
