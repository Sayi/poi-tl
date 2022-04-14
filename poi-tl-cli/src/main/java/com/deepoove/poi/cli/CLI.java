package com.deepoove.poi.cli;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.jsonmodel.support.DefaultGsonHandler;
import com.deepoove.poi.jsonmodel.support.GsonHandler;
import com.deepoove.poi.jsonmodel.support.GsonPreRenderDataCastor;
import com.deepoove.poi.plugin.comment.CommentRenderPolicy;
import com.deepoove.poi.plugin.highlight.HighlightRenderData;
import com.deepoove.poi.plugin.highlight.HighlightRenderPolicy;
import com.deepoove.poi.plugin.markdown.FileMarkdownRenderData;
import com.deepoove.poi.plugin.markdown.MarkdownRenderData;
import com.deepoove.poi.plugin.markdown.MarkdownRenderPolicy;
import com.deepoove.poi.plugin.toc.TOCRenderPolicy;
import com.deepoove.poi.policy.AttachmentRenderPolicy;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

/**
 * java -jar poi-tl-cli.jar --help
 * 
 * @author sayi
 *
 */
public class CLI {

    @Parameter(names = "-t", description = "template file path", required = true, order = 0)
    private String template;

    @Parameter(names = "-o", description = "output file path", required = true, order = 1)
    private String output;

    @Parameter(names = "-d", description = "data model", required = true, order = 3)
    private String datamodel;

    @Parameter(names = "--help", help = true, order = 4)
    private boolean help;

    @Parameter(names = "--version", help = true, order = 5)
    private boolean v;

    private static final Type TYPE = new TypeToken<Map<String, Object>>() {
    }.getType();

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

        // cli logic
        ConfigureBuilder builder = Configure.builder();
        GsonHandler gsonHandler = new DefaultGsonHandler() {
            @Override
            protected RuntimeTypeAdapterFactory<RenderData> createRenderTypeAdapter(boolean readable) {
                return super.createRenderTypeAdapter(readable).registerSubtype(MarkdownRenderData.class, "markdown")
                        .registerSubtype(HighlightRenderData.class, "code")
                        .registerSubtype(FileMarkdownRenderData.class, "markdown-file");
            }

            @Override
            protected List<RuntimeTypeAdapterFactory<?>> createTypeAdapters(boolean readable) {
                List<RuntimeTypeAdapterFactory<?>> typeAdapter = super.createTypeAdapters(readable);
                typeAdapter.add(RuntimeTypeAdapterFactory.of(MarkdownRenderData.class, "type", readable)
                        .registerSubtype(MarkdownRenderData.class, "markdown"));
                typeAdapter.add(RuntimeTypeAdapterFactory.of(HighlightRenderData.class, "type", readable)
                        .registerSubtype(HighlightRenderData.class, "code"));
                typeAdapter.add(RuntimeTypeAdapterFactory.of(MarkdownRenderData.class, "type", readable)
                        .registerSubtype(MarkdownRenderData.class, "markdown")
                        .registerSubtype(FileMarkdownRenderData.class, "markdown-file"));
                return typeAdapter;
            }
        };
        GsonPreRenderDataCastor gsonPreRenderDataCastor = new GsonPreRenderDataCastor();
        gsonPreRenderDataCastor.setGsonHandler(gsonHandler);
        builder.addPreRenderDataCastor(gsonPreRenderDataCastor);
        builder.addPlugin(':', new CommentRenderPolicy())
                .addPlugin(';', new AttachmentRenderPolicy())
                .addPlugin('~', new HighlightRenderPolicy())
                .addPlugin('-', new MarkdownRenderPolicy());
        builder.bind("toc", new TOCRenderPolicy());

        Configure configure = builder.build();
        try {
            String jsonStr = "";
            if (validate(datamodel)) {
                jsonStr = datamodel;
            } else {
                jsonStr = new String(Files.readAllBytes(Paths.get(datamodel)));
            }
            XWPFTemplate.compile(template, configure)
                    .render(gsonHandler.castJsonToType(jsonStr, TYPE))
                    .writeToFile(output);
        } catch (IOException e) {
            e.printStackTrace();
            JCommander.getConsole().println(e.getMessage());
        }
        JCommander.getConsole().println("Output file generated: " + Paths.get(output).toAbsolutePath().toString());
    }

    private static boolean validate(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = JsonParser.parseString(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }

    public String getTemplate() {
        return template;
    }

    public String getOutput() {
        return output;
    }

    public String getDatamodel() {
        return datamodel;
    }

}