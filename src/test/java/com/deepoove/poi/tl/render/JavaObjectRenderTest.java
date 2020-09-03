package com.deepoove.poi.tl.render;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.tl.source.MyDataModel;
import com.deepoove.poi.tl.source.XWPFTestSupport;

public class JavaObjectRenderTest {

    @Test
    public void testRenderTemplateByBean() throws Exception {
        MyDataModel obj = new MyDataModel();
        obj.setHeader("Deeply love what you love.");
        obj.setName("Poi-tl");
        obj.setWord("Word Template");
        obj.setTime("2020-08-31");
        obj.setWhat(
                "Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
        obj.setAuthor("Sayi");
        obj.setIntroduce(Texts.of("http://www.deepoove.com").link("http://www.deepoove.com").create());
        obj.setPortrait(new PictureRenderData(60, 60, "src/test/resources/sayi.png"));
        obj.setSolutionCompare(Tables
                .of(new String[][] { new String[] { "00", "01", "02" }, new String[] { "10", "11", "12" } }).create());
        obj.setFeature(Numberings.create("Plug-in grammar, add new grammar by yourself",
                "Supports word text, local pictures, web pictures, table, list, header, footer...",
                "Templates, not just templates, but also style templates"));

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/template.docx").render(obj);
        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        assertEquals("[" + obj.getName() + "]", document.getParagraphArray(1).getText());
        assertEquals("[" + obj.getWord() + "]", document.getParagraphArray(2).getText());
        assertEquals("[" + obj.getTime() + "]", document.getParagraphArray(3).getText());
        
        assertEquals("00", document.getTables().get(0).getRow(0).getCell(0).getText());
        assertEquals(1, document.getAllPictures().size());
        assertEquals(1, document.getHyperlinks().length);
        document.close();
    }

}
