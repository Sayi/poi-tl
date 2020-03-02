/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.xwpf;

import java.lang.reflect.Field;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.XWPFFieldRun;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.QNameSet;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTPImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XWPFParagraph类的增强
 * 
 * @author Sayi
 * @version
 */
public class XWPFParagraphWrapper {

    private static Logger logger = LoggerFactory.getLogger(XWPFParagraphWrapper.class);
    static final QName HYPER_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main",
            "hyperlink");
    static final QName FLDSIMPLE_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main",
            "fldSimple");
    static final QName R_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "r");

    static final QNameSet RUN_QNAME_SET = QNameSet.forArray(new QName[] { HYPER_QNAME, FLDSIMPLE_QNAME, R_QNAME });

    XWPFParagraph paragraph;

    public XWPFParagraphWrapper(XWPFParagraph paragraph) {
        this.paragraph = paragraph;
    }

    public XWPFHyperlinkRun createHyperLinkRun(String link) {
        PackageRelationship relationship = paragraph.getDocument().getPackagePart().addExternalRelationship(link,
                XWPFRelation.HYPERLINK.getRelation());
        CTHyperlink hyperlink = paragraph.getCTP().addNewHyperlink();
        hyperlink.setId(relationship.getId());
        CTR ctr = hyperlink.addNewR();
        XWPFHyperlinkRun xwpfRun = new XWPFHyperlinkRun(hyperlink, ctr, (IRunBody) paragraph);
        getRuns().add(xwpfRun);
        getIRuns().add(xwpfRun);
        return xwpfRun;
    }

    /**
     * 插入超链接
     * 
     * @param pos
     *            位置
     * @param link
     *            链接
     * @return XWPFRun
     */
    public XWPFRun insertNewHyperLinkRun(int pos, String link) {
        if (pos >= 0 && pos <= paragraph.getRuns().size()) {
            PackageRelationship relationship = paragraph.getDocument().getPackagePart().addExternalRelationship(link,
                    XWPFRelation.HYPERLINK.getRelation());
            CTHyperlink hyperlink = insertNewHyperlink(pos);
            hyperlink.setId(relationship.getId());
            CTR ctr = hyperlink.addNewR();
            XWPFHyperlinkRun newRun = new XWPFHyperlinkRun(hyperlink, ctr, (IRunBody) paragraph);

            List<IRunElement> iruns = getIRuns();
            List<XWPFRun> runs = getRuns();
            // To update the iruns, find where we're going
            // in the normal runs, and go in there
            int iPos = iruns.size();
            if (pos < runs.size()) {
                XWPFRun oldAtPos = runs.get(pos);
                int oldAt = iruns.indexOf(oldAtPos);
                if (oldAt != -1) {
                    iPos = oldAt;
                }
            }
            iruns.add(iPos, newRun);

            // Runs itself is easy to update
            runs.add(pos, newRun);

            return newRun;
        }

        return null;
    }

    private CTHyperlink insertNewHyperlink(int paramInt) {
        // do not insert hyperlink directly #issue 331
        // CTHyperlink hyperlink = paragraph.getCTP().insertNewHyperlink(rPos);

        // the correct insert hyperlink as our run/irun list contains
        // all runs
        CTPImpl ctpImpl = (CTPImpl) paragraph.getCTP();
        // ctpImpl.insertNewR(arg0)
        // ctpImpl.insertNewHyperlink(arg0)
        // ctpImpl.insertNewFldSimple(arg0)
        synchronized (ctpImpl.monitor()) {
            // check_orphaned();
            CTHyperlink localCTHyperlink = null;
            localCTHyperlink = (CTHyperlink) ctpImpl.get_store().insert_element_user(RUN_QNAME_SET, HYPER_QNAME,
                    paramInt);
            return localCTHyperlink;
        }
    }

    public CTR insertNewR(int pos) {
        CTP ctp = paragraph.getCTP();
        synchronized (ctp.monitor()) {
            // check_orphaned();
            CTR localCTR = null;
            localCTR = (CTR) ((CTPImpl) ctp).get_store().insert_element_user(RUN_QNAME_SET, R_QNAME, pos);
            return localCTR;
        }
    }

    public XWPFRun insertNewRun(int pos) {
        if (pos >= 0 && pos <= paragraph.getRuns().size()) {

            CTR ctRun = this.insertNewR(pos);
            XWPFRun newRun = new XWPFRun(ctRun, (IRunBody) this);

            // To update the iruns, find where we're going
            // in the normal runs, and go in there
            List<IRunElement> iruns = getIRuns();
            List<XWPFRun> runs = getRuns();
            int iPos = iruns.size();
            if (pos < runs.size()) {
                XWPFRun oldAtPos = runs.get(pos);
                int oldAt = iruns.indexOf(oldAtPos);
                if (oldAt != -1) {
                    iPos = oldAt;
                }
            }
            iruns.add(iPos, newRun);

            // Runs itself is easy to update
            runs.add(pos, newRun);

            return newRun;
        }

        return null;
    }

    public CTSimpleField insertNewFldSimple(int paramInt) {
        CTP ctp = paragraph.getCTP();
        synchronized (ctp.monitor()) {
            // check_orphaned();
            CTSimpleField localCTSimpleField = null;
            localCTSimpleField = (CTSimpleField) ((CTPImpl) ctp).get_store().insert_element_user(RUN_QNAME_SET,
                    FLDSIMPLE_QNAME, paramInt);
            return localCTSimpleField;
        }
    }

    public XWPFFieldRun insertNewField(int pos) {
        if (pos >= 0 && pos <= paragraph.getRuns().size()) {

            CTSimpleField ctSimpleField = this.insertNewFldSimple(pos);
            CTR addNewR = ctSimpleField.addNewR();
            XWPFFieldRun newRun = new XWPFFieldRun(ctSimpleField, addNewR, (IRunBody) paragraph);

            // To update the iruns, find where we're going
            // in the normal runs, and go in there
            List<IRunElement> iruns = getIRuns();
            List<XWPFRun> runs = getRuns();
            int iPos = iruns.size();
            if (pos < runs.size()) {
                XWPFRun oldAtPos = runs.get(pos);
                int oldAt = iruns.indexOf(oldAtPos);
                if (oldAt != -1) {
                    iPos = oldAt;
                }
            }
            iruns.add(iPos, newRun);

            // Runs itself is easy to update
            runs.add(pos, newRun);

            return newRun;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private List<XWPFRun> getRuns() {
        try {
            Field runsField = XWPFParagraph.class.getDeclaredField("runs");
            runsField.setAccessible(true);
            return (List<XWPFRun>) runsField.get(paragraph);
        } catch (Exception e) {
            logger.error("Cannot get XWPFParagraph'runs", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<IRunElement> getIRuns() {
        try {
            Field runsField = XWPFParagraph.class.getDeclaredField("iruns");
            runsField.setAccessible(true);
            return (List<IRunElement>) runsField.get(paragraph);
        } catch (Exception e) {
            logger.error("Cannot get XWPFParagraph'iRuns", e);
        }
        return null;
    }

    public void setAndUpdateRun(XWPFRun xwpfRun, XWPFRun source, int insertPostionCursor) {
        // body
        // maybe need find correct position:rPos;
        int rPos = 0;
        List<XWPFRun> runs = getRuns();
        if (insertPostionCursor >= 0 && insertPostionCursor <= runs.size()) {
            // calculate the correct pos as our run/irun list contains
            // hyperlinks
            // and fields so it is different to the paragraph R array.
            for (int i = 0; i < insertPostionCursor; i++) {
                XWPFRun currRun = runs.get(i);
                if (!(currRun instanceof XWPFHyperlinkRun || currRun instanceof XWPFFieldRun)) {
                    rPos++;
                }
            }
        }
        paragraph.getCTP().setRArray(rPos, xwpfRun.getCTR());

        // runs
        for (int i = 0; i < runs.size(); i++) {
            XWPFRun ele = runs.get(i);
            if (ele == source) {
                runs.set(i, xwpfRun);
            }
        }

        // iruns
        List<IRunElement> iruns = getIRuns();
        for (int i = 0; i < iruns.size(); i++) {
            IRunElement ele = iruns.get(i);
            if (ele == source) {
                iruns.set(i, xwpfRun);
            }
        }
    }

}
