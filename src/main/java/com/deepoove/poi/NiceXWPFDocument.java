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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.NumberingWrapper;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
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
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对原生poi的扩展
 * 
 * @author Sayi
 * @version 0.0.1
 *
 */
public class NiceXWPFDocument extends XWPFDocument {
    
    private static Logger logger = LoggerFactory.getLogger(NiceXWPFDocument.class);

	public NiceXWPFDocument() {
		super();
	}

	public NiceXWPFDocument(InputStream in) throws IOException {
		super(in);
	}
	
	/**
     * 合并某一行的单元格
     * @param table
     * @param row 合并的行
     * @param fromCol 合并开始列
     * @param toCol 合并结束列
     */
    public static void mergeCellsHorizonal(XWPFTable table, int row, int fromCol,
            int toCol) {
        if (toCol <= fromCol) return;
        XWPFTableCell cell = table.getRow(row).getCell(fromCol);
        CTTcPr tcPr = cell.getCTTc().getTcPr();
        if (null == tcPr)
            tcPr = cell.getCTTc().addNewTcPr();
        XWPFTableRow rowTable = table.getRow(row);
        for (int colIndex = fromCol + 1; colIndex <= toCol; colIndex++) {
            rowTable.getCtRow().removeTc(fromCol + 1);
            rowTable.removeCell(fromCol + 1);
        }
        spanCellsAcrossRow(table, row, fromCol, toCol - fromCol + 1);
    }

    /**
     * 合并某一列的单元格
     * @param table
     * @param col
     * @param fromRow
     * @param toRow
     */
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow,
            int toRow) {

        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {

            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (null == tcPr)
                tcPr = cell.getCTTc().addNewTcPr();
            CTVMerge vMerge = tcPr.addNewVMerge();
            if (rowIndex == fromRow) {
                // The first merged cell is set with RESTART merge value
                vMerge.setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                vMerge.setVal(STMerge.CONTINUE);
            }
        }
    }

    private static void spanCellsAcrossRow(XWPFTable table, int rowNum, int colNum,
            int span) {
        XWPFTableCell cell = table.getRow(rowNum).getCell(colNum);
        cell.getCTTc().getTcPr().addNewGridSpan();
        cell.getCTTc().getTcPr().getGridSpan()
                .setVal(BigInteger.valueOf((long) span));
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
		XmlCursor cursor = ((XWPFParagraph)run.getParent()).getCTP().newCursor();

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
					if (o instanceof CTP || o instanceof CTTbl){
						i++;
					}
				}
				bodyElements.add(i > bodyElements.size() ? bodyElements.size() : i, newT);
//				bodyElements.add(i, newT);
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
	 * 设置表格的宽度
	 * 
	 * @param table
	 * @param width 单位cm
	 * @param rows
	 * @param cols
	 */
	public void widthTable(XWPFTable table, float widthCM, int rows, int cols) {
	    int width = (int)(widthCM/2.54*1440);
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (null == tblPr) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
        CTTblWidth tblW = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblW.setType(0 == width ? STTblWidth.AUTO : STTblWidth.DXA);
        tblW.setW(BigInteger.valueOf(width));

        if (0 != width) {
            CTTblGrid tblGrid = table.getCTTbl().getTblGrid();
            if (null == tblGrid) {
                tblGrid = table.getCTTbl().addNewTblGrid();
            }

            for (int j = 0; j < cols; j++) {
                CTTblGridCol addNewGridCol = tblGrid.addNewGridCol();
                addNewGridCol.setW(BigInteger.valueOf(width / cols));
            }
        }
        CTTblBorders tblBorders = tblPr.getTblBorders();
        tblBorders.getBottom().setSz(BigInteger.valueOf(4));
        tblBorders.getLeft().setSz(BigInteger.valueOf(4));
        tblBorders.getTop().setSz(BigInteger.valueOf(4));
        tblBorders.getRight().setSz(BigInteger.valueOf(4));
        tblBorders.getInsideH().setSz(BigInteger.valueOf(4));
        tblBorders.getInsideV().setSz(BigInteger.valueOf(4));
        
    }
	
	/**
	 * 在某个段落起始处插入段落
	 * @param run
	 * @return
	 */
	public XWPFParagraph insertNewParagraph(XWPFRun run) {
//		XmlCursor cursor = run.getCTR().newCursor();
		XmlCursor cursor = ((XWPFParagraph)run.getParent()).getCTP().newCursor();
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
                    if (o instanceof CTP || o instanceof CTTbl)
                        i++;
                }
                bodyElements.add(i > bodyElements.size() ? bodyElements.size() : i, newP);
                cursor.toCursor(newParaPos);
                cursor.toEndToken();
                return newP;
            } finally {
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
	
	public void createPicture(String blipId, int id, int width, int height) {
		addPicture(blipId, id, width, height, createParagraph().createRun());
	}

	public void addPicture(String blipId, int id, int width, int height,
			XWPFRun run) {
		final int EMU = 9525;
		width *= EMU;
		height *= EMU;
		// String blipId =
		// getAllPictures().get(id).getPackageRelationship().getId();

		CTInline inline = run.getCTR().addNewDrawing().addNewInline();

		String picXml = ""
				+ "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
				+ "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
				+ id
				+ "\" name=\"Generated\"/>"
				+ "            <pic:cNvPicPr/>"
				+ "         </pic:nvPicPr>"
				+ "         <pic:blipFill>"
				+ "            <a:blip r:embed=\""
				+ blipId
				+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
				+ "            <a:stretch>"
				+ "               <a:fillRect/>"
				+ "            </a:stretch>"
				+ "         </pic:blipFill>"
				+ "         <pic:spPr>"
				+ "            <a:xfrm>"
				+ "               <a:off x=\"0\" y=\"0\"/>"
				+ "               <a:ext cx=\""
				+ width
				+ "\" cy=\""
				+ height
				+ "\"/>"
				+ "            </a:xfrm>"
				+ "            <a:prstGeom prst=\"rect\">"
				+ "               <a:avLst/>"
				+ "            </a:prstGeom>"
				+ "         </pic:spPr>"
				+ "      </pic:pic>"
				+ "   </a:graphicData>" + "</a:graphic>";

		// CTGraphicalObjectData graphicData =
		// inline.addNewGraphic().addNewGraphicData();
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(picXml);
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);
		// graphicData.set(xmlToken);

		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);

		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);

		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("Picture " + id);
		docPr.setDescr("Generated");
	}
	
	/**
	 *  生成一个新的流文档
	 * @return
	 * @throws IOException
	 * @since 1.3.0
	 */
	public NiceXWPFDocument generate() throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.write(byteArrayOutputStream);
        this.close();
        return new NiceXWPFDocument(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }
    

    /**
     * 
     * @param docMerges 待合并的文档
     * @param run 合并的位置
     * @return 合并后的文档
     * @throws Exception
     * @since 1.3.0
     */
    public NiceXWPFDocument merge(List<NiceXWPFDocument> docMerges, XWPFRun run) throws Exception {
        if (null == docMerges || docMerges.isEmpty() || null == run) return this;
        XWPFParagraph paragraph = insertNewParagraph(run);
        CTP ctp = paragraph.getCTP();
        
        CTBody body = this.getDocument().getBody();
        String srcString = body.xmlText();
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String sufix = srcString.substring(srcString.lastIndexOf("<"));
        List<String> addParts = new ArrayList<String>();
        for (NiceXWPFDocument docMerge : docMerges) {
            addParts.add(extractMergePart(docMerge));
        }
        
        CTP makeBody = CTP.Factory.parse(prefix + StringUtils.join(addParts, "") + sufix);
        ctp.set(makeBody);

        String xmlText = body.xmlText();
        xmlText = xmlText.replaceAll("<w:p><w:p>", "<w:p>").replaceAll("<w:p><w:p\\s", "<w:p ")
                .replaceAll("<w:p><w:tbl>", "<w:tbl>").replaceAll("<w:p><w:tbl\\s", "<w:tbl ");

        xmlText = xmlText.replaceAll("</w:sectPr></w:p>", "</w:sectPr>").replaceAll("</w:p></w:p>", "</w:p>")
                .replaceAll("</w:tbl></w:p>", "</w:tbl>")
                .replaceAll("<w:p(\\s[A-Za-z0-9:\\s=\"]*)?/></w:p>", "");

//        System.out.println(xmlText);
        body.set(CTBody.Factory.parse(xmlText));

        return generate();
    }
    
    /**
     * 文档合并
     * @param docMerge 待合并文档
     * @return 合并后的文档
     * @throws Exception
     * @since 1.3.0
     */
    public NiceXWPFDocument merge(NiceXWPFDocument docMerge) throws Exception{
        if (null == docMerge) return this;
        CTBody body = this.getDocument().getBody();
        String srcString = body.xmlText();
        String prefix = srcString.substring(0,srcString.indexOf(">")+1);
        String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
        String sufix = srcString.substring( srcString.lastIndexOf("<") );
        
        String addPart = extractMergePart(docMerge);
        
        CTBody makeBody = CTBody.Factory.parse(prefix + mainPart + addPart + sufix);
        body.set(makeBody);
        return generate();
    }

    private String extractMergePart(NiceXWPFDocument docMerge) throws InvalidFormatException {
        CTBody bodyMerge = docMerge.getDocument().getBody();
        Map<String, String> styleIdsMap = mergeStyles(docMerge);
        Map<BigInteger, BigInteger> numIdsMap = mergeNumbering(docMerge);
        Map<String, String> blipIdsMap = mergePicture(docMerge);
        
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
        }
        addPart = addPart.replaceAll("@PoiTL@", "");
        
        for (BigInteger numId : numIdsMap.keySet()) {
            addPart = addPart.replaceAll("<w:numId\\sw:val=\"" + numId + "\"",
                    "<w:numId w:val=\"" + numIdsMap.get(numId) + "\"");
        }
        // 关闭合并流
        try { docMerge.close(); } catch (Exception e) {}
        return addPart;
    }

    private String ridSectPr(String appendString) {
        int lastIndexOf = appendString.lastIndexOf("<w:sectPr");
        String addPart = null;
        if (-1 != lastIndexOf){
            String prefix = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<w:sectPr"));
            String sufix =   appendString.substring(appendString.lastIndexOf("</w:sectPr>") + 11, appendString.lastIndexOf("<"));
            return prefix + sufix;
        }else{
            addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        }
        return addPart;
    }

    private Map<String, String> mergePicture(NiceXWPFDocument docMerge) throws InvalidFormatException {
        Map<String, String> blipIdsMap = new HashMap<String, String>();
        List<XWPFPictureData> allPictures = docMerge.getAllPictures();
        for (XWPFPictureData xwpfPictureData : allPictures) {
            String relationId = docMerge.getRelationId(xwpfPictureData);
            String blidId = this.addPictureData(xwpfPictureData.getData(), xwpfPictureData.getPictureType());
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
        for (XWPFNum xwpfNum : nums) {
            BigInteger mergeNumId = xwpfNum.getCTNum().getNumId();

            xwpfAbstractNum = numberingMerge.getAbstractNum(xwpfNum.getCTNum().getAbstractNumId().getVal());
            cTAbstractNum = xwpfAbstractNum.getCTAbstractNum();
            cTAbstractNum.setAbstractNumId(BigInteger.valueOf(wrapper.getAbstractNumsSize() + 20));
            
            BigInteger numID = numbering.addNum(numbering.addAbstractNum(new XWPFAbstractNum(cTAbstractNum)));

            numIdsMap.put(mergeNumId, numID);
        }
        return numIdsMap;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> mergeStyles(NiceXWPFDocument docMerge){
    	Map<String, String> styleIdsMap = new HashMap<String, String>();
        XWPFStyles styles = this.getStyles();
        XWPFStyles stylesMerge = docMerge.getStyles();
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

}