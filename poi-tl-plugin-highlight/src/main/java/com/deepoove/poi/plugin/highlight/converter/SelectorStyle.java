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
package com.deepoove.poi.plugin.highlight.converter;

import java.util.LinkedHashMap;
import java.util.Map;

public class SelectorStyle {

    private String selectorName;
    private Map<String, String> propertyValues = new LinkedHashMap<String, String>();

    public SelectorStyle() {
    }

    public SelectorStyle(String name) {
        this.selectorName = name;
    }

    public String getSelectorName() {
        return selectorName;
    }

    public void setSelectorName(String selectorName) {
        this.selectorName = selectorName;
    }

    public Map<String, String> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(Map<String, String> propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public String toString() {
        return "SelectorStyle [selectorName=" + selectorName + ", propertyValues=" + propertyValues + "]";
    }

}
