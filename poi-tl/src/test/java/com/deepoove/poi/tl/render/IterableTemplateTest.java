package com.deepoove.poi.tl.render;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperlinkTextRenderData;
import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("Foreach template test case")
public class IterableTemplateTest {

    @SuppressWarnings("serial")
    @Test
    public void testIterableWithStyle() throws Exception {
        List<Map<String, Object>> sections = new ArrayList<>();
        sections.add(new HashMap<String, Object>() {
            {
                put("title", "1.1 小节");
                put("word", "这是第一小节的内容");

            }
        });
        sections.add(new HashMap<String, Object>() {
            {
                put("title", "1.2 小节");
                put("word", "这是第二小节的内容");

            }
        });
        List<Map<String, Object>> chapters = new ArrayList<>();
        chapters.add(new HashMap<String, Object>() {
            {
                put("title", "第一章");
                put("name", "Sayi");
                put("sections", sections);

            }
        });
        chapters.add(new HashMap<String, Object>() {
            {
                put("title", "第二章");
                put("name", "Deepoove");

            }
        });
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("chapters", chapters);

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_foreach_withstyle.docx");
        template.render(datas).writeToFile("out_iterable_foreach_withstyle.docx");
    }

    @SuppressWarnings("serial")
    @Test
    public void testForeach() throws Exception {
        List<Map<String, Object>> addrs = new ArrayList<>();
        addrs.add(Collections.singletonMap("position", "Hangzhou,China"));
        addrs.add(Collections.singletonMap("position", "Shanghai,China"));

        List<Map<String, Object>> users = new ArrayList<>();
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Sayi");
                put("addrs", addrs);
            }
        });
        users.add(Collections.singletonMap("name", "Deepoove"));
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("users", users);
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_foreach.docx");
        template.render(datas);
        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        assertEquals("Hi, poi-tl", document.getParagraphArray(0).getText());
        assertEquals("My perfect Sayi.My perfect Deepoove.", document.getParagraphArray(1).getText());
        assertEquals("Please My perfect Sayi.My perfect Deepoove.", document.getParagraphArray(2).getText());
        assertEquals("Good My perfect Sayi.My perfect Deepoove. Game.", document.getParagraphArray(3).getText());

        assertEquals("Hello, My perfect Sayi.", document.getParagraphArray(5).getText());

        XWPFTable table0 = document.getTables().get(0);
        assertEquals("Sayi", table0.getRow(1).getCell(2).getText());
        assertEquals("addr: Hangzhou,China.", table0.getRow(2).getCell(1).getParagraphArray(0).getText());
        assertEquals("addr: Shanghai,China.", table0.getRow(2).getCell(1).getParagraphArray(1).getText());

        XWPFTable table1 = document.getTables().get(1);
        assertEquals("Deepoove", table1.getRow(1).getCell(2).getText());
        assertEquals("", table1.getRow(2).getCell(1).getText());

        document.close();

    }

    @SuppressWarnings("serial")
    @Test
    public void testHyperField() throws Exception {
        List<Map<String, Object>> addrs = new ArrayList<>();
        addrs.add(Collections.singletonMap("position", "Hangzhou,China"));
        addrs.add(Collections.singletonMap("position", "Shanghai,China"));

        List<Map<String, Object>> users = new ArrayList<>();
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Sayi");
                put("addrs", addrs);

            }
        });
        users.add(new HashMap<String, Object>() {
            {
                put("name", new HyperlinkTextRenderData("Deepoove website.", "http://www.google.com"));

            }
        });
        Map<String, Object> datas = Collections.singletonMap("users", users);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_hyperlink.docx");
        template.render(datas);
        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        assertEquals("开始，", document.getParagraphArray(0).getText());
        assertEquals("结束。", document.getParagraphArray(1).getText());
        assertEquals("开始，结束。", document.getParagraphArray(3).getText());
        assertEquals(
                "Hello, My perfect, http://deepoove.com,Sayi."
                + "addr:http://deepoove.comHangzhou,China.addr:http://deepoove.comShanghai,China."
                + "Hello, My perfect, http://deepoove.com,Deepoove website..",
                document.getParagraphArray(6).getText());
        document.close();
    }

    @SuppressWarnings("serial")
    @Test
    @DisplayName("using all gramer together")
    public void testTogetherBasic() throws Exception {
        RowRenderData row0 = Rows
                .of(new HyperlinkTextRenderData("张三", "http://deepoove.com"), new TextRenderData("1E915D", "研究生"))
                .create();

        RowRenderData row1 = Rows.of("李四", "博士").create();

        RowRenderData row2 = Rows.of("王五", "博士后").create();

        final TextRenderData textRenderData = new TextRenderData("负责生产BUG，然后修复BUG，同时有效实施招聘行为");
        Style style = new Style();
        style.setFontSize(10);
        style.setColor("7F7F7F");
        style.setFontFamily("微软雅黑");
        textRenderData.setStyle(style);
        List<Map<String, Object>> addrs = new ArrayList<>();
        addrs.add(new HashMap<String, Object>() {
            {
                put("position", "Hangzhou,China");

            }
        });
        addrs.add(new HashMap<String, Object>() {
            {
                put("position", "Shanghai,China");

            }
        });

        List<Map<String, Object>> users = new ArrayList<>();
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Sayi");
                put("addrs", addrs);
                put("list", new NumberingRenderData(NumberingFormat.DECIMAL, textRenderData, textRenderData));
                put("image", new PictureRenderData(120, 120, "src/test/resources/sayi.png"));
                put("table", Tables.of(row0, row1, row2).create());

            }
        });
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Deepoove");
                put("list", new NumberingRenderData(NumberingFormat.DECIMAL, textRenderData, textRenderData));
                put("image", new PictureRenderData(120, 120, "src/test/resources/sayi.png"));

            }
        });
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("users", users);
                put("thisref", Arrays.asList("good", "people"));

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_foreach_all.docx");
        template.render(datas);
        template.writeToFile("out_iterable_foreach_all.docx");
    }

}
