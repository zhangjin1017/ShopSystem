package com.my.pojo;

import java.util.Date;

public class Stock {
    public static int IN = 1;
    public static int OUT =2;
    private Integer stockId;

    private Integer goodsId;

    private Integer type;

    private Date date;

    private Integer num;

    public Stock() {
    }

    public Stock(Integer stockId, Integer goodsId, Integer type, Date date, Integer num) {
        this.stockId = stockId;
        this.goodsId = goodsId;
        this.type = type;
        this.date = date;
        this.num = num;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}