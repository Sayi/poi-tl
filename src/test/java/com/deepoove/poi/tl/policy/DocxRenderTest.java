package com.deepoove.poi.tl.policy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.tl.source.DataTest;

/**
 * 模板循坏
 * 
 * @author Sayi
 * @version 1.3.0
 */
public class DocxRenderTest {

    List<DataTest> dataList;

    @Before
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

                // 循环合并模板merge_xwpf_template.docx
                put("docx_template", new DocxRenderData(
                        new File("src/test/resources/merge_xwpf_template.docx"), dataList));

                // 合并文档merge_picture.docx
                put("docx_template2",
                        new DocxRenderData(new File("src/test/resources/merge_picture.docx")));

                put("docx_template3",
                        new DocxRenderData(new File("src/test/resources/merge_table.docx")));

                put("docx_template4", new DocxRenderData(
                        new FileInputStream(new File("src/test/resources/merge_all.docx"))));

                put("newline", "Why poi-tl?");

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/docx_render.docx")
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_docx_render.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }

}
