package com.deepoove.poi.tl.el;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;

/**
 * @author Sayi
 */
public class ELTemplateTest {

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

    @Test
    public void testDotRender() throws Exception {
        DataModel model = new DataModel();
        Author author = new Author();
        author.setName("Sayi");
        author.setAlias(new TextRenderData("FF0000", "卅一"));
        author.setAvatar(new PictureRenderData(60, 60, "src/test/resources/sayi.png"));
        model.setAuthor(author);
        Detail detail = new Detail();
        detail.setDiff(
                new MiniTableRenderData(header, tableDatas, MiniTableRenderData.WIDTH_A4_FULL));
        Desc desc = new Desc();
        desc.setDate("2018-10-01");
        desc.setWebsite(
                new HyperLinkTextRenderData("http://www.deepoove.com", "http://www.deepoove.com"));
        detail.setDesc(desc);
        model.setDetail(detail);

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/dot.docx").render(model);

        FileOutputStream out = new FileOutputStream("out_dot.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

}
