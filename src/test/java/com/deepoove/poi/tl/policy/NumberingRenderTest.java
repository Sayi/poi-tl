package com.deepoove.poi.tl.policy;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;

@DisplayName("Numbering Render test case")
public class NumberingRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testNumberingRender() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                // 1. 2. 3.
//                put("number123", getData(NumberingFormat.DECIMAL));
//                put("number123_dulplicate", getData(NumberingFormat.DECIMAL));
//                // 1) 2) 3)
//                put("number123_kuohao", getData(NumberingFormat.DECIMAL_PARENTHESES));
//                // 无序
//                put("bullet", getData(NumberingFormat.BULLET));
//                // A B C
//                put("ABC", getData(NumberingFormat.UPPER_LETTER));
//                // a b c
//                put("abc", getData(NumberingFormat.LOWER_LETTER));
//                // ⅰ ⅱ ⅲ
//                put("iiiiii", getData(NumberingFormat.LOWER_ROMAN));
//                // Ⅰ Ⅱ Ⅲ
//                put("IIIII", getData(NumberingFormat.UPPER_ROMAN));
//                // 自定义有序列表显示 (one) (two) (three)
//                put("custom_number", getData(new NumberingFormat(STNumberFormat.CARDINAL_TEXT, "(%1)")));
//                // 自定义无序列表显示：定义无序符号
//                put("custom_bullet", getData(new NumberingFormat(STNumberFormat.BULLET, "♬")));
                // 自定义编号样式
                Style fmtStyle = new Style("f44336");
                fmtStyle.setBold(true);
                fmtStyle.setItalic(true);
                fmtStyle.setStrike(true);
                fmtStyle.setFontSize(24);
                put("custom_style",Numberings.of(NumberingFormat.LOWER_ROMAN)
                        .addItem(Paragraphs.of().addText(new TextRenderData("df2d4f","Deeply in love with the things you love, just deepoove.")).glyphStyle(fmtStyle).create())
                        .addItem(Paragraphs.of().addText(new TextRenderData("Deeply in love with the things you love, just deepoove.")).glyphStyle(fmtStyle).create())
                        .addItem(Paragraphs.of().addText(new TextRenderData("5285c5","Deeply in love with the things you love, just deepoove.")).glyphStyle(fmtStyle).create())
                        .create());
                        
                // 图片、超链接、文本
//                put("picture_hyper_text", getPictureData());
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_numbering.docx").render(datas);

        FileOutputStream out = new FileOutputStream("out_render_numbering.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    private NumberingRenderData getData(NumberingFormat format) {
        return Numberings.of(format)
                .addItem(new TextRenderData("df2d4f", "Deeply in love with the things you love, just deepoove."))
                .addItem(new TextRenderData("Deeply in love with the things you love, just deepoove."))
                .addItem(new TextRenderData("5285c5", "Deeply in love with the things you love, just deepoove."))
                .create();
    }

    private NumberingRenderData getPictureData() {
        final HyperLinkTextRenderData hyperLinkTextRenderData = new HyperLinkTextRenderData("Deepoove website.",
                "http://www.deepoove.com");
        hyperLinkTextRenderData.getStyle().setBold(true);
        return Numberings.ofDecimalParentheses().addItem(new PictureRenderData(120, 120, "src/test/resources/sayi.png"))
                .addItem(hyperLinkTextRenderData).addItem(hyperLinkTextRenderData)
                .addItem(new PictureRenderData(120, 120, "src/test/resources/sayi.png"))
                .addItem(new PictureRenderData(120, 120, "src/test/resources/sayi.png"))
                .addItem(new TextRenderData("df2d4f", "Deeply in love with the things you love,\n just deepoove."))
                .addItem(hyperLinkTextRenderData).create();
    }

}
