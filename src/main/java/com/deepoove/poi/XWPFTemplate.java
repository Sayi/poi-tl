/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.Version;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.exception.ResolverException;
import com.deepoove.poi.render.RenderFactory;
import com.deepoove.poi.resolver.TemplateVisitor;
import com.deepoove.poi.resolver.Visitor;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.util.Preconditions;

/**
 * 模板
 * 
 * @author Sayi
 * @version 0.0.1
 */
public class XWPFTemplate {
    private static Logger logger = LoggerFactory.getLogger(XWPFTemplate.class);
    private static final String SUPPORT_MINIMUM_VERSION = "3.16";

    private NiceXWPFDocument doc;
    private Configure config;
    private Visitor visitor;
    private List<ElementTemplate> eleTemplates;

    static {
        Preconditions.checkMinimumVersion(Version.getVersion(), SUPPORT_MINIMUM_VERSION,
                "Require Apach POI version at least " + SUPPORT_MINIMUM_VERSION + ", but now is "
                        + Version.getVersion() + ", please check the dependency of project.");
    }

    private XWPFTemplate() {}

    /**
     * @version 0.0.4
     */
    public static XWPFTemplate compile(String filePath) {
        return compile(new File(filePath));
    }

    public static XWPFTemplate compile(File file) {
        return compile(file, Configure.createDefault());
    }

    /**
     * template file as InputStream
     * 
     * @param inputStream
     * @return
     * @version 1.2.0
     */
    public static XWPFTemplate compile(InputStream inputStream) {
        return compile(inputStream, Configure.createDefault());
    }

    /**
     * @param filePath
     * @param config
     * @return
     * @version 1.0.0
     */
    public static XWPFTemplate compile(String filePath, Configure config) {
        return compile(new File(filePath), config);
    }

    /**
     * @param file
     * @param config
     * @return
     * @version 1.0.0
     */
    public static XWPFTemplate compile(File file, Configure config) {
        try {
            return compile(new FileInputStream(file), config);
        } catch (FileNotFoundException e) {
            throw new ResolverException("Cannot find the file [" + file.getPath() + "]", e);
        }
    }

    /**
     * template file as InputStream
     * 
     * @param inputStream
     * @param config
     * @return
     * @version 1.2.0
     */
    public static XWPFTemplate compile(InputStream inputStream, Configure config) {
        try {
            XWPFTemplate instance = new XWPFTemplate();
            instance.config = config;
            instance.doc = new NiceXWPFDocument(inputStream);
            instance.visitor = new TemplateVisitor(instance.config);
            instance.eleTemplates = instance.visitor.visitDocument(instance.doc);
            return instance;
        } catch (OLE2NotOfficeXmlFileException e) {
            logger.error("Poi-tl currently only supports .docx format");
            throw new ResolverException("Compile template failed", e);
        } catch (IOException e) {
            throw new ResolverException("Compile template failed", e);
        }
    }

    /**
     * 重新解析doc
     * 
     * @param doc
     */
    public void reload(NiceXWPFDocument doc) {
        try {
            this.close();
        } catch (IOException e) {
            logger.error("Close failed", e);
        }
        this.doc = doc;
        this.eleTemplates = this.visitor.visitDocument(doc);
    }

    public XWPFTemplate render(Object model) {
        RenderFactory.getRender(model, config.getElMode()).render(this);
        return this;
    }

    public void write(OutputStream out) throws IOException {
        this.doc.write(out);
    }

    public void writeToFile(String path) throws IOException {
        FileOutputStream out = new FileOutputStream(path);
        this.write(out);
        this.close();
        out.flush();
        out.close();
    }

    public void close() throws IOException {
        this.doc.close();
    }

    public List<ElementTemplate> getElementTemplates() {
        return eleTemplates;
    }

    public NiceXWPFDocument getXWPFDocument() {
        return this.doc;
    }

    public Configure getConfig() {
        return config;
    }

}
