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
package com.deepoove.poi;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
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

    XWPFParagraph paragraph;

    public XWPFParagraphWrapper(XWPFParagraph paragraph) {
        this.paragraph = paragraph;
    }

    public XWPFHyperlinkRun createHyperLinkRun(String link) {
        PackageRelationship relationship = paragraph.getDocument().getPackagePart()
                .addExternalRelationship(link, XWPFRelation.HYPERLINK.getRelation());
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
            PackageRelationship relationship = paragraph.getDocument().getPackagePart()
                    .addExternalRelationship(link, XWPFRelation.HYPERLINK.getRelation());
            CTHyperlink hyperlink = paragraph.getCTP().insertNewHyperlink(pos);
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

}
