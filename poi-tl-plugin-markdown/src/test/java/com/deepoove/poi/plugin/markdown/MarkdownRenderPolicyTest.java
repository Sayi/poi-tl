package com.deepoove.poi.plugin.markdown;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

public class MarkdownRenderPolicyTest {

    @Test
    public void testMarkdown() throws Exception {
        MarkdownRenderData code = new MarkdownRenderData();
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/markdown/mk.md"));
        String mkdn = new String(bytes);
        code.setMarkdown(mkdn);

        MarkdownStyle style = MarkdownStyle.newStyle();
//        style.setImagesDir("/Users/sai/Sayi/blog/");
        style.setShowHeaderNumber(true);
        code.setStyle(style);

        Map<String, Object> data = new HashMap<>();
        data.put("md", code);

        Configure config = Configure.builder().bind("md", new MarkdownRenderPolicy()).build();
        XWPFTemplate.compile("src/test/resources/markdown/markdown.docx", config)
                .render(data)
                .writeToFile("out_render_markdown.docx");
    }

}
