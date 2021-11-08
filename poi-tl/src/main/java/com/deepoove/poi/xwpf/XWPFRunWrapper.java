/*
 * Copyright 2014-2021 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.xwpf;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTxbxContent;

public class XWPFRunWrapper {

    public static final String XPATH_TXBX_TXBXCONTENT = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' \n"
            + "        declare namespace mc='http://schemas.openxmlformats.org/markup-compatibility/2006' .//mc:Choice/*/w:txbxContent";
    public static final String XPATH_TEXTBOX_TXBXCONTENT = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' \n"
            + "        declare namespace mc='http://schemas.openxmlformats.org/markup-compatibility/2006' .//mc:Fallback/*/w:txbxContent";
    public static final String XPATH_PICT_TEXTBOX_TXBXCONTENT = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' \n"
            + "        declare namespace v='urn:schemas-microsoft-com:vml' ./v:shape/v:textbox/w:txbxContent";

    private final XWPFRun run;
    private XWPFTextboxContent wpstxbx;
    private XWPFTextboxContent vtextbox;
    private XWPFTextboxContent shapetxbx;

    public XWPFRunWrapper(XWPFRun run) {
        this(run, true);
    }

    @SuppressWarnings("deprecation")
    public XWPFRunWrapper(XWPFRun run, boolean isParse) {
        this.run = run;
        if (!isParse) return;
        CTR r = run.getCTR();
        XmlObject[] xmlObjects = r.selectPath(XPATH_TXBX_TXBXCONTENT);
        if (xmlObjects != null && xmlObjects.length >= 1) {
            try {
                CTTxbxContent ctTxbxContent = CTTxbxContent.Factory.parse(xmlObjects[0].xmlText());
                wpstxbx = new XWPFTextboxContent(ctTxbxContent, run, run.getParagraph().getBody(), xmlObjects[0]);
            } catch (XmlException e) {
                // no-op
            }
        }
        xmlObjects = r.selectPath(XPATH_TEXTBOX_TXBXCONTENT);
        if (xmlObjects != null && xmlObjects.length >= 1) {
            try {
                CTTxbxContent ctTxbxContent = CTTxbxContent.Factory.parse(xmlObjects[0].xmlText());
                vtextbox = new XWPFTextboxContent(ctTxbxContent, run, run.getParagraph().getBody(), xmlObjects[0]);
            } catch (XmlException e) {
                // no-op
            }
        }
        org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture ctPicture = CollectionUtils
                .isNotEmpty(r.getPictList()) ? r.getPictArray(0) : null;
        if (null != ctPicture) {
            xmlObjects = ctPicture.selectPath(XPATH_PICT_TEXTBOX_TXBXCONTENT);
            if (xmlObjects != null && xmlObjects.length >= 1) {
                try {
                    CTTxbxContent ctTxbxContent = null;
                    if (xmlObjects[0] instanceof CTTxbxContent) {
                        ctTxbxContent = (CTTxbxContent) xmlObjects[0];
                    } else {
                        ctTxbxContent = CTTxbxContent.Factory.parse(xmlObjects[0].xmlText());
                    }
                    shapetxbx = new XWPFTextboxContent(ctTxbxContent, run, run.getParagraph().getBody(), xmlObjects[0]);
                } catch (XmlException e) {
                    // no-op
                }
            }
        }
    }

    public XWPFRun getRun() {
        return run;
    }

    public XWPFTextboxContent getWpstxbx() {
        return wpstxbx;
    }

    public XWPFTextboxContent getVtextbox() {
        return vtextbox;
    }

    public XWPFTextboxContent getShapetxbx() {
        return shapetxbx;
    }

}
