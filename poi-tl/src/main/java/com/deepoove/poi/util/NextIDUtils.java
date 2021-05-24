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
package com.deepoove.poi.util;

import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFComments;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;

public class NextIDUtils {

    public static BigInteger getCommentMaxId(XWPFComments docComments) {
        List<XWPFComment> comments = docComments.getComments();
        BigInteger max = BigInteger.ZERO;
        if (null == comments) return max;
        for (XWPFComment comment : comments) {
            BigInteger id = comment.getCtComment().getId();
            if (null != id && id.compareTo(max) == 1) {
                max = id;
            }
        }
        return max;
    }

    public static BigInteger getAbstractNumMaxId(XWPFNumbering numbering) {
        List<XWPFAbstractNum> abstractNums = numbering.getAbstractNums();
        BigInteger max = BigInteger.ZERO;
        if (abstractNums == null) return max;
        for (XWPFAbstractNum abstractNum : abstractNums) {
            BigInteger id = abstractNum.getCTAbstractNum().getAbstractNumId();
            if (null != id && id.compareTo(max) == 1) {
                max = id;
            }
        }
        return max;
    }

}
