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
package com.deepoove.poi.xwpf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChartSpace;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.util.ParagraphUtils;
import com.deepoove.poi.util.UnitUtils;

/**
 * 
 * @author Sayi
 * @version 0.0.1
 *
 */
public class NiceXWPFDocument extends XWPFDocument {

    private static Logger logger = LoggerFactory.getLogger(NiceXWPFDocument.class);

    protected List<XWPFTable> allTables = new ArrayList<XWPFTable>();
    protected List<XWPFPicture> allPictures = new ArrayList<XWPFPicture>();
    protected IdenifierManagerWrapper idenifierManagerWrapper;
    protected boolean adjustDoc = false;

    protected Map<XWPFChart, PackagePart> chartMappingPart = new HashMap<>();

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
        myDocumentRead();
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

    private void myDocumentRead() {
        initAllElement(this);
        this.getHeaderList().forEach(header -> initAllElement(header));
        this.getFooterList().forEach(header -> initAllElement(header));
    }

    private void initAllElement(IBody body) {
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
                    initAllElement(cell);
                }
            }
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

        NumberingWrapper numberingWrapper = new NumberingWrapper(numbering);
        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        // if we have an existing document, we must determine the next
        // free number first.
        cTAbstractNum.setAbstractNumId(numberingWrapper.getNextAbstractNumID());
        // CTMultiLevelType.setVal(STMultiLevelType.HYBRID_MULTILEVEL);
        for (int i = 0; i < numFmts.length; i++) {
            NumberingFormat numFmt = numFmts[i];
            CTLvl cTLvl = cTAbstractNum.addNewLvl();
            CTPPr ppr = cTLvl.isSetPPr() ? cTLvl.getPPr() : cTLvl.addNewPPr();
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
            }
        }

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);
        return numbering.addNum(abstractNumID);
    }

    public RelationPart addChartData(XWPFChart chart) throws InvalidFormatException, IOException {
        int chartNumber = getNextPartNumber(XWPFRelation.CHART, charts.size() + 1);

        // create relationship in document for new chart
        RelationPart rp = createRelationship(XWPFRelation.CHART,
                new XWPFChartFactory(chartMappingPart.getOrDefault(chart, chart.getPackagePart())), chartNumber, false);

        // initialize xwpfchart object
        XWPFChart xwpfChart = rp.getDocumentPart();
        xwpfChart.setChartIndex(chartNumber);
        CTChartSpace ctChartSpace = xwpfChart.getCTChartSpace();
        ctChartSpace.unsetExternalData();
        xwpfChart.setWorkbook(chart.getWorkbook());

        // add chart object to chart list
        charts.add(xwpfChart);
        chartMappingPart.put(xwpfChart, chart.getPackagePart());
        return rp;
    }

    public NiceXWPFDocument generate() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.write(byteArrayOutputStream);
        this.close();
        return new NiceXWPFDocument(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
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

}
