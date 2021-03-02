package com.deepoove.poi.tl.policy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.policy.ParagraphRenderPolicy;
import com.deepoove.poi.xwpf.XWPFShadingPattern;

@DisplayName("Paragraph Render test case")
public class ParagraphRenderPolicyTest {

    @Test
    public void testParagraphRender() throws IOException {
        ParagraphStyle paragraphStyle = ParagraphStyle.builder().withAlign(ParagraphAlignment.CENTER).withSpacing(1.5f)
                .withIndentLeftChars(1.5f).withIndentRightChars(1.5f).withIndentHangingChars(1.0f).build();

        ParagraphRenderData para = Paragraphs.of().addText("I consider myself the luckiest😁 man on the face of the")
                .addText(Texts.of(" earth").color("0000FF").bold().create())
                .addText(Texts.of("10 ").color("FF0000").sup().create())
                .addPicture(Pictures.ofLocal("src/test/resources/sayi.png").size(40, 40).create())
                .addText(Texts.of(" poi-tl").link("http://deepoove.com/poi-tl").create()).addText(". \n end!")
                .paraStyle(paragraphStyle).create();

        BorderStyle leftBorder = new BorderStyle();
        leftBorder.setColor("70AD47");
        leftBorder.setSize(48); // 6*8
        leftBorder.setType(XWPFBorderType.SINGLE);
        ParagraphRenderData stypePara = Paragraphs.of().addText("Style Paragraph")
                .paraStyle(ParagraphStyle.builder().withAlign(ParagraphAlignment.CENTER).withBackgroundColor("70AD47")
                        .withShadingPattern(XWPFShadingPattern.DIAG_STRIPE).withLeftBorder(leftBorder).build())
                .create();

        Map<String, Object> data = new HashMap<>();
        data.put("paragraph", para);
        data.put("styleParagraph", stypePara);

        Configure config = Configure.builder().bind("paragraph", new ParagraphRenderPolicy())
                .bind("styleParagraph", new ParagraphRenderPolicy()).build();
        XWPFTemplate.compile("src/test/resources/template/render_paragraph.docx", config).render(data)
                .writeToFile("out_render_paragraph.docx");
    }

}
