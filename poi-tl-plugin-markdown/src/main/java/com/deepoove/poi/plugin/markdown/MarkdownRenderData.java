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
package com.deepoove.poi.plugin.markdown;

import com.deepoove.poi.data.RenderData;

/**
 * @author Sayi
 */
public class MarkdownRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private String markdown;

    private MarkdownStyle style;

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public MarkdownStyle getStyle() {
        return style;
    }

    public void setStyle(MarkdownStyle style) {
        this.style = style;
    }

}
