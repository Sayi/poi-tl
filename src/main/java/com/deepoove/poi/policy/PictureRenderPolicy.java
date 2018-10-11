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

import java.io.FileInputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.BytePictureUtils;

public class PictureRenderPolicy implements RenderPolicy {

	protected final Logger logger = LoggerFactory.getLogger(PictureRenderPolicy.class);

	@Override
	public void render(ElementTemplate eleTemplate, Object renderData, XWPFTemplate doc) {
		RunTemplate runTemplate = (RunTemplate) eleTemplate;
		XWPFRun run = runTemplate.getRun();
		if (renderData == null) { run.setText("", 0);return; }
		PictureRenderData pictureRenderData = null;
		if (renderData instanceof PictureRenderData) {
			pictureRenderData = (PictureRenderData) renderData;
		} else {
		    run.setText("", 0);
			logger.warn("Error render data,should be pictureRenderData:" + renderData.getClass());
			return;
		}
		try {
			byte[] data = pictureRenderData.getData();
			if (null == data) {
				FileInputStream is = new FileInputStream(pictureRenderData.getPath());
				data = BytePictureUtils.toByteArray(is);
			} 
			String blipId = doc.getXWPFDocument().addPictureData(data, suggestFileType(pictureRenderData.getPath()));
			doc.getXWPFDocument().addPicture(blipId,
					doc.getXWPFDocument()
							.getNextPicNameNumber(suggestFileType(pictureRenderData.getPath())),
					pictureRenderData.getWidth(), pictureRenderData.getHeight(), run);
			run.setText("", 0);
		} catch (Exception e) {
			logger.error("render picture docx error", e);
		} 
	}

	private int suggestFileType(String imgFile) {
		int format = 0;

		if (imgFile.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
		else if (imgFile.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
		else if (imgFile.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
		else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg"))
			format = XWPFDocument.PICTURE_TYPE_JPEG;
		else if (imgFile.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
		else if (imgFile.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
		else if (imgFile.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
		else if (imgFile.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
		else if (imgFile.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
		else if (imgFile.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
		else if (imgFile.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
		else {
			logger.error("Unsupported picture: " + imgFile
					+ ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");
		}
		return format;
	}

}
