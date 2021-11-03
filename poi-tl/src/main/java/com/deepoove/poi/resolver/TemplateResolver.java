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
package com.deepoove.poi.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.exception.ResolverException;
import com.deepoove.poi.template.BlockTemplate;
import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.template.PictImageTemplate;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.ReflectionUtils;
import com.deepoove.poi.xwpf.CTDrawingWrapper;
import com.deepoove.poi.xwpf.CTPictWrapper;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.deepoove.poi.xwpf.XWPFRunWrapper;
import com.deepoove.poi.xwpf.XWPFTextboxContent;

/**
 * Resolver
 * 
 * @author Sayi
 */
public class TemplateResolver extends AbstractResolver {

    private static Logger logger = LoggerFactory.getLogger(TemplateResolver.class);

    private ElementTemplateFactory elementTemplateFactory;

    public TemplateResolver(Configure config) {
        this(config, config.getElementTemplateFactory());
    }

    private TemplateResolver(Configure config, ElementTemplateFactory elementTemplateFactory) {
        super(config);
        this.elementTemplateFactory = elementTemplateFactory;
    }

    @Override
    public List<MetaTemplate> resolveDocument(XWPFDocument doc) {
        List<MetaTemplate> metaTemplates = new ArrayList<>();
        if (null == doc) return metaTemplates;
        logger.info("Resolve the document start...");
        metaTemplates.addAll(resolveBodyElements(doc.getBodyElements()));
        metaTemplates.addAll(resolveBodys(doc.getHeaderList()));
        metaTemplates.addAll(resolveBodys(doc.getFooterList()));
        metaTemplates.addAll(resolveBodys(doc.getFootnotes()));
        metaTemplates.addAll(resolveBodys(doc.getEndnotes()));
        if (doc instanceof NiceXWPFDocument) {
            metaTemplates.addAll(resolveBodys(((NiceXWPFDocument) doc).getAllComments()));
        }
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
                new RunningRunParagraph(paragraph, templatePattern).refactorRun();
                resolveXWPFRuns(paragraph.getRuns(), metaTemplates, stack);
            } else if (element.getElementType() == BodyElementType.TABLE) {
                XWPFTable table = (XWPFTable) element;
                List<XWPFTableRow> rows = table.getRows();
                if (null == rows) continue;
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    if (null == cells) continue;
                    cells.forEach(cell -> {
                        addNewMeta(metaTemplates, stack, resolveBodyElements(cell.getBodyElements()));
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
                    addNewMeta(metaTemplates, stack, visitBodyElements);
                    continue;
                }

                // picture
                List<PictureTemplate> pictureTemplates = resolveXWPFPictures(run.getEmbeddedPictures());
                if (!pictureTemplates.isEmpty()) {
                    addNewMeta(metaTemplates, stack, pictureTemplates);
                    continue;
                }

                // w:pict v:imagedata
                PictImageTemplate pictImageTemplate = resolvePictImage(run);
                if (null != pictImageTemplate) {
                    addNewMeta(metaTemplates, stack, pictImageTemplate);
                    continue;
                }

                // chart
                ChartTemplate chartTemplate = resolveXWPFChart(run);
                if (null != chartTemplate) {
                    addNewMeta(metaTemplates, stack, chartTemplate);
                    continue;
                }
                continue;
            }
            RunTemplate runTemplate = (RunTemplate) parseTemplateFactory(text, run, run);
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
                addNewMeta(metaTemplates, stack, latestIterableTemplate);
            } else {
                addNewMeta(metaTemplates, stack, runTemplate);
            }
        }
    }

    private ChartTemplate resolveXWPFChart(XWPFRun run) {
        CTDrawing ctDrawing = getCTDrawing(run);
        if (null == ctDrawing) return null;
        CTDrawingWrapper wrapper = new CTDrawingWrapper(ctDrawing);
        String rid = wrapper.getChartId();
        if (null == rid) return null;
        POIXMLDocumentPart documentPart = run.getDocument().getRelationById(rid);
        if (null == documentPart || !(documentPart instanceof XWPFChart)) return null;
        ElementTemplate template = parseTemplateFactory(wrapper.getTitle(), (XWPFChart) documentPart, run);
        return null == template ? (ChartTemplate) parseTemplateFactory(wrapper.getDesc(), (XWPFChart) documentPart, run)
                : (ChartTemplate) template;
    }

    private PictImageTemplate resolvePictImage(XWPFRun run) {
        CTR ctr = run.getCTR();
        CTPicture ctPicture = CollectionUtils.isNotEmpty(ctr.getPictList()) ? ctr.getPictArray(0) : null;
        if (null == ctPicture) return null;
        CTPictWrapper wrapper = new CTPictWrapper(ctPicture);
        return (PictImageTemplate) parseTemplateFactory(wrapper.getShapeAlt(), wrapper, run);
    }

    private List<PictureTemplate> resolveXWPFPictures(List<XWPFPicture> embeddedPictures) {
        List<PictureTemplate> metaTemplates = new ArrayList<>();
        if (embeddedPictures == null) return metaTemplates;

        for (XWPFPicture pic : embeddedPictures) {
            // it's array, to do in the future
            CTDrawing ctDrawing = getCTDrawing(pic);
            if (null == ctDrawing) continue;
            CTDrawingWrapper wrapper = new CTDrawingWrapper(ctDrawing);
            PictureTemplate pictureTemplate = (PictureTemplate) parseTemplateFactory(wrapper.getTitle(), pic, null);
            if (null == pictureTemplate) {
                pictureTemplate = (PictureTemplate) parseTemplateFactory(wrapper.getDesc(), pic, null);
            }
            if (null != pictureTemplate) {
                metaTemplates.add(pictureTemplate);
            }
        }
        return metaTemplates;
    }

    private CTDrawing getCTDrawing(XWPFPicture pic) throws RuntimeException {
        XWPFRun run = (XWPFRun) ReflectionUtils.getValue("run", pic);
        return getCTDrawing(run);
    }

    private CTDrawing getCTDrawing(XWPFRun run) {
        CTR ctr = run.getCTR();
        CTDrawing ctDrawing = CollectionUtils.isNotEmpty(ctr.getDrawingList()) ? ctr.getDrawingArray(0) : null;
        return ctDrawing;
    }

    private void addNewMeta(final List<MetaTemplate> metaTemplates, final Deque<BlockTemplate> stack,
            List<? extends MetaTemplate> newMeta) {
        if (stack.isEmpty()) {
            metaTemplates.addAll(newMeta);
        } else {
            stack.peek().getTemplates().addAll(newMeta);
        }
    }

    private <T extends MetaTemplate> void addNewMeta(final List<MetaTemplate> metaTemplates,
            final Deque<BlockTemplate> stack, T newMeta) {
        addNewMeta(metaTemplates, stack, Collections.singletonList(newMeta));
    }

    private void checkStack(Deque<BlockTemplate> stack) {
        if (!stack.isEmpty()) {
            throw new ResolverException(
                    "Mismatched start/end tags: No end iterable mark found for start mark " + stack.peek());
        }
    }

    private List<MetaTemplate> resolveTextbox(XWPFRun run) {
        XWPFRunWrapper runWrapper = new XWPFRunWrapper(run);
        return Arrays
                .<Supplier<XWPFTextboxContent>>asList(runWrapper::getWpstxbx, runWrapper::getVtextbox,
                        runWrapper::getShapetxbx)
                .stream()
                .map(Supplier::get)
                .map(txbx -> txbx == null ? Collections.<IBodyElement>emptyList() : txbx.getBodyElements())
                .map(this::resolveBodyElements)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    <T extends IBody> List<MetaTemplate> resolveBodys(List<T> bodys) {
        List<MetaTemplate> metaTemplates = new ArrayList<>();
        if (null == bodys) return metaTemplates;

        bodys.forEach(body -> {
            metaTemplates.addAll(resolveBodyElements(body.getBodyElements()));
        });
        return metaTemplates;
    }

    ElementTemplate parseTemplateFactory(String text, Object obj, XWPFRun run) {
        if (null == text) return null;
        ElementTemplate elementTemplate = null;
        if (templatePattern.matcher(text).matches()) {
            String shortClassName = ClassUtils.getShortClassName(obj.getClass());
            String tag = gramerPattern.matcher(text).replaceAll("").trim();
            if (obj.getClass() == XWPFRun.class) {
                elementTemplate = (RunTemplate) elementTemplateFactory.createRunTemplate(config, tag, (XWPFRun) obj);
            } else if (obj.getClass() == XWPFPicture.class) {
                elementTemplate = (PictureTemplate) elementTemplateFactory.createPicureTemplate(config, tag,
                        (XWPFPicture) obj);
            } else if (obj.getClass() == CTPictWrapper.class) {
                elementTemplate = (PictImageTemplate) elementTemplateFactory.createPictImageTemplate(config, tag,
                        (CTPictWrapper) obj, run);
            } else if (obj instanceof XWPFChart) {
                elementTemplate = (ChartTemplate) elementTemplateFactory.createChartTemplate(config, tag,
                        (XWPFChart) obj, run);
            }
            if (null != elementTemplate) {
                logger.debug("Resolve where text: {}, and create {} for {}", text,
                        ClassUtils.getShortClassName(elementTemplate.getClass()), shortClassName);
            }
        }
        return elementTemplate;
    }

}
