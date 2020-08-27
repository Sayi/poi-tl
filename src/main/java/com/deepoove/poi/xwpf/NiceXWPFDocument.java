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
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.PackageRelationshipCollection;
import org.apache.poi.openxml4j.opc.PackageRelationshipTypes;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChartSpace;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.data.NumberingFormat;

/**
 * 对原生poi的扩展
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
            String doc = "<xml-fragment xmlns:wpc=\"http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas\" \n" + 
                    "    xmlns:mo=\"http://schemas.microsoft.com/office/mac/office/2008/main\" \n" + 
                    "    xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" \n" + 
                    "    xmlns:mv=\"urn:schemas-microsoft-com:mac:vml\" \n" + 
                    "    xmlns:o=\"urn:schemas-microsoft-com:office:office\" \n" + 
                    "    xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" \n" + 
                    "    xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" \n" + 
                    "    xmlns:v=\"urn:schemas-microsoft-com:vml\" \n" + 
                    "    xmlns:wp14=\"http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing\" \n" + 
                    "    xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" \n" + 
                    "    xmlns:w10=\"urn:schemas-microsoft-com:office:word\" \n" + 
                    "    xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" \n" + 
                    "    xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\" \n" + 
                    "    xmlns:wpg=\"http://schemas.microsoft.com/office/word/2010/wordprocessingGroup\" \n" + 
                    "    xmlns:wpi=\"http://schemas.microsoft.com/office/word/2010/wordprocessingInk\" \n" + 
                    "    xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\" \n" + 
                    "    xmlns:wps=\"http://schemas.microsoft.com/office/word/2010/wordprocessingShape\" mc:Ignorable=\"w14 wp14\"><w:body></w:body></xml-fragment>";
            parse = CTDocument1.Factory.parse(doc);
            ctDocument.set(parse);
        } catch (XmlException e) {
            logger.warn("Create new document error and merge docx may produce bug: {}", e.getMessage());
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
        paragraphs.forEach(
                paragraph -> paragraph.getRuns().forEach(run -> readRun(run)));
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
            if (null == rows) continue;;
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
        XWPFNumbering numbering = this.getNumbering();
        if (null == numbering) {
            numbering = this.createNumbering();
        }

        NumberingWrapper numberingWrapper = new NumberingWrapper(numbering);
        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        // if we have an existing document, we must determine the next
        // free number first.
        cTAbstractNum
                .setAbstractNumId(numberingWrapper.getNextAbstractNumID());

        Enum fmt = numFmt.getNumFmt();
        String val = numFmt.getLvlText();
        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.addNewNumFmt().setVal(fmt);
        cTLvl.addNewLvlText().setVal(val);
        cTLvl.addNewStart().setVal(BigInteger.valueOf(1));
        cTLvl.setIlvl(BigInteger.valueOf(0));
        if (fmt == STNumberFormat.BULLET) {
            cTLvl.addNewLvlJc().setVal(STJc.LEFT);
        } else {
            // cTLvl.setIlvl(BigInteger.valueOf(0));
        }

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);

        return numbering.addNum(abstractNumID);
    }

    public RelationPart addChartData(XWPFChart chart) throws InvalidFormatException, IOException {
        int chartNumber = getNextPartNumber(XWPFRelation.CHART, charts.size() + 1);

        //create relationship in document for new chart
        RelationPart rp = createRelationship(
                XWPFRelation.CHART, new XWPFChartFactory(chart.getPackagePart()), chartNumber, false);

        // initialize xwpfchart object
        XWPFChart xwpfChart = rp.getDocumentPart();
        xwpfChart.setChartIndex(chartNumber);
        CTChartSpace ctChartSpace = xwpfChart.getCTChartSpace();
        ctChartSpace.unsetExternalData();
        xwpfChart.setWorkbook(chart.getWorkbook());

        //add chart object to chart list
        charts.add(xwpfChart);
        return rp;
    }

    public NiceXWPFDocument generate() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.write(byteArrayOutputStream);
        this.close();
        return new NiceXWPFDocument(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }

    public NiceXWPFDocument generateWithAdjust() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.write(byteArrayOutputStream);
        this.close();
        return new NiceXWPFDocument(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), true);
    }

    /**
     * 文档末尾合并另一个Word文档
     * 
     * @param docMerge
     *            待合并文档
     * @return 合并后的文档
     * @throws Exception
     * @since 1.3.0
     */
    public NiceXWPFDocument merge(NiceXWPFDocument docMerge) throws Exception {
        return merge(Arrays.asList(docMerge), createParagraph().createRun());
    }

    /**
     * 指定位置合并Word文档
     * 
     * @param docMerges
     *            待合并的文档
     * @param run
     *            合并的位置
     * @return 合并后的文档
     * @throws Exception
     * @since 1.3.0
     */
    public NiceXWPFDocument merge(List<NiceXWPFDocument> docMerges, XWPFRun run) throws Exception {
        if (null == docMerges || docMerges.isEmpty() || null == run) return this;
        return merge(docMerges.iterator(), run);
    }


    public NiceXWPFDocument merge(Iterator<NiceXWPFDocument> iterator, XWPFRun run) throws Exception {
        if (null == iterator || !iterator.hasNext() || null == run) return this;
        // XWPFParagraph paragraph = insertNewParagraph(run);
        XWPFParagraph paragraph = (XWPFParagraph) run.getParent();
        CTP ctp = paragraph.getCTP();
        CTBody body = this.getDocument().getBody();

        List<String> addParts = convertStr(iterator);
        iterator = null;
        
        XmlOptions optionsInner = DefaultXmlOptions.OPTIONS_INNER;
        String srcString = body.xmlText(optionsInner );
        //hack for create document or single element document
        if (!srcString.startsWith("<xml-fragment")) {
            body.addNewSectPr();
            srcString = body.xmlText(optionsInner);
        }
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String sufix = srcString.substring(srcString.lastIndexOf("<"));

        CTP makeBody = CTP.Factory.parse(prefix + StringUtils.join(addParts, "") + sufix);
        ctp.set(makeBody);
        
        String xmlText = body.xmlText(optionsInner);
        xmlText = xmlText.replaceAll("<w:p><w:p>", "<w:p>").replaceAll("<w:p><w:p\\s", "<w:p ")
                .replaceAll("<w:p><w:tbl>", "<w:tbl>").replaceAll("<w:p><w:tbl\\s", "<w:tbl ");
        
        xmlText = xmlText.replaceAll("</w:sectPr></w:p>", "</w:sectPr>")
                .replaceAll("</w:p></w:p>", "</w:p>").replaceAll("</w:tbl></w:p>", "</w:tbl>")
                .replaceAll("<w:p(\\s[A-Za-z0-9:\\s=\"]*)?/></w:p>", "")
                .replaceAll("</w:p><w:bookmarkEnd(\\s[A-Za-z0-9:\\s=\"]*)?/></w:p>", "</w:p>");
        
        // System.out.println(xmlText);
        body.set(CTBody.Factory.parse(xmlText));
        
        return generateWithAdjust();
    }

    private List<String> convertStr(Iterator<NiceXWPFDocument> iterator)
            throws InvalidFormatException, IOException {
        List<String> strList = new ArrayList<String>();

        // cache the merge doc style if docs have same style, or should merge style first
        NiceXWPFDocument next = iterator.next();
        Map<String, String> styleMapCache = mergeStyles(next);
        mergeNamespaces(next);
        do {
            strList.add(extractMergePart(next, styleMapCache));
            if (iterator.hasNext()) next = iterator.next();
            else break;
        } while(true);
        return strList;
    }

    private void mergeNamespaces(NiceXWPFDocument docMerge) {
        CTDocument1 document = this.getDocument();
        XmlCursor newCursor = document.newCursor();
        if (toStartCursor(newCursor)) {
            CTDocument1 documentMerge = docMerge.getDocument();
            XmlCursor mergeCursor = documentMerge.newCursor();
            if (toStartCursor(mergeCursor)) {
                Map<String, String> addToThis = new HashMap<>();
                mergeCursor.getAllNamespaces(addToThis);
                addToThis.forEach(newCursor::insertNamespace);
            }
            mergeCursor.dispose();
        }
        newCursor.dispose();
    }

    private boolean toStartCursor(XmlCursor newCursor) {
        do {
            if (newCursor.currentTokenType().isStart()) {
                return true;
            } else if (newCursor.hasNextToken()) { 
                newCursor.toNextToken();
            } else return false;
        } while (true);
    }

    private String extractMergePart(NiceXWPFDocument docMerge, Map<String, String> styleIdsMap) throws InvalidFormatException, IOException {
        CTBody bodyMerge = docMerge.getDocument().getBody();
        // Map<String, String> styleIdsMap = mergeStyles(docMerge);
        Map<BigInteger, BigInteger> numIdsMap = mergeNumbering(docMerge);
        Map<String, String> blipIdsMap = mergePicture(docMerge);
        Map<String, String> hyperlinkMap = mergeHyperlink(docMerge);
        Map<String, String> chartIdsMap = mergeChart(docMerge);

        String appendString = bodyMerge.xmlText(DefaultXmlOptions.OPTIONS_OUTER);
        String addPart = ridSectPr(appendString);

        for (String styleId : styleIdsMap.keySet()) {
            addPart = addPart
                    .replaceAll("<w:pStyle\\sw:val=\"" + styleId + "\"",
                            "<w:pStyle w:val=\"" + styleIdsMap.get(styleId) + "\"")
                    .replaceAll("<w:tblStyle\\sw:val=\"" + styleId + "\"",
                            "<w:tblStyle w:val=\"" + styleIdsMap.get(styleId) + "\"")
                    .replaceAll("<w:rStyle\\sw:val=\"" + styleId + "\"",
                            "<w:rStyle w:val=\"" + styleIdsMap.get(styleId) + "\"");
        }
        
        // 图片旧的id和新的id可能有交集
        Map<String, String> placeHolderblipIdsMap = new HashMap<String, String>();
        for (String relaId : blipIdsMap.keySet()) {
            placeHolderblipIdsMap.put(relaId, blipIdsMap.get(relaId) + "@PoiTL@");
        }
        for (String relaId : placeHolderblipIdsMap.keySet()) {
            addPart = addPart.replaceAll("r:embed=\"" + relaId + "\"",
                    "r:embed=\"" + placeHolderblipIdsMap.get(relaId) + "\"");
            // w:pict v:shape v:imagedata
            addPart = addPart.replaceAll("r:id=\"" + relaId + "\"",
                    "r:id=\"" + placeHolderblipIdsMap.get(relaId) + "\"");
        }
        // 超链接
        for (String relaId : hyperlinkMap.keySet()) {
            hyperlinkMap.put(relaId, hyperlinkMap.get(relaId) + "@PoiTL@");
        }
        for (String relaId : hyperlinkMap.keySet()) {
            // w:hyperlink r:id
            addPart = addPart.replaceAll("r:id=\"" + relaId + "\"",
                    "r:id=\"" + hyperlinkMap.get(relaId) + "\"");
        }
        
        // 图表
        for (String relaId : chartIdsMap.keySet()) {
            chartIdsMap.put(relaId, chartIdsMap.get(relaId) + "@PoiTL@");
        }
        for (String relaId : chartIdsMap.keySet()) {
            addPart = addPart.replaceAll("r:id=\"" + relaId + "\"",
                    "r:id=\"" + chartIdsMap.get(relaId) + "\"");
        }
        
        // 列表numId
        Map<BigInteger, String> numIdsStrMap = new HashMap<BigInteger, String>();
        for (BigInteger relaId : numIdsMap.keySet()) {
            numIdsStrMap.put(relaId, numIdsMap.get(relaId) + "@PoiTL@");
        }
        for (BigInteger numId : numIdsStrMap.keySet()) {
            addPart = addPart.replaceAll("<w:numId\\sw:val=\"" + numId + "\"",
                    "<w:numId w:val=\"" + numIdsStrMap.get(numId) + "\"");
        }
        
        addPart = addPart.replaceAll("@PoiTL@", "");
        // 关闭合并流
        try {
            docMerge.close();
        } catch (Exception e) {}
        return addPart;
    }

    private String ridSectPr(String appendString) {
        int lastIndexOf = appendString.lastIndexOf("<w:sectPr");
        String addPart = "";
        int begin = appendString.indexOf(">") + 1;
        int end = appendString.lastIndexOf("<");
        if (-1 != lastIndexOf) {
            String prefix = appendString.substring(begin, appendString.lastIndexOf("<w:sectPr"));
            String sufix = appendString.substring(appendString.lastIndexOf("</w:sectPr>") + 11,
                    end);
            return prefix + sufix;
        } else if (begin < end) {
            addPart = appendString.substring(begin, end);
        }
        return addPart;
    }

    private Map<String, String> mergePicture(NiceXWPFDocument docMerge)
            throws InvalidFormatException {
        Map<String, String> blipIdsMap = new HashMap<String, String>();
        List<XWPFPictureData> allPictures = docMerge.getAllPictures();
        for (XWPFPictureData xwpfPictureData : allPictures) {
            String relationId = docMerge.getRelationId(xwpfPictureData);
            String blidId = this.addPictureData(xwpfPictureData.getData(),
                    xwpfPictureData.getPictureType());
            blipIdsMap.put(relationId, blidId);
        }
        return blipIdsMap;
    }

    private Map<BigInteger, BigInteger> mergeNumbering(NiceXWPFDocument docMerge) {
        Map<BigInteger, BigInteger> numIdsMap = new HashMap<BigInteger, BigInteger>();
        XWPFNumbering numberingMerge = docMerge.getNumbering();
        if (null == numberingMerge) return numIdsMap;
        NumberingWrapper wrapperMerge = new NumberingWrapper(numberingMerge);
        List<XWPFNum> nums = wrapperMerge.getNums();
        if (null == nums) return numIdsMap;

        XWPFNumbering numbering = this.getNumbering();
        if (null == numbering) numbering = this.createNumbering();
        NumberingWrapper wrapper = new NumberingWrapper(numbering);

        XWPFAbstractNum xwpfAbstractNum;
        CTAbstractNum cTAbstractNum;
        Map<BigInteger, CTAbstractNum> cache = new HashMap<BigInteger, CTAbstractNum>();
        Map<BigInteger, CTAbstractNum> ret = new HashMap<BigInteger, CTAbstractNum>();
        for (XWPFNum xwpfNum : nums) {
            BigInteger mergeNumId = xwpfNum.getCTNum().getNumId();

            cTAbstractNum = cache.get(xwpfNum.getCTNum().getAbstractNumId().getVal());
            if (null == cTAbstractNum) {
                xwpfAbstractNum = numberingMerge
                        .getAbstractNum(xwpfNum.getCTNum().getAbstractNumId().getVal());
                if (null == xwpfAbstractNum) {
                    logger.warn("cannot find cTAbstractNum by XWPFNum.");
                    continue;
                }
                cTAbstractNum = xwpfAbstractNum.getCTAbstractNum();
                // cTAbstractNum.setAbstractNumId(wrapper.getNextAbstractNumID());
                if (cTAbstractNum.isSetNsid()) cTAbstractNum.unsetNsid();
                if (cTAbstractNum.isSetTmpl()) cTAbstractNum.unsetTmpl();
                cache.put(xwpfNum.getCTNum().getAbstractNumId().getVal(), cTAbstractNum);
            }
            ret.put(mergeNumId, cTAbstractNum);
        }
        
        new HashSet<CTAbstractNum>(ret.values()).forEach(abnum -> abnum.setAbstractNumId(wrapper.getNextAbstractNumID()));
        final XWPFNumbering finalNumbering = numbering;
        ret.forEach((mergeNumId, abnum) -> {
            BigInteger numID = finalNumbering.addNum(finalNumbering.addAbstractNum(new XWPFAbstractNum(abnum)));
            numIdsMap.put(mergeNumId, numID);
        });

        return numIdsMap;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> mergeStyles(NiceXWPFDocument docMerge) {
        Map<String, String> styleIdsMap = new HashMap<String, String>();
        XWPFStyles styles = this.getStyles();
        if (null == styles) styles = createStyles();
        XWPFStyles stylesMerge = docMerge.getStyles();
        if (null == stylesMerge) return styleIdsMap;
        try {
            Field listStyleField = XWPFStyles.class.getDeclaredField("listStyle");
            listStyleField.setAccessible(true);
            List<XWPFStyle> lists = (List<XWPFStyle>) listStyleField.get(stylesMerge);
            for (XWPFStyle xwpfStyle : lists) {
                if (styles.styleExist(xwpfStyle.getStyleId())) {
                    String id = xwpfStyle.getStyleId();
                    xwpfStyle.setStyleId(UUID.randomUUID().toString());
                    styleIdsMap.put(id, xwpfStyle.getStyleId());
                }
                styles.addStyle(xwpfStyle);
            }
        } catch (Exception e) {
            // throw exception?
            logger.error("merge style error", e);
        }
        return styleIdsMap;
    }

    private Map<String, String> mergeHyperlink(NiceXWPFDocument docMerge) throws InvalidFormatException {
        Map<String, String> map = new HashMap<String, String>();
        PackageRelationshipCollection hyperlinks = docMerge.getPackagePart().getRelationshipsByType(PackageRelationshipTypes.HYPERLINK_PART);
        Iterator<PackageRelationship> iterator = hyperlinks.iterator();
        while (iterator.hasNext()) {
            PackageRelationship relationship = iterator.next();
            PackageRelationship relationshipNew = getPackagePart()
                    .addExternalRelationship(relationship.getTargetURI().toString(), XWPFRelation.HYPERLINK.getRelation());
            map.put(relationship.getId(), relationshipNew.getId());
        }
        return map;
    }
    
    private Map<String, String> mergeChart(NiceXWPFDocument docMerge) throws InvalidFormatException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        // TODO
        List<XWPFChart> charts = docMerge.getCharts();
        for (XWPFChart chart : charts) {
            String relationId = docMerge.getRelationId(chart);
            RelationPart addChartData = addChartData(chart);
            map.put(relationId, addChartData.getRelationship().getId());
        }
        return map;
    }

    public int getPosOfParagraphCTP(CTP bodyObj) {
        IBodyElement current;
        for (int i = 0; i < bodyElements.size(); i++) {
            current = bodyElements.get(i);
            if (current.getElementType() == BodyElementType.PARAGRAPH) {
                if (((XWPFParagraph)current).getCTP().equals(bodyObj)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getPosOfTableCTTbl(CTTbl bodyObj) {
        IBodyElement current;
        for (int i = 0; i < bodyElements.size(); i++) {
            current = bodyElements.get(i);
            if (current.getElementType() == BodyElementType.TABLE) {
                if (((XWPFTable)current).getCTTbl().equals(bodyObj)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int getParaPos(XWPFParagraph insertNewParagraph) {
        for (int i = 0; i < paragraphs.size(); i++) {
            if (paragraphs.get(i) == insertNewParagraph) {
                return i;
            }
        }
        return -1;
    }

    public int getTablePos(XWPFTable insertNewTbl) {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i) == insertNewTbl) {
                return i;
            }
        }
        return -1;
    }

    public void updateBodyElements(IBodyElement insertNewParagraph, IBodyElement copy) {
        int pos = -1;
        for (int i = 0; i < bodyElements.size(); i++) {
            if (bodyElements.get(i) == insertNewParagraph) {
                pos =  i;
            }
        }
        if (-1 != pos) bodyElements.set(pos, copy);
        
    }

}
