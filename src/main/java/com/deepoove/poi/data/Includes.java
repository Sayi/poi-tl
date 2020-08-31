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
 * builder to build {@link DocxRenderData}
 * 
 * @author Sayi
 *
 */
public class Includes implements RenderDataBuilder<DocxRenderData> {

    DocxRenderData data;

    private Includes() {
    }

    public static Includes ofLocal(String src) {
        return ofBytes(ByteUtils.getLocalByteArray(new File(src)));
    }

    public static Includes ofStream(InputStream inputStream) {
        return ofBytes(ByteUtils.toByteArray(inputStream));
    }

    public static Includes ofBytes(byte[] bytes) {
        Includes inst = new Includes();
        inst.data = new DocxRenderData(bytes, null);
        return inst;
    }

    public Includes setRenderModel(List<?> models) {
        this.data.setDataModels(models);
        return this;
    }

    @Override
    public DocxRenderData create() {
        return data;
    }

}
