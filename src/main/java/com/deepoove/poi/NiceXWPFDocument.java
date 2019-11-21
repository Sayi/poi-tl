/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.PackageRelationshipCollection;
import org.apache.poi.openxml4j.opc.PackageRelationshipTypes;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.util.TableTools;

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

    public NiceXWPFDocument() {
        super();
    }

    public NiceXWPFDocument(InputStream in) throws IOException {
        super(in);
        buildAllTables();
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

    private void buildAllTables() {
        List<XWPFTable> tables = this.getTables();
        if (null == tables) return;
        else allTables.addAll(tables);

        List<XWPFTableRow> rows = null;
        List<XWPFTableCell> cells = null;
        List<XWPFTable> cellTables = null;
        for (XWPFTable table : tables) {
            rows = table.getRows();
            if (null == rows) continue;
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                if (null == cells) continue;
                for (XWPFTableCell cell : cells) {
                    cellTables = cell.getTables();
                    if (null != cellTables) allTables.addAll(cellTables);
                }
            }
        }
    }

    public XWPFTable getTableByCTTbl(CTTbl ctTbl) {
        for (int i = 0; i < allTables.size(); i++) {
            if (allTables.get(i).getCTTbl() == ctTbl) { return allTables.get(i); }
        }
        return null;
    }

    /**
     * 在某个段落起始处插入表格
     * 
     * @param run
     * @param row
     * @param col
     * @return
     */
    public XWPFTable insertNewTable(XWPFRun run, int row, int col) {
        XmlCursor cursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();

        // XmlCursor cursor = run.getCTR().newCursor();
        if (isCursorInBody(cursor)) {
            String uri = CTTbl.type.getName().getNamespaceURI();
            String localPart = "tbl";
            cursor.beginElement(localPart, uri);
            cursor.toParent();

            CTTbl t = (CTTbl) cursor.getObject();
            XWPFTable newT = new XWPFTable(t, this, row, col);
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
                    if (o instanceof CTP || o instanceof CTTbl) {
                        i++;
                    }
                }
                bodyElements.add(i > bodyElements.size() ? bodyElements.size() : i, newT);
                // bodyElements.add(i, newT);
                cursor.toCursor(tableCursor);
                cursor.toEndToken();
                return newT;
            }
            finally {
                tableCursor.dispose();
            }
        }
        return null;
    }

    /**
     * 设置表格的宽度
     * 
     * @param table
     * @param width
     *            单位cm
     * @param rows
     * @param cols
     */
    public void widthTable(XWPFTable table, float widthCM, int rows, int cols) {
        TableTools.widthTable(table, widthCM, cols);
        TableTools.borderTable(table, 4);
    }

    /**
     * 在某个段落起始处插入段落
     * 
     * @param run
     * @return
     */
    public XWPFParagraph insertNewParagraph(XWPFRun run) {
        // XmlCursor cursor = run.getCTR().newCursor();
        XmlCursor cursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
        if (isCursorInBody(cursor)) {
            String uri = CTP.type.getName().getNamespaceURI();
            /*
             * TODO DO not use a coded constant, find the constant in the OOXML
             * classes instead, as the child of type CT_Paragraph is defined in
             * the OOXML schema as 'p'
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
            if ((!(o instanceof CTP)) || (CTP) o == p) {
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
                    if (o instanceof CTP || o instanceof CTTbl) i++;
                }
                bodyElements.add(i > bodyElements.size() ? bodyElements.size() : i, newP);
                cursor.toCursor(newParaPos);
                cursor.toEndToken();
                return newP;
            }
            finally {
                newParaPos.dispose();
            }
        }
        return null;
    }

    private boolean isCursorInBody(XmlCursor cursor) {
        XmlCursor verify = cursor.newCursor();
        verify.toParent();
        try {
            return true;// (verify.getObject() == this.getDocument().getBody());
        }
        finally {
            verify.dispose();
        }
    }
    
    public static XWPFRun insertNewHyperLinkRun(XWPFRun run, String link) {
        XWPFParagraphWrapper paragraph = new XWPFParagraphWrapper((XWPFParagraph) run.getParent());
        int pos = -1;
        List<XWPFRun> runs = ((XWPFParagraph) run.getParent()).getRuns();
        for (int i = 0; i < runs.size(); i++) {
            if (run == runs.get(i)) {
                pos = i;
                break;
            }
        }
        XWPFRun hyperLinkRun = paragraph.insertNewHyperLinkRun(pos, link);
        StyleUtils.styleRun(hyperLinkRun, run);
        return hyperLinkRun;
    }

    public BigInteger addNewNumbericId(Pair<Enum, String> numFmt) {
        XWPFNumbering numbering = this.getNumbering();
        if (null == numbering) {
            numbering = this.createNumbering();
        }

        NumberingWrapper numberingWrapper = new NumberingWrapper(numbering);
        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        // if we have an existing document, we must determine the next
        // free number first.
        cTAbstractNum
                .setAbstractNumId(BigInteger.valueOf(numberingWrapper.getAbstractNumsSize() + 10));

        Enum fmt = numFmt.getLeft();
        String val = numFmt.getRight();
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

    /**
     * 生成一个新的流文档
     * 
     * @return
     * @throws IOException
     * @since 1.3.0
     */
    public NiceXWPFDocument generate() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.write(byteArrayOutputStream);
        this.close();
        return new NiceXWPFDocument(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
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
        // XWPFParagraph paragraph = insertNewParagraph(run);
        XWPFParagraph paragraph = (XWPFParagraph) run.getParent();
        CTP ctp = paragraph.getCTP();

        CTBody body = this.getDocument().getBody();
        String srcString = body.xmlText();
        //hack for create document or single element document
        if (!srcString.startsWith("<xml-fragment")) {
            body.addNewSectPr();
            srcString = body.xmlText();
        }
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String sufix = srcString.substring(srcString.lastIndexOf("<"));
        List<String> addParts = new ArrayList<String>();
        // cache the merge doc style if docs have same style, or should merge style first
        Map<String, String> styleMapCache = mergeStyles(docMerges.get(0));
        for (NiceXWPFDocument docMerge : docMerges) {
            addParts.add(extractMergePart(docMerge, styleMapCache));
        }

        CTP makeBody = CTP.Factory.parse(prefix + StringUtils.join(addParts, "") + sufix);
        ctp.set(makeBody);

        String xmlText = body.xmlText();
        xmlText = xmlText.replaceAll("<w:p><w:p>", "<w:p>").replaceAll("<w:p><w:p\\s", "<w:p ")
                .replaceAll("<w:p><w:tbl>", "<w:tbl>").replaceAll("<w:p><w:tbl\\s", "<w:tbl ");

        xmlText = xmlText.replaceAll("</w:sectPr></w:p>", "</w:sectPr>")
                .replaceAll("</w:p></w:p>", "</w:p>").replaceAll("</w:tbl></w:p>", "</w:tbl>")
                .replaceAll("<w:p(\\s[A-Za-z0-9:\\s=\"]*)?/></w:p>", "")
                .replaceAll("</w:p><w:bookmarkEnd(\\s[A-Za-z0-9:\\s=\"]*)?/></w:p>", "</w:p>");

        // System.out.println(xmlText);
        body.set(CTBody.Factory.parse(xmlText));

        return generate();
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

    private String extractMergePart(NiceXWPFDocument docMerge, Map<String, String> styleIdsMap) throws InvalidFormatException {
        CTBody bodyMerge = docMerge.getDocument().getBody();
        // Map<String, String> styleIdsMap = mergeStyles(docMerge);
        Map<BigInteger, BigInteger> numIdsMap = mergeNumbering(docMerge);
        Map<String, String> blipIdsMap = mergePicture(docMerge);
        Map<String, String> hyperlinkMap = mergeHyperlink(docMerge);
        Map<String, String> chartIdsMap = mergeChart(docMerge);

        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = bodyMerge.xmlText(optionsOuter);
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
            // w:hyperlink r:id
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
                cTAbstractNum
                        .setAbstractNumId(BigInteger.valueOf(wrapper.getAbstractNumsSize() + 20));
                cache.put(xwpfNum.getCTNum().getAbstractNumId().getVal(), cTAbstractNum);
            }

            BigInteger numID = numbering
                    .addNum(numbering.addAbstractNum(new XWPFAbstractNum(cTAbstractNum)));

            numIdsMap.put(mergeNumId, numID);
        }
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
    
    private Map<String, String> mergeChart(NiceXWPFDocument docMerge) throws InvalidFormatException {
        Map<String, String> map = new HashMap<String, String>();
        // TODO
        return map;
    }
    
}