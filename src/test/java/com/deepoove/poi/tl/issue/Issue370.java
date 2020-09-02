package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.xwpf.NiceXWPFDocument;

@DisplayName("Issue370 文档合并命名空间")
public class Issue370 {

    String resource = "src/test/resources/issue/370.docx";

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
        int size = source.getBodyElements().size();

        target = new NiceXWPFDocument(new FileInputStream("src/test/resources/template/render_include_all.docx"));

        result = source.merge(target);
        
        // result.write(new FileOutputStream("out_merge_test.docx"));

        assertEquals(result.getBodyElements().size(), size + target.getBodyElements().size());

        source.close();
        target.close();
        result.close();

    }
}
