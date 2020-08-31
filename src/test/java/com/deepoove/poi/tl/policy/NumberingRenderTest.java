package com.deepoove.poi.tl.policy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.xwpf.NumFormat;

@DisplayName("Numbering Render test case")
public class NumberingRenderTest {

    @Test
    public void testNumberingRender() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>();
        // 1. 2. 3.
        datas.put("number123", getDataList(NumberingFormat.DECIMAL));
        datas.put("number123_dulplicate", getDataList(NumberingFormat.DECIMAL));
        // 1) 2) 3)
        datas.put("number123_kuohao", getDataList(NumberingFormat.DECIMAL_PARENTHESES));
        // bullet
        datas.put("bullet", getDataList(NumberingFormat.BULLET));
        // A B C
        datas.put("ABC", getDataList(NumberingFormat.UPPER_LETTER));
        // a b c
        datas.put("abc", getDataList(NumberingFormat.LOWER_LETTER));
        // ⅰ ⅱ ⅲ
        datas.put("iiiiii", getDataList(NumberingFormat.LOWER_ROMAN));
        // Ⅰ Ⅱ Ⅲ
        datas.put("IIIII", getDataList(NumberingFormat.UPPER_ROMAN));
        // (one) (two) (three)
        datas.put("custom_number", getDataList(new NumberingFormat(NumFormat.CARDINAL_TEXT, "(%1)")));
        // character
        datas.put("custom_bullet", getDataList(new NumberingFormat(NumFormat.BULLET, "♬")));
        // mark style
        Style fmtStyle = new Style("f44336");
        fmtStyle.setBold(true);
        fmtStyle.setItalic(true);
        fmtStyle.setStrike(true);
        fmtStyle.setFontSize(24);
        datas.put("custom_style", Numberings.of(NumberingFormat.LOWER_ROMAN)
                .addItem(Paragraphs.of()
                        .addText(new TextRenderData("df2d4f", "Deeply in love with the things you love, just deepoove."))
                        .glyphStyle(fmtStyle).create())
                .addItem(Paragraphs.of()
                        .addText(new TextRenderData("Deeply in love with the things you love, just deepoove."))
                        .addPicture(Pictures.ofLocal("src/test/resources/sayi.png").size(120, 120).create())
                        .glyphStyle(fmtStyle).create())
                .addItem(Paragraphs.of()
                        .addText(new TextRenderData("5285c5", "Deeply in love with the things you love, just deepoove."))
                        .glyphStyle(fmtStyle).create())
                .create());

        // paragraph list
        datas.put("picture_hyper_text", getPictureData());

        XWPFTemplate.compile("src/test/resources/template/render_numbering.docx").render(datas).writeToFile("out_render_numbering.docx");

    }

    private NumberingRenderData getDataList(NumberingFormat format) {
        return Numberings.of(format)
                .addItem(Texts.of("Deeply in love with the things you love, just deepoove.").color("df2d4f").create())
                .addItem("Deeply in love with the things you love, just deepoove.")
                .addItem(Texts.of("Deeply in love with the things you love, just deepoove.").color("5285c5").create())
                .create();
    }

    private NumberingRenderData getPictureData() {
        TextRenderData hyper = Texts.of("Deepoove website.").link("http://www.deepoove.com").bold().create();
        PictureRenderData image = Pictures.ofLocal("src/test/resources/sayi.png").size(120, 120).create();
        return Numberings.ofDecimalParentheses().addItem(image).addItem(hyper).addItem(hyper).addItem(image)
                .addItem(image)
                .addItem(Texts.of("Deeply in love with the things you love,\n just deepoove.").color("df2d4f").create())
                .addItem(hyper).create();
    }

}
