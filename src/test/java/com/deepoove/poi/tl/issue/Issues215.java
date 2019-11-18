package com.deepoove.poi.tl.issue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.AbstractRenderPolicy.DiscardHandler;

public class Issues215 {

    @SuppressWarnings("serial")
    @Test
    public void testRepeat() throws IOException {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("name", "Poi-tl");
                put("word.tt", "模板引擎");
                put("time", "2019-05-31");
                put("what",
                        "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
                put("author", new TextRenderData("000000", "Sayi卅一"));
                put("introduce", "http://www.deepoove.com");
                put("portrait", new PictureRenderData(60, 60, "src/test/resources/sayi.png"));

            }
        };

        Configure config = Configure.newBuilder().setValidErrorHandler(new DiscardHandler()).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template.docx", config)
                .render(datas);

        template.writeToFile("out_template_supportnulltoblank.docx");
    }

}
