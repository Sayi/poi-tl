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

import java.util.ArrayList;
import java.util.List;

/**
 * A drop-down list ({@code w:dropDownList}) or combo box ({@code w:comboBox}).
 * <p>
 * {@link #selectedValue} is matched against {@link Option#getValue()}, not the
 * label. A missing match shows {@link #placeholder} and marks the control as
 * showing a placeholder; the first option is never selected implicitly.
 */
public class DropDownControlRenderData extends ControlRenderData {

    private static final long serialVersionUID = 1L;

    private final List<Option> options = new ArrayList<Option>();
    private String selectedValue;
    private String placeholder = "请选择";
    /** {@code true} writes {@code w:comboBox}, which also allows free text. */
    private boolean comboBox;

    public List<Option> getOptions() {
        return options;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public boolean isComboBox() {
        return comboBox;
    }

    public void setComboBox(boolean comboBox) {
        this.comboBox = comboBox;
    }

}
