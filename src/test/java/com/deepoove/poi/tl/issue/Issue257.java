package com.deepoove.poi.tl.issue;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;

public class Issue257 {

    @Test
    public void testDocxMerge() throws Exception {

        // 编号继续前一个编号可以修改为重新开始编号
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("docx", new DocxRenderData(new File("src/test/resources/issue/257_MERGE.docx")));

        XWPFTemplate doc = XWPFTemplate.compile("src/test/resources/issue/257.docx");
        doc.render(params);

        FileOutputStream fos = new FileOutputStream("out_issue_257.docx");
        doc.write(fos);
        fos.flush();
        fos.close();

    }

}
