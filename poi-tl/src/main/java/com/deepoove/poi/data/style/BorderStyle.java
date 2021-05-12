package com.deepoove.poi.data.style;

import java.io.Serializable;

import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;

public class BorderStyle implements Serializable {

    public static final BorderStyle DEFAULT = BorderStyle.builder().build();

    static {
        DEFAULT.setSize(8 * 1 / 2);
        DEFAULT.setColor("auto");
        DEFAULT.setType(XWPFBorderType.SINGLE);
    }

    private static final long serialVersionUID = 1L;

    /**
     * specified in measurements of eighths of a point
     */
    private int size;
    /**
     * A value of auto is also permitted and will allow the consuming word processor to determine the color.
     */
    private String color;

    private XWPFBorderType type;

    private int space = 0;

    public BorderStyle() {
    }

    private BorderStyle(Builder builder) {
        this.size = builder.size;
        this.color = builder.color;
        this.type = builder.type;
        this.space = builder.space;
    }

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

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    /**
     * Creates builder to build {@link BorderStyle}.
     * 
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link BorderStyle}.
     */
    public static final class Builder {
        private int size;
        private String color;
        private XWPFBorderType type;
        private int space;

        private Builder() {
        }

        public Builder withSize(int size) {
            this.size = size;
            return this;
        }

        public Builder withColor(String color) {
            this.color = color;
            return this;
        }

        public Builder withType(XWPFBorderType type) {
            this.type = type;
            return this;
        }

        public Builder withSpace(int space) {
            this.space = space;
            return this;
        }

        public BorderStyle build() {
            return new BorderStyle(this);
        }
    }

}
