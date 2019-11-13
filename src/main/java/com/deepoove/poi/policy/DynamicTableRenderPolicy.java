/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.policy;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.TableTools;

/**
 * 支持表格内的文本模板动态持有XWPFTable对象 <br/>
 * 
 * <p>
 * 通常使用在一个表格的样式已经制作好，我们仅仅需要处理表格内部某些单元格， 通过此类可以获得整个表格的XWPFTable对象，进而使用POI处理这个表格
 * </p>
 * 
 * @author Sayi 卅一
 * @version 0.0.3
 */
public abstract class DynamicTableRenderPolicy implements RenderPolicy {

    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        NiceXWPFDocument doc = template.getXWPFDocument();
        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        XWPFRun run = runTemplate.getRun();
        run.setText("", 0);
        try {
            if (!TableTools.isInsideTable(run)) {
                throw new IllegalStateException("The template tag " + runTemplate.getSource() + " must be inside a table");
            }
            // w:tbl-w:tr-w:tc-w:p-w:tr
            XmlCursor newCursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
            newCursor.toParent();
            newCursor.toParent();
            newCursor.toParent();
            XmlObject object = newCursor.getObject();
            XWPFTable table = doc.getTableByCTTbl((CTTbl) object);
            render(table, data);
        } catch (Exception e) {
            throw new RenderException("dynamic table error:" + e.getMessage(), e);
        }
    }

    /**
     * @param table
     *            表格
     * @param data
     *            数据
     */
    public abstract void render(XWPFTable table, Object data);

}
