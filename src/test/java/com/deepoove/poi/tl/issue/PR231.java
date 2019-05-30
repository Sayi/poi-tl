package com.deepoove.poi.tl.issue;

import java.io.FileOutputStream;
import java.util.HashMap;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

public class PR231 {

    /**
     * 支持中文变量--测试
     */
    @SuppressWarnings("serial")
    @Test
    public void testChinese() {
        Configure.ConfigureBuilder builder = Configure.newBuilder();
        builder.buildGramer("${", "}");
        XWPFTemplate template = XWPFTemplate
                .compile("src/test/resources/issue/231.docx", builder.build())
                .render(new HashMap<String, Object>() {
                    {
                        put("title", "模板引擎测试");
                        put("rent", "10000");
                        put("签约时间", "2019年5月13日");// 支持中文变量，格式为：${签约时间}
                    }
                });

        try {
            FileOutputStream out = new FileOutputStream("out_issue_231.docx");
            template.write(out);
            out.flush();
            out.close();
            template.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
