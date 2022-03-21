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

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.BodyType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ICell;
import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.ISDTContents;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;

/**
 * SDT should be in body , paragraph, table-row and SDT
 * 
 * @author Sayi
 *
 */
public class XWPFStructuredDocumentTag implements IBodyElement, IRunElement, ICell, ISDTContents {

    private CTSdtRun ctSdtRun;
    private CTSdtBlock ctSdtBlock;
    private CTSdtCell ctSdtCell;

    private CTSdtPr ctSdtPr;
    private final IBody part;

    private final XWPFStructuredDocumentTagContent content;

    // part should be a paragraph(p), but is currently a paragraph's body
    public XWPFStructuredDocumentTag(CTSdtRun sdtRun, IBody part) {
        this.ctSdtRun = sdtRun;
        this.part = part;
        this.ctSdtPr = sdtRun.getSdtPr();
        this.content = new XWPFStructuredDocumentTagContent(sdtRun.getSdtContent(), part, this);
    }

    // part should be a document(body), but is currently a paragraph's body
    public XWPFStructuredDocumentTag(CTSdtBlock block, IBody part) {
        this.ctSdtBlock = block;
        this.part = part;
        this.ctSdtPr = block.getSdtPr();
        this.content = new XWPFStructuredDocumentTagContent(block.getSdtContent(), part, this);
    }

    // part should be tablerow(tr), but is currently a tablerow'table' body
    public XWPFStructuredDocumentTag(CTSdtCell sdtCell, XWPFTableRow xwpfTableRow, IBody part) {
        this.ctSdtCell = sdtCell;
        this.part = part;
        this.ctSdtPr = sdtCell.getSdtPr();
        this.content = new XWPFStructuredDocumentTagContent(sdtCell.getSdtContent(), xwpfTableRow, part, this);
    }

    public CTSdtRun getCtSdtRun() {
        return ctSdtRun;
    }

    public CTSdtBlock getCtSdtBlock() {
        return ctSdtBlock;
    }

    public CTSdtCell getCtSdtCell() {
        return ctSdtCell;
    }

    public XWPFStructuredDocumentTagContent getContent() {
        return content;
    }

    public CTDataBinding getDataBinding() {
        return ctSdtPr.getDataBinding();
    }

    /**
     * @return document part
     */
    public POIXMLDocumentPart getPart() {
        return part.getPart();
    }

    /**
     * @return partType
     */
    public BodyType getPartType() {
        return BodyType.CONTENTCONTROL;
    }

    /**
     * @return element type
     */
    public BodyElementType getElementType() {
        return BodyElementType.CONTENTCONTROL;
    }

    public XWPFDocument getDocument() {
        return part.getXWPFDocument();
    }

    @Override
    public IBody getBody() {
        return part;
    }

}
