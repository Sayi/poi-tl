package com.deepoove.poi.tl.issue;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;

public class Issue247 {

    @Test
    public void testDocxMerge() throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("docx", new DocxRenderData(new File("src/test/resources/issue/247_MERGE.docx")));

        XWPFTemplate doc = XWPFTemplate.compile("src/test/resources/issue/247.docx");
        doc.render(params);

        FileOutputStream fos = new FileOutputStream("out_issue_247.docx");
        doc.write(fos);
        fos.flush();
        fos.close();

    }

}
