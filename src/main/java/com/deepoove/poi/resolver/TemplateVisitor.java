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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.template.ElementTemplate;

/**
 * 模板解析器
 * 
 * @author Sayi
 * @version 1.4.0
 */
public class TemplateVisitor extends AbstractVisitor {
    private static Logger logger = LoggerFactory.getLogger(TemplateVisitor.class);

    private List<ElementTemplate> eleTemplates;

    public TemplateVisitor(Configure config) {
        super(config);
    }

    @Override
    public List<ElementTemplate> visitDocument(XWPFDocument doc) {
        // reset parsed result
        this.eleTemplates = new ArrayList<>();
        if (null == doc) return this.eleTemplates;
        logger.info("Visit the document start...");
        visitParagraphs(doc.getParagraphs());
        visitTables(doc.getTables());
        visitHeaders(doc.getHeaderList());
        visitFooters(doc.getFooterList());
        logger.info("Visit the document end, resolve and create {} ElementTemplates.",
                this.eleTemplates.size());
        return eleTemplates;
    }

    void visitHeaders(List<XWPFHeader> headers) {
        if (null == headers) return;
        headers.forEach(header -> {
            visitParagraphs(header.getParagraphs());
            visitTables(header.getTables());
        });
    }

    void visitFooters(List<XWPFFooter> footers) {
        if (null == footers) return;
        footers.forEach(footer -> {
            visitParagraphs(footer.getParagraphs());
            visitTables(footer.getTables());
        });
    }

    void visitParagraphs(List<XWPFParagraph> paragraphs) {
        if (null == paragraphs) return;
        paragraphs.forEach(paragraph -> visitParagraph(paragraph));
    }

    void visitTables(List<XWPFTable> tables) {
        if (null == tables) return;
        tables.forEach(tb -> visitTable(tb));
    }

    void visitTable(XWPFTable table) {
        if (null == table) return;
        List<XWPFTableRow> rows = table.getRows();
        visitTableRows(rows);
    }

    void visitTableRows(List<XWPFTableRow> rows) {
        if (null == rows) return;
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            if (null == cells) continue;
            cells.forEach(cell -> {
                visitParagraphs(cell.getParagraphs());
                visitTables(cell.getTables());
            });
        }
    }

    void visitParagraph(XWPFParagraph paragraph) {
        if (null == paragraph) return;
        RunningRunParagraph runningRun = new RunningRunParagraph(paragraph, templatePattern);
        List<XWPFRun> refactorRuns = runningRun.refactorRun();
        if (null == refactorRuns) return;
        refactorRuns.forEach(run -> visitRun(run));
    }

    void visitRun(XWPFRun run) {
        String text = null;
        if (null == run || StringUtils.isBlank(text = run.getText(0))) return;
        ElementTemplate elementTemplate = parseTemplateFactory(text, run);
        if (null != elementTemplate) eleTemplates.add(elementTemplate);
    }

    private <T> ElementTemplate parseTemplateFactory(String text, T obj) {
        logger.debug("Resolve where text: {}, and create ElementTemplate", text);
        // temp ,future need to word analyze
        if (templatePattern.matcher(text).matches()) {
            String tag = gramerPattern.matcher(text).replaceAll("").trim();
            if (obj.getClass() == XWPFRun.class) {
                return TemplateFactory.createRunTemplate(tag, config, (XWPFRun) obj);
            } else if (obj.getClass() == XWPFTableCell.class)
                // return CellTemplate.create(symbol, tagName, (XWPFTableCell)
                // obj);
                return null;
        }
        return null;
    }

}
