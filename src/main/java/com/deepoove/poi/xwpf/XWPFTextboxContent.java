package com.deepoove.poi.xwpf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.util.Internal;
import org.apache.poi.xwpf.usermodel.BodyType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTxbxContent;

public class XWPFTextboxContent implements IBody {

    private final CTTxbxContent ctTxbxContent;
    protected List<XWPFParagraph> paragraphs;
    protected List<IBodyElement> bodyElements;

    protected IBody part;
    private XWPFRun run;
    private XmlObject xmlObject;

    /**
     * If a table cell does not include at least one block-level element, then this
     * document shall be considered corrupt
     */
    public XWPFTextboxContent(CTTxbxContent ctTxbxContent, XWPFRun run, IBody part, XmlObject xmlObject) {
        this.ctTxbxContent = ctTxbxContent;
        this.part = part;
        this.run = run;
        this.xmlObject = xmlObject;

        bodyElements = new ArrayList<>();
        paragraphs = new ArrayList<>();

        XmlCursor cursor = ctTxbxContent.newCursor();
        cursor.selectPath("./*");
        while (cursor.toNextSelection()) {
            XmlObject o = cursor.getObject();
            if (o instanceof CTP) {
                XWPFParagraph p = new XWPFParagraph((CTP) o, this);
                paragraphs.add(p);
                bodyElements.add(p);
            }
        }
        cursor.dispose();
    }

    @Internal
    public CTTxbxContent getCTTxbxContent() {
        return ctTxbxContent;
    }

    @Internal
    public XmlObject getXmlObject() {
        return xmlObject;
    }

    /**
     * get the to which the textbox belongs
     *
     * @see org.apache.poi.xwpf.usermodel.IBody#getPart()
     */
    @Override
    @SuppressWarnings("deprecation")
    public POIXMLDocumentPart getPart() {
        // TODO
        return run.getParagraph().getPart();
    }

    /**
     * @see org.apache.poi.xwpf.usermodel.IBody#getPartType()
     */
    @Override
    public BodyType getPartType() {
        // TODO
        return BodyType.TABLECELL;
    }

    /**
     * returns an Iterator with paragraphs and tables
     *
     * @see org.apache.poi.xwpf.usermodel.IBody#getBodyElements()
     */
    @Override
    public List<IBodyElement> getBodyElements() {
        return Collections.unmodifiableList(bodyElements);
    }

    public void setParagraph(XWPFParagraph p) {
        if (ctTxbxContent.sizeOfPArray() == 0) {
            ctTxbxContent.addNewP();
        }
        ctTxbxContent.setPArray(0, p.getCTP());
    }

    /**
     * returns a list of paragraphs
     */
    @Override
    public List<XWPFParagraph> getParagraphs() {
        return Collections.unmodifiableList(paragraphs);
    }

    /**
     * Add a Paragraph to this textbox
     *
     * @return The paragraph which was added
     */
    public XWPFParagraph addParagraph() {
        XWPFParagraph p = new XWPFParagraph(ctTxbxContent.addNewP(), this);
        addParagraph(p);
        return p;
    }

    /**
     * add a Paragraph to this TableCell
     *
     * @param p the paragaph which has to be added
     */
    public void addParagraph(XWPFParagraph p) {
        paragraphs.add(p);
        bodyElements.add(p);
    }

    /**
     * removes a paragraph of this textbox
     *
     * @param pos The position in the list of paragraphs, 0-based
     */
    public void removeParagraph(int pos) {
        XWPFParagraph removedParagraph = paragraphs.get(pos);
        paragraphs.remove(pos);
        ctTxbxContent.removeP(pos);
        bodyElements.remove(removedParagraph);
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
            bodyElements.remove(paragraph);
        }
    }

    /**
     * if there is a corresponding {@link XWPFParagraph} of the parameter ctp in the
     * paragraphList of this textbox the method will return this paragraph if there
     * is no corresponding {@link XWPFParagraph} the method will return null
     *
     * @param p is instance of CTP and is searching for an XWPFParagraph
     * @return null if there is no XWPFParagraph with an corresponding CTPparagraph
     *         in the paragraphList of this table XWPFParagraph with the
     *         correspondig CTP p
     */
    @Override
    public XWPFParagraph getParagraph(CTP p) {
        for (XWPFParagraph paragraph : paragraphs) {
            if (p.equals(paragraph.getCTP())) {
                return paragraph;
            }
        }
        return null;
    }

    /**
     * add a new paragraph at position of the cursor
     *
     * @param cursor The XmlCursor structure created with XmlBeans
     * @return the inserted paragraph
     */
    @Override
    public XWPFParagraph insertNewParagraph(final XmlCursor cursor) {
        if (!isCursorInTextBox(cursor)) {
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
        bodyElements.add(i, newP);
        p2 = p.newCursor();
        cursor.toCursor(p2);
        p2.dispose();
        cursor.toEndToken();
        return newP;
    }

    /**
     * verifies that cursor is on the right position
     */
    private boolean isCursorInTextBox(XmlCursor cursor) {
        XmlCursor verify = cursor.newCursor();
        verify.toParent();
        boolean result = (verify.getObject() == this.ctTxbxContent);
        verify.dispose();
        return result;
    }

    /**
     * @see org.apache.poi.xwpf.usermodel.IBody#getParagraphArray(int)
     */
    @Override
    public XWPFParagraph getParagraphArray(int pos) {
        if (pos >= 0 && pos < paragraphs.size()) {
            return paragraphs.get(pos);
        }
        return null;
    }

    /**
     * get a table by its CTTbl-Object
     *
     * @see org.apache.poi.xwpf.usermodel.IBody#getTable(org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl)
     */
    @Override
    public XWPFTable getTable(CTTbl ctTable) {
        return null;
    }

    /**
     * @see org.apache.poi.xwpf.usermodel.IBody#getTableArray(int)
     */
    @Override
    public XWPFTable getTableArray(int pos) {
        return null;
    }

    /**
     * @see org.apache.poi.xwpf.usermodel.IBody#getTables()
     */
    @Override
    public List<XWPFTable> getTables() {
        return null;
    }

    /**
     * inserts an existing XWPFTable to the arrays bodyElements and tables
     *
     * @see org.apache.poi.xwpf.usermodel.IBody#insertTable(int,
     *      org.apache.poi.xwpf.usermodel.XWPFTable)
     */
    @Override
    public void insertTable(int pos, XWPFTable table) {
        throw new UnsupportedOperationException();
    }

    @Override
    public XWPFDocument getXWPFDocument() {
        return part.getXWPFDocument();
    }

    @Override
    public XWPFTable insertNewTbl(XmlCursor cursor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public XWPFTableCell getTableCell(CTTc cell) {
        return null;
    }

}
