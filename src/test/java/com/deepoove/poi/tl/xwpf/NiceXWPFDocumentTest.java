package com.deepoove.poi.tl.xwpf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.xwpf.NiceXWPFDocument;

@DisplayName("Merge word test case")
public class NiceXWPFDocumentTest {
    
    @SuppressWarnings("resource")
    @Test
    public void testMerge() throws Exception{
        NiceXWPFDocument source = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/docx_render.docx")));
        NiceXWPFDocument target = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/merge_all.docx")));
        source = source.merge(target);
        
        FileOutputStream out = new FileOutputStream("out_nice_xwpfdocument.docx");
        source.write(out);
        source.close();
        out.close();
    }
    
    @SuppressWarnings("resource")
    @Test
    public void testMergeWithRun() throws Exception{
        NiceXWPFDocument source = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/text_render.docx")));
        NiceXWPFDocument target1 = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/merge_table.docx")));
        NiceXWPFDocument target2 = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/merge_picture.docx")));
        source = source.merge(Arrays.asList(target1, target2), source.getParagraphArray(0).getRuns().get(0));
        
        FileOutputStream out = new FileOutputStream("out_nice_xwpfdocument_run.docx");
        source.write(out);
        source.close();
        out.close();
    }

}
