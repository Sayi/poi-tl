package com.deepoove.poi.tl.issue;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Pictures;

public class Issue554 {

    private Map<String, Object> data = new HashMap<String, Object>();

    @BeforeEach
    public void before() throws Exception {
        data.put("content_name",
                "https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1280325423,1024589167&fm=26&gp=0.jpg");
        data.put("tuan_number", "S-0915");
        data.put("date", "2019 年   10 月    12 日");
        data.put("tuan_start_date", "2019-09-15");
        data.put("tuan_end_date", "2019-09-20");
        data.put("receive", "高唐旅游百事通\n周经理");
        data.put("receive_phone", "15806353196");
        data.put("str", "TTT111111111111");
        data.put("test_img", Pictures.ofLocal("src/test/resources/logo.png").create());
    }

    @Test
    public void test554() throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/554.docx").render(data);
        FileOutputStream out = new FileOutputStream("target/out_issue_554.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

    }

}
