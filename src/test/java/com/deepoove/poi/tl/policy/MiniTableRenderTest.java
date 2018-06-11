package com.deepoove.poi.tl.policy;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;

/**
 * @author Sayi
 * @version 1.3.0
 */
public class MiniTableRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testTable() throws Exception {
        final RowRenderData header = new RowRenderData(Arrays
                .asList(new TextRenderData("FFFFFF", "姓名"), new TextRenderData("FFFFFF", "学历")),
                "ff9800");
        
        final RowRenderData row0 = new RowRenderData(Arrays
                .asList(new TextRenderData("张三"), new TextRenderData("1E915D", "研究生")));
        final RowRenderData row1 = new RowRenderData(Arrays
                .asList(new TextRenderData("FFFFFF", "李四"), new TextRenderData("FFFFFF", "博士")), "ff9800");
        final RowRenderData row2 = new RowRenderData(Arrays
                .asList(new TextRenderData("王五"), new TextRenderData("1E915D", "博士后")));
        
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                // 有表格头 有数据，宽度自适应
                put("table", new MiniTableRenderData(header, Arrays.asList(row0, row1, row2)));
                // 没有表格头 没有数据，最终不会渲染这个table
                put("no_table", new MiniTableRenderData(null, null, "备注内容为空", 0));
                // 有数据，没有表格头
                put("no_header_table", new MiniTableRenderData(Arrays.asList(row0, row1, row2)));
                // 有表格头 没有数据
                put("no_content_table", new MiniTableRenderData(header, null, "备注内容为空", 0));

                // A4 纸宽度最大的表格
                put("width_table", new MiniTableRenderData(header, Arrays.asList(row0, row1, row2), "备注内容为空", 8310));
            }
        };
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/table_render.docx")
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_table_render.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
