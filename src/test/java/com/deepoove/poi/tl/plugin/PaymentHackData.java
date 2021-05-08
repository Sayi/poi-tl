package com.deepoove.poi.tl.plugin;

import java.util.List;

import com.deepoove.poi.data.PictureRenderData;

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

class Goods {
    private int count;
    private String name;
    private String desc;
    private int discount;
    private int tax;
    private int price;
    private int totalPrice;

    private PictureRenderData picture;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PictureRenderData getPicture() {
        return picture;
    }

    public void setPicture(PictureRenderData picture) {
        this.picture = picture;
    }

}

class Labor {
    private String category;
    private int people;
    private int price;
    private int totalPrice;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}
