package com.deepoove.poi.data;

import com.deepoove.poi.util.ByteUtils;
import org.apache.commons.io.output.UnsynchronizedByteArrayOutputStream;
import org.apache.poi.hpsf.ClassIDPredefined;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.Ole10Native;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class OleObjectRenderData extends AttachmentRenderData{

	private static final Logger logger = LoggerFactory.getLogger(ByteUtils.class);

	private final byte[] origin;

	private final String fileName;

	public OleObjectRenderData(byte[] origin,String fileName) {
		this.origin = origin;
		this.fileName = fileName;
		this.setFileType(AttachmentType.OLE);
	}

	@Override
	public byte[] readAttachmentData() {
		Ole10Native ole10 = new Ole10Native(fileName, fileName, fileName, origin);

		try (UnsynchronizedByteArrayOutputStream bos = new UnsynchronizedByteArrayOutputStream(origin.length+500)) {
			ole10.writeOut(bos);
			try (POIFSFileSystem poifs = new POIFSFileSystem()) {
				DirectoryNode root = poifs.getRoot();
				root.createDocument(Ole10Native.OLE10_NATIVE, bos.toInputStream());
				root.setStorageClsid(ClassIDPredefined.OLE_V1_PACKAGE.getClassID());
				try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
					poifs.writeFilesystem(os);
					return os.toByteArray();
				}
			}
		} catch (IOException e) {
			logger.error("get OleObjectData error,{},{}", fileName, e);
		}
		return null;
	}
}
