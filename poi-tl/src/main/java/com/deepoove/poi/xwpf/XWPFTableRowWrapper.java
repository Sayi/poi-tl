package com.deepoove.poi.xwpf;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ICell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;

public class XWPFTableRowWrapper {

    private XWPFTableRow row;

    public XWPFTableRowWrapper(XWPFTableRow row) {
        this.row = row;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<ICell> getTableICells() {
        boolean haveSdt = false;
        List<ICell> cells = new ArrayList<>();
        XmlCursor cursor = row.getCtRow().newCursor();
        try {
            cursor.selectPath("./*");
            while (cursor.toNextSelection()) {
                XmlObject o = cursor.getObject();
                if (o instanceof CTTc) {
                    cells.add(new XWPFTableCell((CTTc) o, row, row.getTable().getBody()));
                } else if (o instanceof CTSdtCell) {
                    haveSdt = true;
                    cells.add(new XWPFStructuredDocumentTag((CTSdtCell) o, row, row.getTable().getBody()));
                }
            }
        } finally {
            cursor.dispose();
        }
        return haveSdt ? cells : (List) row.getTableCells();
    }

    public XWPFTableRow getRow() {
        return row;
    }

}
