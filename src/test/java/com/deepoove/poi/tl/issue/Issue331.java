package com.deepoove.poi.tl.issue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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

    // insertNewRun 实现的bug，如果找不到i元素则在末尾插入，可能需要cursor或者qnameset来插入
    // @Test
    public void testinsertNewRunRun() throws FileNotFoundException, IOException {
        XWPFDocument doc = new XWPFDocument(new FileInputStream("src/test/resources/issue/331_hyper.docx"));
        XWPFParagraph createParagraph = doc.getParagraphArray(0);
        XWPFRun insertNewRun = createParagraph.insertNewRun(0);
        insertNewRun.setText("Hi");

        // FileOutputStream out = new FileOutputStream("out_tem.docx");
        // doc.write(out);
        // doc.close();
        // out.close();

    }

    // @Test
    public void testRunTemlate() throws FileNotFoundException, IOException {
        XWPFTemplate template = XWPFTemplate.compile(new FileInputStream("src/test/resources/issue/331_hyper.docx"));

        template.render(new HashMap<String, Object>() {
            {
                put("title", "Hi");
            }
        });
        // template.writeToFile("out_temp11.docx");

    }

}
