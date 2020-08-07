package com.deepoove.poi.tl.xwpf;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

/**
 * @author Sayi
 */
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

        XWPFTemplate.compile("src/test/resources/template/template_notes.docx").render(datas)
                .writeToFile("out_template_notes.docx");

    }

}
