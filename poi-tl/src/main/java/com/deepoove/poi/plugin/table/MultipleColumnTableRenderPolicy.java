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

    private MultipleColumnTableRenderPolicy(String multiplePrefix, String multipleSuffix, String prefix,
                                            String suffix, boolean onSameLine) {
        this.multiplePrefix = multiplePrefix;
        this.multipleSuffix = multipleSuffix;
        this.prefix = prefix;
        this.suffix = suffix;
        this.onSameLine = onSameLine;
    }

    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {

        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        XWPFRun run = runTemplate.getRun();
        try {
            if (!TableTools.isInsideTable(run)) {
                throw new IllegalStateException("The template tag " + runTemplate.getSource() + " must be inside a " + "table");
            }
            XWPFTableCell tagCell = (XWPFTableCell) ((XWPFParagraph) run.getParent()).getBody();
            XWPFTableRow tableRow = tagCell.getTableRow();
            XWPFTable table = tableRow.getTable();
            run.setText("", 0);

            int loopColumnNum = getLoopColumnNum(tagCell);
            int templateColIndex = getTemplateColIndex(tagCell);
            int rowSize = table.getRows().size();
            int index = 0;

            if (data instanceof Iterable) {
                int dataSize = getSize((Iterable<?>) data);
                int[] loopColWidths = processLoopColWidth(table, tableRow, templateColIndex, dataSize, loopColumnNum);
                int[] newCellInsertPoint4row = new int[rowSize];
                XWPFTableCell[] cursorCell4row = new XWPFTableCell[rowSize];

                boolean initFlag = true;
                TemplateResolver resolver = new TemplateResolver(template.getConfig().copy(prefix, suffix));
                Iterator<?> iterator = ((Iterable<?>) data).iterator();
                boolean hasNext = iterator.hasNext();
                while (hasNext) {
                    Object root = iterator.next();
                    hasNext = iterator.hasNext();

                    List<XWPFTableCell> cells = new ArrayList<>();
                    int loopCellStartPoint = index * loopColumnNum + templateColIndex;

                    for (int j = 0; j < loopColumnNum * 2; j += 2) {
                        for (int i = 0; i < rowSize; i++) {
                            XWPFTableRow row = table.getRow(i);
                            int actualLoopCellPosition = getActualInsertPosition(row, loopCellStartPoint + j);
                            if (-1 == actualLoopCellPosition) {
                                addColGridSpan(row, loopCellStartPoint + j);
                                continue;
                            }
                            XWPFTableCell loopCell = row.getCell(actualLoopCellPosition);
                            if (initFlag) {
                                loopCell.setWidth(String.valueOf(loopColWidths[j / 2]));
                                if (j == 0) {
                                    cursorCell4row[i] = loopCell;
                                    newCellInsertPoint4row[i] = actualLoopCellPosition;
                                }
                            }
                            insertCell(row, newCellInsertPoint4row[i]);
                            setTableCell(row, loopCell, newCellInsertPoint4row[i]);
                            // double set row
                            XmlCursor newCursor = cursorCell4row[i].getCTTc().newCursor();
                            newCursor.toPrevSibling();
                            XmlObject object = newCursor.getObject();
                            XWPFTableCell nextCell = new XWPFTableCell((CTTc) object, row, (IBody) loopCell.getPart());
                            setTableCell(row, nextCell, newCellInsertPoint4row[i]++);
                            cells.add(nextCell);
                        }
                    }
                    initFlag = false;
                    RenderDataCompute dataCompute =
                        template.getConfig().getRenderDataComputeFactory().newCompute(EnvModel.of(root,
                            EnvIterator.makeEnv(index++, hasNext)));
                    cells.forEach(cell -> {
                        List<MetaTemplate> templates = resolver.resolveBodyElements(cell.getBodyElements());
                        new DocumentProcessor(template, resolver, dataCompute).process(templates);
                    });

                }
            }

            int endPoint = index * loopColumnNum + templateColIndex - 1;
            for (int j = loopColumnNum; j > 0; j--) {
                for (int i = 0; i < rowSize; i++) {
                    XWPFTableRow row = table.getRow(i);
                    int actualInsertPosition = getActualInsertPosition(row, endPoint + j);
                    if (-1 == actualInsertPosition) {
                        minusGridSpan(row, endPoint + j);
                        continue;
                    }
                    removeCell(row, actualInsertPosition);
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


    private int getTemplateColIndex(XWPFTableCell tagCell) {
        return onSameLine ? getColIndex(tagCell) : (getColIndex(tagCell) + 1);
    }

    private void minusGridSpan(XWPFTableRow row, int templateColIndex) {
        XWPFTableCell actualCell = getActualCell(row, templateColIndex);
        CTTcPr tcPr = actualCell.getCTTc().getTcPr();
        CTDecimalNumber gridSpan = tcPr.getGridSpan();
        gridSpan.setVal(BigInteger.valueOf(gridSpan.getVal().longValue() - 1));
    }

    private void addColGridSpan(XWPFTableRow row, int insertPosition) {
        XWPFTableCell actualCell = getActualCell(row, insertPosition);
        CTTcPr tcPr = actualCell.getCTTc().getTcPr();
        CTDecimalNumber gridSpan = tcPr.getGridSpan();
        gridSpan.setVal(BigInteger.valueOf(gridSpan.getVal().longValue() + 1));
    }

    private int[] processLoopColWidth(XWPFTable table, XWPFTableRow row, int templateColIndex, int dataSize,
                                      int loopColumnNum) {

        int[] loopColWidths = new int[loopColumnNum];
        CTTblGrid tblGrid = TableTools.getTblGrid(table);

        for (int i = 0; i < loopColumnNum; i++) {
            int actualColIndex = getActualInsertPosition(row, templateColIndex + i);
            XWPFTableCell templateCell = row.getCell(actualColIndex);
            int width = templateCell.getWidth();
            TableWidthType widthType = templateCell.getWidthType();
            if (TableWidthType.DXA != widthType || width == 0) {
                throw new IllegalArgumentException("template col must set width in centimeters.");
            }
            int colWidth = width / dataSize;
            loopColWidths[i] = colWidth;
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
                if (null != gridSpan)
                    intValue = gridSpan.getVal().intValue();
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
                if (null != gridSpan)
                    intValue = gridSpan.getVal().intValue();
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
                if (null != gridSpan)
                    intValue = gridSpan.getVal().intValue();
            }
            orginalCol += intValue;
            if (orginalCol - 1 >= insertPosition) {
                return current;
            }
        }
        return null;
    }

}