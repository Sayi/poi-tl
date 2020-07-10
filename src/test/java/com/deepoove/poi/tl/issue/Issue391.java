package com.deepoove.poi.tl.issue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

public class Issue391 {

    String resource = "src/test/resources/issue/391.docx";
    String resource2 = "src/test/resources/issue/391_MERGE.docx";

    NiceXWPFDocument source;
    NiceXWPFDocument target;
    NiceXWPFDocument result;

    @BeforeEach
    public void init() {

    }

    @AfterEach
    public void detroy() throws IOException {
        if (null != source) source.close();
        if (null != target) target.close();
        if (null != result) result.close();
    }

    @Test
    public void testMergeNamespace() throws Exception {
        source = new NiceXWPFDocument(new FileInputStream(resource));

        target = new NiceXWPFDocument(new FileInputStream(resource2));

        result = source.merge(target);

        result.write(new FileOutputStream("out_issue_391.docx"));

        source.close();
        target.close();
        result.close();

    }

    @Test
    public void testMerge() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("content", new DocxRenderData(new File("src/test/resources/issue/391_1MERGE.docx")));
        datas.put("projectName", "project for test");

        XWPFTemplate.compile("src/test/resources/issue/391_1.docx").render(datas);

    }
}
