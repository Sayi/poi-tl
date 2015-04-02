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
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.SimpleTableRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.resolver.TemplateResolver;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.PictureRunTemplate;
import com.deepoove.poi.template.run.TableRunTemplate;
import com.deepoove.poi.template.run.TextRunTemplate;

/**
 * 模板
 * 
 * @author Sayi
 * @version 0.0.1
 */
public class XWPFTemplate {
	private static Logger logger = LoggerFactory.getLogger(XWPFTemplate.class);
	private NiceXWPFDocument doc;
	private List<ElementTemplate> eleTemplates;
	private Map<String, RenderPolicy> policys;

	private XWPFTemplate() {
		policys = new HashMap<String, RenderPolicy>();
	}

	public static XWPFTemplate create(String filePath) {
		return create(new File(filePath));
	}

	public static XWPFTemplate create(File file) {
		XWPFTemplate xwpfTemplate = new XWPFTemplate();
		try {
			NiceXWPFDocument doc = new NiceXWPFDocument(new FileInputStream(
					file));
			xwpfTemplate.doc = doc;
			xwpfTemplate.parseElementTemplates();
			xwpfTemplate.initPolicy();
		} catch (FileNotFoundException e) {
			logger.error("cannot find the file", e);
		} catch (IOException e) {
			logger.error("create template failed", e);
		}
		return xwpfTemplate;
	}

	private List<ElementTemplate> parseElementTemplates() {
		if (null == eleTemplates) {
			eleTemplates = TemplateResolver.parseElementTemplates(doc);
		}
		return eleTemplates;
	}

	private void initPolicy() {
		registerPolicy(TextRunTemplate.class, new TextRenderPolicy());
		registerPolicy(PictureRunTemplate.class, new PictureRenderPolicy());
		registerPolicy(TableRunTemplate.class, new SimpleTableRenderPolicy());
	}

	public void registerPolicy(Class<?> templateClass, RenderPolicy policy) {
		policys.put(templateClass.getName(), policy);
	}

	public void registerPolicy(String templateName, RenderPolicy policy) {
		policys.put(templateName, policy);
	}

	public RenderPolicy getPolicy(Class<? extends ElementTemplate> clazz) {
		return policys.get(clazz.getName());
	}

	public RenderPolicy getPolicy(String tagName) {
		return policys.get(tagName);
	}

	public void write(OutputStream out) throws IOException {
		doc.write(out);
	}

	public List<ElementTemplate> getElementTemplates() {
		return eleTemplates;
	}

	public NiceXWPFDocument getXWPFDocument() {
		return this.doc;
	}

}
