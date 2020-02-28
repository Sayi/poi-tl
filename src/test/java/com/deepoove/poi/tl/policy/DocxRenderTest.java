package com.deepoove.poi.tl.policy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.tl.source.DataTest;

@DisplayName("Include Docx Render test case")
public class DocxRenderTest {

    List<DataTest> dataList;

    @BeforeEach
    public void init() {
        DataTest data1 = new DataTest();
        data1.setQuestion("贞观之治是历史上哪个朝代");
        data1.setA("宋");
        data1.setB("唐");
        data1.setC("明");
        data1.setD("清");
        data1.setLogo(new PictureRenderData(120, 120, "src/test/resources/sayi.png"));

        DataTest data2 = new DataTest();
        data2.setQuestion("康乾盛世是历史上哪个朝代");
        data2.setA("三国");
        data2.setB("元");
        data2.setC("清");
        data2.setD("唐");
        data2.setLogo(new PictureRenderData(100, 120, "src/test/resources/logo.png"));

        dataList = Arrays.asList(data1, data2);
    }

    @SuppressWarnings("serial")
    @Test
    public void testDocxTemplateRender() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "Hello, poi tl.");

                // 循环合并模板render_include_merge_template.docx
                put("docx_template", new DocxRenderData(
                        new File("src/test/resources/template/render_include_merge_template.docx"), dataList));

                // 合并文档render_include_picture.docx
                put("docx_template2", new DocxRenderData(new File("src/test/resources/template/render_include_picture.docx")));

                put("docx_template3", new DocxRenderData(new File("src/test/resources/template/render_include_table.docx")));

                put("docx_template4", new DocxRenderData(
                        new FileInputStream(new File("src/test/resources/template/render_include_all.docx"))));

                put("newline", "Why poi-tl?");

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_include.docx").render(datas);

        FileOutputStream out = new FileOutputStream("out_render_include.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }

}
