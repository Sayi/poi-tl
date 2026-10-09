package com.deepoove.poi.cli;

import org.junit.jupiter.api.Test;

public class CLITest {

    @Test
    public void test() {
        String cmd = "-t src/test/resources/template.docx -o target/out.docx -d src/test/resources/data.json";
        CLI.main(cmd.split(" "));
    }
    
    @Test
    public void testJsonStr() {
        String cmd = "-t src/test/resources/template.docx -o target/out.docx -d {\"name\":\"Poi-tl\"}";
        CLI.main(cmd.split(" "));
    }

    @Test
    public void testMarkdown() {
        String cmd = "-t src/test/resources/markdown_template.docx -o target/out_md.docx -d src/test/resources/md.json";
        CLI.main(cmd.split(" "));
    }
    
    @Test
    public void testDocsify() {
        String cmd = "-t src/test/resources/markdown_template.docx -o target/out_docsify.docx -d src/test/resources/docsify.json";
        CLI.main(cmd.split(" "));
    }

}
