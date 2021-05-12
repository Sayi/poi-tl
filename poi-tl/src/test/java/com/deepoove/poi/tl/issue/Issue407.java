package com.deepoove.poi.tl.issue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

public class Issue407 {

    @Test
    public void test407() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("董事長", "Li");
        datas.put("經理人", "Wang");
        datas.put("會計主管", "Fan");
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/407.docx").render(datas);
        template.writeToFile("out_issue_407.docx");
    }

}
