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
package com.deepoove.poi.render.processor;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLDocumentPart.RelationPart;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTRelId;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObjectData;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import com.deepoove.poi.xwpf.IdenifierManagerWrapper;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

public class DrawingSupport {

    public static void updateDocPrId(XWPFTable table) {
        List<XWPFTableRow> rows = table.getRows();
        rows.forEach(row -> {
            List<XWPFTableCell> cells = row.getTableCells();
            cells.forEach(cell -> {
                cell.getParagraphs().forEach(DrawingSupport::updateDocPrId);
                cell.getTables().forEach(DrawingSupport::updateDocPrId);
            });
        });
    }

    public static void updateDocPrId(XWPFParagraph paragraph) {
        updateDocPrId(paragraph.getRuns());

    }

    public static void updateDocPrId(List<XWPFRun> runs) {
        runs.forEach(DrawingSupport::updateDocPrId);
    }

    public static void updateDocPrId(XWPFRun run) {
        NiceXWPFDocument document = (NiceXWPFDocument) run.getDocument();
        if (!document.getDocPrIdenifierManager().isValid()) return;
        CTR r = run.getCTR();
        for (CTDrawing ctDrawing : r.getDrawingList()) {
            processCTDrawing(document, ctDrawing);
        }
        XmlObject[] xmlObjects = r.selectPath(IdenifierManagerWrapper.XPATH_DRAWING);
        if (null == xmlObjects || xmlObjects.length <= 0) return;
        for (XmlObject xmlObject : xmlObjects) {
            try {
                CTDrawing ctDrawing = CTDrawing.Factory.parse(xmlObject.xmlText());
                processCTDrawing(document, ctDrawing);
                xmlObject.set(ctDrawing);
            } catch (XmlException e) {
                // no-op
            }
        }
    }

    private static void processCTDrawing(NiceXWPFDocument document, CTDrawing ctDrawing) {
        for (CTAnchor anchor : ctDrawing.getAnchorList()) {
            if (anchor.getDocPr() != null) {
                anchor.getDocPr().setId(document.getDocPrIdenifierManager().reserveNew());
            }
            processCTGraaphicalObject(document, anchor.getGraphic());
        }
        for (CTInline inline : ctDrawing.getInlineList()) {
            if (inline.getDocPr() != null) {
                inline.getDocPr().setId(document.getDocPrIdenifierManager().reserveNew());
            }
            processCTGraaphicalObject(document, inline.getGraphic());
        }
    }

    private static void processCTGraaphicalObject(NiceXWPFDocument document, CTGraphicalObject graphic) {
        // read chart rid
        CTGraphicalObjectData graphicData = graphic.getGraphicData();
        XmlCursor newCursor = graphicData.newCursor();
        try {
            boolean child = newCursor.toChild(0);
            if (!child) return;
            XmlObject xmlObject = newCursor.getObject();
            if (null == xmlObject || !(xmlObject instanceof CTRelId)) return;
            CTRelId cchart = (CTRelId) xmlObject;
            String rid = cchart.getId();
            if (null == rid) return;
            // replace rid
            POIXMLDocumentPart documentPart = document.getRelationById(rid);
            if (null == documentPart || !(documentPart instanceof XWPFChart)) return;
            try {
                XWPFChart xwpfChart = (XWPFChart) documentPart;
                RelationPart relationPart = document.addChartData(xwpfChart);
                String id = relationPart.getRelationship().getId();
                cchart.setId(id);
            } catch (InvalidFormatException | IOException e) {
                e.printStackTrace();
            }
        } finally {
            newCursor.dispose();
        }
    }

}
