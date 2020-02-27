package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("Issue313 模板生成模板表格")
public class Issue313 {

    // 模板生成模板
    @Test
    public void test313() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("item1", "{{#excel_first}}\n{{#excel_second}}");
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/313.docx").render(map);
        FileOutputStream out = new FileOutputStream("out_313temp.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

        excelRender("out_313temp.docx");
    }

    public void excelRender(String temppath) throws Exception {
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

        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        List<XWPFTable> tables = document.getTables();
        assertEquals(tables.size(), 2);

        assertEquals(tables.get(0).getRows().size(), 3);
        assertEquals(tables.get(1).getRows().size(), 2);

        document.close();
        new File(temppath).deleteOnExit();
    }

}
