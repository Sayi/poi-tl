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

import java.io.StringReader;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.ooxml.POIXMLTypeLoader;
import org.apache.poi.ooxml.util.DocumentHelper;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.deepoove.poi.data.AttachmentRenderData;
import com.deepoove.poi.data.AttachmentType;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * attachment render
 * 
 * @author sayi
 *
 */
public class AttachmentRenderPolicy extends AbstractRenderPolicy<AttachmentRenderData> {

    private static final String SHAPE_TYPE_ID = "_x0000_t79";
    private static final String SHAPE_TYPE_XML = "<v:shapetype id=\"" + SHAPE_TYPE_ID + "\" coordsize=\"21600,21600\" o:spt=\"75\" o:preferrelative=\"t\" "
                + "                      path=\"m@4@5l@4@11@9@11@9@5xe\" filled=\"f\" stroked=\"f\">\n"
                + "                        <v:stroke joinstyle=\"miter\"/>\n" 
                + "                        <v:formulas>\n"
                + "                            <v:f eqn=\"if lineDrawn pixelLineWidth 0\"/>\n"
                + "                            <v:f eqn=\"sum @0 1 0\"/>\n"
                + "                            <v:f eqn=\"sum 0 0 @1\"/>\n"
                + "                            <v:f eqn=\"prod @2 1 2\"/>\n"
                + "                            <v:f eqn=\"prod @3 21600 pixelWidth\"/>\n"
                + "                            <v:f eqn=\"prod @3 21600 pixelHeight\"/>\n"
                + "                            <v:f eqn=\"sum @0 0 1\"/>\n"
                + "                            <v:f eqn=\"prod @6 1 2\"/>\n"
                + "                            <v:f eqn=\"prod @7 21600 pixelWidth\"/>\n"
                + "                            <v:f eqn=\"sum @8 21600 0\"/>\n"
                + "                            <v:f eqn=\"prod @7 21600 pixelHeight\"/>\n"
                + "                            <v:f eqn=\"sum @10 21600 0\"/>\n"
                + "                        </v:formulas>\n"
                + "                        <v:path o:extrusionok=\"f\" gradientshapeok=\"t\" o:connecttype=\"rect\"/>\n"
                + "                        <o:lock v:ext=\"edit\" aspectratio=\"t\"/>\n"
                + "                    </v:shapetype>\n";

    private static boolean haveShapeType;

    @Override
    protected boolean validate(AttachmentRenderData data) {
        return null != data && null != data.readAttachmentData() && null != data.getFileType();
    }

    @Override
    protected void afterRender(RenderContext<AttachmentRenderData> context) {
        super.clearPlaceholder(context, false);
    }

    @Override
    public void doRender(RenderContext<AttachmentRenderData> context) throws Exception {
        NiceXWPFDocument doc = context.getXWPFDocument();
        XWPFRun run = context.getRun();
        CTR ctr = run.getCTR();

        // Only one shapetype is needed
        String shapeTypeXml = "";
        if (!haveShapeType) {
            haveShapeType = true;
            shapeTypeXml = SHAPE_TYPE_XML;
        }

        String uuidRandom = UUID.randomUUID().toString().replace("-", "") + ThreadLocalRandom.current().nextInt(1024);
        String shapeId = "_x0000_i20" + uuidRandom;

        AttachmentRenderData data = context.getData();
        AttachmentType fileType = data.getFileType();
        byte[] attachment = data.readAttachmentData();

        PictureRenderData icon = data.getIcon();
        if (null == icon) {
            icon = Pictures.ofBase64(fileType.icon(), PictureType.PNG).size(64, 64).create();
        }
        byte[] image = icon.readPictureData();
        PictureType pictureType = icon.getPictureType();
        if (null == pictureType) {
            pictureType = PictureType.suggestFileType(image);
        }

        PictureStyle style = icon.getPictureStyle();
        if (null == style) style = new PictureStyle();
        double widthPt = Units.pixelToPoints(style.getWidth());
        double heightPt = Units.pixelToPoints(style.getHeight());

        String imageRId = doc.addPictureData(image, pictureType.type());
        // String embeddId = doc.addEmbeddData(attachment, fileType.ordinal());
        String embeddId = doc.addEmbeddData(attachment, fileType.contentType(),
                "/word/embeddings/" + uuidRandom + fileType.ext());

        String wObjectXml = "<w:object xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\""
                + "             xmlns:v=\"urn:schemas-microsoft-com:vml\""
                + "             xmlns:o=\"urn:schemas-microsoft-com:office:office\""
                + "             xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
                + "             w:dxaOrig=\"1520\" w:dyaOrig=\"960\">\n" + shapeTypeXml
                + "                    <v:shape id=\"" + shapeId + "\" type=\"#"
                                            + SHAPE_TYPE_ID + "\" alt=\"\" style=\"width:" + widthPt + "pt;height:" + heightPt
                                            + "pt;mso-width-percent:0;mso-height-percent:0;mso-width-percent:0;mso-height-percent:0\" o:ole=\"\">\n"
                + "                        <v:imagedata r:id=\"" + imageRId + "\" o:title=\"\"/>\n"
                + "                    </v:shape>\n"
                + "                    <o:OLEObject Type=\"Embed\" ProgID=\""+ fileType.programId() + "\" ShapeID=\"" + shapeId
                                            + "\" DrawAspect=\"Icon\" ObjectID=\""+shapeId+"\" r:id=\""+ embeddId + "\">\n"
                + "                     <o:FieldCodes>\\s</o:FieldCodes>\n"
                + "                    </o:OLEObject>"
                + "            </w:object>";

        Document document = DocumentHelper.readDocument(new InputSource(new StringReader(wObjectXml)));
        ctr.set(XmlObject.Factory.parse(document.getDocumentElement(), POIXMLTypeLoader.DEFAULT_XML_OPTIONS));
    }

}