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
package com.deepoove.poi.plugin.barcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class BarcodeRenderPolicyTest {

    private static final String TEMPLATE = "src/test/resources/barcode/barcode_template.docx";
    private static final String OUTPUT = "target/out_barcode.docx";

    @Test
    public void testRenderDedicatedSyntaxAndPictureSyntax() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        try (InputStream logo = getClass().getResourceAsStream("/barcode/logo.png")) {
            data.put("qrcode", BarcodeRenderData.ofQrCode("https://deepoove.com/poi-tl")
                    .size(120, 120)
                    .logo(logo)
                    .errorCorrectionLevel(ErrorCorrectionLevel.H)
                    .center()
                    .build());
        }
        data.put("waybill", BarcodeRenderData.ofCode128("SF1029384756").size(240, 60).showText(true).build());
        data.put("asset", BarcodeRenderData.ofCode39("ASSET-2025-098").size(220, 50).build());
        data.put("udi", BarcodeRenderData.ofDataMatrix("(01)00845678901234(17)251231").size(80, 80).build());
        data.put("ean", BarcodeRenderData.ofEan13("978711118777").size(200, 60).build());
        data.put("legacy", BarcodeRenderData.ofQrCode("legacy picture tag").size(80, 80).build());
        data.put("broken", BarcodeRenderData.ofCode128("运单号001").size(240, 60)
                .altMeta("[运单号条码生成失败]").build());

        Configure config = Configure.builder().addPlugin('%', new BarcodeRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(TEMPLATE, config).render(data);
        template.writeToFile(OUTPUT);
        template.close();

        File output = new File(OUTPUT);
        Assertions.assertTrue(output.length() > 0);

        try (XWPFDocument document = new XWPFDocument(new FileInputStream(output))) {
            // qrcode, waybill, udi and ean appear twice (paragraph and table cell)
            Assertions.assertEquals(10, countDrawings(document),
                    "every tag but the broken one must render a picture");
            Assertions.assertTrue(containsText(document, "[运单号条码生成失败]"),
                    "the invalid content must fall back to the alt text");
            Assertions.assertFalse(document.getAllPictures().isEmpty());
        }
    }

    @Test
    public void testDefaultFallbackTextIsUsedWhenAltMetaIsMissing() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("qrcode", "https://deepoove.com");
        data.put("waybill", BarcodeRenderData.ofCode128("运单号001").size(240, 60).build());
        data.put("asset", BarcodeRenderData.ofCode39("ASSET-2025-098").size(220, 50).build());
        data.put("udi", BarcodeRenderData.ofDataMatrix("(01)00845678901234(17)251231").size(80, 80).build());
        data.put("ean", BarcodeRenderData.ofEan13("978711118777").size(200, 60).build());
        data.put("legacy", BarcodeRenderData.ofQrCode("legacy picture tag").size(80, 80).build());
        data.put("broken", BarcodeRenderData.ofCode128("运单号001").size(240, 60).build());

        Configure config = Configure.builder().addPlugin('%', new BarcodeRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(TEMPLATE, config).render(data);
        template.writeToFile("target/out_barcode_fallback.docx");
        template.close();

        try (XWPFDocument document = new XWPFDocument(
                new FileInputStream("target/out_barcode_fallback.docx"))) {
            Assertions.assertTrue(containsText(document, "[barcode render failed]"));
        }
    }

    @Test
    public void testPlainStringIsRenderedAsQrCode() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("qrcode", "https://deepoove.com/poi-tl");

        Configure config = Configure.builder().addPlugin('%', new BarcodeRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(TEMPLATE, config).render(data);
        template.writeToFile("target/out_barcode_string.docx");
        template.close();

        try (XWPFDocument document = new XWPFDocument(
                new FileInputStream("target/out_barcode_string.docx"))) {
            Assertions.assertTrue(countDrawings(document) > 0);
        }
    }

    @Test
    public void testUnsupportedDataTypeClearsTheTagWithoutBreakingTheDocument() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("qrcode", new Object());

        Configure config = Configure.builder().addPlugin('%', new BarcodeRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(TEMPLATE, config).render(data);
        template.writeToFile("target/out_barcode_unsupported.docx");
        template.close();

        try (XWPFDocument document = new XWPFDocument(
                new FileInputStream("target/out_barcode_unsupported.docx"))) {
            Assertions.assertEquals(0, countDrawings(document));
            Assertions.assertFalse(containsText(document, "qrcode"), "the tag must be cleared");
        }
    }

    @Test
    public void testEmptyContentFallsBackInsteadOfBreakingTheDocument() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("qrcode", "");

        Configure config = Configure.builder().addPlugin('%', new BarcodeRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(TEMPLATE, config).render(data);
        template.writeToFile("target/out_barcode_empty.docx");
        template.close();

        try (XWPFDocument document = new XWPFDocument(
                new FileInputStream("target/out_barcode_empty.docx"))) {
            Assertions.assertTrue(containsText(document, "[barcode render failed]"));
        }
    }

    private static int countDrawings(XWPFDocument document) {
        int count = 0;
        for (IBodyElement element : document.getBodyElements()) {
            if (element instanceof XWPFParagraph) {
                count += countDrawings((XWPFParagraph) element);
            } else if (element instanceof XWPFTable) {
                for (XWPFTableRow row : ((XWPFTable) element).getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            count += countDrawings(paragraph);
                        }
                    }
                }
            }
        }
        return count;
    }

    private static int countDrawings(XWPFParagraph paragraph) {
        int count = 0;
        for (XWPFRun run : paragraph.getRuns()) {
            count += run.getCTR().sizeOfDrawingArray();
        }
        return count;
    }

    private static boolean containsText(XWPFDocument document, String text) {
        for (IBodyElement element : document.getBodyElements()) {
            if (element instanceof XWPFParagraph && ((XWPFParagraph) element).getText().contains(text)) {
                return true;
            }
            if (element instanceof XWPFTable) {
                for (XWPFTableRow row : ((XWPFTable) element).getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            if (paragraph.getText().contains(text)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
