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

package com.deepoove.poi.render.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.resolver.Resolver;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.ParentContext;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

public class IterableProcessor extends AbstractIterableProcessor {

    public IterableProcessor(XWPFTemplate template, Resolver resolver, RenderDataCompute renderDataCompute) {
        super(template, resolver, renderDataCompute);
    }

    @Override
    public void visit(IterableTemplate iterableTemplate) {
        logger.info("Process iterableTemplate:{}", iterableTemplate);
        super.visit(iterableTemplate);
    }

    @Override
    protected void handleNever(IterableTemplate iterableTemplate, BodyContainer bodyContainer) {
        XWPFParagraph startParagraph = (XWPFParagraph) iterableTemplate.getStartRun().getParent();
        XWPFParagraph endParagraph = (XWPFParagraph) iterableTemplate.getEndRun().getParent();

        int startPos = bodyContainer.getPosOfParagraphCTP(startParagraph.getCTP());
        int endPos = bodyContainer.getPosOfParagraphCTP(endParagraph.getCTP());

        // remove content
        for (int i = endPos - 1; i > startPos; i--) {
            bodyContainer.removeBodyElement(i);
        }

        XWPFParagraphWrapper startParagraphWrapper = new XWPFParagraphWrapper(startParagraph);
        XWPFParagraphWrapper endParagraphWrapper = new XWPFParagraphWrapper(endParagraph);
        Integer startRunPos = iterableTemplate.getStartMark().getRunPos();
        Integer endRunPos = iterableTemplate.getEndMark().getRunPos();

        // remove run content
        List<XWPFRun> startRuns = startParagraph.getRuns();
        int startSize = startRuns.size();
        for (int i = startSize - 1; i > startRunPos; i--) {
            startParagraphWrapper.removeRun(i);
        }
        for (int i = endRunPos - 1; i >= 0; i--) {
            endParagraphWrapper.removeRun(i);
        }
    }

    @Override
    protected void handleIterable(IterableTemplate iterableTemplate, BodyContainer bodyContainer, Iterable<?> compute) {
        CTP startCtp = ((XWPFParagraph) iterableTemplate.getStartRun().getParent()).getCTP();
        CTP endCtp = ((XWPFParagraph) iterableTemplate.getEndRun().getParent()).getCTP();

        int startPos = bodyContainer.getPosOfParagraphCTP(startCtp);
        int endPos = bodyContainer.getPosOfParagraphCTP(endCtp);

        NumberingContinue numbringContinue = NumberingContinue.of(bodyContainer, startPos, endPos, iterableTemplate);
        IterableContext context = new IterableContext(startPos, endPos, numbringContinue);

        foreach(iterableTemplate, bodyContainer, context, compute.iterator());

        // clear self iterable template
        for (int i = endPos - 1; i > startPos; i--) {
            bodyContainer.removeBodyElement(i);
        }
    }

    @Override
    public void next(IterableTemplate iterable, ParentContext parentContext, IterableContext context, Object model) {
        BodyContainer bodyContainer = (BodyContainer) parentContext;
        XWPFParagraph endParagraph = (XWPFParagraph) iterable.getEndRun().getParent();
        CTP endCtp = endParagraph.getCTP();

        int start = context.getStart();
        int end = context.getEnd();
        context.getNumberingContinue().resetCache();

        // copy positon cursor
        XmlCursor insertPostionCursor = endCtp.newCursor();

        // copy content
        List<IBodyElement> bodyElements = bodyContainer.getBodyElements();
        List<IBodyElement> copies = new ArrayList<IBodyElement>();
        for (int i = start + 1; i < end; i++) {
            IBodyElement iBodyElement = bodyElements.get(i);
            if (iBodyElement.getElementType() == BodyElementType.PARAGRAPH) {
                insertPostionCursor = endCtp.newCursor();
                XWPFParagraph insertNewParagraph = bodyContainer.insertNewParagraph(insertPostionCursor);
                // find insert paragraph pos
                int paraPos = bodyContainer.getParaPos(insertNewParagraph);
                bodyContainer.setParagraph((XWPFParagraph) iBodyElement, paraPos);
                // re-update ctp reference
                insertPostionCursor = endCtp.newCursor();
                insertPostionCursor.toPrevSibling();
                XmlObject object = insertPostionCursor.getObject();
                XWPFParagraph copy = new XWPFParagraph((CTP) object, bodyContainer.getTarget());
                // update docpr
                DrawingSupport.updateDocPrId(copy);
                // update numbering
                context.getNumberingContinue().updateNumbering((XWPFParagraph) iBodyElement, copy);

                copies.add(copy);
                bodyContainer.updateBodyElements(insertNewParagraph, copy);
                bodyContainer.setParagraph(copy, paraPos);
            } else if (iBodyElement.getElementType() == BodyElementType.TABLE) {
                insertPostionCursor = endCtp.newCursor();
                XWPFTable insertNewTbl = bodyContainer.insertNewTbl(insertPostionCursor);
                // find insert table pos
                int tablePos = bodyContainer.getTablePos(insertNewTbl);
                bodyContainer.setTable(tablePos, (XWPFTable) iBodyElement);

                insertPostionCursor = endCtp.newCursor();
                insertPostionCursor.toPrevSibling();
                XmlObject object = insertPostionCursor.getObject();

                XWPFTable copy = new XWPFTable((CTTbl) object, bodyContainer.getTarget());
                DrawingSupport.updateDocPrId(copy);
                copies.add(copy);
                bodyContainer.updateBodyElements(insertNewTbl, copy);
                bodyContainer.setTable(tablePos, copy);
            }
        }

        // re-parse
        List<MetaTemplate> templates = this.resolver.resolveBodyElements(copies);

        // render
        process(templates, model);
    }

}
