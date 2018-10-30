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
package com.deepoove.poi.resolver;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
import com.deepoove.poi.util.RegexUtils;

/**
 * 模板解析器
 * 
 * @author Sayi
 * @version 1.4.0
 */
public class TemplateVisitor implements Visitor {
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateVisitor.class);

	private Configure config;

	private List<ElementTemplate> eleTemplates;
	
	/**
	 * 使用正则表达式解析
	 */
	private Pattern templatePattern;
    private Pattern gramerPattern;

	public TemplateVisitor(Configure config) {
		this.config = config;
		initPattern();
	}

	@Override
	public List<ElementTemplate> visitDocument(XWPFDocument doc) {
		if (null == doc)
			return null;
		this.eleTemplates = new ArrayList<ElementTemplate>();
		visitParagraphs(doc.getParagraphs());
		visitTables(doc.getTables());
		visitHeaders(doc.getHeaderList());
		visitFooters(doc.getFooterList());
		return eleTemplates;
	}

	void visitHeaders(List<XWPFHeader> headers) {
		if (null == headers)
			return;
		for (XWPFHeader header : headers) {
			visitParagraphs(header.getParagraphs());
			visitTables(header.getTables());
		}
	}

	void visitFooters(List<XWPFFooter> footers) {
		if (null == footers)
			return;
		for (XWPFFooter footer : footers) {
			visitParagraphs(footer.getParagraphs());
			visitTables(footer.getTables());
		}
	}

	void visitParagraphs(List<XWPFParagraph> paragraphs) {
		if (null == paragraphs)
			return;
		for (XWPFParagraph paragraph : paragraphs) {
			visitParagraph(paragraph);
		}
	}

	void visitTables(List<XWPFTable> tables) {
		if (null == tables)
			return;
		for (XWPFTable tb : tables) {
			visitTable(tb);
		}
	}

	void visitTable(XWPFTable table) {
		if (null == table)
			return;
		List<XWPFTableRow> rows = table.getRows();
		if (null == rows)
			return;
		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			if (null == cells)
				continue;
			for (XWPFTableCell cell : cells) {
				visitParagraphs(cell.getParagraphs());
				visitTables(cell.getTables());
			}
		}
	}

	void visitParagraph(XWPFParagraph paragraph) {
		if (null == paragraph)
			return;
		RunningRunParagraph runningRun = new RunningRunParagraph(paragraph, templatePattern);
		List<XWPFRun> refactorRun = runningRun.refactorRun();
		if (null == refactorRun)
			return;
		for (XWPFRun run : refactorRun) {
			visitRun(run);
		}
	}

	void visitRun(XWPFRun run) {
		String text = null;
		if (null == run || StringUtils.isBlank(text = run.getText(0)))
			return;
		ElementTemplate elementTemplate = parseTemplateFactory(text, run);
		if (null != elementTemplate)
		    eleTemplates.add(elementTemplate);
	}

	private <T> ElementTemplate parseTemplateFactory(String text, T obj) {
		LOGGER.debug("Resolve text: {}, and create ElementTemplate", text);
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
	
	private void initPattern() {
        String signRegex = getGramarRegex(config);
        String prefixRegex = RegexUtils.escapeExprSpecialWord(config.getGramerPrefix());
        String suffixRegex = RegexUtils.escapeExprSpecialWord(config.getGramerSuffix());

        templatePattern = Pattern
                .compile(MessageFormat.format("{0}{1}\\w+(\\.\\w+)*{2}", prefixRegex, signRegex, suffixRegex));
        gramerPattern = Pattern.compile(MessageFormat.format("({0})|({1})", prefixRegex, suffixRegex));
    }

	private String getGramarRegex(Configure config) {
		List<Character> gramerChar = new ArrayList<Character>(config.getGramerChars());
		StringBuilder reg = new StringBuilder("(");
		for (int i = 0;; i++) {
			Character chara = gramerChar.get(i);
			String escapeExprSpecialWord = RegexUtils.escapeExprSpecialWord(chara.toString());
			if (i == gramerChar.size() - 1) {
				reg.append(escapeExprSpecialWord).append(")?");
				break;
			} else
				reg.append(escapeExprSpecialWord).append("|");
		}
		return reg.toString();
	}

}
