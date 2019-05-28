package com.deepoove.poi.tl.issue;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;

public class Issue160 {

    @SuppressWarnings("unchecked")
    @Test
    public void testPict() throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("title", "文档合并");
        dataMap.put("author", "poi-tl");
        dataMap.put("date", "2019-05-28");

        Map<String, Object> mergeMap = new HashMap<String, Object>();
        mergeMap.put("title", "文档合并");
        dataMap.put("docx", new DocxRenderData(new File("src/test/resources/issue/160_merge.docx"),
                Arrays.asList(mergeMap)));

        XWPFTemplate.compile("src/test/resources/issue/160.docx").render(dataMap)
                .writeToFile("out_issue_160.docx");
    }

}
