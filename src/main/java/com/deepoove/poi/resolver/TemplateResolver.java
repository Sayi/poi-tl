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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.GramerSymbol;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.RegexUtils;
import com.deepoove.poi.util.StyleUtils;

/**
 * @author Sayi
 * @version 0.0.1
 */
public class TemplateResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateResolver.class);
	
	public final String RULER_REGEX;
	public final String EXTRA_REGEX;
	
	public final Pattern TAG_PATTERN;
	public final Pattern VAR_PATTERN;

	public Configure config;
	
	public TemplateResolver(Configure config) {
		String signRegex = getGramarRegex(config);
		String prefixRegex = RegexUtils.escapeExprSpecialWord(GramerSymbol.GRAMER_PREFIX);
		String suffixRegex = RegexUtils.escapeExprSpecialWord(GramerSymbol.GRAMER_SUFFIX);
		RULER_REGEX = MessageFormat.format("{0}{1}\\w+{2}", prefixRegex, signRegex, suffixRegex);
		EXTRA_REGEX = MessageFormat.format("({0})|({1})", prefixRegex, suffixRegex);
		TAG_PATTERN = Pattern.compile(RULER_REGEX);
		VAR_PATTERN = Pattern.compile(EXTRA_REGEX);
		this.config = config;
	}

	public  List<ElementTemplate> parseElementTemplates(NiceXWPFDocument doc) {
		if (null == doc) return null;
		List<ElementTemplate> rts = new ArrayList<ElementTemplate>();
		rts.addAll(parseParagraph(doc.getParagraphs()));
		rts.addAll(parseTable(doc.getTables()));
		rts.addAll(parseHeader(doc.getHeaderList()));
		rts.addAll(parseFooter(doc.getFooterList()));
		return rts;
	}

	public List<ElementTemplate> parseHeader(List<XWPFHeader> headers) {
		List<ElementTemplate> result = new ArrayList<ElementTemplate>();
		if (null != headers) {
			for (XWPFHeader header : headers) {
				result.addAll(parseParagraph(header.getParagraphs()));
				result.addAll(parseTable(header.getTables()));
			}
		}
		return result;
	}
	public List<ElementTemplate> parseFooter(List<XWPFFooter> footers) {
		List<ElementTemplate> result = new ArrayList<ElementTemplate>();
		if (null != footers) {
			for (XWPFFooter footer : footers) {
				result.addAll(parseParagraph(footer.getParagraphs()));
				result.addAll(parseTable(footer.getTables()));
			}
		}
		return result;
	}

	public  List<RunTemplate> parseParagraph(List<XWPFParagraph> paragraphs) {
		List<RunTemplate> result = new ArrayList<RunTemplate>();
		if (null != paragraphs && !paragraphs.isEmpty()) {
			for (XWPFParagraph paragraph : paragraphs) {
				List<RunTemplate> parseRun = parseRun(paragraph);
				if (null != parseRun) result.addAll(parseRun);
			}
		}
		return result;
	}

	public List<ElementTemplate> parseTable(List<XWPFTable> tables) {
		List<ElementTemplate> result = new ArrayList<ElementTemplate>();
		if (null != tables && !tables.isEmpty()) {
			for (XWPFTable tb : tables) {
				List<ElementTemplate> parseTable = parseTable(tb);
				if (null != parseTable) result.addAll(parseTable);
			}
		}
		return result;
	}

	public  List<ElementTemplate> parseTable(XWPFTable table) {
		if (null == table) return null;
		List<XWPFTableRow> rows = table.getRows();
		if (null == rows) return null;
		List<ElementTemplate> rts = new ArrayList<ElementTemplate>();
		for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			if (null == cells) continue;
			for (XWPFTableCell cell : cells) {
				rts.addAll(parseParagraph(cell.getParagraphs()));
				rts.addAll(parseTable(cell.getTables()));
			}
		}
		return rts;
	}

	/**
	 * running string Algorithm
	 * 
	 * @param paragraph
	 * @return
	 */
	public  List<RunTemplate> parseRun(XWPFParagraph paragraph) {
		if (null  == paragraph) return null;
		List<XWPFRun> runs = paragraph.getRuns();
		if (null == runs || runs.isEmpty()) return null;
		String text = paragraph.getText();
		LOGGER.debug("The Paragrah's text is:{}", text);
		
		List<Pair<RunEdge, RunEdge>> runEdgeListPairs = new ArrayList<Pair<RunEdge, RunEdge>>();
		List<String> tags = new ArrayList<String>();
		
		Matcher matcher = TAG_PATTERN.matcher(text);
		while (matcher.find()) {
			tags.add(matcher.group());
			runEdgeListPairs.add(ImmutablePair.of(new RunEdge(matcher.start(), matcher.group()),
					new RunEdge(matcher.end(), matcher.group())));
		}
		if (tags.isEmpty()) return null;
		
		//search then calculate run edge
		searchRunEdge(runs, runEdgeListPairs);
		for (Pair<RunEdge, RunEdge> pair : runEdgeListPairs) {
			LOGGER.debug(pair.getLeft().toString());
			LOGGER.debug(pair.getRight().toString());
		}
		
		// split and merge
		List<RunTemplate> rts = new ArrayList<RunTemplate>();
		
		int size = runEdgeListPairs.size();
		int tagIndex = size;
		RunTemplate runTemplate;
		Pair<RunEdge, RunEdge> runEdgePair;
		for (int n = size - 1; n >= 0; n--) {
			runEdgePair = runEdgeListPairs.get(n);
			RunEdge startEdge = runEdgePair.getLeft();
			RunEdge endEdge = runEdgePair.getRight();
			int startRunPos = startEdge.getRunPos();
			int endRunPos = endEdge.getRunPos();
			int startOffset = startEdge.getRunEdge();
			int endOffset = endEdge.getRunEdge();
			
			String startText = runs.get(startRunPos).getText(0);
			String endText = runs.get(endRunPos).getText(0);
			if (endOffset + 1 >= endText.length()) {
				//end run 无需split，若不是start run，直接remove
				if (startRunPos != endRunPos) paragraph.removeRun(endRunPos);
			} else {
				//split end run, set extra in a run
				String extra = endText.substring(endOffset + 1, endText.length());
				if (startRunPos == endRunPos) {
					XWPFRun extraRun = paragraph.insertNewRun(endRunPos + 1);
					StyleUtils.styleRun(extraRun, runs.get(endRunPos));
					extraRun.setText(extra, 0);
				} else {
					XWPFRun extraRun = runs.get(endRunPos);
					extraRun.setText(extra, 0);
				}
			}
			
			//remove extra run
			for (int m = endRunPos - 1; m > startRunPos; m--) {
				paragraph.removeRun(m);
			}
			
			if (startOffset <= 0) {
				//start run 无需split
				XWPFRun templateRun = runs.get(startRunPos);
				templateRun.setText(tags.get(--tagIndex), 0);
				runTemplate = parseRun(runs.get(startRunPos));
			} else {
				//split start run, set extra in a run
				String extra = startText.substring(0, startOffset);
				XWPFRun extraRun = runs.get(startRunPos);
				extraRun.setText(extra, 0);
				
				XWPFRun templateRun = paragraph.insertNewRun(startRunPos + 1);
				StyleUtils.styleRun(templateRun, extraRun);
				templateRun.setText(tags.get(--tagIndex), 0);
				runTemplate = parseRun(runs.get(startRunPos + 1));
			}

			if (null != runTemplate) {
				rts.add(runTemplate);
			}
		}
		return rts;
	}

	private void searchRunEdge(List<XWPFRun> runs, List<Pair<RunEdge, RunEdge>> pairs) {
		int size = runs.size();
		int cursor = 0;// 游标
		
		int pos = 0;
		//计算第0个模板
		Pair<RunEdge, RunEdge> pair = pairs.get(pos);
		RunEdge startEdge = pair.getLeft();
		RunEdge endEdge = pair.getRight();
		int start = startEdge.getAllPos();
		int end = endEdge.getAllPos();
		for (int i = 0; i < size; i++) {
			XWPFRun run = runs.get(i);
			String text = run.getText(0);
			//游标略过empty run
			if (null == text) {
				LOGGER.warn("found the empty text run,may be produce bug:" + run);
				cursor += run.toString().length();
				continue;
			}
			LOGGER.debug(text);
			//起始位置不足，游标指向下一run
			if (text.length() + cursor < start) {
				cursor += text.length();
				continue;
			}
			//索引text
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
					
					//计算下一个模板
					pair = pairs.get(++pos);
					startEdge = pair.getLeft();
					endEdge = pair.getRight();
					start = startEdge.getAllPos();
					end = endEdge.getAllPos();
				}
			}
			//游标指向下一run
			cursor += text.length();
		}
	}

	public  RunTemplate parseRun(XWPFRun run) {
		String text = null;
		if (null == run || StringUtils.isBlank(text = run.getText(0))) return null;
		return (RunTemplate) parseTemplateFactory(text, run);
	}

	private <T> ElementTemplate parseTemplateFactory(String text, T obj) {
		LOGGER.debug("parse text:" + text);
		// temp ,future need to word analyze
		if (TAG_PATTERN.matcher(text).matches()) {
			String tag = VAR_PATTERN.matcher(text).replaceAll("").trim();
			if (obj.getClass() == XWPFRun.class) {
				return TemplateFactory.createRunTemplate(tag, config.getGramerChars(),
						(XWPFRun) obj);
			} else if (obj.getClass() == XWPFTableCell.class)
				// return CellTemplate.create(symbol, tagName, (XWPFTableCell)
				// obj);
				return null;
		}
		return null;
	}

	private String getGramarRegex(Configure config) {
		List<Character> gramerChar = config.getGramerChars();
		StringBuffer reg = new StringBuffer("(");
		for (int i = 0; ; i++){
			Character chara = gramerChar.get(i);
			String escapeExprSpecialWord = RegexUtils.escapeExprSpecialWord(chara.toString());
			if (i == gramerChar.size() - 1) {
				reg.append(escapeExprSpecialWord).append(")?");
				break;
			}
			else reg.append(escapeExprSpecialWord).append("|");
		}
		return reg.toString();
	}

}
