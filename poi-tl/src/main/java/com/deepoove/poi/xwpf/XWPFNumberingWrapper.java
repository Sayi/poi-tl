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

import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;

import com.deepoove.poi.util.NextIDUtils;

/**
 * This is a utility class so that I can get access to the protected fields within XWPFNumbering.
 */
public class XWPFNumberingWrapper {

    private final XWPFNumbering numbering;

    private List<XWPFNum> nums;

    private List<XWPFAbstractNum> abstractNums;

    public XWPFNumberingWrapper(XWPFNumbering numbering) {
        this.numbering = numbering;
        this.nums = numbering.getNums();
        this.abstractNums = numbering.getAbstractNums();
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

    public BigInteger getNextAbstractNumID() {
        return NextIDUtils.getAbstractNumMaxId(numbering).add(BigInteger.ONE);
    }

}
