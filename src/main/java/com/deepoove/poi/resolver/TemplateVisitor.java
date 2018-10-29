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
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.RegexUtils;

/**
 * 模板解析器
 * 
 * @author Sayi
 * @version 1.4.0
 */
public class TemplateVisitor {
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateVisitor.class);

	private final Pattern TAG_PATTERN;

	private final Pattern VAR_PATTERN;

	private Configure config;

	private List<ElementTemplate> eleTemplates;

	public TemplateVisitor(Configure config) {
		String signRegex = getGramarRegex(config);
		String prefixRegex = RegexUtils.escapeExprSpecialWord(config.getGramerPrefix());
		String suffixRegex = RegexUtils.escapeExprSpecialWord(config.getGramerSuffix());

		TAG_PATTERN = Pattern
				.compile(MessageFormat.format("{0}{1}\\w+(\\.\\w+)*{2}", prefixRegex, signRegex, suffixRegex));
		VAR_PATTERN = Pattern.compile(MessageFormat.format("({0})|({1})", prefixRegex, suffixRegex));

		this.config = config;
	}

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

	public void visitHeaders(List<XWPFHeader> headers) {
		if (null == headers)
			return;
		for (XWPFHeader header : headers) {
			visitParagraphs(header.getParagraphs());
			visitTables(header.getTables());
		}
	}

	public void visitFooters(List<XWPFFooter> footers) {
		if (null == footers)
			return;
		for (XWPFFooter footer : footers) {
			visitParagraphs(footer.getParagraphs());
			visitTables(footer.getTables());
		}
	}

	public void visitParagraphs(List<XWPFParagraph> paragraphs) {
		if (null == paragraphs)
			return;
		for (XWPFParagraph paragraph : paragraphs) {
			visitParagraph(paragraph);
		}
	}

	public void visitTables(List<XWPFTable> tables) {
		if (null == tables)
			return;
		for (XWPFTable tb : tables) {
			visitTable(tb);
		}
	}

	public void visitTable(XWPFTable table) {
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

	public void visitParagraph(XWPFParagraph paragraph) {
		if (null == paragraph)
			return;
		RunningRunParagraph runningRun = new RunningRunParagraph(paragraph, TAG_PATTERN);
		List<XWPFRun> refactorRun = runningRun.refactorRun();
		if (null == refactorRun)
			return;
		for (XWPFRun run : refactorRun) {
			visitRun(run);
		}
	}

	public void visitRun(XWPFRun run) {
		String text = null;
		if (null == run || StringUtils.isBlank(text = run.getText(0)))
			return;
		eleTemplates.add((RunTemplate) parseTemplateFactory(text, run));
	}

	private <T> ElementTemplate parseTemplateFactory(String text, T obj) {
		LOGGER.debug("resolve text:" + text);
		// temp ,future need to word analyze
		if (TAG_PATTERN.matcher(text).matches()) {
			String tag = VAR_PATTERN.matcher(text).replaceAll("").trim();
			if (obj.getClass() == XWPFRun.class) {
				return TemplateFactory.createRunTemplate(tag, config, (XWPFRun) obj);
			} else if (obj.getClass() == XWPFTableCell.class)
				// return CellTemplate.create(symbol, tagName, (XWPFTableCell)
				// obj);
				return null;
		}
		return null;
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
