package com.deepoove.poi.tl.policy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;

public class TextRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testTextRender() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "Hello, poi tl.");
                put("text", new TextRenderData("28a745", "我是绿色的文字"));
                
                // 超链接
                HyperLinkTextRenderData hyperLinkTextRenderData = new HyperLinkTextRenderData("Deepoove website.", "http://www.deepoove.com");
                hyperLinkTextRenderData.getStyle().setBold(true);
                put("link", hyperLinkTextRenderData);
                put("maillink", new HyperLinkTextRenderData("发邮件给作者", "mailto:adasai90@gmail.com?subject=poi-tl"));
                
                // 指定文本样式
				TextRenderData textRenderData = new TextRenderData("just deepoove.");
				Style style = new Style("FF5722");
				textRenderData.setStyle(style);
				style.setBold(true);
				style.setFontSize(48);
				style.setItalic(true);
				style.setStrike(true);
				style.setUnderLine(true);
				style.setFontFamily("微软雅黑");
                put("word", textRenderData);
                
                // 换行
                put("newline", "我是换行的文字,\n End.");
                
                // 从文件读取文字
                File file = new File("src/test/resources/word.txt");
                FileInputStream in=new FileInputStream(file);  
                int size=in.available();  
                byte[] buffer=new byte[size];  
                in.read(buffer);  
                in.close();  
                put("newline_from_file", new String(buffer));
            }
        };

       
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/text_render.docx")
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_text_render.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }

}
