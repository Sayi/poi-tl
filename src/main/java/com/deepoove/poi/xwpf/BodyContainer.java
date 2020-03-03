package com.deepoove.poi.xwpf;

import java.util.List;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import com.deepoove.poi.util.ParagraphUtils;

public interface BodyContainer {

    int getPosOfParagraphCTP(CTP startCtp);

    void removeBodyElement(int i);

    int getPosOfParagraph(XWPFParagraph startParagraph);

    List<IBodyElement> getBodyElements();

    XWPFParagraph insertNewParagraph(XmlCursor insertPostionCursor);
    
    default XWPFParagraph insertNewParagraph(XWPFRun run) {
        XmlCursor cursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
        return insertNewParagraph(cursor);
    }

    int getParaPos(XWPFParagraph insertNewParagraph);

    void setParagraph(XWPFParagraph iBodyElement, int paraPos);

    IBody getTarget();

    XWPFTable insertNewTbl(XmlCursor insertPostionCursor);

    int getTablePos(XWPFTable insertNewTbl);

    void setTable(int tablePos, XWPFTable iBodyElement);

    void updateBodyElements(IBodyElement insertNewParagraph, IBodyElement copy);

    XWPFTable insertNewTable(XWPFRun run, int row, int col);

    default void clearPlaceholder(XWPFRun run) {
        IRunBody parent = run.getParent();
        run.setText("", 0);
        if (parent instanceof XWPFParagraph) {
            String paragraphText = ParagraphUtils.trimLine((XWPFParagraph) parent);
            if ("".equals(paragraphText)) {
                int pos = getPosOfParagraph((XWPFParagraph) parent);
                removeBodyElement(pos);
            }
        }
    }

}
