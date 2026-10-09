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
 * Bubble series data: X values, Y values, and Bubble sizes
 * 
 * @author Sayi
 */
public class BubbleSeriesRenderData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Number[] xValues;
    private Number[] yValues;
    private Number[] bubbleSizes;
    private String color;
    private Boolean showDataLabels;

    public BubbleSeriesRenderData() {
    }

    public BubbleSeriesRenderData(String name, Number[] xValues, Number[] yValues, Number[] bubbleSizes) {
        this.name = name;
        this.xValues = xValues;
        this.yValues = yValues;
        this.bubbleSizes = bubbleSizes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number[] getxValues() {
        return xValues;
    }

    public void setxValues(Number[] xValues) {
        this.xValues = xValues;
    }

    public Number[] getyValues() {
        return yValues;
    }

    public void setyValues(Number[] yValues) {
        this.yValues = yValues;
    }

    public Number[] getBubbleSizes() {
        return bubbleSizes;
    }

    public void setBubbleSizes(Number[] bubbleSizes) {
        this.bubbleSizes = bubbleSizes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getShowDataLabels() {
        return showDataLabels;
    }

    public void setShowDataLabels(Boolean showDataLabels) {
        this.showDataLabels = showDataLabels;
    }

    public BubbleSeriesRenderData color(String color) {
        this.color = color;
        return this;
    }

    public BubbleSeriesRenderData showDataLabels(Boolean showDataLabels) {
        this.showDataLabels = showDataLabels;
        return this;
    }

}
