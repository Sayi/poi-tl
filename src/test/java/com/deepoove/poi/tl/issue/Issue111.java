package com.deepoove.poi.tl.issue;

import java.util.HashMap;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;

public class Issue111 {

    @SuppressWarnings("serial")
    @Test
    public void testCRBR() throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            String info = "姓名{{name" + i + "}}，年龄：{{age" + i + "}}。";
            sb.append(info).append("\n");
        }
        final String Info = sb.toString();
        XWPFTemplate templateRule = XWPFTemplate.compile("src/test/resources/issue/111.docx")
                .render(new HashMap<String, Object>() {
                    {
                        put("title", Info);
                    }
                });

        templateRule.writeToFile("out_2.docx");
        XWPFTemplate template = XWPFTemplate.compile("out_2.docx")
                .render(new HashMap<String, Object>() {
                    {
                        for (int j = 0; j < 5; j++) {
                            put("name" + j, "测试姓名" + j);
                            put("age" + j, "测试年龄" + j);
                        }
                    }
                });

        template.writeToFile("out_issue_111.docx");
    }

}
