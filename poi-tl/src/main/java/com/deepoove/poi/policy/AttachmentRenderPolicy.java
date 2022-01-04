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
package com.deepoove.poi.policy;

import java.io.StringReader;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import org.apache.poi.ooxml.POIXMLTypeLoader;
import org.apache.poi.ooxml.util.DocumentHelper;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * attachment render
 * 
 * @author sayi
 *
 */
public class AttachmentRenderPolicy extends AbstractRenderPolicy<AttachmentRenderData> {

    private static final String EXCEL_SHEET_12 = "Excel.Sheet.12";
    private static final String WORD_DOCUMENT_12 = "Word.Document.12";
    private static final String SHAPE_TYPE_ID = "_x0000_t79";
    private static final String SHAPE_TYPE_XML = "<v:shapetype id=\"" + SHAPE_TYPE_ID + "\" coordsize=\"21600,21600\" o:spt=\"75\" o:preferrelative=\"t\" "
                + "                      path=\"m@4@5l@4@11@9@11@9@5xe\" filled=\"f\" stroked=\"f\">\n"
                + "                        <v:stroke joinstyle=\"miter\"/>\n" 
                + "                        <v:formulas>\n"
                + "                            <v:f eqn=\"if lineDrawn pixelLineWidth 0\"/>\n"
                + "                            <v:f eqn=\"sum @0 1 0\"/>\n"
                + "                            <v:f eqn=\"sum 0 0 @1\"/>\n"
                + "                            <v:f eqn=\"prod @2 1 2\"/>\n"
                + "                            <v:f eqn=\"prod @3 21600 pixelWidth\"/>\n"
                + "                            <v:f eqn=\"prod @3 21600 pixelHeight\"/>\n"
                + "                            <v:f eqn=\"sum @0 0 1\"/>\n"
                + "                            <v:f eqn=\"prod @6 1 2\"/>\n"
                + "                            <v:f eqn=\"prod @7 21600 pixelWidth\"/>\n"
                + "                            <v:f eqn=\"sum @8 21600 0\"/>\n"
                + "                            <v:f eqn=\"prod @7 21600 pixelHeight\"/>\n"
                + "                            <v:f eqn=\"sum @10 21600 0\"/>\n"
                + "                        </v:formulas>\n"
                + "                        <v:path o:extrusionok=\"f\" gradientshapeok=\"t\" o:connecttype=\"rect\"/>\n"
                + "                        <o:lock v:ext=\"edit\" aspectratio=\"t\"/>\n"
                + "                    </v:shapetype>\n";
    
    private static final String docxIcon = "data:image/png;base64,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";
    private static final String xlsxIcon = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAAAXNSR0IArs4c6QAABXFJREFUeF7tW3tQVFUc/u7dxQU1bPJZoBLQDIWwV0EqnRo0iLKEQEbyQVpjCgnGkDkRwWypNWpOBiNPS0GzUEHy1eAjBcEnfzQp5oROWWg6IiEUk+De25xloL3cu487tevZ7v7+2/O49/d953e/3zlnz2GgcmNowx9qCJ/wvaHxvLP8umcERBoiPdvQwTFGgeMFhmNY6CGAA+DppfEYdtpwusMZJDiFgDBD2IjuuzzHsqyeFwSOIUAZTLAE0KUJ4Aycn5HX6hme5xgGnADTqPopGU2XIUCfow/mGU3viPaOqh7ASCVg5dpSR0BgXqBu8M1hHCMInMAwHCAQoGRkvf4tWOoICMqKGD7Io4eMqt4EtleYQhwBlBoN4HK4yUawWQwEDmAediZYuXf5hPrka7Wabkf5cRco/Ca++jJ5vikLEAJ4sGcc9UKlz/XV+4LVsEq7KWq/76VqFgwE1RLA8GzE3llVZ90EqPUTcEeA+xNwa4BbBKnNAiey6zBEN0SU12dWx/f/nhOUhLlBL9ud99+tz8G5VvEWA9Ui6CaAtghInbZENtwECCg6WmIxFCP8JyNs/CTZ+sKjxRb7URcBAaP8UZW2U+Jwj7EH4e8/YRFI+euboR8bKqr/804XlpSl4lyL5W0/6gggCFYlfICZ3AsSsBsO5mFzfZmkPCr4GaxPWispzz9SgE21n1kVMGcshhSLoE6rw5ncExLHr9++gZj1MyTlX6Zsw2MPPSoqP/tTIxaXpYLnedcjgHhMtCBl2mKJ82sOrMP2U1/1l88Kj0du7HuSdqnlaThx6aTN9EVlBPR53ZBdi6G6oSIQTVcvYG5xcn+Z3OhvaSjHJzWf2gRPGpSmFMFrkHi3jeTyPiPzgJARFjeVpdF4seK/mwcQHSB6MNBW7MhCzfmDSAxPQE5stqi6vasdcXkJaO+6bRcBVIqguecVqV8g6MEgEZi6H48jfVsG5JS/jxy70AOgnoCJ4zhsWSRV8jlF80HC39zqmxuwdOsye7Gb2lFPAHHy46Q1iA6OEgHb1Vhl+gTM7cUNcfi1reX/R8DI+0bg8Ns1VoEVHytFwbdFisCTxjHTn4VWqxX1M1/MKBHAvoc4ZDGUEZ2OV59aKAvwZmcrotbFKAZPOlCdBs0RLX8uE8lT5lkESTThwrUfFJPgEgQEjgpAZdoOq+AIeEKCUnOJT2Db4jKE+IonIycvn8KTAeLF0co9q0HEUYlRnwXkprpXbv2Csvpy5MZJp8DT10bj1h9tdnNANQFjho1BzVv7JWCI2lc27sb2lHKM9h4tSY8kEuw1qglYGW9A7MSZIizX2n/D7II56PyrE4uefg3pUUslWN/Ymo6GZulqUo4UagkgEx8yARpoGw7lYfPx3j0BDavB18uqMPYBX1EzIojzSxbAyBttBgKViyFvL28Q4Rs/fJwIwM+tV0wLHXMjqZGkyIGWf3gjNtV9bpMAKtNgZkwGFkz9Z8nbh8KSyu9O3wn/kf4SsPOKX8H5q01WSaCOgKmPTEFBcr7E6eYbl5C4MUkWzIzQ5/FR4ipJ3aGmI1hescJ1CPDQeKBkYSEmjZ8ocfrN7Zk4drHWIhi5uQJpvHLPh9jVWGmxH1UiaGlLnHhvbWub1D/uHyFLHKnb+91+tPwuv0qkigCbiuWABm4CaPtnyAGDbPWRqo8A6tKgsyPATYATzgkq/mvMmVHgjgB3BDj+qKz7E1D7OcF98dWmY8L9Z4WpOi4f7LOa9dDccZTwCsbu0gOzD1zvJ8D8Raq8MGGL6cD0QN3g+1VyZcYWGeb1qrk0pYQU7h3OzzhIJdfm7CVGVRcn7SXFzxDp6d3TwTGsCq7O2ksKaaeay9NKSHFk278BpaKbbiHMt1QAAAAASUVORK5CYII=";
    private static final String WORD_CONTENTTYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    private static final String EXCEL_CONTENTTYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    private static boolean haveShapeType;

    @Override
    protected boolean validate(AttachmentRenderData data) {
        return null != data && null != data.getAttachment() && null != data.getFileType();
    }

    @Override
    protected void afterRender(RenderContext<AttachmentRenderData> context) {
        super.clearPlaceholder(context, false);
    }

    @Override
    public void doRender(RenderContext<AttachmentRenderData> context) throws Exception {
        NiceXWPFDocument doc = context.getXWPFDocument();
        XWPFRun run = context.getRun();
        CTR ctr = run.getCTR();

        // Only one shapetype is needed
        String shapeTypeXml = "";
        if (!haveShapeType) {
            haveShapeType = true;
            shapeTypeXml = SHAPE_TYPE_XML;
        }

        String uuidRandom = UUID.randomUUID().toString().replace("-", "") + ThreadLocalRandom.current().nextInt(1024);
        String shapeId = "_x0000_i20" + uuidRandom;

        AttachmentRenderData data = context.getData();
        AttachmentType fileType = data.getFileType();
        byte[] attachment = data.getAttachment();

        String contentType = "";
        String part = "";
        String progId = "";
        if (fileType == AttachmentType.DOCX) {
            progId = WORD_DOCUMENT_12;
            contentType = WORD_CONTENTTYPE;
            part = "/word/embeddings/word" + uuidRandom + ".docx";
        } else {
            progId = EXCEL_SHEET_12;
            contentType = EXCEL_CONTENTTYPE;
            part = "/word/embeddings/excel" + uuidRandom + ".xlsx";
        }
        PictureRenderData icon = data.getIcon();
        if (null == icon) {
            icon = Pictures.ofBase64(fileType == AttachmentType.DOCX ? docxIcon : xlsxIcon, PictureType.PNG)
                    .size(64, 64)
                    .create();
        }
        Supplier<byte[]> supplier = icon.getPictureSupplier();
        byte[] image = supplier.get();
        PictureType pictureType = icon.getPictureType();
        if (null == pictureType) {
            pictureType = PictureType.suggestFileType(image);
        }

        PictureStyle style = icon.getPictureStyle();
        if (null == style) style = new PictureStyle();
        double widthPt = Units.pixelToPoints(style.getWidth());
        double heightPt = Units.pixelToPoints(style.getHeight());

        String imageRId = doc.addPictureData(image, pictureType.type());
        //String embeddId = doc.addEmbeddData(attachment, fileType.ordinal());
        String embeddId = doc.addEmbeddData(attachment, contentType, part);

        String wObjectXml = "<w:object xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\""
                + "             xmlns:v=\"urn:schemas-microsoft-com:vml\""
                + "             xmlns:o=\"urn:schemas-microsoft-com:office:office\""
                + "             xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
                + "             w:dxaOrig=\"1520\" w:dyaOrig=\"960\">\n" + shapeTypeXml
                + "                    <v:shape id=\"" + shapeId + "\" type=\"#" 
                                            + SHAPE_TYPE_ID + "\" alt=\"\" style=\"width:" + widthPt + "pt;height:" + heightPt
                                            + "pt;mso-width-percent:0;mso-height-percent:0;mso-width-percent:0;mso-height-percent:0\" o:ole=\"\">\n"
                + "                        <v:imagedata r:id=\"" + imageRId + "\" o:title=\"\"/>\n"
                + "                    </v:shape>\n" 
                + "                    <o:OLEObject Type=\"Embed\" ProgID=\""+ progId + "\" ShapeID=\"" + shapeId 
                                            + "\" DrawAspect=\"Icon\" ObjectID=\""+shapeId+"\" r:id=\""+ embeddId + "\">\n"
                + "                     <o:FieldCodes>\\s</o:FieldCodes>\n"
                + "                    </o:OLEObject>" 
                + "            </w:object>";

        Document document = DocumentHelper.readDocument(new InputSource(new StringReader(wObjectXml)));
        ctr.set(XmlObject.Factory.parse(document.getDocumentElement(), POIXMLTypeLoader.DEFAULT_XML_OPTIONS));
    }

}