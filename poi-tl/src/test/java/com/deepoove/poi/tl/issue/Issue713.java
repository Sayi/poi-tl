package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

public class Issue713 {

    @Test
    public void test713() throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/713.docx");
        assertEquals(template.getElementTemplates().get(0).variable(), "{{orderInfo.Self}}");
        assertEquals(template.getElementTemplates().get(1).variable(), "{{orderInfo.Remoter}}");
    }

}
