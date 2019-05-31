package com.deepoove.poi.tl.policy;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.tl.ext.CustomTableRenderPolicy;

/**
 * @author Sayi
 * @version 1.3.0
 */
public class MiniTableRenderTest {

    RowRenderData header = RowRenderData.build(new TextRenderData("FFFFFF", "姓名"),
            new TextRenderData("FFFFFF", "学历"));

    RowRenderData row0 = RowRenderData.build(
            new HyperLinkTextRenderData("张三", "http://deepoove.com"),
            new TextRenderData("1E915D", "研究生"));

    RowRenderData row1 = RowRenderData.build("李四", "博士");

    RowRenderData row2 = RowRenderData.build("王五", "博士后");

    RowRenderData row3;

    @Before
    public void init() {
        List<CellRenderData> cellDatas = new ArrayList<CellRenderData>();
        TableStyle cellStyle = new TableStyle();
        cellStyle.setBackgroundColor("0000ff");
        cellStyle.setAlign(STJc.LEFT);
        cellDatas.add(new CellRenderData(new TextRenderData("FFFFFF", "白字\n蓝底居左"), cellStyle));
        cellStyle = new TableStyle();
        cellStyle.setBackgroundColor("666666");
        cellStyle.setAlign(STJc.RIGHT);
        cellDatas.add(new CellRenderData(new TextRenderData("00ff00", "绿字灰底居右"), cellStyle));
        row3 = new RowRenderData(cellDatas);
    }

    @SuppressWarnings("serial")
    @Test
    public void testTable() throws Exception {

        // 表格头文字居中对齐
        TableStyle style = new TableStyle();
        style.setBackgroundColor("009688");
        style.setAlign(STJc.CENTER);
        header.setRowStyle(style);

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                // 有表格头 有数据，宽度自适应
                put("table",
                        new MiniTableRenderData(header, Arrays.asList(row0, row1, row2, row3)));
                // 没有表格头 没有数据，最终不会渲染这个table
                put("no_table", new MiniTableRenderData(null, null, "备注内容为空", 10));
                // 有数据，没有表格头
                put("no_header_table",
                        new MiniTableRenderData(Arrays.asList(row0, row1, row2, row3)));
                // 有表格头 没有数据
                put("no_content_table", new MiniTableRenderData(header, "备注内容为空"));

                // 指定宽度的表格
                // 表格居中
                MiniTableRenderData miniTableRenderData = new MiniTableRenderData(header,
                        Arrays.asList(row0, row1, row2, row3), 8.00f);
                TableStyle style = new TableStyle();
                style.setAlign(STJc.CENTER);
                miniTableRenderData.setStyle(style);
                put("width_table", miniTableRenderData);

                // 测试自定义表格策略
                put("report", new Object());
            }
        };
        Configure config = Configure.newBuilder()
                .customPolicy("report", new CustomTableRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/table.docx", config)
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_table.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
