package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.Configure.AbortHandler;
import com.deepoove.poi.config.Configure.DiscardHandler;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;

@DisplayName("Configure test case")
public class ConfigureTest {

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

        ConfigureBuilder builder = Configure.newBuilder();

        // 自定义语法以[[开头，以]]结尾
        builder.buildGramer("[[", "]]");

        // 添加%语法：%开头的也是文本
        builder.addPlugin('%', new TextRenderPolicy());

        // 自定义标签text的策略：不是文本，是图片
        builder.bind("text", new PictureRenderPolicy());

        XWPFTemplate.compile("src/test/resources/config.docx", builder.build()).render(datas)
                .writeToFile("out_config.docx");

    }

    @Test
    public void testDiscardHandler() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>();

        ConfigureBuilder builder = Configure.newBuilder();
        // 自定义语法以[[开头，以]]结尾
        builder.buildGramer("[[", "]]");
        // 没有变量时，保留标签
        builder.setValidErrorHandler(new DiscardHandler());

        XWPFTemplate.compile("src/test/resources/config.docx", builder.build()).render(datas)
                .writeToFile("out_config_DiscardHandler.docx");

    }

    @Test
    public void testAbortHandler() {

        Map<String, Object> datas = new HashMap<String, Object>();

        ConfigureBuilder builder = Configure.newBuilder();
        // 自定义语法以[[开头，以]]结尾
        builder.buildGramer("[[", "]]");
        // 没有变量时，无法容忍，抛出异常
        builder.setValidErrorHandler(new AbortHandler());

        assertThrows(RenderException.class,
                () -> XWPFTemplate.compile("src/test/resources/config.docx", builder.build()).render(datas));

    }

}
