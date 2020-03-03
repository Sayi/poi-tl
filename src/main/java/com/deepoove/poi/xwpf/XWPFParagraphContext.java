package com.deepoove.poi.xwpf;

import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFFieldRun;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;

public class XWPFParagraphContext implements ParagraphContext {

    XWPFParagraphWrapper paragraphWrapper;

    public XWPFParagraphContext(XWPFParagraphWrapper paragraphWrapper) {
        this.paragraphWrapper = paragraphWrapper;
    }

    public XWPFParagraph getParagraph() {
        return paragraphWrapper.getParagraph();
    }

    public void setAndUpdateRun(XWPFRun xwpfRun2, XWPFRun insertNewRun, int insertPostionCursor) {
        paragraphWrapper.setAndUpdateRun(xwpfRun2, insertNewRun, insertPostionCursor);

    }

    public XWPFRun insertNewRun(XWPFRun xwpfRun, int insertPostionCursor) {
        if (xwpfRun instanceof XWPFHyperlinkRun) {
            return paragraphWrapper.insertNewHyperLinkRun(insertPostionCursor, "");
        } else if (xwpfRun instanceof XWPFFieldRun) {
            return paragraphWrapper.insertNewField(insertPostionCursor);
        } else {
            return paragraphWrapper.insertNewRun(insertPostionCursor);
        }
    }

    public XWPFRun createRun(XWPFRun xwpfRun, IRunBody p) {
        if (xwpfRun instanceof XWPFHyperlinkRun) {
            return new XWPFHyperlinkRun((CTHyperlink) ((XWPFHyperlinkRun) xwpfRun).getCTHyperlink().copy(),
                    (CTR) ((XWPFHyperlinkRun) xwpfRun).getCTR().copy(), p);
        } else if (xwpfRun instanceof XWPFFieldRun) {
            return new XWPFFieldRun((CTSimpleField) ((XWPFFieldRun) xwpfRun).getCTField().copy(),
                    (CTR) ((XWPFFieldRun) xwpfRun).getCTR().copy(), p);
        } else {
            return new XWPFRun((CTR) xwpfRun.getCTR().copy(), p);
        }
    }

    public XWPFRun createRun(XmlObject object, IRunBody p) {
        if (object instanceof CTHyperlink) {
            return new XWPFHyperlinkRun((CTHyperlink) object, ((CTHyperlink) object).getRArray(0), p);
        } else if (object instanceof CTSimpleField) {
            return new XWPFFieldRun((CTSimpleField) object, ((CTSimpleField) object).getRArray(0), p);
        } else {
            return new XWPFRun((CTR) object, p);
        }
    }

    @Override
    public void removeRun(int pos) {
        paragraphWrapper.removeRun(pos);
    }

}
