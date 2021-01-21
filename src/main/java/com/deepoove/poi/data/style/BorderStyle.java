package com.deepoove.poi.data.style;

import java.io.Serializable;

import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;

public class BorderStyle implements Serializable {

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
