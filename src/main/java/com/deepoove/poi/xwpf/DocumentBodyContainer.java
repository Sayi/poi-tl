package com.deepoove.poi.xwpf;

import java.util.List;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

public class DocumentBodyContainer implements BodyContainer {

    NiceXWPFDocument doc;

    public DocumentBodyContainer(NiceXWPFDocument doc) {
        this.doc = doc;
    }

    @Override
    public int getPosOfParagraphCTP(CTP startCtp) {
        return doc.getPosOfParagraphCTP(startCtp);
    }

    @Override
    public void removeBodyElement(int i) {
        doc.removeBodyElement(i);

    }

    @Override
    public int getPosOfParagraph(XWPFParagraph startParagraph) {
        return doc.getPosOfParagraph(startParagraph);
    }

    @Override
    public List<IBodyElement> getBodyElements() {
        return doc.getBodyElements();
    }

    @Override
    public XWPFParagraph insertNewParagraph(XmlCursor insertPostionCursor) {
        return doc.insertNewParagraph(insertPostionCursor);
    }

    @Override
    public int getParaPos(XWPFParagraph insertNewParagraph) {
        return doc.getParaPos(insertNewParagraph);
    }

    @Override
    public void setParagraph(XWPFParagraph iBodyElement, int paraPos) {
        doc.setParagraph(iBodyElement, paraPos);
    }

    @Override
    public IBody getTarget() {
        return doc;
    }

    @Override
    public XWPFTable insertNewTbl(XmlCursor insertPostionCursor) {
        return doc.insertNewTbl(insertPostionCursor);
    }

    @Override
    public int getTablePos(XWPFTable insertNewTbl) {
        return doc.getTablePos(insertNewTbl);
    }

    @Override
    public void setTable(int tablePos, XWPFTable iBodyElement) {
       doc.setTable(tablePos, iBodyElement);
    }

    @Override
    public void updateBodyElements(IBodyElement insertNewTbl, IBodyElement copy) {
        doc.updateBodyElements(insertNewTbl, copy);
    }

    @Override
    public XWPFTable insertNewTable(XWPFRun run, int row, int col) {
        XmlCursor cursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
        XWPFTable table = insertNewTbl(cursor);
        for (int i = 0; i < row; i++) {
            XWPFTableRow tabRow = (table.getRow(i) == null) ? table.createRow() : table.getRow(i);
            for (int k = 0; k < col; k++) {
                if (tabRow.getCell(k) == null) {
                    tabRow.createCell();
                }
            }
        }
        return table;
    }

}
