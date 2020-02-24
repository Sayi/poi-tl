//package com.deepoove.poi.render.processor;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.ClassUtils;
//import org.apache.poi.xwpf.usermodel.BodyElementType;
//import org.apache.poi.xwpf.usermodel.IBodyElement;
//import org.apache.poi.xwpf.usermodel.IRunBody;
//import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
//import org.apache.poi.xwpf.usermodel.XWPFNum;
//import org.apache.poi.xwpf.usermodel.XWPFNumbering;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.xmlbeans.XmlCursor;
//import org.apache.xmlbeans.XmlObject;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.deepoove.poi.NiceXWPFDocument;
//import com.deepoove.poi.NumberingWrapper;
//import com.deepoove.poi.XWPFParagraphWrapper;
//import com.deepoove.poi.XWPFTemplate;
//import com.deepoove.poi.config.Configure;
//import com.deepoove.poi.exception.RenderException;
//import com.deepoove.poi.policy.DocxRenderPolicy;
//import com.deepoove.poi.policy.RenderPolicy;
//import com.deepoove.poi.render.compute.ELObjectRenderDataCompute;
//import com.deepoove.poi.render.compute.RenderDataCompute;
//import com.deepoove.poi.resolver.TemplateResolver;
//import com.deepoove.poi.template.ElementTemplate;
//import com.deepoove.poi.template.IterableTemplate;
//import com.deepoove.poi.template.MetaTemplate;
//import com.deepoove.poi.template.run.RunTemplate;
//import com.deepoove.poi.util.ParagraphUtils;
//import com.deepoove.poi.xwpf.Container;
//import com.deepoove.poi.xwpf.ContainerFactory;
//
//public class IterableRender {
//
//    @SuppressWarnings("deprecation")
//    public static void render(IterableTemplate iterableTemplate, XWPFTemplate template,
//            RenderDataCompute renderDataCompute) {
//
//        Object compute = renderDataCompute.compute(iterableTemplate.getStartMark().getTagName());
//        boolean show = (compute != null && !(compute instanceof Boolean)) || (compute instanceof Boolean && (Boolean) compute);
//        if (show) {
//            if (!(compute instanceof Iterable)) {
//                NiceXWPFDocument doc = template.getXWPFDocument();
//                RunTemplate start = (RunTemplate) iterableTemplate.getStartMark();
//                XWPFRun run = start.getRun();
//                Container container = ContainerFactory.getContainer(run);
//                
//                clearPlaceholder(((RunTemplate) iterableTemplate.getStartMark()).getRun(), true, container);
//                clearPlaceholder(((RunTemplate) iterableTemplate.getEndMark()).getRun(), true, container);
//    
//                ELObjectRenderDataCompute dataCompute = new ELObjectRenderDataCompute(compute, false);
//                
//                List<MetaTemplate> templates = iterableTemplate.getTemplates();
//                for (MetaTemplate temp : templates) {
//                    if (temp instanceof IterableTemplate) {
////                        ret.addAll(IterableRender.render((IterableTemplate) temp, template, renderDataCompute));
//                        IterableRender.render((IterableTemplate) temp, template, dataCompute);
//                    } else {
////                        ret.add(temp);
//                        RenderPolicy policy = findPolicy(template.getConfig(), temp);
//                        if (policy instanceof DocxRenderPolicy) {
//                            ;
//                        } else {
//                            doRender(temp, policy, template, dataCompute);
//                        }
//                    }
//                }
//            } else {
//                NiceXWPFDocument doc = template.getXWPFDocument();
//                
//                
//                RunTemplate start = (RunTemplate) iterableTemplate.getStartMark();
//                RunTemplate end = (RunTemplate) iterableTemplate.getEndMark();
//    
//                XWPFRun startRun = start.getRun();
//                XWPFRun endRun = end.getRun();
//                
//                Container container = ContainerFactory.getContainer(startRun);
//    
//                XWPFParagraph startParagraph = startRun.getParagraph();
//                XWPFParagraph endParagraph = endRun.getParagraph();
//    
//                CTP startCtp = startParagraph.getCTP();
//                CTP endCtp = endParagraph.getCTP();
//                
//                int startPos = container.getPosOfParagraphCTP(startCtp);
//                int endPos = container.getPosOfParagraphCTP(endCtp);
//                
//                Integer startRunPos = start.getRunPos();
//                Integer endRunPos = end.getRunPos();
//                
//                Iterable model = (Iterable)compute;
//                Iterator iterator = model.iterator();
//                
//                CTR endCtr = endRun.getCTR();
//                
//                if (startCtp == endCtp) {
//                    while (iterator.hasNext()) {
//                        Object dataModel = iterator.next();
//                        
//                        // copy position cursor
//                        int insertPostionCursor = end.getRunPos();
//                        
//                        //copy content
//                        List<XWPFRun> runs = startParagraph.getRuns();
//                        List<XWPFRun> copies = new ArrayList<XWPFRun>();
//                        for (int i = startRunPos + 1; i < endRunPos; i++) {
//                            insertPostionCursor = end.getRunPos();
//                            
//                            XWPFRun xwpfRun = runs.get(i);
//                            XWPFRun insertNewRun = startParagraph.insertNewRun(insertPostionCursor);
//                            XWPFRun xwpfRun2 = new XWPFRun((CTR) xwpfRun.getCTR().copy(), startParagraph);
//                            XWPFParagraphWrapper paragraphWrapper = new XWPFParagraphWrapper(startParagraph);
//                            paragraphWrapper.setAndUpdateRun(xwpfRun2, insertNewRun, insertPostionCursor);
//                            
//                            XmlCursor newCursor = endCtr.newCursor();
//                            newCursor.toPrevSibling();
//                            XmlObject object = newCursor.getObject();
//                            XWPFRun copy = new XWPFRun((CTR) object, startParagraph);
//                            copies.add(copy);
//                            paragraphWrapper.setAndUpdateRun(copy, xwpfRun2, insertPostionCursor);
//                        }
//                        
//                        // re-parse
//                        TemplateResolver visitor = new TemplateResolver(template.getConfig());
//                        List<MetaTemplate> templates = visitor.resolveXWPFRuns(copies);
//                        
//                        // render
//                        ELObjectRenderDataCompute dataCompute = new ELObjectRenderDataCompute(dataModel, false);
//                        for (MetaTemplate temp : templates) {
//                            if (temp instanceof IterableTemplate) {
//                                IterableRender.render((IterableTemplate) temp, template, dataCompute);
//                            } else {
//                                RenderPolicy policy = findPolicy(template.getConfig(), temp);
//                                if (policy instanceof DocxRenderPolicy) {
//                                    ;
//                                } else {
//                                    doRender(temp, policy, template, dataCompute);
//                                }
//                            }
//                        }
//                    }
//                    
//                    // clear self iterable template
//                    for (int i = endRunPos - 1; i > startRunPos; i--) {
//                        startParagraph.removeRun(i);
//                    }
//                    
//                    clearPlaceholder(((RunTemplate) iterableTemplate.getStartMark()).getRun(), false, container);
//                    clearPlaceholder(((RunTemplate) iterableTemplate.getEndMark()).getRun(), false, container);
//                    
//                    return;
//                }
//                
//                
//                while (iterator.hasNext()) {
//                    Object dataModel = iterator.next();
//                    
//                    // copy positon cursor
//                    XmlCursor insertPostionCursor = endCtp.newCursor();
//                    
//                    //copy content
//                    List<IBodyElement> bodyElements = container.getBodyElements();
//                    List<IBodyElement> copies = new ArrayList<IBodyElement>();
//                    Map<BigInteger, BigInteger> consistCache = new HashMap<>();
//                    for (int i = startPos + 1; i < endPos; i++) {
//                        IBodyElement iBodyElement = bodyElements.get(i);
//                        if (iBodyElement.getElementType() == BodyElementType.PARAGRAPH) {
//                            insertPostionCursor = endCtp.newCursor();
//                            XWPFParagraph insertNewParagraph = container.insertNewParagraph(insertPostionCursor);
//                            // find insert paragraph pos
//                            int paraPos = container.getParaPos(insertNewParagraph);
//                            container.setParagraph((XWPFParagraph)iBodyElement, paraPos);
//                            // re-update ctp reference
//                            insertPostionCursor = endCtp.newCursor();
//                            insertPostionCursor.toPrevSibling();
//                            XmlObject object = insertPostionCursor.getObject();
//                            XWPFParagraph copy = new XWPFParagraph((CTP)object, container.getTarget());
//                            
//                            // update numbering
//                            updateNumbering((XWPFParagraph)iBodyElement, copy, doc, consistCache);
//                            
//                            copies.add(copy);
//                            container.updateBodyElements(insertNewParagraph, copy);
//                            container.setParagraph(copy, paraPos);
//                        } else if (iBodyElement.getElementType() == BodyElementType.TABLE) {
//                            insertPostionCursor = endCtp.newCursor();
//                            XWPFTable insertNewTbl = container.insertNewTbl(insertPostionCursor);
//                            // find insert table pos
//                            int tablePos = container.getTablePos(insertNewTbl);
//                            container.setTable(tablePos, (XWPFTable)iBodyElement);
//                            
//                            insertPostionCursor = endCtp.newCursor();
//                            insertPostionCursor.toPrevSibling();
//                            XmlObject object = insertPostionCursor.getObject();
//                            
//                            XWPFTable copy = new XWPFTable((CTTbl) object, container.getTarget());
//                            copies.add(copy);
//                            container.updateBodyElements(insertNewTbl, copy);
//                            container.setTable(tablePos, copy);
//                        }
//                    }
//                    
//                    // re-parse
//                    TemplateResolver visitor = new TemplateResolver(template.getConfig());
//                    List<MetaTemplate> templates = visitor.resolveBodyElements(copies);
//                    
//                    // render
//                    ELObjectRenderDataCompute dataCompute = new ELObjectRenderDataCompute(dataModel, false);
//                    for (MetaTemplate temp : templates) {
//                        if (temp instanceof IterableTemplate) {
//                            IterableRender.render((IterableTemplate) temp, template, dataCompute);
//                        } else {
//                            RenderPolicy policy = findPolicy(template.getConfig(), temp);
//                            if (policy instanceof DocxRenderPolicy) {
//                                ;
//                            } else {
//                                doRender(temp, policy, template, dataCompute);
//                            }
//                        }
//                    }
//                }
//                
//                // clear self iterable template
//                for (int i = endPos - 1; i > startPos; i--) {
//                    container.removeBodyElement(i);
//                }
//                
//                clearPlaceholder(((RunTemplate) iterableTemplate.getStartMark()).getRun(), true, container);
//                clearPlaceholder(((RunTemplate) iterableTemplate.getEndMark()).getRun(), true, container);
//                
//    
//                
//                // 1
////                XWPFParagraph copyParagraph = doc.getParagraphArray(startPos + 1);
////                XmlCursor cursor = copyParagraph.getCTP().newCursor();
////                cursor.toNextSibling();
////                //inserts a blank paragraph before the original one
////                doc.insertNewParagraph(cursor);
////                doc.setParagraph(copyParagraph, endPos);
//            }
//            
//
//        } else {
//            NiceXWPFDocument doc = template.getXWPFDocument();
//
//            RunTemplate start = (RunTemplate) iterableTemplate.getStartMark();
//            RunTemplate end = (RunTemplate) iterableTemplate.getEndMark();
//
//            XWPFRun startRun = start.getRun();
//            XWPFRun endRun = end.getRun();
//            
//            Container container = ContainerFactory.getContainer(startRun);
//
//            XWPFParagraph startParagraph = startRun.getParagraph();
//            XWPFParagraph endParagraph = endRun.getParagraph();
//
//            CTP startCtp = startParagraph.getCTP();
//            CTP endCtp = endParagraph.getCTP();
//            
//            int startPos = container.getPosOfParagraphCTP(startCtp);
//            int endPos = container.getPosOfParagraphCTP(endCtp);
//            
//             // remove content
//            for (int i = endPos - 1; i > startPos; i--) {
//                container.removeBodyElement(i);
//            }
//
////            XmlCursor startCursor = startCtp.newCursor();
//////            startCursor.selectPath("./*");
//////            while (startCursor.toNextSelection()) {
////            
////            while (startCursor.toNextSibling()) {
////                XmlObject bodyObj = startCursor.getObject();
////                if (endCtp == bodyObj)
////                    break;
////                if (bodyObj instanceof CTP) {
////                    int pos = doc.getPosOfParagraphCTP((CTP) bodyObj);
////                    //doc.removeBodyElement(pos);
////                    System.out.println(pos);
////                } else if (bodyObj instanceof CTTbl) {
////                    int pos = doc.getPosOfTableCTTbl((CTTbl) bodyObj);
////                    //doc.removeBodyElement(pos);
////                    System.out.println(pos);
////                }
////            }
////            startCursor.dispose();
//            
//            Integer startRunPos = start.getRunPos();
//            Integer endRunPos = end.getRunPos();
//            
//            
//            if (startPos == endPos) {
//                for (int i = endRunPos; i >= startRunPos; i--) {
//                    startParagraph.removeRun(i);
//                }
//                
//                String paragraphText = ParagraphUtils.trimLine(startParagraph);
//                if ("".equals(paragraphText)) {
//                    int pos = container.getPosOfParagraph(startParagraph);
//                    container.removeBodyElement(pos);
//                }
//                
//            }else {
//                List<XWPFRun> startRuns = startParagraph.getRuns();
//                int startSize = startRuns.size();
//                for (int i = startSize -  1; i >= startRunPos; i--) {
//                    startParagraph.removeRun(i);
//                }
//                
//                for (int i = endRunPos; i >= 0; i--) {
//                    endParagraph.removeRun(i);
//                }
//                
//                String paragraphText = ParagraphUtils.trimLine(endParagraph);
//                if ("".equals(paragraphText)) {
//                    int pos = container.getPosOfParagraph(endParagraph);
//                    container.removeBodyElement(pos);
//                }
//                paragraphText = ParagraphUtils.trimLine(startParagraph);
//                if ("".equals(paragraphText)) {
//                    int pos = container.getPosOfParagraph(startParagraph);
//                    container.removeBodyElement(pos);
//                }
//            }
//            
//
////            clearPlaceholder(((RunTemplate) runTemplate.getStartMark()).getRun(), true, template);
////            clearPlaceholder(((RunTemplate) runTemplate.getEndMark()).getRun(), true, template);
//        }
//
//    }
//
//    private static void updateNumbering(XWPFParagraph source, XWPFParagraph target, NiceXWPFDocument doc, Map<BigInteger, BigInteger> consistCache) {
//        // TODO Auto-generated method stub
//        XWPFNumbering numbering = doc.getNumbering();
//        if (null == numbering) return;
//        BigInteger numID = source.getNumID();
//        if (numID == null) return;
//        
//        if (consistCache.get(numID) != null) {
//            target.setNumID(consistCache.get(numID));
//            return;
//        }
//        
//        NumberingWrapper wrapper = new NumberingWrapper(numbering);
//        XWPFNum num = numbering.getNum(numID);
//        if (null == num) return;
//        XWPFAbstractNum abstractNum = numbering.getAbstractNum(num.getCTNum().getAbstractNumId().getVal());
//        CTAbstractNum ctAbstractNum = (CTAbstractNum) abstractNum.getAbstractNum().copy();
//        ctAbstractNum.setAbstractNumId(wrapper.getMaxIdOfAbstractNum().add(BigInteger.valueOf(1)));
//        
//        // clear continues list
//        // (related to tracking numbering definitions when documents are repurposed and changed
//        ctAbstractNum.unsetNsid();
//        // related to where the definition can be displayed in the user interface
//        ctAbstractNum.unsetTmpl();
//        
//        BigInteger abstractNumID = numbering.addAbstractNum(new XWPFAbstractNum(ctAbstractNum));
//        BigInteger newNumId = numbering.addNum(abstractNumID);
//        target.setNumID(newNumId);
//        consistCache.put(numID, newNumId);
//        
//    }
//
//    public static void clearPlaceholder(XWPFRun run, boolean clearParagraph, Container container) {
//        IRunBody parent = run.getParent();
//        String text = run.text();
//        run.setText("", 0);
//        if (clearParagraph && (parent instanceof XWPFParagraph)) {
//            String paragraphText = ParagraphUtils.trimLine((XWPFParagraph) parent);
//            // 段落就是当前标签则删除段落
//            if ("".equals(paragraphText)) {
//                int pos = container.getPosOfParagraph((XWPFParagraph) parent);
//                // TODO p inside table for-each cell's p and remove
//                container.removeBodyElement(pos);
//            }
//        }
//    }
//    
//    private static RenderPolicy findPolicy(Configure config, MetaTemplate runTemplate) {
//        RenderPolicy policy = config.getPolicy(((ElementTemplate)runTemplate).getTagName(), ((ElementTemplate)runTemplate).getSign());
//        if (null == policy) { throw new RenderException(
//                "Cannot find render policy: [" + ((ElementTemplate)runTemplate).getTagName() + "]"); }
//        return policy;
//    }
//
//    private static void doRender(MetaTemplate runTemplate, RenderPolicy policy, XWPFTemplate template, RenderDataCompute renderDataCompute) {
//        LOGGER.info("Start render TemplateName:{}, Sign:{}, policy:{}", ((ElementTemplate)runTemplate).getTagName(),
//                ((ElementTemplate)runTemplate).getSign(), ClassUtils.getShortClassName(policy.getClass()));
//        policy.render(((ElementTemplate)runTemplate), renderDataCompute.compute(((ElementTemplate)runTemplate).getTagName()), template);
//    }
//    
//    private static final Logger LOGGER = LoggerFactory.getLogger(IterableRender.class);
//
//}
