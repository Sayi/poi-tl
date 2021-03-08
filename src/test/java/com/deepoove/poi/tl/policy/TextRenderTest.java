package com.deepoove.poi.tl.policy;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.xwpf.XWPFHighlightColor;

@DisplayName("Text Render test case")
public class TextRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testTextRender() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "Hello, poi tl.");
                put("text", new TextRenderData("28a745", "我是\n绿色且换行的文字"));

                // 超链接
                put("link", Texts.of("Deepoove website.").link("http://www.deepoove.com").bold().create());
                // 邮箱超链接
                put("maillink", Texts.of("发邮件给作者").mailto("sayi@apache.org", "poi-tl").create());

                // 指定文本样式
                Style style = new Style("FF5722");
                style.setBold(true); 
                style.setFontSize(48l);
                style.setItalic(true);
                style.setStrike(true);
                style.setUnderlinePatterns(UnderlinePatterns.SINGLE);
                style.setFontFamily("微软雅黑");
                style.setWesternFontFamily("Monaco");
                style.setCharacterSpacing(20);
                style.setHighlightColor(XWPFHighlightColor.DARK_GREEN);
                put("word", Texts.of("深爱你所爱；just deepoove.").style(style).create());

                // 换行
                put("newline", "hi\n\n\n\n\nhello");

                // 从文件读取文字
                File file = new File("src/test/resources/template/render_text_word.txt");
                FileInputStream in = new FileInputStream(file);
                int size = in.available();
                byte[] buffer = new byte[size];
                in.read(buffer);
                in.close();
                put("newline_from_file", new String(buffer));

                // 上标
                put("super", Texts.of("a+b").sup().create());
                // 下标
                put("sub", Texts.of("a+b").sub().create());
            }
        };

        XWPFTemplate.compile("src/test/resources/template/render_text.docx").render(datas)
                .writeToFile("out_render_text.docx");

    }

}
