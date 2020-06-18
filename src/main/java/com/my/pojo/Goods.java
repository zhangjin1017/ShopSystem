package com.my.pojo;

public class Goods {
    private Integer goodsId;

    private Integer businessId;

    private String name;

    private Double price;

    private Integer typeId;

    private String imgUrl;

    private Integer stock;

    private String info;

    public Goods() {
    }

    public Goods(Integer goodsId, Integer businessId, String name, Double price, Integer typeId, String imgUrl, Integer stock, String info) {
        this.goodsId = goodsId;
        this.businessId = businessId;
        this.name = name;
        this.price = price;
        this.typeId = typeId;
        this.imgUrl = imgUrl;
        this.stock = stock;
        this.info = info;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }
}