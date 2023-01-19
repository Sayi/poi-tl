package com.deepoove.poi.data;

import com.deepoove.poi.util.ByteUtils;

public class UrlAttachmentRenderData extends AttachmentRenderData {

	private static final long serialVersionUID = 1L;

	private String url;

	public UrlAttachmentRenderData(String url) {
		this(url, null);
	}

	public UrlAttachmentRenderData(String url, AttachmentType fileType) {
		this.url = url;
		this.setFileType(fileType);
	}

	@Override
	protected String getFileSrc() {
		return url;
	}

	@Override
	public byte[] readAttachmentData() {
		return ByteUtils.getUrlByteArray(url);
	}
}
