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
package com.deepoove.poi.data;

/**
 * attachment file:docx or xlsx
 *
 * @author Sayi
 */
public abstract class AttachmentRenderData implements RenderData {

	private static final long serialVersionUID = 1L;

	private AttachmentType fileType;
	private PictureRenderData icon;

	public abstract byte[] readAttachmentData();

	public AttachmentType getFileType() {
		if (null != fileType) {
			return fileType;
		}
		setFileType(detectFileType());
		return fileType;
	}

	public void setFileType(AttachmentType fileType) {
		this.fileType = fileType;
	}

	public PictureRenderData getIcon() {
		return icon;
	}

	public void setIcon(PictureRenderData icon) {
		this.icon = icon;
	}

	protected String getFileSrc() {
		return null;
	}

	protected AttachmentType detectFileType() {
		if (null == getFileSrc()) {
			return null;
		}
		AttachmentType type = AttachmentType.suggestFileType(getFileSrc());
		if (null == type) {
			type = AttachmentType.suggestFileType(readAttachmentData());
		}
		return type;
	}

}
