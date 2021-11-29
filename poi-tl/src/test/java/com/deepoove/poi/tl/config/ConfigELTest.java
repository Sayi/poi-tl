package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.exception.ExpressionEvalException;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("ConfigEL test case")
public class ConfigELTest {

    /**
     * {{author.name}} {{author.alias}} {{@author.avatar}}
     * 
     * {{#detail.diff}} {{detail.desc.date}} {{detail.desc.website}}
     */
    String resource = "src/test/resources/template/config_elmode.docx";

    DataModel model;

    @BeforeEach
    public void init() {
        model = new DataModel();

        Author author = new Author();
        author.setName("Sayi");
        author.setAlias(new TextRenderData("FF0000", "卅一"));
        author.setAvatar(new PictureRenderData(60, 60, "src/test/resources/sayi.png"));
        model.setAuthor(author);

        Detail detail = new Detail();
        TableRenderData table = Tables
                .of(new String[][] { new String[] { "00", "01", "02" }, new String[] { "10", "11", "12" }, }).create();
        detail.setDiff(table);
        Desc desc = new Desc();
        desc.setDate("2018-10-01");
        desc.setWebsite(new HyperlinkTextRenderData("http://www.deepoove.com", "http://www.deepoove.com"));
        detail.setDesc(desc);
        model.setDetail(detail);
    }

    @Test
    public void testDefaultELMode() throws Exception {
        model.getDetail().setDesc(null);
        // 当变量不存在时，会友好的认为变量是null，不会抛出异常
        XWPFTemplate template = XWPFTemplate.compile(resource).render(model);
        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        XWPFParagraph paragraph = document.getParagraphArray(0);
        assertEquals(paragraph.getText(), "Sayi");
        paragraph = document.getParagraphArray(1);
        assertEquals(paragraph.getText(), "卅一");
        paragraph = document.getParagraphArray(3);
        assertNull(paragraph);

        assertEquals(document.getAllPictures().size(), 1);
        assertEquals(document.getTables().size(), 1);
        document.close();
    }

    @Test
    public void testStrictELMode() throws Exception {
        model.getDetail().setDesc(null);
        // 无法容忍变量不存在，直接抛出异常(可以防止人为的失误)
        Configure config = Configure.builder().useDefaultEL(true).build();
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
