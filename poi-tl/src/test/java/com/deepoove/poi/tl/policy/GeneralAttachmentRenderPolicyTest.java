package com.deepoove.poi.tl.policy;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.GeneralAttachmentRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.policy.GeneralAttachmentRenderPolicy;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@DisplayName("General Attachment test case")
public class GeneralAttachmentRenderPolicyTest {

    @Test
    public void createCSVAttachment() throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("attachment", GeneralAttachmentRenderData.of(createCSVFile(), "测试文件.csv"));
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(model)
            .writeToFile("target/test_csv_attachment.docx");
    }

    @Test
    public void createTxtAttachment() throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("attachment", GeneralAttachmentRenderData.of(createCSVFile(), "测试文件.txt"));
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(model)
            .writeToFile("target/test_txt_attachment.docx");
    }

    @Test
    public void createPdfAttachment() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/attachment/poi-tl.pdf"));
        Map<String, Object> model = new HashMap<>();
        model.put("attachment", GeneralAttachmentRenderData.of(bytes, "测试文件.pdf"));
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(model)
            .writeToFile("target/test_pdf_attachment.docx");
    }

    @Test
    public void createOfficeAttachment() throws IOException {
        String[] arr = new String[] {"docx", "doc", "wps", "xlsx", "xls", "et", "pptx", "ppt", "dps"};
        Map<String, Object> model = new HashMap<>();
        Documents.DocumentBuilder documentBuilder = Documents.of();
        ConfigureBuilder configureBuilder = Configure.builder();
        GeneralAttachmentRenderPolicy generalAttachmentRenderPolicy = new GeneralAttachmentRenderPolicy();
        for (String ext : arr) {
            byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/attachment/test." + ext));
            model.put(ext, GeneralAttachmentRenderData.of(bytes, "测试文件." + ext));
            documentBuilder.addParagraph(Paragraphs.of(String.format("{{%s}}", ext)).create());
            configureBuilder.bind(ext, generalAttachmentRenderPolicy);
        }
        DocumentRenderData template = documentBuilder.create();
        NiceXWPFDocument document = XWPFTemplate.create(template).getXWPFDocument();
        Configure configure = configureBuilder.build();
        XWPFTemplate.compile(document, configure)
            .render(model)
            .writeToFile("target/test_office_attachment.docx");
    }

    @Test
    public void createUnknownAttachment() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/attachment/poi-tl"));
        Map<String, Object> model = new HashMap<>();
        model.put("attachment", GeneralAttachmentRenderData.of(bytes, "测试文件"));
        XWPFTemplate.compile(createTemplate(), createConfigure())
            .render(model)
            .writeToFile("target/test_unknown_attachment.docx");
    }

    /**
     * 创建通用配置文件
     * @return
     */
    private Configure createConfigure() {
        return Configure.builder()
            .bind("attachment", new GeneralAttachmentRenderPolicy())
            .build();
    }

    /**
     * 创建模板
     * @return
     */
    private XWPFDocument createTemplate() {
        DocumentRenderData template = Documents.of()
            .addParagraph(Paragraphs.of("{{attachment}}").create())
            .create();
        return XWPFTemplate.create(template).getXWPFDocument();
    }

    /**
     * 创建 CSV 文件数据
     * @return
     */
    private byte[] createCSVFile() {
        String str = "姓名,年龄,城市\r\n" +
            "小明,10,邯郸\r\n" +
            "小红,11,石家庄";
        return str.getBytes(StandardCharsets.UTF_8);
    }

}