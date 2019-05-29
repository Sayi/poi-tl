package com.deepoove.poi.tl.issue;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import com.deepoove.poi.NiceXWPFDocument;

public class Issue149 {

    @Test
    public void testMerge() throws Exception {
        NiceXWPFDocument docFirst = new NiceXWPFDocument();
        NiceXWPFDocument doc = docFirst.merge(new NiceXWPFDocument(
                new FileInputStream("src/test/resources/issue/test_stu_tea.docx")));

        FileOutputStream out = new FileOutputStream("out_issue_149.docx");
        doc.write(out);
        doc.close();
        out.close();
        docFirst.close();

    }

    @Test
    public void testMerge2() throws Exception {
        NiceXWPFDocument docFirst = new NiceXWPFDocument();
        FileOutputStream out = new FileOutputStream("out_issue_docfirst.docx");
        docFirst.write(out);
        docFirst.close();
        out.close();
        docFirst.close();

        docFirst = new NiceXWPFDocument(new FileInputStream("out_issue_docfirst.docx"));

        NiceXWPFDocument doc = docFirst.merge(new NiceXWPFDocument(
                new FileInputStream("src/test/resources/issue/test_stu_tea.docx")));

        out = new FileOutputStream("out_issue_149.docx");
        doc.write(out);
        doc.close();
        out.close();
        docFirst.close();

    }

    @Test
    public void testMerge3() throws Exception {
        NiceXWPFDocument docFirst = new NiceXWPFDocument();
        XWPFParagraph createParagraph = docFirst.createParagraph();
        createParagraph.createRun();
        // FileOutputStream out = new
        // FileOutputStream("out_issue_docfirst.docx");
        // docFirst.write(out);
        // docFirst.close();
        // out.close();
        // docFirst.close();
        //
        // docFirst = new NiceXWPFDocument(
        // new FileInputStream("out_issue_docfirst.docx"));
        //
        NiceXWPFDocument doc = docFirst.merge(new NiceXWPFDocument(
                new FileInputStream("src/test/resources/issue/test_stu_tea.docx")));

        FileOutputStream out = new FileOutputStream("out_issue_149.docx");
        doc.write(out);
        doc.close();
        out.close();
        docFirst.close();

    }

    @Test
    public void testMerge4() throws Exception {
        NiceXWPFDocument docFirst = new NiceXWPFDocument();
        NiceXWPFDocument doc = docFirst.merge(new NiceXWPFDocument());

        FileOutputStream out = new FileOutputStream("out_issue_149.docx");
        doc.write(out);
        doc.close();
        out.close();
        docFirst.close();

    }

    @Test
    public void testMerge6() throws Exception {
        NiceXWPFDocument docFirst = new NiceXWPFDocument();
        NiceXWPFDocument niceXWPFDocument = new NiceXWPFDocument();
        FileOutputStream out = new FileOutputStream("out_issue_docfirst.docx");
        niceXWPFDocument.write(out);
        niceXWPFDocument.close();
        out.close();
        niceXWPFDocument.close();
        NiceXWPFDocument doc = docFirst
                .merge(new NiceXWPFDocument(new FileInputStream("out_issue_docfirst.docx")));

        out = new FileOutputStream("out_issue_149.docx");
        doc.write(out);
        doc.close();
        out.close();
        docFirst.close();

    }

    @Test
    public void testMerge5() throws Exception {
        NiceXWPFDocument docFirst = new NiceXWPFDocument();
        NiceXWPFDocument niceXWPFDocument = new NiceXWPFDocument();
        XWPFParagraph createParagraph = niceXWPFDocument.createParagraph();
        createParagraph.createRun();
        NiceXWPFDocument doc = docFirst.merge(niceXWPFDocument);

        FileOutputStream out = new FileOutputStream("out_issue_149.docx");
        doc.write(out);
        doc.close();
        out.close();
        docFirst.close();

    }

    @SuppressWarnings("resource")
    @Test
    public void testTT() {
        // XmlOptions xmlOptions = POIXMLTypeLoader.DEFAULT_XML_OPTIONS;
        // xmlOptions.setSaveImplicitNamespaces((Map)
        // xmlOptions.get("SAVE_SUGGESTED_PREFIXES"));
        // xmlOptions.setLoadAdditionalNamespaces((Map)
        // xmlOptions.get("SAVE_SUGGESTED_PREFIXES"));
        // xmlOptions.setCompileSubstituteNames((Map)
        // xmlOptions.get("SAVE_SUGGESTED_PREFIXES"));
        // xmlOptions.setSaveAggressiveNamespaces();
        // xmlOptions.setSaveInner();
        //
        // CTDocument1 ctDocument = CTDocument1.Factory.newInstance(xmlOptions);
        // ctDocument.addNewBody();
        // System.out.println(ctDocument);

        NiceXWPFDocument doc = new NiceXWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.createRun();
        CTP ctp = paragraph.getCTP();
        System.out.println(ctp.xmlText());

        CTDocument1 document = doc.getDocument();
        CTBody body = document.getBody();
        body.addNewSectPr();
        System.out.println(body);
        System.out.println(body.xmlText());
        System.out.println(document.xmlText());

    }

}
