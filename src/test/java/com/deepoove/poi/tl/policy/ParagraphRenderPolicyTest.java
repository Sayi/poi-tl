package com.deepoove.poi.tl.policy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.policy.ParagraphRenderPolicy;

public class ParagraphRenderPolicyTest {

    @Test
    public void testParagraph() throws IOException {
        Configure config = Configure.newBuilder().bind("paragraph", new ParagraphRenderPolicy()).build();
        Map<String, Object> data = new HashMap<>();
        ParagraphRenderData para = new ParagraphRenderData();
        para.addText("I consider myself the luckiest😁 man on the face of the");
        para.addText(Texts.of(" earth").color("0000FF").bold().create());
        para.addText(Texts.of("10 ").color("FF0000").sup().create());
        para.addPicture(Pictures.ofLocal("src/test/resources/sayi.png").size(40, 40).create());
        para.addText(Texts.of(" poi-tl").link("http://deepoove.com/poi-tl").create());
        para.addText(". \n end!");

        ParagraphStyle paragraphStyle = ParagraphStyle.builder().withAlign(ParagraphAlignment.CENTER).withSpacing(1.5f)
                .withIndentLeftChars(1.5f).withIndentRightChars(1.5f).withIndentHangingChars(1.0f).build();
        para.setParagraphStyle(paragraphStyle);

        data.put("paragraph", para);
        XWPFTemplate.compile("src/test/resources/template/render_paragraph.docx", config).render(data)
                .writeToFile("out_render_paragraph.docx");
    }

}
