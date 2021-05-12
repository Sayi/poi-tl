package com.deepoove.poi.tl.policy;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.TextRenderData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONRenderPolicyTest {
    @SuppressWarnings("serial")
    @Test
    public void testJSONRender() throws Exception {

        File file = new File("src/test/resources/swagger/petstore.json");
        FileInputStream in = new FileInputStream(file);
        int size = in.available();
        byte[] buffer = new byte[size];
        in.read(buffer);
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new String(buffer));
        List<TextRenderData> codes = new JSONRenderPolicy("000000").convert(jsonNode, 1);
        codes.forEach(System.out::print);

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("json", new String(buffer));
                put("codes", codes);

            }
        };

        JSONRenderPolicy policy = new JSONRenderPolicy("ffffff");
        Configure config = Configure.builder().bind("json", policy).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_json.docx", config)
                .render(datas);

        template.writeToFile("out_render_json.docx");

    }
}
