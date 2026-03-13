package com.deepoove.poi.data;

/**
 * 通用附件渲染数据
 * @author yangxiao
 */
public class GeneralAttachmentRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    /**
     * 通用附件插入 Word 显示的图标
     */
    private static final String ICON = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAgY0hSTQAAeiYAAICEAAD6AAAAgOgAAHUwAADqYAAAOpgAABdwnLpRPAAAAqRJREFUeNrtm01rE1EUhs+kUaimyWDJCDWZnWg+/KAiCMWNf0DEKlhw46YYXTYLwbgoTvsP3FlBtKA/QETFjaJgpQjGIiLCxJUrM1kq+Hq4dzEOFAultzO9OS88hBDI3PvMnJObywxJJBKJZHPZxexn/G2gRBnLFPOYWWNCw/SYZ0wjS5P/Sk4OtGcc5PrmKE6AcnkQEZjXTJ1STp55xIBOXAFd/wBqh6A5A7R7oNk3LOGAEpAVCR7TVWf+2ipoEaDAEAvQcktVOHuLGKkezoQEnwnV5TkX6oHeNkQAfYxiBTmvisLCE+SPnE5IGB4B5QqKyyGKS2vIN6cSEoZHwINvcF8CY3e7G0iwWcDzP3BfrC/BTgE3foC8OpxCCWN3VtQVwBLUqyqHuCe8Ymo2CdDM/wYdnVGT3H3mkpLAV4JmOVSNMVc99K+Eul0CAmb2LWj8oJqkU3DB5RDj+XBGC1qA5inj2iIglnD1Hej4ZVC5pnpCgpKvF0t6xRgyvk0CYgnzv8A94T8rxgl7BWg2WDG6fpYFmP65FAEiQASIABGQIQGBCXaKgJt99ZkROlGmBegBNqb1qqxU2Ur0dzbOs+CfoCCzAgag5kVzApoXlOQsl4AeYPu7GW4N1DGGV0BnID1AeoD0AOkB0gOkB9hbAr3EXh+z3lLYWgG6yQXQdKLke30se3tAXON9Te0sqH4uUfMxFvaAuMYHTKQmr4VEO2w/oNPfmr+7nT4TDdOGiGyJiQARIAJEgAgQASJABIiALAvwmI80ug/Ueh/fLJ0mi0xrBWpMPDambPp2+YcM6NgMqLWq7KeIPhHNaRARmCVmhAznFPOFHCd+YCJN+MyrsRB9ZiZpm3KSuc98YsKU6TL3mMk0HpryGD9lykxenmGTSCSSTeQv2r2QaWU+QhYAAAAASUVORK5CYII=";

    /**
     * 附件打开关联程序 ID
     */
    private static final String PROGRAM_ID = "Package";

    /**
     * 通用附件在 Word 中嵌入对象的内容类型
     */
    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.oleObject";

    /**
     * 输入流
     */
    private final byte[] bytes;
    /**
     * 文件名
     */
    private final String fileName;

    /**
     * 默认构造函数
     * @param bytes 附件二进制数据
     * @param fileName 附件文件名
     */
    private GeneralAttachmentRenderData(byte[] bytes, String fileName) {
        this.bytes = bytes;
        this.fileName = fileName;
    }

    /**
     * 使用静态方法构建通用附件渲染数据对象
     * @param data 附件二进制数据
     * @param fileName 附件文件名
     * @return {@link GeneralAttachmentRenderData}
     */
    public static GeneralAttachmentRenderData of(byte[] data, String fileName) {
        return new GeneralAttachmentRenderData(data, fileName);
    }

    /**
     * 获取输入流
     * @return 附件二进制数据
     */
    public byte[] readAttachmentData() {
        return this.bytes;
    }

    /**
     * 获取文件名
     * @return 返回文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 获取通用文件图标
     * @return {@link PictureRenderData}
     */
    public PictureRenderData getIcon() {
        return Pictures.ofBase64(ICON, PictureType.PNG).size(64, 64).create();
    }

    /**
     * 获取程序ID
     * @return 关联程序ID
     */
    public String getProgramId() {
        return PROGRAM_ID;
    }

    /**
     * 获取内容类型
     * @return 返回内容类型
     */
    public String getContentType() {
        return CONTENT_TYPE;
    }
}
