package com.deepoove.poi.tl.issue;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Issue1145 {

	@Test
	public void test1145() throws IOException {
        Configure configure = Configure.builder().
            addPlugin('~', new LoopRowTableRenderPolicy())
            .build();
        Map<String, Object> model = new HashMap<>();
        HashMap<Object, Object> user = new HashMap<>();
        user.put("name","张三");
        user.put("sex","男");
        user.put("images",Collections.emptyList());
        model.put("users", Arrays.asList(user));
        model.put("images",Collections.emptyList());
        model.put("isDemo",false);
        model.put("names", Arrays.asList("张三","王五"));
        XWPFTemplate.compile("src/test/resources/issue/1145.docx",configure).render(model).writeToFile("target/out_1145.docx");
	}

}
