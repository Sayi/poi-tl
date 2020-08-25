/*
 * Copyright 2014-2020 Sayi
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
 * 待合并子文档和数据集合
 * 
 * @author Sayi
 * @version 1.3.0
 */
public class DocxRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    /**
     * stream流无法重用，使用字节数组表示待合并文档
     */
    private byte[] mergeDoc;

    /**
     * 渲染待合并文档模板的数据集合，若合并文档不是个模板，可为空
     */
    private List<?> dataModels;

    /**
     * @param docx 子文档
     */
    public DocxRenderData(File docx) {
        this(docx, null);
    }

    /**
     * 构造子文档和渲染数据源
     * 
     * @param docx        子文档
     * @param renderDatas 渲染数据列表，列表的大小表示循环的次数
     */
    public DocxRenderData(File docx, List<?> renderDatas) {
        this(ByteUtils.getLocalByteArray(docx), renderDatas);
    }

    /**
     * @param inputStream 子文档流
     */
    public DocxRenderData(InputStream inputStream) {
        this(inputStream, null);
    }

    /**
     * @param inputStream
     * @param renderDatas
     */
    public DocxRenderData(InputStream inputStream, List<?> renderDatas) {
        this(ByteUtils.toByteArray(inputStream), renderDatas);
    }

    /**
     * @param input       子文档字节数组
     * @param renderDatas
     */
    public DocxRenderData(byte[] input, List<?> renderDatas) {
        this.dataModels = renderDatas;
        this.mergeDoc = input;
    }

    public byte[] getDocx() {
        return mergeDoc;
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
