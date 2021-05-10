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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.TableWidthType;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;

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

/**
 * Hack for loop table column
 * 
 * @author Sayi
 */
public class LoopColumnTableRenderPolicy implements RenderPolicy {

    private String prefix;
    private String suffix;
    private boolean onSameLine;

    public LoopColumnTableRenderPolicy() {
        this(false);
    }

    public LoopColumnTableRenderPolicy(boolean onSameLine) {
        this("[", "]", onSameLine);
    }

    public LoopColumnTableRenderPolicy(String prefix, String suffix) {
        this(prefix, suffix, false);
    }

    public LoopColumnTableRenderPolicy(String prefix, String suffix, boolean onSameLine) {
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
                throw new IllegalStateException(
                        "The template tag " + runTemplate.getSource() + " must be inside a table");
            }
            XWPFTableCell tagCell = (XWPFTableCell) ((XWPFParagraph) run.getParent()).getBody();
            XWPFTable table = tagCell.getTableRow().getTable();
            run.setText("", 0);

            int templateColIndex = getTemplateColIndex(tagCell);
            int actualColIndex = getActualInsertPosition(tagCell.getTableRow(), templateColIndex);
            XWPFTableCell firstCell = tagCell.getTableRow().getCell(actualColIndex);
            int width = firstCell.getWidth();
            TableWidthType widthType = firstCell.getWidthType();
            if (TableWidthType.DXA != widthType || width == 0) {
                throw new IllegalArgumentException("template col must set width in centimeters.");
            }

            int rowSize = table.getRows().size();
            if (null != data && data instanceof Iterable) {
                int colWidth = processLoopColWidth(table, width, templateColIndex, data);

                Iterator<?> iterator = ((Iterable<?>) data).iterator();
                int insertPosition = templateColIndex;

                TemplateResolver resolver = new TemplateResolver(template.getConfig().copy(prefix, suffix));

                int index = 0;
                boolean hasNext = iterator.hasNext();
                while (hasNext) {
                    Object root = iterator.next();
                    hasNext = iterator.hasNext();

                    insertPosition = templateColIndex++;
                    List<XWPFTableCell> cells = new ArrayList<XWPFTableCell>();

                    for (int i = 0; i < rowSize; i++) {
                        XWPFTableRow row = table.getRow(i);
                        int actualInsertPosition = getActualInsertPosition(row, insertPosition);
                        if (-1 == actualInsertPosition) {
                            addColGridSpan(row, insertPosition);
                            continue;
                        }
                        XWPFTableCell templateCell = row.getCell(actualInsertPosition);
                        templateCell.setWidth(colWidth + "");
                        XWPFTableCell nextCell = insertCell(row, actualInsertPosition);
                        setTableCell(row, templateCell, actualInsertPosition);

                        // double set row
                        XmlCursor newCursor = templateCell.getCTTc().newCursor();
                        newCursor.toPrevSibling();
                        XmlObject object = newCursor.getObject();
                        nextCell = new XWPFTableCell((CTTc) object, row, (IBody) nextCell.getPart());
                        setTableCell(row, nextCell, actualInsertPosition);

                        cells.add(nextCell);
                    }

                    RenderDataCompute dataCompute = template.getConfig()
                            .getRenderDataComputeFactory()
                            .newCompute(EnvModel.of(root, EnvIterator.makeEnv(index++, hasNext)));
                    cells.forEach(cell -> {
                        List<MetaTemplate> templates = resolver.resolveBodyElements(cell.getBodyElements());
                        new DocumentProcessor(template, resolver, dataCompute).process(templates);
                    });
                }
            }

            for (int i = 0; i < rowSize; i++) {
                XWPFTableRow row = table.getRow(i);
                int actualInsertPosition = getActualInsertPosition(row, templateColIndex);
                if (-1 == actualInsertPosition) {
                    minusGridSpan(row, templateColIndex);
                    continue;
                }
                removeCell(row, actualInsertPosition);
            }
            afterloop(table, data);
        } catch (Exception e) {
            throw new RenderException("HackLoopTable for " + eleTemplate + "error: " + e.getMessage(), e);
        }
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

    private int processLoopColWidth(XWPFTable table, int width, int templateColIndex, Object data) {
        CTTblGrid tblGrid = TableTools.getTblGrid(table);
        int dataSize = getSize((Iterable<?>) data);
        int colWidth = width / dataSize;
        // int colWidth = width;
        for (int j = 0; j < dataSize; j++) {
            CTTblGridCol newGridCol = tblGrid.insertNewGridCol(templateColIndex);
            newGridCol.setW(BigInteger.valueOf(colWidth));
        }
        tblGrid.removeGridCol(templateColIndex + dataSize);
        return colWidth;
    }

    private int getSize(Iterable<?> data) {
        int size = 0;
        Iterator<?> iterator = data.iterator();
        while (iterator.hasNext()) {
            iterator.next();
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
            if (current == cell) {
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
