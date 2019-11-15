/*
 * Copyright 2014-2019 the original author or authors.
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
package com.deepoove.poi.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.QNameSet;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTEmpty;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTRImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.util.StyleUtils;

public class RunningRunParagraph {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunningRunParagraph.class);

    static QNameSet qname = QNameSet.forArray(new QName[] {
            new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "br"),
            new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "t"),
            new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "cr") });

    private XWPFParagraph paragraph;
    private List<XWPFRun> runs;

    List<Pair<RunEdge, RunEdge>> pairs = new ArrayList<Pair<RunEdge, RunEdge>>();

    public RunningRunParagraph(XWPFParagraph paragraph, Pattern pattern) {
        this.paragraph = paragraph;
        this.runs = paragraph.getRuns();
        if (null == runs || runs.isEmpty()) return;

        Matcher matcher = pattern.matcher(paragraph.getText());
        if (matcher.find()) {
            refactorParagraph();
        }

        buildRunEdge(pattern);
    }

    public List<XWPFRun> refactorRun() {
        if (pairs.isEmpty()) return null;
        List<XWPFRun> templateRuns = new ArrayList<XWPFRun>();
        int size = pairs.size();
        Pair<RunEdge, RunEdge> runEdgePair;
        for (int n = size - 1; n >= 0; n--) {
            runEdgePair = pairs.get(n);
            RunEdge startEdge = runEdgePair.getLeft();
            RunEdge endEdge = runEdgePair.getRight();
            int startRunPos = startEdge.getRunPos();
            int endRunPos = endEdge.getRunPos();
            int startOffset = startEdge.getRunEdge();
            int endOffset = endEdge.getRunEdge();

            // String startText = runs.get(startRunPos).getText(0);
            // String endText = runs.get(endRunPos).getText(0);
            String startText = runs.get(startRunPos).text();
            String endText = runs.get(endRunPos).text();
            if (endOffset + 1 >= endText.length()) {
                // end run 无需split，若不是start run，直接remove
                if (startRunPos != endRunPos) paragraph.removeRun(endRunPos);
            } else {
                // split end run, set extra in a run
                String extra = endText.substring(endOffset + 1, endText.length());
                if (startRunPos == endRunPos) {
                    XWPFRun extraRun = paragraph.insertNewRun(endRunPos + 1);
                    StyleUtils.styleRun(extraRun, runs.get(endRunPos));
                    buildExtra(extra, extraRun);
                } else {
                    XWPFRun extraRun = runs.get(endRunPos);
                    buildExtra(extra, extraRun);
                }
            }

            // remove extra run
            for (int m = endRunPos - 1; m > startRunPos; m--) {
                paragraph.removeRun(m);
            }

            if (startOffset <= 0) {
                // start run 无需split
                XWPFRun templateRun = runs.get(startRunPos);
                templateRun.setText(startEdge.getTag(), 0);
                templateRuns.add(runs.get(startRunPos));
            } else {
                // split start run, set extra in a run
                String extra = startText.substring(0, startOffset);
                XWPFRun extraRun = runs.get(startRunPos);
                buildExtra(extra, extraRun);// TODO

                XWPFRun templateRun = paragraph.insertNewRun(startRunPos + 1);
                StyleUtils.styleRun(templateRun, extraRun);
                templateRun.setText(startEdge.getTag(), 0);
                templateRuns.add(runs.get(startRunPos + 1));
            }

        }
        return templateRuns;
    }

    private void buildExtra(String extra, XWPFRun extraRun) {
        extraRun.setText(extra, 0);
    }

    private void refactorParagraph() {
        for (int i = runs.size() - 1; i >= 0; i--) {
            XWPFRun xwpfRun = runs.get(i);
            CTR ctr = xwpfRun.getCTR();
            CTRImpl ctrimpl = (CTRImpl) ctr;
            int sizeOfBrArray = ctr.sizeOfBrArray();
            int sizeOfCrArray = ctr.sizeOfCrArray();
            if ((sizeOfBrArray + sizeOfCrArray) > 0) {
                synchronized (ctrimpl.monitor()) {
                    // ctrimpl.check_orphaned();
                    ArrayList<Object> localArrayList = new ArrayList<Object>();
                    ctrimpl.get_store().find_all_element_users(qname, localArrayList);
                    int size = localArrayList.size();
                    for (int j = size - 1; j >= 0; j--) {
                        Object obj = localArrayList.get(j);
                        if (obj instanceof CTEmpty) {
                            XWPFRun insertNewRun = paragraph.insertNewRun(i + 1);
                            String tagName = ((CTEmpty) obj).getDomNode().getNodeName();
                            if ("w:br".equals(tagName) || "br".equals(tagName)) {
                                insertNewRun.addBreak();
                            }
                            if ("w:cr".equals(tagName) || "cr".equals(tagName)) {
                                insertNewRun.addCarriageReturn();
                            }
                        } else if (obj instanceof CTBr) {
                            XWPFRun insertNewRun = paragraph.insertNewRun(i + 1);
                            CTBr addNewBr = insertNewRun.getCTR().addNewBr();
                            if (null != ((CTBr) obj).getType()) addNewBr.setType(((CTBr) obj).getType());
                            if (null != ((CTBr) obj).getClear()) addNewBr.setClear(((CTBr) obj).getClear());
                        } else if (obj instanceof CTText) {
                            XWPFRun insertNewRun = paragraph.insertNewRun(i + 1);
                            StyleUtils.styleRun(insertNewRun, xwpfRun);
                            insertNewRun.setText(((CTText) obj).getStringValue());
                        }
                    }
                    paragraph.removeRun(i);
                    this.runs = paragraph.getRuns();
                }
            }
        }
    }

    private void buildRunEdge(Pattern pattern) {
        Matcher matcher = pattern.matcher(paragraph.getText());
        while (matcher.find()) {
            pairs.add(ImmutablePair.of(new RunEdge(matcher.start(), matcher.group()),
                    new RunEdge(matcher.end(), matcher.group())));
        }
        if (pairs.isEmpty()) return;

        int size = runs.size();
        int cursor = 0;// 游标

        int pos = 0;
        // 计算第0个模板
        Pair<RunEdge, RunEdge> pair = pairs.get(pos);
        RunEdge startEdge = pair.getLeft();
        RunEdge endEdge = pair.getRight();
        int start = startEdge.getAllEdge();
        int end = endEdge.getAllEdge();
        for (int i = 0; i < size; i++) {
            XWPFRun run = runs.get(i);
            // String text = run.getText(0);
            String text = run.text();
            // 游标略过empty run
            if (null == text) {
                LOGGER.warn("found the empty text run,may be produce bug:" + run);
                cursor += run.toString().length();
                continue;
            }
            LOGGER.debug(text);
            // 起始位置不足，游标指向下一run
            if (text.length() + cursor < start) {
                cursor += text.length();
                // if (null != run.getCTR().getBrArray()){
                // cursor += run.getCTR().getBrArray().length;
                // }
                continue;
            }
            // 索引text
            for (int offset = 0; offset < text.length(); offset++) {
                if (cursor + offset == start) {
                    startEdge.setRunPos(i);
                    startEdge.setRunEdge(offset);
                    startEdge.setText(text);
                }
                if (cursor + offset == end - 1) {
                    endEdge.setRunPos(i);
                    endEdge.setRunEdge(offset);
                    endEdge.setText(text);

                    if (pos == pairs.size() - 1) break;

                    // 计算下一个模板
                    pair = pairs.get(++pos);
                    startEdge = pair.getLeft();
                    endEdge = pair.getRight();
                    start = startEdge.getAllEdge();
                    end = endEdge.getAllEdge();
                }
            }
            // 游标指向下一run
            cursor += text.length();
        }

        for (Pair<RunEdge, RunEdge> runEdges : pairs) {
            LOGGER.debug("[Start]:" + runEdges.getLeft().toString());
            LOGGER.debug("[End]:" + runEdges.getRight().toString());
        }
    }

}
