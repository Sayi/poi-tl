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
package com.deepoove.poi.plugin.comment;

import static org.apache.poi.ooxml.POIXMLTypeLoader.DEFAULT_XML_OPTIONS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.ooxml.POIXMLRelation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFactory;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRelation;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTComment;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTComments;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CommentsDocument;

import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * specifies all of the comments defined in the current document
 * 
 * @author Sayi
 */
public class XWPFComments extends POIXMLDocumentPart {

    private List<XWPFComment> comments = new ArrayList<>();
    private List<XWPFPictureData> pictures = new ArrayList<>();

    private CTComments ctComments;
    XWPFDocument document;

    public XWPFComments(PackagePart part) {
        super(part);
    }

    public XWPFComments() {
        ctComments = CTComments.Factory.newInstance();
    }

    @Override
    public void onDocumentRead() throws IOException {
        try (InputStream is = getPackagePart().getInputStream()) {
            CommentsDocument doc = CommentsDocument.Factory.parse(is, DEFAULT_XML_OPTIONS);
            ctComments = doc.getComments();
            for (CTComment ctComment : ctComments.getCommentList()) {
                comments.add(new XWPFComment(ctComment, this));
            }
        } catch (XmlException e) {
            throw new POIXMLException("Unable to read comments", e);
        }

        for (POIXMLDocumentPart poixmlDocumentPart : getRelations()) {
            if (poixmlDocumentPart instanceof XWPFPictureData) {
                XWPFPictureData xwpfPicData = (XWPFPictureData) poixmlDocumentPart;
                pictures.add(xwpfPicData);
                ((NiceXWPFDocument) getXWPFDocument()).niceRegisterPackagePictureData(xwpfPicData);
            }
        }
    }

    public String addPictureData(InputStream is, int format) throws InvalidFormatException, Exception {
        byte[] data = IOUtils.toByteArray(is);
        return addPictureData(data, format);
    }

    private static class Child extends XWPFPictureData {
        public static POIXMLRelation getRelations(int format) {
            return RELATIONS[format];
        }
    }

    public String addPictureData(byte[] pictureData, int format) throws Exception {
        XWPFPictureData xwpfPicData = ((NiceXWPFDocument) getXWPFDocument()).niceFindPackagePictureData(pictureData,
                format);
        POIXMLRelation relDesc = Child.getRelations(format);
//        POIXMLRelation relDesc = XWPFPictureData.RELATIONS[format];

        if (xwpfPicData == null) {
            /* Part doesn't exist, create a new one */
            int idx = getXWPFDocument().getNextPicNameNumber(format);
            xwpfPicData = (XWPFPictureData) createRelationship(relDesc, XWPFFactory.getInstance(), idx);
            /* write bytes to new part */
            PackagePart picDataPart = xwpfPicData.getPackagePart();
            try (OutputStream out = picDataPart.getOutputStream()) {
                out.write(pictureData);
            } catch (IOException e) {
                throw new POIXMLException(e);
            }

            ((NiceXWPFDocument) getXWPFDocument()).niceRegisterPackagePictureData(xwpfPicData);
            pictures.add(xwpfPicData);
            return getRelationId(xwpfPicData);
        } else if (!getRelations().contains(xwpfPicData)) {
            /*
             * Part already existed, but was not related so far. Create relationship to the already existing part and
             * update POIXMLDocumentPart data.
             */
            // TODO add support for TargetMode.EXTERNAL relations.
            RelationPart rp = addRelation(null, XWPFRelation.IMAGES, xwpfPicData);
            pictures.add(xwpfPicData);
            return rp.getRelationship().getId();
        } else {
            /* Part already existed, get relation id and return it */
            return getRelationId(xwpfPicData);
        }
    }

    @Override
    protected void commit() throws IOException {
        XmlOptions xmlOptions = new XmlOptions(DEFAULT_XML_OPTIONS);
        xmlOptions.setSaveSyntheticDocumentElement(new QName(CTComments.type.getName().getNamespaceURI(), "comments"));
        PackagePart part = getPackagePart();
        OutputStream out = part.getOutputStream();
        ctComments.save(out, xmlOptions);
        out.close();
    }

    public List<XWPFPictureData> getAllPictures() {
        return Collections.unmodifiableList(pictures);
    }

    public CTComments getCtComments() {
        return ctComments;
    }

    public void setCtComments(CTComments ctComments) {
        this.ctComments = ctComments;
    }

    public List<XWPFComment> getComments() {
        return comments;
    }

    public void setXWPFDocument(XWPFDocument document) {
        this.document = document;
    }

    public XWPFDocument getXWPFDocument() {
        if (null != document) {
            return document;
        }
        return (XWPFDocument) getParent();
    }

    public XWPFComment addComment() {
        return createComment(getMaxId().add(BigInteger.ONE));
    }

    public XWPFComment createComment(BigInteger cid) {
        CTComment ctComment = ctComments.addNewComment();
        ctComment.setId(cid);
        XWPFComment comment = new XWPFComment(ctComment, this);
        comments.add(comment);
        return comment;
    }

    public BigInteger getMaxId() {
        BigInteger max = BigInteger.ZERO;
        for (XWPFComment comment : comments) {
            BigInteger id = comment.getCtComment().getId();
            if (null != id && id.compareTo(max) == 1) {
                max = id;
            }
        }
        return max;
    }

    public XWPFComment getComment(int pos) {
        if (pos >= 0 && pos < ctComments.sizeOfCommentArray()) {
            return getComments().get(pos);
        }
        return null;
    }

    public void removeComment(int pos) {
        if (pos >= 0 && pos < ctComments.sizeOfCommentArray()) {
            comments.remove(pos);
            ctComments.removeComment(pos);
        }
    }

    public XWPFComment getComment(CTComment comment) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getCtComment() == comment) {
                return comments.get(i);
            }
        }
        return null;
    }

}
