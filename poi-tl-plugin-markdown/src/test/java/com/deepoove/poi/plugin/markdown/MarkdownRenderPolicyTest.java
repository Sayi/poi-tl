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
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/markdown/basic.md"));
        String mkdn = new String(bytes);
        code.setMarkdown(mkdn);

        MarkdownStyle style = MarkdownStyle.newStyle();
//        style.setImagesDir("/Users/sai/Sayi/blog/");
        style.setShowHeaderNumber(true);
        code.setStyle(style);

        Map<String, Object> data = new HashMap<>();
         data.put("md", code);
        
//        FileMarkdownRenderData fileMarkdownRenderData = new FileMarkdownRenderData();
//        fileMarkdownRenderData.setPath("src/test/resources/markdown/basic.md");
//        fileMarkdownRenderData.setStyle(style);
//        data.put("md", fileMarkdownRenderData);
        
        Configure config = Configure.builder().bind("md", new MarkdownRenderPolicy()).build();
        XWPFTemplate.compile("src/test/resources/markdown/markdown_template.docx", config)
                .render(data)
                .writeToFile("target/out_markdown.docx");
    }

}
