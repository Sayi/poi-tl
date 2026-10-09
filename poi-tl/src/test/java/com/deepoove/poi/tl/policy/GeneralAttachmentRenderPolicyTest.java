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
package com.deepoove.poi.tl.policy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.poifs.filesystem.Ole10Native;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.Attachments;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.GeneralAttachmentRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.policy.GeneralAttachmentRenderPolicy;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

@DisplayName("General Attachment test case")
public class GeneralAttachmentRenderPolicyTest {

    private static final String OLE_RELATION_TYPE = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/oleObject";

    @Test
    public void createCSVAttachment() throws Exception {
        byte[] csvBytes = createCSVFile();
        String fileName = "测试文件.csv";
        Map<String, Object> model = new HashMap<>();
        model.put("attachment", Attachments.ofGeneral(csvBytes, fileName).create());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(model)
            .write(out);

        assertOleAttachment(out.toByteArray(), fileName, csvBytes);
    }

    @Test
    public void createTxtAttachment() throws Exception {
        byte[] txtBytes = "hello general attachment txt".getBytes(StandardCharsets.UTF_8);
        String fileName = "测试文件.txt";
        Map<String, Object> model = new HashMap<>();
        model.put("attachment", Attachments.ofGeneral(txtBytes, fileName).create());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(model)
            .write(out);

        assertOleAttachment(out.toByteArray(), fileName, txtBytes);
    }

    @Test
    public void createPdfAttachment() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/attachment/poi-tl.pdf"));
        String fileName = "测试文件.pdf";
        Map<String, Object> model = new HashMap<>();
        model.put("attachment", Attachments.ofGeneral(bytes, fileName).create());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(model)
            .write(out);

        assertOleAttachment(out.toByteArray(), fileName, bytes);
    }

    @Test
    public void createOfficeAttachment() throws Exception {
        String[] arr = new String[] {"docx", "doc", "wps", "xlsx", "xls", "et", "pptx", "ppt", "dps"};
        Map<String, Object> model = new HashMap<>();
        Documents.DocumentBuilder documentBuilder = Documents.of();
        ConfigureBuilder configureBuilder = Configure.builder();
        GeneralAttachmentRenderPolicy generalAttachmentRenderPolicy = new GeneralAttachmentRenderPolicy();
        for (String ext : arr) {
            byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/attachment/test." + ext));
            model.put(ext, Attachments.ofGeneral(bytes, "测试文件." + ext).create());
            documentBuilder.addParagraph(Paragraphs.of(String.format("{{%s}}", ext)).create());
            configureBuilder.bind(ext, generalAttachmentRenderPolicy);
        }
        DocumentRenderData template = documentBuilder.create();
        NiceXWPFDocument document = XWPFTemplate.create(template).getXWPFDocument();
        Configure configure = configureBuilder.build();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFTemplate.compile(document, configure)
            .render(model)
            .write(out);

        try (XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(out.toByteArray()))) {
            List<POIXMLDocumentPart.RelationPart> oleParts = doc.getRelationParts().stream()
                .filter(p -> OLE_RELATION_TYPE.equals(p.getRelationship().getRelationshipType()))
                .collect(Collectors.toList());
            assertEquals(arr.length, oleParts.size());

            // 验证同一文档中 <v:shapetype id="_x0000_t79"> 没有被重复定义
            String xml = doc.getDocument().xmlText();
            int firstIdx = xml.indexOf("_x0000_t79");
            assertTrue(firstIdx >= 0, "Document should contain _x0000_t79");
            int secondIdx = xml.indexOf("<v:shapetype", xml.indexOf("<v:shapetype") + 1);
            assertEquals(-1, secondIdx, "Shapetype should only be defined once in document");
        }
    }

    @Test
    public void createUnknownAttachment() throws Exception {
        byte[] bytes = "unknown format binary content".getBytes(StandardCharsets.UTF_8);
        String fileName = "测试文件";
        Map<String, Object> model = new HashMap<>();
        model.put("attachment", Attachments.ofGeneral(bytes, fileName).create());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(model)
            .write(out);

        assertOleAttachment(out.toByteArray(), fileName, bytes);
    }

    @Test
    public void testCustomIconAndFileSources() throws Exception {
        File pdfFile = new File("src/test/resources/attachment/poi-tl.pdf");
        GeneralAttachmentRenderData data = Attachments.ofGeneral(pdfFile)
            .icon(Pictures.ofLocal("src/test/resources/logo.png").size(48, 48).create())
            .create();

        assertNotNull(data.getIcon());
        assertEquals("poi-tl.pdf", data.getFileName());

        Map<String, Object> model = new HashMap<>();
        model.put("attachment", data);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(model)
            .write(out);

        assertOleAttachment(out.toByteArray(), "poi-tl.pdf", Files.readAllBytes(pdfFile.toPath()));
    }

    @Test
    public void testAttachmentsOfGeneralFactory() throws Exception {
        byte[] csvBytes = createCSVFile();
        GeneralAttachmentRenderData data = Attachments.ofGeneral(csvBytes, "通用附件.csv")
            .icon(Pictures.ofLocal("src/test/resources/logo.png").size(48, 48).create())
            .create();

        assertNotNull(data.getIcon());
        assertEquals("通用附件.csv", data.getFileName());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(Collections.singletonMap("attachment", data))
            .write(out);

        assertOleAttachment(out.toByteArray(), "通用附件.csv", csvBytes);
    }

    private void assertOleAttachment(byte[] docxBytes, String expectedFileName, byte[] expectedData) throws Exception {
        try (XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(docxBytes))) {
            List<POIXMLDocumentPart.RelationPart> oleParts = doc.getRelationParts().stream()
                .filter(p -> OLE_RELATION_TYPE.equals(p.getRelationship().getRelationshipType()))
                .collect(Collectors.toList());
            assertEquals(1, oleParts.size(), "Should have exactly 1 OLE object relation");

            POIXMLDocumentPart part = oleParts.get(0).getDocumentPart();
            String partName = part.getPackagePart().getPartName().getName();
            assertTrue(partName.startsWith("/word/embeddings/"), "Part name must be in /word/embeddings/ but was " + partName);
            assertTrue(partName.endsWith(".bin"), "Part name must end with .bin");

            try (POIFSFileSystem poifs = new POIFSFileSystem(part.getPackagePart().getInputStream())) {
                Ole10Native ole = Ole10Native.createFromEmbeddedOleObject(poifs);
                assertEquals(expectedFileName, ole.getFileName(), "Ole10Native fileName should match original");
                assertEquals(expectedFileName, ole.getLabel(), "Ole10Native label should match original");
                assertArrayEquals(expectedData, ole.getDataBuffer(), "Ole10Native data buffer should match");
            }
        }
    }

    /**
     * 创建通用配置文件
     */
    private Configure createConfigure() {
        return Configure.builder()
            .bind("attachment", new GeneralAttachmentRenderPolicy())
            .build();
    }

    /**
     * 创建模板
     */
    private XWPFDocument createTemplate() {
        DocumentRenderData template = Documents.of()
            .addParagraph(Paragraphs.of("{{attachment}}").create())
            .create();
        return XWPFTemplate.create(template).getXWPFDocument();
    }

    /**
     * 创建 CSV 文件数据
     */
    private byte[] createCSVFile() {
        String str = "姓名,年龄,城市\r\n" +
            "小明,10,邯郸\r\n" +
            "小红,11,石家庄";
        return str.getBytes(StandardCharsets.UTF_8);
    }

}
