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

import java.io.File;
import java.io.InputStream;

import com.deepoove.poi.util.ByteUtils;

/**
 * attachment file:docx or xlsx
 * 
 * @author Sayi
 */
public class AttachmentRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private byte[] attachment;
    private AttachmentType fileType;
    private PictureRenderData icon;

    AttachmentRenderData() {
    }

    public AttachmentRenderData(File attachmentFile) {
        this(ByteUtils.getLocalByteArray(attachmentFile));
    }

    public AttachmentRenderData(InputStream inputStream) {
        this(ByteUtils.toByteArray(inputStream));
    }

    public AttachmentRenderData(byte[] input) {
        this.attachment = input;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public AttachmentType getFileType() {
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

}
