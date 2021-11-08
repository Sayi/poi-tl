package com.deepoove.poi.tl.plugin;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.RemoveTableColumnRenderPolicy;
import com.deepoove.poi.plugin.table.SectionColumnTableRenderPolicy;

public class SectionTableRenderPolicyTest {

    String resource = "src/test/resources/template/ifcol.docx";

    @Test
    public void test() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("r1", 12);
        data.put("r2", 0);
        data.put("r34", 0);
        Configure config = Configure.builder()
                .addPlugin('-', new SectionColumnTableRenderPolicy())
                .bind("ifcol", new RemoveTableColumnRenderPolicy())
                .useSpringEL(false)
                .build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config).render(data);
        template.writeToFile("out_render_ifcol.docx");
    }

}
