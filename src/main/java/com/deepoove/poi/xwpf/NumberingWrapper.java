package com.deepoove.poi.xwpf;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a utility class so that I can get access to the protected fields
 * within XWPFNumbering.
 */
public class NumberingWrapper {

    private static Logger logger = LoggerFactory.getLogger(NumberingWrapper.class);

    private final XWPFNumbering numbering;

    private List<XWPFNum> nums;

    private List<XWPFAbstractNum> abstractNums;

    public NumberingWrapper(XWPFNumbering numbering) {
        this.numbering = numbering;
        this.nums = tryGetNums();
        this.abstractNums = tryGetAbstractNums();
    }

    @SuppressWarnings("unchecked")
    private List<XWPFNum> tryGetNums() {
        try {
            Field field = XWPFNumbering.class.getDeclaredField("nums");
            field.setAccessible(true);
            return (List<XWPFNum>) field.get(this.numbering);
        } catch (Exception e) {
            logger.error("Cannot get XWPFNumbering nums", e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<XWPFAbstractNum> tryGetAbstractNums() {
        try {
            Field field = XWPFNumbering.class.getDeclaredField("abstractNums");
            field.setAccessible(true);
            return (List<XWPFAbstractNum>) field.get(this.numbering);
        } catch (Exception e) {
            logger.error("Cannot get XWPFNumbering abstractNums", e);
        }
        return null;
    }

    public List<XWPFAbstractNum> getAbstractNums() {
        return abstractNums;
    }

    public List<XWPFNum> getNums() {
        return nums;
    }

    public XWPFNumbering getNumbering() {
        return numbering;
    }

    public int getAbstractNumsSize() {
        return abstractNums == null ? 0 : abstractNums.size();
    }
    
    public BigInteger getMaxIdOfAbstractNum() {
        if (abstractNums == null) return BigInteger.valueOf(0);
        BigInteger max = BigInteger.valueOf(0);
        for (XWPFAbstractNum abstractNum : abstractNums) {
            CTAbstractNum ctAbstractNum = abstractNum.getCTAbstractNum();
            BigInteger abstractNumId = ctAbstractNum.getAbstractNumId();
            if (null != abstractNumId && abstractNumId.compareTo(max) == 1) {
                max = abstractNumId;
            }
        }
        return max;
    }

}
