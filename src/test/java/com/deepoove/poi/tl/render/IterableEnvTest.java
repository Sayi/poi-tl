package com.deepoove.poi.tl.render;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.tl.source.XWPFTestSupport;

public class IterableEnvTest {

    @SuppressWarnings("serial")
    @Test
    public void testEnv() throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph para = doc.createParagraph();
        XWPFRun createRun = para.createRun();
        createRun.setText("{{?list}}");
        createRun = para.createRun();
        createRun.setText("index:{{_index+1}}");
        createRun = para.createRun();
        createRun.setText("_is_first:{{_is_first}}");
        createRun = para.createRun();
        createRun.setText("_is_last:{{_is_last}}");
        createRun = para.createRun();
        createRun.setText("_has_next:{{_has_next}}");
        createRun = para.createRun();
        createRun.setText("_is_even_item:{{_is_even_item}}");
        createRun = para.createRun();
        createRun.setText("_is_odd_item:{{_is_odd_item}}");
        createRun = para.createRun();
        createRun.setText("{{/list}}");

        XWPFTemplate template = XWPFTemplate.compile(XWPFTestSupport.readInputStream(doc), Configure.builder().useSpringEL().build());
        template.render(new HashMap<String, Object>() {
            {
                put("list", Arrays.asList("1", "2", "3", "4"));
            }
        });
        XWPFDocument newDocument = XWPFTestSupport.readNewDocument(template);
        String text = newDocument.getParagraphArray(0).getText();
        assertEquals(
                "index:1_is_first:true_is_last:false_has_next:true_is_even_item:false_is_odd_item:trueindex:2_is_first:false_is_last:false_has_next:true_is_even_item:true_is_odd_item:falseindex:3_is_first:false_is_last:false_has_next:true_is_even_item:false_is_odd_item:trueindex:4_is_first:false_is_last:true_has_next:false_is_even_item:true_is_odd_item:false",
                text);
    }

}
