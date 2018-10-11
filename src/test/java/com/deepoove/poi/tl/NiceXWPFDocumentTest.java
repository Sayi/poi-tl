package com.deepoove.poi.tl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import org.junit.Test;

import com.deepoove.poi.NiceXWPFDocument;

public class NiceXWPFDocumentTest {
    
    @SuppressWarnings("resource")
    @Test
    public void testMerge() throws Exception{
        NiceXWPFDocument doc = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/docx_render.docx")));
        NiceXWPFDocument docMerge = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/merge_all.docx")));
        doc = doc.merge(docMerge);
        
        FileOutputStream out = new FileOutputStream("out_nice_xwpfdocument.docx");
        doc.write(out);
        doc.close();
        out.close();
    }
    
    @SuppressWarnings("resource")
    @Test
    public void testMergeWithRun() throws Exception{
        NiceXWPFDocument doc = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/text_render.docx")));
        NiceXWPFDocument docMerge = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/merge_table.docx")));
        NiceXWPFDocument docMerge2 = new NiceXWPFDocument(new FileInputStream(new File("src/test/resources/merge_picture.docx")));
        doc = doc.merge(Arrays.asList(docMerge, docMerge2), doc.getParagraphArray(0).getRuns().get(0));
        
        FileOutputStream out = new FileOutputStream("out_nice_xwpfdocument_run.docx");
        doc.write(out);
        doc.close();
        out.close();
    }

}
