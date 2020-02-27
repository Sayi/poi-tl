package com.deepoove.poi.tl.xwpf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.xwpf.NiceXWPFDocument;

@DisplayName("Merge word test case")
public class NiceXWPFDocumentTest {

    @SuppressWarnings("resource")
    @Test
    public void testMergeAtEnd() throws Exception {
        NiceXWPFDocument source = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/render_include.docx")));
        int sourceSize = source.getBodyElements().size();
        NiceXWPFDocument target = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/render_include_all.docx")));
        int targetSize = target.getBodyElements().size();
        source = source.merge(target);

        int resultSize = source.getBodyElements().size();
        // 会创建一个空的段落，然后移除
        assertEquals(sourceSize + targetSize, resultSize);

        source.close();
    }

    @SuppressWarnings("resource")
    @Test
    public void testMergeAtRun() throws Exception {
        NiceXWPFDocument source = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/render_text.docx")));
        int sourceSize = source.getBodyElements().size();
        NiceXWPFDocument target1 = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/render_include_table.docx")));
        int targetSize1 = target1.getBodyElements().size();

        NiceXWPFDocument target2 = new NiceXWPFDocument(
                new FileInputStream(new File("src/test/resources/render_include_picture.docx")));
        int targetSize2 = target2.getBodyElements().size();
        source = source.merge(Arrays.asList(target1, target2), source.getParagraphArray(0).getRuns().get(0));

        int resultSize = source.getBodyElements().size();

        // 会移除source.getParagraphArray(0).getRuns().get(0)这个所在段落
        assertEquals(sourceSize - 1 + targetSize1 + targetSize2, resultSize);

        source.close();

    }

}
