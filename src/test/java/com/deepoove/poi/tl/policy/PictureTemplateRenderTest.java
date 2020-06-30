package com.deepoove.poi.tl.policy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;

@DisplayName("PictureTemplate Render test case")
public class PictureTemplateRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testPictureRender() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("pic", new PictureRenderData(100, 120, ".png", new FileInputStream("src/test/resources/sayi.png")));
            }
        };
        Map<String, Object> data1 = new HashMap<String, Object>() {
            {
                put("title", "poi-tl2");
                put("pic", new PictureRenderData(100, 120, ".png", new FileInputStream("src/test/resources/logo.png")));
            }
        };
        
        List<Map<String, Object>> mores = new ArrayList<Map<String, Object>>();
        mores.add(data);
        mores.add(data1);
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("mores", mores);

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_picture_template.docx")
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_render_picture_template.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

}
