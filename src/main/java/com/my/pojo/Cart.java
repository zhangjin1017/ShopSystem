package com.my.pojo;

public class Cart {
    private Integer cartId;

    private Integer userId;

    private Integer goodsId;

    private Integer num;

    private Integer type;

    public Cart() {
    }

    public Cart(Integer cartId, Integer userId, Integer goodsId, Integer num, Integer type) {
        this.cartId = cartId;
        this.userId = userId;
        this.goodsId = goodsId;
        this.num = num;
        this.type = type;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}