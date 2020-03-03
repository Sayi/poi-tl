package com.deepoove.poi.render.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

public class InlineIterableProcessor extends AbstractIterableProcessor {

    public InlineIterableProcessor(XWPFTemplate template, RenderDataCompute renderDataCompute) {
        super(template, renderDataCompute);
    }

    @Override
    public void visit(InlineIterableTemplate iterableTemplate) {

        logger.info("Process InlineIterableTemplate:{}", iterableTemplate);
        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(iterableTemplate);

        Object compute = renderDataCompute.compute(iterableTemplate.getStartMark().getTagName());
        int times = conditionTimes(compute);

        if (TIMES_ONCE == times) {

            RenderDataCompute dataCompute = template.getConfig().getRenderDataComputeFactory().newCompute(compute);
            new DocumentProcessor(this.template, dataCompute).process(iterableTemplate.getTemplates());

        } else if (TIMES_N == times) {

            RunTemplate start = iterableTemplate.getStartMark();
            RunTemplate end = iterableTemplate.getEndMark();

            XWPFRun startRun = start.getRun();
            XWPFRun endRun = end.getRun();

            XWPFParagraph currentParagraph = (XWPFParagraph) startRun.getParent();
            XWPFParagraphWrapper paragraphWrapper = new XWPFParagraphWrapper(currentParagraph);

            Integer startRunPos = start.getRunPos();
            Integer endRunPos = end.getRunPos();

            CTR endCtr = endRun.getCTR();

            Iterable<?> model = (Iterable<?>) compute;
            Iterator<?> iterator = model.iterator();
            while (iterator.hasNext()) {
                // copy position cursor
                int insertPostionCursor = end.getRunPos();

                // copy content
                List<XWPFRun> runs = currentParagraph.getRuns();
                List<XWPFRun> copies = new ArrayList<XWPFRun>();
                for (int i = startRunPos + 1; i < endRunPos; i++) {
                    insertPostionCursor = end.getRunPos();

                    XWPFRun xwpfRun = runs.get(i);
                    XWPFRun insertNewRun = paragraphWrapper.insertNewRun(xwpfRun, insertPostionCursor);
                    XWPFRun xwpfRun2 = paragraphWrapper.createRun(xwpfRun, (IRunBody)currentParagraph);
                    paragraphWrapper.setAndUpdateRun(xwpfRun2, insertNewRun, insertPostionCursor);

                    XmlCursor newCursor = endCtr.newCursor();
                    newCursor.toPrevSibling();
                    XmlObject object = newCursor.getObject();
                    XWPFRun copy = paragraphWrapper.createRun(object, (IRunBody)currentParagraph);
                    copies.add(copy);
                    paragraphWrapper.setAndUpdateRun(copy, xwpfRun2, insertPostionCursor);
                }

                // re-parse
                List<MetaTemplate> templates = template.getResolver().resolveXWPFRuns(copies);

                // render
                RenderDataCompute dataCompute = template.getConfig().getRenderDataComputeFactory().newCompute(iterator.next());
                new DocumentProcessor(this.template, dataCompute).process(templates);
            }

            // clear self iterable template
            for (int i = endRunPos - 1; i > startRunPos; i--) {
                paragraphWrapper.removeRun(i);
            }

        } else {

            XWPFParagraph currentParagraph = (XWPFParagraph) iterableTemplate.getStartRun().getParent();
            XWPFParagraphWrapper paragraphWrapper = new XWPFParagraphWrapper(currentParagraph);

            Integer startRunPos = iterableTemplate.getStartMark().getRunPos();
            Integer endRunPos = iterableTemplate.getEndMark().getRunPos();

            for (int i = endRunPos - 1; i > startRunPos; i--) {
                paragraphWrapper.removeRun(i);
            }

        }

        bodyContainer.clearPlaceholder(iterableTemplate.getStartRun());
        bodyContainer.clearPlaceholder(iterableTemplate.getEndRun());
    }

}
