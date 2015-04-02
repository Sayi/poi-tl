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
package com.deepoove.poi.render;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.POIXMLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.TextRunTemplate;

public class RenderAPI {

	private static final Logger logger = LoggerFactory
			.getLogger(RenderAPI.class);

	public static void debug(XWPFTemplate template,
			Map<String, Object> datas) {
		List<ElementTemplate> all = template.getElementTemplates();
		Set<String> tagtKeys = new HashSet<String>();

		if (all == null || all.isEmpty()) {
			if (null == datas || datas.isEmpty()) {
				logger.debug("no template gramer find or no render data find");
				return;
			}
		}

		logger.debug("template tag size is :" + (null == all ? 0 : all.size()));
		for (ElementTemplate ele : all) {
			logger.debug("parse the tag:" + ele.getTagName());
			tagtKeys.add(ele.getTagName());
		}

		Set<String> keySet = datas.keySet();
		HashSet<String> copySet = new HashSet<String>(keySet);

		copySet.removeAll(tagtKeys);
		Iterator<String> iterator = copySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			logger.warn("cannot find the gramer tag in template:" + key);
		}
	}

	public static void selfRender(XWPFTemplate template) {
		if (null == template)
			throw new POIXMLException(
					"template is null,should be setted first.");
		List<ElementTemplate> elementTemplates = template.getElementTemplates();
		if (null == elementTemplates || elementTemplates.isEmpty())
			return;
		for (ElementTemplate runTemplate : elementTemplates) {
			logger.info("tag-name:" + runTemplate.getTagName());
			logger.info(runTemplate.getClass().toString());
			RenderPolicy policy = template.getPolicy(TextRunTemplate.class);
			policy.render(runTemplate,
					new TextRenderData(runTemplate.getSource()), template);

		}
	}

	public static void render(XWPFTemplate template, Map<String, Object> datas) {
		if (null == template)
			throw new POIXMLException(
					"template is null,should be setted first.");
		List<ElementTemplate> elementTemplates = template.getElementTemplates();
		if (null == elementTemplates || elementTemplates.isEmpty()
				|| null == datas || datas.isEmpty())
			return;
		for (ElementTemplate runTemplate : elementTemplates) {
			logger.info("tag-name:" + runTemplate.getTagName());
			logger.info(runTemplate.getClass().toString());
			RenderPolicy policy = template.getPolicy(runTemplate.getTagName());
			if (null == policy)
				policy = template.getPolicy(runTemplate.getClass());
			if (null == policy)
				throw new RenderException("cannot find render policy: "
						+ runTemplate.getTagName());
			// Method method = policy.getMethod("render", ElementTemplate.class,
			// RenderData.class, XWPFTemplate.class);
			// method.invoke(policy.newInstance(),
			// runTemplate,datas.get(runTemplate.getTagName()), template);
			policy.render(runTemplate, datas.get(runTemplate.getTagName()),
					template);

		}
	}

}
