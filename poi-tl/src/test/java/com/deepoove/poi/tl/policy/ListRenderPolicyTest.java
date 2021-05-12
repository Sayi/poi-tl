package com.deepoove.poi.tl.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.ListRenderPolicy;

@DisplayName("List Render test case")
public class ListRenderPolicyTest {

    @SuppressWarnings("serial")
    @Test
    public void testListData() throws Exception {

        final List<Object> list = new ArrayList<Object>() {
            {
                add(new TextRenderData("ver 0.0.3"));
                add(new PictureRenderData(100, 120, "src/test/resources/logo.png"));
                add(new TextRenderData("9d55b8", "Deeply in love with the things you love, just deepoove."));
                add(new TextRenderData("ver 0.0.4"));
                add(new PictureRenderData(100, 120, "src/test/resources/logo.png"));
                add(Numberings.ofDecimalParentheses()
                        .addItem("Deeply in love with the things you love, just deepoove.")
                        .addItem("Deeply in love with the things you love, just deepoove.")
                        .addItem("Deeply in love with the things you love, just deepoove.").create());
                add(Tables.of(new String[][] { 
                    new String[] { "00", "01" }, 
                    new String[] { "10", "11" }, 
                }).create());
            }
        };

        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("website", list);

        /**
         * List demo {{website}} List demo.
         */
        Configure config = Configure.builder().bind("website", new ListRenderPolicy() {
        }).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_list.docx", config);
        template.render(datas).writeToFile("out_render_list.docx");
    }

}
