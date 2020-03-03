package com.deepoove.poi.xwpf;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import com.deepoove.poi.template.IterableTemplate;

public class BodyContainerFactory {

    public static BodyContainer getBodyContainer(IBody body) {
        BodyContainer bodyContainer = null;
        if (body instanceof XWPFTableCell) {
            bodyContainer = new CellBodyContainer((XWPFTableCell) body);
        } else if (body instanceof XWPFHeaderFooter) {
            bodyContainer = new HeaderFooterBodyContainer((XWPFHeaderFooter) body);
        } else {
            bodyContainer = new DocumentBodyContainer((NiceXWPFDocument) body);
        }
        return bodyContainer;
    }

    public static BodyContainer getBodyContainer(XWPFRun run) {
        return getBodyContainer(((XWPFParagraph) run.getParent()).getBody());
    }

    public static BodyContainer getBodyContainer(IterableTemplate iterableTemplate) {
        return getBodyContainer(iterableTemplate.getStartRun());
    }

}
