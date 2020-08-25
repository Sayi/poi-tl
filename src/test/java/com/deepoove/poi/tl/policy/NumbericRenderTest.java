package com.deepoove.poi.tl.policy;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;

@DisplayName("Numberic Render test case")
public class NumbericRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testNumbericRender() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                // 1. 2. 3.
                put("number123", getData(NumberingFormat.DECIMAL));
                put("number123_dulplicate", getData(NumberingFormat.DECIMAL));
                // 1) 2) 3)
                put("number123_kuohao", getData(NumberingFormat.DECIMAL_PARENTHESES));
                // 无序
                put("bullet", getData(NumberingFormat.BULLET));
                // A B C
                put("ABC", getData(NumberingFormat.UPPER_LETTER));
                // a b c
                put("abc", getData(NumberingFormat.LOWER_LETTER));
                // ⅰ ⅱ ⅲ
                put("iiiiii", getData(NumberingFormat.LOWER_ROMAN));
                // Ⅰ Ⅱ Ⅲ
                put("IIIII", getData(NumberingFormat.UPPER_ROMAN));
                // 自定义有序列表显示 (one) (two) (three)
                put("custom_number", getData(new NumberingFormat(STNumberFormat.CARDINAL_TEXT, "(%1)")));
                // 自定义无序列表显示：定义无序符号
                put("custom_bullet", getData(new NumberingFormat(STNumberFormat.BULLET, "♬")));
                // 自定义编号样式
                Style fmtStyle = new Style("f44336");
                fmtStyle.setBold(true);
                fmtStyle.setItalic(true);
                fmtStyle.setStrike(true);
                fmtStyle.setFontSize(24);
                put("custom_style", new NumbericRenderData(NumberingFormat.LOWER_ROMAN, fmtStyle, new ArrayList<TextRenderData>() {
                    {
                        add(new TextRenderData("df2d4f", "Deeply in love with the things you love, just deepoove."));
                        add(new TextRenderData("Deeply in love with the things you love, just deepoove."));
                        add(new TextRenderData("5285c5", "Deeply in love with the things you love, just deepoove."));
                    }
                }));
                // 图片、超链接、文本
                put("picture_hyper_text", getPictureData());
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_numberic.docx").render(datas);

        FileOutputStream out = new FileOutputStream("out_render_numberic.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    @SuppressWarnings("serial")
    private NumbericRenderData getData(NumberingFormat format) {
        return new NumbericRenderData(format, new ArrayList<TextRenderData>() {
            {
                add(new TextRenderData("df2d4f", "Deeply in love with the things you love, just deepoove."));
                add(new TextRenderData("Deeply in love with the things you love, just deepoove."));
                add(new TextRenderData("5285c5", "Deeply in love with the things you love, just deepoove."));
            }
        });
    }

    @SuppressWarnings("serial")
    private NumbericRenderData getPictureData() {
        final HyperLinkTextRenderData hyperLinkTextRenderData = new HyperLinkTextRenderData("Deepoove website.",
                "http://www.deepoove.com");
        hyperLinkTextRenderData.getStyle().setBold(true);
        return new NumbericRenderData(NumberingFormat.DECIMAL_PARENTHESES, new ArrayList<RenderData>() {
            {
                add(new PictureRenderData(120, 120, "src/test/resources/sayi.png"));
                add(hyperLinkTextRenderData);
                add(hyperLinkTextRenderData);
                add(new PictureRenderData(120, 120, "src/test/resources/sayi.png"));
                add(new PictureRenderData(120, 120, "src/test/resources/sayi.png"));
                add(new TextRenderData("df2d4f", "Deeply in love with the things you love,\n just deepoove."));
                add(hyperLinkTextRenderData);
                // add(getData(NumberingFormat.UPPER_LETTER));
            }
        });
    }

}
