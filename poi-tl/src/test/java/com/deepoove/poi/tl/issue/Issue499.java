package com.deepoove.poi.tl.issue;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Includes;

@DisplayName("Issue 499")
public class Issue499 {

    @Test
    public void test499() throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/499.docx").render(new HashMap<String, Object>() {
            {
                put("experience", Includes.ofLocal("src/test/resources/issue/499_MERGE.docx").create());
            }
        });
        template.writeToFile("target/out_issue_499.docx");
    }

}
