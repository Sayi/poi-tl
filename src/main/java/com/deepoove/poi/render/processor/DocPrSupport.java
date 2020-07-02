package com.deepoove.poi.render.processor;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import com.deepoove.poi.xwpf.IdenifierManagerWrapper;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

public class DocPrSupport {

    public static void updateDocPrId(XWPFTable table) {
        List<XWPFTableRow> rows = table.getRows();
        rows.forEach(row -> {
            List<XWPFTableCell> cells = row.getTableCells();
            cells.forEach(cell -> {
                cell.getParagraphs().forEach(DocPrSupport::updateDocPrId);
                cell.getTables().forEach(DocPrSupport::updateDocPrId);
            });
        });
    }

    public static void updateDocPrId(XWPFParagraph paragraph) {
        updateDocPrId(paragraph.getRuns());

    }

    public static void updateDocPrId(List<XWPFRun> runs) {
        runs.forEach(DocPrSupport::updateDocPrId);
    }

    public static void updateDocPrId(XWPFRun run) {
        NiceXWPFDocument document = (NiceXWPFDocument) run.getDocument();
        if (!document.getDocPrIdenifierManager().isValid()) return;
        CTR r = run.getCTR();
        for (CTDrawing ctDrawing : r.getDrawingList()) {
            processCTDrawing(document, ctDrawing);
        }
        XmlObject[] xmlObjects = r.selectPath(IdenifierManagerWrapper.XPATH_DRAWING);
        if (null == xmlObjects || xmlObjects.length <= 0) return;
        for (XmlObject xmlObject : xmlObjects) {
            try {
                CTDrawing ctDrawing = CTDrawing.Factory.parse(xmlObject.xmlText());
                processCTDrawing(document, ctDrawing);
                xmlObject.set(ctDrawing);
            } catch (XmlException e) {
                // no-op
            }
        }
    }

    private static void processCTDrawing(NiceXWPFDocument document, CTDrawing ctDrawing) {
        for (CTAnchor anchor : ctDrawing.getAnchorList()) {
            if (anchor.getDocPr() != null) {
                anchor.getDocPr().setId(document.getDocPrIdenifierManager().reserveNew());
            }
        }
        for (CTInline inline : ctDrawing.getInlineList()) {
            if (inline.getDocPr() != null) {
                inline.getDocPr().setId(document.getDocPrIdenifierManager().reserveNew());
            }
        }
    }

}
