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

import com.deepoove.poi.data.style.StyleBuilder;

/**
 * 超链接
 * 
 * @author Sayi
 * @version 1.4.0
 */
public class HyperLinkTextRenderData extends TextRenderData {

    private static final long serialVersionUID = 1L;

    /**
     * 超链接: http://deepoove.com <br/>
     * 发送邮件链接:mailto:adasai90@gmail.com?subject=poi-tl <br/>
     * 锚点：anchor:AnchorName
     */
    private String url;

    HyperLinkTextRenderData() {
    }

    public HyperLinkTextRenderData(String text, String url) {
        super(text);
        this.url = url;
        // 链接默认蓝色加下划线
        this.style = StyleBuilder.newBuilder().buildColor("0000FF").buildUnderLine().build();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
