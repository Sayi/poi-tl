package com.deepoove.poi.cli;

import org.junit.jupiter.api.Test;

public class CLITest {

    @Test
    public void test() {
        String cmd = "-t src/test/resources/template.docx -o target/out.docx -d src/test/resources/data.json";
        CLI.main(cmd.split(" "));
    }

    @Test
    public void testMD() {
        String cmd = "-t src/test/resources/markdown_template.docx -o target/out_md.docx -d src/test/resources/md.json";
        CLI.main(cmd.split(" "));
    }

}
