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


public class OleObjectRenderData extends AttachmentRenderData {

    private static final Logger logger = LoggerFactory.getLogger(ByteUtils.class);

    private final byte[] origin;

    private final String fileName;

    public OleObjectRenderData(byte[] origin, String fileName) {
        this.origin = origin;
        this.fileName = fileName;
        this.setFileType(AttachmentType.OLE);
    }

    @Override
    public byte[] readAttachmentData() {

        return origin;
    }
}
