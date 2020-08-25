package com.deepoove.poi.data;

import com.deepoove.poi.exception.RenderException;

/**
 * @author Sayi
 * @see org.apache.poi.xwpf.usermodel.Document
 */
public enum PictureType {
    /**
     * Extended windows meta file
     */
    EMF(2),

    /**
     * Windows Meta File
     */
    WMF(3),

    /**
     * Mac PICT format
     */
    PICT(4),

    /**
     * JPEG format
     */
    JPEG(5),

    /**
     * PNG format
     */
    PNG(6),

    /**
     * Device independent bitmap
     */
    DIB(7),

    /**
     * GIF image format
     */
    GIF(8),

    /**
     * Tag Image File (.tiff)
     */
    TIFF(9),

    /**
     * Encapsulated Postscript (.eps)
     */
    EPS(10),

    /**
     * Windows Bitmap (.bmp)
     */
    BMP(11),

    /**
     * WordPerfect graphics (.wpg)
     */
    WPG(12);

    private int type;

    PictureType(int type) {
        this.type = type;
    }

    public int type() {
        return this.type;
    }

    public String format() {
        return super.name().toLowerCase();
    }

    public static PictureType suggestFileType(String imgFile) {
        PictureType format = null;

        imgFile = imgFile.toLowerCase();
        if (imgFile.endsWith(".emf"))
            format = EMF;
        else if (imgFile.endsWith(".wmf"))
            format = WMF;
        else if (imgFile.endsWith(".pict"))
            format = PICT;
        else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg"))
            format = JPEG;
        else if (imgFile.endsWith(".png"))
            format = PNG;
        else if (imgFile.endsWith(".dib"))
            format = DIB;
        else if (imgFile.endsWith(".gif"))
            format = GIF;
        else if (imgFile.endsWith(".tiff"))
            format = TIFF;
        else if (imgFile.endsWith(".eps"))
            format = EPS;
        else if (imgFile.endsWith(".bmp"))
            format = BMP;
        else if (imgFile.endsWith(".wpg"))
            format = WPG;
        else {
            throw new RenderException(
                    "Unsupported picture: " + imgFile + ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");
        }
        return format;
    }

}
