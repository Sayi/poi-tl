/*
 * Copyright 2014-2021 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import org.apache.poi.Version;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.exception.ResolverException;
import com.deepoove.poi.policy.DocumentRenderPolicy;
import com.deepoove.poi.render.DefaultRender;
import com.deepoove.poi.render.Render;
import com.deepoove.poi.resolver.Resolver;
import com.deepoove.poi.resolver.TemplateResolver;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.util.Preconditions;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * The facade of word(docx) template
 * <p>
 * It works by expanding tags in a template using values provided in a Map or Object.
 * </p>
 * 
 * @author Sayi
 */
public class XWPFTemplate implements Closeable {

    public static final String TEMPLATE_TAG_NAME = "var";

    private static Logger logger = LoggerFactory.getLogger(XWPFTemplate.class);
    private static final String SUPPORT_MINIMUM_VERSION = "4.1.2";

    private NiceXWPFDocument doc;
    private Configure config;
    private Resolver resolver;
    private Render renderer;
    private List<MetaTemplate> eleTemplates;

    static {
        try {
            Class.forName("org.apache.poi.Version");
            Preconditions.checkMinimumVersion(Version.getVersion(), SUPPORT_MINIMUM_VERSION,
                    (cur, min) -> "Require Apache POI version at least " + min + ", but now is " + cur
                            + ", please check the dependency of project.");
        } catch (ClassNotFoundException e) {
            // no-op
        }
    }

    private XWPFTemplate() {
    }

    /**
     * Compile template from absolute file path
     * 
     * @param absolutePath template path
     * @return
     */
    public static XWPFTemplate compile(String absolutePath) {
        return compile(new File(absolutePath));
    }

    /**
     * Compile template from file
     * 
     * @param templateFile template file
     * @return
     */
    public static XWPFTemplate compile(File templateFile) {
        return compile(templateFile, Configure.createDefault());
    }

    /**
     * Compile template from template input stream
     * 
     * @param inputStream template input
     * @return
     */
    public static XWPFTemplate compile(InputStream inputStream) {
        return compile(inputStream, Configure.createDefault());
    }

    /**
     * Compile template from document
     * 
     * @param document template document
     * @return
     */
    public static XWPFTemplate compile(XWPFDocument document) {
        return compile(document, Configure.createDefault());
    }

    /**
     * Compile template from absolute file path with configure
     * 
     * @param absolutePath absolute template file path
     * @param config
     * @return
     */
    public static XWPFTemplate compile(String absolutePath, Configure config) {
        return compile(new File(absolutePath), config);
    }

    /**
     * Compile template from file with configure
     * 
     * @param templateFile template file
     * @param config
     * @return
     */
    public static XWPFTemplate compile(File templateFile, Configure config) {
        try {
            return compile(new FileInputStream(templateFile), config);
        } catch (FileNotFoundException e) {
            throw new ResolverException("Cannot find the file [" + templateFile.getPath() + "]", e);
        }
    }

    /**
     * Compile template from document with configure
     * 
     * @param document template document
     * @param config
     * @return
     */
    public static XWPFTemplate compile(XWPFDocument document, Configure config) {
        try {
            return compile(PoitlIOUtils.docToInputStream(document), config);
        } catch (IOException e) {
            throw new ResolverException("Cannot compile document", e);
        }
    }

    /**
     * Compile template from template input stream with configure
     * 
     * @param inputStream template input
     * @param config
     * @return
     */
    public static XWPFTemplate compile(InputStream inputStream, Configure config) {
        try {
            XWPFTemplate template = new XWPFTemplate();
            template.config = config;
            template.doc = new NiceXWPFDocument(inputStream);
            template.resolver = new TemplateResolver(template.config);
            template.renderer = new DefaultRender();
            template.eleTemplates = template.resolver.resolveDocument(template.doc);
            return template;
        } catch (OLE2NotOfficeXmlFileException e) {
            logger.error("Poi-tl currently only supports .docx format");
            throw new ResolverException("Compile template failed", e);
        } catch (IOException e) {
            throw new ResolverException("Compile template failed", e);
        }
    }

    /**
     * Create new document
     * 
     * @return template
     * @since 1.10.0
     */
    public static XWPFTemplate create(DocumentRenderData data) {
        return create(data, null);
    }

    /**
     * Create new document with styled tag
     * 
     * @return template
     * @since 1.10.0
     */
    public static XWPFTemplate create(DocumentRenderData data, Style templateTagStyle) {
        Configure configure = Configure.builder().bind(TEMPLATE_TAG_NAME, new DocumentRenderPolicy()).build();
        XWPFDocument document = new NiceXWPFDocument();
        XWPFRun run = document.createParagraph().createRun();
        run.setText(configure.getGramerPrefix() + TEMPLATE_TAG_NAME + configure.getGramerSuffix());
        StyleUtils.styleRun(run, templateTagStyle);
        return compile(document, configure).render(Collections.singletonMap(TEMPLATE_TAG_NAME, data));
    }

    /**
     * Render the template by data model
     * 
     * @param model render data
     * @return
     */
    public XWPFTemplate render(Object model) {
        this.renderer.render(this, model);
        return this;
    }

    /**
     * Render the template by data model and write to OutputStream, do'not forget invoke {@link XWPFTemplate#close()},
     * {@link OutputStream#close()}
     * 
     * @param model render data
     * @param out   output
     * @return
     * @throws IOException
     */
    public XWPFTemplate render(Object model, OutputStream out) throws IOException {
        this.render(model);
        this.write(out);
        return this;
    }

    /**
     * write to output stream, do'not forget invoke {@link XWPFTemplate#close()}, {@link OutputStream#close()} finally
     * 
     * @param out eg.ServletOutputStream
     * @throws IOException
     */
    public void write(OutputStream out) throws IOException {
        this.doc.write(out);
    }

    /**
     * write to and close output stream
     * 
     * @param out eg.ServletOutputStream
     * @throws IOException
     */
    public void writeAndClose(OutputStream out) throws IOException {
        try {
            this.write(out);
            out.flush();
        } finally {
            PoitlIOUtils.closeQuietlyMulti(this.doc, out);
        }
    }

    /**
     * write to file, this method will close all the stream
     * 
     * @param path output path
     * @throws IOException
     */
    public void writeToFile(String path) throws IOException {
        this.writeAndClose(new FileOutputStream(path));
    }

    /**
     * reload the template
     * 
     * @param doc load new template document
     */
    public void reload(NiceXWPFDocument doc) {
        PoitlIOUtils.closeLoggerQuietly(this.doc);
        this.doc = doc;
        this.eleTemplates = this.resolver.resolveDocument(doc);
    }

    /**
     * close the document
     * 
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        this.doc.close();
    }

    /**
     * Get all tags in the document
     * 
     * @return
     */
    public List<MetaTemplate> getElementTemplates() {
        return eleTemplates;
    }

    /**
     * Get document
     * 
     * @return
     */
    public NiceXWPFDocument getXWPFDocument() {
        return this.doc;
    }

    /**
     * Get configuration
     * 
     * @return
     */
    public Configure getConfig() {
        return config;
    }

    /**
     * Get Resolver
     * 
     * @return
     */
    public Resolver getResolver() {
        return resolver;
    }

}
