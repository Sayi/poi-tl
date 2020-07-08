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

/**
 * 图表系列
 * 
 * @author Sayi
 * @version 1.8.0
 */
public class SeriesRenderData {

    private String name;
    private Number[] values;

    public SeriesRenderData() {}

    public SeriesRenderData(String name, Number[] data) {
        this.name = name;
        this.values = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number[] getValues() {
        return values;
    }

    public void setValues(Number[] data) {
        this.values = data;
    }

}
