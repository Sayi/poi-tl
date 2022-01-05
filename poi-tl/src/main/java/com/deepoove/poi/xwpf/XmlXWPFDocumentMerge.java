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
package com.deepoove.poi.xwpf;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;

import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.POIXMLDocumentPart.RelationPart;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

public class XmlXWPFDocumentMerge extends AbstractXWPFDocumentMerge {

    private static final String CROSS_REPLACE_STRING = "@PoiTL@";

    public XmlXWPFDocumentMerge() {
    }

    @Override
    public NiceXWPFDocument merge(NiceXWPFDocument source, Iterator<NiceXWPFDocument> mergeIterator, XWPFRun run)
            throws Exception {
        CTBody body = source.getDocument().getBody();
        List<String> addParts = createMergeableStrings(source, mergeIterator);
        String[] startEnd = truncatedStartEndXmlFragment(body);

        XWPFParagraph paragraph = (XWPFParagraph) run.getParent();
        CTP mergedContainer = paragraph.getCTP();
        CTP mergedBody = CTP.Factory.parse(startEnd[0] + String.join("", addParts) + startEnd[1]);
        // instead insert xml-fragment?
        mergedContainer.set(mergedBody);
        String xmlText = truncatedOverlapWP(body);
        body.set(CTBody.Factory.parse(xmlText));
        return source.generate(true);
    }

    private String truncatedOverlapWP(CTBody body) {
        String xmlText = body.xmlText(DefaultXmlOptions.OPTIONS_INNER);
        xmlText = xmlText.replaceAll("<w:p><w:p>", "<w:p>")
                .replaceAll("<w:p><w:p\\s", "<w:p ")
                .replaceAll("<w:p><w:tbl>", "<w:tbl>")
                .replaceAll("<w:p><w:tbl\\s", "<w:tbl ");

        xmlText = xmlText.replaceAll("</w:sectPr></w:p>", "</w:sectPr>")
                .replaceAll("</w:p></w:p>", "</w:p>")
                .replaceAll("</w:tbl></w:p>", "</w:tbl>")
                .replaceAll("<w:p(\\s[A-Za-z0-9:\\s=\"]*)?/></w:p>", "")
                .replaceAll("</w:p><w:bookmarkEnd(\\s[A-Za-z0-9:\\s=\"]*)?/></w:p>", "</w:p>");
        return xmlText;
    }

    private String[] truncatedStartEndXmlFragment(CTBody body) {
        String srcString = body.xmlText(DefaultXmlOptions.OPTIONS_INNER);
        // hack for create document or single element document
        if (!srcString.startsWith("<xml-fragment")) {
            body.addNewSectPr();
            srcString = body.xmlText(DefaultXmlOptions.OPTIONS_INNER);
        }
        String prefix = srcString.substring(0, srcString.indexOf(">") + 1);
        String suffix = srcString.substring(srcString.lastIndexOf("<"));
        return new String[] { prefix, suffix };
    }

    private List<String> createMergeableStrings(NiceXWPFDocument source, Iterator<NiceXWPFDocument> iterator)
            throws InvalidFormatException, IOException {
        List<String> addParts = new ArrayList<String>();
        if (!iterator.hasNext()) return addParts;
        NiceXWPFDocument next = iterator.next();

        // apply style merge once
        Map<String, String> mergeStyles = mergeStyles(source, next);
        // apply namespaces merge once
        mergeNamespaces(source, next);
        do {
            addParts.add(createMergeableString(source, next, mergeStyles));
            try {
                next.close();
            } catch (Exception e) {
                logger.warn("close merged doc failed!", e);
            }
            if (iterator.hasNext())
                next = iterator.next();
            else
                break;
        } while (true);

        return addParts;
    }

    private void mergeNamespaces(NiceXWPFDocument source, NiceXWPFDocument docMerge) {
        CTDocument1 document = source.getDocument();
        XmlCursor newCursor = document.newCursor();
        if (toStartCursor(newCursor)) {
            CTDocument1 documentMerge = docMerge.getDocument();
            XmlCursor mergeCursor = documentMerge.newCursor();
            if (toStartCursor(mergeCursor)) {
                Map<String, String> addToThis = new HashMap<>();
                mergeCursor.getAllNamespaces(addToThis);
                addToThis.forEach(newCursor::insertNamespace);
            }
            mergeCursor.dispose();
        }
        newCursor.dispose();
    }

    private boolean toStartCursor(XmlCursor newCursor) {
        do {
            if (newCursor.currentTokenType().isStart()) {
                return true;
            } else if (newCursor.hasNextToken()) {
                newCursor.toNextToken();
            } else
                return false;
        } while (true);
    }

    private String createMergeableString(NiceXWPFDocument source, NiceXWPFDocument merged,
            Map<String, String> styleIdsMap) throws InvalidFormatException, IOException {
        CTBody mergedBody = merged.getDocument().getBody();
        // TODO For the same style, reduce the number of merges
        // Map<String, String> styleIdsMap = mergeStyles(docMerge);
        Map<String, String> numIdsMap = mergeNumbering(source, merged);
        Map<String, String> blipIdsMap = mergePicture(source, merged);
        Map<String, String> externalBlipIdsMap = mergeExternalPicture(source, merged);
        Map<String, String> hyperlinkMap = mergeHyperlink(source, merged);
        Map<String, String> chartIdsMap = mergeChart(source, merged);
        Map<String, String> attachmentIdsMap = mergeAttachment(source, merged);

        String appendString = mergedBody.xmlText(DefaultXmlOptions.OPTIONS_OUTER);
        String addPart = ridSectPr(appendString);

        // style
        for (String styleId : styleIdsMap.keySet()) {
            addPart = addPart
                    .replaceAll("<w:pStyle\\sw:val=\"" + styleId + "\"",
                            "<w:pStyle w:val=\"" + styleIdsMap.get(styleId) + "\"")
                    .replaceAll("<w:tblStyle\\sw:val=\"" + styleId + "\"",
                            "<w:tblStyle w:val=\"" + styleIdsMap.get(styleId) + "\"")
                    .replaceAll("<w:rStyle\\sw:val=\"" + styleId + "\"",
                            "<w:rStyle w:val=\"" + styleIdsMap.get(styleId) + "\"");
        }

        // picture id
        Map<String, String> placeHolderblipIdsMap = new HashMap<String, String>();
        for (String relaId : blipIdsMap.keySet()) {
            placeHolderblipIdsMap.put(relaId, blipIdsMap.get(relaId) + CROSS_REPLACE_STRING);
        }
        for (String relaId : placeHolderblipIdsMap.keySet()) {
            addPart = addPart.replaceAll("r:embed=\"" + relaId + "\"",
                    "r:embed=\"" + placeHolderblipIdsMap.get(relaId) + "\"");
            // w:pict v:shape v:imagedata
            addPart = addPart.replaceAll("r:id=\"" + relaId + "\"",
                    "r:id=\"" + placeHolderblipIdsMap.get(relaId) + "\"");
        }
        Map<String, String> placeHolderExternalblipIdsMap = new HashMap<String, String>();
        for (String relaId : externalBlipIdsMap.keySet()) {
            placeHolderExternalblipIdsMap.put(relaId, blipIdsMap.get(relaId) + CROSS_REPLACE_STRING);
        }
        for (String relaId : placeHolderExternalblipIdsMap.keySet()) {
            addPart = addPart.replaceAll("r:link=\"" + relaId + "\"",
                    "r:link=\"" + placeHolderExternalblipIdsMap.get(relaId) + "\"");
        }
        // hyperlink id
        for (String relaId : hyperlinkMap.keySet()) {
            hyperlinkMap.put(relaId, hyperlinkMap.get(relaId) + CROSS_REPLACE_STRING);
        }
        for (String relaId : hyperlinkMap.keySet()) {
            // w:hyperlink r:id
            addPart = addPart.replaceAll("r:id=\"" + relaId + "\"", "r:id=\"" + hyperlinkMap.get(relaId) + "\"");
        }

        // chart id
        for (String relaId : chartIdsMap.keySet()) {
            chartIdsMap.put(relaId, chartIdsMap.get(relaId) + CROSS_REPLACE_STRING);
        }
        for (String relaId : chartIdsMap.keySet()) {
            addPart = addPart.replaceAll("r:id=\"" + relaId + "\"", "r:id=\"" + chartIdsMap.get(relaId) + "\"");
        }

        // attachment id
        for (String relaId : attachmentIdsMap.keySet()) {
            attachmentIdsMap.put(relaId, attachmentIdsMap.get(relaId) + CROSS_REPLACE_STRING);
        }
        for (String relaId : attachmentIdsMap.keySet()) {
            addPart = addPart.replaceAll("r:id=\"" + relaId + "\"", "r:id=\"" + attachmentIdsMap.get(relaId) + "\"");
        }

        // numbering numId
        for (String relaId : numIdsMap.keySet()) {
            numIdsMap.put(relaId, numIdsMap.get(relaId) + CROSS_REPLACE_STRING);
        }
        for (String numId : numIdsMap.keySet()) {
            addPart = addPart.replaceAll("<w:numId\\sw:val=\"" + numId + "\"",
                    "<w:numId w:val=\"" + numIdsMap.get(numId) + "\"");
        }

        return addPart.replaceAll(CROSS_REPLACE_STRING, "");
    }

    private String ridSectPr(String appendString) {
        int lastIndexOf = appendString.lastIndexOf("<w:sectPr");
        String addPart = "";
        int begin = appendString.indexOf(">") + 1;
        int end = appendString.lastIndexOf("<");
        if (-1 != lastIndexOf) {
            String prefix = appendString.substring(begin, appendString.lastIndexOf("<w:sectPr"));
            String suffix = appendString.substring(appendString.lastIndexOf("</w:sectPr>") + 11, end);
            return prefix + suffix;
        } else if (begin < end) {
            addPart = appendString.substring(begin, end);
        }
        return addPart;
    }

    private Map<String, String> mergePicture(NiceXWPFDocument source, NiceXWPFDocument merged)
            throws InvalidFormatException {
        Map<String, String> blipIdsMap = new HashMap<String, String>();
        List<XWPFPictureData> allPictures = merged.getAllPictures();
        for (XWPFPictureData xwpfPictureData : allPictures) {
            String relationId = merged.getRelationId(xwpfPictureData);
            String blidId = source.addPictureData(xwpfPictureData.getData(), xwpfPictureData.getPictureType());
            blipIdsMap.put(relationId, blidId);
        }
        return blipIdsMap;
    }

    private Map<String, String> mergeExternalPicture(NiceXWPFDocument source, NiceXWPFDocument merged)
            throws InvalidFormatException {
        Map<String, String> blipIdsMap = new HashMap<String, String>();
        PackageRelationshipCollection imagePart = merged.getPackagePart()
                .getRelationshipsByType(PackageRelationshipTypes.IMAGE_PART);
        Iterator<PackageRelationship> iterator = imagePart.iterator();
        while (iterator.hasNext()) {
            PackageRelationship relationship = iterator.next();
            if (relationship.getTargetMode() == TargetMode.EXTERNAL) {
                PackageRelationship relationshipNew = source.getPackagePart()
                        .addExternalRelationship(relationship.getTargetURI().toString(),
                                XWPFRelation.IMAGES.getRelation());
                blipIdsMap.putIfAbsent(relationship.getId(), relationshipNew.getId());
            }
        }
        return blipIdsMap;
    }

    private Map<String, String> mergeNumbering(NiceXWPFDocument source, NiceXWPFDocument merged) {
        Map<String, String> numIdsMap = new HashMap<String, String>();
        XWPFNumbering numberingMerge = merged.getNumbering();
        if (null == numberingMerge) return numIdsMap;
        XWPFNumberingWrapper wrapperMerge = new XWPFNumberingWrapper(numberingMerge);
        List<XWPFNum> nums = wrapperMerge.getNums();
        if (null == nums) return numIdsMap;

        XWPFNumbering numbering = source.getNumbering();
        if (null == numbering) numbering = source.createNumbering();
        XWPFNumberingWrapper wrapper = new XWPFNumberingWrapper(numbering);

        XWPFAbstractNum xwpfAbstractNum;
        CTAbstractNum cTAbstractNum;
        Map<BigInteger, CTAbstractNum> cache = new HashMap<BigInteger, CTAbstractNum>();
        Map<BigInteger, CTAbstractNum> ret = new HashMap<BigInteger, CTAbstractNum>();
        for (XWPFNum xwpfNum : nums) {
            BigInteger mergeNumId = xwpfNum.getCTNum().getNumId();

            cTAbstractNum = cache.get(xwpfNum.getCTNum().getAbstractNumId().getVal());
            if (null == cTAbstractNum) {
                xwpfAbstractNum = numberingMerge.getAbstractNum(xwpfNum.getCTNum().getAbstractNumId().getVal());
                if (null == xwpfAbstractNum) {
                    logger.warn("cannot find cTAbstractNum by XWPFNum.");
                    continue;
                }
                cTAbstractNum = xwpfAbstractNum.getCTAbstractNum();
                // cTAbstractNum.setAbstractNumId(wrapper.getNextAbstractNumID());
                if (cTAbstractNum.isSetNsid()) cTAbstractNum.unsetNsid();
                if (cTAbstractNum.isSetTmpl()) cTAbstractNum.unsetTmpl();
                cache.put(xwpfNum.getCTNum().getAbstractNumId().getVal(), cTAbstractNum);
            }
            ret.put(mergeNumId, cTAbstractNum);
        }
        long nextId = wrapper.getNextAbstractNumID().longValue();
        Set<CTAbstractNum> hashSet = new HashSet<>(ret.values());
        for (CTAbstractNum abnum : hashSet) {
            abnum.setAbstractNumId(BigInteger.valueOf(nextId++));
        }
        final XWPFNumbering finalNumbering = numbering;
        ret.forEach((mergeNumId, abnum) -> {
            BigInteger numID = finalNumbering.addNum(finalNumbering.addAbstractNum(new XWPFAbstractNum(abnum)));
            numIdsMap.put(mergeNumId.toString(), numID.toString());
        });

        return numIdsMap;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> mergeStyles(NiceXWPFDocument source, NiceXWPFDocument merged) {
        Map<String, String> styleIdsMap = new HashMap<String, String>();
        XWPFStyles styles = source.getStyles();
        if (null == styles) styles = source.createStyles();
        XWPFStyles stylesMerge = merged.getStyles();
        if (null == stylesMerge) return styleIdsMap;
        try {
            Field listStyleField = XWPFStyles.class.getDeclaredField("listStyle");
            listStyleField.setAccessible(true);
            List<XWPFStyle> lists = (List<XWPFStyle>) listStyleField.get(stylesMerge);
            String defaultParaStyleId = null;
            for (XWPFStyle xwpfStyle : lists) {
                if (styles.styleExist(xwpfStyle.getStyleId())) {
                    String id = xwpfStyle.getStyleId();
                    xwpfStyle.setStyleId(UUID.randomUUID().toString().substring(0, 8));
                    styleIdsMap.put(id, xwpfStyle.getStyleId());
                }

                // fix github issue 499
                CTStyle ctStyle = xwpfStyle.getCTStyle();
                if (ctStyle.isSetDefault() && ctStyle.getDefault() == XWPFOnOff.X_1
                        && ctStyle.getType() == STStyleType.PARAGRAPH) {
                    defaultParaStyleId = ctStyle.getStyleId();
                }

                if (ctStyle.isSetDefault()) {
                    ctStyle.unsetDefault();
                }
                if (ctStyle.isSetName()) {
                    ctStyle.getName().setVal(ctStyle.getName().getVal() + xwpfStyle.getStyleId());
                }
                if (ctStyle.isSetBasedOn()) {
                    String newId = styleIdsMap.get(ctStyle.getBasedOn().getVal());
                    if (null != newId) ctStyle.getBasedOn().setVal(newId);
                }
                styles.addStyle(xwpfStyle);
            }

            if (null != defaultParaStyleId) {
                final String dpid = defaultParaStyleId;
                merged.getParagraphs().stream().filter(p -> null == p.getStyle()).forEach(p -> p.setStyle(dpid));
            }
        } catch (Exception e) {
            // throw exception?
            logger.error("merge style error", e);
        }
        return styleIdsMap;
    }

    private Map<String, String> mergeHyperlink(NiceXWPFDocument source, NiceXWPFDocument merged)
            throws InvalidFormatException {
        Map<String, String> map = new HashMap<String, String>();
        PackageRelationshipCollection hyperlinks = merged.getPackagePart()
                .getRelationshipsByType(PackageRelationshipTypes.HYPERLINK_PART);
        Iterator<PackageRelationship> iterator = hyperlinks.iterator();
        while (iterator.hasNext()) {
            PackageRelationship relationship = iterator.next();
            PackageRelationship relationshipNew = source.getPackagePart()
                    .addExternalRelationship(relationship.getTargetURI().toString(),
                            XWPFRelation.HYPERLINK.getRelation());
            map.put(relationship.getId(), relationshipNew.getId());
        }
        return map;
    }

    private Map<String, String> mergeChart(NiceXWPFDocument source, NiceXWPFDocument merged)
            throws InvalidFormatException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        List<XWPFChart> charts = merged.getCharts();
        for (XWPFChart chart : charts) {
            String relationId = merged.getRelationId(chart);
            RelationPart addChartData = source.addChartData(chart);
            map.put(relationId, addChartData.getRelationship().getId());
        }
        return map;
    }

    private Map<String, String> mergeAttachment(NiceXWPFDocument source, NiceXWPFDocument merged)
            throws InvalidFormatException, IOException {
        Map<String, String> attachmentIdsMap = new HashMap<String, String>();
        PackageRelationshipCollection part = merged.getPackagePart()
                .getRelationshipsByType(POIXMLDocument.PACK_OBJECT_REL_TYPE);
        Iterator<PackageRelationship> iterator = part.iterator();
        while (iterator.hasNext()) {
            PackageRelationship relationship = iterator.next();
            PackagePart embeddPart = merged.getPackagePart().getRelatedPart(relationship);
            String path = relationship.getTargetURI().getPath();
            if (null == path || (!path.endsWith(".docx") && !path.endsWith(".xlsx"))) continue;
            try {
                byte[] byteData = IOUtils.toByteArray(embeddPart.getInputStream());
                String newId = source.addEmbeddData(byteData, path.endsWith("docx") ? 0 : 1);
                attachmentIdsMap.putIfAbsent(relationship.getId(), newId);
            } catch (IOException e) {
                throw new POIXMLException(e);
            }
        }
        return attachmentIdsMap;
    }

    // TODO merge header, footer, pageSect...

}
