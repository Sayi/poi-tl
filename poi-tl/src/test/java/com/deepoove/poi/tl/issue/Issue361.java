package com.deepoove.poi.tl.issue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

/**
 * wp:docPr id should be a unique identifier
 *
 */
public class Issue361 {

    @Test
    public void test361() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("testItems", Arrays.asList("2", "2"));
        Configure config = Configure.builder().buildGramer("${", "}").build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/361.docx", config).render(datas);
        template.writeToFile("out_issue_361.docx");
    }

}
