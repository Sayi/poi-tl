package com.deepoove.poi.tl.source;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;

public class TocTest {
    
    private static final String HEADING1 = "Heading1";
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        XWPFDocument doc = new XWPFDocument();

//        XWPFParagraph p = doc.createParagraph();
//        XWPFRun run = p.createRun();
//        run.setText("Heading 1");
//
//        p.getCTP().addNewPPr();
//        p.getCTP().getPPr().addNewPStyle();
//        p.setStyle(HEADING1);
//        
//        p = doc.createParagraph();
//        run = p.createRun();
//        run.setText("Heading 1");
//        
//        p.getCTP().addNewPPr();
//        p.getCTP().getPPr().addNewPStyle();
//        p.setStyle(HEADING1);
//
//        doc.createTOC();
        
        XWPFParagraph p = doc.createParagraph();
        XWPFRun run = p.createRun();
        run.setText("Heading 1");
        p.getCTP().addNewPPr();
        p.getCTP().getPPr().addNewPStyle();
        p.setStyle(HEADING1);
        
        CTP ctP = p.getCTP();
        CTSimpleField toc = ctP.addNewFldSimple();
        toc.setInstr("TOC \\h");
        toc.setDirty(STOnOff.TRUE);
        
        doc.createTOC();
        

        doc.write(new FileOutputStream("CreateWordBookmark.docx"));
        doc.close();
    }

}
