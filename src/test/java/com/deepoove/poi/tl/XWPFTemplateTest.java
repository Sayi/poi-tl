package com.deepoove.poi.tl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.tl.source.MyDataModel;

/**
 * @author Sayi
 */
public class XWPFTemplateTest {

    RowRenderData header;
    List<RowRenderData> tableDatas;

    @Before
    public void init() {
        header = new RowRenderData(
                Arrays.asList(new TextRenderData("FFFFFF", "Word处理解决方案"),
                        new TextRenderData("FFFFFF", "是否跨平台"), new TextRenderData("FFFFFF", "易用性")),
                "ff9800");
        RowRenderData row0 = RowRenderData.build("Poi-tl", "纯Java组件，跨平台", "简单：模板引擎功能，并对POI进行了一些封装");
        RowRenderData row1 = RowRenderData.build("Apache Poi", "纯Java组件，跨平台", "简单，缺少一些功能的封装");
        RowRenderData row2 = RowRenderData.build("Freemarker", "XML操作，跨平台", "复杂，需要理解XML结构");
        RowRenderData row3 = RowRenderData.build("OpenOffice", "需要安装OpenOffice软件",
                "复杂，需要了解OpenOffice的API");
        RowRenderData row4 = RowRenderData.build("Jacob、winlib", "Windows平台", "复杂，不推荐使用");
        tableDatas = Arrays.asList(row0, row1, row2, row3, row4);
    }

    @SuppressWarnings("serial")
    @Test
    public void testRenderMap() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("header", "Deeply love what you love.");
                put("name", "Poi-tl");
                put("word", "模板引擎");
                put("time", "2019-05-31");
                put("what",
                        "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
                put("author", new TextRenderData("000000", "Sayi卅一"));
                put("introduce", new HyperLinkTextRenderData("http://www.deepoove.com", "http://www.deepoove.com"));
                put("portrait", new PictureRenderData(60, 60, "src/test/resources/sayi.png"));

                put("solution_compare", new MiniTableRenderData(header, tableDatas,
                        MiniTableRenderData.WIDTH_A4_FULL));

                put("feature", new NumbericRenderData(new ArrayList<TextRenderData>() {
                    {
                        add(new TextRenderData("Plug-in grammar, add new grammar by yourself"));
                        add(new TextRenderData(
                                "Supports word text, local pictures, web pictures, table, list, header, footer..."));
                        add(new TextRenderData(
                                "Templates, not just templates, but also style templates"));
                    }
                }));
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template.docx")
                .render(datas);;

        FileOutputStream out = new FileOutputStream("out_template.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    @SuppressWarnings("serial")
    @Test
    public void testRenderObject() throws Exception {
        MyDataModel obj = new MyDataModel();
        obj.setHeader("Deeply love what you love.");
        obj.setName("Poi-tl");
        obj.setWord("模板引擎");
        obj.setTime("2019-05-31");
        obj.setWhat(
                "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
        obj.setAuthor("Sayi卅一");
        obj.setIntroduce("http://www.deepoove.com");
        obj.setPortrait(new PictureRenderData(60, 60, "src/test/resources/sayi.png"));
        obj.setSolutionCompare(new MiniTableRenderData(header, tableDatas));
        obj.setFeature(new NumbericRenderData(new ArrayList<TextRenderData>() {
            {
                add(new TextRenderData("Plug-in grammar, add new grammar by yourself"));
                add(new TextRenderData(
                        "Supports word text, local pictures, web pictures, table, list, header, footer..."));
                add(new TextRenderData("Templates, not just templates, but also style templates"));
            }
        }));

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template.docx")
                .render(obj);

        FileOutputStream out = new FileOutputStream("out_template_object.docx");
        template.write(out);
        template.close();
        out.flush();
        out.close();
    }
}
