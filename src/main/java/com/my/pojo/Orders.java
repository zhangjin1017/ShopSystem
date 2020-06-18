package com.my.pojo;

import java.util.Date;

public class Orders {
    private String orderId;

    private Integer businessId;

    private Integer userId;

    private Integer goodsId;

    private Date date;

    private Integer type;

    private Integer addressId;

    private String logistics;

    public Orders() {
    }

    public Orders(String orderId, Integer businessId, Integer userId, Integer goodsId, Date date, Integer type, Integer addressId, String logistics) {
        this.orderId = orderId;
        this.businessId = businessId;
        this.userId = userId;
        this.goodsId = goodsId;
        this.date = date;
        this.type = type;
        this.addressId = addressId;
        this.logistics = logistics;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics == null ? null : logistics.trim();
    }
}