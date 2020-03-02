package com.deepoove.poi.tl.issue;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;

@DisplayName("Issue331 hyperlink fldSimple")
public class Issue331 {

    @Test
    public void test331() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        // 超链接
        HyperLinkTextRenderData hyperLinkTextRenderData = new HyperLinkTextRenderData("Deepoove website.",
                "http://www.deepoove.com");
        hyperLinkTextRenderData.getStyle().setBold(true);
        map.put("link", hyperLinkTextRenderData);
        map.put("maillink", new HyperLinkTextRenderData("发邮件给作者", "mailto:adasai90@gmail.com?subject=poi-tl"));

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/331.docx").render(map);
        template.writeToFile("out_issue_331.docx");
    }

}
