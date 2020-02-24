package com.deepoove.poi.xwpf;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import com.deepoove.poi.template.IterableTemplate;

public class ContainerFactory {

    public static Container getContainer(IBody body) {
        Container container = null;
        if (body instanceof XWPFTableCell) {
            container = new CellContainer((XWPFTableCell) body);
        } else if (body instanceof XWPFHeaderFooter) {
            container = new HeaderFooterContainer((XWPFHeaderFooter) body);
        } else {
            container = new DocContainer((NiceXWPFDocument) body);
        }
        return container;
    }

    public static Container getContainer(XWPFRun run) {
        return getContainer(((XWPFParagraph) run.getParent()).getBody());
    }

    public static Container getContainer(IterableTemplate iterableTemplate) {
        return getContainer(iterableTemplate.getStartMark().getRun());
    }

}
