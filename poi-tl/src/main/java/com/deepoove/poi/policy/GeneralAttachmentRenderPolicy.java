package com.deepoove.poi.policy;

import com.deepoove.poi.data.GeneralAttachmentRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
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
import org.apache.poi.xslf.usermodel.XSLFRelation;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.schema.XmlObjectFactory;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Word 中插入通用附件渲染策略
 */
public class GeneralAttachmentRenderPolicy extends AbstractRenderPolicy<GeneralAttachmentRenderData> {

    private static final String SHAPE_TYPE_ID = "_x0000_t75";

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

        String uuidRandom = UUID.randomUUID().toString().replace("-", "") + ThreadLocalRandom.current().nextInt(1024);
        String shapeId = "_x0000_i10" + uuidRandom;

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
//         String embeddId = doc.addEmbeddData(attachment, 1);
//        String embeddId = doc.addEmbeddData(attachment, data.getContentType(),
//                "/xwpf/embeddings/" + uuidRandom + data.ext());

        String embeddId = createOLEObject(doc, attachment, data.getFileName());

        String wObjectXml = "<w:object xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\"" +
                "             xmlns:v=\"urn:schemas-microsoft-com:vml\"" +
                "             xmlns:o=\"urn:schemas-microsoft-com:office:office\"" +
                "             xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"" +
                "             w:dxaOrig=\"1520\" w:dyaOrig=\"960\">\n" +
                "                <v:shapetype id=\"" + SHAPE_TYPE_ID + "\" coordsize=\"21600,21600\" o:spt=\"75\" o:preferrelative=\"t\"\n" +
                "                             path=\"m@4@5l@4@11@9@11@9@5xe\" filled=\"f\" stroked=\"f\">\n" +
                "                  <v:stroke joinstyle=\"miter\"/>\n" +
                "                  <v:formulas>\n" +
                "                    <v:f eqn=\"if lineDrawn pixelLineWidth 0\"/>\n" +
                "                    <v:f eqn=\"sum @0 1 0\"/>\n" +
                "                    <v:f eqn=\"sum 0 0 @1\"/>\n" +
                "                    <v:f eqn=\"prod @2 1 2\"/>\n" +
                "                    <v:f eqn=\"prod @3 21600 pixelWidth\"/>\n" +
                "                    <v:f eqn=\"prod @3 21600 pixelHeight\"/>\n" +
                "                    <v:f eqn=\"sum @0 0 1\"/>\n" +
                "                    <v:f eqn=\"prod @6 1 2\"/>\n" +
                "                    <v:f eqn=\"prod @7 21600 pixelWidth\"/>\n" +
                "                    <v:f eqn=\"sum @8 21600 0\"/>\n" +
                "                    <v:f eqn=\"prod @7 21600 pixelHeight\"/>\n" +
                "                    <v:f eqn=\"sum @10 21600 0\"/>\n" +
                "                  </v:formulas>\n" +
                "                  <v:path o:extrusionok=\"f\" gradientshapeok=\"t\" o:connecttype=\"rect\"/>\n" +
                "                  <o:lock v:ext=\"edit\" aspectratio=\"t\"/>\n" +
                "                </v:shapetype>\n" +
                "                <v:shape id=\"" + shapeId + "\" type=\"#" + SHAPE_TYPE_ID + "\" style=\"width:" + widthPt + "pt;height:" + heightPt + "pt\" o:ole=\"\">\n" +
                "                  <v:imagedata r:id=\"" + imageRId + "\" o:title=\"\"/>\n" +
                "                </v:shape>\n" +
                "                <o:OLEObject Type=\"Embed\" ProgID=\"" + data.getProgramId() + "\" ShapeID=\"" + shapeId + "\" DrawAspect=\"Icon\"\n" +
                "                             ObjectID=\"" + shapeId + "\" r:id=\"" + embeddId + "\">\n" +
                "                  <o:LockedField>false</o:LockedField>\n" +
                "                </o:OLEObject>" +
                "              </w:object>";

        Document document = DocumentHelper.readDocument(new InputSource(new StringReader(wObjectXml)));
        XmlObjectFactory<XmlObject> factory = XmlObject.Factory;
        XmlObject parse = factory.parse(document.getDocumentElement(), POIXMLTypeLoader.DEFAULT_XML_OPTIONS);
        ctr.set(parse);
    }

    /**
     * 创建OLE Object
     * @param document
     * @param fileData
     * @param filename
     * @throws InvalidFormatException
     */
    private String createOLEObject(XWPFDocument document, byte[] fileData, String filename) throws InvalidFormatException, IOException {
        GeneralXWPFRelation oleRelDef = GeneralXWPFRelation.OLE_OBJECT;
        int unusedPartIndex = document.getPackage().getUnusedPartIndex(oleRelDef.getDefaultFileName());
        POIXMLDocumentPart.RelationPart relationship = document.createRelationship(oleRelDef, GeneralXWPFFactory.INSTANCE, unusedPartIndex, false);
        try (OutputStream out = relationship.getDocumentPart().getPackagePart().getOutputStream();
                POIFSFileSystem poifs = new POIFSFileSystem()) {
            String oleFileName = UUID.randomUUID().toString().replace("-", "") + "." + getExt(filename);
            Ole10Native ole10Native = new Ole10Native(oleFileName, oleFileName, oleFileName, fileData);
            poifs.getRoot().setStorageClsid(ClassIDPredefined.OLE_V1_PACKAGE.getClassID());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ole10Native.writeOut(bos);
            poifs.createDocument(new ByteArrayInputStream(bos.toByteArray()), Ole10Native.OLE10_NATIVE);
            poifs.writeFilesystem(out);
            return relationship.getRelationship().getId();
        }
    }

    /**
     * 获取扩展名
     * @return
     */
    private String getExt(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return lastDot > 0 ? filename.substring(lastDot + 1) : "bin";
    }

    /**
     * 创建OLE对象关系，该对象在不同情况下会产生不同的关系，顾不能通用
     */
    static class GeneralXWPFRelation extends POIXMLRelation {

        static final GeneralXWPFRelation OLE_OBJECT = new GeneralXWPFRelation(
                XSLFRelation.OLE_OBJECT.getContentType(),
                XSLFRelation.OLE_OBJECT.getRelation(),
                "/xwpf/embeddings/oleObject#.bin",
                XSLFRelation.OLE_OBJECT.getNoArgConstructor(),
                XSLFRelation.OLE_OBJECT.getPackagePartConstructor(),
                XSLFRelation.OLE_OBJECT.getParentPartConstructor()
        );

        protected GeneralXWPFRelation(String type, String rel, String defaultName, NoArgConstructor noArgConstructor, PackagePartConstructor packagePartConstructor, ParentPartConstructor parentPartConstructor) {
            super(type, rel, defaultName, noArgConstructor, packagePartConstructor, parentPartConstructor);
        }
    }

    static class GeneralXWPFFactory extends POIXMLFactory {
        static final GeneralXWPFFactory INSTANCE = new GeneralXWPFFactory();

        protected POIXMLRelation getDescriptor(String relationshipType) {
            return GeneralXWPFRelation.OLE_OBJECT;
        }

        protected POIXMLDocumentPart createDocumentPart
                (Class<? extends POIXMLDocumentPart> cls, Class<?>[] classes, Object[] values)
                throws SecurityException {
            return new GeneralXWPFDocumentPart();
        }
    }

    static class GeneralXWPFDocumentPart extends POIXMLDocumentPart {

        /**
         * Create a new XSLFObjectData node
         */
        GeneralXWPFDocumentPart() {
        }

        public GeneralXWPFDocumentPart(final PackagePart part) {
            super(part);
        }

        /**
         * XSLFObjectData objects store the actual content in the part directly without keeping a
         * copy like all others therefore we need to handle them differently.
         */
        @Override
        protected void prepareForCommit() {
            // do not clear the part here
        }


        public void setData(final byte[] data) throws IOException {
            try (final OutputStream os = getPackagePart().getOutputStream()) {
                os.write(data);
            }
        }
    }
}
