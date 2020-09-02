package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.HyperlinkTextRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.exception.ExpressionEvalException;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("ELMode test case")
public class ELModeTest {

    /**
     * {{author.name}} {{author.alias}} {{@author.avatar}}
     * 
     * {{#detail.diff}} {{detail.desc.date}} {{detail.desc.website}}
     */
    String resource = "src/test/resources/template/config_elmode.docx";

    DataModel model = new DataModel();

    TableRenderData table;

    @BeforeEach
    public void init() {
        RowRenderData header = Rows.of(new TextRenderData("FFFFFF", "Word处理解决方案"),
                new TextRenderData("FFFFFF", "是否跨平台"), new TextRenderData("FFFFFF", "易用性")).bgColor("ff9800").create();
        RowRenderData row0 = Rows.of("Poi-tl", "纯Java组件，跨平台", "简单：模板引擎功能，并对POI进行了一些封装").create();
        RowRenderData row1 = Rows.of("Apache Poi", "纯Java组件，跨平台", "简单，缺少一些功能的封装").create();
        RowRenderData row2 = Rows.of("Freemarker", "XML操作，跨平台", "复杂，需要理解XML结构").create();
        RowRenderData row3 = Rows.of("OpenOffice", "需要安装OpenOffice软件", "复杂，需要了解OpenOffice的API").create();
        RowRenderData row4 = Rows.of("Jacob、winlib", "Windows平台", "复杂，不推荐使用").create();
        table = Tables.of(header, row0, row1, row2, row3, row4).create();

        Author author = new Author();
        author.setName("Sayi");
        author.setAlias(new TextRenderData("FF0000", "卅一"));
        author.setAvatar(new PictureRenderData(60, 60, "src/test/resources/sayi.png"));
        model.setAuthor(author);
        Detail detail = new Detail();
        detail.setDiff(table);
        Desc desc = new Desc();
        desc.setDate("2018-10-01");
        desc.setWebsite(new HyperlinkTextRenderData("http://www.deepoove.com", "http://www.deepoove.com"));
        detail.setDesc(desc);
        model.setDetail(detail);
    }

    @Test
    public void testPoitlELMode() throws Exception {
        model.getDetail().setDesc(null);
        // poi_tl_mode 当变量不存在时，会友好的认为变量是null，不会抛出异常
        XWPFTemplate template = XWPFTemplate.compile(resource).render(model);
        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        XWPFParagraph paragraph = document.getParagraphArray(0);
        assertEquals(paragraph.getText(), "Sayi");
        paragraph = document.getParagraphArray(1);
        assertEquals(paragraph.getText(), "卅一");
        paragraph = document.getParagraphArray(3);
        assertEquals(paragraph.getText(), "");
        paragraph = document.getParagraphArray(4);
        assertEquals(paragraph.getText(), "");

        assertEquals(document.getAllPictures().size(), 1);
        assertEquals(document.getTables().size(), 1);
        document.close();
    }

    @Test
    public void testPoitlStrictELMode() throws Exception {
        model.getDetail().setDesc(null);
        // poi_tl_strict_mode 无法容忍变量不存在，直接抛出异常(可以防止人为的失误)
        Configure config = Configure.builder().useDefaultStrictEL().build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config);

        RenderException exception = assertThrows(RenderException.class, () -> template.render(model));
        assertTrue(exception.getCause() instanceof ExpressionEvalException);
    }

    @Test
    public void testSpringELMode() throws Exception {
        // Spring EL 无法容忍变量不存在，直接抛出异常，表达式计算引擎为Spring Expression Language
        Configure config = Configure.builder().useSpringEL().build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config).render(model);

        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        XWPFParagraph paragraph = document.getParagraphArray(0);
        assertEquals(paragraph.getText(), "Sayi");
        paragraph = document.getParagraphArray(1);
        assertEquals(paragraph.getText(), "卅一");
        paragraph = document.getParagraphArray(3);
        assertEquals(paragraph.getText(), "2018-10-01");
        paragraph = document.getParagraphArray(4);
        assertEquals(paragraph.getText(), "http://www.deepoove.com");

        assertEquals(document.getAllPictures().size(), 1);
        assertEquals(document.getTables().size(), 1);
        document.close();

    }

}
