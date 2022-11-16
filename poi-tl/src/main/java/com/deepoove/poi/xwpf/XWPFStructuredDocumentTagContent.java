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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtContentBlock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtContentCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtContentRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;

/**
 * sdt content should include run, paragraph,table cell and sdt, not only
 * IBodyElement
 * 
 * implements IBodyElement for compatible!
 * 
 * @author Sayi
 *
 */
public class XWPFStructuredDocumentTagContent implements ISDTContent, IRunBody, IBody, IBodyElement {

    private XWPFStructuredDocumentTag sdt;
    private CTSdtContentRun sdtContentRun;
    private CTSdtContentBlock sdtContentBlock;
    private CTSdtContentCell sdtContentCell;

    private final List<ISDTContents> sdtElements = new ArrayList<>();
    private List<XWPFParagraph> paragraphs = new ArrayList<>();
    private List<XWPFTableCell> cells = new ArrayList<>();
    private List<XWPFRun> runs = new ArrayList<>();

    public XWPFStructuredDocumentTagContent(CTSdtContentRun sdtRun, IBody part, XWPFStructuredDocumentTag parent) {
        this.sdtContentRun = sdtRun;
        this.sdt = parent;
        if (sdtRun == null) {
            return;
        }
        XmlCursor cursor = sdtRun.newCursor();
        cursor.selectPath("./*");
        while (cursor.toNextSelection()) {
            XmlObject o = cursor.getObject();
            if (o instanceof CTR) {
                XWPFRun run = new XWPFRun((CTR) o, this);
                runs.add(run);
                sdtElements.add(run);
            } else if (o instanceof CTSdtRun) {
                XWPFStructuredDocumentTag c = new XWPFStructuredDocumentTag(((CTSdtRun) o), this);
                sdtElements.add(c);
            }
        }
        cursor.dispose();
    }

    public XWPFStructuredDocumentTagContent(CTSdtContentBlock block, IBody part, XWPFStructuredDocumentTag parent) {
        this.sdtContentBlock = block;
        this.sdt = parent;
        if (block == null) {
            return;
        }
        XmlCursor cursor = block.newCursor();
        cursor.selectPath("./*");
        while (cursor.toNextSelection()) {
            XmlObject o = cursor.getObject();
            if (o instanceof CTP) {
                XWPFParagraph p = new XWPFParagraph((CTP) o, this);
                paragraphs.add(p);
                sdtElements.add(p);
            } else if (o instanceof CTTbl) {
                XWPFTable t = new XWPFTable((CTTbl) o, this);
                sdtElements.add(t);
            } else if (o instanceof CTSdtBlock) {
                XWPFStructuredDocumentTag c = new XWPFStructuredDocumentTag(((CTSdtBlock) o), this);
                sdtElements.add(c);
            } else if (o instanceof CTR) {
                XWPFRun run = new XWPFRun((CTR) o, this);
                // runs.add(run);
                sdtElements.add(run);
            }
        }
        cursor.dispose();
    }

    public XWPFStructuredDocumentTagContent(CTSdtContentCell cell, XWPFTableRow row, IBody part,
            XWPFStructuredDocumentTag parent) {
        this.sdt = parent;
        this.sdtContentCell = cell;
        if (cell == null) {
            return;
        }
        XmlCursor cursor = cell.newCursor();
        cursor.selectPath("./*");
        while (cursor.toNextSelection()) {
            XmlObject o = cursor.getObject();
            if (o instanceof CTTc) {
                XWPFTableCell c = new XWPFTableCell((CTTc) o, row, this);
                cells.add(c);
            }
        }
        cursor.dispose();

    }

    public XWPFRun insertNewRun(int pos) {
        if (pos == runs.size()) {
            return createRun();
        }
        return insertNewProvidedRun(pos, newCursor -> {
            String uri = CTR.type.getName().getNamespaceURI();
            String localPart = "r";
            // creates a new run, cursor is positioned inside the new
            // element
            newCursor.beginElement(localPart, uri);
            // move the cursor to the START token to the run just created
            newCursor.toParent();
            CTR r = (CTR) newCursor.getObject();
            return new XWPFRun(r, (IRunBody) this);
        });
    }

    public XWPFRun createRun() {
        XWPFRun xwpfRun = new XWPFRun(sdtContentRun.addNewR(), (IRunBody) this);
        runs.add(xwpfRun);
        sdtElements.add(xwpfRun);
        return xwpfRun;
    }

    /**
     * insert a new run provided by in all runs
     *
     * @param <T>      XWPFRun or XWPFHyperlinkRun or XWPFFieldRun
     * @param pos      The position at which the new run should be added.
     * @param provider provide a new run at position of the given cursor.
     * @return the inserted run or null if the given pos is out of bounds.
     */
    private <T extends XWPFRun> T insertNewProvidedRun(int pos, Function<XmlCursor, T> provider) {
        if (pos >= 0 && pos < runs.size()) {
            XWPFRun run = runs.get(pos);
            CTR ctr = run.getCTR();
            XmlCursor newCursor = ctr.newCursor();
            if (!isCursorInRunContent(newCursor)) {
                // look up correct position for CTP -> XXX -> R array
                newCursor.toParent();
            }
            if (isCursorInRunContent(newCursor)) {
                // provide a new run
                T newRun = provider.apply(newCursor);

                // To update the iruns, find where we're going
                // in the normal runs, and go in there
                int iPos = sdtElements.size();
                int oldAt = sdtElements.indexOf(run);
                if (oldAt != -1) {
                    iPos = oldAt;
                }
                sdtElements.add(iPos, newRun);
                // Runs itself is easy to update
                runs.add(pos, newRun);
                return newRun;
            }
            newCursor.dispose();
        }
        return null;
    }

    public XWPFRun insertNewRunAfter(int pos) {
        if (pos == runs.size()) {
            return createRun();
        }

        if (pos >= 0 && pos < runs.size()) {
            XWPFRun run = runs.get(pos);
            CTR ctr = run.getCTR();
            XmlCursor newCursor = ctr.newCursor();
            if (!isCursorInRunContent(newCursor)) {
                // look up correct position for CTP -> XXX -> R array
                newCursor.toParent();
            }
            if (isCursorInRunContent(newCursor)) {
                boolean nextSibling = newCursor.toNextSibling();
                if (!nextSibling) {
                    return createRun();
                }
                // provide a new run
                String uri = CTR.type.getName().getNamespaceURI();
                String localPart = "r";
                // creates a new run, cursor is positioned inside the new
                // element
                newCursor.beginElement(localPart, uri);
                // move the cursor to the START token to the run just created
                newCursor.toParent();
                CTR r = (CTR) newCursor.getObject();
                XWPFRun newRun = new XWPFRun(r, (IRunBody) this);

                // To update the iruns, find where we're going
                // in the normal runs, and go in there
                int iPos = sdtElements.size();
                int oldAt = sdtElements.indexOf(run);
                if (oldAt != -1) {
                    iPos = oldAt;
                }
                if (iPos + 1 >= sdtElements.size()) {
                    sdtElements.add(newRun);
                } else {
                    sdtElements.add(iPos, newRun);
                }
                // Runs itself is easy to update
                if (pos + 1 >= runs.size()) {
                    runs.add(newRun);
                } else {
                    runs.add(pos + 1, newRun);
                }
                newCursor.dispose();
                return newRun;
            }
            newCursor.dispose();
        }
        return null;

    }

    private boolean isCursorInRunContent(XmlCursor cursor) {
        XmlCursor verify = cursor.newCursor();
        verify.toParent();
        boolean result = (verify.getObject() == this.sdtContentRun);
        verify.dispose();
        return result;
    }

    private boolean isCursorInBlockContent(XmlCursor cursor) {
        XmlCursor verify = cursor.newCursor();
        verify.toParent();
        boolean result = (verify.getObject() == this.sdtContentBlock);
        verify.dispose();
        return result;
    }

    /**
     * removes a Run at the position pos in the paragraph
     *
     * @return true if the run was removed
     */
    public boolean removeRun(int pos) {
        if (pos >= 0 && pos < runs.size()) {
            XWPFRun run = runs.get(pos);
            XmlCursor c = run.getCTR().newCursor();
            c.removeXml();
            c.dispose();
            runs.remove(pos);
            sdtElements.remove(run);
            return true;
        }
        return false;
    }

    public void setAndUpdateRun(XWPFRun xwpfRun, XWPFRun source, int insertPostionCursor) {
        // body
        // maybe need find correct position:rPos;
        int rPos = 0;
        if (insertPostionCursor >= 0 && insertPostionCursor <= runs.size()) {
            // calculate the correct pos as our run/irun list contains
            // hyperlinks
            // and fields so it is different to the paragraph R array.
            for (int i = 0; i < insertPostionCursor; i++) {
                XWPFRun currRun = runs.get(i);
                if (!(currRun instanceof XWPFHyperlinkRun || currRun instanceof XWPFFieldRun)) {
                    rPos++;
                }
            }
        }

        sdtContentRun.setRArray(rPos, xwpfRun.getCTR());

        // runs
        for (int i = 0; i < runs.size(); i++) {
            XWPFRun ele = runs.get(i);
            if (ele == source) {
                runs.set(i, xwpfRun);
            }
        }

        // iruns
        List<ISDTContents> iruns = getSdtElements();
        for (int i = 0; i < iruns.size(); i++) {
            ISDTContents ele = iruns.get(i);
            if (ele == source) {
                iruns.set(i, xwpfRun);
            }
        }
    }

    @Override
    public String getText() {
        StringBuilder text = new StringBuilder();
        boolean addNewLine = false;
        for (int i = 0; i < sdtElements.size(); i++) {
            Object o = sdtElements.get(i);
            if (o instanceof XWPFParagraph) {
                appendParagraph((XWPFParagraph) o, text);
                addNewLine = true;
            } else if (o instanceof XWPFTable) {
                appendTable((XWPFTable) o, text);
                addNewLine = true;
            } else if (o instanceof XWPFSDT) {
                text.append(((XWPFSDT) o).getContent().getText());
                addNewLine = true;
            } else if (o instanceof XWPFRun) {
                text.append(o);
                addNewLine = false;
            }
            if (addNewLine && i < sdtElements.size() - 1) {
                text.append("\n");
            }
        }
        return text.toString();
    }

    private void appendTable(XWPFTable table, StringBuilder text) {
        // this works recursively to pull embedded tables from within cells
        for (XWPFTableRow row : table.getRows()) {
            List<ICell> cells = row.getTableICells();
            for (int i = 0; i < cells.size(); i++) {
                ICell cell = cells.get(i);
                if (cell instanceof XWPFTableCell) {
                    text.append(((XWPFTableCell) cell).getTextRecursively());
                } else if (cell instanceof XWPFSDTCell) {
                    text.append(((XWPFSDTCell) cell).getContent().getText());
                }
                if (i < cells.size() - 1) {
                    text.append("\t");
                }
            }
            text.append('\n');
        }
    }

    private void appendParagraph(XWPFParagraph paragraph, StringBuilder text) {
        for (IRunElement run : paragraph.getRuns()) {
            text.append(run);
        }
    }

    @Override
    public String toString() {
        return getText();
    }

    public List<ISDTContents> getSdtElements() {
        return sdtElements;
    }

    public List<XWPFTableCell> getCells() {
        return cells;
    }

    public List<XWPFRun> getRuns() {
        return runs;
    }

    public XWPFStructuredDocumentTag getSdt() {
        return sdt;
    }

    public CTSdtContentRun getSdtContentRun() {
        return sdtContentRun;
    }

    public CTSdtContentBlock getSdtContentBlock() {
        return sdtContentBlock;
    }

    public CTSdtContentCell getSdtContentCell() {
        return sdtContentCell;
    }

    @Override
    public XWPFDocument getDocument() {
        return getXWPFDocument();
    }

    @Override
    public POIXMLDocumentPart getPart() {
        return sdt.getPart();
    }

    @Override
    public BodyType getPartType() {
        return sdt.getPartType();
    }

    @Override
    public List<XWPFParagraph> getParagraphs() {
        return Collections.unmodifiableList(paragraphs);
    }

    /**
     * add a Paragraph to this sdt content
     *
     * @param p the paragraph which has to be added
     */
    public void addParagraph(XWPFParagraph p) {
        paragraphs.add(p);
        sdtElements.add(p);
    }

    /**
     * removes a paragraph of this textbox
     *
     * @param pos The position in the list of paragraphs, 0-based
     */
    public void removeParagraph(int pos) {
        XWPFParagraph removedParagraph = paragraphs.get(pos);
        paragraphs.remove(pos);
        sdtContentBlock.removeP(pos);
        sdtElements.remove(removedParagraph);
    }

    /**
     * Removes a specific paragraph from this textbox
     *
     * @param paragraph - {@link XWPFParagraph} object to remove
     */
    public void removeParagraph(XWPFParagraph paragraph) {
        if (paragraphs.contains(paragraph)) {
            CTP ctP = paragraph.getCTP();
            XmlCursor c = ctP.newCursor();
            c.removeXml();
            c.dispose();
            paragraphs.remove(paragraph);
            sdtElements.remove(paragraph);
        }
    }

    @Override
    public XWPFParagraph getParagraph(CTP p) {
        for (XWPFParagraph paragraph : paragraphs) {
            if (p.equals(paragraph.getCTP())) {
                return paragraph;
            }
        }
        return null;
    }

    @Override
    public XWPFParagraph getParagraphArray(int pos) {
        if (pos >= 0 && pos < paragraphs.size()) {
            return paragraphs.get(pos);
        }
        return null;
    }

    public XWPFParagraph insertNewParagraph(final XmlCursor cursor) {
        if (!isCursorInBlockContent(cursor)) {
            return null;
        }

        String uri = CTP.type.getName().getNamespaceURI();
        String localPart = "p";
        cursor.beginElement(localPart, uri);
        cursor.toParent();
        CTP p = (CTP) cursor.getObject();
        XWPFParagraph newP = new XWPFParagraph(p, this);
        XmlObject o = null;
        while (!(o instanceof CTP) && (cursor.toPrevSibling())) {
            o = cursor.getObject();
        }
        if ((!(o instanceof CTP)) || o == p) {
            paragraphs.add(0, newP);
        } else {
            int pos = paragraphs.indexOf(getParagraph((CTP) o)) + 1;
            paragraphs.add(pos, newP);
        }
        int i = 0;
        XmlCursor p2 = p.newCursor();
        cursor.toCursor(p2);
        p2.dispose();
        while (cursor.toPrevSibling()) {
            o = cursor.getObject();
            if (o instanceof CTP || o instanceof CTTbl) {
                i++;
            }
        }
        sdtElements.add(i, newP);
        p2 = p.newCursor();
        cursor.toCursor(p2);
        p2.dispose();
        cursor.toEndToken();
        return newP;
    }

    @Override
    public XWPFTable getTable(CTTbl ctTable) {
        return null;
    }

    @Override
    public XWPFTable getTableArray(int pos) {
        return null;
    }

    @Override
    public XWPFTable insertNewTbl(XmlCursor cursor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertTable(int pos, XWPFTable table) {
        throw new UnsupportedOperationException();
    }

    @Override
    public XWPFTableCell getTableCell(CTTc cell) {
        return null;
    }

    @Override
    public List<IBodyElement> getBodyElements() {
        return Collections.unmodifiableList(sdtElements.stream()
                .filter(e -> e instanceof IBodyElement)
                .map(e -> (IBodyElement) e)
                .collect(Collectors.toList()));
    }

    @Override
    public List<XWPFTable> getTables() {
        return null;
    }

    @Override
    public XWPFDocument getXWPFDocument() {
        return sdt.getDocument();
    }

    @Override
    public IBody getBody() {
        return this;
    }

    @Override
    public BodyElementType getElementType() {
        return sdt.getElementType();
    }

}
