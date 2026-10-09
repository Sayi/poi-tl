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

/**
 * 通用附件渲染数据
 *
 * @author yangxiao, Sayi
 */
public class GeneralAttachmentRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    /**
     * 通用附件插入 Word 显示的默认图标
     */
    private static final String DEFAULT_ICON = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAgY0hSTQAAeiYAAICEAAD6AAAAgOgAAHUwAADqYAAAOpgAABdwnLpRPAAAAqRJREFUeNrtm01rE1EUhs+kUaimyWDJCDWZnWg+/KAiCMWNf0DEKlhw46YYXTYLwbgoTvsP3FlBtKA/QETFjaJgpQjGIiLCxJUrM1kq+Hq4dzEOFAultzO9OS88hBDI3PvMnJObywxJJBKJZHPZxexn/G2gRBnLFPOYWWNCw/SYZ0wjS5P/Sk4OtGcc5PrmKE6AcnkQEZjXTJ1STp55xIBOXAFd/wBqh6A5A7R7oNk3LOGAEpAVCR7TVWf+2ipoEaDAEAvQcktVOHuLGKkezoQEnwnV5TkX6oHeNkQAfYxiBTmvisLCE+SPnE5IGB4B5QqKyyGKS2vIN6cSEoZHwINvcF8CY3e7G0iwWcDzP3BfrC/BTgE3foC8OpxCCWN3VtQVwBLUqyqHuCe8Ymo2CdDM/wYdnVGT3H3mkpLAV4JmOVSNMVc99K+Eul0CAmb2LWj8oJqkU3DB5RDj+XBGC1qA5inj2iIglnD1Hej4ZVC5pnpCgpKvF0t6xRgyvk0CYgnzv8A94T8rxgl7BWg2WDG6fpYFmP65FAEiQASIABGQIQGBCXaKgJt99ZkROlGmBegBNqb1qqxU2Ur0dzbOs+CfoCCzAgag5kVzApoXlOQsl4AeYPu7GW4N1DGGV0BnID1AeoD0AOkB0gOkB9hbAr3EXh+z3lLYWgG6yQXQdKLke30se3tAXON9Te0sqH4uUfMxFvaAuMYHTKQmr4VEO2w/oNPfmr+7nT4TDdOGiGyJiQARIAJEgAgQASJABIiALAvwmI80ug/Ueh/fLJ0mi0xrBWpMPDambPp2+YcM6NgMqLWq7KeIPhHNaRARmCVmhAznFPOFHCd+YCJN+MyrsRB9ZiZpm3KSuc98YsKU6TL3mMk0HpryGD9lykxenmGTSCSSTeQv2r2QaWU+QhYAAAAASUVORK5CYII=";

    /**
     * 附件打开关联程序 ID
     */
    private static final String PROGRAM_ID = "Package";

    /**
     * 通用附件在 Word 中嵌入对象的内容类型
     */
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.oleObject";

    /**
     * 输入二进制数据
     */
    private final byte[] bytes;

    /**
     * 文件名
     */
    private final String fileName;

    /**
     * 自定义图标
     */
    private PictureRenderData icon;

    public GeneralAttachmentRenderData(byte[] bytes, String fileName) {
        this(bytes, fileName, null);
    }

    public GeneralAttachmentRenderData(byte[] bytes, String fileName, PictureRenderData icon) {
        this.bytes = bytes;
        this.fileName = fileName;
        this.icon = icon;
    }

    public byte[] readAttachmentData() {
        return this.bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public PictureRenderData getIcon() {
        if (null != icon) {
            return icon;
        }
        return Pictures.ofBase64(DEFAULT_ICON, PictureType.PNG).size(64, 64).create();
    }

    public GeneralAttachmentRenderData setIcon(PictureRenderData icon) {
        this.icon = icon;
        return this;
    }

    public String getProgramId() {
        return PROGRAM_ID;
    }

    public String getContentType() {
        return CONTENT_TYPE;
    }
}
