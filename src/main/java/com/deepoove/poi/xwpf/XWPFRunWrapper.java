package com.deepoove.poi.xwpf;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTxbxContent;

public class XWPFRunWrapper {

    public static final String XPATH_TXBX_TXBXCONTENT = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' \n"
            + "        declare namespace mc='http://schemas.openxmlformats.org/markup-compatibility/2006' .//mc:Choice/*/w:txbxContent";
    public static final String XPATH_TEXTBOX_TXBXCONTENT = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' \n"
            + "        declare namespace mc='http://schemas.openxmlformats.org/markup-compatibility/2006' .//mc:Fallback/*/w:txbxContent";

    private final XWPFRun run;
    private XWPFTextboxContent wpstxbx;
    private XWPFTextboxContent vtextbox;

    public XWPFRunWrapper(XWPFRun run) {
        this.run = run;

        CTR r = run.getCTR();
        XmlCursor cursor = r.newCursor();
        cursor.selectPath(XPATH_TXBX_TXBXCONTENT);
        while (cursor.toNextSelection()) {
            XmlObject o = cursor.getObject();
            if (o instanceof CTTxbxContent) {
                wpstxbx = new XWPFTextboxContent((CTTxbxContent)o, run, run.getParagraph().getBody());
                System.out.println(wpstxbx.getCTTxbxContent());
            }
        }
        cursor.dispose();
//        XmlObject[] xmlObjects = r.selectPath(XPATH_TXBX_TXBXCONTENT);
//        if (xmlObjects != null && xmlObjects.length >= 1) {
//            try {
//                CTTxbxContent ctTxbxContent = CTTxbxContent.Factory.parse(xmlObjects[0].xmlText());
//                wpstxbx = new XWPFTextboxContent(ctTxbxContent, run, run.getParagraph().getBody());
//                xmlObjects[0].set(ctTxbxContent);
//            } catch (XmlException e) {
//                // no-op
//            }
//        }
//        xmlObjects = r.selectPath(XPATH_TEXTBOX_TXBXCONTENT);
//        if (xmlObjects != null && xmlObjects.length >= 1) {
//            try {
//                CTTxbxContent ctTxbxContent = CTTxbxContent.Factory.parse(xmlObjects[0].xmlText());
//                vtextbox = new XWPFTextboxContent(ctTxbxContent, run, run.getParagraph().getBody());
//                xmlObjects[0].set(ctTxbxContent);
//            } catch (XmlException e) {
//                // no-op
//            }
//        }
    }

    public XWPFTextboxContent getWpstxbx() {
        return wpstxbx;
    }

    public XWPFTextboxContent getVtextbox() {
        return vtextbox;
    }

}
