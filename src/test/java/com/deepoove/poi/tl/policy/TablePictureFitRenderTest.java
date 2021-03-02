package com.deepoove.poi.tl.policy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;

@DisplayName("Width Fit Render test case")
public class TablePictureFitRenderTest {

    @Test
    public void testFit() throws Exception {
        // table
        TableRenderData table = Tables
                .of(new String[][] { new String[] { "00", "01", "02", "03", "04" },
                        new String[] { "10", "11", "12", "13", "14" } })
                .percentWidth("100%", new int[] { 10, 20, 10, 20, 40 }).create();

        // picture
        PictureRenderData picture = Pictures.ofLocal("src/test/resources/large.png").fitSize().create();

        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("table", table);
        datas.put("picture", picture);

        Configure config = Configure.builder().build();
        XWPFTemplate.compile("src/test/resources/template/width_fit.docx", config).render(datas)
                .writeToFile("out_fit_width.docx");

    }
}
