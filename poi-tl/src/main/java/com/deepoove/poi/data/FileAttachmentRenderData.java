package com.deepoove.poi.data;

import java.io.File;

import com.deepoove.poi.util.ByteUtils;

public class FileAttachmentRenderData extends AttachmentRenderData {

	private static final long serialVersionUID = 1L;

	private String path;

	public FileAttachmentRenderData(String path) {
		this(path, null);
	}

	public FileAttachmentRenderData(String path, AttachmentType fileType) {
		this.path = path;
		this.setFileType(fileType);
	}

	@Override
	protected String getFileSrc() {
		return path;
	}

	@Override
	public byte[] readAttachmentData() {
		return ByteUtils.getLocalByteArray(new File(path));
	}
}