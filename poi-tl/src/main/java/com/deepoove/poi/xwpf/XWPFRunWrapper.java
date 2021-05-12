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

import static org.apache.poi.ooxml.POIXMLTypeLoader.DEFAULT_XML_OPTIONS;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ooxml.util.DocumentHelper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.impl.values.XmlAnyTypeImpl;
import org.openxmlformats.schemas.drawingml.x2006.main.*;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPictureNonVisual;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTxbxContent;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.deepoove.poi.plugin.comment.XWPFComments;

public class XWPFRunWrapper {

    public static final String XPATH_TXBX_TXBXCONTENT = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' \n"
            + "        declare namespace mc='http://schemas.openxmlformats.org/markup-compatibility/2006' .//mc:Choice/*/w:txbxContent";
    public static final String XPATH_TEXTBOX_TXBXCONTENT = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' \n"
            + "        declare namespace mc='http://schemas.openxmlformats.org/markup-compatibility/2006' .//mc:Fallback/*/w:txbxContent";
    public static final String XPATH_PICT_TEXTBOX_TXBXCONTENT = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' \n"
            + "        declare namespace v='urn:schemas-microsoft-com:vml' ./v:shape/v:textbox/w:txbxContent";

    private final XWPFRun run;
    private XWPFTextboxContent wpstxbx;
    private XWPFTextboxContent vtextbox;
    private XWPFTextboxContent shapetxbx;

    public XWPFRunWrapper(XWPFRun run) {
        this(run, true);
    }

    @SuppressWarnings("deprecation")
    public XWPFRunWrapper(XWPFRun run, boolean isParse) {
        this.run = run;
        if (!isParse) return;
        CTR r = run.getCTR();
        XmlObject[] xmlObjects = r.selectPath(XPATH_TXBX_TXBXCONTENT);
        if (xmlObjects != null && xmlObjects.length >= 1) {
            try {
                CTTxbxContent ctTxbxContent = CTTxbxContent.Factory.parse(xmlObjects[0].xmlText());
                wpstxbx = new XWPFTextboxContent(ctTxbxContent, run, run.getParagraph().getBody(), xmlObjects[0]);
            } catch (XmlException e) {
                // no-op
            }
        }
        xmlObjects = r.selectPath(XPATH_TEXTBOX_TXBXCONTENT);
        if (xmlObjects != null && xmlObjects.length >= 1) {
            try {
                CTTxbxContent ctTxbxContent = CTTxbxContent.Factory.parse(xmlObjects[0].xmlText());
                vtextbox = new XWPFTextboxContent(ctTxbxContent, run, run.getParagraph().getBody(), xmlObjects[0]);
            } catch (XmlException e) {
                // no-op
            }
        }
        org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture ctPicture = CollectionUtils
                .isNotEmpty(r.getPictList()) ? r.getPictArray(0) : null;
        if (null != ctPicture) {
            xmlObjects = ctPicture.selectPath(XPATH_PICT_TEXTBOX_TXBXCONTENT);
            if (xmlObjects != null && xmlObjects.length >= 1) {
                try {
                    CTTxbxContent ctTxbxContent = null;
                    if (xmlObjects[0] instanceof CTTxbxContent) {
                        ctTxbxContent = (CTTxbxContent) xmlObjects[0];
                    } else {
                        ctTxbxContent = CTTxbxContent.Factory.parse(xmlObjects[0].xmlText());
                    }
                    shapetxbx = new XWPFTextboxContent(ctTxbxContent, run, run.getParagraph().getBody(), xmlObjects[0]);
                } catch (XmlException e) {
                    // no-op
                }
            }
        }
    }

    public XWPFRun getRun() {
        return run;
    }

    public XWPFTextboxContent getWpstxbx() {
        return wpstxbx;
    }

    public XWPFTextboxContent getVtextbox() {
        return vtextbox;
    }

    public XWPFTextboxContent getShapetxbx() {
        return shapetxbx;
    }

    public XWPFPicture addPicture(InputStream pictureData, int pictureType, String filename, int width, int height)
            throws InvalidFormatException, Exception {
        IRunBody parent = run.getParent();
        if (!(parent.getPart() instanceof XWPFComments)) {
            return run.addPicture(pictureData, pictureType, filename, width, height);
        }
        XWPFComments comments = (XWPFComments) parent.getPart();
        String relationId = comments.addPictureData(pictureData, pictureType);
        XWPFPictureData picData = (XWPFPictureData) comments.getRelationById(relationId);

        // Create the drawing entry for it
        try {
            CTDrawing drawing = run.getCTR().addNewDrawing();
            CTInline inline = drawing.addNewInline();

            // Do the fiddly namespace bits on the inline
            // (We need full control of what goes where and as what)
            String xml = "<a:graphic xmlns:a=\"" + CTGraphicalObject.type.getName().getNamespaceURI() + "\">"
                    + "<a:graphicData uri=\"" + CTPicture.type.getName().getNamespaceURI() + "\">"
                    + "<pic:pic xmlns:pic=\"" + CTPicture.type.getName().getNamespaceURI() + "\" />"
                    + "</a:graphicData>" + "</a:graphic>";
            InputSource is = new InputSource(new StringReader(xml));
            org.w3c.dom.Document doc = DocumentHelper.readDocument(is);
            inline.set(XmlToken.Factory.parse(doc.getDocumentElement(), DEFAULT_XML_OPTIONS));

            // Setup the inline
            inline.setDistT(0);
            inline.setDistR(0);
            inline.setDistB(0);
            inline.setDistL(0);

            CTNonVisualDrawingProps docPr = inline.addNewDocPr();
            long id = ((NiceXWPFDocument) run.getParent().getDocument()).getDocPrIdenifierManager().reserveNew();
            docPr.setId(id);
            /* This name is not visible in Word 2010 anywhere. */
            docPr.setName("Drawing " + id);
            docPr.setDescr(filename);

            CTPositiveSize2D extent = inline.addNewExtent();
            extent.setCx(width);
            extent.setCy(height);

            // Grab the picture object
            CTGraphicalObject graphic = inline.getGraphic();
            CTGraphicalObjectData graphicData = graphic.getGraphicData();
            CTPicture pic = getCTPictures(graphicData).get(0);

            // Set it up
            CTPictureNonVisual nvPicPr = pic.addNewNvPicPr();

            CTNonVisualDrawingProps cNvPr = nvPicPr.addNewCNvPr();
            /* use "0" for the id. See ECM-576, 20.2.2.3 */
            cNvPr.setId(0L);
            /* This name is not visible in Word 2010 anywhere */
            cNvPr.setName("Picture " + id);
            cNvPr.setDescr(filename);

            CTNonVisualPictureProperties cNvPicPr = nvPicPr.addNewCNvPicPr();
            cNvPicPr.addNewPicLocks().setNoChangeAspect(true);

            CTBlipFillProperties blipFill = pic.addNewBlipFill();
            CTBlip blip = blipFill.addNewBlip();
            blip.setEmbed(parent.getPart().getRelationId(picData));
            blipFill.addNewStretch().addNewFillRect();

            CTShapeProperties spPr = pic.addNewSpPr();
            CTTransform2D xfrm = spPr.addNewXfrm();

            CTPoint2D off = xfrm.addNewOff();
            off.setX(0);
            off.setY(0);

            CTPositiveSize2D ext = xfrm.addNewExt();
            ext.setCx(width);
            ext.setCy(height);

            CTPresetGeometry2D prstGeom = spPr.addNewPrstGeom();
            prstGeom.setPrst(STShapeType.RECT);
            prstGeom.addNewAvLst();

            // Finish up
            XWPFPicture xwpfPicture = new XWPFPicture(pic, run);
            run.getEmbeddedPictures().add(xwpfPicture);
            return xwpfPicture;
        } catch (XmlException | SAXException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<CTPicture> getCTPictures(XmlObject o) {
        List<CTPicture> pics = new ArrayList<>();
        XmlObject[] picts = o
                .selectPath("declare namespace pic='" + CTPicture.type.getName().getNamespaceURI() + "' .//pic:pic");
        for (XmlObject pict : picts) {
            if (pict instanceof XmlAnyTypeImpl) {
                try {
                    pict = CTPicture.Factory.parse(pict.toString(), DEFAULT_XML_OPTIONS);
                } catch (XmlException e) {
                    throw new POIXMLException(e);
                }
            }
            if (pict instanceof CTPicture) {
                pics.add((CTPicture) pict);
            }
        }
        return pics;
    }

}
