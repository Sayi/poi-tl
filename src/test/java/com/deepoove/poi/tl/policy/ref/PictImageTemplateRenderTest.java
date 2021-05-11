package com.deepoove.poi.tl.policy.ref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Pictures;

@DisplayName("PictImage Render test case")
public class PictImageTemplateRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testIterablePictRender() throws Exception {
        PictureRenderData image = Pictures.ofLocal("src/test/resources/sayi.png").size(100, 120).create();
        PictureRenderData image2 = Pictures.ofLocal("src/test/resources/logo.png").size(100, 120).create();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "poi-tl");
        data.put("test_img", image);
        Map<String, Object> data1 = new HashMap<String, Object>();
        data1.put("title", "poi-tl2");
        data1.put("test_img", image2);

        List<Map<String, Object>> mores = new ArrayList<Map<String, Object>>();
        mores.add(data);
        mores.add(data1);

        XWPFTemplate.compile("src/test/resources/template/reference_pict_iterable.docx")
                .render(new HashMap<String, Object>() {
                    {
                        put("mores", mores);
                    }
                })
                .writeToFile("out_reference_pict_iterable.docx");
    }

    @SuppressWarnings("serial")
    @Test
    public void testReplacePict() throws Exception {
        XWPFTemplate.compile("src/test/resources/template/reference_pict.docx").render(new HashMap<String, Object>() {
            {
                put("test_img", Pictures.ofLocal("src/test/resources/sayi.png").size(100, 120).create());
            }
        }).writeToFile("out_reference_pict.docx");
    }

}
