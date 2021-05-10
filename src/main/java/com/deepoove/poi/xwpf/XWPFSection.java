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

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHdrFtrRef;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

public class XWPFSection {

    protected CTSectPr sectPr;

    protected XWPFPageMargin pageMargin;
    protected XWPFPageSize pageSize;

    public XWPFSection(CTSectPr sectPr) {
        this.sectPr = sectPr;
        this.pageMargin = new XWPFPageMargin(sectPr.getPgMar());
        this.pageSize = new XWPFPageSize(sectPr.getPgSz());
    }

    public boolean haveHeader(String headerRelationId) {
        if (null == headerRelationId) return false;
        List<CTHdrFtrRef> referenceList = sectPr.getHeaderReferenceList();
        for (CTHdrFtrRef ref : referenceList) {
            String id = ref.getId();
            if (id.equals(headerRelationId)) return true;
        }
        return false;
    }

    public boolean haveFooter(String footerRelationId) {
        if (null == footerRelationId) return false;
        List<CTHdrFtrRef> referenceList = sectPr.getFooterReferenceList();
        for (CTHdrFtrRef ref : referenceList) {
            String id = ref.getId();
            if (id.equals(footerRelationId)) return true;
        }
        return false;
    }

    public CTSectPr getSectPr() {
        return sectPr;
    }

    public XWPFPageMargin getPageMargin() {
        return pageMargin;
    }

    public XWPFPageSize getPageSize() {
        return pageSize;
    }

    public int getPageWidth() {
        return pageSize.getWidth();
    }

    public int getPageHeight() {
        return pageSize.getHeight();
    }

    public BigInteger getPageContentWidth() {
        return BigInteger
                .valueOf(getPageWidth() - pageMargin.getLeft().longValue() - pageMargin.getRight().longValue());
    }
}
