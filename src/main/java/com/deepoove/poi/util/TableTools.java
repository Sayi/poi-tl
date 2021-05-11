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
package com.deepoove.poi.util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BodyType;
import org.apache.poi.xwpf.usermodel.TableWidthType;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblLayoutType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblLayoutType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

/**
 * XWPFTable Tools
 * 
 * @author Sayi
 * @version 1.4.0
 */
public final class TableTools {

    /**
     * merge several columns of cells in the same row
     * 
     * @param table   table
     * @param row     index of the row
     * @param fromCol from column to be merged
     * @param toCol   to column to be merged
     */
    public static void mergeCellsHorizonal(XWPFTable table, int row, int fromCol, int toCol) {
        Preconditions.requireGreaterThan(toCol, fromCol, "To column to be merged must greater than from column.");
        mergeCellsHorizontalWithoutRemove(table, row, fromCol, toCol);
        XWPFTableRow rowTable = table.getRow(row);
        for (int colIndex = fromCol + 1; colIndex <= toCol; colIndex++) {
            rowTable.removeCell(fromCol + 1);
            if (rowTable.getTableCells().size() != rowTable.getCtRow().sizeOfTcArray()) {
                rowTable.getCtRow().removeTc(fromCol + 1);
            }
        }
    }

    /**
     * merge several columns of cells in the same row, but do'not remove extra cells
     * 
     * @param table   table
     * @param row     index of the row
     * @param fromCol from column to be merged
     * @param toCol   to column to be merged
     */
    public static void mergeCellsHorizontalWithoutRemove(XWPFTable table, int row, int fromCol, int toCol) {
        Preconditions.requireGreaterThan(toCol, fromCol, "To column to be merged must greater than from column.");
        XWPFTableCell cell = table.getRow(row).getCell(fromCol);
        CTTcPr tcPr = getTcPr(cell);
        tcPr.addNewGridSpan();
        tcPr.getGridSpan().setVal(BigInteger.valueOf((long) (toCol - fromCol + 1)));
        int tcw = 0;
        for (int colIndex = fromCol; colIndex <= toCol; colIndex++) {
            XWPFTableCell tableCell = table.getRow(row).getCell(colIndex);
            // TODO pct, auto
            if (TableWidthType.DXA == tableCell.getWidthType()) {
                if (-1 == tableCell.getWidth()) return;
                tcw += tableCell.getWidth();
            } else {
                return;
            }
        }
        if (0 != tcw) cell.setWidth(tcw + "");
    }

    /**
     * merge several rows of cells in the same column
     * 
     * @param table   table
     * @param col     index of the column
     * @param fromRow from row to be merged
     * @param toRow   end row to be merged
     */
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        Preconditions.requireGreaterThan(toRow, fromRow, "To row to be merged must greater than from row.");
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            CTTcPr tcPr = getTcPr(cell);
            CTVMerge vMerge = tcPr.addNewVMerge();
            if (rowIndex == fromRow) {
                // The first merged cell is set with RESTART merge value
                vMerge.setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                vMerge.setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * set table width
     * 
     * @param table
     * @param width
     * @param colWidths
     */
    public static void setWidth(XWPFTable table, String width, int[] colWidths) {
        ensureTblW(table);
        table.setWidth(width);
        if (null == colWidths) {
            int columnSize = TableTools.obtainColumnSize(table);
            if (table.getWidthType() == TableWidthType.DXA) {
                colWidths = UnitUtils.average(Integer.valueOf(width), columnSize);
            } else if (table.getWidthType() == TableWidthType.PCT) {
                int sum = 0;
                colWidths = new int[columnSize];
                for (int i = 0; i < columnSize - 1; i++) {
                    colWidths[i] = 100 / columnSize;
                    sum += colWidths[i];
                }
                colWidths[columnSize - 1] = 100 - sum;
            }
        }
        if (null != colWidths) {
            BigInteger[] gridCol = null;
            String[] cellWidth = null;
            if (table.getWidthType() == TableWidthType.DXA) {
                cellWidth = Arrays.stream(colWidths).mapToObj(String::valueOf).toArray(String[]::new);
                gridCol = Arrays.stream(colWidths).mapToObj(BigInteger::valueOf).toArray(BigInteger[]::new);
            } else if (table.getWidthType() == TableWidthType.PCT) {
                cellWidth = Arrays.stream(colWidths).mapToObj(w -> w + "%").toArray(String[]::new);
                BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(table.getBody());
                int pageWidth = bodyContainer.elementPageWidth(table);
                int tableWidth = pageWidth * Integer.valueOf(width.substring(0, width.length() - 1)) / 100;
                gridCol = Arrays.stream(colWidths)
                        .mapToObj(w -> BigInteger.valueOf(w * tableWidth / 100))
                        .toArray(BigInteger[]::new);
            }
            CTTblGrid tblGrid = TableTools.getTblGrid(table);
            CTTblLayoutType tblLayout = TableTools.getTblLayout(table);
            tblLayout.setType(STTblLayoutType.FIXED);
            for (int index = 0; index < colWidths.length; index++) {
                if (null != gridCol) {
                    CTTblGridCol addNewGridCol = tblGrid.addNewGridCol();
                    addNewGridCol.setW(gridCol[index]);
                }
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    row.getCell(index).setWidth(cellWidth[index]);
                }
            }
        }
    }

    private static void ensureTblW(XWPFTable table) {
        CTTbl ctTbl = table.getCTTbl();
        CTTblPr tblPr = (ctTbl.getTblPr() != null) ? ctTbl.getTblPr() : ctTbl.addNewTblPr();
        if (!tblPr.isSetTblW()) tblPr.addNewTblW();
    }

    public static void borderTable(XWPFTable table, BorderStyle borderStyle) {
        setBorder(table::setLeftBorder, borderStyle);
        setBorder(table::setRightBorder, borderStyle);
        setBorder(table::setTopBorder, borderStyle);
        setBorder(table::setBottomBorder, borderStyle);
        setBorder(table::setInsideHBorder, borderStyle);
        setBorder(table::setInsideVBorder, borderStyle);
    }

    public static void borderTable(XWPFTable table, int size) {
        CTTblPr tblPr = getTblPr(table);
        CTTblBorders tblBorders = tblPr.getTblBorders();
        if (null == tblBorders) {
            tblBorders = tblPr.addNewTblBorders();
        }
        BigInteger borderSize = BigInteger.valueOf(size);
        if (!tblBorders.isSetBottom()) tblBorders.addNewBottom();
        if (!tblBorders.isSetLeft()) tblBorders.addNewLeft();
        if (!tblBorders.isSetTop()) tblBorders.addNewTop();
        if (!tblBorders.isSetRight()) tblBorders.addNewRight();
        if (!tblBorders.isSetInsideH()) tblBorders.addNewInsideH();
        if (!tblBorders.isSetInsideV()) tblBorders.addNewInsideV();
        tblBorders.getBottom().setSz(borderSize);
        tblBorders.getLeft().setSz(borderSize);
        tblBorders.getTop().setSz(borderSize);
        tblBorders.getRight().setSz(borderSize);
        tblBorders.getInsideH().setSz(borderSize);
        tblBorders.getInsideV().setSz(borderSize);
    }

    /**
     * set border style
     * <p>
     * TableTools.setBorder(table::setLeftBorder, border);
     * </p>
     * 
     * @param consumer
     * @param border
     */
    public static void setBorder(FourthConsumer<XWPFBorderType, Integer, Integer, String> consumer,
            BorderStyle border) {
        if (null != border) consumer.accept(border.getType(), border.getSize(), 0, border.getColor());
    }

    public static void initBasicTable(XWPFTable table, int col, float width, TableStyle style) {
        int defaultBorderSize = 4;
        widthTable(table, width, col);
        borderTable(table, defaultBorderSize);
        styleTable(table, style);
    }

    public static boolean isInsideTable(XWPFRun run) {
        return ((XWPFParagraph) run.getParent()).getPartType() == BodyType.TABLECELL;
    }

    public static void styleTable(XWPFTable table, TableStyle style) {
        StyleUtils.styleTable(table, style);
    }

    public static void styleTableRow(XWPFTableRow row, RowStyle rowStyle) {
        StyleUtils.styleTableRow(row, rowStyle);
    }

    public static void styleTableCell(XWPFTableCell cell, CellStyle cellStyle) {
        StyleUtils.styleTableCell(cell, cellStyle);
    }

    public static int obtainRowSize(XWPFTable table) {
        return table.getRows().size();
    }

    public static int obtainColumnSize(XWPFTable table) {
        return table.getRows().get(0).getTableCells().size();
    }

    public static CTTblGrid getTblGrid(XWPFTable table) {
        CTTblGrid tblGrid = table.getCTTbl().getTblGrid();
        if (null == tblGrid) {
            tblGrid = table.getCTTbl().addNewTblGrid();
        }
        return tblGrid;
    }

    public static CTTblLayoutType getTblLayout(XWPFTable table) {
        CTTblPr tblPr = getTblPr(table);
        return tblPr.isSetTblLayout() ? tblPr.getTblLayout() : tblPr.addNewTblLayout();
    }

    private static CTTblPr getTblPr(XWPFTable table) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (null == tblPr) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
        return tblPr;
    }

    public static CTTcPr getTcPr(XWPFTableCell cell) {
        CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();
        return tcPr;
    }

    @Deprecated
    public static void widthTable(XWPFTable table, float[] colWidths) {
        float widthCM = 0;
        for (float w : colWidths) {
            widthCM += w;
        }
        int width = UnitUtils.cm2Twips(widthCM);
        CTTblPr tblPr = getTblPr(table);
        CTTblWidth tblW = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblW.setType(0 == width ? STTblWidth.AUTO : STTblWidth.DXA);
        tblW.setW(BigInteger.valueOf(width));

        if (0 != width) {
            CTTblGrid tblGrid = getTblGrid(table);
            for (float w : colWidths) {
                CTTblGridCol addNewGridCol = tblGrid.addNewGridCol();
                addNewGridCol.setW(BigInteger.valueOf(UnitUtils.cm2Twips(w)));
            }
        }
    }

    @Deprecated
    public static void widthTable(XWPFTable table, float widthCM, int cols) {
        int width = UnitUtils.cm2Twips(widthCM);
        CTTblPr tblPr = getTblPr(table);
        CTTblWidth tblW = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblW.setType(0 == width ? STTblWidth.AUTO : STTblWidth.DXA);
        tblW.setW(BigInteger.valueOf(width));

        if (0 != width) {
            CTTblGrid tblGrid = getTblGrid(table);
            for (int j = 0; j < cols; j++) {
                CTTblGridCol addNewGridCol = tblGrid.addNewGridCol();
                addNewGridCol.setW(BigInteger.valueOf(width / cols));
            }
        }
    }

}
