package com.my.pojo;

public class GoodsList {
    private Integer listId;

    private Integer orderId;

    private Integer goodsId;

    private Integer goodsNum;

    public GoodsList() {
    }

    public GoodsList(Integer listId, Integer orderId, Integer goodsId, Integer goodsNum) {
        this.listId = listId;
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.goodsNum = goodsNum;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }
}