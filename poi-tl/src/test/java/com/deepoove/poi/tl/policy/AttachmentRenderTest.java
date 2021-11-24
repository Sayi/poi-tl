package com.deepoove.poi.tl.policy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.AttachmentType;
import com.deepoove.poi.data.Attachments;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.policy.AttachmentRenderPolicy;

@DisplayName("Attachment Render test case")
public class AttachmentRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testAttachmentRender() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("attachment",
                        Attachments.ofLocal("src/test/resources/template/attachment.docx", AttachmentType.DOCX)
                                .create());
                put("xlsx", Attachments.ofLocal("src/test/resources/template/attachment.xlsx", AttachmentType.XLSX)
                        .create());
                put("chart", Charts
                        .ofMultiSeries("CustomTitle",
                                new String[] { "中文", "English", "日本語", "português", "中文", "English", "日本語",
                                        "português" })
                        .addSeries("countries", new Double[] { 15.0, 6.0, 18.0, 231.0, 150.0, 6.0, 118.0, 31.0 })
                        .addSeries("speakers", new Double[] { 223.0, 119.0, 154.0, 142.0, 223.0, 119.0, 54.0, 42.0 })
                        .addSeries("youngs", new Double[] { 323.0, 89.0, 54.0, 42.0, 223.0, 119.0, 54.0, 442.0 })
                        .create());
            }
        };

        Configure configure = Configure.builder()
                .bind("attachment", new AttachmentRenderPolicy())
                .bind("xlsx", new AttachmentRenderPolicy())
                .build();

        XWPFTemplate.compile("src/test/resources/template/render_attachment.docx", configure)
                .render(new HashMap<String, Object>() {
                    {
                        put("attachment",
                                Attachments.ofLocal("src/test/resources/template/attachment.docx", AttachmentType.DOCX)
                                        .create());
                        put("xlsx",
                                Attachments.ofLocal("src/test/resources/template/attachment.xlsx", AttachmentType.XLSX)
                                        .create());
                        put("list", Arrays.asList(datas, datas));

                    }
                })
                .writeToFile("out_render_attachment.docx");

    }

}
