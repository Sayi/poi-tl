package com.deepoove.poi.util;

import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridCol;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

/**
 * XWPFTable 增强工具类 <br/>
 * 
 * <ul>
 * <li>合并行单元格</li>
 * <li>合并列单元格</li>
 * <li>设置每列宽度</li>
 * <li>边框大小</li>
 * </ul>
 * 
 * @author Sayi
 * @version 1.4.0
 */
public final class TableTools {

    /**
     * 合并行单元格
     * 
     * @param table
     *            表格对象
     * @param row
     *            行
     * @param fromCol
     *            起始列
     * @param toCol
     *            结束列
     */
    public static void mergeCellsHorizonal(XWPFTable table, int row, int fromCol, int toCol) {
        if (toCol <= fromCol) return;
        XWPFTableCell cell = table.getRow(row).getCell(fromCol);
        CTTcPr tcPr = getTcPr(cell);
        XWPFTableRow rowTable = table.getRow(row);
        for (int colIndex = fromCol + 1; colIndex <= toCol; colIndex++) {
            rowTable.getCtRow().removeTc(colIndex);
            rowTable.removeCell(colIndex);
        }

        tcPr.addNewGridSpan();
        tcPr.getGridSpan().setVal(BigInteger.valueOf((long) (toCol - fromCol + 1)));
    }

    /**
     * 合并列单元格
     * 
     * @param table
     *            表格对象
     * @param col
     *            列
     * @param fromRow
     *            起始行
     * @param toRow
     *            结束行
     */
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
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
     * 设置表格每列的宽度
     * 
     * @param table
     *            表格对象
     * @param widths
     *            每列的宽度，单位CM
     */
    public static void widthTable(XWPFTable table, float[] widths) {
        float widthCM = 0;
        for (float w : widths) {
            widthCM += w;
        }
        long width = (int) (widthCM / 2.54 * 1440);
        CTTblPr tblPr = getTblPr(table);
        CTTblWidth tblW = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblW.setType(0 == width ? STTblWidth.AUTO : STTblWidth.DXA);
        tblW.setW(BigInteger.valueOf(width));

        if (0 != width) {
            CTTblGrid tblGrid = table.getCTTbl().getTblGrid();
            if (null == tblGrid) {
                tblGrid = table.getCTTbl().addNewTblGrid();
            }

            for (float w : widths) {
                CTTblGridCol addNewGridCol = tblGrid.addNewGridCol();
                addNewGridCol.setW(BigInteger.valueOf((long) (w / 2.54 * 1440)));
            }
        }
    }

    /**
     * 设置表格边框
     * 
     * @param table
     * @param size
     */
    public static void borderTable(XWPFTable table, int size) {
        CTTblPr tblPr = getTblPr(table);
        CTTblBorders tblBorders = tblPr.getTblBorders();
        BigInteger borderSize = BigInteger.valueOf(size);
        tblBorders.getBottom().setSz(borderSize);
        tblBorders.getLeft().setSz(borderSize);
        tblBorders.getTop().setSz(borderSize);
        tblBorders.getRight().setSz(borderSize);
        tblBorders.getInsideH().setSz(borderSize);
        tblBorders.getInsideV().setSz(borderSize);
    }

    private static CTTblPr getTblPr(XWPFTable table) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (null == tblPr) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
        return tblPr;
    }

    private static CTTcPr getTcPr(XWPFTableCell cell) {
        CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr()
                : cell.getCTTc().addNewTcPr();
        return tcPr;
    }

}
