package com.deepoove.poi.tl.issue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Charts;

public class Issue657 {

    @SuppressWarnings("serial")
    @Test
    public void test657() throws Exception {

        XWPFTemplate.compile("src/test/resources/issue/657.docx").render(new HashMap<String, Object>() {
            {
                put("chart",
                        Charts.ofRadar("", new String[] { "","","","","" })
                                .addSeries("SPP", new Number[] { 42, 36, 50, 52, 41 })
                                .addSeries("SPP2", new Number[] { 32, 36, 50, 52, 41 })
                                .create());
            }
        }).writeToFile("out_issue_657.docx");

    }
    
    @Test
    public void test6575() throws Exception {
        
        XWPFTemplate.compile("out_issue_657.docx");
        
    }

}
