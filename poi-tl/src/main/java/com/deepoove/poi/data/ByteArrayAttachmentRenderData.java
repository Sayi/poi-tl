package com.deepoove.poi.data;

public class ByteArrayAttachmentRenderData extends AttachmentRenderData {

	private static final long serialVersionUID = 1L;

	private byte[] bytes;

	public ByteArrayAttachmentRenderData(byte[] bytes) {
		this(bytes, null);
	}

	public ByteArrayAttachmentRenderData(byte[] bytes, AttachmentType fileType) {
		this.bytes = bytes;
		this.setFileType(fileType);
	}

	@Override
	public AttachmentType getFileType() {
		AttachmentType type = super.getFileType();
		if (null == type) {
			type = AttachmentType.suggestFileType(readAttachmentData());
			setFileType(type);
		}
		return type;
	}

	@Override
	public byte[] readAttachmentData() {
		return bytes;
	}
}
