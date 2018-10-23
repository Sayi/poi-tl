package com.deepoove.poi.data;

import com.deepoove.poi.data.style.Style;

/**
 * 超链接
 * @author Sayi
 * @version 1.4.0 
 */
public class HyperLinkTextRenderData extends TextRenderData{
    
    private String url;
    
    public HyperLinkTextRenderData(String text, String url) {
        super(text);
        this.url = url;
        this.style = new Style("0000FF");
        this.style.setUnderLine(true);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

}
