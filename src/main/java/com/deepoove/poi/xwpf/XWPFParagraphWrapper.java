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

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.XWPFFieldRun;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.QNameSet;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTMarkupRange;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTPImpl;

import com.deepoove.poi.exception.ReflectionException;

/**
 * XWPFParagraph wrapper
 * 
 * @author Sayi
 */
public class XWPFParagraphWrapper {

    static final QName HYPER_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main",
            "hyperlink");
    static final QName FLDSIMPLE_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main",
            "fldSimple");
    static final QName R_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "r");
    static final QName BOOKMARK_START_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main",
            "bookmarkStart");
    static final QName BOOKMARK_END_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main",
            "bookmarkEnd");
    static final QName COMMENT_START_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main",
            "commentRangeStart");
    static final QName COMMENT_END_QNAME = new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main",
            "commentRangeEnd");

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

    public XWPFRun insertNewHyperLinkRun(XWPFRun run, String link) {
        if (StringUtils.isBlank(link)) throw new IllegalArgumentException("Hyperlink must not be Empty!");
        if (link.startsWith("anchor:")) {
            return insertNewAnchor(getPosOfRun(run), link.substring("anchor:".length()));
        }
        return insertNewHyperLinkRun(getPosOfRun(run), link);
    }

    public XWPFHyperlinkRun insertNewHyperLinkRun(int pos, String link) {
        if (pos >= 0 && pos <= paragraph.getRuns().size()) {
            CTHyperlink hyperlink = insertNewHyperlink(pos);
            // hyperlink.setAnchor(link.substring("anchor:".length()));
            PackageRelationship relationship = paragraph.getPart()
                    .getPackagePart()
                    .addExternalRelationship(link, XWPFRelation.HYPERLINK.getRelation());
            hyperlink.setId(relationship.getId());

            CTR ctr = hyperlink.addNewR();
            XWPFHyperlinkRun newRun = new XWPFHyperlinkRun(hyperlink, ctr, (IRunBody) paragraph);
            updateRunsAndIRuns(pos, newRun);
            return newRun;
        }
        return null;
    }

    public CTHyperlink insertNewHyperlink(int paramInt) {
        CTP ctp = paragraph.getCTP();
        synchronized (ctp.monitor()) {
            // check_orphaned();
            CTHyperlink localCTHyperlink = null;
            localCTHyperlink = (CTHyperlink) ((CTPImpl) ctp).get_store()
                    .insert_element_user(RUN_QNAME_SET, HYPER_QNAME, paramInt);
            return localCTHyperlink;
        }
    }

    public XWPFFieldRun insertNewAnchor(int pos, String anchorName) {
        if (pos >= 0 && pos <= paragraph.getRuns().size()) {
            XWPFFieldRun insertNewField = insertNewField(pos);
            insertNewField.setFieldInstruction("HYPERLINK \\l \"" + anchorName + "\"");
            return insertNewField;
        }
        return null;
    }

    public CTBookmark insertNewBookmark(XWPFRun run) {
        int pos = getPosOfRun(run);

        CTMarkupRange end = insertNewBookmarkEnd(pos + 1);
        CTBookmark start = insertNewBookmarkStart(pos);
        start.setId(BigInteger.valueOf(500 + new Random().nextInt(50000)));
        end.setId(start.getId());
        return start;
    }

    public CTMarkupRange insertNewBookmarkEnd(int paramInt) {
        CTP ctp = paragraph.getCTP();
        synchronized (ctp.monitor()) {
            // check_orphaned();
            CTMarkupRange local = null;
            local = (CTMarkupRange) ((CTPImpl) ctp).get_store()
                    .insert_element_user(RUN_QNAME_SET, BOOKMARK_END_QNAME, paramInt);
            return local;
        }
    }

    public CTBookmark insertNewBookmarkStart(int paramInt) {
        CTP ctp = paragraph.getCTP();
        synchronized (ctp.monitor()) {
            // check_orphaned();
            CTBookmark local = null;
            local = (CTBookmark) ((CTPImpl) ctp).get_store()
                    .insert_element_user(RUN_QNAME_SET, BOOKMARK_START_QNAME, paramInt);
            return local;
        }
    }

    public void insertNewCommentRangeStart(XWPFRun run, BigInteger cId) {
        int pos = getPosOfRun(run);
        CTP ctp = paragraph.getCTP();
        synchronized (ctp.monitor()) {
            // check_orphaned();
            CTMarkupRange mark = (CTMarkupRange) ((CTPImpl) ctp).get_store()
                    .insert_element_user(RUN_QNAME_SET, COMMENT_START_QNAME, pos);
            mark.setId(cId);
        }
    }

    public void insertNewCommentRangeEnd(XWPFRun run, BigInteger cId) {
        int pos = getPosOfRun(run);
        CTP ctp = paragraph.getCTP();
        synchronized (ctp.monitor()) {
            // check_orphaned();
            CTMarkupRange mark = (CTMarkupRange) ((CTPImpl) ctp).get_store()
                    .insert_element_user(RUN_QNAME_SET, COMMENT_END_QNAME, pos);
            mark.setId(cId);
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
            localCTSimpleField = (CTSimpleField) ((CTPImpl) ctp).get_store()
                    .insert_element_user(RUN_QNAME_SET, FLDSIMPLE_QNAME, paramInt);
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

    private void updateRunsAndIRuns(int pos, XWPFRun newRun) {
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

    private int getPosOfRun(XWPFRun run) {
        int pos = -1;
        List<XWPFRun> runs = paragraph.getRuns();
        for (int i = 0; i < runs.size(); i++) {
            if (run == runs.get(i)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    @SuppressWarnings("unchecked")
    private List<XWPFRun> getRuns() {
        try {
            Field runsField = XWPFParagraph.class.getDeclaredField("runs");
            runsField.setAccessible(true);
            return (List<XWPFRun>) runsField.get(paragraph);
        } catch (Exception e) {
            throw new ReflectionException("runs", XWPFParagraph.class, e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<IRunElement> getIRuns() {
        try {
            Field runsField = XWPFParagraph.class.getDeclaredField("iruns");
            runsField.setAccessible(true);
            return (List<IRunElement>) runsField.get(paragraph);
        } catch (Exception e) {
            throw new ReflectionException("iruns", XWPFParagraph.class, e);
        }
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

    public XWPFParagraph getParagraph() {
        return paragraph;
    }

}
