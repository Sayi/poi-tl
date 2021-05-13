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
import java.util.List;

import com.deepoove.poi.util.ByteUtils;

/**
 * Nested/Merge/Include/Reference docx
 * 
 * @author Sayi
 */
public class DocxRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    /**
     * docx byte array to be merged
     */
    private byte[] mergedDoc;

    /**
     * Render data for the docx template to be merged. If the merged document is not a template, it can be empty
     */
    private List<?> dataModels;

    DocxRenderData() {
    }

    /**
     * @param docx file to be merged
     */
    public DocxRenderData(File docx) {
        this(docx, null);
    }

    /**
     * @param docx        file to be merged
     * @param renderDatas Render data for the docx template, the size of the list indicates the number of cycles
     */
    public DocxRenderData(File docx, List<?> renderDatas) {
        this(ByteUtils.getLocalByteArray(docx), renderDatas);
    }

    /**
     * @param inputStream stream to be merged
     */
    public DocxRenderData(InputStream inputStream) {
        this(inputStream, null);
    }

    /**
     * @param inputStream stream to be merged
     * @param renderDatas Render data for the stream template, the size of the list indicates the number of cycles
     */
    public DocxRenderData(InputStream inputStream, List<?> renderDatas) {
        this(ByteUtils.toByteArray(inputStream), renderDatas);
    }

    /**
     * @param input       byte array to be merged
     * @param renderDatas
     */
    public DocxRenderData(byte[] input, List<?> renderDatas) {
        this.dataModels = renderDatas;
        this.mergedDoc = input;
    }

    public byte[] getMergedDoc() {
        return mergedDoc;
    }

    public List<?> getDataModels() {
        return dataModels;
    }

    public void setDataModels(List<?> renderDatas) {
        this.dataModels = renderDatas;
    }

    public void setRenderDatas(List<?> renderDatas) {
        this.dataModels = renderDatas;
    }

}
