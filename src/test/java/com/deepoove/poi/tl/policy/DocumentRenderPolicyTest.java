package com.deepoove.poi.tl.policy;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.NumberingFormat;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.policy.DocumentRenderPolicy;
import com.deepoove.poi.xwpf.NumFormat;

@DisplayName("Document Render test case")
public class DocumentRenderPolicyTest {

    @Test
    public void testDocumentRender() throws IOException {
        Configure config = Configure.builder().bind("document", new DocumentRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_document.docx", config);

        // create paragraph
        ParagraphRenderData paragraph = Paragraphs.of()
                .addText("I consider myself the luckiest😁 man on the face of the")
                .addPicture(Pictures.ofLocal("src/test/resources/sayi.png").size(40, 40).create())
                .addText(Texts.of(" poi-tl").link("http://deepoove.com/poi-tl").create()).create();

        // create numbering
        NumberingRenderData numbering = Numberings.of("Plug-in grammar, add new grammar by yourself",
                "Supports word text, local pictures, web pictures, table, list, header, footer...",
                "Templates, not just templates, but also style templates").create();
        // create table
        RowRenderData row = Rows.of("row", "row", "row").create();
        TableRenderData table = Tables.of(row, row, row).width(10.0f, null).create();

        BigInteger numId = template.getXWPFDocument().addNewMultiLevelNumberingId(NumberingFormat.DECIMAL,
                new NumberingFormat(NumFormat.UPPER_LETTER, "%2)."));
        // 1.
        ParagraphRenderData paraOfNumbering0 = Paragraphs.of().addText("I consider myself the luckiest man")
                .paraStyle(ParagraphStyle.builder().withNumId(numId.longValue()).withLvl(0).build()).create();
        // 1. A.
        ParagraphRenderData paraOfNumbering00 = Paragraphs.of().addText("I consider myself the luckiest man")
                .paraStyle(ParagraphStyle.builder().withNumId(numId.longValue()).withLvl(1).build()).create();
        // 1. B.
        ParagraphRenderData paraOfNumbering01 = Paragraphs.of().addText("I consider myself the luckiest man")
                .paraStyle(ParagraphStyle.builder().withNumId(numId.longValue()).withLvl(1).build()).create();
        // 2.
        ParagraphRenderData paraOfNumbering1 = Paragraphs.of().addText("I consider myself the luckiest man")
                .paraStyle(ParagraphStyle.builder().withNumId(numId.longValue()).withLvl(0).build()).create();

        DocumentRenderData document = Documents.of().addParagraph(paragraph).addNumbering(numbering).addTable(table)
                .addParagraph(paraOfNumbering0).addParagraph(paraOfNumbering00).addParagraph(paraOfNumbering01)
                .addParagraph(paraOfNumbering1).create();

        Map<String, Object> data = new HashMap<>();
        data.put("document", document);
        template.render(data).writeToFile("out_render_document.docx");
    }

}
