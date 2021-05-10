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
package com.deepoove.poi.plugin.highlight;

/**
 * highlight data style
 * 
 * @author Sayi
 *
 */
public class HighlightStyle {

    private boolean showLine;
    private String theme;
    private String fontFamily;
    private double fontSize;

    public HighlightStyle() {
    }

    private HighlightStyle(Builder builder) {
        this.showLine = builder.showLine;
        this.theme = builder.theme;
        this.fontFamily = builder.fontFamily;
        this.fontSize = builder.fontSize;
    }

    public boolean isShowLine() {
        return showLine;
    }

    public void setShowLine(boolean showLine) {
        this.showLine = showLine;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Creates builder to build {@link HighlightStyle}.
     * 
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link HighlightStyle}.
     */
    public static final class Builder {
        private boolean showLine;
        private String theme;
        private String fontFamily;
        private double fontSize;

        private Builder() {
        }

        public Builder withShowLine(boolean showLine) {
            this.showLine = showLine;
            return this;
        }

        public Builder withTheme(String theme) {
            this.theme = theme;
            return this;
        }

        public Builder withFontFamily(String fontFamily) {
            this.fontFamily = fontFamily;
            return this;
        }

        public Builder withFontSize(double fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public HighlightStyle build() {
            return new HighlightStyle(this);
        }
    }

}
