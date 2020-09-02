package com.deepoove.poi.tl.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;

@DisplayName("Foreach basic example")
public class IterableRenderBasicExample {

    TableRenderData table;
    NumberingRenderData numbering;

    @BeforeEach
    public void init() {
        // create table
        RowRenderData header = Rows.of("Word处理方案", "是否跨平台", "易用性").textColor("FFFFFF").bgColor("ff9800").center()
                .rowHeight(2.5f).create();
        RowRenderData row0 = Rows.of("Poi-tl", "纯Java组件，跨平台", "简单：模板引擎功能，并对POI进行了一些封装").create();
        RowRenderData row1 = Rows.of("Apache Poi", "纯Java组件，跨平台", "简单，缺少一些功能的封装").create();
        RowRenderData row2 = Rows.of("Freemarker", "XML操作，跨平台", "复杂，需要理解XML结构").create();
        RowRenderData row3 = Rows.of("OpenOffice", "需要安装OpenOffice软件", "复杂，需要了解OpenOffice的API").create();
        RowRenderData row4 = Rows.of("Jacob、winlib", "Windows平台", "复杂，不推荐使用").create();
        table = Tables.of(header, row0, row1, row2, row3, row4).create();

        // create numbering
        numbering = Numberings.of("Plug-in grammar, add new grammar by yourself",
                "Supports word text, local pictures, web pictures, table, list, header, footer...",
                "Templates, not just templates, but also style templates").create();
    }

    @SuppressWarnings("serial")
    @Test
    public void testRenderIterable() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("header", "Deeply love what you love.");
        data.put("name", "Poi-tl");
        data.put("word", "模板引擎");
        data.put("time", "2020-08-31");
        data.put("what",
                "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
        data.put("author", Texts.of("Sayi卅一").color("000000").create());
        data.put("introduce", Texts.of("http://www.deepoove.com").link("http://www.deepoove.com").create());
        data.put("portrait", Pictures.ofLocal("src/test/resources/sayi.png").size(60, 60).create());

        data.put("solution_compare", table);
        data.put("feature", numbering);

        List<Map<String, Object>> mores = new ArrayList<Map<String, Object>>();
        mores.add(data);
        mores.add(data);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_basic.docx");
        template.render(new HashMap<String, Object>() {
            {
                put("mores", mores);
            }
        }).writeToFile("out_iterable_basic.docx");
    }

}
