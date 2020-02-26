package com.deepoove.poi.tl.issue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.tl.source.DataTest;

// -Xloggc:gc.log  -XX:+PrintGCDetails -XX:+PrintGCDateStamps
public class Issue329 {

    List<DataTest> dataList;

    @BeforeEach
    public void init() {
        DataTest data1 = new DataTest();
        data1.setQuestion("贞观之治是历史上哪个朝代");
        data1.setA("宋");
        data1.setB("唐");
        data1.setC("明");
        data1.setD("清");
        // data1.setLogo(new PictureRenderData(120, 120,
        // "src/test/resources/sayi.png"));

        dataList = new ArrayList<DataTest>();

        for (int i = 0; i < 1000; i++) {
            dataList.add(data1);
        }
    }

    @SuppressWarnings("serial")
    @Test
    public void testDocxTemplateRender() throws Exception {

        // TimeUnit.SECONDS.sleep(20);

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                // 循环合并模板merge_xwpf_template.docx
                put("docx_template", new DocxRenderData(
                        new File("src/test/resources/merge_xwpf_template.docx"), dataList));
            }
        };

        // Zip Bomb detected
        ZipSecureFile.setMinInflateRatio(-1.0d);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/docx_render.docx")
                .render(datas);

        template.writeToFile("out_329.docx");

        System.out.println("game over................");

        TimeUnit.SECONDS.sleep(10);

    }

}
