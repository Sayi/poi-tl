/*
 * Copyright 2014-2026 Sayi
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
package com.deepoove.poi.plugin.comment;

import static com.deepoove.poi.policy.DocumentRenderPolicy.Helper.renderDocument;
import static com.deepoove.poi.policy.ParagraphRenderPolicy.Helper.renderParagraph;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackagingURIHelper;
import org.apache.poi.ooxml.util.DocumentHelper;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFComments;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.NextIDUtils;
import com.deepoove.poi.util.ParagraphUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

/**
 * comment render
 * 
 * @author Sayi
 */
public class CommentRenderPolicy extends AbstractRenderPolicy<CommentRenderData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentRenderPolicy.class);

    @Override
    protected boolean validate(CommentRenderData data) {
        if (null == data) return false;
        if (null == data.getContents()) {
            throw new RenderException("CommentRenderData must set content!");
        }
        return true;
    }

    @Override
    public void doRender(RenderContext<CommentRenderData> context) throws Exception {
        Helper.renderComment(context.getRun(), context.getData());
    }

    @Override
    protected void afterRender(RenderContext<CommentRenderData> context) {
        clearPlaceholder(context, false);
    }

    public static class Helper {

        private static final QName W14_PARA_ID = new QName(
                "http://schemas.microsoft.com/office/word/2010/wordml", "paraId", "w14");
        private static final String CEX_NS = "http://schemas.microsoft.com/office/word/2018/wordml/cex";
        private static final String CID_NS = "http://schemas.microsoft.com/office/word/2016/wordml/cid";
        private static final String W15_NS = "http://schemas.microsoft.com/office/word/2012/wordml";

        public static void renderComment(XWPFRun run, CommentRenderData data) throws Exception {
            XWPFParagraph paragraph = (XWPFParagraph) run.getParent();
            XWPFParagraphWrapper parentContext = new XWPFParagraphWrapper(paragraph);
            BigInteger cId = BigInteger.ZERO;
            CommentContent commentContent = data.getCommentContent();
            if (null != commentContent) {
                NiceXWPFDocument document = (NiceXWPFDocument) paragraph.getDocument();
                XWPFComments comments = document.createComments();
                XWPFComment newComment = comments
                        .createComment(NextIDUtils.getCommentMaxId(comments).add(BigInteger.ONE));
                newComment.setAuthor(commentContent.getAuthor());
                Calendar utcCalendar = toUtcCalendar(commentContent.getDate());
                if (null != utcCalendar) {
                    newComment.setDate(utcCalendar);
                }
                newComment.setInitials(commentContent.getInitials());
                renderDocument(newComment.createParagraph().createRun(), commentContent.getContent());
                cId = newComment.getCtComment().getId();
                parentContext.insertNewCommentRangeStart(run, cId);
                if (null != utcCalendar) {
                    syncCommentsExtensible(document, newComment, utcCalendar);
                }
            }
            renderParagraph(run, Paragraphs.of().addList(data.getContents()).create());
            if (null != commentContent) {
                parentContext.insertNewCommentRangeEnd(run, cId);
                XWPFRun newRun = parentContext.insertNewRun(ParagraphUtils.getRunPos(run));
                newRun.getCTR().addNewCommentReference().setId(cId);
            }
        }

        public static Calendar toUtcCalendar(Calendar calendar) {
            if (null == calendar) {
                return null;
            }
            Calendar utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            utcCalendar.setTimeInMillis(calendar.getTimeInMillis());
            if (calendar.get(Calendar.MILLISECOND) == 0) {
                utcCalendar.clear(Calendar.MILLISECOND);
            }
            return utcCalendar;
        }

        public static void syncCommentsExtensible(XWPFDocument document, XWPFComment newComment, Calendar utcCalendar) {
            if (null == document || null == newComment || null == utcCalendar) {
                return;
            }
            try {
                PackagePartName extensiblePartName = PackagingURIHelper.createPartName("/word/commentsExtensible.xml");
                PackagePart extensiblePart = document.getPackage().getPart(extensiblePartName);
                if (null == extensiblePart) {
                    return;
                }

                List<XWPFParagraph> paragraphs = newComment.getParagraphs();
                if (paragraphs.isEmpty()) {
                    return;
                }
                XWPFParagraph commentParagraph = paragraphs.get(paragraphs.size() - 1);
                XmlCursor cursor = commentParagraph.getCTP().newCursor();
                String paraId = cursor.getAttributeText(W14_PARA_ID);
                if (null == paraId || paraId.trim().isEmpty()) {
                    paraId = String.format("%08X", ThreadLocalRandom.current().nextInt());
                    cursor.setAttributeText(W14_PARA_ID, paraId);
                }
                cursor.dispose();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateUtcStr = sdf.format(utcCalendar.getTime());

                // 1. Resolve or create durableId in /word/commentsIds.xml if present
                PackagePartName idsPartName = PackagingURIHelper.createPartName("/word/commentsIds.xml");
                PackagePart idsPart = document.getPackage().getPart(idsPartName);
                String durableId = null;
                if (null != idsPart) {
                    Document idsDoc;
                    try (InputStream in = idsPart.getInputStream()) {
                        idsDoc = DocumentHelper.readDocument(in);
                    }
                    NodeList idNodes = idsDoc.getElementsByTagNameNS(CID_NS, "commentId");
                    if (idNodes.getLength() == 0) {
                        idNodes = idsDoc.getElementsByTagName("w16cid:commentId");
                    }
                    for (int i = 0; i < idNodes.getLength(); i++) {
                        Element el = (Element) idNodes.item(i);
                        String pId = getAttr(el, CID_NS, "paraId", "w16cid");
                        if (paraId.equalsIgnoreCase(pId)) {
                            durableId = getAttr(el, CID_NS, "durableId", "w16cid");
                            break;
                        }
                    }
                    if (null == durableId || durableId.trim().isEmpty()) {
                        durableId = String.format("%08X", ThreadLocalRandom.current().nextInt());
                        Element newIdElem = idsDoc.createElementNS(CID_NS, "w16cid:commentId");
                        newIdElem.setAttributeNS(CID_NS, "w16cid:paraId", paraId);
                        newIdElem.setAttributeNS(CID_NS, "w16cid:durableId", durableId);
                        idsDoc.getDocumentElement().appendChild(newIdElem);
                        writeXmlPart(idsPart, idsDoc);
                    }
                } else {
                    durableId = String.format("%08X", ThreadLocalRandom.current().nextInt());
                }

                // 2. Ensure /word/commentsExtended.xml entry if present
                PackagePartName extendedPartName = PackagingURIHelper.createPartName("/word/commentsExtended.xml");
                PackagePart extendedPart = document.getPackage().getPart(extendedPartName);
                if (null != extendedPart) {
                    Document extendedDoc;
                    try (InputStream in = extendedPart.getInputStream()) {
                        extendedDoc = DocumentHelper.readDocument(in);
                    }
                    NodeList exNodes = extendedDoc.getElementsByTagNameNS(W15_NS, "commentEx");
                    if (exNodes.getLength() == 0) {
                        exNodes = extendedDoc.getElementsByTagName("w15:commentEx");
                    }
                    boolean found = false;
                    for (int i = 0; i < exNodes.getLength(); i++) {
                        Element el = (Element) exNodes.item(i);
                        String pId = getAttr(el, W15_NS, "paraId", "w15");
                        if (paraId.equalsIgnoreCase(pId)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        Element newExElem = extendedDoc.createElementNS(W15_NS, "w15:commentEx");
                        newExElem.setAttributeNS(W15_NS, "w15:paraId", paraId);
                        newExElem.setAttributeNS(W15_NS, "w15:done", "0");
                        extendedDoc.getDocumentElement().appendChild(newExElem);
                        writeXmlPart(extendedPart, extendedDoc);
                    }
                }

                // 3. Update or append in /word/commentsExtensible.xml
                Document extDoc;
                try (InputStream in = extensiblePart.getInputStream()) {
                    extDoc = DocumentHelper.readDocument(in);
                }
                NodeList extNodes = extDoc.getElementsByTagNameNS(CEX_NS, "commentExtensible");
                if (extNodes.getLength() == 0) {
                    extNodes = extDoc.getElementsByTagName("w16cex:commentExtensible");
                }
                boolean updated = false;
                for (int i = 0; i < extNodes.getLength(); i++) {
                    Element el = (Element) extNodes.item(i);
                    String dId = getAttr(el, CEX_NS, "durableId", "w16cex");
                    if (durableId.equalsIgnoreCase(dId)) {
                        el.setAttributeNS(CEX_NS, "w16cex:dateUtc", dateUtcStr);
                        updated = true;
                        break;
                    }
                }
                if (!updated) {
                    Element newExtElem = extDoc.createElementNS(CEX_NS, "w16cex:commentExtensible");
                    newExtElem.setAttributeNS(CEX_NS, "w16cex:durableId", durableId);
                    newExtElem.setAttributeNS(CEX_NS, "w16cex:dateUtc", dateUtcStr);
                    extDoc.getDocumentElement().appendChild(newExtElem);
                }
                writeXmlPart(extensiblePart, extDoc);
            } catch (Exception e) {
                LOGGER.warn("Failed to synchronize commentsExtensible", e);
            }
        }

        private static String getAttr(Element el, String nsUri, String localName, String prefix) {
            if (el.hasAttributeNS(nsUri, localName)) {
                return el.getAttributeNS(nsUri, localName);
            }
            if (el.hasAttribute(prefix + ":" + localName)) {
                return el.getAttribute(prefix + ":" + localName);
            }
            if (el.hasAttribute(localName)) {
                return el.getAttribute(localName);
            }
            return "";
        }

        private static void writeXmlPart(PackagePart part, Document doc) throws Exception {
            part.clear();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            try (OutputStream out = part.getOutputStream()) {
                transformer.transform(new DOMSource(doc), new StreamResult(out));
            }
        }

    }

}
