package com.deepoove.poi.plugin.table;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.compute.EnvModel;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.render.processor.DocumentProcessor;
import com.deepoove.poi.render.processor.EnvIterator;
import com.deepoove.poi.resolver.TemplateResolver;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.ReflectionUtils;
import com.deepoove.poi.util.TableTools;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * word模板替换，多列表格复用渲染
 * <p>
 * 该插件旨在替换多列表格内容
 * </p>
 * <p>
 * 单列表格循环可以使用{@link LoopColumnTableRenderPolicy}
 * </p>
 *
 * @author show1999
 */
public class MultipleColumnTableRenderPolicy implements RenderPolicy {

	private final static String DEFAULT_MULTIPLE_PREFIX = "$(";

	private final static String DEFAULT_MULTIPLE_SUFFIX = ")";

	private final static String DEFAULT_PREFIX = "[";

	private final static String DEFAULT_SUFFIX = "]";

	private final static int DEFAULT_MULTIPLE_COLUMN_NUM = 1;

	private final String regex = "\\$\\([0-9]+\\)";

	private final String multiplePrefix;

	private final String multipleSuffix;

	private final String prefix;

	private final String suffix;


	private final boolean onSameLine;


	public MultipleColumnTableRenderPolicy() {
		this(DEFAULT_MULTIPLE_PREFIX, DEFAULT_MULTIPLE_SUFFIX, DEFAULT_PREFIX, DEFAULT_SUFFIX, false);
	}

	public MultipleColumnTableRenderPolicy(boolean onSameLine) {
		this(DEFAULT_MULTIPLE_PREFIX, DEFAULT_MULTIPLE_SUFFIX, DEFAULT_PREFIX, DEFAULT_SUFFIX, onSameLine);
	}

	public MultipleColumnTableRenderPolicy(String prefix, String suffix, boolean onSameLine) {
		this(DEFAULT_MULTIPLE_PREFIX, DEFAULT_MULTIPLE_SUFFIX, prefix, suffix, onSameLine);
	}

	private MultipleColumnTableRenderPolicy(String multiplePrefix, String multipleSuffix, String prefix, String suffix, boolean onSameLine) {
		this.multiplePrefix = multiplePrefix;
		this.multipleSuffix = multipleSuffix;
		this.prefix = prefix;
		this.suffix = suffix;
		this.onSameLine = onSameLine;
	}

	@Override
	public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {

		try {
			if (!(data instanceof Iterable)) {
				throw new RenderException(
					data.toString() + " must  instanceof Iterable");
			}
			RunTemplate runTemplate = cast2runTemplate(eleTemplate);
			XWPFRun run = runTemplate.getRun();
			if (!TableTools.isInsideTable(run)) {
				throw new IllegalStateException(
					"The template tag " + runTemplate.getSource() + " must be inside a table");
			}
			XWPFTableCell tagCell = (XWPFTableCell) ((XWPFParagraph) run.getParent()).getBody();
			XWPFTableRow tableRow = tagCell.getTableRow();
			XWPFTable table = tableRow.getTable();
			run.setText("", 0);
			//循环的列数
			int loopColumnNum = getLoopColumnNum(tagCell);
			int templateColIndex = getTemplateColIndex(tagCell);
			int rowSize = table.getRows().size();
			int dataSize = getSize((Iterable<?>) data);
			//得到 要循环的列 它们相应的 width
			int[] loopColWidths = processLoopColWidth(table, tableRow, templateColIndex, dataSize, loopColumnNum);

			//初始化resolver
			TemplateResolver resolver = new TemplateResolver(template.getConfig().copy(prefix, suffix));
			Iterator<?> iterator = ((Iterable<?>) data).iterator();
			int index = 0;
			boolean hasNext = iterator.hasNext();
			//存放光标位置
			XWPFTableCell tableCell4cursor;
			while (hasNext) {
				Object root = iterator.next();
				hasNext = iterator.hasNext();
				List<XWPFTableCell> cells = new ArrayList<>();

				for (int i = 0; i < rowSize; i++) {
					XWPFTableRow row = table.getRow(i);
					int actualColIndex = getActualInsertPosition(row, templateColIndex);
					if (-1 == actualColIndex) {
						addColGridSpan(row, templateColIndex);
						continue;
					}
					int beginningPosition = actualColIndex + index * loopColumnNum;
					int curInsertPosition = beginningPosition;
					int tableCellPosition = beginningPosition;
					int widthIdx = 0;
					int size = loopColumnNum;
					while (size-- > 0) {
						XWPFTableCell templateCell = row.getCell(tableCellPosition);
						tableCell4cursor = row.getCell(curInsertPosition);
						int colWidth = loopColWidths[widthIdx++];
						templateCell.setWidth(String.valueOf(colWidth));

						XWPFTableCell nextCell = insertCell(row, curInsertPosition);
						setTableCell(row, templateCell, curInsertPosition);
						// double set row
						XmlCursor newCursor = tableCell4cursor.getCTTc().newCursor();
						newCursor.toPrevSibling();
						XmlObject object = newCursor.getObject();
						nextCell = new XWPFTableCell((CTTc) object, row, (IBody) nextCell.getPart());
						setTableCell(row, nextCell, curInsertPosition);

						cells.add(nextCell);
						curInsertPosition++;
						tableCellPosition += 2;
					}
				}

				RenderDataCompute dataCompute = template.getConfig()
					.getRenderDataComputeFactory()
					.newCompute(EnvModel.of(root, EnvIterator.makeEnv(index++, hasNext)));
				cells.forEach(cell -> {
					List<MetaTemplate> templates = resolver.resolveBodyElements(cell.getBodyElements());
					new DocumentProcessor(template, resolver, dataCompute).process(templates);
				});
			}
			//移除用来循环渲染的列
			for (int i = 0; i < rowSize; i++) {
				XWPFTableRow row = table.getRow(i);
				int actualInsertPosition = getActualInsertPosition(row, templateColIndex);
				if (-1 == actualInsertPosition) {
					minusGridSpan(row, templateColIndex);
					continue;
				}
				int startColumn = dataSize * loopColumnNum + actualInsertPosition;
				int size = loopColumnNum;
				while (size > 0) {
					removeCell(row, startColumn);
					size--;
				}
			}
			afterloop(table, data);
		} catch (Exception e) {
			throw new RenderException("failed to render table multi-column template", e);
		}
	}

	protected int getLoopColumnNum(XWPFTableCell tagCell) {
		int loopColumnNum = DEFAULT_MULTIPLE_COLUMN_NUM;
		String text = tagCell.getText();
		Matcher matcher = Pattern.compile(regex).matcher(text);
		if (matcher.find()) {
			String rowNumText = matcher.group(0);
			loopColumnNum = Integer.parseInt(rowNumText.replace(multiplePrefix, "").replace(multipleSuffix, ""));
			List<XWPFParagraph> paragraphs = tagCell.getParagraphs();
			paragraphs.get(0).getRuns().get(0).setText(text.replace(rowNumText, ""), 0);
			Iterator<XWPFParagraph> paragraphsIt = paragraphs.iterator();
			boolean isFirstParagraph = true;
			boolean hasNext = paragraphsIt.hasNext();
			while (hasNext) {
				XWPFParagraph nextParagraph = paragraphsIt.next();
				hasNext = paragraphsIt.hasNext();
				for (int i = 0; i < nextParagraph.getRuns().size(); i++) {
					if (isFirstParagraph) {
						isFirstParagraph = false;
					} else {
						nextParagraph.getRuns().get(i).setText("", 0);
					}
				}

			}
		}
		return loopColumnNum;
	}

	protected RunTemplate cast2runTemplate(MetaTemplate template) {
		if (!(template instanceof RunTemplate)) {
			throw new ClassCastException("type conversion failed, template is not of type RunTemplate");
		}
		return (RunTemplate) template;
	}

	private int getTemplateColIndex(XWPFTableCell tagCell) {
		return onSameLine ? getColIndex(tagCell) : (getColIndex(tagCell) + 1);
	}

	private void minusGridSpan(XWPFTableRow row, int templateColIndex) {
		XWPFTableCell actualCell = getActualCell(row, templateColIndex);
		if (actualCell == null) return;
		CTTcPr tcPr = actualCell.getCTTc().getTcPr();
		CTDecimalNumber gridSpan = tcPr.getGridSpan();
		gridSpan.setVal(BigInteger.valueOf(gridSpan.getVal().longValue() - 1));
	}

	private void addColGridSpan(XWPFTableRow row, int insertPosition) {
		XWPFTableCell actualCell = getActualCell(row, insertPosition);
		if (actualCell == null) return;
		CTTcPr tcPr = actualCell.getCTTc().getTcPr();
		CTDecimalNumber gridSpan = tcPr.getGridSpan();
		gridSpan.setVal(BigInteger.valueOf(gridSpan.getVal().longValue() + 1));
	}

	private int[] processLoopColWidth(XWPFTable table, XWPFTableRow row, int templateColIndex, int dataSize, int loopColumnNum) {

		int[] loopColWidths = new int[loopColumnNum];
		CTTblGrid tblGrid = TableTools.getTblGrid(table);

		for (int idx = 0; idx < loopColumnNum; idx++) {
			int actualColIndex = getActualInsertPosition(row, templateColIndex + idx);
			XWPFTableCell templateCell = row.getCell(actualColIndex);
			int width = templateCell.getWidth();
			TableWidthType widthType = templateCell.getWidthType();
			if (TableWidthType.DXA != widthType || width == 0) {
				throw new IllegalArgumentException("template col must set width in centimeters.");
			}
			int colWidth = width / dataSize;
			loopColWidths[idx] = colWidth;
		}

		for (int i = 0; i < dataSize; i++) {
			for (int j = 0; j < loopColumnNum; j++) {
				CTTblGridCol newGridCol = tblGrid.insertNewGridCol(templateColIndex + j + i * loopColumnNum);
				newGridCol.setW(BigInteger.valueOf(loopColWidths[j]));
			}
		}

		for (int i = loopColumnNum * dataSize + loopColumnNum + (templateColIndex - 1); i >= loopColumnNum * dataSize + 1 + (templateColIndex - 1); i--) {
			tblGrid.removeGridCol(i);
		}

		return loopColWidths;
	}

	private int getSize(Iterable<?> data) {
		int size = 0;
		for (Object datum : data) {
			size++;
		}
		return size;
	}

	@SuppressWarnings("unchecked")
	private void removeCell(XWPFTableRow row, int actualInsertPosition) {
		List<XWPFTableCell> cells = (List<XWPFTableCell>) ReflectionUtils.getValue("tableCells", row);
		cells.remove(actualInsertPosition);
		row.getCtRow().removeTc(actualInsertPosition);

	}

	@SuppressWarnings("unchecked")
	private XWPFTableCell insertCell(XWPFTableRow tableRow, int actualInsertPosition) {
		CTRow row = tableRow.getCtRow();
		CTTc newTc = row.insertNewTc(actualInsertPosition);
		XWPFTableCell cell = new XWPFTableCell(newTc, tableRow, tableRow.getTable().getBody());

		List<XWPFTableCell> cells = (List<XWPFTableCell>) ReflectionUtils.getValue("tableCells", tableRow);
		cells.add(actualInsertPosition, cell);
		return cell;
	}

	protected void afterloop(XWPFTable table, Object data) {
	}

	@SuppressWarnings("unchecked")
	private void setTableCell(XWPFTableRow row, XWPFTableCell templateCell, int pos) {
		List<XWPFTableCell> rows = (List<XWPFTableCell>) ReflectionUtils.getValue("tableCells", row);
		rows.set(pos, templateCell);
		row.getCtRow().setTcArray(pos, templateCell.getCTTc());
	}


	private int getColIndex(XWPFTableCell cell) {
		XWPFTableRow tableRow = cell.getTableRow();
		int orginalCol = 0;
		for (int i = 0; i < tableRow.getTableCells().size(); i++) {
			XWPFTableCell current = tableRow.getCell(i);
			int intValue = 1;
			CTTcPr tcPr = current.getCTTc().getTcPr();
			if (null != tcPr) {
				CTDecimalNumber gridSpan = tcPr.getGridSpan();
				if (null != gridSpan) intValue = gridSpan.getVal().intValue();
			}
			orginalCol += intValue;
			if (current.getCTTc() == cell.getCTTc()) {
				return orginalCol - intValue;
			}
		}
		return -1;
	}

	private int getActualInsertPosition(XWPFTableRow tableRow, int insertPosition) {
		int orginalCol = 0;
		for (int i = 0; i < tableRow.getTableCells().size(); i++) {
			XWPFTableCell current = tableRow.getCell(i);
			int intValue = 1;
			CTTcPr tcPr = current.getCTTc().getTcPr();
			if (null != tcPr) {
				CTDecimalNumber gridSpan = tcPr.getGridSpan();
				if (null != gridSpan) intValue = gridSpan.getVal().intValue();
			}
			orginalCol += intValue;
			if (orginalCol - intValue == insertPosition && intValue == 1) {
				return i;
			}
		}
		return -1;
	}

	private XWPFTableCell getActualCell(XWPFTableRow tableRow, int insertPosition) {
		int orginalCol = 0;
		for (int i = 0; i < tableRow.getTableCells().size(); i++) {
			XWPFTableCell current = tableRow.getCell(i);
			int intValue = 1;
			CTTcPr tcPr = current.getCTTc().getTcPr();
			if (null != tcPr) {
				CTDecimalNumber gridSpan = tcPr.getGridSpan();
				if (null != gridSpan) intValue = gridSpan.getVal().intValue();
			}
			orginalCol += intValue;
			if (orginalCol - 1 >= insertPosition) {
				return current;
			}
		}
		return null;
	}

}