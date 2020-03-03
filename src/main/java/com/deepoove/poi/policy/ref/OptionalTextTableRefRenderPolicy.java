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
package com.deepoove.poi.policy.ref;

import java.util.List;
import java.util.Objects;

import javax.xml.namespace.QName;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.impl.values.TypeStoreUser;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTTblPrImpl;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * 可选文字匹配XWPFTable
 * 
 * @author Sayi
 * @version
 */
public abstract class OptionalTextTableRefRenderPolicy extends ReferenceRenderPolicy<XWPFTable>
        implements OptionalText {

    protected static final QName TBLCAPTION = new QName(
            "http://schemas.openxmlformats.org/wordprocessingml/2006/main", "tblCaption");
    protected static final QName TBLDESCRIPTION = new QName(
            "http://schemas.openxmlformats.org/wordprocessingml/2006/main", "tblDescription");
    protected static final QName VAL = new QName(
            "http://schemas.openxmlformats.org/wordprocessingml/2006/main", "val");

    @Override
    protected XWPFTable locate(XWPFTemplate template) {
        logger.info("Try locate the XWPFTable object which mathing optional text [{}]...",
                optionalText());
        NiceXWPFDocument xwpfDocument = template.getXWPFDocument();
        List<XWPFTable> tables = xwpfDocument.getAllTables();
        for (XWPFTable table : tables) {
            CTTbl ctTbl = table.getCTTbl();
            CTTblPr tblPr = ctTbl.getTblPr();
            CTTblPrImpl cTTblPrImpl = (CTTblPrImpl) tblPr;
            String caption = getTblStringVal(cTTblPrImpl, TBLCAPTION);
            if (Objects.equals(optionalText(), caption)) return table;
            String description = getTblStringVal(cTTblPrImpl, TBLDESCRIPTION);
            if (Objects.equals(optionalText(), description)) return table;
        }

        return null;
    }

    private String getTblStringVal(CTTblPrImpl cTTblPrImpl, QName qname) {
        synchronized (cTTblPrImpl.monitor()) {
            // check_orphaned();
            if (cTTblPrImpl.get_store().count_elements(qname) == 0) return null;
            // CTString localCTString = (CTString)
            // cTTblPrImpl.get_store().find_element_user(TBLCAPTION, 0);
            TypeStoreUser find_element_user = cTTblPrImpl.get_store().find_element_user(qname, 0);
            if (null == find_element_user) return null;
            TypeStoreUser find_attribute_user = find_element_user.get_store()
                    .find_attribute_user(VAL);
            if (null == find_attribute_user) return null;
            return ((SimpleValue) find_attribute_user).getStringValue();
        }
    }

}
