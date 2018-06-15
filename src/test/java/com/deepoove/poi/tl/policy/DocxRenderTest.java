package com.deepoove.poi.tl.policy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.policy.DocxRenderPolicy;

/**
 * 模板循坏
 * @author Sayi
 * @version 1.3.0
 */
public class DocxRenderTest {
    
    List<Data>  dataList;
    
    @Before
    public void init(){
        Data data1 = new Data();
        data1.setQuestion("贞观之治是历史上哪个朝代");
        data1.setA("宋");
        data1.setB("唐");
        data1.setC("明");
        data1.setD("清");
        data1.setLogo(new PictureRenderData(100, 120, "src/test/resources/logo.png"));
        
        Data data2 = new Data();
        data2.setQuestion("康乾盛世是历史上哪个朝代");
        data1.setA("三国");
        data1.setB("元");
        data1.setC("清");
        data1.setD("唐");
        data2.setLogo(new PictureRenderData(20, 20, "src/test/resources/0-1.png"));
        
        dataList = Arrays.asList(data1, data2);
    }

    @SuppressWarnings("serial")
    @Test
    public void testDocxRender() throws Exception {
        
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "Hello, poi tl.");
                
                put("docx_template", new DocxRenderData(new File("src/test/resources/merge_xwpf_template.docx"), dataList));

                put("newline", "End.");
                
            }
        };

        Configure configure = Configure.newBuilder()
                .customPolicy("docx_template", new DocxRenderPolicy()) 
                .build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/docx_render.docx", configure)
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_docx_render.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }
    
}
