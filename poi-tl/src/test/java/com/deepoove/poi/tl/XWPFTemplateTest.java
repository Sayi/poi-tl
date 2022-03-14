package com.deepoove.poi.tl;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;

/**
 * @author Sayi
 */
public class XWPFTemplateTest {

    @Test
    public void testRenderTemplate() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>();

        // create table
        RowRenderData header = Rows.of("Word处理方案", "是否跨平台", "易用性")
                .textColor("FFFFFF")
                .bgColor("ff9800")
                .center()
                .rowHeight(2.5f)
                .create();
        RowRenderData row0 = Rows.create("Poi-tl", "纯Java组件，跨平台", "简单：模板引擎功能，并对POI进行了一些封装");
        RowRenderData row1 = Rows.create("Apache Poi", "纯Java组件，跨平台", "简单，缺少一些功能的封装");
        RowRenderData row2 = Rows.create("Freemarker", "XML操作，跨平台", "复杂，需要理解XML结构");
        RowRenderData row3 = Rows.create("OpenOffice", "需要安装OpenOffice软件", "复杂，需要了解OpenOffice的API");
        TableRenderData table = Tables.create(header, row0, row1, row2, row3);

        // text
        datas.put("header", "Deeply love what you love.");
        datas.put("name", "Poi-tl");
        datas.put("word", "模板引擎");
        datas.put("time", "2020-08-31");
        datas.put("what", "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java.");
        datas.put("author", Texts.of("Sayi卅一").color("000000").create());

        // hyperlink
        datas.put("introduce", Texts.of("http://www.deepoove.com").link("http://www.deepoove.com").create());
        // picture
        datas.put("portrait", Pictures.ofLocal("src/test/resources/sayi.png").size(60, 60).create());
        // table
        datas.put("solution_compare", table);
        // numbering
        datas.put("feature",
                Numberings.create("Plug-in grammar, add new grammar by yourself",
                        "Supports word text, local pictures, web pictures, table, list, header, footer...",
                        "Templates, not just templates, but also style templates"));

        // chart
        datas.put("chart",
                Charts.ofMultiSeries("易用性", new String[] { "代码量", "维护量" })
                        .addSeries("poi-tl", new Double[] { 10.0, 5.0 })
                        .addSeries("freemark", new Double[] { 90.0, 70.0 })
                        .create());

        XWPFTemplate.compile("src/test/resources/template/template.docx")
                .render(datas)
                .writeToFile("target/out_template.docx");
    }

}
