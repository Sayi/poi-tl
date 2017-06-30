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

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

/**
 * 对原生poi的扩展
 * 
 * @author Sayi
 * @version 0.0.1
 *
 */
public class NiceXWPFDocument extends XWPFDocument {

	public NiceXWPFDocument() {
		super();
	}

	public NiceXWPFDocument(InputStream in) throws IOException {
		super(in);
	}
	
	/**
	 * 合并行单元格
	 * @param table
	 * @param row
	 * @param fromCol
	 * @param toCol
	 */
	public void mergeCellsHorizonal(XWPFTable table, int row, int fromCol,
			int toCol) {
		for (int colIndex = fromCol; colIndex <= toCol; colIndex++) {
			XWPFTableCell cell = table.getRow(row).getCell(colIndex);
			CTTcPr tcPr = cell.getCTTc().getTcPr();
			if (null == tcPr)
				tcPr = cell.getCTTc().addNewTcPr();
			CTHMerge hMerge = tcPr.addNewHMerge();
			if (colIndex == fromCol) {
				// The first merged cell is set with RESTART merge value
				hMerge.setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				hMerge.setVal(STMerge.CONTINUE);
			}
		}
	}

	/**
	 * 合并列单元格
	 * @param table
	 * @param col
	 * @param fromRow
	 * @param toRow
	 */
	public void mergeCellsVertically(XWPFTable table, int col, int fromRow,
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

	@Deprecated
	public void spanCellsAcrossRow(XWPFTable table, int rowNum, int colNum,
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
					if (o instanceof CTP || o instanceof CTTbl)
						i++;
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

}