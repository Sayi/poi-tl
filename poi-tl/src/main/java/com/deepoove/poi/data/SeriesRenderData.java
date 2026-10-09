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
package com.deepoove.poi.data;

import java.io.Serializable;

/**
 * One Series data
 * 
 * @author Sayi
 */
public class SeriesRenderData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * series name
     */
    private String name;
    /**
     * value must be mapped one to one with category
     */
    private Number[] values;

    /**
     * Only specify the type of series in the combination chart
     */
    private ComboType comboType;

    /**
     * Custom series color (hex string, e.g. "#FF5733" or "FF5733", or "transparent")
     */
    private String color;

    /**
     * Custom colors for individual data points / slices in pie or doughnut charts
     */
    private String[] colors;

    /**
     * Whether to show data labels for this series
     */
    private Boolean showDataLabels;

    /**
     * Whether to bind this series to the secondary value axis (right Y-axis) in a combo chart
     */
    private boolean secondaryAxis = false;

    public SeriesRenderData() {
    }

    public enum ComboType {
        BAR, LINE, AREA;
    }

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

    public ComboType getComboType() {
        return comboType;
    }

    public void setComboType(ComboType comboType) {
        this.comboType = comboType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public Boolean getShowDataLabels() {
        return showDataLabels;
    }

    public void setShowDataLabels(Boolean showDataLabels) {
        this.showDataLabels = showDataLabels;
    }

    public boolean isSecondaryAxis() {
        return secondaryAxis;
    }

    public void setSecondaryAxis(boolean secondaryAxis) {
        this.secondaryAxis = secondaryAxis;
    }

    public SeriesRenderData color(String color) {
        this.color = color;
        return this;
    }

    public SeriesRenderData colors(String... colors) {
        this.colors = colors;
        return this;
    }

    public SeriesRenderData showDataLabels(Boolean showDataLabels) {
        this.showDataLabels = showDataLabels;
        return this;
    }

    public SeriesRenderData secondaryAxis(boolean secondaryAxis) {
        this.secondaryAxis = secondaryAxis;
        return this;
    }

}
