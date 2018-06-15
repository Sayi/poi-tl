package com.deepoove.poi.data;

import java.io.File;
import java.util.List;

public class DocxRenderData implements RenderData {

    private File docx;

    private List<?> dataList;
    
    public DocxRenderData(File docx) {
        this.docx = docx;
    }
    public DocxRenderData(File docx, List<?> dataList) {
        this.docx = docx;
        this.dataList = dataList;
    }

    public File getDocx() {
        return docx;
    }

    public void setDocx(File docx) {
        this.docx = docx;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }
    
    

    

}
