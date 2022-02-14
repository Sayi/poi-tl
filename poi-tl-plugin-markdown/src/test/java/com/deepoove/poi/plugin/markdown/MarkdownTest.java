package com.deepoove.poi.plugin.markdown;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

public class MarkdownTest {

    public static void testMarkdown(String name) throws Exception {
        MarkdownRenderData code = new MarkdownRenderData();
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/markdown/" + name + ".md"));
        String mkdn = new String(bytes);
        code.setMarkdown(mkdn);

        MarkdownStyle style = MarkdownStyle.newStyle();
        style.setShowHeaderNumber(true);
        code.setStyle(style);

        Map<String, Object> data = new HashMap<>();
        data.put("md", code);

        Configure config = Configure.builder().bind("md", new MarkdownRenderPolicy()).build();
        XWPFTemplate.compile("src/test/resources/markdown/markdown_template.docx", config)
                .render(data)
                .writeToFile("target/out_markdown_" + name + ".docx");
    }

    public static void main(String[] args) throws Exception {
        testMarkdown("api");
        testMarkdown("func");
        testMarkdown("README");
    }

}
