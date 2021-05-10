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

package com.deepoove.poi.render.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.resolver.Resolver;
import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.ParagraphContext;
import com.deepoove.poi.xwpf.ParentContext;
import com.deepoove.poi.xwpf.RunBodyContext;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

public class InlineIterableProcessor extends AbstractIterableProcessor {

    public InlineIterableProcessor(XWPFTemplate template, Resolver resolver, RenderDataCompute renderDataCompute) {
        super(template, resolver, renderDataCompute);
    }

    @Override
    public void visit(InlineIterableTemplate iterableTemplate) {
        logger.info("Process InlineIterableTemplate:{}", iterableTemplate);
        super.visit((IterableTemplate) iterableTemplate);
    }

    @Override
    protected void handleNever(IterableTemplate iterableTemplate, BodyContainer bodyContainer) {
        RunBodyContext parentContext = new ParagraphContext(
                new XWPFParagraphWrapper((XWPFParagraph) iterableTemplate.getStartRun().getParent()));

        Integer startRunPos = iterableTemplate.getStartMark().getRunPos();
        Integer endRunPos = iterableTemplate.getEndMark().getRunPos();

        for (int i = endRunPos - 1; i > startRunPos; i--) {
            parentContext.removeRun(i);
        }
    }

    @Override
    protected void handleIterable(IterableTemplate iterableTemplate, BodyContainer bodyContainer, Iterable<?> compute) {
        RunTemplate start = iterableTemplate.getStartMark();
        RunTemplate end = iterableTemplate.getEndMark();
        RunBodyContext parentContext = new ParagraphContext(
                new XWPFParagraphWrapper((XWPFParagraph) start.getRun().getParent()));

        Integer startRunPos = start.getRunPos();
        Integer endRunPos = end.getRunPos();
        IterableContext context = new IterableContext(startRunPos, endRunPos);

        EnvIterator.foreach(compute.iterator(), model -> next(iterableTemplate, parentContext, context, model));

        // clear self iterable template
        for (int i = endRunPos - 1; i > startRunPos; i--) {
            parentContext.removeRun(i);
        }
    }

    @Override
    public void next(IterableTemplate iterable, ParentContext parentContext, IterableContext context, Object model) {
        RunBodyContext paragraphContext = (RunBodyContext) parentContext;
        RunTemplate end = iterable.getEndMark();
        CTR endCtr = end.getRun().getCTR();
        int startPos = context.getStart();
        int endPos = context.getEnd();

        // copy position cursor
        int insertPostionCursor = end.getRunPos();

        // copy content
        List<XWPFRun> runs = paragraphContext.getRuns();
        List<XWPFRun> copies = new ArrayList<XWPFRun>();
        for (int i = startPos + 1; i < endPos; i++) {
            insertPostionCursor = end.getRunPos();

            XWPFRun xwpfRun = runs.get(i);
            XWPFRun insertNewRun = paragraphContext.insertNewRun(xwpfRun, insertPostionCursor);
            XWPFRun replaceXwpfRun = paragraphContext.createRun(xwpfRun, paragraphContext.getTarget());
            paragraphContext.setAndUpdateRun(replaceXwpfRun, insertNewRun, insertPostionCursor);

            XmlCursor newCursor = endCtr.newCursor();
            newCursor.toPrevSibling();
            XmlObject object = newCursor.getObject();
            XWPFRun copy = paragraphContext.createRun(object, paragraphContext.getTarget());
            DrawingSupport.updateDocPrId(copy);
            copies.add(copy);
            paragraphContext.setAndUpdateRun(copy, replaceXwpfRun, insertPostionCursor);
        }

        // re-parse
        List<MetaTemplate> templates = this.resolver.resolveXWPFRuns(copies);

        // render
        process(templates, model);
    }

}
