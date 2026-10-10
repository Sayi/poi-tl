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
package com.deepoove.poi.data.control;

import java.io.Serializable;

/**
 * One entry of a drop-down list or combo box.
 */
public final class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Text shown in Word ({@code w:displayText}). */
    private String label;
    /** Business value stored on the list item ({@code w:value}). */
    private String value;

    public Option() {
    }

    public Option(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public static Option of(String label, String value) {
        return new Option(label, value);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
