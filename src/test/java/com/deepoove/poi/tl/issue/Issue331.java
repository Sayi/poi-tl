package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperlinkTextRenderData;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("Issue331 hyperlink fldSimple")
public class Issue331 {

    @Test
    public void test331() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        // 超链接
        HyperlinkTextRenderData hyperLinkTextRenderData = new HyperlinkTextRenderData("Deepoove website.",
                "http://www.deepoove.com");
        hyperLinkTextRenderData.getStyle().setBold(true);
        map.put("link", hyperLinkTextRenderData);
        map.put("maillink", new HyperlinkTextRenderData("发邮件给作者", "mailto:adasai90@gmail.com?subject=poi-tl"));

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/331.docx").render(map);
        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        assertEquals("QA4时20分2020/3/2 16:20Deepoove website. and 发邮件给作者", document.getParagraphArray(0).getText());
        document.close();

    }

    @SuppressWarnings("serial")
    @Test
    public void testRunTemlate() throws FileNotFoundException, IOException {
        XWPFTemplate template = XWPFTemplate.compile(new FileInputStream("src/test/resources/issue/331_hyper.docx"));

        template.render(new HashMap<String, Object>() {
            {
                put("title", "Hi");
            }
        });
        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        assertEquals("HiAAhttp://baidu.comhttp:deepoove.com", document.getParagraphArray(0).getText());
        document.close();
    }

}
