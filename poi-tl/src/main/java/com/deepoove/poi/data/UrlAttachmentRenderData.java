/*
 * Copyright 2014-2024 Sayi
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
