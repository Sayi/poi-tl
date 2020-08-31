package com.deepoove.poi.tl.example;

import java.util.List;

import com.deepoove.poi.data.RowV2RenderData;

public class DetailData {
    
    // 货品数据
    private List<RowV2RenderData> goods;
    
    // 人工费数据
    private List<RowV2RenderData> labors;

    public List<RowV2RenderData> getGoods() {
        return goods;
    }

    public void setGoods(List<RowV2RenderData> goods) {
        this.goods = goods;
    }

    public List<RowV2RenderData> getLabors() {
        return labors;
    }

    public void setLabors(List<RowV2RenderData> labors) {
        this.labors = labors;
    }
}
