package com.deepoove.poi.xwpf;

import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlObject;

/**
 * Paragraph is parent
 */
public interface ParagraphContext extends ParentContext {

    XWPFParagraph getParagraph();

    XWPFRun insertNewRun(XWPFRun xwpfRun, int insertPostionCursor);

    void setAndUpdateRun(XWPFRun xwpfRun2, XWPFRun insertNewRun, int insertPostionCursor);

    XWPFRun createRun(XmlObject object, IRunBody paragraph);

    XWPFRun createRun(XWPFRun xwpfRun, IRunBody paragraph);

    void removeRun(int pos);

}
