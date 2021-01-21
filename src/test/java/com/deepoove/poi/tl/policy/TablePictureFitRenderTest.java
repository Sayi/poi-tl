package com.deepoove.poi.tl.policy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.policy.TableRenderPolicy;

@DisplayName("Width Fit Render test case")
public class TablePictureFitRenderTest {

    @Test
    public void testFit() throws Exception {
        // table
        TableRenderData table = Tables
                .of(new String[][] { new String[] { "00", "01", "02", "03", "04" },
                        new String[] { "10", "11", "12", "13", "14" }, new String[] { "20", "21", "22", "23", "24" },
                        new String[] { "30", "31", "32", "33", "34" } })
                .border(BorderStyle.DEFAULT).create();

        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("table", table);

        Configure config = Configure.builder().bind("table", new TableRenderPolicy()).build();
        XWPFTemplate.compile("src/test/resources/template/width_fit.docx", config).render(datas)
                .writeToFile("out_width_fit.docx");

    }
}
