package com.deepoove.poi.tl.issue;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.Configure.DiscardHandler;
import com.deepoove.poi.data.Includes;

public class Issue540 {

    @SuppressWarnings("serial")
    @Test
    public void testIssue540() throws IOException {

        Configure config = Configure.builder().setValidErrorHandler(new DiscardHandler()).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/540.docx", config)
                .render(new HashMap<String, Object>() {
                    {
//                        put("var1",
//                                Includes.ofLocal("src/test/resources/issue/540_MERGE.docx")
//                                        .setRenderModel(Collections.singletonMap("text", "1"))
//                                        .create());
                        put("var2",
                                Includes.ofLocal("src/test/resources/issue/540_MERGE.docx")
                                        .setRenderModel(Collections.singletonMap("text", "2"))
                                        .create());
                        put("var3",
                                Includes.ofLocal("src/test/resources/issue/540_MERGE.docx")
                                        .setRenderModel(Collections.singletonMap("text", "3"))
                                        .create());
                        put("var4",
                                Includes.ofLocal("src/test/resources/issue/540_MERGE.docx")
                                        .setRenderModel(Collections.singletonMap("text", "4"))
                                        .create());
                    }
                });
        template.writeToFile("out_issue_540.docx");
    }

}
