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
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.cell.CellTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * @author Sayi
 * @version 0.0.1
 */
public class TemplateResolver {
	private static final Logger logger = LoggerFactory.getLogger(TemplateResolver.class);
	public static final String RULER_REGEX;
	public static final String EXTRA_REGEX;
	public static final Pattern TAG_PATTERN;
	public static final Pattern VAR_PATTERN;

	static {
		RULER_REGEX = escapeExprSpecialWord(GramerSymbol.GRAMER_PREFIX) + "(#|@)?\\w+"
				+ escapeExprSpecialWord(GramerSymbol.GRAMER_SUFFIX);
		EXTRA_REGEX = "(" + escapeExprSpecialWord(GramerSymbol.GRAMER_PREFIX) + ")|("
				+ escapeExprSpecialWord(GramerSymbol.GRAMER_SUFFIX) + ")";
		TAG_PATTERN = Pattern.compile(RULER_REGEX);
		VAR_PATTERN = Pattern.compile(EXTRA_REGEX);
	}

	public static List<ElementTemplate> parseElementTemplates(NiceXWPFDocument doc) {
		if (null == doc) return null;
		List<ElementTemplate> rts = new ArrayList<ElementTemplate>();
		rts.addAll(parseParagraph(doc.getParagraphs()));
		rts.addAll(parseTable(doc.getTables()));
		List<XWPFHeader> headers = doc.getHeaderList();
		if (null != headers) {
			for (XWPFHeader header : headers) {
				rts.addAll(parseParagraph(header.getParagraphs()));
				rts.addAll(parseTable(header.getTables()));
			}
		}
		List<XWPFFooter> footers = doc.getFooterList();
		if (null != footers) {
			for (XWPFFooter footer : footers) {
				rts.addAll(parseParagraph(footer.getParagraphs()));
				rts.addAll(parseTable(footer.getTables()));
			}
		}
		return rts;
	}

	private static List<RunTemplate> parseParagraph(List<XWPFParagraph> paragraphs) {
		List<RunTemplate> result = new ArrayList<RunTemplate>();
		if (null != paragraphs && !paragraphs.isEmpty()) {
			for (XWPFParagraph paragraph : paragraphs) {
				List<RunTemplate> parseRun = parseRun(paragraph);
				if (null != parseRun) result.addAll(parseRun);
			}
		}
		return result;
	}

	private static List<ElementTemplate> parseTable(List<XWPFTable> tables) {
		List<ElementTemplate> result = new ArrayList<ElementTemplate>();
		if (null != tables && !tables.isEmpty()) {
			for (XWPFTable tb : tables) {
				List<ElementTemplate> parseTable = parseTable(tb);
				if (null != parseTable) result.addAll(parseTable);
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	private static CellTemplate parseCell(XWPFTableCell cell) {
		if (null == cell) return null;
		String text = cell.getText();
		if (null == text || "".equals(text.trim())) return null;
		return (CellTemplate) parseTemplateFactory(text, cell);
	}

	public static List<ElementTemplate> parseTable(XWPFTable table) {
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
	public static List<RunTemplate> parseRun(XWPFParagraph paragraph) {
		List<XWPFRun> runs = paragraph.getRuns();
		if (null == runs || runs.isEmpty()) return null;
		String text = paragraph.getText();
		logger.debug("Paragrah's text is:" + text);
		List<Pair<RunEdge, RunEdge>> pairs = new ArrayList<Pair<RunEdge, RunEdge>>();
		List<String> tags = new ArrayList<String>();
		calcTagPosInParagraph(text, pairs, tags);

		List<RunTemplate> rts = new ArrayList<RunTemplate>();
		if (pairs.isEmpty()) return rts;
		RunTemplate runTemplate;
		calcRunPosInParagraph(runs, pairs);
		for (Pair<RunEdge, RunEdge> pai : pairs) {
			logger.debug(pai.getLeft().toString());
			logger.debug(pai.getRight().toString());
		}
		// split and merge
		Pair<RunEdge, RunEdge> pair2 = pairs.get(0);
		int length = pairs.size();
		int tagIndex = length;
		for (int n = length - 1; n >= 0; n--) {
			pair2 = pairs.get(n);
			RunEdge left2 = pair2.getLeft();
			RunEdge right2 = pair2.getRight();
			int left_r = left2.getRunPos();
			int right_r = right2.getRunPos();
			int runEdge = left2.getRunEdge();
			int runEdge2 = right2.getRunEdge();
			String text1 = runs.get(left_r).getText(0);
			String text2 = runs.get(right_r).getText(0);
			if (runEdge2 + 1 >= text2.length()) {
				if (left_r != right_r) paragraph.removeRun(right_r);
			} else {
				String substring = text2.substring(runEdge2 + 1, text2.length());
				if (left_r == right_r) {
					XWPFRun insertNewRun = paragraph.insertNewRun(right_r + 1);
					styleRun(insertNewRun, runs.get(right_r));
					insertNewRun.setText(substring, 0);
				} else runs.get(right_r).setText(substring, 0);
			}
			for (int m = right_r - 1; m > left_r; m--) {
				paragraph.removeRun(m);
			}
			if (runEdge <= 0) {
				runs.get(left_r).setText(tags.get(--tagIndex), 0);
				runTemplate = parseRun(runs.get(left_r));
			} else {
				String substring = text1.substring(0, runEdge);
				XWPFRun xwpfRun = runs.get(left_r);
				runs.get(left_r).setText(substring, 0);
				XWPFRun insertNewRun = paragraph.insertNewRun(left_r + 1);
				styleRun(insertNewRun, xwpfRun);
				insertNewRun.setText(tags.get(--tagIndex), 0);
				runTemplate = parseRun(runs.get(left_r + 1));
			}

			if (null != runTemplate) {
				rts.add(runTemplate);
			}
		}
		return rts;
	}

	private static void calcRunPosInParagraph(List<XWPFRun> runs,
			List<Pair<RunEdge, RunEdge>> pairs) {
		int size = runs.size(), pos = 0, calc = 0;
		Pair<RunEdge, RunEdge> pair = pairs.get(pos);
		RunEdge leftEdge = pair.getLeft();
		RunEdge rightEdge = pair.getRight();
		int leftInAll = leftEdge.getAllPos();
		int rightInAll = rightEdge.getAllPos();
		for (int i = 0; i < size; i++) {
			XWPFRun run = runs.get(i);
			String str = run.getText(0);
			if (null == str) {
				logger.warn("found the empty text run,may be produce bug:" + run);
				calc += run.toString().length();
				continue;
			}
			logger.debug(str);
			if (str.length() + calc < leftInAll) {
				calc += str.length();
				continue;
			}
			for (int j = 0; j < str.length(); j++) {
				if (calc + j == leftInAll) {
					leftEdge.setRunPos(i);
					leftEdge.setRunEdge(j);
					leftEdge.setText(str);
				}
				if (calc + j == rightInAll - 1) {
					rightEdge.setRunPos(i);
					rightEdge.setRunEdge(j);
					rightEdge.setText(str);

					if (pos == pairs.size() - 1) break;
					pair = pairs.get(++pos);
					leftEdge = pair.getLeft();
					rightEdge = pair.getRight();
					leftInAll = leftEdge.getAllPos();
					rightInAll = rightEdge.getAllPos();
				}
			}
			calc += str.length();
		}
	}

	private static void calcTagPosInParagraph(String text, List<Pair<RunEdge, RunEdge>> pairs,
			List<String> tags) {
		String group = null;
		int start = 0, end = 0;
		Matcher matcher = TAG_PATTERN.matcher(text);
		while (matcher.find()) {
			group = matcher.group();
			tags.add(group);
			start = text.indexOf(group, end);
			end = start + group.length();
			pairs.add(new ImmutablePair<RunEdge, RunEdge>(new RunEdge(start, group),
					new RunEdge(end, group)));
		}
	}

	private static void styleRun(XWPFRun destRun, XWPFRun srcRun) {
		if (null == destRun || null == srcRun) return;
		destRun.setBold(srcRun.isBold());
		destRun.setColor(srcRun.getColor());
		destRun.setCharacterSpacing(srcRun.getCharacterSpacing());
		destRun.setFontFamily(srcRun.getFontFamily());
		int fontSize = srcRun.getFontSize();
		if (-1 != fontSize) destRun.setFontSize(fontSize);
		destRun.setItalic(srcRun.isItalic());
		destRun.setStrikeThrough(srcRun.isStrikeThrough());
		destRun.setUnderline(srcRun.getUnderline());
	}

	public static RunTemplate parseRun(XWPFRun run) {
		String text = null;
		if (null == run || StringUtils.isBlank(text = run.getText(0))) return null;
		return (RunTemplate) parseTemplateFactory(text, run);
	}

	private static <T> ElementTemplate parseTemplateFactory(String text, T obj) {
		logger.debug("parse text:" + text);
		// temp ,future need to word analyze
		if (TAG_PATTERN.matcher(text).matches()) {
			String tag = VAR_PATTERN.matcher(text).replaceAll("").trim();
			GramerSymbol symbol = GramerSymbolFactory.getGramerSymbol(tag);
			String tagName = symbol == GramerSymbol.TEXT ? tag : tag.substring(0);

			if (obj.getClass() == XWPFRun.class)
				return RunTemplate.createRunTemplate(symbol, tagName, (XWPFRun) obj);
			else if (obj.getClass() == XWPFTableCell.class)
				return CellTemplate.create(symbol, tagName, (XWPFTableCell) obj);
		}
		return null;
	}

	private static String escapeExprSpecialWord(String keyword) {
		if (StringUtils.isNotBlank(keyword)) {
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}",
					"|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}

}
