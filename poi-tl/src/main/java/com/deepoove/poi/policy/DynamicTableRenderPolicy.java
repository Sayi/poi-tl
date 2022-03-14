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
package com.deepoove.poi.policy;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.TableTools;

/**
 * Support the text template(anywhere in table) in the table to dynamically hold
 * the XWPFTable object.
 * 
 * <p>
 * When the style of a table has been designed, we only need to process some
 * cells in the table. Through this class, we can get the XWPFTable object of
 * the entire table, and then use POI to process the table
 * </p>
 * 
 * @author Sayi
 */
public abstract class DynamicTableRenderPolicy extends AbstractRenderPolicy<Object> {

    public abstract void render(XWPFTable table, Object data) throws Exception;

    @Override
    public void doRender(RenderContext<Object> context) throws Exception {
        RunTemplate runTemplate = (RunTemplate) context.getEleTemplate();
        XWPFRun run = runTemplate.getRun();
        run.setText("", 0);
        try {
            if (!TableTools.isInsideTable(run)) {
                throw new IllegalStateException(
                        "The template tag " + runTemplate.getSource() + " must be inside a table");
            }
            XWPFTableCell cell = (XWPFTableCell) ((XWPFParagraph) run.getParent()).getBody();
            XWPFTable table = cell.getTableRow().getTable();
            render(table, context.getData());
        } catch (Exception e) {
            throw new RenderException("Dynamic render table error:" + e.getMessage(), e);
        }
    }

}
