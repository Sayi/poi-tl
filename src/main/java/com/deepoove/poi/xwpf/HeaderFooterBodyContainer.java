package com.deepoove.poi.xwpf;

import java.util.List;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import com.deepoove.poi.util.ReflectionUtils;

public class HeaderFooterBodyContainer implements BodyContainer {

    XWPFHeaderFooter headerFooter;

    public HeaderFooterBodyContainer(XWPFHeaderFooter cell) {
        this.headerFooter = cell;
    }

    @Override
    public int getPosOfParagraphCTP(CTP startCtp) {
        IBodyElement current;
        List<IBodyElement> bodyElements = headerFooter.getBodyElements();
        for (int i = 0; i < bodyElements.size(); i++) {
            current = bodyElements.get(i);
            if (current.getElementType() == BodyElementType.PARAGRAPH) {
                if (((XWPFParagraph) current).getCTP().equals(startCtp)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void removeBodyElement(int pos) {
        List<IBodyElement> bodyElements = getBodyElements();
        if (pos >= 0 && pos < bodyElements.size()) {
            BodyElementType type = bodyElements.get(pos).getElementType();
            if (type == BodyElementType.TABLE) {
                headerFooter.removeTable((XWPFTable) bodyElements.get(pos));
            }
            if (type == BodyElementType.PARAGRAPH) {
                headerFooter.removeParagraph((XWPFParagraph) bodyElements.get(pos));
            }
        }
    }

    @Override
    public int getPosOfParagraph(XWPFParagraph startParagraph) {
        return getPosOfParagraphCTP(startParagraph.getCTP());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IBodyElement> getBodyElements() {
        return (List<IBodyElement>) ReflectionUtils.getValue("bodyElements", headerFooter);
    }

    @Override
    public XWPFParagraph insertNewParagraph(XmlCursor insertPostionCursor) {
        return headerFooter.insertNewParagraph(insertPostionCursor);
    }

    @Override
    public int getParaPos(XWPFParagraph insertNewParagraph) {
        List<XWPFParagraph> paragraphs = headerFooter.getParagraphs();
        for (int i = 0; i < paragraphs.size(); i++) {
            if (paragraphs.get(i) == insertNewParagraph) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setParagraph(XWPFParagraph p, int paraPos) {
        List<XWPFParagraph> paragraphs = (List<XWPFParagraph>) ReflectionUtils.getValue("paragraphs", headerFooter);
        paragraphs.set(paraPos, p);
        CTHdrFtr ctTc = headerFooter._getHdrFtr();
        ctTc.setPArray(paraPos, p.getCTP());

    }

    @Override
    public IBody getTarget() {
        return headerFooter;
    }

    @Override
    public void updateBodyElements(IBodyElement insertNewParagraph, IBodyElement copy) {
        int pos = -1;
        List<IBodyElement> bodyElements = getBodyElements();
        for (int i = 0; i < bodyElements.size(); i++) {
            if (bodyElements.get(i) == insertNewParagraph) {
                pos = i;
            }
        }
        if (-1 != pos) bodyElements.set(pos, copy);

    }

    @Override
    public XWPFTable insertNewTbl(XmlCursor insertPostionCursor) {
        return headerFooter.insertNewTbl(insertPostionCursor);
    }

    @Override
    public int getTablePos(XWPFTable insertNewTbl) {
        List<XWPFTable> tables = headerFooter.getTables();
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i) == insertNewTbl) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setTable(int pos, XWPFTable table) {
        // cell.getTables().set(pos, table);
        List<XWPFTable> tables = (List<XWPFTable>) ReflectionUtils.getValue("tables", headerFooter);
        tables.set(pos, table);
        headerFooter._getHdrFtr().setTblArray(pos, table.getCTTbl());

    }

    @Override
    public XWPFTable insertNewTable(XWPFRun run, int row, int col) {
        XmlCursor cursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
        XWPFTable table = insertNewTbl(cursor);
        // hack for cursor.removeXmlContents(); in XWPFHeaderFooter
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 0; i < rows.size(); i++) {
            table.removeRow(i);
        }
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
