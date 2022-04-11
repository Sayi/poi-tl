package com.deepoove.poi.tl.issue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Includes;

public class Issue760 {

    @Test
    public void test760() throws IOException {
        Map<String, Object> mainData = new HashMap<>();
        mainData.put("include", Includes.ofLocal("src/test/resources/issue/760_MERGE.docx").create());
        XWPFTemplate render = XWPFTemplate.compile("src/test/resources/issue/760.docx").render(mainData);
        render.writeToFile("target/out_issue_760.docx");
    }

}
