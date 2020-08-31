/*
 * Copyright 2014-2020 Sayi
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
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;

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

    /**
     * auto, xx% or xx in twips
     * 
     * @see #{@link UnitUtils#cm2Twips()}
     */
    private String width;

    /**
     * in twips
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
    
    public static class BorderStyle implements Serializable {

        public static final BorderStyle DEFAULT = new BorderStyle();

        static {
            DEFAULT.setSize(4);
            DEFAULT.setColor("auto");
            DEFAULT.setType(XWPFBorderType.SINGLE);
        }

        private static final long serialVersionUID = 1L;

        /**
         * specified in measurements of eighths of a point
         */
        private int size;
        /**
         * A value of auto is also permitted and will allow the consuming word processor
         * to determine the color.
         */
        private String color;

        private XWPFBorderType type;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public XWPFBorderType getType() {
            return type;
        }

        public void setType(XWPFBorderType type) {
            this.type = type;
        }

    }


}
