package com.deepoove.poi.tl.plugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.xwpf.XWPFOnOff;

public class TocTest {

    private static final String HEADING1 = "1";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        XWPFDocument doc = new XWPFDocument(new FileInputStream("toc.docx"));

        XWPFStyles styles = doc.getStyles();
        System.out.println(styles);

        XWPFParagraph p = doc.createParagraph();
        XWPFRun run = p.createRun();
        run.setText("Heading 1");
        p.getCTP().addNewPPr();
        p.getCTP().getPPr().addNewPStyle();
        p.setStyle(HEADING1);

        XWPFParagraph tocPara = doc.createParagraph();
        CTP ctP = tocPara.getCTP();
        CTSimpleField toc = ctP.addNewFldSimple();
        toc.setInstr("TOC \\h");
        toc.setDirty(XWPFOnOff.ON);

        System.out.println(doc.isEnforcedUpdateFields());

        // doc.enforceUpdateFields();

        // doc.createTOC();

        doc.write(new FileOutputStream("CreateWordBookmark.docx"));
        doc.close();
    }

    public void testTOC() throws IOException {
        Configure config = Configure.builder().bind("toc", new RenderPolicy() {

            @SuppressWarnings("deprecation")
            @Override
            public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
                XWPFRun run = ((RunTemplate) eleTemplate).getRun();
                run.setText("", 0);
                XWPFParagraph tocPara = run.getParagraph();
                CTP ctP = tocPara.getCTP();

                CTSimpleField toc = ctP.addNewFldSimple();
                toc.setInstr("TOC \\o");
                toc.setDirty(XWPFOnOff.ON);

                // XWPFRun createRun = tocPara.createRun();
                // createRun.addBreak(BreakType.PAGE);
            }
        }).build();
        XWPFTemplate template = XWPFTemplate.compile("out_example_swagger.docx", config).render(new HashMap<>());
        template.writeToFile("out_example_swagger_toc.docx");
    }

}
