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
package com.deepoove.poi.data.style;

import java.io.Serializable;

import org.apache.poi.xwpf.usermodel.TableRowAlign;

import com.deepoove.poi.util.UnitUtils;

public class TableStyle implements Serializable {

    private static final long serialVersionUID = 1L;

    private TableRowAlign align;

    private BorderStyle leftBorder;
    private BorderStyle rightBorder;
    private BorderStyle topBorder;
    private BorderStyle bottomBorder;
    private BorderStyle insideHBorder;
    private BorderStyle insideVBorder;

    private int leftCellMargin;
    private int topCellMargin;
    private int rightCellMargin;
    private int bottomCellMargin;

    private Double indentation;

    /**
     * auto, xx% or xx in twips
     * 
     * @see #{@link UnitUtils#cm2Twips()}
     */
    private String width;

    /**
     * in twips for none pattern or percent for fit
     * 
     * @see #{@link UnitUtils#cm2Twips()}
     */
    private int[] colWidths;

    public TableRowAlign getAlign() {
        return align;
    }

    public void setAlign(TableRowAlign align) {
        this.align = align;
    }

    public BorderStyle getLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(BorderStyle leftBorder) {
        this.leftBorder = leftBorder;
    }

    public BorderStyle getRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(BorderStyle rightBorder) {
        this.rightBorder = rightBorder;
    }

    public BorderStyle getTopBorder() {
        return topBorder;
    }

    public void setTopBorder(BorderStyle topBorder) {
        this.topBorder = topBorder;
    }

    public BorderStyle getBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(BorderStyle bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public BorderStyle getInsideHBorder() {
        return insideHBorder;
    }

    public void setInsideHBorder(BorderStyle insideHBorder) {
        this.insideHBorder = insideHBorder;
    }

    public BorderStyle getInsideVBorder() {
        return insideVBorder;
    }

    public void setInsideVBorder(BorderStyle insideVBorder) {
        this.insideVBorder = insideVBorder;
    }

    public String getWidth() {
        return width;
    }

    /**
     * @param width auto, xx% or xx in twips
     */
    public void setWidth(String width) {
        this.width = width;
    }

    public int[] getColWidths() {
        return colWidths;
    }

    /**
     * @param colWidths in twips #{@link UnitUtils#cm2Twips()}
     */
    public void setColWidths(int[] colWidths) {
        this.colWidths = colWidths;
    }

    public int getLeftCellMargin() {
        return leftCellMargin;
    }

    public void setLeftCellMargin(int leftCellMargin) {
        this.leftCellMargin = leftCellMargin;
    }

    public int getTopCellMargin() {
        return topCellMargin;
    }

    public void setTopCellMargin(int topCellMargin) {
        this.topCellMargin = topCellMargin;
    }

    public int getRightCellMargin() {
        return rightCellMargin;
    }

    public void setRightCellMargin(int rightCellMargin) {
        this.rightCellMargin = rightCellMargin;
    }

    public int getBottomCellMargin() {
        return bottomCellMargin;
    }

    public void setBottomCellMargin(int bottomCellMargin) {
        this.bottomCellMargin = bottomCellMargin;
    }

    public Double getIndentation() {
        return indentation;
    }

    public void setIndentation(Double indentation) {
        this.indentation = indentation;
    }

}
