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
package com.deepoove.poi.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTEmpty;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTRImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.xwpf.RunBodyContext;

/**
 * Running Run algorithm
 * 
 * @author Sayi
 * @version
 */
public class RunningRunBody {

    private static final Logger LOG = LoggerFactory.getLogger(RunningRunBody.class);

    static QNameSet qname = QNameSet
            .forArray(new QName[] { new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "br"),
                    new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "t"),
                    new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "cr"),
                    new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "tab") });

    private RunBodyContext runBodyContext;
    private List<XWPFRun> runs;

    List<Pair<RunEdge, RunEdge>> pairs = new ArrayList<>();

    public RunningRunBody(RunBodyContext context, Pattern pattern) {
        this.runBodyContext = context;
        this.runs = context.getRuns();
        if (null == runs || runs.isEmpty()) return;

        Matcher matcher = pattern.matcher(getText(context));
        if (matcher.find()) {
            refactorBody();
        }

        buildRunEdge(pattern);
    }

    private String getText(RunBodyContext context) {
        StringBuilder out = new StringBuilder(64);
        for (XWPFRun run : runs) {
            out.append(run.text());
        }
        return out.toString();
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

            String startText = runs.get(startRunPos).text();
            String endText = runs.get(endRunPos).text();

            if (endOffset + 1 >= endText.length()) {
                // delete the redundant end Run directly
                if (startRunPos != endRunPos) runBodyContext.removeRun(endRunPos);
            } else {
                // split end run, set extra in a run
                String extra = endText.substring(endOffset + 1, endText.length());
                if (startRunPos == endRunPos) {
                    // create run and set extra content
                    XWPFRun extraRun = runBodyContext.insertNewRunAfter(endRunPos);
                    StyleUtils.styleRun(extraRun, runs.get(endRunPos));
                    buildExtra(extra, extraRun);
                } else {
                    // Set the extra content to the redundant end run
                    XWPFRun extraRun = runs.get(endRunPos);
                    buildExtra(extra, extraRun);
                }
            }

            // remove extra run
            for (int m = endRunPos - 1; m > startRunPos; m--) {
                runBodyContext.removeRun(m);
            }

            if (startOffset <= 0) {
                // set the start Run directly
                XWPFRun templateRun = runs.get(startRunPos);
                templateRun.setText(startEdge.getTag(), 0);
                templateRuns.add(runs.get(startRunPos));
            } else {
                // split start run, set extra in a run
                String extra = startText.substring(0, startOffset);
                XWPFRun extraRun = runs.get(startRunPos);
                buildExtra(extra, extraRun);

                XWPFRun templateRun = runBodyContext.insertNewRunAfter(startRunPos);
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

    private void refactorBody() {
        for (int i = runs.size() - 1; i >= 0; i--) {
            XWPFRun xwpfRun = runs.get(i);
            CTR ctr = xwpfRun.getCTR();
            CTRImpl ctrimpl = (CTRImpl) ctr;
            int sizeOfBrArray = ctr.sizeOfBrArray();
            int sizeOfCrArray = ctr.sizeOfCrArray();
            int sizeOfTabArray = ctr.sizeOfTabArray();
            if ((sizeOfBrArray + sizeOfCrArray + sizeOfTabArray) > 0) {
                synchronized (ctrimpl.monitor()) {
                    // ctrimpl.check_orphaned();
                    List<? extends XmlObject> localArrayList = new ArrayList<XmlObject>();
                    ctrimpl.get_store().find_all_element_users(qname, localArrayList);
                    int size = localArrayList.size();
                    for (int j = size - 1; j >= 0; j--) {
                        Object obj = localArrayList.get(j);
                        if (obj instanceof CTEmpty) {
                            XWPFRun insertNewRun = runBodyContext.insertNewRunAfter(i);
                            String tagName = ((CTEmpty) obj).getDomNode().getNodeName();
                            if ("w:br".equals(tagName) || "br".equals(tagName)) {
                                insertNewRun.addBreak();
                            }
                            if ("w:cr".equals(tagName) || "cr".equals(tagName)) {
                                insertNewRun.addCarriageReturn();
                            }
                            if ("w:tab".equals(tagName) || "tab".equals(tagName)) {
                                insertNewRun.addTab();
                            }
                        } else if (obj instanceof CTBr) {
                            XWPFRun insertNewRun = runBodyContext.insertNewRunAfter(i);
                            CTBr addNewBr = insertNewRun.getCTR().addNewBr();
                            if (null != ((CTBr) obj).getType()) addNewBr.setType(((CTBr) obj).getType());
                            if (null != ((CTBr) obj).getClear()) addNewBr.setClear(((CTBr) obj).getClear());
                        } else if (obj instanceof CTText) {
                            XWPFRun insertNewRun = runBodyContext.insertNewRunAfter(i);
                            StyleUtils.styleRun(insertNewRun, xwpfRun);
                            insertNewRun.setText(((CTText) obj).getStringValue());
                        }
                    }
                    runBodyContext.removeRun(i);
                }
            }
        }
        this.runs = runBodyContext.getRuns();
    }

    private void buildRunEdge(Pattern pattern) {
        // find all templates
        Matcher matcher = pattern.matcher(getText(runBodyContext));
        while (matcher.find()) {
            pairs.add(ImmutablePair.of(new RunEdge(matcher.start(), matcher.group()),
                    new RunEdge(matcher.end(), matcher.group())));
        }
        if (pairs.isEmpty()) return;

        boolean endflag = false;
        int size = runs.size();
        int cursor = 0;
        int pos = 0;

        // find the run where all templates are located
        Pair<RunEdge, RunEdge> pair = pairs.get(pos);
        RunEdge startEdge = pair.getLeft();
        RunEdge endEdge = pair.getRight();
        int start = startEdge.getAllEdge();
        int end = endEdge.getAllEdge();
        for (int i = 0; i < size; i++) {
            XWPFRun run = runs.get(i);
            String text = run.text();
            // empty run
            if (null == text) {
                LOG.warn("found the empty text run,may be produce bug:" + run);
                cursor += run.toString().length();
                continue;
            }
            LOG.debug(text);
            // The starting position is not enough, the cursor points to the next run
            if (text.length() + cursor < start) {
                cursor += text.length();
                continue;
            }
            // index text
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

                    if (pos == pairs.size() - 1) {
                        endflag = true;
                        break;
                    }

                    // Continue to calculate the next template
                    pair = pairs.get(++pos);
                    startEdge = pair.getLeft();
                    endEdge = pair.getRight();
                    start = startEdge.getAllEdge();
                    end = endEdge.getAllEdge();
                }
            }
            if (endflag) break;
            // the cursor points to the next run
            cursor += text.length();
        }

        loggerInfo();
    }

    public void loggerInfo() {
        for (Pair<RunEdge, RunEdge> runEdges : pairs) {
            LOG.debug("[Start]:" + runEdges.getLeft().toString());
            LOG.debug("[End]:" + runEdges.getRight().toString());
        }
    }
}
