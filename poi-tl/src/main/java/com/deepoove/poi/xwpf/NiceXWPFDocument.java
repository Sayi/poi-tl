/*
 * Copyright 2014-2025 Sayi
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

import java.io.*;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.*;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ooxml.POIXMLRelation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.*;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.util.ParagraphUtils;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.util.ReflectionUtils;
import com.deepoove.poi.util.UnitUtils;

/**
 * Enhanced XWPFDocument
 * 
 * @author Sayi
 */
public class NiceXWPFDocument extends XWPFDocument {

    private static Logger logger = LoggerFactory.getLogger(NiceXWPFDocument.class);

    protected List<XWPFStructuredDocumentTag> structuredDocumentTags = new ArrayList<>();
    protected List<XWPFTable> allTables = new ArrayList<XWPFTable>();
    protected List<XWPFPicture> allPictures = new ArrayList<XWPFPicture>();
    protected List<POIXMLDocumentPart> embedds = new ArrayList<POIXMLDocumentPart>();
    protected IdenifierManagerWrapper idenifierManagerWrapper;
    protected boolean adjustDoc = false;

    protected Map<XWPFChart, PackagePart> chartMappingPart = new HashMap<>();
    protected static XWPFRelation DOCUMENT;

    static {
        try {
            Constructor<XWPFRelation> constructor = ReflectionUtils.findConstructor(XWPFRelation.class, String.class,
                    String.class, String.class, POIXMLRelation.NoArgConstructor.class,
                    POIXMLRelation.PackagePartConstructor.class);
            DOCUMENT = constructor.newInstance(
                    new Object[] { "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                            POIXMLDocument.PACK_OBJECT_REL_TYPE, "/word/embeddings/Microsoft_Word_#.docx",
                            new POIXMLRelation.NoArgConstructor() {

                                @Override
                                public POIXMLDocumentPart init() {
                                    return new XWPFDocument();
                                }
                            }, new POIXMLRelation.PackagePartConstructor() {

                                @Override
                                public POIXMLDocumentPart init(PackagePart part) throws IOException, XmlException {
                                    return new XWPFDocument(part.getInputStream());
                                }
                            } });
        } catch (Exception e) {
            logger.warn("init releation error: {}", e.getMessage());
        }
    }

    public NiceXWPFDocument() {
        super();
    }

    public NiceXWPFDocument(InputStream in) throws IOException {
        this(in, false);
    }

    public NiceXWPFDocument(InputStream in, boolean adjustDoc) throws IOException {
        super(in);
        this.adjustDoc = adjustDoc;
        idenifierManagerWrapper = new IdenifierManagerWrapper(this);
        niceDocumentRead();
    }

    @Override
    protected void onDocumentCreate() {
        // add all document attribute for new document
        super.onDocumentCreate();
        try {
            CTDocument1 ctDocument = getDocument();
            CTDocument1 parse = null;
            String doc = "<xml-fragment xmlns:wpc=\"http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas\" \n"
                    + "    xmlns:mo=\"http://schemas.microsoft.com/office/mac/office/2008/main\" \n"
                    + "    xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" \n"
                    + "    xmlns:mv=\"urn:schemas-microsoft-com:mac:vml\" \n"
                    + "    xmlns:o=\"urn:schemas-microsoft-com:office:office\" \n"
                    + "    xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" \n"
                    + "    xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" \n"
                    + "    xmlns:v=\"urn:schemas-microsoft-com:vml\" \n"
                    + "    xmlns:wp14=\"http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing\" \n"
                    + "    xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" \n"
                    + "    xmlns:w10=\"urn:schemas-microsoft-com:office:word\" \n"
                    + "    xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" \n"
                    + "    xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\" \n"
                    + "    xmlns:wpg=\"http://schemas.microsoft.com/office/word/2010/wordprocessingGroup\" \n"
                    + "    xmlns:wpi=\"http://schemas.microsoft.com/office/word/2010/wordprocessingInk\" \n"
                    + "    xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\" \n"
                    + "    xmlns:wps=\"http://schemas.microsoft.com/office/word/2010/wordprocessingShape\" mc:Ignorable=\"w14 wp14\"><w:body></w:body></xml-fragment>";
            parse = CTDocument1.Factory.parse(doc);
            ctDocument.set(parse);
        } catch (XmlException e) {
            logger.warn("Create new document error: {}", e.getMessage());
        }
    }

    private void niceDocumentRead() throws IOException {
        read(this);
        this.getHeaderList().forEach(header -> read(header));
        this.getFooterList().forEach(header -> read(header));
        // structured document tag
        if (!contentControls.isEmpty()) {
            XmlCursor docCursor = getDocument().newCursor();
            docCursor.selectPath("./*");
            while (docCursor.toNextSelection()) {
                XmlObject o = docCursor.getObject();
                if (o instanceof CTBody) {
                    XmlCursor bodyCursor = o.newCursor();
                    bodyCursor.selectPath("./*");
                    while (bodyCursor.toNextSelection()) {
                        XmlObject bodyObj = bodyCursor.getObject();
                        if (bodyObj instanceof CTSdtBlock) {
                            XWPFStructuredDocumentTag c = new XWPFStructuredDocumentTag((CTSdtBlock) bodyObj, this);
                            bodyElements.add(c);
                            structuredDocumentTags.add(c);
                        }
                    }
                    bodyCursor.dispose();
                }
            }
            docCursor.dispose();
        }
    }

    public List<XWPFPicture> getAllEmbeddedPictures() {
        return Collections.unmodifiableList(allPictures);
    }

    public List<XWPFTable> getAllTables() {
        return Collections.unmodifiableList(allTables);
    }

    public IdenifierManagerWrapper getDocPrIdenifierManager() {
        return idenifierManagerWrapper;
    }

    public BigInteger addNewNumberingId(NumberingFormat numFmt) {
        return addNewMultiLevelNumberingId(numFmt);
    }

    public BigInteger addNewMultiLevelNumberingId(NumberingFormat... numFmts) {
        XWPFNumbering numbering = this.getNumbering();
        if (null == numbering) {
            numbering = this.createNumbering();
        }

        XWPFNum xwpfNum = useExistNum(numbering, numFmts);
        if (null != xwpfNum) return xwpfNum.getCTNum().getNumId();

        XWPFNumberingWrapper numberingWrapper = new XWPFNumberingWrapper(numbering);
        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        // if we have an existing document, we must determine the next
        // free number first.
        cTAbstractNum.setAbstractNumId(numberingWrapper.getNextAbstractNumID());
        // CTMultiLevelType addNewMultiLevelType = cTAbstractNum.addNewMultiLevelType();
        // addNewMultiLevelType.setVal(STMultiLevelType.HYBRID_MULTILEVEL);
        for (int i = 0; i < numFmts.length; i++) {
            NumberingFormat numFmt = numFmts[i];
            CTLvl cTLvl = cTAbstractNum.addNewLvl();
            CTPPrBase ppr = cTLvl.isSetPPr() ? cTLvl.getPPr() : cTLvl.addNewPPr();
            CTInd ind = ppr.isSetInd() ? ppr.getInd() : ppr.addNewInd();
            ind.setLeft(BigInteger.valueOf(UnitUtils.cm2Twips(0.74f) * i));

            Enum fmt = STNumberFormat.Enum.forInt(numFmt.getNumFmt());
            String val = numFmt.getLvlText();
            cTLvl.addNewNumFmt().setVal(fmt);
            cTLvl.addNewLvlText().setVal(val);
            cTLvl.addNewStart().setVal(BigInteger.valueOf(1));
            cTLvl.setIlvl(BigInteger.valueOf(i));
            if (fmt == STNumberFormat.BULLET) {
                cTLvl.addNewLvlJc().setVal(STJc.LEFT);
                CTRPr addNewRPr = cTLvl.addNewRPr();
                CTFonts ctFonts = addNewRPr.addNewRFonts();
                ctFonts.setAscii("Wingdings");
                ctFonts.setHAnsi("Wingdings");
                ctFonts.setHint(STHint.DEFAULT);
            }
        }

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);
        return numbering.addNum(abstractNumID);
    }

    private static XWPFNum useExistNum(XWPFNumbering numbering, NumberingFormat[] numFmts) {
        List<XWPFNum> nums = numbering.getNums();
        for (XWPFNum num : nums) {
            BigInteger abstractNumID = numbering.getAbstractNumID(num.getCTNum().getNumId());
            XWPFAbstractNum abstractNum = numbering.getAbstractNum(abstractNumID);
            CTAbstractNum ctAbstractNum = abstractNum.getCTAbstractNum();
            List<CTLvl> lvlList = ctAbstractNum.getLvlList();
            int size = lvlList.size();
            int length = numFmts.length;
            if (size == length) {
                for (int i = 0; i < size; i++) {
                    CTLvl ctLvl = lvlList.get(i);
                    NumberingFormat numFmt = numFmts[i];
                    if (ctLvl.getNumFmt().getVal() != Enum.forInt(numFmt.getNumFmt())
                        || !ctLvl.getLvlText().getVal().equals(numFmt.getLvlText())) {
                        break;
                    }
                    if (i == size -1) {
                        return num;
                    }

                }
            }
        }
        return null;
    }

    public RelationPart addChartData(XWPFChart chart) throws InvalidFormatException, IOException {
        int chartNumber = getNextPartNumber(XWPFRelation.CHART, charts.size() + 1);

        PackagePart packagePart = chartMappingPart.getOrDefault(chart, chart.getPackagePart());
        // create relationship in document for new chart
        RelationPart rp = createRelationship(XWPFRelation.CHART, new XWPFChartFactory(packagePart), chartNumber, false);

        // initialize xwpfchart object
        XWPFChart xwpfChart = rp.getDocumentPart();
        xwpfChart.setChartIndex(chartNumber);
        // CTChartSpace ctChartSpace = xwpfChart.getCTChartSpace();
        // ctChartSpace.unsetExternalData();
        xwpfChart.setWorkbook(PoitlIOUtils.cloneWorkbook(chart.getWorkbook(), false));

        // add chart object to chart list
        charts.add(xwpfChart);
        chartMappingPart.put(xwpfChart, packagePart);
        return rp;
    }

    public String addEmbeddData(byte[] embeddData, int format) throws InvalidFormatException {
        XWPFRelation relation = 0 == format ? DOCUMENT : XWPFRelation.WORKBOOK;

        int idx = 256 + getRelationIndex(relation);
        POIXMLDocumentPart embeddPart = createRelationship(relation, XWPFFactory.getInstance(), idx);
        embedds.add(embeddPart);
        /* write bytes to new part */
        PackagePart picDataPart = embeddPart.getPackagePart();
        try (OutputStream out = picDataPart.getOutputStream()) {
            out.write(embeddData);
        } catch (IOException e) {
            throw new POIXMLException(e);
        }
        return getRelationId(embeddPart);
    }

    public String addEmbeddData(byte[] embeddData, String contentType, String relType, String part)
            throws InvalidFormatException {
        PackagePartName partName = PackagingURIHelper.createPartName(part);
        PackagePart packagePart = getPackage().createPart(partName, contentType);

        try (OutputStream out = packagePart.getOutputStream()) {
            out.write(embeddData);
        } catch (IOException e) {
            throw new POIXMLException(e);
        }
        PackageRelationship ole = getPackagePart().addRelationship(partName, TargetMode.INTERNAL, relType);
        return ole.getId();
    }

    private int getRelationIndex(XWPFRelation relation) {
        int i = 1;
        for (RelationPart rp : getRelationParts()) {
            if (rp.getRelationship().getRelationshipType().equals(relation.getRelation())) {
                i++;
            }
        }
        return i;
    }

    @Override
    protected void commit() throws IOException {
        saveEmbedds();
        super.commit();
    }

    private void saveEmbedds() {
        embedds.forEach(part -> part.setCommitted(true));
    }

    public NiceXWPFDocument generate() throws IOException {
        return generate(false);
    }

    public NiceXWPFDocument generate(boolean adjust) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.write(byteArrayOutputStream);
        this.close();
        return new NiceXWPFDocument(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), adjust);
    }

    public NiceXWPFDocument merge(NiceXWPFDocument docMerge) throws Exception {
        return merge(Arrays.asList(docMerge), createParagraph().createRun());
    }

    public NiceXWPFDocument merge(List<NiceXWPFDocument> docMerges, XWPFRun run) throws Exception {
        if (null == docMerges || docMerges.isEmpty() || null == run) return this;
        return merge(docMerges.iterator(), run);
    }

    public NiceXWPFDocument merge(Iterator<NiceXWPFDocument> iterator, XWPFRun run) throws Exception {
        XWPFRun newRun = run;
        String paragraphText = ParagraphUtils.trimLine((XWPFParagraph) run.getParent());
        boolean havePictures = ParagraphUtils.havePictures((XWPFParagraph) run.getParent());
        if (!ParagraphUtils.trimLine(run.text()).equals(paragraphText) || havePictures) {
            BodyContainer container = BodyContainerFactory.getBodyContainer(run);
            XWPFParagraph paragraph = container.insertNewParagraph(run);
            newRun = paragraph.createRun();
        }
        return new XmlXWPFDocumentMerge().merge(this, iterator, newRun);
    }

    @Override
    public XWPFParagraph insertNewParagraph(XmlCursor cursor) {
        if (isCursorInBody(cursor)) {
            String uri = CTP.type.getName().getNamespaceURI();
            /*
             * TODO DO not use a coded constant, find the constant in the OOXML
             * classes instead, as the child of type CT_Paragraph is defined in the
             * OOXML schema as 'p'
             */
            String localPart = "p";
            // creates a new Paragraph, cursor is positioned inside the new
            // element
            cursor.beginElement(localPart, uri);
            // move the cursor to the START token to the paragraph just created
            cursor.toParent();
            CTP p = (CTP) cursor.getObject();
            XWPFParagraph newP = new XWPFParagraph(p, this);
            XmlObject o = null;
            /*
             * move the cursor to the previous element until a) the next
             * paragraph is found or b) all elements have been passed
             */
            while (!(o instanceof CTP) && (cursor.toPrevSibling())) {
                o = cursor.getObject();
            }
            /*
             * if the object that has been found is a) not a paragraph or b) is
             * the paragraph that has just been inserted, as the cursor in the
             * while loop above was not moved as there were no other siblings,
             * then the paragraph that was just inserted is the first paragraph
             * in the body. Otherwise, take the previous paragraph and calculate
             * the new index for the new paragraph.
             */
            if ((!(o instanceof CTP)) || o == p) {
                paragraphs.add(0, newP);
            } else {
                int pos = paragraphs.indexOf(getParagraph((CTP) o)) + 1;
                paragraphs.add(pos, newP);
            }

            /*
             * create a new cursor, that points to the START token of the just
             * inserted paragraph
             */
            XmlCursor newParaPos = p.newCursor();
            try {
                /*
                 * Calculate the paragraphs index in the list of all body
                 * elements
                 */
                int i = 0;
                cursor.toCursor(newParaPos);
                while (cursor.toPrevSibling()) {
                    o = cursor.getObject();
                    if (o instanceof CTP || o instanceof CTTbl || o instanceof CTSdtBlock) {
                        i++;
                    }
                }
                bodyElements.add(i, newP);
                cursor.toCursor(newParaPos);
                cursor.toEndToken();
                return newP;
            } finally {
                newParaPos.dispose();
            }
        }
        return null;
    }

    @Override
    public XWPFTable insertNewTbl(XmlCursor cursor) {
        if (isCursorInBody(cursor)) {
            String uri = CTTbl.type.getName().getNamespaceURI();
            String localPart = "tbl";
            cursor.beginElement(localPart, uri);
            cursor.toParent();
            CTTbl t = (CTTbl) cursor.getObject();
            XWPFTable newT = new XWPFTable(t, this);
            XmlObject o = null;
            while (!(o instanceof CTTbl) && (cursor.toPrevSibling())) {
                o = cursor.getObject();
            }
            if (!(o instanceof CTTbl)) {
                tables.add(0, newT);
            } else {
                int pos = tables.indexOf(getTable((CTTbl) o)) + 1;
                tables.add(pos, newT);
            }
            int i = 0;
            XmlCursor tableCursor = t.newCursor();
            try {
                cursor.toCursor(tableCursor);
                while (cursor.toPrevSibling()) {
                    o = cursor.getObject();
                    if (o instanceof CTP || o instanceof CTTbl || o instanceof CTSdtBlock) {
                        i++;
                    }
                }
                bodyElements.add(i, newT);
                cursor.toCursor(tableCursor);
                cursor.toEndToken();
                return newT;
            } finally {
                tableCursor.dispose();
            }
        }
        return null;
    }

    /**
     * verifies that cursor is on the right position
     */
    private boolean isCursorInBody(XmlCursor cursor) {
        XmlCursor verify = cursor.newCursor();
        verify.toParent();
        boolean result = (verify.getObject() == this.getDocument().getBody());
        verify.dispose();
        return result;
    }

    private void read(IBody body) {
        readParagraphs(body.getParagraphs());
        readTables(body.getTables());
    }

    private void readParagraphs(List<XWPFParagraph> paragraphs) {
        paragraphs.forEach(paragraph -> paragraph.getRuns().forEach(run -> readRun(run)));
    }

    private void readRun(XWPFRun run) {
        allPictures.addAll(run.getEmbeddedPictures());
        // compatible for unique identifier: issue#361 #225
        // mc:AlternateContent/mc:Choice/w:drawing
        if (!this.idenifierManagerWrapper.isValid()) return;
        CTR r = run.getCTR();
        XmlObject[] xmlObjects = r.selectPath(IdenifierManagerWrapper.XPATH_DRAWING);
        if (null == xmlObjects || xmlObjects.length <= 0) return;
        for (XmlObject xmlObject : xmlObjects) {
            try {
                CTDrawing ctDrawing = CTDrawing.Factory.parse(xmlObject.xmlText());
                for (CTAnchor anchor : ctDrawing.getAnchorList()) {
                    if (anchor.getDocPr() != null) {
                        long id = anchor.getDocPr().getId();
                        long reserve = this.idenifierManagerWrapper.reserve(id);
                        if (adjustDoc && id != reserve) {
                            anchor.getDocPr().setId(reserve);
                            xmlObject.set(ctDrawing);
                        }
                    }
                }
                for (CTInline inline : ctDrawing.getInlineList()) {
                    if (inline.getDocPr() != null) {
                        long id = inline.getDocPr().getId();
                        long reserve = this.idenifierManagerWrapper.reserve(id);
                        if (adjustDoc && id != reserve) {
                            inline.getDocPr().setId(reserve);
                            xmlObject.set(ctDrawing);
                        }
                    }
                }
            } catch (XmlException e) {
                // no-op
            }
        }
    }

    private void readTables(List<XWPFTable> tables) {
        allTables.addAll(tables);
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rows = table.getRows();
            if (null == rows) continue;
            ;
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();
                if (null == cells) continue;
                for (XWPFTableCell cell : cells) {
                    read(cell);
                }
            }
        }
    }

    @Override
    public boolean removeBodyElement(int pos) {
        if (pos >= 0 && pos < this.bodyElements.size()) {
            BodyElementType type = ((IBodyElement) this.bodyElements.get(pos)).getElementType();
            int paraPos;
            if (type == BodyElementType.TABLE) {
                paraPos = this.getTablePos(pos);
                this.tables.remove(paraPos);
                this.getDocument().getBody().removeTbl(paraPos);
            }
            if (type == BodyElementType.PARAGRAPH) {
                paraPos = this.getParagraphPos(pos);
                this.paragraphs.remove(paraPos);
                this.getDocument().getBody().removeP(paraPos);
            }
            if (type == BodyElementType.CONTENTCONTROL) {
                paraPos = getBodyElementSpecificPos(pos, contentControls);
                this.contentControls.remove(paraPos);
                this.getDocument().getBody().removeSdt(paraPos);
            }
            this.bodyElements.remove(pos);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Finds that for example the 2nd entry in the body list is the 1st paragraph
     */
    private int getBodyElementSpecificPos(int pos, List<? extends IBodyElement> list) {
        // If there's nothing to find, skip it
        if (list.isEmpty()) {
            return -1;
        }
        if (pos >= 0 && pos < bodyElements.size()) {
            // Ensure the type is correct
            IBodyElement needle = bodyElements.get(pos);
            if (needle.getElementType() != list.get(0).getElementType()) {
                // Wrong type
                return -1;
            }

            // Work back until we find it
            int startPos = Math.min(pos, list.size() - 1);
            for (int i = startPos; i >= 0; i--) {
                if (list.get(i) == needle) {
                    return i;
                }
            }
        }
        // Couldn't be found
        return -1;
    }
}
