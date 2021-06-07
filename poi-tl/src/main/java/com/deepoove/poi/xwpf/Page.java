package com.deepoove.poi.xwpf;

import java.math.BigInteger;

import com.deepoove.poi.util.UnitUtils;

public class Page {

    public static final Page A4_NORMAL = new Page(210, 297, new PageMargin(1440, 1440, 1800, 1800));
    public static final Page A4_NARROW = new Page(210, 297, new PageMargin(720, 720, 720, 720));
    public static final Page A4_MEDIUM = new Page(210, 297, new PageMargin(1440, 1440, 1080, 1080));
    public static final Page A4_EXTEND = new Page(210, 297, new PageMargin(1440, 1440, 2880, 2880));

    private int pageWidth;
    private int pageHeight;
    private PageMargin pageMar;

    Page(int width, int height, PageMargin pageMar) {
        this.pageWidth = width;
        this.pageHeight = height;
        this.pageMar = pageMar;
    }

    public BigInteger contentWidth() {
        int pageWidthTwips = UnitUtils.cm2Twips(this.pageWidth / 10.0);
        return BigInteger.valueOf(pageWidthTwips - pageMar.left.intValue() - pageMar.right.intValue());
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
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
