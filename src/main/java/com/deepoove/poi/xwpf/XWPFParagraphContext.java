package com.deepoove.poi.xwpf;

import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlObject;

public class XWPFParagraphContext implements ParagraphContext {

    XWPFParagraphWrapper paragraphWrapper;

    public XWPFParagraphContext(XWPFParagraphWrapper paragraphWrapper) {
        this.paragraphWrapper = paragraphWrapper;
    }

    public XWPFParagraph getParagraph() {
        return paragraphWrapper.getParagraph();
    }

    public XWPFRun insertNewRun(XWPFRun xwpfRun, int insertPostionCursor) {
        return paragraphWrapper.insertNewRun(xwpfRun, insertPostionCursor);
    }

    public void setAndUpdateRun(XWPFRun xwpfRun2, XWPFRun insertNewRun, int insertPostionCursor) {
        paragraphWrapper.setAndUpdateRun(xwpfRun2, insertNewRun, insertPostionCursor);

    }

    public XWPFRun createRun(XmlObject object, IRunBody paragraph) {
        return paragraphWrapper.createRun(object, paragraph);
    }

    public XWPFRun createRun(XWPFRun xwpfRun, IRunBody paragraph) {
        return paragraphWrapper.createRun(xwpfRun, paragraph);
    }

    @Override
    public void removeRun(int pos) {
        paragraphWrapper.removeRun(pos);
    }

}
