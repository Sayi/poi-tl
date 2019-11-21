package com.deepoove.poi.tl.policy;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.policy.AbstractRenderPolicy.DiscardHandler;

public class ConfigurePluginTest {

    @SuppressWarnings("serial")
    @Test
    public void testConfig() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "Hello, poi tl.");
                put("text", new PictureRenderData(100, 120, "src/test/resources/logo.png"));
                put("word", "虽然我是百分号开头，但是我也被自定义成文本了");
            }
        };

        Configure configure = Configure.newBuilder().buildGramer("[[", "]]")  //自定义语法以[[开头，以]]结尾
                .addPlugin('%', new TextRenderPolicy()) //添加%语法：%开头的也是文本
                .customPolicy("text", new PictureRenderPolicy()) //自定义标签text的策略：不是文本，是图片
                .build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/config.docx", configure)
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_config.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }
    
    @Test
    public void testNullToDoNothing() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>();

        Configure configure = Configure.newBuilder().buildGramer("[[", "]]").setValidErrorHandler(new DiscardHandler())
                .build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/config.docx", configure)
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_config_noblank.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }

}
