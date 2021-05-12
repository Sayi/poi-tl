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

package com.deepoove.poi.xwpf;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;

import com.deepoove.poi.exception.ReflectionException;

/**
 * This is a utility class so that I can get access to the protected fields
 * within XWPFNumbering.
 */
public class XWPFNumberingWrapper {

    private final XWPFNumbering numbering;

    private List<XWPFNum> nums;

    private List<XWPFAbstractNum> abstractNums;

    public XWPFNumberingWrapper(XWPFNumbering numbering) {
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
            throw new ReflectionException("nums", XWPFNumbering.class, e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<XWPFAbstractNum> tryGetAbstractNums() {
        try {
            Field field = XWPFNumbering.class.getDeclaredField("abstractNums");
            field.setAccessible(true);
            return (List<XWPFAbstractNum>) field.get(this.numbering);
        } catch (Exception e) {
            throw new ReflectionException("abstractNums", XWPFNumbering.class, e);
        }
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
        if (abstractNums == null) return BigInteger.ZERO;
        BigInteger max = BigInteger.ZERO;
        for (XWPFAbstractNum abstractNum : abstractNums) {
            CTAbstractNum ctAbstractNum = abstractNum.getCTAbstractNum();
            BigInteger abstractNumId = ctAbstractNum.getAbstractNumId();
            if (null != abstractNumId && abstractNumId.compareTo(max) == 1) {
                max = abstractNumId;
            }
        }
        return max;
    }

    public BigInteger getNextAbstractNumID() {
        return getMaxIdOfAbstractNum().add(BigInteger.valueOf(1));
    }

}
