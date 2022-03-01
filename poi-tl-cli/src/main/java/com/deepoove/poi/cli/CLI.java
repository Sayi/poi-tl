package com.deepoove.poi.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * java -jar poi-tl-cli-1.0.0-all.jar --help
 * 
 * @author sayi
 *
 */
public class CLI {

    @Parameter(names = "-t", description = "template file path", required = true, order = 0)
    private String templateFile;

    @Parameter(names = "-o", description = "output file path", required = true, order = 1)
    private String outputFile;

    @Parameter(names = "-d", description = "data model", required = true, order = 3)
    private String dataModel;

    @Parameter(names = "--help", help = true, order = 4)
    private boolean help;

    @Parameter(names = "--version", help = true, order = 5)
    private boolean v;

    public static void main(String[] args) {
        CLI cli = new CLI();
        JCommander jCommander = JCommander.newBuilder().addObject(cli).build();
        jCommander.parse(args);
        cli.run(jCommander);
    }

    public void run(JCommander jCommander) {
        if (help) {
            jCommander.setProgramName("java -jar poi-tl-cli.jar");
            jCommander.usage();
            return;
        }
        if (v) {
            JCommander.getConsole().println("1.0.0");
            return;
        }

        // TODO implement cli logic

        JCommander.getConsole().println("");
    }

    public String getTemplateFile() {
        return templateFile;
    }

    public String getOutputFIle() {
        return outputFile;
    }

    public String getDataModel() {
        return dataModel;
    }

}