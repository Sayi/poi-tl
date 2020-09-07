package com.deepoove.poi.xwpf;

import java.math.BigInteger;

class Page {

    public static final Page A4_NORMAL = new Page(210, 297, new PageMargin(1440, 1440, 1800, 1800));
    public static final Page A4_NARROW = new Page(210, 297, new PageMargin(720, 720, 720, 720));
    public static final Page A4_MEDIUM = new Page(210, 297, new PageMargin(1440, 1440, 1080, 1080));
    public static final Page A4_EXTEND = new Page(210, 297, new PageMargin(1440, 1440, 2880, 2880));

    private int widthInMilli;
    private int heightInMilli;
    private PageMargin pageMar;

    Page(int width, int height, PageMargin pageMar) {
        this.widthInMilli = width;
        this.heightInMilli = height;
        this.pageMar = pageMar;
    }

    public BigInteger tableWidth() {
        long fullWidth = (long) (this.widthInMilli / 10 / 2.54 * 1440);
        return BigInteger.valueOf(fullWidth - pageMar.left.longValue() - pageMar.right.longValue());
    }

    public int getWidthInMilli() {
        return widthInMilli;
    }

    public int getHeightInMilli() {
        return heightInMilli;
    }

    public PageMargin getPageMar() {
        return pageMar;
    }

    static class PageMargin {

        public PageMargin(long t, long b, long r, long l) {
            this.top = BigInteger.valueOf(t);
            this.bottom = BigInteger.valueOf(b);
            this.right = BigInteger.valueOf(r);
            this.left = BigInteger.valueOf(l);
        }

        private BigInteger top;
        private BigInteger bottom;
        private BigInteger right;
        private BigInteger left;
        private BigInteger gutter = BigInteger.ZERO;

        public BigInteger getTop() {
            return top;
        }

        public BigInteger getBottom() {
            return bottom;
        }

        public BigInteger getRight() {
            return right;
        }

        public BigInteger getLeft() {
            return left;
        }

        public BigInteger getGutter() {
            return gutter;
        }

    }

}
