package com.deepoove.poi.tl.plugin;

import java.util.List;

public class PaymentHackData {
    private String subtotal;
    private String tax;
    private String transform;
    private String other;
    private String unpay;
    private String total;

    private List<Goods> goods;
    private List<Goods> goods2;
    private List<Labor> labors;
    private List<Labor> labors2;

    public List<Goods> getGoods2() {
        return goods2;
    }

    public void setGoods2(List<Goods> goods2) {
        this.goods2 = goods2;
    }

    public List<Labor> getLabors2() {
        return labors2;
    }

    public void setLabors2(List<Labor> labors2) {
        this.labors2 = labors2;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public List<Labor> getLabors() {
        return labors;
    }

    public void setLabors(List<Labor> labors) {
        this.labors = labors;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getSubtotal() {
        return this.subtotal;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTax() {
        return this.tax;
    }

    public void setTransform(String transform) {
        this.transform = transform;
    }

    public String getTransform() {
        return this.transform;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOther() {
        return this.other;
    }

    public void setUnpay(String unpay) {
        this.unpay = unpay;
    }

    public String getUnpay() {
        return this.unpay;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
