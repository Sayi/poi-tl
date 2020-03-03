package com.deepoove.poi.render.processor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.compute.RenderDataCompute;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.deepoove.poi.xwpf.NumberingWrapper;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

public class IterableProcessor extends AbstractIterableProcessor {

    public IterableProcessor(XWPFTemplate template, RenderDataCompute renderDataCompute) {
        super(template, renderDataCompute);
    }

    @Override
    public void visit(IterableTemplate iterableTemplate) {

        logger.info("Process iterableTemplate:{}", iterableTemplate);

        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(iterableTemplate);
        NiceXWPFDocument doc = template.getXWPFDocument();

        Object compute = renderDataCompute.compute(iterableTemplate.getStartMark().getTagName());
        int times = conditionTimes(compute);

        if (TIMES_ONCE == times) {

            RenderDataCompute dataCompute = template.getConfig().getRenderDataComputeFactory().newCompute(compute);
            new DocumentProcessor(this.template, dataCompute).process(iterableTemplate.getTemplates());

        } else if (TIMES_N == times) {

            CTP startCtp = ((XWPFParagraph) iterableTemplate.getStartRun().getParent()).getCTP();
            CTP endCtp = ((XWPFParagraph) iterableTemplate.getEndRun().getParent()).getCTP();

            int startPos = bodyContainer.getPosOfParagraphCTP(startCtp);
            int endPos = bodyContainer.getPosOfParagraphCTP(endCtp);

            Iterable<?> model = (Iterable<?>) compute;
            Iterator<?> iterator = model.iterator();
            while (iterator.hasNext()) {
                // copy positon cursor
                XmlCursor insertPostionCursor = endCtp.newCursor();

                // copy content
                List<IBodyElement> bodyElements = bodyContainer.getBodyElements();
                List<IBodyElement> copies = new ArrayList<IBodyElement>();
                Map<BigInteger, BigInteger> consistCache = new HashMap<>();
                for (int i = startPos + 1; i < endPos; i++) {
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

                        // update numbering
                        updateNumbering((XWPFParagraph) iBodyElement, copy, doc, consistCache);

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
                        copies.add(copy);
                        bodyContainer.updateBodyElements(insertNewTbl, copy);
                        bodyContainer.setTable(tablePos, copy);
                    }
                }

                // re-parse
                List<MetaTemplate> templates = template.getResolver().resolveBodyElements(copies);

                // render
                RenderDataCompute dataCompute = template.getConfig().getRenderDataComputeFactory()
                        .newCompute(iterator.next());
                new DocumentProcessor(this.template, dataCompute).process(templates);
            }

            // clear self iterable template
            for (int i = endPos - 1; i > startPos; i--) {
                bodyContainer.removeBodyElement(i);
            }

        } else {

            XWPFParagraph startParagraph = (XWPFParagraph) iterableTemplate.getStartRun().getParent();
            XWPFParagraph endParagraph = (XWPFParagraph) iterableTemplate.getEndRun().getParent();
            XWPFParagraphWrapper startParagraphWrapper = new XWPFParagraphWrapper(startParagraph);
            XWPFParagraphWrapper endParagraphWrapper = new XWPFParagraphWrapper(endParagraph);

            CTP startCtp = startParagraph.getCTP();
            CTP endCtp = endParagraph.getCTP();

            int startPos = bodyContainer.getPosOfParagraphCTP(startCtp);
            int endPos = bodyContainer.getPosOfParagraphCTP(endCtp);

            Integer startRunPos = iterableTemplate.getStartMark().getRunPos();
            Integer endRunPos = iterableTemplate.getEndMark().getRunPos();

            // remove content
            for (int i = endPos - 1; i > startPos; i--) {
                bodyContainer.removeBodyElement(i);
            }

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

        bodyContainer.clearPlaceholder(iterableTemplate.getStartRun());
        bodyContainer.clearPlaceholder(iterableTemplate.getEndRun());
    }

    private static void updateNumbering(XWPFParagraph source, XWPFParagraph target, NiceXWPFDocument doc,
            Map<BigInteger, BigInteger> consistCache) {
        XWPFNumbering numbering = doc.getNumbering();
        if (null == numbering) return;
        BigInteger numID = source.getNumID();
        if (numID == null) return;

        if (consistCache.get(numID) != null) {
            target.setNumID(consistCache.get(numID));
            return;
        }

        NumberingWrapper wrapper = new NumberingWrapper(numbering);
        XWPFNum num = numbering.getNum(numID);
        if (null == num) return;
        XWPFAbstractNum abstractNum = numbering.getAbstractNum(num.getCTNum().getAbstractNumId().getVal());
        CTAbstractNum ctAbstractNum = (CTAbstractNum) abstractNum.getAbstractNum().copy();
        ctAbstractNum.setAbstractNumId(wrapper.getMaxIdOfAbstractNum().add(BigInteger.valueOf(1)));

        // clear continues list
        // (related to tracking numbering definitions when documents are repurposed and
        // changed
        if (ctAbstractNum.isSetNsid()) ctAbstractNum.unsetNsid();
        // related to where the definition can be displayed in the user interface
        if (ctAbstractNum.isSetTmpl()) ctAbstractNum.unsetTmpl();

        BigInteger abstractNumID = numbering.addAbstractNum(new XWPFAbstractNum(ctAbstractNum));
        BigInteger newNumId = numbering.addNum(abstractNumID);
        target.setNumID(newNumId);
        consistCache.put(numID, newNumId);
    }

}
