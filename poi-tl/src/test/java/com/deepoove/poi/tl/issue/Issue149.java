package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.xwpf.NiceXWPFDocument;

@DisplayName("Issue149 新建文档合并")
public class Issue149 {

    /**
     * {{title}} {{+students}} {{+teachers}}
     */
    String resource = "src/test/resources/issue/149.docx";

    NiceXWPFDocument source;
    NiceXWPFDocument target;
    NiceXWPFDocument result;

    @BeforeEach
    public void init() {

    }

    @AfterEach
    public void detroy() throws IOException {
        if (null != source) source.close();
        if (null != target) target.close();
        if (null != result) result.close();
    }

    @Test
    public void testNewMergeOld() throws Exception {
        source = new NiceXWPFDocument();
        target = new NiceXWPFDocument(new FileInputStream(resource));
        result = source.merge(target);

        XWPFParagraph paragraph = result.getParagraphArray(0);
        assertEquals(paragraph.getText(), "{{title}}");
        paragraph = result.getParagraphArray(1);
        assertEquals(paragraph.getText(), "{{+students}}");
        paragraph = result.getParagraphArray(2);
        assertEquals(paragraph.getText(), "{{+teachers}}");

        source.close();
        target.close();
        result.close();

    }

    @Test
    public void testOldMergeOld() throws Exception {
        source = new NiceXWPFDocument();
        FileOutputStream out = new FileOutputStream("out_149temp.docx");
        source.write(out);
        source.close();
        out.close();
        source.close();

        source = new NiceXWPFDocument(new FileInputStream("out_149temp.docx"));
        target = new NiceXWPFDocument(new FileInputStream(resource));
        result = source.merge(target);

        XWPFParagraph paragraph = result.getParagraphArray(0);
        assertEquals(paragraph.getText(), "{{title}}");
        paragraph = result.getParagraphArray(1);
        assertEquals(paragraph.getText(), "{{+students}}");
        paragraph = result.getParagraphArray(2);
        assertEquals(paragraph.getText(), "{{+teachers}}");

        source.close();
        target.close();
        result.close();

        new File("out_149temp.docx").deleteOnExit();

    }

    @Test
    public void testNewMergeOld2() throws Exception {
        source = new NiceXWPFDocument();
        XWPFParagraph createParagraph = source.createParagraph();
        createParagraph.createRun();

        target = new NiceXWPFDocument(new FileInputStream(resource));
        result = source.merge(target);

        XWPFParagraph paragraph = result.getParagraphArray(0);
        assertEquals(paragraph.getText(), "");
        paragraph = result.getParagraphArray(1);
        assertEquals(paragraph.getText(), "{{title}}");
        paragraph = result.getParagraphArray(2);
        assertEquals(paragraph.getText(), "{{+students}}");
        paragraph = result.getParagraphArray(3);
        assertEquals(paragraph.getText(), "{{+teachers}}");

        source.close();
        target.close();
        result.close();

    }

    @Test
    public void testNewMergeNew() throws Exception {
        source = new NiceXWPFDocument();
        target = new NiceXWPFDocument();
        result = source.merge(target);

        assertEquals(result.getParagraphs().size(), 1);
        assertEquals(result.getParagraphArray(0).getText(), "");

        source.close();
        target.close();
        result.close();

    }

    @Test
    public void testNewMergeNew2() throws Exception {
        target = new NiceXWPFDocument();
        FileOutputStream out = new FileOutputStream("out_149temp1.docx");
        target.write(out);
        target.close();
        out.close();
        target.close();

        target = new NiceXWPFDocument(new FileInputStream("out_149temp1.docx"));

        source = new NiceXWPFDocument();
        result = source.merge(target);

        assertEquals(result.getParagraphs().size(), 1);
        assertEquals(result.getParagraphArray(0).getText(), "");

        source.close();
        target.close();
        result.close();

        new File("out_149temp1.docx").deleteOnExit();

    }

    @Test
    public void testNewMergeNew3() throws Exception {
        source = new NiceXWPFDocument();

        target = new NiceXWPFDocument();
        XWPFParagraph createParagraph = target.createParagraph();
        createParagraph.createRun();

        result = source.merge(target);

        assertEquals(result.getParagraphs().size(), 1);
        assertEquals(result.getParagraphArray(0).getText(), "");

        source.close();
        target.close();
        result.close();

    }

}
