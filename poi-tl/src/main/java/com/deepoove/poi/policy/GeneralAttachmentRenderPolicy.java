/*
 * Copyright 2014-2026 Sayi
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.hpsf.ClassIDPredefined;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLFactory;
import org.apache.poi.ooxml.POIXMLRelation;
import org.apache.poi.ooxml.POIXMLTypeLoader;
import org.apache.poi.ooxml.util.DocumentHelper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.poifs.filesystem.Ole10Native;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.deepoove.poi.data.GeneralAttachmentRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * Word 中插入通用附件渲染策略
 *
 * @author yangxiao, Sayi
 */
public class GeneralAttachmentRenderPolicy extends AbstractRenderPolicy<GeneralAttachmentRenderData> {

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

    private static final Set<NiceXWPFDocument> RENDERED_DOCS = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    @Override
    protected boolean validate(GeneralAttachmentRenderData data) {
        return null != data && null != data.readAttachmentData() && null != data.getFileName();
    }

    @Override
    protected void afterRender(RenderContext<GeneralAttachmentRenderData> context) {
        super.clearPlaceholder(context, false);
    }

    @Override
    public void doRender(RenderContext<GeneralAttachmentRenderData> context) throws Exception {
        NiceXWPFDocument doc = context.getXWPFDocument();
        XWPFRun run = context.getRun();
        CTR ctr = run.getCTR();

        String shapeTypeXml = getShapeTypeXml(doc);

        String uuidRandom = UUID.randomUUID().toString().replace("-", "") + ThreadLocalRandom.current().nextInt(1024);
        String shapeId = "_x0000_i20" + uuidRandom;

        GeneralAttachmentRenderData data = context.getData();
        byte[] attachment = data.readAttachmentData();

        PictureRenderData icon = data.getIcon();
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
        String embeddId = createOLEObject(doc, attachment, data.getFileName());

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
                + "                    <o:OLEObject Type=\"Embed\" ProgID=\"" + data.getProgramId() + "\" ShapeID=\"" + shapeId
                + "\" DrawAspect=\"Icon\" ObjectID=\"" + shapeId + "\" r:id=\"" + embeddId + "\">\n"
                + "                     <o:FieldCodes>\\s</o:FieldCodes>\n"
                + "                    </o:OLEObject>"
                + "            </w:object>";

        Document document = DocumentHelper.readDocument(new InputSource(new StringReader(wObjectXml)));
        ctr.set(XmlObject.Factory.parse(document.getDocumentElement(), POIXMLTypeLoader.DEFAULT_XML_OPTIONS));
    }

    private String getShapeTypeXml(NiceXWPFDocument doc) {
        if (!RENDERED_DOCS.add(doc)) {
            return "";
        }
        if (doc.getDocument().xmlText().contains(SHAPE_TYPE_ID)) {
            return "";
        }
        return SHAPE_TYPE_XML;
    }

    /**
     * 创建 OLE Object
     */
    private String createOLEObject(XWPFDocument document, byte[] fileData, String filename)
            throws InvalidFormatException, IOException {
        GeneralXWPFRelation oleRelDef = GeneralXWPFRelation.OLE_OBJECT;
        int unusedPartIndex = document.getPackage().getUnusedPartIndex(oleRelDef.getDefaultFileName());
        POIXMLDocumentPart.RelationPart relationship = document.createRelationship(oleRelDef,
                GeneralXWPFFactory.INSTANCE, unusedPartIndex, false);
        try (OutputStream out = relationship.getDocumentPart().getPackagePart().getOutputStream();
                POIFSFileSystem poifs = new POIFSFileSystem()) {
            String cleanFileName = cleanFileName(filename);
            String command = toAsciiSafeCommand(cleanFileName);
            Ole10Native ole10Native = new Ole10Native(cleanFileName, cleanFileName, command, fileData);
            ole10Native.setLabel2(cleanFileName);
            ole10Native.setFileName2(cleanFileName);
            ole10Native.setCommand2(command);
            poifs.getRoot().setStorageClsid(ClassIDPredefined.OLE_V1_PACKAGE.getClassID());
            Ole10Native.createOleMarkerEntry(poifs);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ole10Native.writeOut(bos);
            poifs.createDocument(new ByteArrayInputStream(bos.toByteArray()), Ole10Native.OLE10_NATIVE);
            poifs.writeFilesystem(out);
            return relationship.getRelationship().getId();
        }
    }

    private String cleanFileName(String filename) {
        if (null == filename || filename.trim().isEmpty()) {
            return "attachment.bin";
        }
        int lastSep = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
        if (lastSep >= 0 && lastSep < filename.length() - 1) {
            return filename.substring(lastSep + 1);
        }
        return filename;
    }

    private String toAsciiSafeCommand(String filename) {
        boolean isAscii = true;
        for (int i = 0; i < filename.length(); i++) {
            if (filename.charAt(i) > 127) {
                isAscii = false;
                break;
            }
        }
        if (isAscii) {
            return filename;
        }
        String ext = getExt(filename);
        return "attachment." + ext;
    }

    private String getExt(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return lastDot > 0 ? filename.substring(lastDot + 1) : "bin";
    }

    static class GeneralXWPFRelation extends POIXMLRelation {

        static final GeneralXWPFRelation OLE_OBJECT = new GeneralXWPFRelation(
                "application/vnd.openxmlformats-officedocument.oleObject",
                "http://schemas.openxmlformats.org/officeDocument/2006/relationships/oleObject",
                "/word/embeddings/oleObject#.bin",
                GeneralXWPFDocumentPart::new,
                GeneralXWPFDocumentPart::new,
                null
        );

        protected GeneralXWPFRelation(String type, String rel, String defaultName,
                NoArgConstructor noArgConstructor,
                PackagePartConstructor packagePartConstructor,
                ParentPartConstructor parentPartConstructor) {
            super(type, rel, defaultName, noArgConstructor, packagePartConstructor, parentPartConstructor);
        }
    }

    static class GeneralXWPFFactory extends POIXMLFactory {
        static final GeneralXWPFFactory INSTANCE = new GeneralXWPFFactory();

        @Override
        protected POIXMLRelation getDescriptor(String relationshipType) {
            return GeneralXWPFRelation.OLE_OBJECT;
        }
    }

    static class GeneralXWPFDocumentPart extends POIXMLDocumentPart {

        GeneralXWPFDocumentPart() {
        }

        public GeneralXWPFDocumentPart(final PackagePart part) {
            super(part);
        }

        @Override
        protected void prepareForCommit() {
            // do not clear the part here
        }
    }
}
