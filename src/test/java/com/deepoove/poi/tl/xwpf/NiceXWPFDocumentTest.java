package com.deepoove.poi.tl.xwpf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.xwpf.NiceXWPFDocument;

@DisplayName("Merge word test case")
public class NiceXWPFDocumentTest {

    @Test
    public void testMergeAtEnd() throws Exception {
        NiceXWPFDocument source = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/template/render_include.docx")));
        int sourceSize = source.getBodyElements().size();
        NiceXWPFDocument target = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/template/render_include_all.docx")));
        int targetSize = target.getBodyElements().size();
        source = source.merge(target);

        int resultSize = source.getBodyElements().size();
        // 会创建一个空的段落，然后移除
        assertEquals(sourceSize + targetSize, resultSize);

        source.close();
    }

    @Test
    public void testMergeAtRun() throws Exception {
        NiceXWPFDocument source = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/template/render_text.docx")));
        int sourceSize = source.getBodyElements().size();
        NiceXWPFDocument target1 = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/template/render_include_table.docx")));
        int targetSize1 = target1.getBodyElements().size();

        NiceXWPFDocument target2 = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/template/render_include_picture.docx")));
        int targetSize2 = target2.getBodyElements().size();
        source = source.merge(Arrays.asList(target1, target2), source.getParagraphArray(0).getRuns().get(0));

        int resultSize = source.getBodyElements().size();

        // 会移除source.getParagraphArray(0).getRuns().get(0)这个所在段落
        assertEquals(sourceSize - 1 + targetSize1 + targetSize2, resultSize);

        source.close();

    }

    @SuppressWarnings("resource")
    @Test
    public void testMergeWithChart() throws Exception {
        NiceXWPFDocument source = new NiceXWPFDocument();
        NiceXWPFDocument target1 = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/template/reference_chart.docx")));

        source = source.merge(target1);
        source.write(new FileOutputStream("out_merge_chart.docx"));
        source.close();

    }

    @Test
    public void testNewMergeNewNamespace() throws Exception {
        NiceXWPFDocument source;
        NiceXWPFDocument target;
        NiceXWPFDocument result;

        XWPFDocument doc = new XWPFDocument();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        doc.write(byteArrayOutputStream);
        doc.close();

        source = new NiceXWPFDocument(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        assertEquals(-1, source.getXWPFDocument().getDocument().toString().indexOf(":wps="));

        target = new NiceXWPFDocument();
        assertNotEquals(-1, target.getXWPFDocument().getDocument().toString().indexOf(":wps="));
        XWPFParagraph createParagraph = target.createParagraph();
        createParagraph.createRun();

        result = source.merge(target);

        assertEquals(result.getParagraphs().size(), 1);
        assertEquals(result.getParagraphArray(0).getText(), "");
        assertNotEquals(-1, target.getXWPFDocument().getDocument().toString().indexOf(":wps="));

        source.close();
        target.close();
        result.close();

    }

}
