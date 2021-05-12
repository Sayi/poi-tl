package com.deepoove.poi.tl.xwpf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperlinkTextRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TextRenderData;

/**
 * @author Sayi
 */
public class TextboxTest {

    @SuppressWarnings("serial")
    @Test
    public void testRender() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("name", "Poi-tl");
                put("word", "模板引擎");
                put("time", "2019-05-31");
                put("author", new TextRenderData("000000", "Sayi卅一"));
                put("introduce", new HyperlinkTextRenderData("http://www.deepoove.com", "http://www.deepoove.com"));
                put("portrait", new PictureRenderData(60, 60, "src/test/resources/sayi.png"));
                
            }
        };

        List<Map<String, Object>> mores = new ArrayList<Map<String, Object>>();
        mores.add(datas);
        mores.add(datas);
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                put("mores", mores);
//                put("volumes", "1900");
//                put("journal", new HashMap<String, String>(){
//                    {
//                        put("issn", "8848");
//                    }
//                });
            }
        };

        XWPFTemplate.compile("src/test/resources/template/template_textbox.docx").render(data)
                .writeToFile("out_template_textbox.docx");

    }

}
