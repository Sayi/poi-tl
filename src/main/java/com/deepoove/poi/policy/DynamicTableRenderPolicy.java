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
package com.deepoove.poi.policy;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * 支持表格内的文本模板动态持有XWPFTable对象
 * @author Sayi 卅一
 * @version 0.0.3
 */
public abstract class DynamicTableRenderPolicy implements RenderPolicy {

	@Override
	public void render(ElementTemplate eleTemplate, Object data,
			XWPFTemplate template) {
		NiceXWPFDocument doc = template.getXWPFDocument();
		RunTemplate runTemplate = (RunTemplate) eleTemplate;
		XWPFRun run = runTemplate.getRun();
		try {
			XmlCursor newCursor = ((XWPFParagraph)run.getParent()).getCTP().newCursor();
			newCursor.toParent();
			//if (newCursor.getObject() instanceof CTTc) 
			newCursor.toParent();
			newCursor.toParent();
			XmlObject object = newCursor.getObject();
			XWPFTable table = doc.getTable((CTTbl) object);
			render(table, data);
		} catch (Exception e) {
			logger.error("dynamic table error:" + e.getMessage(), e);
		}
	}

	public abstract void render(XWPFTable table, Object data);

}
