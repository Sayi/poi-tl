/*
 * Copyright 2014-2026 Sayi
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

/**
 * One parsed CSS rule of a highlight stylesheet.
 * <p>
 * It pairs a selector, for example {@code .keyword} or {@code .cm-comment},
 * with the declarations that were found for it. The stylesheet parser feeds
 * these rules to the converter, which turns them into Word run styles.
 * </p>
 */
public class SelectorStyle {

    private String selectorName;
    private Map<String, String> propertyValues = new LinkedHashMap<String, String>();

    public SelectorStyle() {
    }

    /**
     * Creates a rule for the given selector.
     *
     * @param name the selector name
     */
    public SelectorStyle(String name) {
        this.selectorName = name;
    }

    /**
     * Returns the selector name of this rule.
     *
     * @return the selector name
     */
    public String getSelectorName() {
        return selectorName;
    }

    /**
     * Sets the selector name of this rule.
     *
     * @param selectorName the selector name to set
     */
    public void setSelectorName(String selectorName) {
        this.selectorName = selectorName;
    }

    /**
     * Returns the declarations of this rule, keyed by property name.
     *
     * @return the property values
     */
    public Map<String, String> getPropertyValues() {
        return propertyValues;
    }

    /**
     * Sets the declarations of this rule.
     *
     * @param propertyValues the property values to set
     */
    public void setPropertyValues(Map<String, String> propertyValues) {
        this.propertyValues = propertyValues;
    }

    @Override
    public String toString() {
        return "SelectorStyle [selectorName=" + selectorName + ", propertyValues=" + propertyValues + "]";
    }

}
