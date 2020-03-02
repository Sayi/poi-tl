/*
 * Copyright 2014-2020 the original author or authors.
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
import java.util.ArrayList;
import java.util.Iterator;
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
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTPImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XWPFParagraph 包装器
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

        // clean structure when have duplicate XWPFHyperlinkRun
        // because wrong structure lead to wrong position
        List<XWPFRun> runs = getRuns();
        List<IRunElement> iRuns = getIRuns();
        List<CTHyperlink> caches = new ArrayList<>();
        Iterator<XWPFRun> iterator = runs.iterator();
        while (iterator.hasNext()) {
            XWPFRun next = iterator.next();
            if (next instanceof XWPFHyperlinkRun) {
                CTHyperlink ctHyperlink = ((XWPFHyperlinkRun) next).getCTHyperlink();
                if (caches.contains(ctHyperlink)) {
                    iterator.remove();
                    iRuns.remove(next);
                } else {
                    caches.add(ctHyperlink);
                }
            }
        }
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

    public XWPFRun insertNewHyperLinkRun(int pos, String link) {
        if (pos >= 0 && pos <= paragraph.getRuns().size()) {
            PackageRelationship relationship = paragraph.getDocument().getPackagePart().addExternalRelationship(link,
                    XWPFRelation.HYPERLINK.getRelation());
            CTHyperlink hyperlink = insertNewHyperlink(pos);
            hyperlink.setId(relationship.getId());
            CTR ctr = hyperlink.addNewR();
            XWPFHyperlinkRun newRun = new XWPFHyperlinkRun(hyperlink, ctr, (IRunBody) paragraph);

            updateRunsAndIRuns(pos, newRun);

            return newRun;
        }

        return null;
    }

    public CTHyperlink insertNewHyperlink(int paramInt) {
        // org.apache.xmlbeans.impl.values.TypeStore.insert_element_user(QName,
        // int)
        // Note that if there are no existing elements of the given
        // name, you may need to call back to discover the proper
        // ordering to use to insert the first one. Otherwise,
        // it should be inserted adjacent to existing elements with
        // the same name.

        // do not insert hyperlink directly #issue 331
        // CTHyperlink hyperlink = paragraph.getCTP().insertNewHyperlink(rPos);

        CTP ctp = paragraph.getCTP();
        synchronized (ctp.monitor()) {
            // check_orphaned();
            CTHyperlink localCTHyperlink = null;
            localCTHyperlink = (CTHyperlink) ((CTPImpl) ctp).get_store().insert_element_user(RUN_QNAME_SET, HYPER_QNAME,
                    paramInt);
            return localCTHyperlink;
        }
    }

    public CTR insertNewR(int paramInt) {
        CTP ctp = paragraph.getCTP();
        synchronized (ctp.monitor()) {
            // check_orphaned();
            CTR localCTR = null;
            localCTR = (CTR) ((CTPImpl) ctp).get_store().insert_element_user(RUN_QNAME_SET, R_QNAME, paramInt);
            return localCTR;
        }
    }

    public XWPFRun insertNewRun(int pos) {
        if (pos >= 0 && pos <= paragraph.getRuns().size()) {

            CTR ctRun = this.insertNewR(pos);
            XWPFRun newRun = new XWPFRun(ctRun, (IRunBody) paragraph);

            updateRunsAndIRuns(pos, newRun);

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
            updateRunsAndIRuns(pos, newRun);

            return newRun;
        }

        return null;
    }

    public void updateRunsAndIRuns(int pos, XWPFRun newRun) {
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
                if (xwpfRun instanceof XWPFHyperlinkRun) {
                    if (currRun instanceof XWPFHyperlinkRun) {
                        rPos++;
                    }
                } else if (xwpfRun instanceof XWPFFieldRun) {
                    if (currRun instanceof XWPFFieldRun) {
                        rPos++;
                    }
                } else {
                    if (!(currRun instanceof XWPFHyperlinkRun || currRun instanceof XWPFFieldRun)) {
                        rPos++;
                    }
                }
            }
        }

        if (xwpfRun instanceof XWPFHyperlinkRun) {
            paragraph.getCTP().setHyperlinkArray(rPos, ((XWPFHyperlinkRun) xwpfRun).getCTHyperlink());
        } else if (xwpfRun instanceof XWPFFieldRun) {
            paragraph.getCTP().setFldSimpleArray(rPos, ((XWPFFieldRun) xwpfRun).getCTField());
        } else {
            paragraph.getCTP().setRArray(rPos, xwpfRun.getCTR());
        }

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

    public XWPFRun insertNewRun(XWPFRun xwpfRun, int insertPostionCursor) {
        if (xwpfRun instanceof XWPFHyperlinkRun) {
            return insertNewHyperLinkRun(insertPostionCursor, "");
        } else if (xwpfRun instanceof XWPFFieldRun) {
            return insertNewField(insertPostionCursor);
        } else {
            return insertNewRun(insertPostionCursor);
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

    public boolean removeRun(int pos) {
        List<IRunElement> iruns = getIRuns();
        List<XWPFRun> runs = getRuns();
        if (pos >= 0 && pos < runs.size()) {
            // Remove the run from our high level lists
            XWPFRun run = runs.get(pos);
            runs.remove(pos);
            iruns.remove(run);
            // Remove the run from the low-level XML
            // calculate the correct pos as our run/irun list contains
            // hyperlinks and fields so is different to the paragraph R array.
            int rPos = 0;
            for (int i = 0; i < pos; i++) {
                XWPFRun currRun = runs.get(i);
                if (run instanceof XWPFHyperlinkRun) {
                    if (currRun instanceof XWPFHyperlinkRun) {
                        rPos++;
                    }
                } else if (run instanceof XWPFFieldRun) {
                    if (currRun instanceof XWPFFieldRun) {
                        rPos++;
                    }
                } else {
                    if (!(currRun instanceof XWPFHyperlinkRun || currRun instanceof XWPFFieldRun)) {
                        rPos++;
                    }
                }
            }

            if (run instanceof XWPFHyperlinkRun) {
                paragraph.getCTP().removeHyperlink(rPos);
            } else if (run instanceof XWPFFieldRun) {
                paragraph.getCTP().removeFldSimple(rPos);
            } else {
                paragraph.getCTP().removeR(rPos);
            }
            return true;
        }
        return false;
    }

}
