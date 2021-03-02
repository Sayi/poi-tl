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

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.exception.ResolverException;
import com.deepoove.poi.template.*;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.ReflectionUtils;
import com.deepoove.poi.xwpf.CTDrawingWrapper;
import com.deepoove.poi.xwpf.XWPFRunWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTD;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTF;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
				new RunningRunParagraph(paragraph, templatePattern).refactorRun();  // 将runs合并
				resolveXWPFRuns(paragraph.getRuns(), metaTemplates, stack);
				if(!paragraph.getCTP().getOMathParaList().isEmpty()){ // 解析公式
					List<MetaTemplate> mathTemplates = resolveCTOMathParas(paragraph.getCTP().getOMathParaList());
					if(!mathTemplates.isEmpty()){
						addNewMeta(metaTemplates, stack, mathTemplates);
					}
				}
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

                List<PictureTemplate> pictureTemplates = resolveXWPFPictures(run.getEmbeddedPictures());
                if (!pictureTemplates.isEmpty()) {
                    addNewMeta(metaTemplates, stack, pictureTemplates);
                    continue;
                }

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
        String rid = wrapper.getCharId();
        if (null == rid) return null;
        POIXMLDocumentPart documentPart = run.getDocument().getRelationById(rid);
        if (null == documentPart || !(documentPart instanceof XWPFChart)) return null;
        ElementTemplate template = parseTemplateFactory(wrapper.getTitle(), (XWPFChart) documentPart, run);
        return null == template ? (ChartTemplate) parseTemplateFactory(wrapper.getDesc(), (XWPFChart) documentPart, run)
                : (ChartTemplate) template;
    }

	private List<MetaTemplate> resolveCTOMathParas(List<CTOMathPara> mathParaList){
		List<MetaTemplate> metaTemplates = new ArrayList<>();
		mathParaList.forEach(mathPara -> {
			mathPara.getOMathList().forEach(math -> {
				resolveMathCTR(math.getRList(), metaTemplates);
				resolveMathCTF(math.getFList(), metaTemplates);
				resolveMathCTD(math.getDList(), metaTemplates);
			});
		});
		return metaTemplates;
	}

	private void resolveMathArgs(List<CTOMathArg> maths, List<MetaTemplate> metaTemplates){
		maths.forEach(math -> {
			resolveMathCTR(math.getRList(), metaTemplates);
			resolveMathCTF(math.getFList(), metaTemplates);
			resolveMathCTD(math.getDList(), metaTemplates);
		});
	}

	private void resolveMathCTF(List<CTF> ctfs, List<MetaTemplate> metaTemplates){
		ctfs.forEach(ctf -> {
			CTOMathArg math = ctf.getDen();
			if(math != null){
				resolveMathCTR(math.getRList(), metaTemplates);
				resolveMathCTF(math.getFList(), metaTemplates);
				resolveMathCTD(math.getDList(), metaTemplates);
			}

			math = ctf.getNum();
			if(math != null){
				resolveMathCTR(math.getRList(), metaTemplates);
				resolveMathCTF(math.getFList(), metaTemplates);
				resolveMathCTD(math.getDList(), metaTemplates);
			}
		});
	}

	private void resolveMathCTD(List<CTD> ctds, List<MetaTemplate> metaTemplates){
		ctds.forEach(ctd -> {
			resolveMathArgs(ctd.getEList(), metaTemplates);
		});
	}

	private void resolveMathCTR(List<org.openxmlformats.schemas.officeDocument.x2006.math.CTR> ctrList, List<MetaTemplate> metaTemplates){
		ctrList.forEach(ctr -> {
			ctr.getTList();
			ctr.getT2List().forEach(t2 -> {
				ElementTemplate elementTemplate = parseTemplateFactory(t2.getStringValue(), t2, null);
				if(elementTemplate != null){
					metaTemplates.add(elementTemplate);
				}
			});
		});
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
        if (null == runWrapper.getWpstxbx()) return new ArrayList<>();
        return resolveBodyElements(runWrapper.getWpstxbx().getBodyElements());
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
        if (templatePattern.matcher(text).matches()) {
            logger.debug("Resolve where text: {}, and create ElementTemplate for {}", text, obj.getClass());
            String tag = gramerPattern.matcher(text).replaceAll("").trim();
            if (obj.getClass() == XWPFRun.class) {
                return (RunTemplate) elementTemplateFactory.createRunTemplate(config, tag, (XWPFRun) obj);
            } else if (obj.getClass() == XWPFPicture.class) {
                return (PictureTemplate) elementTemplateFactory.createPicureTemplate(config, tag, (XWPFPicture) obj);
            } else if (obj.getClass() == XWPFChart.class) {
                return (ChartTemplate) elementTemplateFactory.createChartTemplate(config, tag, (XWPFChart) obj, run);
            } else if(obj.getClass() == org.openxmlformats.schemas.officeDocument.x2006.math.impl.CTTextImpl.class){
            	return elementTemplateFactory.createMathTemplate(config, tag, (org.openxmlformats.schemas.officeDocument.x2006.math.CTText)obj);
			}
        }
        return null;
    }
}
