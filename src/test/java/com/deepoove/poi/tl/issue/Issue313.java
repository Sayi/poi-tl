package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.TextRenderData;
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
        RowRenderData header = Rows.of(new TextRenderData("FFFFFF", "姓名"),
                new TextRenderData("FFFFFF", "学历"), new TextRenderData("FFFFFF", "aaf")).bgColor("80C687").create();// 表头
        RowRenderData row0 = Rows.of("张三", "研究生", "").create();// 每列的数据
        RowRenderData row1 = Rows.of("李四", "博士", "aa").create();
        RowRenderData row2 = Rows.of("王五", "博士后", "").create();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("excel_first", Tables.of(header, row0, row1).create());
        map.put("excel_second", Tables.of(header, row2).create());
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
