package com.deepoove.poi.tl.policy;

import static com.deepoove.poi.data.NumbericRenderData.FMT_BULLET;
import static com.deepoove.poi.data.NumbericRenderData.FMT_DECIMAL;
import static com.deepoove.poi.data.NumbericRenderData.FMT_DECIMAL_PARENTHESES;
import static com.deepoove.poi.data.NumbericRenderData.FMT_LOWER_LETTER;
import static com.deepoove.poi.data.NumbericRenderData.FMT_LOWER_ROMAN;
import static com.deepoove.poi.data.NumbericRenderData.FMT_UPPER_LETTER;
import static com.deepoove.poi.data.NumbericRenderData.FMT_UPPER_ROMAN;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;

/**
 * 列表模板
 * 
 * @author Sayi
 * @version 0.0.5
 */
public class NumbericRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testNumbericRender() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                // 1. 2. 3.
                put("number123", getData(FMT_DECIMAL));
                put("number123_dulplicate", getData(FMT_DECIMAL));
                // 1) 2) 3)
                put("number123_kuohao", getData(FMT_DECIMAL_PARENTHESES));
                // 无序
                put("bullet", getData(FMT_BULLET));
                // A B C
                put("ABC", getData(FMT_UPPER_LETTER));
                // a b c
                put("abc", getData(FMT_LOWER_LETTER));
                // ⅰ ⅱ ⅲ
                put("iiiiii", getData(FMT_LOWER_ROMAN));
                // Ⅰ Ⅱ Ⅲ
                put("IIIII", getData(FMT_UPPER_ROMAN));
                // 自定义有序列表显示 (one) (two) (three)
                put("custom_number", getData(Pair.of(STNumberFormat.CARDINAL_TEXT, "(%1)")));
                // 自定义无序列表显示：定义无序符号
                put("custom_bullet", getData(Pair.of(STNumberFormat.BULLET, "♬")));
                // 自定义编号样式
                Style fmtStyle = new Style("f44336");
                fmtStyle.setBold(true);
                fmtStyle.setItalic(true);
                fmtStyle.setStrike(true);
                fmtStyle.setFontSize(24);
                put("custom_style", new NumbericRenderData(FMT_LOWER_ROMAN, fmtStyle,
                        new ArrayList<TextRenderData>() {
                            {
                                add(new TextRenderData("df2d4f",
                                        "Deeply in love with the things you love, just deepoove."));
                                add(new TextRenderData(
                                        "Deeply in love with the things you love, just deepoove."));
                                add(new TextRenderData("5285c5",
                                        "Deeply in love with the things you love, just deepoove."));
                            }
                        }));
                // 图片、超链接、文本
                put("picture_hyper_text", getPictureData(FMT_DECIMAL_PARENTHESES));
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/numberic.docx")
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_numberic.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    @SuppressWarnings("serial")
    private NumbericRenderData getData(Pair<Enum, String> pair) {
        return new NumbericRenderData(pair, new ArrayList<TextRenderData>() {
            {
                add(new TextRenderData("df2d4f",
                        "Deeply in love with the things you love, just deepoove."));
                add(new TextRenderData("Deeply in love with the things you love, just deepoove."));
                add(new TextRenderData("5285c5",
                        "Deeply in love with the things you love, just deepoove."));
            }
        });
    }

    @SuppressWarnings("serial")
    private NumbericRenderData getPictureData(Pair<Enum, String> pair) {
        final HyperLinkTextRenderData hyperLinkTextRenderData = new HyperLinkTextRenderData(
                "Deepoove website.", "http://www.deepoove.com");
        hyperLinkTextRenderData.getStyle().setBold(true);
        return new NumbericRenderData(pair, new ArrayList<RenderData>() {
            {
                add(new PictureRenderData(120, 120, "src/test/resources/sayi.png"));
                add(hyperLinkTextRenderData);
                add(hyperLinkTextRenderData);
                add(new PictureRenderData(120, 120, "src/test/resources/sayi.png"));
                add(new PictureRenderData(120, 120, "src/test/resources/sayi.png"));
                add(new TextRenderData("df2d4f",
                        "Deeply in love with the things you love,\n just deepoove."));
                add(hyperLinkTextRenderData);
                // add(getData(FMT_UPPER_LETTER));
            }
        });
    }

}
