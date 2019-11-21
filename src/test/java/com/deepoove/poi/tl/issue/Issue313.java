package com.deepoove.poi.tl.issue;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;

public class Issue313 {

    // 模板生成模板
    @Test
    public void tempToTemp() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("item1", "{{#excel_first}}\n{{#excel_second}}");
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/313.docx")
                .render(map);
        FileOutputStream out = new FileOutputStream("out_issue_template_313.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

        excelRender("out_issue_template_313.docx", "out_issue_313.docx");
    }

    public void excelRender(String temppath, String outpath) throws Exception {
        TextRenderData[] arr = new TextRenderData[10];
        arr[1] = new TextRenderData("FFFFFF", "姓名");
        TableStyle headerStyle = new TableStyle();
        headerStyle.setBackgroundColor("80C687");// 表的标题背景
        RowRenderData header = RowRenderData.build(new TextRenderData("FFFFFF", "姓名"),
                new TextRenderData("FFFFFF", "学历"), new TextRenderData("FFFFFF", "aaf"));// 表头
        header.setRowStyle(headerStyle);
        RowRenderData row0 = RowRenderData.build("张三", "研究生", "");// 每列的数据
        RowRenderData row1 = RowRenderData.build("李四", "博士", "aa");
        RowRenderData row2 = RowRenderData.build("王五", "博士后", "");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("excel_first", new MiniTableRenderData(header, Arrays.asList(row0, row1)));
        map.put("excel_second", new MiniTableRenderData(header, Arrays.asList(row2)));
        XWPFTemplate template = XWPFTemplate.compile(temppath).render(map);
        FileOutputStream out = new FileOutputStream(outpath);
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

}
