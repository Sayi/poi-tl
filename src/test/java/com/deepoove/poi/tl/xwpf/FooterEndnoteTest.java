package com.deepoove.poi.tl.xwpf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFEndnote;
import org.apache.poi.xwpf.usermodel.XWPFFootnote;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.tl.source.XWPFTestSupport;

public class FooterEndnoteTest {

    @SuppressWarnings("serial")
    @Test
    public void testRender() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("endnote", "this is endnote");
                put("footnote", "this is footernote");
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/template_notes.docx").render(datas);

        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        // separator continue para
        XWPFFootnote xwpfFootnote = document.getFootnotes().get(2);
        XWPFEndnote xwpfEndnote = document.getEndnotes().get(2);
        assertEquals(" Chinathis is endnote", xwpfEndnote.getParagraphArray(0).getText());
        assertEquals("世界地图this is footernote", xwpfFootnote.getParagraphArray(0).getText());

        document.close();

    }

}
