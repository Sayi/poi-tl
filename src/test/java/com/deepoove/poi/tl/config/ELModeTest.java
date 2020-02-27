package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.Configure.ELMode;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.exception.ExpressionEvalException;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.tl.XWPFTestSupport;

@DisplayName("ELMode test case")
public class ELModeTest {

    /**
     * {{author.name}} {{author.alias}} {{@author.avatar}}
     * 
     * {{#detail.diff}} {{detail.desc.date}} {{detail.desc.website}}
     */
    String resource = "src/test/resources/config_elmode.docx";

    DataModel model = new DataModel();

    @BeforeEach
    public void init() {
        RowRenderData header = new RowRenderData(Arrays.asList(new TextRenderData("FFFFFF", "Word处理解决方案"),
                new TextRenderData("FFFFFF", "是否跨平台"), new TextRenderData("FFFFFF", "易用性")), "ff9800");
        RowRenderData row0 = RowRenderData.build("Poi-tl", "纯Java组件，跨平台", "简单：模板引擎功能，并对POI进行了一些封装");
        RowRenderData row1 = RowRenderData.build("Apache Poi", "纯Java组件，跨平台", "简单，缺少一些功能的封装");
        RowRenderData row2 = RowRenderData.build("Freemarker", "XML操作，跨平台", "复杂，需要理解XML结构");
        RowRenderData row3 = RowRenderData.build("OpenOffice", "需要安装OpenOffice软件", "复杂，需要了解OpenOffice的API");
        RowRenderData row4 = RowRenderData.build("Jacob、winlib", "Windows平台", "复杂，不推荐使用");
        List<RowRenderData> tableDatas = Arrays.asList(row0, row1, row2, row3, row4);

        Author author = new Author();
        author.setName("Sayi");
        author.setAlias(new TextRenderData("FF0000", "卅一"));
        author.setAvatar(new PictureRenderData(60, 60, "src/test/resources/sayi.png"));
        model.setAuthor(author);
        Detail detail = new Detail();
        detail.setDiff(new MiniTableRenderData(header, tableDatas, MiniTableRenderData.WIDTH_A4_FULL));
        Desc desc = new Desc();
        desc.setDate("2018-10-01");
        desc.setWebsite(new HyperLinkTextRenderData("http://www.deepoove.com", "http://www.deepoove.com"));
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
        Configure config = Configure.newBuilder().setElMode(ELMode.POI_TL_STICT_MODE).build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config);

        RenderException exception = assertThrows(RenderException.class, () -> template.render(model));
        assertTrue(exception.getCause() instanceof ExpressionEvalException);
    }

    @Test
    public void testSpringELMode() throws Exception {
        // Spring EL 无法容忍变量不存在，直接抛出异常，表达式计算引擎为Spring Expression Language
        Configure config = Configure.newBuilder().setElMode(ELMode.SPEL_MODE).build();
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
