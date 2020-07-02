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
package com.deepoove.poi.resolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.SimpleValue;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.impl.CTNonVisualDrawingPropsImpl;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.exception.ReflectionException;
import com.deepoove.poi.exception.ResolverException;
import com.deepoove.poi.template.BlockTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.xwpf.XWPFRunWrapper;

/**
 * Resolver
 * 
 * @author Sayi
 * @version 1.7.0
 */
public class TemplateResolver extends AbstractResolver {

    private static Logger logger = LoggerFactory.getLogger(TemplateResolver.class);

    private RunTemplateFactory<?> runTemplateFactory;

    public TemplateResolver(Configure config) {
        this(config, config.getRunTemplateFactory());
    }

    private TemplateResolver(Configure config, RunTemplateFactory<?> runTemplateFactory) {
        super(config);
        this.runTemplateFactory = runTemplateFactory;
    }

    @Override
    public List<MetaTemplate> resolveDocument(XWPFDocument doc) {
        List<MetaTemplate> metaTemplates = new ArrayList<>();
        if (null == doc) return metaTemplates;
        logger.info("Resolve the document start...");
        metaTemplates.addAll(resolveBodyElements(doc.getBodyElements()));
        metaTemplates.addAll(resolveHeaders(doc.getHeaderList()));
        metaTemplates.addAll(resolveFooters(doc.getFooterList()));
        logger.info("Resolve the document end, resolve and create {} MetaTemplates.", metaTemplates.size());
        return metaTemplates;
    }

    @Override
    public List<MetaTemplate> resolveBodyElements(List<IBodyElement> bodyElements) {
        List<MetaTemplate> metaTemplates = new ArrayList<>();
        if (null == bodyElements) return metaTemplates;

        // current iterable templates state
        Deque<BlockTemplate> stack = new LinkedList<BlockTemplate>();

        for (IBodyElement element : bodyElements) {
            if (element == null) continue;
            if (element.getElementType() == BodyElementType.PARAGRAPH) {
                XWPFParagraph paragraph = (XWPFParagraph) element;
                RunningRunParagraph runningRun = new RunningRunParagraph(paragraph, templatePattern);
                runningRun.refactorRun();
                //if (null == refactorRuns) continue;
                //Collections.reverse(refactorRuns);
                resolveXWPFRuns(paragraph.getRuns(), metaTemplates, stack);
            } else if (element.getElementType() == BodyElementType.TABLE) {
                XWPFTable table = (XWPFTable) element;
                List<XWPFTableRow> rows = table.getRows();
                if (null == rows) continue;
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    if (null == cells) continue;
                    cells.forEach(cell -> {
                        List<MetaTemplate> visitBodyElements = resolveBodyElements(cell.getBodyElements());
                        if (stack.isEmpty()) {
                            metaTemplates.addAll(visitBodyElements);
                        } else {
                            stack.peek().getTemplates().addAll(visitBodyElements);
                        }
                    });
                }
            }
        }

        checkStack(stack);
        return metaTemplates;
    }

    @Override
    public List<MetaTemplate> resolveXWPFRuns(List<XWPFRun> runs) {
        List<MetaTemplate> metaTemplates = new ArrayList<>();
        if (runs == null) return metaTemplates;

        Deque<BlockTemplate> stack = new LinkedList<BlockTemplate>();
        resolveXWPFRuns(runs, metaTemplates, stack);
        checkStack(stack);
        return metaTemplates;
    }

    private void resolveXWPFRuns(List<XWPFRun> runs, final List<MetaTemplate> metaTemplates,
            final Deque<BlockTemplate> stack) {
        for (XWPFRun run : runs) {
            String text = null;
            if (StringUtils.isBlank(text = run.getText(0))) {
                // textbox
                List<MetaTemplate> visitBodyElements = resolveTextbox(run);
                if (!visitBodyElements.isEmpty()) {
                    if (stack.isEmpty()) {
                        metaTemplates.addAll(visitBodyElements);
                    } else {
                        stack.peek().getTemplates().addAll(visitBodyElements);
                    }
                    continue;
                }

                List<XWPFPicture> embeddedPictures = run.getEmbeddedPictures();
                List<PictureTemplate> pictureTemplates = resolveXWPFPictures(embeddedPictures);
                if (stack.isEmpty()) {
                    metaTemplates.addAll(pictureTemplates);
                } else {
                    stack.peek().getTemplates().addAll(pictureTemplates);
                }
                continue;
            }
            // if (null == run || StringUtils.isBlank(text = run.getText(0))) continue;
            RunTemplate runTemplate = parseTemplateFactory(text, run);
            if (null == runTemplate) continue;
            char charValue = runTemplate.getSign().charValue();
            if (charValue == config.getIterable().getLeft()) {
                IterableTemplate freshIterableTemplate = new IterableTemplate(runTemplate);
                stack.push(freshIterableTemplate);
            } else if (charValue == config.getIterable().getRight()) {
                if (stack.isEmpty()) throw new ResolverException(
                        "Mismatched start/end tags: No start mark found for end mark " + runTemplate);
                BlockTemplate latestIterableTemplate = stack.pop();
                if (StringUtils.isNotEmpty(runTemplate.getTagName())
                        && !latestIterableTemplate.getStartMark().getTagName().equals(runTemplate.getTagName())) {
                    throw new ResolverException("Mismatched start/end tags: start mark "
                            + latestIterableTemplate.getStartMark() + " does not match to end mark " + runTemplate);
                }
                latestIterableTemplate.setEndMark(runTemplate);
                if (latestIterableTemplate instanceof IterableTemplate) {
                    latestIterableTemplate = ((IterableTemplate) latestIterableTemplate).buildIfInline();
                }
                if (stack.isEmpty()) {
                    metaTemplates.add(latestIterableTemplate);
                } else {
                    stack.peek().getTemplates().add(latestIterableTemplate);
                }
            } else {
                if (stack.isEmpty()) {
                    metaTemplates.add(runTemplate);
                } else {
                    stack.peek().getTemplates().add(runTemplate);
                }
            }
        }
    }

    private List<PictureTemplate> resolveXWPFPictures(List<XWPFPicture> embeddedPictures) {
        List<PictureTemplate> metaTemplates = new ArrayList<>();
        if (embeddedPictures == null) return metaTemplates;
        
        for (XWPFPicture pic : embeddedPictures) {
                // it's array, to do in the future
                CTDrawing ctDrawing = getCTDrawing(pic);
                if (null == ctDrawing) continue;

                CTNonVisualDrawingProps docPr = null;
                if (ctDrawing.sizeOfAnchorArray() > 0) {
                    CTAnchor anchorArray = ctDrawing.getAnchorArray(0);
                    docPr = anchorArray.getDocPr();

                } else if (ctDrawing.sizeOfInlineArray() > 0) {
                    CTInline inline = ctDrawing.getInlineArray(0);
                    docPr = inline.getDocPr();
                }

                if (null != docPr) {
                    String title = getTitle(docPr);
                    String descr = docPr.getDescr();
                    
                    PictureTemplate pictureTemplate = parsePicTemplateFactory(title, pic);
                    if (null != pictureTemplate) metaTemplates.add(pictureTemplate);
                }
        }
        return metaTemplates;
    }
    
   

    private String getTitle(CTNonVisualDrawingProps docPr) {
        QName TITLE = new QName("", "title");
        CTNonVisualDrawingPropsImpl docPrImpl = (CTNonVisualDrawingPropsImpl) docPr;
        synchronized (docPrImpl.monitor()) {
            // check_orphaned();
            SimpleValue localSimpleValue = null;
            localSimpleValue = (SimpleValue) docPrImpl.get_store().find_attribute_user(TITLE);
            if (localSimpleValue == null) { return null; }
            return localSimpleValue.getStringValue();
        }
    }

    public CTDrawing getCTDrawing(XWPFPicture pic) throws RuntimeException {
        XWPFRun run;
        try {
            Field field = XWPFPicture.class.getDeclaredField("run");
            field.setAccessible(true);
            run = (XWPFRun) field.get(pic);
        } catch (Exception e) {
            throw new ReflectionException("run", XWPFPicture.class, e);
        }
        CTR ctr = run.getCTR();
        return ctr.getDrawingList() != null ? ctr.getDrawingArray(0) : null;
    }

    private List<MetaTemplate> resolveTextbox(XWPFRun run) {
        XWPFRunWrapper runWrapper = new XWPFRunWrapper(run);
        if (null == runWrapper.getWpstxbx()) return new ArrayList<>();
        return resolveBodyElements(runWrapper.getWpstxbx().getBodyElements());
    }

    private void checkStack(Deque<BlockTemplate> stack) {
        if (!stack.isEmpty()) {
            throw new ResolverException(
                    "Mismatched start/end tags: No end iterable mark found for start mark " + stack.peek());
        }
    }

    List<MetaTemplate> resolveHeaders(List<XWPFHeader> headers) {
        List<MetaTemplate> metaTemplates = new ArrayList<>();
        if (null == headers) return metaTemplates;

        headers.forEach(header -> {
            metaTemplates.addAll(resolveBodyElements(header.getBodyElements()));
        });
        return metaTemplates;
    }

    List<MetaTemplate> resolveFooters(List<XWPFFooter> footers) {
        List<MetaTemplate> metaTemplates = new ArrayList<>();
        if (null == footers) return metaTemplates;

        footers.forEach(footer -> {
            metaTemplates.addAll(resolveBodyElements(footer.getBodyElements()));
        });
        return metaTemplates;
    }

    <T> RunTemplate parseTemplateFactory(String text, T obj) {
        if (templatePattern.matcher(text).matches()) {
            logger.debug("Resolve where text: {}, and create ElementTemplate", text);
            String tag = gramerPattern.matcher(text).replaceAll("").trim();
            if (obj.getClass() == XWPFRun.class) {
                return (RunTemplate) runTemplateFactory.createRunTemplate(tag, (XWPFRun) obj);
            } else if (obj.getClass() == XWPFTableCell.class)
                // return CellTemplate.create(symbol, tagName, (XWPFTableCell)
                // obj);
                return null;
        }
        return null;
    }
    
    private PictureTemplate parsePicTemplateFactory(String text, XWPFPicture pic) {
        logger.debug("Resolve where text: {}, and create ElementTemplate", text);
        if (templatePattern.matcher(text).matches()) {
            String tag = gramerPattern.matcher(text).replaceAll("").trim();
            if (pic.getClass() == XWPFPicture.class) {
                return (PictureTemplate) runTemplateFactory.createPicureTemplate(tag, pic);
            } 
        }
        return null;
    }

}
